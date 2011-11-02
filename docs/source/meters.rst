Meters
======

Creating
--------

Create your meter::

    (use '[metrics.meters :only (meter)])

    (def files-served (meter "files-served" "files"))

The second argument to `meter` is a string describing the "units" for the meter.
In this example it's "files", as in "18732 files".

Reading
-------

Writing
-------

Mark the meter every time the event happens::

    (use '[metrics.meters :only (mark!)])

    (mark! files-served)
