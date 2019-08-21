(defproject worker "0.1.0"
  :description "Get schedules from mongo db and run it, as fast as possible."
  :url "http://127-0-0-1.nip.io"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.5.0"]
                 [com.novemberain/monger "3.1.0"]
                 [clj-time "0.15.2"]
                 [me.raynes/fs "1.4.6"]]
  :main ^:skip-aot worker.core
  :target-path "target/%s"
  :uberjar-name "app-standalone.jar"
  :profiles {:uberjar {:aot :all}
             :dev {:plugins [[lein-cljfmt "0.6.4"]
                             [lein-cloverage "1.1.1"]
                             [jonase/eastwood "0.3.5"]
                             [lein-kibit "0.1.6"]
                             [lein-codox "0.10.7"]]}})
