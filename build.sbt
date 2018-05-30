name := "sbt-web-brotli"

description := "sbt-web plugin for brotling assets"

sbtPlugin := true

isSnapshot := false

addSbtPlugin("com.typesafe.sbt" % "sbt-web" % "1.4.3")
//addSbtWeb("1.4.2")

//crossSbtVersions := Seq("1.0.1", "0.13.16")
//crossSbtVersions := Seq("0.13.16")
sbtVersion := "1.1.5"

scalaVersion := "2.12.6"

resolvers += (
  "bintray-nitram509-jbrotli" at "http://dl.bintray.com/nitram509/jbrotli"
)

val brotliNativeArtefact = {

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

  s"jbrotli-native-$family-$arch"
}

libraryDependencies ++= Seq(
  "org.meteogroup.jbrotli" % "jbrotli" % "0.5.0",
  "org.meteogroup.jbrotli" % brotliNativeArtefact % "0.5.0" % "provided"
)


//*******************************
// Maven settings
//*******************************

publishMavenStyle := true

publishConfiguration := publishConfiguration.value.withOverwrite(true)

organization := "com.github.enalmada"

startYear := Some(2018)

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (isSnapshot.value)
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

pomExtra in Global := {
  <url>https://github.com/Enalmada/sbt-web-brotli</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
      </license>
    </licenses>
    <scm>
      <connection>scm:git:git@github.com:enalmada/sbt-web-brotli.git</connection>
      <developerConnection>scm:git:git@github.com:enalmada/sbt-web-brotli.git</developerConnection>
      <url>https://github.com/enalmada</url>
    </scm>
    <developers>
      <developer>
        <id>enalmada</id>
        <name>Adam Lane</name>
        <url>https://github.com/enalmada</url>
      </developer>
    </developers>
}

credentials += Credentials(Path.userHome / ".sbt" / "sonatype.credentials")

// https://github.com/xerial/sbt-sonatype/issues/30
sources in (Compile, doc) := Seq()