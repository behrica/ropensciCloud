(ns ropen-sci-cloud.test.web
  (:require [clojure.test :refer :all]
            [ropen-sci-cloud.web :refer [app]]
            [ropen-sci-cloud.digitalocean :refer [create-docker-droplet]]

            [ring.mock.request :as mock]
            [midje.sweet :refer [fact provided => anything]]
            ))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is  (not (nil?  (re-find #".*submit.*" (:body response) ))))))
  
  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))


(fact
  (app (mock/request :post "/create"  {:token "1234" :imagename "cboettig/ropensci" :ssh_id "9999"})) => {:body "{:status \"OK\"}", :headers {"Content-Type" "text/plain"}, :status 200}
  (provided (create-docker-droplet "1234" "cboettig/ropensci" "9999")  => {:status "OK"}))
