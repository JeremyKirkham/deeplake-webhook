(ns handler
  (:require
    [deeplake-webhook.core]
    [uswitch.lambada.core :refer [deflambdafn]]
    [clojure.java.io :as io]
    [cheshire.core]
    [clojure.data.json :as json]))

(defn run
  "Function that kicks off the event handling"
  [event]
  (if (deeplake-webhook.core/valid? event)
    (deeplake-webhook.core/process! event)
    (throw (Exception. (str "403 Forbidden: Please check the webhook URL is correct!")))))

(deflambdafn deeplake-webhook.run
  [in out ctx]
  (let [event (cheshire.core/parse-stream (io/reader in) true)
        res (run event)]
    (with-open [w (io/writer out)]
      (json/write res w))))

; Test data for running the handler locally.
(def test-event
  {:body-json {:foo "bar"}
   :params {:path
              {:id 1
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
