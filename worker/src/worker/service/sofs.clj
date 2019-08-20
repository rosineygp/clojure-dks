(ns worker.service.sofs
  "script on file system
  This write a script on file system and return the path
  the default name is run.sh"
  (:require [me.raynes.fs :refer :all]
            [clojure.java.io :as io]))

(def storage "/tmp/ajb/")
(def header "set -x \n")

(defn- write-file [full-path script]
  (with-open [w (clojure.java.io/writer full-path :append false)]
    (.write w (str script))))

(defn make-script [id script]
  (let [folder (str storage id)
        full-path (str folder "/run.sh")]
    (mkdirs folder)
    (write-file full-path (str header script))
    (chmod "+x" full-path)
    (str folder)))
