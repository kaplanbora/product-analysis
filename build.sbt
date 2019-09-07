ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / organization := "dev.kaplan"

val flinkVersion = "1.9.0"

lazy val root = (project in file("."))
  .settings(
    name := "product-analysis",
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",
      "com.github.pureconfig" %% "pureconfig" % "0.11.0",
      "org.apache.flink" %% "flink-scala" % flinkVersion % Provided,
      "org.apache.flink" %% "flink-clients" % flinkVersion % Provided,
      "org.scalatest" %% "scalatest" % "3.0.8" % Test
    )
  )

enablePlugins(PackPlugin)

parallelExecution in Test := false