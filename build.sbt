val commonSettings = Seq(
  organization := "co.subneon",
  scalaVersion := "3.0.0",
  scalacOptions ++= List(
    "-unchecked",
    "-deprecation",
    "-encoding",
    "UTF8"
  )
)

val companiesFilter = project
  .in(file("."))
  .settings(commonSettings)
  .settings(
    version := "0.0.1",
    libraryDependencies ++= Seq(
      "org.scalatest"  %% "scalatest"  % "3.2.9"  % Test,
      "org.scalacheck" %% "scalacheck" % "1.15.4" % Test
    )
  )