Removing Metrics
================

You can remove metrics as long as you know their names::

    (use '[metrics.core :only (remove-metric)])

    (remove-metric "files-served")

You can use the sequence form of :ref:`metric names <metric-names>` here too, of
course::

    (remove-metric ["webservice" "views" "response-time"])

