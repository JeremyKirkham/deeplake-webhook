(ns source.core-test
  (:require [clojure.test :refer :all]
            [source.core :refer :all]
            [deeplake-webhook.event-helpers]))

(def test-event
  {:body-json {:foo "bar"}
   :params {:path
              {:datasource_id 1
               :secret "foo"
               :datasource "github"}
            :querystring {:id "baz"}
            :header {:some-header "foo"}}
   :stage-variables {:environment "staging"}})

(def test-event-piv
 {:params {:path {:datasource "pivotal"}}})

(deftest multimethod-handler-test
  (testing "We dispatch our event depending on the tagged metadata"
    (let [tagged (deeplake-webhook.event-helpers/tag-event! test-event)
          tagged-piv (deeplake-webhook.event-helpers/tag-event! test-event-piv)]
      (is (= (process! tagged) "github multimethod!"))
      (is (= (process! tagged-piv) "pivotal multimethod!")))))
