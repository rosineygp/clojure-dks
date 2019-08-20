(ns worker.cni-test
  (:require [clojure.test :refer :all]
            [worker.service.cni :refer :all :as cni]))

(deftest docker-pull-test
  (testing "Dowload images"
    (is (= (:exit (cni/docker-pull "alpine")) 0))
    (is (= (:exit (cni/docker-pull "ubuntu")) 0)))

  (testing "Unexisted images"
    (is (= (:exit (cni/docker-pull "alpiness")) 1))
    (is (= (:exit (cni/docker-pull "ubuntuei")) 1)))

  (testing "Wrong image name"
    (is (= (:exit (cni/docker-pull "alpinO")) 1))
    (is (= (:exit (cni/docker-pull "uRbuntu")) 1))))

(deftest docker-run-test
  (testing "Running commands"
    (is (= (:exit (cni/docker-run "alpine" ["ls" "-la" "/"])) 0))
    (is (= (:exit (cni/docker-run "alpine" ["ps" "xua"])) 0)))

  (testing "Running commands for fail (! ee ahead)"
    (is (= (:exit (cni/docker-run "alpine" ["01101110" "01110101"])) 127))
    (is (= (:exit (cni/docker-run "alpine" ["bd5af1f610a12434c9128e4a399cef8a"])) 127))))

(deftest docker-logs-test
  (testing "Failed reference"
    (is (= (:exit (cni/docker-logs "incredible_jack")) 1))
    (is (= (:exit (cni/docker-logs "awesome_sre")) 1)))

  (testing "True reference"
    (is (= (-> (cni/docker-run "alpine" ["ls" "-la" "/"])
               :out
               (clojure.string/trim)
               (cni/docker-logs)
               :exit) 0))))

