(ns metrics.utils
  (:import com.yammer.metrics.Metrics
           [com.yammer.metrics.core Sampling MetricName]))


(defn get-percentile [^Sampling metric ^double percentile]
  (-> metric
    .getSnapshot
    (.getValue percentile)))

(defn get-percentiles [metric percentiles]
  (zipmap percentiles
          (map (partial get-percentile metric)
               percentiles)))

(defn ^MetricName metric-name [title]
  (if (string? title)
    (new MetricName "default" "default" ^String title)
    (new MetricName
         ^String (first title)
         ^String (second title)
         ^String (last title))))

(defn all-metrics []
  (letfn [(parse-name [^MetricName metric-name]
            (str (.getGroup metric-name)
                 "." (.getType metric-name)
                 "." (.getName metric-name)))
          (parse-entry [[metric-name metric]]
            [(parse-name metric-name)
             metric])]
    (into {} (map parse-entry (.allMetrics (Metrics/defaultRegistry))))))

(defn desugared-title
  "Syntactic sugary goodness for defining metrics concisely with macros.

  Returns a vector of: [symbol-to-define MetricName]"
  [mn]
  (cond
    (string? mn) [(symbol mn) mn]
    (symbol? mn) [mn (str mn)]
    :else [(symbol (last mn))
           (map str mn)]))
