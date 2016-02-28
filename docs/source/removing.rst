Removing Metrics
================

You can remove metrics as long as you know their names::

    (require '[metrics.core :refer [remove-metric]])

    (remove-metric "files-served")

You can use the sequence form of :ref:`metric names <metric-names>` here too, of
course::

    (remove-metric ["webservice" "views" "response-time"])

You can also remove a bunch of metrics by using a predicate::

    (remove-metrics #(= "webservice" (first %)))
