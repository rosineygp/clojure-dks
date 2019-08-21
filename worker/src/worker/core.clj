(ns worker.core
  (:require [worker.controller.orchestrator :refer :all :as o])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... but docker, maybe."
  [& args]
  (while true
    (o/schedule)
    (Thread/sleep 20000)
    (println "Looping")))