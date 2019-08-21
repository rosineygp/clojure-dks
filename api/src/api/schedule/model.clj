(ns api.schedule.model
  "Application model."
  (:require [schema.core :as s]))

(s/defschema Cron
  "Model of date and time."
  {:year s/Int
   :month s/Int
   :day s/Int
   :hour s/Int
   :minute s/Int})

(s/defschema Create
  "Model of schedule creation."
  {:docker-image s/Str
   :command s/Str
   :cron Cron})

(s/defschema Patch
  "Model of Alter schedule."
  {(s/optional-key :docker-image) s/Str
   (s/optional-key :command) s/Str
   (s/optional-key :cron) Cron})

(s/defschema Delete
  "Model of delete a schedule."
  {:result s/Int})

(s/defschema Logs
  "Logs based on shell exit."
  {:exit s/Int
   :out s/Str
   :err s/Str})

(s/defschema Schedule
  "Full schedule definition."
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
  "Model of probes."
  {:status s/Str})
