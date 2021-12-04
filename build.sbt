organization := "com.github.dwickern"
name := "sbt-web-brotli"
description := "sbt-web plugin for brotling assets"
sbtPlugin := true

enablePlugins(ScriptedPlugin)
scriptedLaunchOpts += s"-Dproject.version=${version.value}"

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.4.4")

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
  "com.nixxcode.jvmbrotli" % "jvmbrotli" % "0.2.0",
  "com.nixxcode.jvmbrotli" % brotliNativeArtifact % "0.2.0" % "provided"
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
