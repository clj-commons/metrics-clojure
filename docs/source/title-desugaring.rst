.. _desugaring:

Title Desugaring by the ``def[metric]`` Macros
==============================================

All of the ``def[metric]`` macros (:ref:`defcounter <defcounter>`,
:ref:`defgauge <defgauge>`, :ref:`defhistogram <defhistogram>`, :ref:`defmeter
<defmeter>`, and :ref:`deftimer <deftimer>`) take a title as their first
argument.

All of the macros will use this title as a symbol when binding it to a var, and
will convert it to a string to use as the :ref:`name <metric-names>` of the
metric.

For example, this::

    (defmeter post-requests)

is equivalent to::

    (def post-requests (meter "post-requests"))

If you want to define a metric with a name outside of the default group/type,
you can use a vector of three strings and/or symbols instead of a bare symbol.
The last entry in the vector will be used as the symbol for the var (and will be
coerced if necessary). For example, all of these::

    (defcounter ["mysite.http" api post-requests])
    (defcounter ["mysite.http" "api" post-requests])
    (defcounter ["mysite.http" "api" "post-requests"])

are equivalent to::

    (def post-requests (meter ["mysite.http" "api" "post-requests"]))

If you need more control than this (e.g.: if you want a var named differently
than the last segment of the metric name) you should use the normal creation
methods.  These macros are only a bit of syntactic sugar to reduce typing.
