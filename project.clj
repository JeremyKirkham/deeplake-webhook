(defproject deeplake-webhook "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure                   "1.7.0"]
                 [uswitch/lambada                       "0.1.2"]
                 [cheshire                              "5.6.3"]
                 [clj-time                              "0.12.0"]
                 [com.amazonaws/aws-lambda-java-core    "1.1.0"]
                 [com.amazonaws/aws-java-sdk-core       "1.11.40"]
                 [com.amazonaws/aws-java-sdk-s3         "1.11.40"]
                 [com.amazonaws/aws-java-sdk-dynamodb   "1.11.40"]
                 [amazonica                             "0.3.48"
                      :exclusions [com.amazonaws/aws-java-sdk
                                   com.amazonaws/amazon-kinesis-client]]]
  :plugins [[lein-cloverage "1.0.7"]]
  :main handler/-handler
  :aot :all
  :jar-name "deeplakeWebhookEventHandler.jar"
  :uberjar-name "deeplakeWebhookEventHandler-standalone.jar")
