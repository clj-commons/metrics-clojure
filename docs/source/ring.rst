Extras for Ring
===============

``metrics-clojure`` contains some extra glue code you can use with your `Ring
<https://github.com/mmcgrana/ring>`_ apps.

Installation
------------

The extra Ring-related functionality is in a separate ``metrics-clojure-ring``
library so you don't have to install it unless you want it.

To install it, add this to your ``project.clj``'s dependencies::

    [metrics-clojure-ring "0.7.0"]

**Note:** the versions of ``metrics-clojure`` and ``metrics-clojure-ring``
should always be the same.


Exposing Metrics as JSON
------------------------

You can expose your app's metrics as JSON by using the
``expose-metrics-as-json`` middleware::

    (use '[metrics.ring.expose :only (expose-metrics-as-json)])

    (def app (expose-metrics-as-json app))

This will add a ``/metrics/`` URL that will show all the metrics for the app.
The trailing slash is required.

If you want to use a different endpoint you can pass it as a parameter::

    (use '[metrics.ring.expose :only (expose-metrics-as-json)])

    (def app (expose-metrics-as-json app "/admin/stats/"))

**WARNING**: this URL will not be protected by a username or password in any way
(yet), so if you have sensitive metrics you might want to think twice about
using it (or protect it yourself).

JSON Format
~~~~~~~~~~~

Here's an example of the JSON format::

    {
        "default.default.sample-metric": {
            "type": "counter",
            "value": 10
        }
    }

The JSON is an object that maps metric names to their data.

Each metric object will have a ``type`` attribute.  The rest of the attributes
will depend on the type of metric.

**TODO**: Document each individual type.

Troubleshooting
---------------

If you're using these extras alongside Noir you'll need to be running the latest
version of Noir to avoid dependency issues.

If you're getting errors about overriding stuff in Jackson this is the problem.
