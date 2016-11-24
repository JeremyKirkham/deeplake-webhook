(ns deeplake-webhook.core
  (:require
    [deeplake-webhook.event-helpers :as h]
    [config.core]
    [warehouse.s3.core]
    [source.core]
    [source.github.core]
    [source.pivotal.core]))

(defn tag-event!
  "Adds some metadata to the event for handling down the line"
  [event]
  (let [source (:datasource (h/event-path-params event))
        event (with-meta event {:datasource source})
        type (source.core/type? event)
        action (source.core/action? event)
        hash (h/event-hash event)]
    (with-meta event {:datasource source :type type :action action :hash hash})))

(defn format!
  "Formats an event based on it's source type"
  [event]
  (let [tagged (tag-event! event)
        tagged (merge tagged {:timestamp (h/generate-timestamp)})]
    (source.core/process! tagged)))

(defn valid?
  "Returns true if event is valid, otherwise false"
  [event]
  (h/valid-event? event))

(defn process-event!
  "Primary function for processing an event"
  [event]
  (let [f (format! event)
        e (h/event-environment f)
        m (meta f)
        i {:datasource_id (Integer. (:id (h/event-path-params f)))
           :timestamp (Integer. (:timestamp f))
           :type (:type m)
           :action (:action m)
           :hash (:hash m)
           :filename (warehouse.s3.core/generate-title f)}]
    (config.core/record-event! (config.core/event-table e) i)
    f))
