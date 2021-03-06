name := "sbt-jasperreports"

organization := "com.jeduardocosta.jasperreports"
organization := "com.jeduardocosta.jasperreports"

scalacOptions := Seq("-unchecked", "-deprecation", "-feature", "-encoding", "utf8")

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalaVersion := "2.12.8"

sbtPlugin := true

crossSbtVersions := Seq("0.13.18", "1.2.8")

publishMavenStyle := true

publishArtifact in Test := false
parallelExecution in Test := false

sbtrelease.ReleasePlugin.autoImport.releasePublishArtifactsAction := PgpKeys.publishSigned.value
sbtrelease.ReleasePlugin.autoImport.releaseCrossBuild := true

resolvers ++= Seq("Jasper" at "http://jasperreports.sourceforge.net/maven2")

libraryDependencies := Seq(
    "net.sf.jasperreports" % "jasperreports" % "6.8.0",
    "com.lowagie" % "itext" % "2.1.7",
    "org.scalatest" %% "scalatest" % "3.0.1" % "test",
)

import ReleaseTransformations._
releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  releaseStepCommandAndRemaining("^test"),
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("^publishSigned"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

releaseCrossBuild := false

publishTo := {
  if (isSnapshot.value)
    Some("snapshots" at "https://oss.sonatype.org/content/repositories/snapshots")
  else
    Some("releases" at "https://oss.sonatype.org/service/local/staging/deploy/maven2")
}

pomExtra := {
  <url>https://github.com/jeduardocosta/sbt-jasperreports</url>
    <licenses>
      <license>
        <name>Apache 2</name>
        <url>http://www.apache.org/licenses/LICENSE-2.0</url>
        <distribution>repo</distribution>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jeduardocosta/sbt-jasperreports.git</url>
      <connection>scm:git@github.com:jeduardocosta/sbt-jasperreports.git</connection>
    </scm>
    <developers>
      <developer>
        <id>jeduardocosta</id>
        <name>jeduardocosta</name>
        <url>http://github.com/jeduardocosta</url>
      </developer>
    </developers>
}