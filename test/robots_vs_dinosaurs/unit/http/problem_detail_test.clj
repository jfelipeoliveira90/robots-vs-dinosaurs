(ns robots-vs-dinosaurs.unit.http.problem-detail-test
  (:require [clojure.test :refer :all]
            [robots-vs-dinosaurs.http.problem-detail :as problem-detail]))

(deftest validate-response-body
  (let [response (problem-detail/response 200 "Title Example" "Detail example")]
    (testing "Validate status property"
      (is (not= nil (get response :status))))

    (testing "Validate headers property"
      (is (not= nil (get response :headers)))
      (is (= "application/problem+json" (get-in response [:headers "Content-Type"]))))

    (testing "Validate body property"
      (is (not= nil (get response :body)))
      (is (not= nil (get-in response [:body :title])))
      (is (not= nil (get-in response [:body :status])))
      (is (not= nil (get-in response [:body :detail]))))))

(deftest validate-status-code-response
  (testing "Validate status code for bad request"
    (let [bad-request (problem-detail/bad-request "Bad Request Unit Test")]
      (is (= 400 (get bad-request :status)))))

  (testing "Validate status code for not found"
    (let [not-found (problem-detail/not-found "Not Found Unit Test")]
      (is (= 404 (get not-found :status)))))

  (testing "Validate status code for internal server error"
    (let [internal-server-error (problem-detail/internal-server-error)]
      (is (= 500 (get internal-server-error :status))))))