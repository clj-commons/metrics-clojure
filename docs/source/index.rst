metrics-clojure
===============

`metrics-clojure <http://github.com/sjl/metrics-clojure>`_ is a thin Clojure
façade around Coda Hale's wonderful `metrics
<http://github.com/codahale/metrics/>`_ library.

**metrics-clojure is currently being built.  A lot of things work, but don't
complain if things change in backwards-incompatible ways.  Once it hits 1.0.0
it'll be stable, but until then use it at your own risk.**

``metrics-clojure`` is a thin wrapper around metrics, so if you don't know what
any of the words in this documentaion mean you should probably read the `metrics
documentation <https://github.com/codahale/metrics/tree/development/docs>`_
and/or watch the `talk
<http://pivotallabs.com/talks/139-metrics-metrics-everywhere>`_.

* Source (Mercurial): http://bitbucket.org/sjl/metrics-clojure/
* Source (Git): http://github.com/sjl/metrics-clojure/
* Documentation: http://metrics-clojure.rtfd.org/
* Issues: http://github.com/sjl/metrics-clojure/issues/

Table of Contents
-----------------

.. toctree::
   :maxdepth: 2

   installation
   counters
   gauges
   meters
   histograms
   timers
   reporting
   names
   removing
   sideeffects
