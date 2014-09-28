(ns ropen-sci-cloud.test.web
  (:require [clojure.test :refer :all]
            [ropen-sci-cloud.web :refer :all]
            [ring.mock.request :as mock]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Hello from ropen-sci-cloud"))))
  
  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))
