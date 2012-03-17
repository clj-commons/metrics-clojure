Counters
========

Counters are values you can increment and decrement.

Creating
--------

Create your counter::

    (use '[metrics.counters :only (counter)])

    (def users-connected (counter "users-connected"))

.. _defcounter:

You can also use the ``defcounter`` macro to create a counter and bind it to a var
in one concise, easy step::

    (use '[metrics.counters :only (defcounter)])

    (defcounter users-connected)

All the ``def[metric]`` macros do some :ref:`magic <desugaring>` to the metric
title to make it easier to define.

Writing
-------

Now increment and decrement it::

    (use '[metrics.counters :only (inc! dec!)])

    (inc! users-connected)
    (inc! users-connected 2)

    (dec! users-connected)
    (dec! users-connected 2)

Reading
-------

There's only one way to get data from a counter.

``value``
~~~~~~~~~

You can get the current value of a counter with ``value``::

    (use '[metrics.counters :only (value)])

    (value users-connected)
