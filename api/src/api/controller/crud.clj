(ns api.controller.crud
  "Application controller."
  (:require [api.service.mongo :as mongo]
            [clj-time.local :as l]))

(defn create
  "Get user input schedule and create a mongo document 
  that represents the initial status of a job."
  [data]
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

(defn restore
  "Handler, restore all entries in mongo."
  []
  (mongo/restore))

(defn restore-by-id
  "Handler, get a document by Id in mongo."
  [id]
  (mongo/restore-by-id id))

(defn update
  "Get the current document in mongo by Id.
  Merge it with new data and update all user entries."
  [id data]
  (let [document (dissoc (restore-by-id id) :_id)
        time {:updated-at (str (l/local-now))}]
    (mongo/update-by-id id (merge document data time))
    (mongo/restore-by-id id)))

(defn patch
  "Get the current document in mongo by Id.
  Merge it with new data and update it back.
  For one or more changes."
  [id data]
  (let [document (dissoc (restore-by-id id) :_id)
        time {:updated-at (str (l/local-now))}]
    (mongo/update-by-id id (merge document data time))
    (mongo/restore-by-id id)))

(defn delete
  "Make a soft delete, just change the status to deleted
  and updating deleted-at time."
  [id]
  (let [document (dissoc (restore-by-id id) :_id)
        data {:deleted-at (str (l/local-now)) :status "deleted"}]
    (mongo/update-by-id id (merge document data))
    (mongo/restore-by-id id)))
