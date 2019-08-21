(ns api.schedule.model
  (:require [schema.core :as s]))

(s/defschema Cron
  {:year s/Int
   :month s/Int
   :day s/Int
   :hour s/Int
   :minute s/Int})

(s/defschema Create
  {:docker-image s/Str
   :command s/Str
   :cron Cron})

(s/defschema Patch
  {(s/optional-key :docker-image) s/Str
   (s/optional-key :command) s/Str
   (s/optional-key :cron) Cron})

(s/defschema Delete
  {:result s/Int})

(s/defschema Logs
  {:exit s/Int
   :out s/Str
   :err s/Str})

(s/defschema Schedule
  {:_id s/Str
   :client-id s/Str
   :docker-image s/Str
   :cron Cron
   :command s/Str
   :status s/Str
   :created-at s/Str
   :updated-at (s/maybe s/Str)
   :deleted-at (s/maybe s/Str)
   :executed-at (s/maybe s/Str)
   :logs (s/maybe [Logs])})

(s/defschema Probe
  {:status s/Str})
