(ns metrics.test.counters
  (:require [metrics.counters :as counters])
  (:use [clojure.test]))


(counters/defcounter ["test" "counters" "defcountered"])

(deftest test-defcounter
  (is (= (counters/value defcountered) 0))
  (counters/inc! defcountered)
  (is (= (counters/value defcountered) 1)))

(deftest test-inc
  (let [c (counters/counter ["test" "counters" "test-inc"])]
    (is (= (counters/value c) 0))
    (counters/inc! c)
    (is (= (counters/value c) 1))
    (counters/inc! c 2)
    (is (= (counters/value c) 3))))

(deftest test-dec
  (let [c (counters/counter ["test" "counters" "test-dec"])]
    (is (= (counters/value c) 0))
    (counters/dec! c)
    (is (= (counters/value c) -1))
    (counters/dec! c 2)
    (is (= (counters/value c) -3))))

(deftest test-clear
  (let [c (counters/counter ["test" "counters" "test-clear"])]
    (is (= (counters/value c) 0))
    (counters/inc! c 100)
    (is (= (counters/value c) 100))
    (counters/clear! c)
    (is (= (counters/value c) 0))))
