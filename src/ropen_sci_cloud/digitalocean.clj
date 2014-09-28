(ns ropen-sci-cloud.digitalocean
  (:require [digitalocean.v2.core :as do]
            [clojure.java.io :as io]
            ))

(defn create-docker-droplet [token]
  (let [user-data (slurp (io/resource "user_data.txt"))]
    (println "token: " token)
    (println "user data: " user-data)
    (do/create-droplet token
                       nil {:name "core10":region "ams3" :size "512mb" :image 6373176 :ssh_keys [42550] :user_data user-data})))

