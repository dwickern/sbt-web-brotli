sbt-web-brotli
==========

[sbt-web] plugin for brotli-compressing web assets using [jvmbrotli] bindings.

Rewritten from [sbt-gzip] sources, thanks to Typesafe/Lightbend.
Some parts of code, docs, tests are copy-pasted with no changes.


Add plugin
----------

Add the plugin to `project/plugins.sbt` (it must all be there and not build.sbt for plugin to initialize):

```scala
val brotliNativeArtifact = {
  val osName = System.getProperty("os.name").toLowerCase
  val osArch = System.getProperty("os.arch").toLowerCase

  val family = if (osName.startsWith("linux")) {
    "linux"
  } else if (osName.startsWith("mac os x") || osName.startsWith("darwin")) {
    "darwin"
  } else {
    "win32"
  }

  val arch = if (family == "darwin") {
    "x86-amd64"
  } else if (osArch == "i386" || osArch == "i486" || osArch == "i586" || osArch == "i686") {
    "x86"
  } else if (osArch == "amd64" || osArch == "x86-64" || osArch == "x64") {
    "x86-amd64"
  } else if (family == "linux" && osArch.startsWith("arm")) {
    "arm32-vfp-hflt"
  }

  s"jvmbrotli-$family-$arch"
}

libraryDependencies ++= Seq(
  "com.nixxcode.jvmbrotli" % brotliNativeArtifact % "0.2.0"
)

addSbtPlugin("com.github.enalmada" % "sbt-web-brotli" % "0.5.5")
```


Your project's build file also needs to enable sbt-web plugins. For example with build.sbt:

    lazy val root = (project.in file(".")).enablePlugins(SbtWeb)

As with all sbt-web asset pipeline plugins you must declare their order of execution e.g.:

```scala
pipelineStages := Seq(brotli)
```

Configuration
-------------

### Filters

Include and exclude filters can be provided. For example, to only create
brotli files for `.js` files:

```scala
includeFilter in brotli := "*.js"
```

Or to exclude all `.js` files but include any other files:

```scala
excludeFilter in brotli := "*.js"
```

The '''default''' filters configured like this:

```scala
includeFilter in brotli := "*.html" || "*.css" || "*.js"

excludeFilter in brotli := HiddenFileFilter || "*.woff" || "*.woff2" || "*.gz"
```

If you also using `sbt-gzip`, you may want configure it to ignore brotli-compressed files:
```scala
excludeFilter in gzip := "*.woff" || "*.woff2" || "*.br"
```

License
-------

This code is licensed under the [Apache 2.0 License][apache].

[jvmbrotli]: https://github.com/nixxcode/jvm-brotli
[sbt-gzip]: https://github.com/sbt/sbt-gzip
[sbt-web]: https://github.com/sbt/sbt-web
[apache]: http://www.apache.org/licenses/LICENSE-2.0.html
