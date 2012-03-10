(ns metrics.utils
  (:import (com.yammer.metrics Metrics))
  (:import (com.yammer.metrics.core Sampling MetricName)))


(defn get-percentile [^Sampling metric ^double percentile]
  (-> metric
    .getSnapshot
    (.getValue percentile)))

(defn get-percentiles [metric percentiles]
  (zipmap percentiles
           (map (partial get-percentile metric)
                percentiles)))

(defn metric-name ^MetricName [title]
  (if (string? title)
    (new MetricName "default" "default" ^String title)
    (new MetricName
         ^String (first title)
         ^String (second title)
         ^String (last title))))

(defn all-metrics []
  (letfn [(parse-name [metric-name]
            (str (.getGroup metric-name)
                 "." (.getType metric-name)
                 "." (.getName metric-name)))
          (parse-entry [[metric-name metric]]
            [(parse-name metric-name)
             metric])]
    (into {} (map parse-entry (.allMetrics (Metrics/defaultRegistry))))))
