(ns warehouse.s3.core
  (:require
    [config.core]
    [amazonica.aws.s3 :refer [put-object]]
    [clojure.java.io :as io]
    [clj-time.format :as f]
    [clj-time.coerce]))

; Constant defining bucket for events.
(defn event-bucket
  [environment]
  (case environment
        "local" "deeplake-events-local"
        "staging" "deeplake-events-staging"
        "production" (str "deeplake-events-1-" config.core/aws-region)
        "deeplake-events-local"))

; Custom date format.
(def custom-formatter (f/formatter "yyyy/MM/dd"))

(defn generate-title
  "Generates an S3 appropriate filename"
  [event]
  (let [timestamp (:timestamp event)
        time (clj-time.coerce/from-long timestamp)
        date (f/unparse custom-formatter time)]
    (str (:datasource_id event) "/" date "/" (:timestamp event) ".edn")))


(defn map->bytes
  "Transforms a map into then bytes"
  [in]
  (-> in
    (pr-str ,,,)
    (.getBytes ,,,)))

; do stuff.
(defn record-event!
  "Records the event in S3"
  [environment event]
  (let [bytes (map->bytes event)]
    (put-object config.core/aws-creds
                :bucket-name (event-bucket environment)
                :metadata {:server-side-encryption "AES256"}
                :key (generate-title event)
                :input-stream (io/input-stream bytes)
                :metadata {:content-length (count bytes)})))
