(ns worker.mongo-test
  (:require [clojure.test :refer :all]
            [worker.service.mongo :refer :all :as m])
  (:import org.bson.types.ObjectId))

(deftest mongo-id-to-str-test
  (testing "if id conversion return a string"
    (let [data {:_id (ObjectId.)
                :name "just new data"}]
      (is (= (string? (:_id (m/id-to-str data))) true)))))

(deftest mongo-restore-test
  (testing "if return of restore is a list"
    (is (= (seq? (m/restore)) true))))