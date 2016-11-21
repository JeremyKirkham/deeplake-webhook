(ns handler
  (:require
    [deeplake-webhook.core]
    [uswitch.lambada.core :refer [deflambdafn]]))

(deflambdafn deeplake-webhook.run
  [in out ctx]
  (println "OMG I'm running in the cloud!!!111oneone"))

(defn -handler
  []
  (println "OMG I'm running in the cloud!!!111oneone"))
