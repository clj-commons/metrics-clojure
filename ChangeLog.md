## Changes Between 2.0.2 and 2.0.3

### timers/mean Returns Value (Not Rate)

`metrics.timers/mean` now returns mean value (not rate)
of the timer.

Contributed by Steve Miner.


### Ring Extension Updated for 2.0

The Ring extension is now updated for Metrics core 2.0 API.

Contributed by John Cowie (ThoughtWorks).



## Changes Between 1.1.0 and 2.0.0

### Metrics Registries

`metrics-clojure` 1.x maintained a metrics registry in a dynamic var.
This approach makes the library a little easier for beginners but
also much harder to use in more sophisticated cases, e.g. in concurrent
applications or those that use a Component-like approach to
program structure.

As such, `metrics-clojure` 2.0+ makes metrics registry a required
explicit argument to most functions in the API:

``` clojure
(require '[metrics.meters :as meters])

;; with 1.x
(meters/rate-mean)
(meters/mark! 10)

;; with 2.0
(let [m (meters/meter ["test" "meters" "test-rate-mean-update-multiple"])]
  (meters/rate-mean m)
  (meters/mark! m 10))
```

The library maintains a default registry in `metrics.core/default-registry`
which tries to keep the `1.x` API as functional as possible but using
your own registry is encouraged.

To instantiate a registry, use `metrics.core/new-registry`:

``` clojure
(require '[metrics.core :as mtr])

(mtr/new-registry)
```

See [GH #19](https://github.com/sjl/metrics-clojure/issues/19) for
discussion.

### defgauge Restricted to Functions Only

In `metrics-clojure` 1.x, `metrics.gauges/defgauge` could accept
a function or a bunch of forms (body). In 2.0, it only accepts
a function. This is in part due to the new API structure but also
make the API more straightforward and works much better with explicit
registry management now advocated by the library.


### Nanoseconds Precision in Timers

Metrics 3.0 uses nanoseconds precision in timers.


### Upgrade to Metrics 3.0

Metrics 3.0 is now used internally by the library.

### Clojure 1.3 No Longer Supported

Clojure 1.3 is no longer supported by the library.


## Changes Between 1.0.0 and 1.1.0

### Support for TRACE, OPTION, CONNECT HTTP Verbs

Ring integration now tracks rates of requests that use TRACE, OPTION,
CONNECT, and non-standard HTTP verbs.

Contributed by Joe Littlejohn (Nokia).


### Clojure 1.6

The project now depends on `org.clojure/clojure` version `1.6.0`. It is
still compatible with Clojure 1.4 and if your `project.clj` depends on
a different version, it will be used, but 1.6 is the default now.

### Cheshire 5.3

The project now uses [Cheshire](https://github.com/dakrone/cheshire) 5.3.
