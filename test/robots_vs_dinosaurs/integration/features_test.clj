(ns robots-vs-dinosaurs.integration.features-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [robots-vs-dinosaurs.db.saving-grid :as db.saving-grid]
            [robots-vs-dinosaurs.db.saving-robot :as db.saving-robot]
            [robots-vs-dinosaurs.db.saving-dinosaur :as db.saving-dinosaur]
            [robots-vs-dinosaurs.controller :as controller]
            [robots-vs-dinosaurs.supervisor]
            [robots-vs-dinosaurs.service :as service]
            [robots-vs-dinosaurs.middleware.exception :refer [wrap-exception-handling]]))

(def handler
  (-> service/all-route
      wrap-exception-handling))

(deftest validate-create-grid
  (testing "Should create a new grid successfully"
    (with-redefs
      [db.saving-grid/save-grid
       (fn [size-x size-y] {:id     1
                            :size-x 50
                            :size-y 50})]
      (is (= (handler (-> (mock/request :post "/grids")))
             {:status  201
              :headers {"Location" nil}
              :body    {:id     1
                        :size-x 50
                        :size-y 50}})))))

(deftest validate-create-robot
  (testing "Should create a new robot successfully"
    (with-redefs
      [controller/entity-within-the-grid? (fn [entity] true)
       controller/entity-position-available? (fn [entity] true)
       db.saving-robot/save-robot
       (fn [robot] {:id        1
                    :pos-x     0
                    :pos-y     0
                    :direction "N"
                    :grid-id   1})]
      (is (= (handler
               (-> (mock/request :post "/robots")
                   (mock/json-body {:pos-x     0
                                    :pos-y     0
                                    :direction "N"
                                    :grid-id   1})))
             {:status  201
              :headers {"Location" nil}
              :body    {:id        1
                        :pos-x     0
                        :pos-y     0
                        :direction "N"
                        :grid-id   1}}))))

  (testing "Should not create a new robot (outside the grid)"
    (with-redefs
      [controller/entity-within-the-grid? (fn [entity] false)
       controller/entity-position-available? (fn [entity] true)]
      (is (= (handler
               (-> (mock/request :post "/robots")
                   (mock/json-body {:pos-x     51
                                    :pos-y     0
                                    :direction "N"
                                    :grid-id   1})))
             {:status  400
              :headers {"Content-Type" "application/problem+json"}
              :body    {:title  "Bad Request"
                        :status 400
                        :detail "Position of the robot outside the grid"}}))))

  (testing "Should not create a new robot (position not available)"
    (with-redefs
      [controller/entity-within-the-grid? (fn [entity] true)
       controller/entity-position-available? (fn [entity] false)]
      (is (= (handler
               (-> (mock/request :post "/robots")
                   (mock/json-body {:pos-x     0
                                    :pos-y     0
                                    :direction "N"
                                    :grid-id   1})))
             {:status  400
              :headers {"Content-Type" "application/problem+json"}
              :body    {:title  "Bad Request"
                        :status 400
                        :detail "An entity already exists in this position"}})))))

(deftest validate-create-dinosaur
  (testing "Should create a new dinosaur successfully"
    (with-redefs
      [controller/entity-within-the-grid? (fn [entity] true)
       controller/entity-position-available? (fn [entity] true)
       db.saving-dinosaur/save-dinosaur
       (fn [dinosaur]
         {:id      1
          :pos-x   0
          :pos-y   0
          :grid-id 1})]
      (is (= (handler
               (-> (mock/request :post "/dinosaurs")
                   (mock/json-body {:pos-x   0
                                    :pos-y   0
                                    :grid-id 1})))
             {:status  201
              :headers {"Location" nil}
              :body    {:id      1
                        :pos-x   0
                        :pos-y   0
                        :grid-id 1}}))))

  (testing "Should not create a new dinosaur (outside the grid)"
    (with-redefs
      [controller/entity-within-the-grid? (fn [entity] false)
       controller/entity-position-available? (fn [entity] true)]
      (is (= (handler
               (-> (mock/request :post "/dinosaurs")
                   (mock/json-body {:pos-x   0
                                    :pos-y   0
                                    :grid-id 1})))
             {:status  400
              :headers {"Content-Type" "application/problem+json"}
              :body    {:title  "Bad Request"
                        :status 400
                        :detail "Position of the dinosaur outside the grid"}}))))

  (testing "Should not create a new dinosaur (position not available)"
    (with-redefs
      [controller/entity-within-the-grid? (fn [entity] true)
       controller/entity-position-available? (fn [entity] false)]
      (is (= (handler
               (-> (mock/request :post "/dinosaurs")
                   (mock/json-body {:pos-x   0
                                    :pos-y   0
                                    :grid-id 1})))
             {:status  400
              :headers {"Content-Type" "application/problem+json"}
              :body    {:title  "Bad Request"
                        :status 400
                        :detail "An entity already exists in this position"}})))))

(deftest validate-create-instruction
  (testing "Should send robot an instruction successfully"
    (with-redefs [controller/entity-within-the-grid? (fn [entity] true)
                  controller/entity-position-available? (fn [entity] true)
                  db.saving-robot/find-robot (fn [entity] {:direction "N"})
                  db.saving-robot/update-robot (fn [entity] {})]
      (is (= (handler (-> (mock/request :put "/robots/1/instructions")
                          (mock/json-body {:action "turn-left"})))
             {:status  204
              :headers {}
              :body    ""})))))

(deftest validate-display-status
  (testing "Should show current state of match"
    (with-redefs
      [db.saving-grid/find-grid
       (fn [grid-id]
         {:id        1
          :size-x    50
          :size-y    50
          :robots    [{:id        1
                       :pos-x     0
                       :pos-y     0
                       :direction "N"}]
          :dinosaurs [{:id    1
                       :pos-x 0
                       :pos-y 0}]})]
      (is (= (handler (-> (mock/request :get "/grids/1")))
             {:status  200
              :headers {}
              :body    {:id        1
                        :size-x    50
                        :size-y    50
                        :robots    [{:id        1
                                     :pos-x     0
                                     :pos-y     0
                                     :direction "N"}]
                        :dinosaurs [{:id    1
                                     :pos-x 0
                                     :pos-y 0}]}})))

    (testing "Should return not found for nonexistent grid"
      (with-redefs [db.saving-grid/find-grid (fn [grid-id] nil)]
        (is (= (handler (-> (mock/request :get "/grids/1")))
               {:status  404
                :headers {"Content-Type" "application/problem+json"}
                :body    {:title  "Not Found"
                          :status 404
                          :detail "Grid not found with id 1"}}))))))