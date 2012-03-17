Timers
======

Timers record the time it takes to do things.  They're a bit like histograms
where the value being recorded is time.

Timers should be a fairly intuitive concept.  They can tell you things like:

    75% of all searches took 0.5 seconds or less.  95% of all searches took 1.0
    seconds or less.

Timers also track the rate of the timed events, so it's like they have a meter
metric built-in for convenience.

Creating
--------

Create your timer::

    (use '[metrics.timers :only (timer)])

    (def image-processing-time (timer "image-processing-time"))

.. _timers/deftimer:

You can also use the ``deftimer`` macro to create a timer and bind it to a var
in one concise, easy step::

    (use '[metrics.timers :only (deftimer)])

    (deftimer image-processing-time)

All the ``def[metric]`` macros do some :ref:`magic <desugaring>` to the metric
title to make it easier to define.

Writing
-------

Once you have a timer you can record times to it in two different ways.

.. _timers/time!:

``time!``
~~~~~~~~~

You can record the time it takes to evaluate one or more expressions with the ``time!`` macro::

    (use '[metrics.timers :only (time!)])

    (time! image-processing-time
           (process-image-part-1 ...)
           (process-image-part-2 ...))

.. _timers/time-fn!:

``time-fn!``
~~~~~~~~~~~~

``time!`` is a macro.  If you need a function instead (e.g.: for ``map``'ing
over a list), you can use ``time-fn!``, but you'll need to pass it a function
instead of a body::

    (use '[metrics.timers :only (time-fn!)])

    (time-fn! image-processing-time
              (fn []
                (process-image-part-1 ...)
                (process-image-part-2 ...)))

Reading
-------

.. _timers/percentiles:

``percentiles``
~~~~~~~~~~~~~~~

You can use ``percentiles`` to find the percentage of actions that take less
than or equal to a certain amount of time::

    (use '[metrics.timers :only (percentiles)])

    (percentiles image-processing-time)
    => { 0.75  232.00
         0.95  240.23
         0.99  280.01
         0.999 400.232
         1.0   903.1 }

This returns a map of the percentiles you probably care about.  The keys are the
percentiles (doubles between 0 and 1 inclusive) and the values are the maximum
time taken for that percentile.  In this example:

* 75% of images were processed in 232.00 milliseconds or less.
* 95% of images were processed in 240.23 milliseconds or less.
* ... etc.

If you want a different set of percentiles just pass them as a sequence::

    (use '[metrics.timers :only (percentiles)])

    (percentiles image-processing-time [0.50 0.75])
    => { 0.50 182.11
         0.75 232.00 }

.. _timers/number-recorded:

``number-recorded``
~~~~~~~~~~~~~~~~~~~

To get the number of data points recorded over the entire lifetime of this
timers::

    (use '[metrics.timers :only (number-recorded)])

    (number-recorded image-processing-time)
    => 12882

.. _timers/smallest:

``smallest``
~~~~~~~~~~~~

To get the smallest data point recorded over the entire lifetime of this
timer::

    (use '[metrics.timers :only (smallest)])

    (smallest image-processing-time)
    => 80.66

.. _timers/largest:

``largest``
~~~~~~~~~~~

To get the largest data point recorded over the entire lifetime of this
timer::

    (use '[metrics.timers :only (largest)])

    (largest image-processing-time)
    => 903.1

.. _timers/mean:

``mean``
~~~~~~~~

To get the mean of the data points recorded over the entire lifetime of this
timer::

    (use '[metrics.timers :only (mean)])

    (mean image-processing-time)
    => 433.12

.. _timers/std-dev:

``std-dev``
~~~~~~~~~~~

To get the standard deviation of the data points recorded over the entire
lifetime of this timer::

    (use '[metrics.histograms :only (std-dev)])

    (std-dev image-processing-time)
    => 300.51

.. _timers/sample:

``sample``
~~~~~~~~~~

You can get the current sample points the timer is using with ``sample``, but
you almost *certainly* don't care about this.  If you use it make sure you know
what you're doing.

::

    (use '[metrics.timers :only (sample)])

    (sample image-processing-time)
    => [803.234 102.223 ...]


TODO: Rates
~~~~~~~~~~~
