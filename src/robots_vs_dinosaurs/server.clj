(ns robots-vs-dinosaurs.server
  (:require [robots-vs-dinosaurs.storage]
            [robots-vs-dinosaurs.supervisor]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]
            [robots-vs-dinosaurs.middleware.exception :refer [wrap-exception-handling]]
            [robots-vs-dinosaurs.service :as service]
            [ring.adapter.jetty :refer [run-jetty]])
  (:gen-class))

(def app
  (-> service/all-route
      wrap-exception-handling
      wrap-json-response
      wrap-json-body))

(defn -main [& args]
  (run-jetty app {:port 8080}))