(ns api.boot.connector
  "Open connection in our beautiful mongo db
   Only simple connection, gorget about replicas set"
  (:require [monger.core :as mg]
            [monger.credentials :as mcred])
  (:import org.bson.types.ObjectId))

(let [host (or (System/getenv "MONGO_HOST") "localhost")
      port (Integer/parseInt (or (System/getenv "MONGO_PORT") "27017"))
      database (or (System/getenv "MONGO_DATABASE_NAME") "schedule")
      credentials (mcred/create
                   (or (System/getenv "MONGO_USER") "root")
                   (or (System/getenv "MONGO_AUTH_DB") "admin")
                   (or (System/getenv "MONGO_PASSWORD") "password"))]
  (defn monger
    "Monger connection like a boss, better than before I guess."
    []
    (let [conn (mg/connect-with-credentials host port credentials)]
      (mg/get-db conn database))))