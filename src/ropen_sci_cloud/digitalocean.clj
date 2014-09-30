(ns ropen-sci-cloud.digitalocean
  (:require [digitalocean.v2.core :as do]
            [clojure.java.io :as io]
            [selmer.parser :refer[render-file]]
            ))

(defn make-user-data [image-name]
  (render-file "user_data.txt" {:imagename image-name}))

(defn find-key-from-name [token ssh-name]
  (:id (first (filter #(= (:name % ) ssh-name)  (:ssh_keys (do/ssh-keys token) )))))

(defn create-docker-droplet [token imagename ssh-name]
  (let [user-data (make-user-data imagename)
        ssh-id (find-key-from-name token ssh-name)
        ]
    (println "token: " token)
    (println "user data: " user-data)
    (println "imagename: " imagename)

    (do/create-droplet token
                       nil {:name "coreos-ropensci" :region "ams3" :size "512mb" :image 6373176 :ssh_keys [ssh-id] :user_data user-data})))

