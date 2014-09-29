(ns ropen-sci-cloud.web
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE ANY]]
            [compojure.handler :refer [site]]
            [compojure.route :as route]
            [clojure.java.io :as io]
            [ring.adapter.jetty :as jetty]
            [environ.core :refer [env]]
            [ropen-sci-cloud.digitalocean :refer [create-docker-droplet]]
            [ropen-sci-cloud.version :refer [info]]
            [hiccup.form :as f]
            [hiccup.page :as h :refer [html5]]
            [ring.middleware.params :refer [wrap-params]]
  ))

(defn page []
  (h/html5
    [:body
    (f/form-to [:post "/create"]
               [:p "Digitalocean token: " (f/text-field :token)]
               [:p "Digitalocean ssh_id: " (f/text-field :ssh_id)]
               [:p "Docker image name: " (f/text-field :imagename)]

               [:p (f/submit-button "create droplet")]
               [:p]
               [:p "Version: " (pr-str info)]
               )
    ])
  )

(defn create-droplet [token imagename ssh_id]
  {:status 200
   :headers {"Content-Type" "text/plain"}
    :body  (pr-str (create-docker-droplet token imagename ssh_id)) })


(defroutes
           route
           (GET "/" []
                (page))
           (POST "/create" [token imagename ssh_id]
                (create-droplet token imagename ssh_id))
           (ANY "*" []
                (route/not-found (slurp (io/resource "404.html")))))


(def app (site #'route))
(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty app {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

