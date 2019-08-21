(ns worker.orchestrator-test
  (:require [clojure.test :refer :all]
            [worker.controller.orchestrator :refer :all :as o]))

(deftest orchestrator-schedule-test
  (testing "get list"
    (is (= (seq? (o/schedule))))))

(deftest orchestrator-run-job-test
  (testing "commands test"
    (is (= (:exit (o/run-job {:docker-image "ubuntu"
                              :_id "simple-ubuntu"
                              :command "ls -la /"})) 0))

    (is (= (:exit (o/run-job {:docker-image "alpine"
                              :_id "simple-alpine"
                              :command "ls -la /"})) 0))

    (is (= (:exit (o/run-job {:docker-image "alpino"
                              :_id "failed-image"
                              :command "ls -la /"})) 125))

    (is (= (:exit (o/run-job {:docker-image "alpine"
                              :_id "failed-command"
                              :command "01b2cf8ec9687a57b03c9481580f69a5"})) 127))

    (is (= (:exit (o/run-job {:docker-image "alpine"
                              :_id "complex-command"
                              :command "apk update && apk add curl"})) 0))

    (is (= (:exit (o/run-job {:docker-image "alpine"
                              :_id "script"
                              :command "if [ true ]
                                        then
                                            echo ok
                                        fi"})) 0))))