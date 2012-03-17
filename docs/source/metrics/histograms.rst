Histograms
==========

Histograms are used to record the distribution of a piece of data over time.

They're used when you have a type of data for which the following are true:

* There are distinct "events" for this type of data, such as "user performs
  a search and we return N results".
* Each event has a numeric value (the "N results" in our example).
* Comparisons of these numeric values are meaningful.

For example: HTTP status codes do *not* fit this because comparisons between the
numeric values are not meaingful.  The fact that 404 happens to be less than 500
doesn't tell you anything.

Contrast this with something like "search results returned": one value being
less than the other tells you something meaningful about the data.

Histograms can tell you things like:

    75% of all searches returned 100 or fewer results, while 95% got 200 or
    fewer.

If the numeric value you're recording is the amount of time taken to do
something, you probably want a timer instead of a histogram.

Examples of metrics you might want to track with a histogram:

* Search results returned ("99% of searches returned 300 or fewer results").
* Response body size ("75% of responses were 30kb or smaller").

**TODO:** More examples.

Creating
--------

Create your histogram::

    (use '[metrics.histograms :only (histogram)])

    (def search-results-returned
      (histogram "search-results-returned"))

You can create an unbiased histogram by passing an extra boolean argument
(though you probably don't want to)::

    (def search-results-returned-biased
      (histogram "search-results-returned-unbiased" false))

.. _histograms/defhistogram:

You can also use the ``defhistogram`` macro to create a histogram and bind it to
a var in one concise, easy step::

    (use '[metrics.histograms :only (defhistogram)])

    (defhistogram search-results-returned)

All the ``def[metric]`` macros do some :ref:`magic <desugaring>` to the metric
title to make it easier to define.

Writing
-------

Once you've got a histogram you can update it with the numeric values of events
as they occur.

.. _histograms/update!:

``update!``
~~~~~~~~~~~

Update the histogram when you have a new value to record with ``update!``::

    (use '[metrics.histograms :only (update!)])

    (update! search-results-returned 10)

Reading
-------

The data of a histogram metrics can be retrived in a bunch of different ways.

.. _histograms/percentiles:

``percentiles``
~~~~~~~~~~~~~~~

The function you'll usually want to use to pull data from a histogram is
``percentiles``::

    (use '[metrics.histograms :only (percentiles)])

    (percentiles search-results-returned)
    => { 0.75   180
         0.95   299
         0.99   300
         0.999  340
         1.0   1345 }

This returns a map of the percentiles you probably care about.  The keys are the
percentiles (doubles between 0 and 1 inclusive) and the values are the maximum
value for that percentile.  In this example:

* 75% of searches returned 180 or fewer results.
* 95% of searches returned 299 or fewer results.
* ... etc.

If you want a different set of percentiles just pass them as a sequence::

    (use '[metrics.histograms :only (percentiles)])

    (percentiles search-results-returned [0.50 0.75])
    => { 0.50 100
         0.75 180 }

.. _histograms/number-recorded:

``number-recorded``
~~~~~~~~~~~~~~~~~~~

To get the number of data points recorded over the entire lifetime of this
histogram::

    (use '[metrics.histograms :only (number-recorded)])

    (number-recorded search-results-returned)
    => 12882

.. _histograms/smallest:

``smallest``
~~~~~~~~~~~~

To get the smallest data point recorded over the entire lifetime of this
histogram::

    (use '[metrics.histograms :only (smallest)])

    (smallest search-results-returned)
    => 4

.. _histograms/largest:

``largest``
~~~~~~~~~~~

To get the largest data point recorded over the entire lifetime of this
histogram::

    (use '[metrics.histograms :only (largest)])

    (largest search-results-returned)
    => 1345

.. _histograms/mean:

``mean``
~~~~~~~~

To get the mean of the data points recorded over the entire lifetime of this
histogram::

    (use '[metrics.histograms :only (mean)])

    (mean search-results-returned)
    => 233.12

.. _histograms/std-dev:

``std-dev``
~~~~~~~~~~~

To get the standard deviation of the data points recorded over the entire
lifetime of this histogram::

    (use '[metrics.histograms :only (std-dev)])

    (std-dev search-results-returned)
    => 80.2

.. _histograms/sample:

``sample``
~~~~~~~~~~

You can get the current sample points the histogram is using with ``sample``,
but you almost *certainly* don't care about this.  If you use it make sure you
know what you're doing.

::

    (use '[metrics.histograms :only (sample)])

    (sample search-results-returned)
    => [12 2232 234 122]
