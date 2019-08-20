(ns worker.service.cni
  "Container Interface"
  (:require [clojure.java.shell :as shell]
            [clojure.tools.logging :as log]))

(defn docker-pull
  "Docker pull images"
  [image]
  (log/info (str "docker-pull: " image))
  (shell/sh "docker" "image" "pull" image))

(defn docker-run
  "Execute docker command"
  [image command]
  (log/info (str "docker-run: " image " command: " command))
  (apply shell/sh
         (concat ["docker" "container" "run" "--detach" image] command)))

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

(defn docker-logs
  "Retrieve docker logs"
  [container-id]
  (log/info (str "docker-logs: " container-id))
  (shell/sh "docker" "container" "logs" "--timestamps" container-id))
