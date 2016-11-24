(ns deeplake-webhook.core
  (:require
    [deeplake-webhook.event-helpers]
    [source.core]
    [source.github.core]
    [source.pivotal.core]))

(defn tag-event!
  "Adds some metadata to the event for handling down the line"
  [event]
  (let [source (:datasource (deeplake-webhook.event-helpers/event-path-params event))
        event (with-meta event {:datasource source})
        type (source.core/type? event)
        action (source.core/action? event)
        hash (deeplake-webhook.event-helpers/event-hash event)]
    (with-meta event {:datasource source :type type :action action :hash hash})))

(defn format!
  "Formats an event based on it's source type"
  [event]
  (let [tagged (tag-event! event)]
    (source.core/process! tagged)))

(defn valid?
  "Returns true if event is valid, otherwise false"
  [event]
  (deeplake-webhook.event-helpers/valid-event? event))

(defn process-event!
  "Primary function for processing an event"
  [event]
  (let [formatted (format! event)]
    formatted))
