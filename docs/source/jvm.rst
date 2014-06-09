Extras for JVM
===============

metrics-clojure contains some functions for instrumenting JVM metrics

Installation
------------

The extra JVM-related functionality is in a separate ``metrics-clojure-jvm``
library so it's installation is optional.

To install it, add this to your ``project.clj``'s dependencies::

    [metrics-clojure-jvm "0.1.0-SNAPSHOT"]


Instrumenting the JVM
------------------------

The simplest way to add JVM metrics to your application is to simply call the ``instrument-jvm``
function in your code::

    (require '[metrics.jvm.core :refer [instrument-jvm]])

    (instrument-jvm metric-registry)

This will add a number of metrics, listed below.

``jvm.attribute``
~~~~~~~~~~~~~~~~~~~

A set of gauges for the JVM name, vendor and uptime.

``jvm.memory``
~~~~~~~~~~~~~~~~~~~

A set of gauges for JVM memory usage, include stats on
heap vs non-heap memory, plus GC-specific memory pools.

``jvm.file``
~~~~~~~~~~~~~~~~~~~

A gauge for the ratio of used to total file descriptors.

``jvm.gc``
~~~~~~~~~~~~~~~~~~~

A set of gauges for the counts and elapsed times of garbage collection.

``jvm.thread``
~~~~~~~~~~~~~~~~~~~

A set of gauges for the number of threads in their various states and deadlock detection.


If you want to add the individual gauge/metric sets that codahale's metrics-jvm library provides,
then the following functions are available::

    (require '[metrics.jvm.core :as jvm])

    (register-jvm-attribute-gauge-set metric-registry)

    (register-memory-usage-gauge-set metric-registry)

    (register-file-descriptor-ratio-gauge-set metric-registry)
    
    (register-garbage-collector-metric-set metric-registry)
    
    (register-thread-state-gauge-set metric-registry)

These functions also take an optional second argument
should you wish to override the metric prefix, e.g.::

    (register-jvm-attribute-gauge-set metric-registry ["my" "preferred" "prefix"])


