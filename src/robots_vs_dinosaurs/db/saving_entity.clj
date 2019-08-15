(ns robots-vs-dinosaurs.db.saving-entity
  (:require [korma.db :refer :all]
            [korma.core :refer :all]))

(defentity entity)

(defn count-by-position [filter-entity]
  (:cnt
    (first
      (select
        entity
        (aggregate (count :*) :cnt)
        (where
          {:pos-x   (get filter-entity :pos-x)
           :pos-y   (get filter-entity :pos-y)
           :grid-id (get filter-entity :grid-id)
           :id      [not= (get filter-entity :id 0)]})))))