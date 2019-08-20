(ns worker.core-test
  (:require [clojure.test :refer :all]
            [worker.core :refer :all]
            [worker.service.cni :refer :all :as cni]))

; (deftest a-test
;   (testing "FIXME, I fail."
;     (is (= 1 1))))

; (deftest cni
;   (testing "docker run"
;     (is (= (re-matches #"[A-Za-z0-9]{12}" (cni/docker-run "alpine" "ls -la /"))))))
