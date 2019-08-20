(defproject worker "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.logging "0.5.0"]
                 [com.novemberain/monger "3.1.0"]
                 [clj-time "0.15.2"]
                 [me.raynes/fs "1.4.6"]
                 [org.slf4j/slf4j-nop "1.7.12"]]
  :main ^:skip-aot worker.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:plugins [[lein-cljfmt "0.6.4"]
                             [lein-cloverage "1.1.1"]
                             [jonase/eastwood "0.3.5"]
                             [lein-kibit "0.1.6"]]}})
