(ns api.core-test
  (:require [cheshire.core :as cheshire]
            [clojure.test :refer :all]
            [api.handler :refer :all]
            [ring.mock.request :as mock]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest a-test

  (testing "Test if docs exists"
    (let [response (app (-> (mock/request :get  "/index.html")))]
      (is (= (:status response) 200)))))
