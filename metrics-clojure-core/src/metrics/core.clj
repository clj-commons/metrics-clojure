(ns metrics.core
  (:import [com.codahale.metrics MetricRegistry Metric]))

(def ^{:tag MetricRegistry :doc "Default registry used by public API functions when no explicit registry argument is given"}
  default-registry
  (MetricRegistry.))

(defn ^com.codahale.metrics.MetricRegistry new-registry
  []
  (MetricRegistry.))

(defn ^String metric-name
  [title]
  (if (string? title)
    (MetricRegistry/name "default"
                         ^"[Ljava.lang.String;" (into-array String ["default" ^String title]))
    (MetricRegistry/name
     ^String (first title)
     ^"[Ljava.lang.String;" (into-array String
                                        (if (= 3 (count title))
                                          [(second title) (last title)]
                                          (rest title))))))

(defn add-metric
  "Add a metric with the given title."
  ([title ^Metric metric]
   (add-metric default-registry title metric))
  ([^MetricRegistry reg title ^Metric metric]
   (.register reg (metric-name title) metric)))

(defn remove-metric
  "Remove the metric with the given title."
  ([title]
   (remove-metric default-registry title))
  ([^MetricRegistry reg title]
   (.remove reg (metric-name title))))

(defn remove-metrics
  "Remove all the metrics matching the given predicate in the given
  repository, or the default registry if no registry given. The
  predicate takes one argument, the name of the metric."
  ([pred] (remove-metrics default-registry pred))
  ([^MetricRegistry reg pred]
   (doseq [metric (.getNames reg)]
     (when (pred metric) (.remove reg metric)))))

(defn remove-all-metrics
  "Remove all the metrics in the given registry, or the default
  registry if no registry given."
  ([] (remove-all-metrics default-registry))
  ([^MetricRegistry reg]
   (remove-metrics reg (constantly true))))

(defn replace-metric
  "Replace a metric with the given title."
  ([title ^Metric metric]
   (replace-metric default-registry title metric))
  ([^MetricRegistry reg title ^Metric metric]
   (remove-metric reg (metric-name title))
   (add-metric reg (metric-name title) metric)))
