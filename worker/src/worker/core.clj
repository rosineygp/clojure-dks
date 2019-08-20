(ns worker.core
  (:require [worker.controller.orchestrator :refer :all :as o])
  (:gen-class))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (while true
    (o/schedule)
    (Thread/sleep 10000)
    (println "Looping")))