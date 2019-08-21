(ns worker.controller.orchestrator
  "Worker flow."
  (:require [worker.service.cni :refer :all :as cni]
            [worker.service.mongo :refer :all :as mongo]
            [worker.service.sofs :refer :all :as sofs]
            [worker.service.cron :refer :all :as cron]))

(defn set-as-running
  "Change the state of scheduler to running."
  [id]
  (let [document (dissoc (restore-by-id id) :_id)]
    (mongo/update-by-id
     id (merge document
               {:updated-at (cron/local-now)
                :status "running"}))))

(defn save-log
  "Update result of job to database."
  [id log]
  (let [document (dissoc (restore-by-id id) :_id)
        date-time (cron/local-now)]
    (mongo/update-by-id
     id (merge document
               {:executed-at date-time
                :logs [log]
                :status "completed"}))))

(defn run-job
  "Just run the job"
  [job]
  (let [image (:docker-image job)
        command (:command job)
        id (:_id job)
        mount-point (sofs/make-script id command)]
    (cni/docker-script id image mount-point)))

(defn schedule
  "Get information from database and orchestrate the flow."
  []
  (let [jobs (filter #(cron/filter-run (:cron %))
                     (mongo/find {:status "created"}))]
    (pmap (fn [job]
            (let [id (:_id job)]
              (set-as-running id)
              (save-log id (run-job job)))) jobs)))