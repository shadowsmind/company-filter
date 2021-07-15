val commonSettings = Seq(
  organization := "co.subneon",
  scalaVersion := "2.13.6",
  scalacOptions ++= List(
    "-unchecked",
    "-deprecation",
    "-encoding",
    "UTF8"
  ),
  run := Defaults.runTask(Runtime / fullClasspath, Compile / run / mainClass, run / runner).evaluated
)

val projectDependencies = {
  val fs2Version        = "3.0.6"
  val fs2DataVersion    = "1.0.0"
  val circeVersion      = "0.14.1"
  val scalatestVersion  = "3.2.9"

  Seq(
    "co.fs2"         %% "fs2-core"             % fs2Version,
    "co.fs2"         %% "fs2-io"               % fs2Version,
    "org.gnieh"      %% "fs2-data-csv"         % fs2DataVersion,
    "org.gnieh"      %% "fs2-data-csv-generic" % fs2DataVersion,
    "io.circe"       %% "circe-core"           % circeVersion,
    "io.circe"       %% "circe-generic"        % circeVersion,
    "io.circe"       %% "circe-parser"         % circeVersion,
    "org.scalatest"  %% "scalatest"            % scalatestVersion  % Test
  )
}

val companiesFilter = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    version := "0.0.1",
    libraryDependencies ++= projectDependencies
  )
