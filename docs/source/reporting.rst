Reporting
=========

Once you've started tracking some metrics there are several ways you can read
their values.

More reporting methods will be added in the future.  Pull requests are welcome.

Console
-------

metrics-clojure supports reporting metrics through the console (on standard
error)::

    (require '[metrics.reporters.console :as console])

    (def CR (console/reporter {}))
    (console/start CR 10)

This will tell ``metrics`` to print all metrics to the console every ten
seconds.

Optional arguments to console/reporter are:

- :stream
- :locale
- :clock
- :rate-unit
- :duration-unit
- :filter

CSV Reporting
-------------

metrics-clojure supports reporting metrics into csv files (one file per metric)::

    (require '[metrics.reporters.csv :as csv])

    (def CR (csv/reporter "/tmp/csv_reporter" {}))
    (csv/start CR 1)

This will tell ``metrics`` to append the most recent value or each
metric (every second), to a file named after the metric, in
``/tmp/csv_reporter``. The directory name is required. The directory
(and parents) will be created if they doesn't exist, it will throw an
exception if it is not writable, or if the given path is not a
directory.

To use this reporter, you may need to sanitize your metric names to
ensure that they are valid filenames for your system.

Optional arguments to csv/reporter are:

- :locale
- :rate-unit
- :duration-unit
- :filter

JMX and ``jvisualvm``
---------------------

metrics-clojure also supports `JMX reporting
<http://metrics.dropwizard.io/3.1.0/manual/core/#jmx>`_, since it's built into
``metrics`` itself.::

    (require '[metrics.reporters.jmx :as jmx])

    (def JR (jmx/reporter {}))
    (jmx/start JR)

This will tell ``metrics`` to make all metrics available via ``JMX`` under ``metrics`` domain.

Once this is done, you can open ``jvisualvm`` (which you probably already have as
it's included in many Java distributions), connect to a process, and view
metrics in the MBeans tab.

Optional arguments to jmx/reporter are:

- :domain
- :rate-unit
- :duration-unit
- :filter

Note that there are options available to the JMX reporter that are not
visible through the Clojure interface. I'm not that familiar with JMX,
and didn't know how to handle things like ObjectNameFactory.

See https://github.com/dropwizard/metrics/blob/master/metrics-core/src/main/java/com/codahale/metrics/JmxReporter.java
for the original code.
