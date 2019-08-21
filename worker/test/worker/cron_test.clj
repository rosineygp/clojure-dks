(ns worker.cron-test
  (:require [clojure.test :refer :all]
            [worker.service.cron :refer :all :as c]
            [clj-time.core :as t]
            [clj-time.local :as l]))

(deftest cron-run-it-test
  (testing "past"
    (is (= (c/run-it? 2019 1 1 1 1) true))
    (is (= (c/run-it? 1912 7 23 9 45) true)))

  (testing "now"
    (let [now (l/local-now)]

      (is (= (c/run-it? (t/year now)
                        (t/month now)
                        (t/day now)
                        (t/hour now)
                        (t/minute now)) true))))

  (testing "future"
    (is (= (c/run-it? 2070 1 1 1 1) false))
    (is (= (c/run-it? 3001 7 23 9 45) false))))

(deftest cron-filter-run-test
  (testing "map"
    (is (= (c/filter-run {:year 2019
                          :month 1
                          :day 1
                          :hour 1
                          :minute 1}) true))))

(deftest cron-local-now-test
  (testing "return date as string"
    (println (c/local-now))
    (is (= (string? (c/local-now)) true))))
