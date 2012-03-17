.. _metric-names:

Metric Names
============

In all the examples we've used a string to name the metric.  The ``metrics``
library actually names metrics with three-part names, sometimes called "group",
"type", and "metric name".

In Java and Scala those names are usually set to the package and class where the
metric is being used.

In Clojure you usually won't have a meaningful class name to record, and the
package name would be a pain to find, so metrics-clojure uses "default" and
"default" for those parts.  This results in a name like
"default.default.my-metric".

If you want to specify something other than "default" you can pass a collection
of three strings instead of a single string::

    (use '[metrics.timers :only (timer)])

    (def response-time
      (timer ["webservice" "views" "response-time"]))

This will result in a name like "webservice.views.response-time".
