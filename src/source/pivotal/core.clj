(ns source.pivotal.core
  [:use source.core]
  (:require [deeplake-webhook.event-helpers]))

; Constant defining the PivotalTracker source.
(def ^:const piv "pivotal")

(defmethod process! piv
  [event]
  "pivotal multimethod!")

(defmethod type? piv
  [event]
  (let [body (deeplake-webhook.event-helpers/event-body event)]
    (:kind body)))

(defmethod action? piv
  [event]
  (let [body (deeplake-webhook.event-helpers/event-body event)]
    (:highlight body)))
