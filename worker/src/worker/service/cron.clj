(ns worker.service.cron
  "Match times"
  (:require [clj-time.core :as t]
            [clj-time.local :as l]
            [clojure.string :as s]))

(defn- sys-time 
  "return a date-time based on system local time"
  []
  (let [sys (l/local-now)]
    (t/date-time (t/year sys)
                 (t/month sys)
                 (t/day sys)
                 (t/hour sys)
                 (t/minute sys))))

(defn run-it? 
  "Return true if date and time passed is lower than or equal now"
  [year month day hour minute]
  (let [sys (sys-time)
        cron (t/date-time year month day hour minute)]
    (cond (t/equal? sys cron) true
          (t/after? sys cron) true
          :else false)))

(defn filter-run
  "A suggar for run-it?"
  [cron]
  (println cron)
  (run-it? (:year cron)
           (:month cron)
           (:day cron)
           (:hour cron)
           (:minute cron)))

(defn local-now 
  "retunr local time as string. util to send to db."
  []
  (str (l/local-now)))
