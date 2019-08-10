(ns worker.cni
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

(defn docker-logs
  "Retrieve docker logs"
  [container-id]
  (log/info (str "docker-logs: " container-id))
  (shell/sh "docker" "container" "logs" "--timestamps" container-id))