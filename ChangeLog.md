## Changes Between 1.1.0 and 1.1.0

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
