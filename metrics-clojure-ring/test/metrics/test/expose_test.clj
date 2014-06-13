(ns metrics.test.expose-test
  (:require [clojure.test :refer :all]
            [metrics.ring.expose :refer [expose-metrics-as-json]])
  (:import [com.codahale.metrics MetricRegistry]))

(deftest test-expose-metrics-as-json
  ;; Ensure that ring.expose compiles
  (expose-metrics-as-json (constantly nil)))


