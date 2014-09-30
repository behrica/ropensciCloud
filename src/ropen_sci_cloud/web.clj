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
               [:p "Digitalocean token: " (f/text-field :token)]
               [:p "Digitalocean ssh key: " (f/text-field :ssh_name)]
               [:p "Docker image name: " (f/text-field :imagename)]

               [:p (f/submit-button "create droplet")]
               [:p]
               )
    ])
  )

(defn create-droplet [token imagename ssh-name]
  {:status 200
   :headers {"Content-Type" "text/plain"}
    :body  (pr-str (create-docker-droplet token imagename ssh-name)) })


(defroutes
           route
           (GET "/" []
                (page))
           (POST "/create" [token imagename ssh_name]
                (create-droplet token imagename ssh_name))
           (ANY "*" []
                (route/not-found (slurp (io/resource "404.html")))))


(def app (site #'route))
(defn -main [& [port]]
  (let [port (Integer. (or port (env :port) 5000))]
    (jetty/run-jetty app {:port port :join? false})))

;; For interactive development:
;; (.stop server)
;; (def server (-main))

