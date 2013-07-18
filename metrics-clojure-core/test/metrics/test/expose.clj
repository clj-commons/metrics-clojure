(ns metrics.test.expose
  (:require [metrics.counters :as counters]
            [metrics.expose :as expose])
  (:use metrics.test.test-utils
        clojure.test))


(deftest test-exposing-as-clj-data-and-json
  (counters/defcounter clicks)
  (counters/inc! clicks)

  (is (= "{\"default.default.beeps\":{\"type\":\"counter\",\"value\":6},\"default.default.clicks\":{\"type\":\"counter\",\"value\":1}}"
         (expose/json-metrics)))
  (is (= {"default.default.beeps" {:type :counter, :value 6},
          "default.default.clicks" {:type :counter, :value 1}}
         (expose/clojure-data-metrics))))

