Side Effects and Agents
=======================

Pretty much everything ``metrics-clojure`` does causes side effects.  If you're
recording metrics inside of a ``dosync`` they may be recorded multiple times if
the transaction is restarted.

This may or may not be what you want.  If you're recording how long it takes to
do something, and you do it twice, then it makes sense to record it each time.
If you're recording how many responses get sent to a user, then you probably
don't want to overcount them.

``metrics-clojure`` doesn't try to decide for you.  It leaves it up to you to
handle the issue.

If you don't want to record something multiple times, an agent may be a good way
to handle things::

    (def thing-given-to-user (agent (counter "thing-given-to-user")))

    (dosync
       (send thing-given-to-user inc!)
       (make-thing ...))

This works because the recording functions for counters, meters and histograms
return the metric object.

**This will *not* work with timers!**  ``time!`` returns the result of the work,
so the agent's value will be replaced by that and it won't work more than once.

In practice this shouldn't be a problem though, because if you're timing work
you'll probably want to time it every time it happens, right?
