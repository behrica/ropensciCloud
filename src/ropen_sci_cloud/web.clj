(ns ropen-sci-cloud.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ropen-sci-cloud.digitalocean :refer [create-docker-droplet]]
            [hiccup.form :as f]
            [hiccup.page :as h :refer [html5]]
            [ring.middleware.params :refer [wrap-params]]
  ))

(defn page []
  (h/html5
    [:body
    (f/form-to [:post "/create"]
               "Token: " (f/text-field :token)
               (f/submit-button "create")
               )
    ])
  )

(defn create-droplet [token]
  {:status 200
   :headers {"Content-Type" "text/plain"}
    :body  (pr-str (create-docker-droplet token)) })


(defroutes
           routes
           (GET "/" []
                (page))
           (POST "/create" [token]
                (create-droplet token))
           (ANY "*" []
                (route/not-found (slurp (io/resource "404.html")))))



(def app (wrap-params routes))

(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty (site #'app) {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

;;"11d0dec395268780e2242dad5c6912f7b3c8eba7a56d6b26aa2db05bb185faf8"