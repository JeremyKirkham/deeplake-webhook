(ns source.pivotal.core
  (:require [deeplake-webhook.event-helpers]
            [source.core]))

; Constant defining the PivotalTracker source.
(def ^:const piv "pivotaltracker")

(defmethod source.core/process! piv
  [event]
  event)

(defmethod source.core/type? piv
  [event]
  (let [body (deeplake-webhook.event-helpers/event-body event)]
    (:kind body)))

(defmethod source.core/action? piv
  [event]
  (let [body (deeplake-webhook.event-helpers/event-body event)]
    (:highlight body)))

(defmethod source.core/user? piv
  [event]
  (let [body (deeplake-webhook.event-helpers/event-body event)]
    (:name (:performed_by body))))
