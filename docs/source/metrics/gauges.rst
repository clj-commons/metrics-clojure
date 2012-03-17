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

Create your gauge::

    (use '[metrics.gauges :only (gauge)])

    (def files-open
      (gauge "files-open"
             (return-number-of-files-open ...)))

That's it.  Pretty simple.

``gauge`` is a macro.  If you need a function instead you can use ``gauge-fn``,
but you have to pass it a function, not just a body::

    (use '[metrics.gauges :only (gauge-fn)])

    (def files-open
      (gauge-fn "files-open"
             #(return-number-of-files-open ...)))

.. _gauges/defgauge:

You can also use the ``defgauge`` macro to create a gauge and bind it to a var
in one concise, easy step::

    (use '[metrics.gauges :only (defgauge)])

    (defgauge files-open
      (return-number-of-files-open ...))

``defgauge`` can take a body of statements like ``gauge`` or a function like
``gauge-fn``.

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

    (use '[metrics.gauges :only (value)])

    (value files-open)
