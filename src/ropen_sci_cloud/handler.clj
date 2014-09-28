(ns ropen-sci-cloud.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defroutes app-routes
  (GET "/" [] "Hello World")
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site app-routes))


;;"11d0dec395268780e2242dad5c6912f7b3c8eba7a56d6b26aa2db05bb185faf8"