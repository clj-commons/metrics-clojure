
## Changes Between 2.10.0 and 2.11.0 (unreleased)

### Migration to clj-commons

Project was migrated to clj-commons org and new source is [clj-commons/metrics-clojure](https://github.com/clj-commons/metrics-clojure/)
- Added ORIGINATOR and CODEOWNERS files.
- Renamed links to use new github url
- Fixed readthedocs publishing - account was transfered to @ieugen
- Upgraded com.izettle/dropwizard-metrics-influxdb to 1.3.4 since 1.2.4 is no longer on Maven Central.
- Added bb.edn with test task to automate project testing

### Dockerfile for documentation build

Added a Dockerfile using sphinx image so documentation can be built without having sphinx locally.

### Migration to deps.edn

Migration was done using `lein2deps` tool for each repo

```shell
 bb -Sdeps '{:deps {io.github.borkdude/lein2deps {:git/sha "1fdaf45577edc9a6aeee027501e50924b9b793f5"}}}'    -m lein2deps.api --write-file deps.edn
```

- Added deps.edn to metrics-clojure-core and dropped codahale repo since service shut down
- Added deps.edn to metrics-clojure-ganglia
- Added deps.edn to metrics-clojure-graphite
- Added deps.edn to metrics-clojure-health
- Added deps.edn to metrics-clojure-influxdb
- Added deps.edn to metrics-clojure-jvm
- Added deps.edn to metrics-clojure-riemann
- Added deps.edn to metrics-clojure-ring

### Riemann Client updated

Riemann Client was migrated to io.riemann package from com.aphyr as of version 0.4.3.


## Changes Between 2.9.0 and 2.10.0 (Sep 19th, 2017)

### JSON Filtering of Metrics

GitHub issues: [#124](https://github.com/sjl/metrics-clojure/pull/124), [#123](https://github.com/sjl/metrics-clojure/pull/123)

Contributed by Marcus Spiegel.

### Per Ring Endpoint Metrics

GitHub issue: [#119](https://github.com/sjl/metrics-clojure/issues/119)

Contributed by Andy Chambers.

### Graphite Reporter Accepts Arbitrary Graphite Instances

GitHub issue: [#122](https://github.com/sjl/metrics-clojure/pull/122)

Contributed by Taylor Wood.

### Fewer Transient Dependencies

GitHub issue: [#117](https://github.com/sjl/metrics-clojure/pull/117)

Contributed by Joe Littlejohn.

### Cheshire Dependency Bump

GitHub issue: [#125](https://github.com/sjl/metrics-clojure/pull/125).

Contribyted by Shantanu Kumar.



## Changes Between 2.8.0 and 2.9.0 (Feb 12th, 2017)

### InfluxDB Reporter Option Renamed

The misspelled `:group-guages` option was renamed to `:group-gauges`.

Contributed by Pierre-Yves Ritschard.


### Reflection Fixes

Contributed by Pierre-Yves Ritschard.


## Changes Between 2.7.0 and 2.8.0 (Dec 28th, 2016)

### Updated Dependencies

Contributed by Pierre-Yves Ritschard.


### InfluxDB Reporter

Contributed by Andrew Jones.





## Changes Between 2.6.0 and 2.7.0 (June 1st, 2016)

### Functions for Working with Reservoirs

Ning Sun contributed several new functions that instantiate
various reservoir types:

 * `metrics.core/uniform-reservior`
 * `metrics.core/exponentially-decaying-reservoir`
 * `metrics.core/sliding-time-window-reservoir`
 * `metrics.core/sliding-window-reservoir`

and a few more.

GH issue: [#104](https://github.com/sjl/metrics-clojure/pull/104)

### Easier to Retrieve Metrics

Ning Sun contributed several new functions that retrieve different
type of metrics from a registry:

 * `metrics.core/meters`
 * `metrics.core/histograms`
 * `metrics.core/timers`
 * `metrics.core/gauges`
 * `metrics.core/counters`

GH issue: [#102](https://github.com/sjl/metrics-clojure/pull/102)

### Updated Dependencies

Contributed by Pierre-Yves Ritschard.




## Changes Between 2.5.0 and 2.6.0 (Nov 30th, 2015)

### Riemann Reporter

Contribited by Ragnar Dahlén.

### DropWizard Metrics Upgrade

DropWizard Metrics was upgraded to `3.1.2`.

Contributed by Matthias Nüßler.

### Cheshire Upgrade

Cheshire was upgraded to `5.5.0`.

Contributed by Shantanu Kumar.

### Predicate-based Metric Removal

`metrics.core/remove-metrics` is a new function that removes
metrics based on a predicate.

Contributed by Vincent Bernat.

### Infinite Recursion in Ganglia

Ganglia reporter had an infinite recursion.

GH issue: #71.

### Add support for timing expressions in core.async/go blocks

Added the macro `metrics.timers/start-stop-time` to enable timing expressions that include parking functions in `core.async/go` blocks.

Contributed by Wil Yegelwel.

## Changes Between 2.4.0 and 2.5.0

### Type Hints Use Fully-Qualified Names

Type hints across the libraries now use fully-qualified names, which means
returned types don't have to be imported in the caller namespaces.

Contributed by Jason Whitlark.

### :total Key In Timers

`metrics.timers/rate` return value now includes `:total`.

Contributed by Andrew Rudenko.


### Added remove-all-metrics function

`metrics-clojure` Now has a function to remove all existing metrics from a given registry.

Contributed by Jason Whitlark.


### Metrics 3.1.1

`metrics-clojure` is now based on [Metrics 3.1.1](https://github.com/dropwizard/metrics/issues/694#issuecomment-77668929).

Contributed by Jason Whitlark.


## Changes Between 2.3.0 and 2.4.0

### Metrics 3.1.0

`metrics-clojure` is now based on [Metrics 3.1.0](https://groups.google.com/forum/#!topic/metrics-user/zwzHnMBcAX4).

### Improve ring instrumentation.

`metrics-clojure-ring` now instruments the scheme of requests, (http or https).

Contributed by Jason Whitlark.

## Changes Between 2.2.x and 2.3.0

### Enhanced Timers

`metrics.timers` now provides two new functions, `start` and `stop`.

Contributed by Jason Whitlark.

### metrics.health

`metrics-clojure-health` is a new sub-project that makes implementing
[health checks](http://metrics.dropwizard.io/3.1.0/manual/healthchecks/) easier in Clojure.

Contributed by Jason Whitlark.

### Ganglia Reporter

`metrics-clojure-ganglia` is a new sub-project that makes using the Ganglia
reporter easier from Clojure.

Contributed by Jason Whitlark.

### CSV Reporter

CSV reporter is now provided in `metrics.reporters.csv`.

Contributed by Jason Whitlark.

### JMX Reporter

JMX reporter is now provided in `metrics.reporters.jmx`.

Contributed by Jason Whitlark.

### Stop Function for Reporter

All reporter namespace now provide a new function, `stop`, that stops
their respective reporter.

Contributed by Jason Whitlark.

### Graphite Reporter API in Line With the Console One

`metrics.reporters.graphite/start` is a function that starts the reporter,
just like other reporter namespaces (e.g. console) do.

Contributed by Jason Whitlark.

### Graphite and Console Reporters Documentation Improvements

Contributed by Jason Whitlark.


## Changes Between 2.1.x and 2.2.0

### Graphite Extension

`metrics.reporters.graphite` is a new sub-project that contains
a Graphite reporter.


### Ring Extension Now Respects User-provided Registry

The Ring extension now respects user-provided registries
instead of always using the default one.

Contributed by David Smith.

### Bugfixes

- Fixed `metrics.core/default-registry` to have a valid reflection
  hint (no longer causes compile error when used in interop.) Fix
  planned on 2.1.x as well. Contributed by Tim McCormack.


## Changes Between 2.0.x and 2.1.0

### Ring Extension Supports Multiple Registries and Options

It is now possible to use `metrics.ring.expose` with a custom
registry:

``` clojure
(require '[metrics.ring.expose :refer [expose-metrics-as-json]])

(expose-metrics-as-json ring-app "/ops/metrics" registry {:pretty-print? true})
```

### JVM Instrumentation Extension

The project now also has a JVM instrumentation extension that covers:

 * Number of threads and their states
 * GC stats, heap, off heap memory
 * File descriptors

To enable full instrumenting, use

``` clojure
(require '[metrics.jvm.core :refer [instrument-jvm]])

(instrument-jvm metric-registry)
```

Contributed by John Cowie (ThoughtWorks).


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
