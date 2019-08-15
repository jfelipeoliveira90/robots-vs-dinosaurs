(ns robots-vs-dinosaurs.supervisor
  (:require [dire.core :refer :all]
            [robots-vs-dinosaurs.controller :as controller]))

(with-precondition!
  #'controller/create-robot
  :same-position-not-allowed
  (fn [robot & args]
    (controller/entity-position-available? robot)))

(with-handler!
  #'controller/create-robot
  {:precondition :same-position-not-allowed}
  (fn [e & args] (throw (IllegalArgumentException. "An entity already exists in this position"))))

(with-precondition!
  #'controller/create-dinosaur
  :same-position-not-allowed
  (fn [dinosaur & args]
    (controller/entity-position-available? dinosaur)))

(with-handler!
  #'controller/create-dinosaur
  {:precondition :same-position-not-allowed}
  (fn [e & args] (throw (IllegalArgumentException. "An entity already exists in this position"))))

(with-postcondition!
  #'controller/send-instruction-robot
  :same-position-not-allowed
  (fn [robot & args]
    (controller/entity-position-available? robot)))

(with-handler!
  #'controller/send-instruction-robot
  {:postcondition :same-position-not-allowed}
  (fn [e & args] (throw (IllegalArgumentException. "An entity already exists in this position"))))

(with-precondition!
  #'controller/create-robot
  :entity-outside-grid-not-allowed
  (fn [robot & args]
    (controller/entity-within-the-grid? robot)))

(with-handler!
  #'controller/create-robot
  {:precondition :entity-outside-grid-not-allowed}
  (fn [e & args] (throw (IllegalArgumentException. "Position of the robot outside the grid"))))

(with-precondition!
  #'controller/create-dinosaur
  :entity-outside-grid-not-allowed
  (fn [dinosaur & args]
    (controller/entity-within-the-grid? dinosaur)))

(with-handler!
  #'controller/create-dinosaur
  {:precondition :entity-outside-grid-not-allowed}
  (fn [e & args] (throw (IllegalArgumentException. "Position of the dinosaur outside the grid"))))

(with-postcondition!
  #'controller/send-instruction-robot
  :entity-outside-grid-not-allowed
  (fn [robot & args]
    (controller/entity-within-the-grid? robot)))

(with-handler!
  #'controller/send-instruction-robot
  {:postcondition :entity-outside-grid-not-allowed}
  (fn [e & args] (throw (IllegalArgumentException. "Position of the robot outside the grid"))))