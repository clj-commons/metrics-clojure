Counters
========

Counters are integer values you can increment and decrement.

They can be useful for tracking things that you don't have a particular list of,
but you do control when they're added/removed/opened/closed.

For example: it's not necessarily very easy to get a list of all the open HTTP
connections for a web server, but it's often easy to wrap some "middleware" that
increments the "open requests" counter when a request comes in and decrements it
when the response goes out.

They can also be useful for tracking simple "total" values like "total requests
served in this app's entire lifetime".

Examples of metrics you might want to track with a counter:

* Number of http requests currently being processed.
* Total number of requests/responses received/sent.

**TODO:** More examples.

Creating
--------

Create your counter::

    (require '[metrics.core :refer [new-registry]])
    (require '[metrics.counters :refer [counter]])

    (def reg (new-registry))
    (def users-connected (counter reg "users-connected"))

The ``counter`` function is idempotent, which means that you don't
need to keep a local reference to the counter. Once a counter has been
registered, a call to ``(counter reg "users-connected")`` will return
the existing counter.

.. _counters/defcounter:

You can also use the ``defcounter`` macro to create a counter and bind it to a var
in one concise, easy step::

    (require '[metrics.counters :refer [defcounter]])

    (defcounter reg users-connected)

All the ``def[metric]`` macros do some :ref:`magic <desugaring>` to the metric
title to make it easier to define.

Writing
-------

Once you have a counter you can increment it and decrement it.

.. _counters/inc!:

``inc!``
~~~~~~~~

Increment counters with ``inc!``.  You can pass a number to increment it by, or
omit it to increment by 1::

    (require '[metrics.counters :refer [inc!]])

    (inc! users-connected)
    (inc! users-connected 2)

Or if you haven't held a reference to ``users-connected``, you can do the following::

    (inc! (counter reg "users-connected"))
    (inc! (counter reg "users-connected") 2)

.. _counters/dec!:

``dec!``
~~~~~~~~

Decrement counters with ``dec!``.  You can pass a number to decrement it by, or
omit it to decrement by 1::

    (require '[metrics.counters :refer [dec!]])

    (dec! users-connected)
    (dec! users-connected 2)

Or if you haven't held a reference to ``users-connected``, you can do the following::

    (dec! (counter reg "users-connected"))
    (dec! (counter reg "users-connected") 2)

Reading
-------

There's only one way to get data from a counter.

.. _counters/value:

``value``
~~~~~~~~~

You can get the current value of a counter with ``value``::

    (require '[metrics.counters :refer [value]])

    (value users-connected)

Or if you haven't held a reference to ``users-connected``, you can do the following::

    (value (counter reg "users-connected"))

The counter will be created and return the default value if it hasn't
been registered before.
