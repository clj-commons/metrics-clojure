(ns metrics.core
  (:import [com.codahale.metrics MetricRegistry Metric
            UniformReservoir ExponentiallyDecayingReservoir
            SlidingWindowReservoir SlidingTimeWindowReservoir]
           [java.util.concurrent TimeUnit]))

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

(defn meters
  "Returns a map of all the meters in the registry and their names."
  [^MetricRegistry reg]
  (.getMeters reg))

(defn histograms
  "Returns a map of all the histograms in the registry and their names."
  [^MetricRegistry reg]
  (.getHistograms reg))

(defn timers
  "Returns a map of all the timers in the registry and their names."
  [^MetricRegistry reg]
  (.getTimers reg))

(defn gauges
  "Returns a map of all the gauges in the registry and their names."
  [^MetricRegistry reg]
  (.getGauges reg))

(defn counters
  "Returns a map of all the counters in the registry and their names."
  [^MetricRegistry reg]
  (.getCounters reg))

(defn uniform-reservoir
  "Create a uniform reservior, which uses Vitter's Algorithm R to
  produce a statistically representative sample. Default size: 1028."
  ([]
   (UniformReservoir.))
  ([size]
   (UniformReservoir. size)))

(defn exponentially-decaying-reservoir
  "Create an exponentially decaying reservior, which uses Cormode et al's
  forward-decaying priority reservoir sampling method to produce a
  statistically representative sampling reservoir, exponentially biased
  towards newer entries. Default size: 1028, alpha 0.015"
  ([]
   (ExponentiallyDecayingReservoir.))
  ([size alpha]
   (ExponentiallyDecayingReservoir. size alpha)))

(defn sliding-time-window-reservoir
  "Create a reservior backed by a sliding window that stores only the
  measurements made in the last N seconds"
  [window ^TimeUnit unit]
  (SlidingTimeWindowReservoir. window unit))

(defn sliding-window-reservoir
  "Create a reservior backed by a sliding window that stores the last
  N measurements."
  [size]
  (SlidingWindowReservoir. size))
