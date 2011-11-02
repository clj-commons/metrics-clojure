Histograms
==========

Histograms are used to record the distribution of a piece of data over time.

Creating
--------

Create your histogram::

    (use '[metrics.histograms :only (histogram)])

    (def search-results-returned
      (histogram "search-results-returned"))

You can create a biased histogram by passing an extra boolean argument::

    (def search-results-returned-biased
      (histogram "search-results-returned-biased" true))

Writing
-------

Update the histogram when you have a new value to record::

    (use '[metrics.histograms :only (update!)])

    (update! search-results-returned 10)

Reading
-------

The data of a histogram metrics can be retrived in a bunch of different ways.

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

``number-recorded``
~~~~~~~~~~~~~~~~~~~

To get the number of data points recorded over the entire lifetime of this
histogram::

    (use '[metrics.histograms :only (number-recorded)])

    (number-recorded search-results-returned)
    => 12882

``smallest``
~~~~~~~~~~~~

To get the smallest data point recorded over the entire lifetime of this
histogram::

    (use '[metrics.histograms :only (smallest)])

    (smallest search-results-returned)
    => 4

``largest``
~~~~~~~~~~~

To get the largest data point recorded over the entire lifetime of this
histogram::

    (use '[metrics.histograms :only (largest)])

    (largest search-results-returned)
    => 1345

``mean``
~~~~~~~~

To get the mean of the data points recorded over the entire lifetime of this
histogram::

    (use '[metrics.histograms :only (mean)])

    (mean search-results-returned)
    => 233.12

``std-dev``
~~~~~~~~~~~

To get the standard deviation of the data points recorded over the entire
lifetime of this histogram::

    (use '[metrics.histograms :only (std-dev)])

    (std-dev search-results-returned)
    => 80.2

``sample``
~~~~~~~~~~

You can get the current sample points the histogram is using with ``sample``,
but you almost *certainly* don't care about this.  If you use it make sure you
know what you're doing.

::

    (use '[metrics.histograms :only (sample)])

    (sample search-results-returned)
    => [12 2232 234 122]
