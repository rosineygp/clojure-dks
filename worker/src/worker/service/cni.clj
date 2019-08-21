(ns worker.service.cni
  "Container Interface"
  (:require [clojure.java.shell :as shell]
            [clojure.tools.logging :as log]))

(defn docker-script
  "Execute docker script, semi-safe"
  [id image mount]
  (log/info (str ":docker-run " image " :mount-point " mount))
  (apply shell/sh ["docker"
                   "container"
                   "run"
                   "--name" (str "ajb_" id)
                   "--cpus" "1"
                   "--memory" "512M"
                   "--rm"
                   "--volume" (str mount ":/api-job-scheduler")
                   "--workdir" "/api-job-scheduler"
                   "--tty"
                   image
                   "sh"
                   "-c"
                   "./run.sh"]))
