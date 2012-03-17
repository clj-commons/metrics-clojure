(ns metrics.test.histograms
  (:require [metrics.histograms :as histograms])
  (:use [metrics.test.test-utils])
  (:use [clojure.test]))


(deftest test-mean
  (let [h (histograms/histogram ["test" "histograms" "test-mean"])]
    (is (= (histograms/mean h) 0.0))
    (histograms/update! h 5)
    (is (= (histograms/mean h) 5.0))
    (histograms/update! h 15)
    (is (= (histograms/mean h) 10.0))))

(deftest test-number-recorded
  (let [h (histograms/histogram ["test" "histograms" "test-number-recorded"])]
    (is (= (histograms/number-recorded h) 0))
    (histograms/update! h 0)
    (is (= (histograms/number-recorded h) 1))
    (histograms/update! h 0)
    (is (= (histograms/number-recorded h) 2))))

(deftest test-std-dev
  (let [h (histograms/histogram ["test" "histograms" "test-std-dev"])]
    (is (= (histograms/std-dev h) 0.0))
    (histograms/update! h 2)
    (histograms/update! h 4)
    (histograms/update! h 6)
    (is (= (histograms/std-dev h) 2.0))))

(deftest test-smallest
  (let [h (histograms/histogram ["test" "histograms" "test-smallest"])]
    (is (= (histograms/smallest h) 0.0))
    (histograms/update! h 1)
    (histograms/update! h 9)
    (histograms/update! h 3)
    (is (= (histograms/smallest h) 1.0))))

(deftest test-largest
  (let [h (histograms/histogram ["test" "histograms" "test-largest"])]
    (is (= (histograms/largest h) 0.0))
    (histograms/update! h 1)
    (histograms/update! h 9)
    (histograms/update! h 3)
    (is (= (histograms/largest h) 9.0))))

(deftest test-percentiles
  (let [h (histograms/histogram ["test" "histograms" "test-percentiles"])]
    (is (maps-within-one (histograms/percentiles h)
                    {0.75 0, 0.95 0, 0.99 0, 0.999 0, 1.00 0}))

    (dorun (map (partial histograms/update! h) (range 1 101)))

    (is (maps-within-one (histograms/percentiles h)
                    {0.75 75, 0.95 95, 0.99 99, 0.999 100, 1.00 100}))

    (is (maps-within-one (histograms/percentiles h [0.10 0.50])
                    {0.10 10, 0.50 50}))))

(deftest test-clear
  (let [h (histograms/histogram ["test" "histograms" "test-clear"])]
    (is (= (histograms/mean h) 0.0))
    (histograms/update! h 5)
    (is (= (histograms/mean h) 5.0))
    (histograms/clear! h)
    (is (= (histograms/mean h) 0.0))))

