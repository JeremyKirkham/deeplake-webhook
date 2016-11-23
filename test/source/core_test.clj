(ns source.core-test
  (:require [clojure.test :refer :all]
            [source.core :refer :all]
            [deeplake-webhook.event-helpers]
            [deeplake-webhook.core]))

(def test-event
  {:body-json {:foo "bar" :action "opened"}
   :params {:path
              {:id 1
               :secret "foo"
               :datasource "github"}
            :querystring {:id "baz"}
            :header {:X-GitHub-Event "foo"}}
   :stage-variables {:environment "staging"}})

(def test-event-piv
 {:params {:path {:datasource "pivotal"}}})

(deftest multimethod-handler-test
  (testing "We dispatch our event depending on the tagged metadata"
    (let [tagged (deeplake-webhook.core/tag-event! test-event)
          tagged-piv (deeplake-webhook.core/tag-event! test-event-piv)]
      (is (= (process! tagged) test-event))
      (is (= (process! tagged-piv) test-event-piv)))))
