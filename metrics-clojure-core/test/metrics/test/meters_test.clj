(ns metrics.test.meters-test
  (:require [metrics.meters :as meters]
            [metrics.test.test-utils :refer :all]
            [clojure.test :refer :all]))

(meters/defmeter ["test" "meters" "defmetered"])

(deftest test-defmeter
  (is (= (meters/rate-mean defmetered) 0.0))
  (meters/mark! defmetered)
  (is (> (meters/rate-mean defmetered) 0.0)))

(deftest test-rate-mean
  (let [m (meters/meter ["test" "meters" "test-rate-mean"])]
    (is (= (meters/rate-mean m) 0.0))
    (meters/mark! m)
    (is (> (meters/rate-mean m) 0.0))))

(deftest test-rate-mean-update-multiple
  (let [m (meters/meter ["test" "meters" "test-rate-mean-update-multiple"])]
    (is (= (meters/rate-mean m) 0.0))
    (meters/mark! m 10)
    (is (> (meters/rate-mean m) 0.0))))

(deftest test-rate-one
  (let [m (meters/meter ["test" "meters" "test-rate-one"])]
    (is (= (meters/rate-one m) 0.0))
    (meters/mark! m 20000)
    (Thread/sleep 8000)
    (is (> (meters/rate-one m) 0.0))))

(deftest test-rate-five
  (let [m (meters/meter ["test" "meters" "test-rate-five"])]
    (is (= (meters/rate-five m) 0.0))
    (meters/mark! m 20000)
    (Thread/sleep 8000)
    (is (> (meters/rate-five m) 0.0))))

(deftest test-rate-fifteen
  (let [m (meters/meter ["test" "meters" "test-rate-fifteen"])]
    (is (= (meters/rate-fifteen m) 0.0))
    (meters/mark! m 20000)
    (Thread/sleep 8000)
    (is (> (meters/rate-fifteen m) 0.0))))

(deftest test-rates
  (let [m (meters/meter ["test" "meters" "test-rates"])]
    (is (every? zero? (vals (meters/rates m))))
    (meters/mark! m 20000)
    (Thread/sleep 8000)
    (is (every? #(> % 0.0) (vals (meters/rates m))))))

(deftest test-count
  (let [m (meters/meter ["test" "meters" "test-count"])]
    (is (zero? (meters/count m)))
    (meters/mark! m)
    (is (= 1 (meters/count m)))))
