(ns robots-vs-dinosaurs.db.saving-dinosaur
  (:require [korma.db :refer :all]
            [korma.core :refer :all]))

(defentity dinosaur)

(defn save-dinosaur [new-dinosaur]
  (insert
    dinosaur
    (values
      {:grid-id (get new-dinosaur :grid-id)
       :pos-x   (get new-dinosaur :pos-x)
       :pos-y   (get new-dinosaur :pos-y)})))

(defn delete-dinosaur-by-position
  [grid-id pos-x pos-y]
  (delete
    dinosaur
    (where
      {:grid-id grid-id
       :pos-x   pos-x
       :pos-y   pos-y})))