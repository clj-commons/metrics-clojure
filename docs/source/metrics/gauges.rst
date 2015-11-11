Gauges
======

Gauges are used to measure the instantaneous value of something.

They're often useful for things that aren't events, like "users currently in the
database".  If you can perform a query of some kind at any time to get a value,
it's probably something suitable for a gauge.

Examples of metrics you might want to track with a gauge:

* Number of users in your database.

**TODO:** More examples.

Creating
--------

Create your gauge using ``gauge-fn``::
but you have to pass it a function, not just a body::

    (require '[metrics.gauges :refer [gauge-fn gauge]])

    (def files-open
      (gauge-fn "files-open"
             #(return-number-of-files-open ...)))

Once a gauge has been registered, a call to ``(gauge "files-open")`` will
return the existing gauge.

.. _gauges/defgauge:

You can also use the ``defgauge`` macro to create a gauge and bind it to a var
in one concise, easy step::

    (require '[metrics.gauges :refer [defgauge]])

    (defgauge files-open
      (fn []
        (return-number-of-files-open ...)))

``defgauge`` takes a function like ``gauge-fn``.

All the ``def[metric]`` macros do some :ref:`magic <desugaring>` to the metric
title to make it easier to define.

Writing
-------

With gauges there is no writing.  Gauges execute the form(s) (or function) you
passed when creating them every time they're read.  You don't need to do
anything else.

Reading
-------

There's only one way to get data from a gauge.

.. _gauges/value:

``value``
~~~~~~~~~

You can read the value of a gauge at any time with ``value``::

    (require '[metrics.gauges :refer [value]])

    (value files-open)
