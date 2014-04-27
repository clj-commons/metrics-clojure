(ns metrics.test.gauges-test
  (:require [metrics.gauges :as gauges]
            [metrics.core :as mc]
            [clojure.test :refer :all]))

(let [reg (mc/new-registry)]
  (gauges/defgauge reg ["test" "gauges" "defgauged"] (constantly 1001))

  (deftest test-defgauge
    (is (= (gauges/value defgauged) 1001))))

(deftest test-gauge-fn
  (let [r (mc/new-registry)
        g (gauges/gauge-fn ["test" "gauges" "test-gauge-fn"]
                           #(+ 100 2))]
    (is (= (gauges/value g) 102))))
