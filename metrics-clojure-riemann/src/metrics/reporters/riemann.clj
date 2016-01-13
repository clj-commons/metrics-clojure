(ns metrics.reporters.riemann
  (:require [metrics.core :refer [default-registry]]
            [com.stuartsierra.component :as component])
  (:import [java.util.concurrent TimeUnit]
           [com.codahale.metrics.riemann Riemann RiemannReporter RiemannReporter$Builder]
           [com.aphyr.riemann.client IRiemannClient]))

(defn make-riemann
  ([riemann-client]
   (Riemann. riemann-client))
  ([host port]
   (Riemann. host (int port)))
  ([host port batch-size]
   (Riemann. host (int port) (int batch-size))))

(defn reporter
  "Create a reporter that forwards events to a Riemann instance.

  riemann is either an instance of Riemann, as created by
  make-riemann, or an implementation of IRiemannClient. 
  opts is a map with the following optional keys:
    :clock         - Clock instance to use for time
    :prefix        - String to prefix all metrics with
    :rate-unit     - TimeUnit to convert rates to
    :duration-unit - TimeUnit to convert durations to
    :filter        - MetricFilter for filtering reported metrics
    :ttl           - Time to live for reported metrics
    :separator     - Separator between metric name components
    :host-name     - Override source host name
    :tags          - collection of tags to attach to event"
  ([riemann opts]
   (reporter riemann default-registry opts))
  ([riemann registry {:keys [clock
                             prefix
                             rate-unit
                             duration-unit
                             filter
                             ttl
                             separator
                             host-name
                             tags]
                      :as opts}]
   (let [builder (cond-> (RiemannReporter/forRegistry registry)
                   clock (.withClock clock)
                   prefix (.prefixedWith prefix)
                   rate-unit (.convertRatesTo rate-unit)
                   duration-unit (.convertDurationsTo duration-unit)
                   filter (.filter filter )
                   ttl (.withTtl (float ttl))
                   separator (.useSeparator separator)
                   host-name (.localHost host-name)
                   tags (.tags tags))
         riemann (if (instance? IRiemannClient riemann)
                   (make-riemann riemann)
                   riemann)]
     (.build builder riemann))))

(defn start
  ([^RiemannReporter r seconds]
   (start r seconds TimeUnit/SECONDS))
  ([^RiemannReporter r period time-unit]
   (.start r period time-unit)))

(defn stop
  [^RiemannReporter r]
  (.stop r))
