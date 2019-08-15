(ns robots-vs-dinosaurs.db.saving-robot
  (:require [korma.db :refer :all]
            [korma.core :refer :all]))

(defentity robot)

(defn find-robot [robot-id]
  (first
    (select
      robot
      (where {:id robot-id}))))

(defn save-robot [new-robot]
  (insert
    robot
    (values
      {:grid-id   (get new-robot :grid-id)
       :pos-x     (get new-robot :pos-x)
       :pos-y     (get new-robot :pos-y)
       :direction (get new-robot :direction)})))

(defn update-robot [old-robot]
  (first
    (exec-raw
      ["UPDATE
          robot
        SET
          pos_x = ?,
          pos_y = ?,
          direction = ?
        WHERE
          id = ?
        RETURNING *"
       [(get old-robot :pos-x)
        (get old-robot :pos-y)
        (get old-robot :direction)
        (get old-robot :id)]]
      :results)))