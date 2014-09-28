(defproject ropen-sci-cloud "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [compojure "1.1.8"]
                 [digitalocean "1.2"]

            ;     [org.apache.jclouds/jclouds-all "1.8.0"]
            ;     [org.apache.jclouds.driver/jclouds-sshj "1.8.0"]
                 ]
  :plugins [
;[lein-ring "0.8.11"]
[environ/environ.lein "0.2.1"]
]
 ; :ring {:handler ropen-sci-cloud.handler/app}
  :min-lein-version "2.0.0"
  :profiles
  {
;:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
   ;                     [ring-mock "0.1.5"]]}
:production {:env {:production true}}
                        })
