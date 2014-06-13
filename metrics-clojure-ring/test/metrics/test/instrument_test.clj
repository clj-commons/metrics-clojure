(ns metrics.test.instrument-test
  (:require [clojure.test :refer :all]
            [metrics.ring.instrument :refer [instrument]])
  (:import [com.codahale.metrics MetricRegistry]))

(deftest test-instrument
  ;; ensure that ring.instrument compiles
  (let [dummy-handler (fn [handler] (fn [request] request))]
    (instrument dummy-handler (MetricRegistry. ))))

