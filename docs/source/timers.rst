Timers
======

Creating
--------

Create your timer::

    (use '[metrics.timers :only (timer)])

    (def image-processing-time (timer "image-processing-time"))

Reading
-------

Writing
-------

Now time something::

    (use '[metrics.timers :only (time!)])

    (time! image-processing-time
           (process-image-part-1 ...)
           (process-image-part-2 ...))

`time!` is a macro.  If you need a function instead, you can use `time-fn!`, but
you'll need to pass it a function instead of just a body::

    (use '[metrics.timers :only (time-fn!)])

    (time-fn! image-processing-time
              (fn []
                (process-image-part-1 ...)
                (process-image-part-2 ...)))

