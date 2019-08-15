(ns robots-vs-dinosaurs.service
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [robots-vs-dinosaurs.controller :as controller]
            [ring.util.response :as ring-resp]
            [robots-vs-dinosaurs.http.problem-detail :as problem-detail]))

(defn create-grid-default-size []
  (ring-resp/created nil (controller/create-grid-default-size)))

(defn create-robot [req]
  (let [grid-id (get-in req [:body "grid-id"])
        pos-x (get-in req [:body "pos-x"])
        pos-y (get-in req [:body "pos-y"])
        direction (get-in req [:body "direction"])]
    (ring-resp/created
      nil
      (controller/create-robot
        {:grid-id   grid-id
         :pos-x     pos-x
         :pos-y     pos-y
         :direction direction}))))

(defn create-dinosaur [req]
  (let [grid-id (get-in req [:body "grid-id"])
        pos-x (get-in req [:body "pos-x"])
        pos-y (get-in req [:body "pos-y"])]
    (ring-resp/created
      nil
      (controller/create-dinosaur
        {:grid-id grid-id
         :pos-x   pos-x
         :pos-y   pos-y}))))

(defn create-instruction [req]
  (let [robot-id (read-string (get-in req [:params :id]))
        action (get-in req [:body "action"])]
    (controller/create-instruction robot-id action)
    {:status 204}))

(defn display-status [req]
  (let [grid-id (read-string (get-in req [:params :id]))
        grid (controller/display-status grid-id)]
    (if (not= grid nil)
      (ring-resp/response grid)
      (problem-detail/not-found
        (str "Grid not found with id " grid-id)))))

(defroutes all-route
           (POST "/grids" [] (create-grid-default-size))
           (GET "/grids/:id" req (display-status req))
           (POST "/robots" req (create-robot req))
           (POST "/dinosaurs" req (create-dinosaur req))
           (PUT "/robots/:id/instructions" req (create-instruction req))
           (route/not-found (problem-detail/not-found "Resource not found")))
