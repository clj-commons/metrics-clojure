Histograms
==========

Creating
--------

Create your histogram::

    (use '[metrics.histograms :only (histogram)])

    (def search-results-returned
      (histogram "search-results-returned"))

You can create a biased histogram by passing an extra boolean argument::

    (def search-results-returned-biased
      (histogram "search-results-returned-biased" true))

Reading
-------

Writing
-------

Update the histogram when you have a new value to record::

    (use '[metrics.histograms :only (update!)])

    (update! search-results-returned 10)

