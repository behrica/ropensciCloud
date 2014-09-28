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
               "Image name: " (f/text-field :imagename)
               (f/submit-button "create")
               )
    ])
  )

(defn create-droplet [token imagename]
  {:status 200
   :headers {"Content-Type" "text/plain"}
    :body  (pr-str (create-docker-droplet token imagename)) })


(defroutes
           route
           (GET "/" []
                (page))
           (POST "/create" [token imagename]
                (create-droplet token imagename))
           (ANY "*" []
                (route/not-found (slurp (io/resource "404.html")))))


(def app (site #'route))
(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty app {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

