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
        user (source.core/user? event)
        hash (h/event-hash event)]
    (with-meta event {:datasource source :type type :action action :user user :hash hash})))

(defn format!
  "Formats an event based on it's source type"
  [event]
  (let [t (tag-event! event)
        t (merge t {:datasource_id (:id (h/event-path-params t)) :timestamp (h/generate-timestamp)})]
    (source.core/process! t)))

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
        i {:datasource_id (Integer. (:datasource_id f))
           :timestamp (:timestamp f)
           :type (:type m)
           :action (:action m)
           :user (:user m)
           :hash (:hash m)
           :filename (warehouse.s3.core/generate-title f)}]
    (config.core/record-event! (config.core/event-table e) i)
    (warehouse.s3.core/record-event! (warehouse.s3.core/event-bucket e) f)
    f))
