(ns source.github.core
  [:use source.core]
  (:require [deeplake-webhook.event-helpers]))

; Constant defining the Github source.
(def ^:const git "github")

(defmethod process! git
  [event]
  "github multimethod!")

(defmethod type? git
  [event]
  (let [headers (deeplake-webhook.event-helpers/event-headers event)]
    (:X-GitHub-Event headers)))

(defmethod action? git
  [event]
  (let [body (deeplake-webhook.event-helpers/event-body event)]
    (:action body)))
