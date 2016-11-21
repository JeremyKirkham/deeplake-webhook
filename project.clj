(defproject deeplake-webhook "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure                "1.7.0"]
                 [uswitch/lambada                    "0.1.2"]]
  :main handler/-handler
  :jar-name "deepLakeWebhook.jar"
  :uberjar-name "deepLakeWebhook-standalone.jar")
