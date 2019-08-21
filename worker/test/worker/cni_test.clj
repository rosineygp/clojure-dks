(ns worker.cni-test
  (:require [clojure.test :refer :all]
            [worker.service.sofs :refer :all :as s]
            [worker.service.cni :refer :all :as c]))

(deftest docker-script-test
  (testing "run simple script"
    (let [id "test_id_01"]
      (is (= (:exit (c/docker-script id "alpine" (s/make-script id "ls -la /"))) 0))
      (is (= (:exit (c/docker-script id "ubuntu" (s/make-script id "ls -la /"))) 0))))

  (testing "run complex script"
    (let [id "test_id_01"]
      (is (= (:exit (c/docker-script id "alpine" (s/make-script id "apk update && apk add curl"))) 0))
      (is (= (:exit (c/docker-script id "ubuntu" (s/make-script id "apt-get update && apt-get install curl -y"))) 0)))))
