(ns metrics.test.timers
  (:require [metrics.timers :as timers])
  (:use [metrics.test.utils])
  (:use [clojure.test]))


(defn- sleep-100 []
  (Thread/sleep 100)
  100)

(defn- sleep-200 []
  (Thread/sleep 200)
  200)


(deftest test-rate-mean
  (let [t (timers/timer ["test" "timers" "test-rate-mean"])]
    (is (= (timers/rate-mean t) 0))
    (is (= (timers/time! t (sleep-100)) 100))
    (is (> (timers/rate-mean t) 0))))

(deftest test-rate-mean-fn
  (let [t (timers/timer ["test" "timers" "test-rate-mean-fn"])]
    (is (= (timers/rate-mean t) 0))
    (is (= (timers/time-fn! t sleep-100) 100))
    (is (> (timers/rate-mean t) 0))))

(deftest test-rate-one
  (let [t (timers/timer ["test" "timers" "test-rate-one"])]
    (is (= (timers/rate-one t) 0))
    (is (= (timers/time! t (sleep-100)) 100))
    (Thread/sleep 8000)
    (is (> (timers/rate-one t) 0))))

(deftest test-rate-five
  (let [t (timers/timer ["test" "timers" "test-rate-five"])]
    (is (= (timers/rate-five t) 0))
    (is (= (timers/time! t (sleep-100)) 100))
    (Thread/sleep 8000)
    (is (> (timers/rate-five t) 0))))

(deftest test-rate-fifteen
  (let [t (timers/timer ["test" "timers" "test-rate-fifteen"])]
    (is (= (timers/rate-fifteen t) 0))
    (is (= (timers/time! t (sleep-100)) 100))
    (Thread/sleep 8000)
    (is (> (timers/rate-fifteen t) 0))))


(deftest test-largest
  (let [t (timers/timer ["test" "timers" "test-largest"])]
    (is (= (timers/largest t) 0))
    (is (= (timers/time! t (sleep-100)) 100))
    (is (within-ten (timers/largest t) 100))
    (is (= (timers/time! t (sleep-200)) 200))
    (is (within-ten (timers/largest t) 200))))
(deftest test-smallest
  (let [t (timers/timer ["test" "timers" "test-smallest"])]
    (is (= (timers/smallest t) 0))
    (is (= (timers/time! t (sleep-100)) 100))
    (is (within-ten (timers/smallest t) 100))
    (is (= (timers/time! t (sleep-200)) 200))
    (is (within-ten (timers/smallest t) 100))))
