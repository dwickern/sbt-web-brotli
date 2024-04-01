organization := "com.github.dwickern"
name := "sbt-web-brotli"
description := "sbt-web plugin for brotling assets"
sbtPlugin := true

enablePlugins(ScriptedPlugin)
scriptedLaunchOpts += s"-Dproject.version=${version.value}"
scriptedDependencies := {
  // publish to maven instead of ivy, in order to activate maven profiles on Windows
  (Test / compile).value
  publishM2.value
}

addSbtPlugin("com.github.sbt" % "sbt-web" % "1.5.3")

val jvmBrotliVersion = "0.2.0"
libraryDependencies ++= Seq(
  "com.nixxcode.jvmbrotli" % "jvmbrotli" % jvmBrotliVersion
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
    <profiles>
      <profile>
        <id>win32-x86-amd64</id>
        <activation>
          <os>
            <family>windows</family>
            <arch>amd64</arch>
          </os>
        </activation>
        <dependencies>
          <dependency>
            <groupId>com.nixxcode.jvmbrotli</groupId>
            <artifactId>jvmbrotli-win32-x86-amd64</artifactId>
            <version>{jvmBrotliVersion}</version>
          </dependency>
        </dependencies>
      </profile>
      <profile>
        <id>win32-x86</id>
        <activation>
          <os>
            <family>windows</family>
            <arch>x86</arch>
          </os>
        </activation>
        <dependencies>
          <dependency>
            <groupId>com.nixxcode.jvmbrotli</groupId>
            <artifactId>jvmbrotli-win32-x86</artifactId>
            <version>{jvmBrotliVersion}</version>
          </dependency>
        </dependencies>
      </profile>
    </profiles>
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
