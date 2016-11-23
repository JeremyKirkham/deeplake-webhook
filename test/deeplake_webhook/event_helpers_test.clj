(ns deeplake-webhook.event-helpers-test
  (:require [clojure.test :refer :all]
            [deeplake-webhook.event-helpers :refer :all]
            [clj-time.core :as t]
            [clj-time.coerce :as c]))

; Test data for running the handler locally.
(def test-event
  {:body-json {:foo "bar"}
   :params {:path
              {:datasource_id 1
               :secret "foo"
               :datasource "github"}
            :querystring {:id "baz"}
            :header {:some-header "foo"}}
   :stage-variables {:environment "staging"}})

(deftest generate-timestamp-test
 (testing "generates the current time in milliseconds"
   (is (<= (- (c/to-long (t/now)) (generate-timestamp)) 5))))

(deftest body-test
  (testing "We get the body of the event"
    (is (= (event-body test-event) {:foo "bar"}))))

(deftest params-test
  (testing "We get the parameters of the event"
    (is (= (event-params test-event) {:path
                                         {:datasource_id 1
                                          :secret "foo"
                                          :datasource "github"}
                                      :querystring {:id "baz"}
                                      :header {:some-header "foo"}}))))

(deftest stage-test
  (testing "We get the stage variables of the event"
    (is (= (event-stage-vars test-event) {:environment "staging"}))))

(deftest path-test
  (testing "We get the paramter path variables of the event"
    (is (= (event-path-params test-event) {:datasource_id 1
                                           :secret "foo"
                                           :datasource "github"}))))

(deftest headers-test
 (testing "We get the headers of the event"
   (is (= (event-headers test-event) {:some-header "foo"}))))

(deftest query-test
  (testing "We get the query parameters of the event"
    (is (= (event-query-params test-event) {:id "baz"}))))

(deftest environment-test
  (testing "We get the environment of the event if sent"
    (is (= (event-environment test-event) "staging")))
  (testing "We get the default local environment if it's not sent in the event"
    (is (= (event-environment {:body-json {:foo "bar"}}) "local"))))

(deftest valid-event-test
  (testing "Our valid-event? func doesn't throw errors"
    (is (= (valid-event? test-event) false))))

(deftest verify-match-test
  (testing "We only verify valid events"
    (is (= (verify-match 1 1 "foo" "foo") true)))
  (testing "We handle invalid events when ids don't match"
    (is (= (verify-match 1 2 "foo" "foo") false)))
  (testing "We handle invalid events when secrets don't match"
    (is (= (verify-match 1 1 "foo" "bar") false)))
  (testing "We handle invalid events when ids and secrets don't match"
    (is (= (verify-match 1 3 "baz" "bar") false))))
