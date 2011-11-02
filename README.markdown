metrics-clojure
===============

`metrics-clojure` is a Clojure façade around Coda Hale's [metrics][] library.

[metrics]: http://github.com/codahale/metrics/

Installation
------------

Add this to your `project.clj`'s dependencies:

    [metrics-clojure "0.1.0"]

That's it.

Usage
-----

`metrics-clojure` is a thin wrapper around `metrics`, so if you don't know what
any of these words mean you should probably read the [metrics documentation][]
and/or watch the [talk][]:


[metrics documentation]: https://github.com/codahale/metrics/tree/development/docs
[talk]: http://pivotallabs.com/talks/139-metrics-metrics-everywhere

### Creating Metrics

`metrics-clojure` wraps up the `metrics` classes to make them more Clojurey.

#### Gauges

Create your gauge:

    (use '[metrics.gauges :only (gauge)])

    (def files-open
      (gauge "files-open"
             (return-number-of-files-open ...)))

That's it.  Pretty simple.

`gauge` is a macro.  If you need a function instead you can use `gauge-fn`, but
you have to pass it a function, not just a body:

    (use '[metrics.gauges :only (gauge-fn)])

    (def files-open
      (gauge-fn "files-open"
             #(return-number-of-files-open ...)))

#### Counters

Create your counter:

    (use '[metrics.counters :only (counter)])

    (def users-connected (counter "users-connected"))

Now increment and decrement it:

    (use '[metrics.counters :only (inc! dec!)])

    (inc! users-connected)
    (inc! users-connected 2)

    (dec! users-connected)
    (dec! users-connected 2)

#### Meters

Create your meter:

    (use '[metrics.meters :only (meter)])

    (def files-served (meter "files-served" "files"))

The second argument to `meter` is a string describing the "units" for the meter.
In this example it's "files", as in "18732 files".

Mark the meter every time the event happens:

    (use '[metrics.meters :only (mark!)])

    (mark! files-served)

#### Histograms

Create your histogram:

    (use '[metrics.histograms :only (histogram)])

    (def search-results-returned
      (histogram "search-results-returned"))

You can create a biased histogram by passing an extra boolean argument:

    (def search-results-returned-biased
      (histogram "search-results-returned-biased" true))

Update the histogram when you have a new value to record:

    (use '[metrics.histograms :only (update!)])

    (update! search-results-returned 10)

#### Timers

Create your timer:

    (use '[metrics.timers :only (timer)])

    (def image-processing-time (timer "image-processing-time"))

Now time something:

    (use '[metrics.timers :only (time!)])

    (time! image-processing-time
           (process-image-part-1 ...)
           (process-image-part-2 ...))

`time!` is a macro.  If you need a function instead, you can use `time-fn!`, but
you'll need to pass it a function instead of just a body:

    (use '[metrics.timers :only (time-fn!)])

    (time-fn! image-processing-time
              (fn []
                (process-image-part-1 ...)
                (process-image-part-2 ...)))

### Metric Names

In all these examples we've used a string to name the metric.  The `metrics`
library actually names metrics with three-part names.  In Java and Scala those
names are usually the package and class where the timer is being used.

In Clojure you usually won't have a meaningful class name to record, and the
package name would be a pain to find, so `metrics-clojure` uses "default" and
"default" for those parts.  This results in a name like
"default.default.my-metric".

If you want to specify something other than "default" you can pass a collection
of three strings instead of a single string:

    (use '[metrics.timers :only (timer)])

    (def response-time
      (timer ["webservice" "views" "response-time"]))

This will result in a name like "webservice.views.response-time".

### Removing Metrics

You can remove metrics as long as you know their names:

    (use '[metrics.core :only (remove-metric)])

    (remove-metric "files-served")

You can use the sequence form of metric names here too, of course:

    (remove-metric ["webservice" "views" "response-time"])

### Reporting Metrics

Currently `metrics-clojure` only supports reporting metrics through the console
(on standard error):

    (use '[metrics.core :only (report-to-console)])

    (report-to-console 10)

This will tell `metrics` to print all metrics to the console every ten seconds.

More reporting methods will be added in the future.  Pull requests are welcome.

### Side Effects and Agents

Pretty much everything `metrics-clojure` does causes side effects.  If you're
recording metrics inside of a `dosync` they may be recorded multiple times if
the transaction is restarted.

This may or may not be what you want.  If you're recording how long it takes to
do something, and you do it twice, then it makes sense to record it each time.
If you're recording how many responses get sent to a user, then you probably
don't want to overcount them.

`metrics-clojure` doesn't try to decide for you.  It leaves it up to you to
handle the issue.

If you don't want to record something multiple times, an agent may be a good way
to handle things:

    (def thing-given-to-user (agent (counter "thing-given-to-user")))

    (dosync
       (send thing-given-to-user inc!)
       (make-thing ...))

This works because the recording functions for counters, meters and histograms
return the metric object.

**This will *not* work with timers!**  `time!` returns the result of the work,
so the agent's value will be replaced by that and it won't work more than once.

In practice this shouldn't be a problem though, because if you're timing work
you'll probably want to time it every time it happens, right?

More Information
----------------

* Source (Mercurial): <http://bitbucket.org/sjl/metrics-clojure/>
* Source (Git): <http://github.com/sjl/metrics-clojure/>
* License: MIT/X11
* Issues: <http://github.com/sjl/metrics-clojure/issues/>

