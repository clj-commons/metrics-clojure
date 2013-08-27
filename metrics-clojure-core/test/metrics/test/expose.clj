(ns metrics.test.expose
  (:require [metrics.core :as core]
            [metrics.counters :as counters]
            [metrics.expose :as expose])
  (:use metrics.test.test-utils
        clojure.test))


(deftest test-exposing-as-clj-data-and-json
  (core/remove-all-metrics)
  (counters/defcounter clicks)
  (counters/inc! clicks)
  (is (= {"default.default.clicks" {:type :counter, :value 1}}
         (expose/clojure-data-metrics))))

