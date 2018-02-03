(ns metrics.test.histograms-test
  (:require [metrics.core :as mc]
            [metrics.histograms :as mh]
            [metrics.test.test-utils :refer :all]
            [clojure.test :refer :all])
  (:import [java.util.concurrent TimeUnit]))


(let [reg (mc/new-registry)]
  (mh/defhistogram reg ["test" "histograms" "defhistogrammed"])

  (deftest test-defhistogram
  (is (= (mh/number-recorded defhistogrammed) 0))
  (mh/update! defhistogrammed 0)
  (is (= (mh/number-recorded defhistogrammed) 1))
  (mh/update! defhistogrammed 0)
  (is (= (mh/number-recorded defhistogrammed) 2))))

(deftest test-mean
  (let [reg (mc/new-registry)
        h   (mh/histogram reg ["test" "histograms" "test-mean"])]
    (is (= (mh/mean h) 0.0))
    (mh/update! h 5)
    (is (= (mh/mean h) 5.0))
    (mh/update! h 15)
    (is (= (mh/mean h) 10.0))))

(deftest test-number-recorded
  (let [reg (mc/new-registry)
        h   (mh/histogram reg ["test" "histograms" "test-number-recorded"])]
    (is (= (mh/number-recorded h) 0))
    (mh/update! h 0)
    (is (= (mh/number-recorded h) 1))
    (mh/update! h 0)
    (is (= (mh/number-recorded h) 2))))

(deftest test-std-dev
  (let [reg (mc/new-registry)
        h   (mh/histogram reg ["test" "histograms" "test-std-dev"])]
    (is (= (mh/std-dev h) 0.0))
    (mh/update! h 2)
    (mh/update! h 4)
    (mh/update! h 6)
    (is (> (mh/std-dev h) 1.63))))

(deftest test-smallest
  (let [reg (mc/new-registry)
        h   (mh/histogram reg ["test" "histograms" "test-smallest"])]
    (is (= (mh/smallest h) 0))
    (mh/update! h 1)
    (mh/update! h 9)
    (mh/update! h 3)
    (is (= (mh/smallest h) 1))))

(deftest test-largest
  (let [reg (mc/new-registry)
        h   (mh/histogram reg ["test" "histograms" "test-largest"])]
    (is (= (mh/largest h) 0))
    (mh/update! h 1)
    (mh/update! h 9)
    (mh/update! h 3)
    (is (= (mh/largest h) 9))))

(deftest test-percentiles
  (let [reg (mc/new-registry)
        h   (mh/histogram reg ["test" "histograms" "test-percentiles"])]
    (dorun (map (partial mh/update! h) (range 1 101)))
    (is (maps-within-one (mh/percentiles h)
                         {0.5 50 0.75 75, 0.95 95, 0.99 99, 0.999 100, 1.00 100}))
    (is (maps-within-one (mh/percentiles h [0.10 0.50])
                         {0.10 10, 0.50 50}))))

(deftest test-histogram-with-reservoir
  (let [r (mc/new-registry)
        s (mc/sliding-time-window-reservoir 10 TimeUnit/MINUTES)]
    (mh/histogram-with-reservoir r s "histo")
    (try
      (mh/histogram-with-reservoir r s "histo")
      (is false)
      (catch IllegalArgumentException _
        (is true)))
    (is (some? (mh/histogram r "histo")))))
