(ns ropen-sci-cloud.test.digitalocean
  (:require
    [ropen-sci-cloud.digitalocean :refer [make-user-data create-docker-droplet]]
    [midje.sweet :refer [fact provided => anything unfinished]]
    [digitalocean.v2.core :as do]
    ))

(def expected-user-data "#cloud-config\n\ncoreos:\n    units:\n      - name: docker-rs.service\n        command: start\n        content: |\n          [Unit]\n          Description=RStudio service container\n          Author=Me\n          After=docker.service\n\n          [Service]\n          Restart=always\n          ExecStart=/usr/bin/docker run -p 49000:8787 --name \"rstudio\" my/docker/image\n          ExecStop=/usr/bin/docker stop rstudio")

(fact
  (make-user-data "my/docker/image") => expected-user-data)

(fact
  (create-docker-droplet "token" "my/docker/image" "ssh-key-name") => nil
  (provided
    (do/ssh-keys "token") => {:ssh_keys [{:id 9999, :name "ssh-key-name"}
                                         {:id 12345, :name "b@gandalf"}
                                         ], :meta {:total 2}}
    (do/create-droplet "token" nil {:name "coreos-ropensci" :region "ams3" :size "512mb" :image 6373176 :ssh_keys [9999] :user_data expected-user-data}) => nil
    ))
