(ns ropen-sci-cloud.digitalocean
  (:require [digitalocean.v2.core :as do]
            [clojure.java.io :as io]
            ))

(defn create-docker-droplet [token]
  (let [user-data (slurp (io/file (io/resource "user_data.txt")))]
    (do/create-droplet token
                       nil {:name "core10":region "ams3" :size "512mb" :image 6373176 :ssh_keys [42550] :user_data user-data})))

;;"11d0dec395268780e2242dad5c6912f7b3c8eba7a56d6b26aa2db05bb185faf8"