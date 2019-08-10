(ns worker.core
  (:require [worker.cni :refer :all :as cni])
  (:gen-class))

(use '[clojure.java.shell :only [sh]])

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "---")
  (println  (cni/docker-run "ubuntu" ["ls" "-la"]))
  (println  (cni/docker-run "ubuntu" ["ps" "xua"]))
  (println  (cni/docker-run "ubuntu" ["000000a11"]))
  (println "---")
  (println "Hello, World!"))