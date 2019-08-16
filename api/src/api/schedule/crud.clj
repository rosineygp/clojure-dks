(ns api.schedule.crud
  (:require [api.service.mongo :as mongo]
            [clj-time.local :as l]))

(defn create [data]
  (let [insert-data (merge
                     data
                     {:status "created"
                      :client-id "anonymous"
                      :logs []
                      :created-at (str (l/local-now))
                      :updated-at nil
                      :deleted-at nil
                      :executed-at nil})]
    (mongo/create insert-data)))

(defn restore []
  (mongo/restore))

(defn restore-by-id [id]
  (mongo/restore-by-id id))

(defn update [id]
  (println id)
  (hash-map
   :id id
   :command "ls -la"
   :docker-image "alpine"
   :cron "* * * * *"
   :status "running"
   :created-at "2019"
   :updated-at "2019"
   :deleted-at "2019"
   :logs {:exit "0"
          :out "ok computer"}))

(defn delete [id]
  (println id)
  (hash-map
   :id id
   :command "ls -la"
   :docker-image "alpine"
   :cron "* * * * *"
   :status "running"
   :created-at "2019"
   :updated-at "2019"
   :deleted-at "2019"
   :logs {:exit "0"
          :out "ok computer"}))
