(ns metrics.test.gauges-test
  (:require [metrics.gauges :as gauges]
            [clojure.test :refer :all]))

(gauges/defgauge ["test" "gauges" "defgauged"]
  1001)

(deftest test-defgauge
  (is (= (gauges/value defgauged) 1001)))

(deftest test-gauge
  (let [g (gauges/gauge ["test" "gauges" "test-gauge"]
                        (+ 100 1))]
    (is (= (gauges/value g) 101))))

(deftest test-gauge-fn
  (let [g (gauges/gauge-fn ["test" "gauges" "test-gauge-fn"]
                           #(+ 100 2))]
    (is (= (gauges/value g) 102))))
