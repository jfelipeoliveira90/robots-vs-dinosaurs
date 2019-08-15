(ns robots-vs-dinosaurs.unit.logic-test
  (:require [clojure.test :refer :all]
            [robots-vs-dinosaurs.logic :as logic]))

(deftest validate-turn-left-robot
  (testing "Validate diretion N -> W"
    (let [robot (logic/turn-left-robot {:direction "N"})]
      (is (= "W" (get robot :direction)))))

  (testing "Validate diretion S -> E"
    (let [robot (logic/turn-left-robot {:direction "S"})]
      (is (= "E" (get robot :direction)))))

  (testing "Validate diretion E -> N"
    (let [robot (logic/turn-left-robot {:direction "E"})]
      (is (= "N" (get robot :direction)))))

  (testing "Validate diretion W -> S"
    (let [robot (logic/turn-left-robot {:direction "W"})]
      (is (= "S" (get robot :direction))))))

(deftest validate-turn-right-robot
  (testing "Validate diretion N -> E"
    (let [robot (logic/turn-right-robot {:direction "N"})]
      (is (= "E" (get robot :direction)))))

  (testing "Validate diretion S -> W"
    (let [robot (logic/turn-right-robot {:direction "S"})]
      (is (= "W" (get robot :direction)))))

  (testing "Validate diretion E -> S"
    (let [robot (logic/turn-right-robot {:direction "E"})]
      (is (= "S" (get robot :direction)))))

  (testing "Validate diretion W -> N"
    (let [robot (logic/turn-right-robot {:direction "W"})]
      (is (= "N" (get robot :direction))))))

(deftest move-forward-robot
  (testing "Validate move forward for diretion N"
    (let
      [robot
       (logic/move-forward-robot
         {:direction "N"
          :pos-y     0})]
      (is (= 1 (get robot :pos-y)))))

  (testing "Validate move forward for diretion S"
    (let
      [robot
       (logic/move-forward-robot
         {:direction "S"
          :pos-y     1})]
      (is (= 0 (get robot :pos-y)))))

  (testing "Validate move forward for diretion E"
    (let
      [robot
       (logic/move-forward-robot
         {:direction "E"
          :pos-x     0})]
      (is (= 1 (get robot :pos-x)))))

  (testing "Validate move forward for diretion W"
    (let
      [robot
       (logic/move-forward-robot
         {:direction "W"
          :pos-x     1})]
      (is (= 0 (get robot :pos-x))))))

(deftest validate-move-backward-robot
  (testing "Validate move backward for diretion N"
    (let
      [robot
       (logic/move-backward-robot
         {:direction "N"
          :pos-y     1})]
      (is (= 0 (get robot :pos-y)))))

  (testing "Validate move backward for diretion S"
    (let
      [robot
       (logic/move-backward-robot
         {:direction "S"
          :pos-y     0})]
      (is (= 1 (get robot :pos-y)))))

  (testing "Validate move backward for diretion E"
    (let
      [robot
       (logic/move-backward-robot
         {:direction "E"
          :pos-x     1})]
      (is (= 0 (get robot :pos-x)))))

  (testing "Validate move backward for diretion W"
    (let
      [robot
       (logic/move-backward-robot
         {:direction "W"
          :pos-x     0})]
      (is (= 1 (get robot :pos-x))))))

(deftest validate-positions-around-robot
  (testing "Validate positions around robot"
    (let
      [positions
       (logic/get-positions-around-robot
         {:pos-x 0
          :pos-y 0})]
      (is (= {:pos-x -1 :pos-y 0} (nth positions 0)))
      (is (= {:pos-x 0 :pos-y -1} (nth positions 1)))
      (is (= {:pos-x 1 :pos-y 0} (nth positions 2)))
      (is (= {:pos-x 0 :pos-y 1} (nth positions 3))))))