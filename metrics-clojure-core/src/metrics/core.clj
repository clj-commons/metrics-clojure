(ns metrics.core
  (:import [com.codahale.metrics MetricRegistry]))

(def ^{:tag "MetricRegistry" :doc "Default registry used by public API functions when no explicit registry argument is given"}
  default-registry
  (MetricRegistry.))

(defn ^String metric-name
  [title]
  (if (string? title)
    (MetricRegistry/name "default"
                         ^"[Ljava.lang.String;" (into-array String ["default" ^String title]))
    (MetricRegistry/name
     ^String (first title)
     ^"[Ljava.lang.String;" (into-array String
                                        [(second title)
                                         (last title)]))))

(defn remove-metric
  "Remove the metric with the given title."
  ([title]
     (remove-metric default-registry title))
  ([^MetricRegistry reg title]
     (.remove reg (metric-name title))))
