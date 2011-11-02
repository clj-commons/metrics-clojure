Counters
========

Creating
--------

Create your counter::

    (use '[metrics.counters :only (counter)])

    (def users-connected (counter "users-connected"))

Reading
-------

Writing
-------

Now increment and decrement it::

    (use '[metrics.counters :only (inc! dec!)])

    (inc! users-connected)
    (inc! users-connected 2)

    (dec! users-connected)
    (dec! users-connected 2)


