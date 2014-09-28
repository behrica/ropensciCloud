(ns ropen-sci-cloud.digitalocean
  (:require [digitalocean.v2.core :as do]
            [clojure.java.io :as io]
            [selmer.parser :refer[render-file]]
            ))

(defn make-user-data [imagename]
  (render-file "user_data.txt" {:imagename imagename}))

(defn create-docker-droplet [token imagename]
  (let [user-data (make-user-data imagename)]
    (println "token: " token)
    (println "user data: " user-data)
    (println "imagename: " imagename)

    (do/create-droplet token
                       nil {:name "coreos-ropensci":region "ams3" :size "512mb" :image 6373176 :ssh_keys [42550] :user_data user-data})))

