sbt-web-brotli
==========

[![build](https://github.com/dwickern/sbt-web-brotli/workflows/build/badge.svg)](https://github.com/dwickern/sbt-web-brotli/actions)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.dwickern/sbt-web-brotli/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.dwickern/sbt-web-brotli)

[sbt-web] plugin for brotli-compressing web assets using [jvmbrotli] bindings.

Rewritten from [sbt-gzip] sources, thanks to Typesafe/Lightbend.
Some parts of code, docs, tests are copy-pasted with no changes.


Usage
----------

Add the plugin to `project/plugins.sbt`:

```scala
addSbtPlugin("com.github.dwickern" % "sbt-web-brotli" % "1.1.0")
```

Add `brotli` to the sbt-web asset pipeline in your `build.sbt`:

```scala
pipelineStages := Seq(brotli)
```

Pipeline stages are only used in the production build.
To see the output of your pipeline, run in your sbt shell:

```
> show webStage
```

Configuration
-------------

### Filters

Include and exclude filters can be provided. For example, to only create brotli files for `.js` files:

```scala
brotli / includeFilter := "*.js"
```

Or to exclude all `.js` files but include any other files:

```scala
brotli / excludeFilter := "*.js"
```

The default filters configured like this:

```scala
brotli / includeFilter := "*.html" || "*.css" || "*.js"
brotli / excludeFilter := HiddenFileFilter || "*.woff" || "*.woff2" || "*.gz"
```

### Usage with sbt-gzip

If you also use [sbt-gzip], make sure `brotli` comes after `gzip` in the pipeline:

```scala
pipelineStages := Seq(gzip, brotli)
```

Or, alternatively, configure `gzip` to ignore the brotli-compressed files:

```scala
pipelineStages := Seq(brotli, gzip)
gzip / excludeFilter ~= { _ || "*.br" }
```

License
-------

This code is licensed under the [Apache 2.0 License][apache].

[jvmbrotli]: https://github.com/nixxcode/jvm-brotli
[sbt-gzip]: https://github.com/sbt/sbt-gzip
[sbt-web]: https://github.com/sbt/sbt-web
[apache]: http://www.apache.org/licenses/LICENSE-2.0.html
