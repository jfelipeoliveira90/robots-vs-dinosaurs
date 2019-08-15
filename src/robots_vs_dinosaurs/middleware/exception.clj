(ns robots-vs-dinosaurs.middleware.exception
  (:require [robots-vs-dinosaurs.http.problem-detail :as problem-detail]
            [clojure.tools.logging :as log]))

(defn wrap-exception-handling [handler]
  (fn [request]
    (try
      (handler request)
      (catch IllegalArgumentException e
        (log/info (get (Throwable->map e) :cause))
        (problem-detail/bad-request (get (Throwable->map e) :cause)))
      (catch Exception e
        (log/error e)
        (problem-detail/internal-server-error)))))