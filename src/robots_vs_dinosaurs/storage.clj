(ns robots-vs-dinosaurs.storage
  (:require [camel-snake-kebab.core :as csk])
  (:use korma.db))

(defdb db (postgres
            {:classname   "org.postgresql.Driver"
             :subprotocol "postgresql"
             :subname     "//localhost:5432/robots_vs_dinosaurs"
             :user        "postgres"
             :password    "postgres"
             :naming      {:keys   csk/->kebab-case
                           :fields csk/->snake_case}}))