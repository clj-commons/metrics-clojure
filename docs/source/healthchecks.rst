HealthChecks
============

``metrics-clojure-health`` will allow you to define and run healthcheck.

Install
-------

``metrics-clojure-health`` is provided as a separate package from ``metrics.core``.
To install add the following to your ``project.clj``:

.. parsed-literal::

    [metrics-clojure-health "|release|"]


Example
-------

An example using the default HealthCheckRegistry, (which is different
from the default-registry).::

    (require '[metrics.health.core :as health])
    (defhealthcheck "second-check" (fn [] (let [now (.getSeconds (java.util.Date.))]
                                             (if (< now 30)
                                                (health/healthy "%d is less than 30!" now)
                                                (health/unhealthy "%d is more than 30!" now)))))

    (health/check second-check)
    #_ => #<Result Result{isHealthy=true, message=3 is less than 30!}>

    (health/check-all)
    #_ => {:healthy [#<UnmodifiableEntry foo=Result{isHealthy=true, message=6 is less than 30!}>]}
