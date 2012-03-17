Meters
======

Meters are metrics that let you "mark" when an event happens and tell you how
often it occurs.

Meters are used for events where the only thing you care about is "this event
happened".

If you need to record a value along with this you probably want a histogram.
For example: "a user performed a search" could be tracked with a meter, but "a
user performed a search *and got N results*" would need a histogram.

Meters can tell you things like:

    Over the past five minutes, an average of 6,500 searches were performed each
    second.

Examples of metrics you might want to track with a meter:

* A user logged in.
* A POST request was received.

**TODO:** More examples.

Creating
--------

Create your meter::

    (use '[metrics.meters :only (meter)])

    (def files-served (meter "files-served" "files"))

The second argument to ``meter`` is a string describing the "units" for the
meter.  In this example it's "files", as in "18732 files".

.. _meters/defmeter:

You can also use the ``defmeter`` macro to create a meter and bind it to a var
in one concise, easy step::

    (use '[metrics.meters :only (defmeter)])

    (defmeter files-served "files")

All the ``def[metric]`` macros do some :ref:`magic <desugaring>` to the metric
title to make it easier to define.

Writing
-------

Once you've got a meter you can mark occurrences of events.

.. _meters/mark!:

``mark!``
~~~~~~~~~

Mark the meter every time the event happens with ``mark!``::

    (use '[metrics.meters :only (mark!)])

    (mark! files-served)

Reading
-------

There are a few functions you can use to retrieve the rate at which the metered
events occur.

.. _meters/rates:

``rates``
~~~~~~~~~

You can get a map containing the mean rates of the event considering the last
one, five, and fifteen minute periods with ``rates``::

    (use '[metrics.meters :only (rates)])

    (rates files-served)
    => { 1 100.0,
         5 120.0,
        15 76.0}

In this example the event happened approximately 100 times per second during the
last one minute period, 120 times per second in the last five minute period, and
76 times per second in the last fifteen minute period.

.. _meters/rate-one:

``rate-one``
~~~~~~~~~~~~

If you only care about the rate of events during the last minute you can use
``rate-one``::

    (use '[metrics.meters :only (rate-one)])

    (rate-one files-served)
    => 100.0

.. _meters/rate-five:

``rate-five``
~~~~~~~~~~~~~

If you only care about the rate of events during the last five minutes you can
use ``rate-five``::

    (use '[metrics.meters :only (rate-five)])

    (rate-five files-served)
    => 120.0

.. _meters/rate-fifteen:

``rate-fifteen``
~~~~~~~~~~~~~~~~

If you only care about the rate of events during the last fifteen minutes you
can use ``rate-fifteen``::

    (use '[metrics.meters :only (rate-fifteen)])

    (rate-fifteen files-served)
    => 76.0

.. _meters/rate-mean:

``rate-mean``
~~~~~~~~~~~~~

If you really want the mean rate of events over the lifetime of the meter (hint:
you probably don't) you can use ``rate-mean``::

    (use '[metrics.meters :only (rate-mean)])

    (rate-mean files-served)
    => 204.123
