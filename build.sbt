organization := "com.github.dwickern"
name := "sbt-web-brotli"
description := "sbt-web plugin for brotling assets"
sbtPlugin := true

enablePlugins(ScriptedPlugin)
scriptedLaunchOpts += s"-Dproject.version=${version.value}"

addSbtPlugin("com.github.sbt" % "sbt-web" % "1.5.5")

libraryDependencies ++= Seq(
  "com.aayushatharva.brotli4j" % "brotli4j" % "1.16.0"
)

homepage := Some(url("https://github.com/dwickern/sbt-web-brotli"))
licenses := List(License.Apache2)
developers := List(
  Developer(
    "dwickern",
    "Derek Wickern",
    "dwickern@gmail.com",
    url("https://github.com/dwickern")
  )
)
