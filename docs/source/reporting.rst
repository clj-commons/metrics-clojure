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

JMX and ``jvisualvm``
---------------------

metrics-clojure also supports `JMX reporting
<http://metrics.codahale.com/manual.html#jmx-reporter>`_, since it's built into
``metrics`` itself.

In a nutshell: you can open ``jvisualvm`` (which you probably already have as
it's included in many Java distributions), connect to a process, and view
metrics in the MBeans tab.
