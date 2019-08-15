(ns api.schedule.crud
  (:require [api.service.mongo :as mongo]))

(defn create [data]
  (let [result (mongo/create data)
        id (str (:_id result))]
    (-> (select-keys result [:client-id
                             :cron
                             :command
                             :docker-image
                             :created-at
                             :updated-at
                             :deleted-at
                             :executed-at
                             :logs
                             :status])
        (merge {:_id id}))))

(defn restore []
  (hash-map (mongo/restore)))

(defn restoreUUUU [id]
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
