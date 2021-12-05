organization := "com.github.dwickern"
name := "sbt-web-brotli"
description := "sbt-web plugin for brotling assets"
sbtPlugin := true

enablePlugins(ScriptedPlugin)
scriptedLaunchOpts += s"-Dproject.version=${version.value}"

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.4.4")

libraryDependencies ++= Seq(
  "com.nixxcode.jvmbrotli" % "jvmbrotli" % "0.2.0",
)

publishTo := sonatypePublishToBundle.value
pomExtra := {
  <url>https://github.com/dwickern/sbt-web-brotli</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:git@github.com:dwickern/sbt-web-brotli.git</connection>
      <developerConnection>scm:git:git@github.com:dwickern/sbt-web-brotli.git</developerConnection>
      <url>https://github.com/dwickern</url>
    </scm>
    <developers>
      <developer>
        <id>dwickern</id>
        <name>Derek Wickern</name>
        <url>https://github.com/dwickern</url>
      </developer>
      <developer>
        <id>enalmada</id>
        <name>Adam Lane</name>
        <url>https://github.com/enalmada</url>
      </developer>
    </developers>
}

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
