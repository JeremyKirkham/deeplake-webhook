(ns deeplake-webhook.event-helpers
  (:require
    [config.core]
    [clj-time.core :as t]
    [clj-time.coerce :as c]
    [pandect.algo.sha1]
    [cheshire.core]))

(defn generate-timestamp
  "Generates a timestamp for an event"
  []
  (c/to-long (t/now)))

(defn event-body
  "Returns the body payload of the event"
  [event]
  (:body-json event))

(defn event-params
  "Returns the parameters sent with the event"
  [event]
  (:params event))

(defn event-stage-vars
  "Returns the stage variables of the event"
  [event]
  (:stage-variables event))

(defn event-path-params
  "Returns the path variables set from the API gateway"
  [event]
  (let [params (event-params event)]
    (:path params)))

(defn event-headers
  "Returns the headers sent with the event"
  [event]
  (let [params (event-params event)]
     (:header params)))

(defn event-query-params
  "Returns the querystring parameters of the event"
  [event]
  (let [params (event-params event)]
    (:querystring params)))

; The default environment
(def default-environment "local")

(defn event-environment
  "Returns the environment of the event - defaults to local"
  [event]
  (let [stage (event-stage-vars event)
        env (:environment stage)]
    (if (nil? env)
      default-environment
      env)))

(defn verify-match
  "Checks an event id and secret match what we've got in dynamo"
  [id db-id secret db-secret]
  (and (= id db-id) (= secret db-secret)))

(defn valid-event?
  "Determines if a datasource id is in our system with matching secret"
  [event]
  (let [env (event-environment event)
        id (str (:id (event-path-params event)))
        secret (str (:secret (event-path-params event)))
        item (config.core/get-item-by-key! (config.core/datasource-table env) id)
        db-id (str (:id item))
        db-secret (str (:secret item))]
    (verify-match id db-id secret db-secret)))

(defn event-hash
  "Returns the hash of an event body"
  [event]
  (let [body (event-body event)]
    (pandect.algo.sha1/sha1 (cheshire.core/generate-string body))))
