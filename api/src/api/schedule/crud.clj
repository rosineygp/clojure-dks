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

(defn update [id data]
  (let [document (dissoc (restore-by-id id) :_id)
        time {:updated-at (str (l/local-now))}]
    (mongo/update-by-id id (merge document data time))
    (mongo/restore-by-id id)))

(defn patch [id data]
  (let [document (dissoc (restore-by-id id) :_id)
        time {:updated-at (str (l/local-now))}]
    (mongo/update-by-id id (merge document data time))
    (mongo/restore-by-id id)))

(defn delete [id]
  (let [document (dissoc (restore-by-id id) :_id)
        data {:deleted-at (str (l/local-now))}]
    {:result (mongo/update-by-id id (merge document data))}))
