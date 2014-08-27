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

JMX and ``jvisualvm``
---------------------

metrics-clojure also supports `JMX reporting
<http://metrics.codahale.com/manual.html#jmx-reporter>`_, since it's built into
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
