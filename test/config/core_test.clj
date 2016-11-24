(ns config.core-test
  (:require [clojure.test :refer :all]
            [config.core :refer :all]))

(deftest verify-dynamodb-datasource-table-environment
  (testing "verify we send events to correct bucket"
    (is (= (config.core/datasource-table "local") "datasources-local"))
    (is (= (config.core/datasource-table "staging") "datasources-staging"))
    (is (= (config.core/datasource-table "production") "datasources"))
    (is (= (config.core/datasource-table nil) "datasources-local"))))

(deftest verify-dynamodb-event-table-environment
  (testing "verify we send events to correct bucket"
    (is (= (config.core/event-table "local") "events-local"))
    (is (= (config.core/event-table "staging") "events-staging"))
    (is (= (config.core/event-table "production") "events"))
    (is (= (config.core/event-table nil) "events-local"))))
