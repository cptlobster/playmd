ThisBuild / scalaVersion := "3.3.1"

ThisBuild / version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayScala)
  .settings(
    name := """playmd""",
    libraryDependencies ++= Seq(
      guice,
      "es.nitaur.markdown" % "txtmark" % "0.16",
    )
  )
