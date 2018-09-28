name := "apibuilder-validation"

organization := "io.flow"

scalaVersion in ThisBuild := "2.12.7"

crossScalaVersions := Seq("2.12.7", "2.11.12")

version := "0.2.1"

lazy val root = project
  .in(file("."))
  .settings(
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.6.10",
      "org.scalatest" %% "scalatest" % "3.0.5" % Test
    )
  )

publishTo := {
  val host = "https://flow.artifactoryonline.com/flow"
  if (isSnapshot.value) {
    Some("Artifactory Realm" at s"$host/libs-snapshot-local;build.timestamp=" + new java.util.Date().getTime)
  } else {
    Some("Artifactory Realm" at s"$host/libs-release-local")
  }
}
