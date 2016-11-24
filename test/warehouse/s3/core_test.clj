(ns warehouse.s3.core-test
  (:require [clojure.test :refer :all]
            [warehouse.s3.core :refer :all]))

(def event1 {:datasource_id 1 :timestamp 1476182402392})
(def event2 {:datasource_id 2 :timestamp 1276200000000})

(deftest verify-file-naming
  (testing "verify we are naming files correctly"
    (is (= (generate-title event1) "1/2016/10/11/1476182402392.edn"))
    (is (= (generate-title event2) "2/2010/06/10/1276200000000.edn"))))

(deftest verify-bucket-environment
  (testing "verify we send events to correct bucket"
    (is (= (event-bucket "local") "deeplake-events-local"))
    (is (= (event-bucket "staging") "deeplake-events-staging"))
    (is (= (event-bucket "production") "deeplake-events-1-us-east-1"))
    (is (= (event-bucket nil) "deeplake-events-local"))))
