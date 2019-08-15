(ns robots-vs-dinosaurs.db.saving-grid
  (:require [korma.db :refer :all]
            [korma.core :refer :all]))

(defentity robot)
(defentity dinosaur)

(defentity grid
           (has-many robot {:fk :grid-id})
           (has-many dinosaur {:fk :grid-id}))

(defn find-grid [grid-id]
  (first
    (select
      grid
      (where {:id grid-id})
      (with robot)
      (with dinosaur))))

(defn save-grid [size-x size-y]
  (insert
    grid
    (values
      {:size-x size-x
       :size-y size-y})))