(ns worker.core
  "Application loop"
  (:require
   [worker.controller.orchestrator :refer :all :as o]
   [clojure.tools.logging :as log])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... but docker, maybe.
  It checks the database every 10 secs searching for new jobs"
  [& args]
  (while true
    (log/info (str "searching for new jobs"))
    (try
      (o/schedule)
      (catch Exception ex
        (.printStackTrace ex)
        (log/error (str "database connection error"))))
    (Thread/sleep 10000)))
