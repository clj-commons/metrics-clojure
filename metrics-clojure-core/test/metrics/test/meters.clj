(ns metrics.test.meters
  (:require [metrics.meters :as meters])
  (:use [metrics.test.test-utils])
  (:use [clojure.test]))


(deftest test-rate-mean
  (let [m (meters/meter ["test" "meters" "test-rate-mean"] "test-events")]
    (is (= (meters/rate-mean m) 0.0))
    (meters/mark! m)
    (is (> (meters/rate-mean m) 0.0))))

(deftest test-rate-mean-update-multiple
  (let [m (meters/meter ["test" "meters" "test-rate-mean-update-multiple"] "test-events")]
    (is (= (meters/rate-mean m) 0.0))
    (meters/mark! m 10)
    (is (> (meters/rate-mean m) 0.0))))

(deftest test-rate-one
  (let [m (meters/meter ["test" "meters" "test-rate-one"] "test-events")]
    (is (= (meters/rate-one m) 0.0))
    (meters/mark! m 20000)
    (Thread/sleep 8000)
    (is (> (meters/rate-one m) 0.0))))

(deftest test-rate-five
  (let [m (meters/meter ["test" "meters" "test-rate-five"] "test-events")]
    (is (= (meters/rate-five m) 0.0))
    (meters/mark! m 20000)
    (Thread/sleep 8000)
    (is (> (meters/rate-five m) 0.0))))

(deftest test-rate-fifteen
  (let [m (meters/meter ["test" "meters" "test-rate-fifteen"] "test-events")]
    (is (= (meters/rate-fifteen m) 0.0))
    (meters/mark! m 20000)
    (Thread/sleep 8000)
    (is (> (meters/rate-fifteen m) 0.0))))

(deftest test-rates
  (let [m (meters/meter ["test" "meters" "test-rates"] "test-events")]
    (is (every? zero? (vals (meters/rates m))))
    (meters/mark! m 20000)
    (Thread/sleep 8000)
    (is (every? #(> % 0.0) (vals (meters/rates m))))))
