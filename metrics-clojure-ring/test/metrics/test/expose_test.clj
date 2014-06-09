(ns metrics.test.expose-test
  (:require [clojure.test :refer :all]
            [metrics.ring.expose :refer [expose-metrics-as-json]])
  (:import [com.codahale.metrics MetricRegistry]))

(deftest test-expose-metrics-as-json
  "Ensure that the handler can be called without blowing up."
  (expose-metrics-as-json (constantly nil)))


