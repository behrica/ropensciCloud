(defproject ropen-sci-cloud "0.1.0-SNAPSHOT"
            :description "FIXME: write description"
            :url "http://example.com/FIXME"
            :dependencies [[org.clojure/clojure "1.6.0"]
                           [compojure "1.1.9"]
                           [digitalocean "1.2" :exclusions [midje]]
                           [ring/ring-jetty-adapter "1.3.1"]
                           [environ "0.5.0"]
                           [hiccup "1.0.4"]
                           [selmer "0.7.1"]
                           ;     [org.apache.jclouds/jclouds-all "1.8.0"]
                           ;     [org.apache.jclouds.driver/jclouds-sshj "1.8.0"]
                           ]
            :plugins [
                       [lein-ring "0.8.11"]
                       [environ/environ.lein "0.2.1"]
                       ]
            :hooks [environ.leiningen.hooks]
            :uberjar-name "ropen-sci-cloud-standalone.jar"
            :ring {:handler ropen-sci-cloud.web/app}
            :min-lein-version "2.0.0"
            :profiles
            {
              :dev {:dependencies [[ring-mock "0.1.5"]
                                   [midje "1.6.3"]
                                   ]}
              :production {:env {:production true}}
              })
