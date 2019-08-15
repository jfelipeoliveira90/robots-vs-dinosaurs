(ns robots-vs-dinosaurs.unit.controller-test
  (:require [clojure.test :refer :all]
            [robots-vs-dinosaurs.db.saving-entity :as db.saving-entity]
            [robots-vs-dinosaurs.db.saving-grid :as db.saving-grid]
            [robots-vs-dinosaurs.controller :as controller]
            [robots-vs-dinosaurs.logic :as logic]))

(deftest validate-send-instruction-robot
  (testing "Validate send instruction turn left"
    (with-redefs [logic/turn-left-robot (fn [robot] {})]
      (is (not= (#'controller/send-instruction-robot {} "turn-left")
                nil))))

  (testing "Validate send instruction turn right"
    (with-redefs [logic/turn-right-robot (fn [robot] {})]
      (is (not= (#'controller/send-instruction-robot {} "turn-right")
                nil))))

  (testing "Validate send instruction move forward"
    (with-redefs [logic/move-forward-robot (fn [robot] {})]
      (is (not= (#'controller/send-instruction-robot {} "move-forward")
                nil))))

  (testing "Validate send instruction move backward"
    (with-redefs [logic/move-backward-robot (fn [robot] {})]
      (is (not= (#'controller/send-instruction-robot {} "move-backward")
                nil))))

  (testing "Validate send instruction attack"
    (with-redefs [logic/get-positions-around-robot (fn [robot] {})]
      (is (not= (#'controller/send-instruction-robot {} "attack")
                nil)))))

(deftest validate-entity-position-available?
  (testing "Validate if position is available for entity"
    (with-redefs [db.saving-entity/count-by-position (fn [entity] 0)]
      (is (true? (controller/entity-position-available? {})))))

  (testing "Validate if position is not available for entity"
    (with-redefs [db.saving-entity/count-by-position (fn [entity] 1)]
      (is (false? (controller/entity-position-available? {}))))))

(deftest validate-entity-within-the-grid?
  (testing "Validate entity is within the grid"
    (with-redefs
      [db.saving-grid/find-grid
       (fn [grid-id] {:size-x 50
                      :size-y 50})]
      (is (true? (controller/entity-within-the-grid?
                   {:pos-x 0
                    :pos-y 0})))))

  (testing "Validate entity is out of grid (pos-x > size-x)"
    (with-redefs
      [db.saving-grid/find-grid
       (fn [grid-id] {:size-x 50
                      :size-y 50})]
      (is (false? (controller/entity-within-the-grid?
                    {:pos-x 51
                     :pos-y 0})))))

  (testing "Validate entity is out of grid (pos-y > size-y)"
    (with-redefs
      [db.saving-grid/find-grid
       (fn [grid-id] {:size-x 50
                      :size-y 50})]
      (is (false? (controller/entity-within-the-grid?
                    {:pos-x 0
                     :pos-y 51})))))

  (testing "Validate entity is out of grid (pos-x < 0)"
    (with-redefs
      [db.saving-grid/find-grid
       (fn [grid-id] {:size-x 50
                      :size-y 50})]
      (is (false? (controller/entity-within-the-grid?
                    {:pos-x -1
                     :pos-y 0})))))

  (testing "Validate entity is out of grid (pos-y < 0)"
    (with-redefs
      [db.saving-grid/find-grid
       (fn [grid-id] {:size-x 50
                      :size-y 50})]
      (is (false? (controller/entity-within-the-grid?
                    {:pos-x 0
                     :pos-y -1}))))))