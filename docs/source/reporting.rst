Reporting
=========

Currently ``metrics-clojure`` supports reporting metrics through the
console (on standard error)::

    (use '[metrics.core :only (report-to-console)])

    (report-to-console 10)

This will tell ``metrics`` to print all metrics to the console every ten
seconds.

``metrics-clojure`` also supports [JMX reporting][jmx], since it's built into
``metrics`` itself.

More reporting methods will be added in the future.  Pull requests are welcome.

[jmx]: http://metrics.codahale.com/manual.html#jmx-reporter
