 (defproject api "0.1.0-SNAPSHOT"
   :description "FIXME: write description"
   :dependencies [[org.clojure/clojure "1.8.0"]
                  [metosin/compojure-api "1.1.11"]
                  [com.novemberain/monger "3.1.0"]
                  [clj-time "0.15.2"]]
   :ring {:handler api.handler/app}
   :uberjar-name "server.jar"
   :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                                   [cheshire "5.5.0"]
                                   [ring/ring-mock "0.3.0"]]
                    :plugins [[lein-ring "0.12.0"]
                              [lein-cljfmt "0.6.4"]
                              [lein-cloverage "1.1.1"]
                              [jonase/eastwood "0.3.5"]]}})
