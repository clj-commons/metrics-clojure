Gauges
======

Gauges are used to measure the instantaneous value of something.

Creating
--------

Create your gauge::

    (use '[metrics.gauges :only (gauge)])

    (def files-open
      (gauge "files-open"
             (return-number-of-files-open ...)))

That's it.  Pretty simple.

`gauge` is a macro.  If you need a function instead you can use `gauge-fn`, but
you have to pass it a function, not just a body::

    (use '[metrics.gauges :only (gauge-fn)])

    (def files-open
      (gauge-fn "files-open"
             #(return-number-of-files-open ...)))

Reading
-------

You can read the value of a gauge at any time with ``value``::

    (use '[metrics.gauges :only (value)])

    (value files-open)
