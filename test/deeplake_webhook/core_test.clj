(ns deeplake-webhook.core-test
  (:require [clojure.test :refer :all]
            [deeplake-webhook.core :refer :all]))

; Test data for running the handler locally.
(def test-event
  {:body-json {:foo "bar" :action "opened"}
   :params {:path
              {:id 1
               :secret "foo"
               :datasource "github"}
            :querystring {:id "baz"}
            :header {:X-GitHub-Event "foo"}}
   :stage-variables {:environment "staging"}})

(deftest metadata-test
  (testing "We tag our event with the datasource"
    (let [tagged (tag-event! test-event)]
      (is (= (meta tagged) {:datasource "github", :type "foo", :action "opened"})))))
