(ns robots-vs-dinosaurs.logic)

(defn turn-left-robot
  "Changes the direction of the robot to the left depending
  on the direction it is in the cardinal point"
  [robot]
  (cond
    (= (:direction robot) "N") (assoc-in robot [:direction] "W")
    (= (:direction robot) "S") (assoc-in robot [:direction] "E")
    (= (:direction robot) "E") (assoc-in robot [:direction] "N")
    (= (:direction robot) "W") (assoc-in robot [:direction] "S")))

(defn turn-right-robot
  "Changes the direction of the robot to the right depending
  on the direction it is in the cardinal point"
  [robot]
  (cond
    (= (:direction robot) "N") (assoc-in robot [:direction] "E")
    (= (:direction robot) "S") (assoc-in robot [:direction] "W")
    (= (:direction robot) "E") (assoc-in robot [:direction] "S")
    (= (:direction robot) "W") (assoc-in robot [:direction] "N")))

(defn move-forward-robot
  "Moves the robot forward, depending on its direction at the cardinal point"
  [robot]
  (cond
    (= (:direction robot) "N") (update-in robot [:pos-y] inc)
    (= (:direction robot) "S") (update-in robot [:pos-y] dec)
    (= (:direction robot) "E") (update-in robot [:pos-x] inc)
    (= (:direction robot) "W") (update-in robot [:pos-x] dec)))

(defn move-backward-robot
  "Move the robot backwards, depending on its direction at the cardinal point"
  [robot]
  (cond
    (= (:direction robot) "N") (update-in robot [:pos-y] dec)
    (= (:direction robot) "S") (update-in robot [:pos-y] inc)
    (= (:direction robot) "E") (update-in robot [:pos-x] dec)
    (= (:direction robot) "W") (update-in robot [:pos-x] inc)))

(defn get-positions-around-robot
  "Returns a list containing the positions around the robot"
  [robot]
  (list {:pos-x (dec (get robot :pos-x))
         :pos-y (get robot :pos-y)}
        {:pos-x (get robot :pos-x)
         :pos-y (dec (get robot :pos-y))}
        {:pos-x (inc (get robot :pos-x))
         :pos-y (get robot :pos-y)}
        {:pos-x (get robot :pos-x)
         :pos-y (inc (get robot :pos-y))}))