(ns metrics.test.utils-test
  (:require [metrics.utils :as utils])
  (:use [clojure.test]))

(deftest test-desugared-title
  (let [[s title] (utils/desugared-title "foo")]
    (is (= s 'foo))
    (is (= title "foo")))
  (let [[s title] (utils/desugared-title 'foo)]
    (is (= s 'foo))
    (is (= title "foo")))
  (dorun (for [test-title [["a" "b" "c"]
                           ['a  'b  'c]
                           ["a" 'b  'c]
                           ["a" "b" 'c]
                           ['a  'b  "c"]]]
           (let [[s title] (utils/desugared-title test-title)]
             (is (= s 'c))
             (is (= title ["a" "b" "c"]))))))
