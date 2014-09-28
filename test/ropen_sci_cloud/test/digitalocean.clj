(ns ropen-sci-cloud.test.digitalocean
  (:require
            [ropen-sci-cloud.digitalocean :refer [make-user-data]]
            [midje.sweet :refer [fact provided => anything unfinished]]
            ))



(fact
   (make-user-data "my/docker/image") => "#cloud-config\n\ncoreos:\n    units:\n      - name: docker-rs.service\n        command: start\n        content: |\n          [Unit]\n          Description=RStudio service container\n          Author=Me\n          After=docker.service\n\n          [Service]\n          Restart=always\n          ExecStart=/usr/bin/docker run -p 49000:8787 --name \"rstudio\" my/docker/image\n          ExecStop=/usr/bin/docker stop rstudio")
