(ns metrics.test.expose-test
  (:require [clojure.test :refer :all]
            [metrics.ring.expose :refer [filter-matches? expose-metrics-as-json]])
  (:import [com.codahale.metrics MetricRegistry]))

(deftest test-expose-metrics-as-json
  ;; Ensure that ring.expose compiles
  (is (instance? Object (expose-metrics-as-json (constantly nil)))))

(deftest test-filter-matches?
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "ring")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "ring.requests-scheme")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "ring.")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "ring.*")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "ring..")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "*.requests-scheme")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" ".requests-scheme")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "ring.*.rate")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "ring..rate")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "ring.*.*.https")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "ring.*.rate.*")))
  (is (true? (filter-matches? "ring.requests-scheme.rate.https" "ring.requests-scheme.rate.https")))
  ;;
  (is (false? (filter-matches? "ring.requests-scheme.rate.https" "RING")))
  (is (false? (filter-matches? "ring.requests-scheme.rate.https" "RING.requests-scheme")))
  (is (false? (filter-matches? "ring.requests-scheme.rate.https" "..requests-scheme")))
  (is (false? (filter-matches? "ring.requests-scheme.rate.https" "ring.*.https")))

  )