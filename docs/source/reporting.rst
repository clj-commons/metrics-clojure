Reporting
=========

Once you've started tracking some metrics there are several ways you can read
their values.

More reporting methods will be added in the future.  Pull requests are welcome.

Console
-------

metrics-clojure supports reporting metrics through the console (on standard
error)::

    (use '[metrics.core :only (report-to-console)])

    (report-to-console 10)

This will tell ``metrics`` to print all metrics to the console every ten
seconds.

Ganglia
-------

The optional metrics-clojure-ganglia module adds supports to stream metrics to a Ganglia server::

    (use '[metrics.ganglia-reporter])

    (report-to-ganglia 10 "ganglia.example.com" "8649")

    ; prefixes all ganglia metrics with "myapp-metrics"
    (report-to-ganglia 10 "ganglia.example.com" "8649" "myapp-metrics")

Both lines will stream metrics to the Ganglia server located at the specified host and port every 10 minutes.

Graphite
-------

The optional metrics-clojure-graphite module adds supports to stream metrics to a Graphite server::

    (use '[metrics.graphite-reporter])

    (report-to-graphite 10 "graphite.example.com" "2003")

    ; prefixes all graphite metrics with "myapp-metrics"
    (report-to-graphite 10 "graphite.example.com" "2003" "myapp-metrics")

Both lines will stream metrics to the Graphite server located at the specified host and port every 10 minutes.

JMX and ``jvisualvm``
---------------------

metrics-clojure also supports `JMX reporting
<http://metrics.codahale.com/manual.html#jmx-reporter>`_, since it's built into
``metrics`` itself.

In a nutshell: you can open ``jvisualvm`` (which you probably already have as
it's included in many Java distributions), connect to a process, and view
metrics in the MBeans tab.
