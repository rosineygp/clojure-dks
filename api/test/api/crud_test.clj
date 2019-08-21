(ns api.crud-test
  (:require [clojure.test :refer :all]
            [api.model.all :as m]
            [api.controller.crud :as c]
            [schema.core :as s]))

(deftest create-test
  (testing "create a new schedule"
    (let [data {:docker-image "alpine"
                :command "ps -ef"
                :cron {:year 2019
                       :month 1
                       :day 1
                       :hour 1
                       :minute 1}}]
      (is (= (s/validate m/Schedule (c/create data)))))))

(deftest restore-test
  (testing "restore all itens"
    (is (= (seq? (c/restore)) true))))

(deftest restore-by-id-test
  (testing "restore one item"
    (let [data (c/create {:docker-image "alpine"
                          :command "ps -ef"
                          :cron {:year 2019
                                 :month 1
                                 :day 1
                                 :hour 1
                                 :minute 1}})]
      (is (= (c/restore-by-id (:_id data)) data)))))

(deftest update-test
  (testing "update schedule"
    (let [data (c/create {:docker-image "alpine"
                          :command "ps -ef"
                          :cron {:year 2019
                                 :month 1
                                 :day 1
                                 :hour 1
                                 :minute 1}})
          update {:docker-image "ubuntu"
                  :command "ls -la /"
                  :cron {:year 2018
                         :month 1
                         :day 1
                         :hour 1
                         :minute 1}}]
      (is (= (map? (s/validate m/Schedule (c/update (:_id data) update))) true)))))

(deftest patch-test
  (testing "patch schedule"
    (let [data (c/create {:docker-image "alpine"
                          :command "ps -ef"
                          :cron {:year 2019
                                 :month 1
                                 :day 1
                                 :hour 1
                                 :minute 1}})]
      (is (= (map? (s/validate m/Schedule (c/update (:_id data) {:docker-image "debian"}))) true))
      (is (= (map? (s/validate m/Schedule (c/update (:_id data) {:docker-image "ls -la /"}))) true))
      (is (= (map? (s/validate m/Schedule (c/update (:_id data) {:cron {:year 2011
                                                                        :month 2
                                                                        :day 2
                                                                        :hour 2
                                                                        :minute 2}}))) true)))))

(deftest delete-by-id-test
  (testing "restore one item"
    (let [data (c/create {:docker-image "alpine"
                          :command "ps -ef"
                          :cron {:year 2019
                                 :month 1
                                 :day 1
                                 :hour 1
                                 :minute 1}})]
      (is (= (map? (s/validate m/Schedule (c/delete (:_id data)))) true)))))