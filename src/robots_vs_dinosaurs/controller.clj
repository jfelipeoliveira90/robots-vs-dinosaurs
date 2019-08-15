(ns robots-vs-dinosaurs.controller
  (:require [robots-vs-dinosaurs.db.saving-grid :as db.saving-grid]
            [robots-vs-dinosaurs.db.saving-robot :as db.saving-robot]
            [robots-vs-dinosaurs.db.saving-dinosaur :as db.saving-dinosaur]
            [robots-vs-dinosaurs.db.saving-entity :as db.saving-entity]
            [robots-vs-dinosaurs.logic :as logic]))

(defn create-grid-default-size []
  (db.saving-grid/save-grid 50 50))

(defn display-status [grid-id]
  (db.saving-grid/find-grid grid-id))

(defn create-robot [robot]
  (db.saving-robot/save-robot robot))

(defn create-dinosaur [dinosaur]
  (db.saving-dinosaur/save-dinosaur dinosaur))

(defn- attack-dinosaur [robot]
  (let [positions (logic/get-positions-around-robot robot)]
    (doseq [position positions]
      (db.saving-dinosaur/delete-dinosaur-by-position
        (get robot :grid-id)
        (get position :pos-x)
        (get position :pos-y)))
    robot))

(defn- send-instruction-robot
  [robot action]
  (cond
    (= action "turn-left") (logic/turn-left-robot robot)
    (= action "turn-right") (logic/turn-right-robot robot)
    (= action "move-forward") (logic/move-forward-robot robot)
    (= action "move-backward") (logic/move-backward-robot robot)
    (= action "attack") (attack-dinosaur robot)))

(defn create-instruction
  [robot-id action]
  (-> (db.saving-robot/find-robot robot-id)
      (send-instruction-robot action)
      db.saving-robot/update-robot))

(defn entity-position-available? [entity]
  (let [count (db.saving-entity/count-by-position entity)]
    (true? (= count 0))))

(defn entity-within-the-grid? [entity]
  (let [grid (db.saving-grid/find-grid (get entity :grid-id))
        size-x (get grid :size-x)
        size-y (get grid :size-y)
        pos-x (get entity :pos-x)
        pos-y (get entity :pos-y)]
    (true?
      (and
        (>= pos-x 0)
        (<= pos-x size-x)
        (>= pos-y 0)
        (<= pos-y size-y)))))
