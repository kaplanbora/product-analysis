ThisBuild / scalaVersion := "2.12.8"
ThisBuild / version := "1.0.0"
ThisBuild / organization := "dev.kaplan"

val flinkVersion = "1.9.0"

lazy val root = (project in file("."))
  .settings(
    name := "product-analysis",
    libraryDependencies ++= Seq(
      "ch.qos.logback" % "logback-classic" % "1.2.3",
      "org.apache.flink" %% "flink-scala" % flinkVersion % Provided,
      "org.apache.flink" %% "flink-clients" % flinkVersion % Provided,
      "org.scalatest" %% "scalatest" % "3.0.8" % Test
    )
  )

parallelExecution in Test := false
