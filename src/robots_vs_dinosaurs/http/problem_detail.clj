(ns robots-vs-dinosaurs.http.problem-detail
  "Small implementation of RFC 7807 (Problem Details for HTTP APIs)
  https://tools.ietf.org/html/rfc7807")

(defn response
  [status title detail]
  {:status  status
   :headers {"Content-Type" "application/problem+json"}
   :body    {:title  title
             :status status
             :detail detail}})

(defn bad-request [detail]
  (response 400 "Bad Request" detail))

(defn not-found [detail]
  (response 404 "Not Found" detail))

(defn internal-server-error []
  (response 500 "Internal Server Error" "An internal error occurred, please try again later"))