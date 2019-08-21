(ns worker.service.sofs
  "script on file system
  This write a script on file system and return the path
  the default name is run.sh."
  (:require [me.raynes.fs :refer :all]
            [clojure.java.io :as io]))

(def storage
  "Place that files will placed and shared with containers."
  (or (System/getenv "SCRIPT_STORAGE") "/tmp/ajb/"))
(def header
  "Script header."
  (or (System/getenv "SCRIPT_HEADER") "set -x \n"))

(defn- write-file
  "Write or rewrite a file in file system."
  [full-path script]
  (with-open [w (clojure.java.io/writer full-path :append false)]
    (.write w (str script))))

(defn make-script
  "Concat the header value with information passed to user."
  [id script]
  (let [folder (str storage id)
        full-path (str folder "/run.sh")]
    (mkdirs folder)
    (write-file full-path (str header script))
    (chmod "+x" full-path)
    (str folder)))
