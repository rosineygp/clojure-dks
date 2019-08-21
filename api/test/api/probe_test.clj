(ns api.probe-test
  (:require [cheshire.core :as cheshire]
            [clojure.test :refer :all]
            [api.handler :refer :all]
            [ring.mock.request :as mock]))

(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest health

  (testing "Liveness probe"
    (let [response (app (-> (mock/request :get  "/health/liveness")))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= (:status body) "ok"))))

  (testing "Readiness probe"
    (let [response (app (-> (mock/request :get  "/health/readiness")))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (= (:status body) "ok")))))