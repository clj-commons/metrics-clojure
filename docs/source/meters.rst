Meters
======

Meters are metrics that let you "mark" when something happens and tell you how
often it occurs.

Creating
--------

Create your meter::

    (use '[metrics.meters :only (meter)])

    (def files-served (meter "files-served" "files"))

The second argument to ``meter`` is a string describing the "units" for the
meter.  In this example it's "files", as in "18732 files".

Writing
-------

Mark the meter every time the event happens::

    (use '[metrics.meters :only (mark!)])

    (mark! files-served)

Reading
-------

There are a few functions you can use to retrieve the rate at which the metered
events occur.

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

``rate-one``
~~~~~~~~~~~~

If you only care about the rate of events during the last minute you can use
``rate-one``::

    (use '[metrics.meters :only (rate-one)])

    (rate-one files-served)
    => 100.0

``rate-five``
~~~~~~~~~~~~~

If you only care about the rate of events during the last five minutes you can
use ``rate-five``::

    (use '[metrics.meters :only (rate-five)])

    (rate-five files-served)
    => 120.0

``rate-fifteen``
~~~~~~~~~~~~~~~~

If you only care about the rate of events during the last fifteen minutes you
can use ``rate-fifteen``::

    (use '[metrics.meters :only (rate-fifteen)])

    (rate-fifteen files-served)
    => 76.0

``rate-mean``
~~~~~~~~~~~~~

If you really want the mean rate of events over the lifetime of the meter (hint:
you probably don't) you can use ``rate-mean``::

    (use '[metrics.meters :only (rate-mean)])

    (rate-mean files-served)
    => 204.123
