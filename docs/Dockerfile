#
# Docker image to build docs using sphinx
#
# docker buildx build -t metrics-clojure-docs . --load
#
# docker run -v ./build:/docs/build metrics-clojure-docs help
#
# https://www.sphinx-doc.org/en/master/usage/installation.html#docker
# pass docker.io/sphinxdoc/sphinx-latexpdf to build PDF - very large image !
#
ARG SPHINX_IMAGE=docker.io/sphinxdoc/sphinx
ARG SPHINX_TAG=7.1.2
FROM ${SPHINX_IMAGE}:${SPHINX_TAG}

WORKDIR /docs
COPY . /docs

# Documentation is generated in build dir
VOLUME [ "/docs/build" ]

# Entrypoint is the makefile run with --entrypoint bash to change
ENTRYPOINT [ "make" ]
# By default we run the html make target. Use help to see full list.
CMD [ "html" ]

