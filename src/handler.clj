(ns handler
  (:require
    [deeplake-webhook.core]
    [uswitch.lambada.core :refer [deflambdafn]]
    [clojure.java.io :as io]
    [cheshire.core]))

(defn run
  "Function that kicks off the event handling"
  [event]
  (let [response event]
    (str response)))

(deflambdafn deeplake-webhook.run
  [in out ctx]
  (let [event (cheshire.core/parse-stream (io/reader in) true)
        res (run event)]
    (with-open [w (io/writer out)]
      (cheshire.core/generate-string res w))))

; Test data for running the handler locally.
(def test-event
  {:body-json {:foo "bar"}
   :params {:path
              {:datasource_id 1
               :secret "foo"
               :datasource "github"}
            :querystring {}
            :header {}}
   :stage-variables {:environment "local"}})

(defn -handler
  "Dummy handler for running locally"
  []
  (let [event test-event
        res (run event)]
    (println res)))
