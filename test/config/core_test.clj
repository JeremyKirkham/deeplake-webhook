(ns config.core-test
  (:require [clojure.test :refer :all]
            [config.core :refer :all]))

(deftest verify-dynamodb-table-environment
  (testing "verify we send events to correct bucket"
    (is (= (config.core/datasource-table "local") "datasources-local"))
    (is (= (config.core/datasource-table "staging") "datasources-staging"))
    (is (= (config.core/datasource-table "production") "datasources"))
    (is (= (config.core/datasource-table nil) "datasources-local"))))
