(ns source.github.core
  (:require [deeplake-webhook.event-helpers]
            [source.core]))

; Constant defining the Github source.
(def ^:const git "github")

(defmethod source.core/process! git
  [event]
  event)

(defmethod source.core/type? git
  [event]
  (let [headers (deeplake-webhook.event-helpers/event-headers event)]
    (:X-GitHub-Event headers)))

(defmethod source.core/action? git
  [event]
  (let [body (deeplake-webhook.event-helpers/event-body event)]
    (:action body)))

(defmethod source.core/user? git
  [event]
  (let [body (deeplake-webhook.event-helpers/event-body event)]
    (:login (:user body))))
