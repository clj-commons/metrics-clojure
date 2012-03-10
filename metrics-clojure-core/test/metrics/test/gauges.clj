(ns metrics.test.gauges
  (:require [metrics.gauges :as gauges])
  (:use [clojure.test]))


(deftest test-gauge
  (let [g (gauges/gauge ["test" "gauges" "test-gauge"]
                        (+ 100 1))]
    (is (= (gauges/value g) 101))))

(deftest test-gauge-fn
  (let [g (gauges/gauge-fn ["test" "gauges" "test-gauge-fn"]
                           #(+ 100 2))]
    (is (= (gauges/value g) 102))))
