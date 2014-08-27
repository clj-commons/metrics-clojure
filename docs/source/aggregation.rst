Aggregation, Graphing, and Historical Values
============================================

metrics-clojure is a wrapper around metrics, and metrics only stores the current
value of any given metric.  It doesn't keep the values at previous times around
(aside from the one/five/fifteen minute windows for timers and meters).

Not storing historical data is important because it allows metrics to be used in
production without worrying about memory usage growing out of control.

In practice you'll want to not only view these instantaneous values but also
their values over time.  For this you'll need to turn to external utilities.

You can implement a solution yourself as we'll show in the example below, or use
an existing utility that metrics-clojure provides support for.

Sending Metrics to Graphite
---------------------------

Note: You must include ``metrics-clojure-graphite`` in your project.clj.

metrics-clojure supports aggregating metrics to graphite::

    (require '[metrics.reporters.graphite :as graphite])
    (import '[java.util.concurrent.TimeUnit])
    (import '[com.codahale.metrics MetricFilter])

    (def GR (graphite/reporter {:host "your.graphite.host"
                                :prefix "my-api.common.prefix"
                                :rate-unit TimeUnit/SECONDS
                                :duration-unit TimeUnit/MILLISECONDS
                                :filter MetricFilter/ALL}))
    (graphite/start GR 10)

This will tell ``metrics`` to aggregate all metrics to graphite every
ten seconds.

Optional arguments to graphite/reporter are:

- :host
- :port
- :prefix
- :clock
- :rate-unit
- :duration-unit
- :filter

Sending Metrics to Ganglia
--------------------------

**TODO**

Implementing a Simple Graphing Server
-------------------------------------

**TODO**
