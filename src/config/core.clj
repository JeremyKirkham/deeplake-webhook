(ns config.core
  (require  [clojure.java.io :as io]
            [amazonica.aws.dynamodbv2 :refer [get-item put-item]]
            [clojure.edn :as edn]))

; Default AWS settings.

; Region
(def aws-region "us-east-1")
(def aws-creds {:endpoint aws-region})

; Dynamo operations
(defn datasource-table
  "Returns the correct datasource table for the API"
  [environment]
  (case environment
        "local" "datasources-local"
        "staging" "datasources-staging"
        "production" "datasources"
        "datasources-local"))

(defn event-table
  "Returns the correct event table to record data to"
  [environment]
  (case environment
        "local" "events-local"
        "staging" "events-staging"
        "production" "events"
        "events-local"))

(defn get-item-by-key!
  "Returns an item from dynamodb"
  [table id]
  (:item (get-item
            aws-creds
            :region aws-region
            :table-name table
            :key {:id {:n id}})))

(defn record-event!
  "Inserts a new item in dynamodb"
  [table item-map]
  (put-item
    aws-creds
    :table-name table
    :item item-map))
