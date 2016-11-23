(ns source.pivotal.core
  (:require [deeplake-webhook.event-helpers]
            [source.core]))

; Constant defining the PivotalTracker source.
(def ^:const piv "pivotal")

(defmethod source.core/process! piv
  [event]
  "pivotal multimethod!")

(defmethod source.core/type? piv
  [event]
  (let [body (deeplake-webhook.event-helpers/event-body event)]
    (:kind body)))

(defmethod source.core/action? piv
  [event]
  (let [body (deeplake-webhook.event-helpers/event-body event)]
    (:highlight body)))
