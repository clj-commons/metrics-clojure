Extras for Ring
===============

metrics-clojure contains some extra glue code you can use with your `Ring
<https://github.com/ring-clojure/ring>`_ apps.

Installation
------------

The extra Ring-related functionality is in a separate ``metrics-clojure-ring``
library so you don't have to install it unless you want it.

To install it, add this to your ``project.clj``'s dependencies:

.. parsed-literal::

    [metrics-clojure-ring "|release|"]

**Note:** the versions of metrics-clojure and metrics-clojure-ring should always
be the same.


Exposing Metrics as JSON
------------------------

You can expose your app's metrics as JSON by using the
``expose-metrics-as-json`` middleware::

    (require '[metrics.ring.expose :refer [expose-metrics-as-json]])

    (def app (expose-metrics-as-json app))

This will add a ``/metrics/`` URL that will show all the metrics for the app.
The trailing slash is required.

This middleware works great with Noir, too.

If you want to use a different endpoint you can pass it as a parameter::

    (require '[metrics.ring.expose :refer [expose-metrics-as-json]])

    (def app (expose-metrics-as-json app "/admin/stats/"))

Using compojure::

    (def app
      (-> (routes home-routes app-routes)
          (wrap-base-url)
          (expose-metrics-as-json)))

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

Instrumenting a Ring App
------------------------

You can add some common metrics by using the ``instrument`` middleware::

    (require '[metrics.ring.instrument :refer [instrument]])

    (def app (instrument app))
    
Using compojure::

    (def app
      (-> (routes home-routes app-routes)
          (wrap-base-url)
          (instrument)))

This will add a number of metrics, listed below.

This middleware works great with Noir, too.

``ring.requests.active``
~~~~~~~~~~~~~~~~~~~~~~~~

A counter tracking the number of currently open requests.

``ring.requests.rate``
~~~~~~~~~~~~~~~~~~~~~~

A meter measuring the rate of all incoming requests.

``ring.requests.rate.*``
~~~~~~~~~~~~~~~~~~~~~~~~

Six separate meters (ending in ``GET``, ``POST``, etc) measuring the rate of
incoming requests of a given type.

``ring.requests-scheme.rate.*``
~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

Two separate meters (ending in ``http`` or ``https``) measuring the
rate of incoming requests of a given type.

``ring.responses.rate``
~~~~~~~~~~~~~~~~~~~~~~~

A meter measuring the rate of all outgoing responses.

``ring.responses.rate.*``
~~~~~~~~~~~~~~~~~~~~~~~~~

Four separate meters (ending in ``2xx``, ``3xx``, etc) measuring the rate of
outgoing responses of a given type.

``ring.handling-time.*``
~~~~~~~~~~~~~~~~~~~~~~~~

Six separate timers (ending in ``GET``, ``POST``, etc) measuring the time taken
to handle incoming requests of a given type.

Options
-------

An optional map may be supplied to the instrument function to configure how metrics
are registered. The options supported are as follows:-

``prefix``

A vector of strings that will be used as the prefix when creating the metric name. The
vector will be concatenated with the metric names described above before being joined
into a string to produce metric names such as

foo.ring.responses.rate
foo.ring.handling-time

This is useful if you want to break your metrics down by endpoint.

Troubleshooting
---------------

If you're using these extras alongside Noir you'll need to be running the latest
version of Noir to avoid dependency issues.

If you're getting errors about overriding stuff in Jackson this is the problem.
