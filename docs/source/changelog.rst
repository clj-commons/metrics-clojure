Changelog
=========

As of version ``1.0.0`` metrics-clojure is stable.  New features will still be
added, but backwards-incompatible changes should be very rare.

We use `semantic versioning <http://semver.org/>`_ to decide how to number
versions, so you know what's going to happen when you upgrade.

2.3.0
-----

- Updated graphite reporter api to bring it in line with the console reporter
- Updated docs for console reporter and graphite reporter.

2.0.0
-----

Metrics Registries
~~~~~~~~~~~~~~~~~~

`metrics-clojure` 1.x maintained a metrics registry in a dynamic var.
This approach makes the library a little easier for beginners but
also much harder to use in more sophisticated cases, e.g. in concurrent
applications or those that use a Component-like approach to
program structure.

As such, `metrics-clojure` 2.0+ makes metrics registry a required
explicit argument to most functions in the API::

  (require '[metrics.meters :as meters])

  ;; with 1.x
  (meters/rate-mean)

  (meters/mark! 10)
  ;; with 2.0
  (let [m (meters/meter ["test" "meters" "test-rate-mean-update-multiple"])]
    (meters/rate-mean m)
    (meters/mark! m 10))


The library maintains a default registry in `metrics.core/default-registry`
which tries to keep the `1.x` API as functional as possible but using
your own registry is encouraged.

To instantiate a registry, use `metrics.core/new-registry`::

  (require '[metrics.core :as mtr])

  (mtr/new-registry)


See `GH #19 <https://github.com/sjl/metrics-clojure/issues/19>`_ for
discussion.

defgauge Restricted to Functions Only
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

In `metrics-clojure` 1.x, `metrics.gauges/defgauge` could accept
a function or a bunch of forms (body). In 2.0, it only accepts
a function. This is in part due to the new API structure but also
make the API more straightforward and works much better with explicit
registry management now advocated by the library.


Nanoseconds Precision in Timers
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Metrics 3.0 uses nanoseconds precision in timers.


Upgrade to Metrics 3.0
~~~~~~~~~~~~~~~~~~~~~~

Metrics 3.0 is now used internally by the library.

Clojure 1.3 No Longer Supported
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Clojure 1.3 is no longer supported by the library.



1.0.1
-----

Fixes compilation of ``defhistogram``, ``defcounter`` and friends.


1.0.0
-----

Initial stable release.
