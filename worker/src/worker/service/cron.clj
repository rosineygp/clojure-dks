(ns worker.service.cron
  (:require [clj-time.core :as t]
            [clj-time.local :as l]
            [clojure.string :as s]))

(defn- sys-time []
  (let [sys (l/local-now)]
    (t/date-time (t/year sys)
                 (t/month sys)
                 (t/day sys)
                 (t/hour sys)
                 (t/minute sys))))

(defn run-it? [year month day hour minute]
  (let [sys (sys-time)
        cron (t/date-time year month day hour minute)]
    (cond (t/equal? sys cron) true
          (t/after? sys cron) true
          :else false)))

(defn filter-run [cron]
  (println cron)
  (run-it? (:year cron)
           (:month cron)
           (:day cron)
           (:hour cron)
           (:minute cron)))

(defn local-now []
  (str (l/local-now)))
