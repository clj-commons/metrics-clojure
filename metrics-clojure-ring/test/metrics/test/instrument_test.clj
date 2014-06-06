(ns metrics.test.instrument-test
  (:require [clojure.test :refer :all]
            [metrics.ring.instrument :refer [instrument]])
  (:import [com.codahale.metrics MetricRegistry]))

(deftest test-instrument
  "Shouldn't blow up with ClassCastException when passing in a request"
    (let [dummy-handler (fn [handler] (fn [request] request))]
         (instrument dummy-handler (MetricRegistry. ))))

