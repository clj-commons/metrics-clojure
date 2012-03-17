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

.. _desugaring:

Title Desugaring by the ``def[metric]`` Macros
----------------------------------------------

All of the ``def[metric]`` macros (:ref:`defcounter <counters/defcounter>`,
:ref:`defgauge <gauges/defgauge>`, :ref:`defhistogram
<histograms/defhistogram>`, :ref:`defmeter <meters/defmeter>`, and
:ref:`deftimer <timers/deftimer>`) take a title as their first argument.

All of the macros will use this title as a symbol when binding it to a var, and
will convert it to a string to use as the name of the metric.

For example, this::

    (defmeter post-requests)

is equivalent to::

    (def post-requests (meter "post-requests"))

If you want to define a metric with a name outside of the default group/type,
you can use a vector of three strings and/or symbols instead of a bare symbol.
The last entry in the vector will be used as the symbol for the var (and will be
coerced if necessary). For example, all of these::

    (defcounter ["mysite.http" api post-requests])
    (defcounter ["mysite.http" "api" post-requests])
    (defcounter ["mysite.http" "api" "post-requests"])

are equivalent to::

    (def post-requests (meter ["mysite.http" "api" "post-requests"]))

If you need more control than this (e.g.: if you want a var named differently
than the last segment of the metric name) you should use the normal creation
methods.  These macros are only a bit of syntactic sugar to reduce typing.
