import sbt._
import Keys._

object BuildSettings {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    organization := "com.suguruhamazaki",
    version := "1.0-SNAPSHOT",
    scalaVersion := "2.11.0",
    scalacOptions ++= Seq("-deprecation", "-unchecked", "-feature", "-encoding", "utf8", "-Xlint")
  )
}

object Dependencies {
  val akkaVersion = "2.3.2"

  val scalaz = "org.scalaz" %% "scalaz-core" % "7.0.6"
  val akkaActor = "com.typesafe.akka" %% "akka-actor" % akkaVersion
  val akkaSlf4j = "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
  val akkaTestkit = "com.typesafe.akka" %% "akka-testkit" % akkaVersion % "test"
  val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging-slf4j" % "2.1.2"
  val logback = "ch.qos.logback" % "logback-classic" % "1.1.2"
  val scalatest = "org.scalatest" %% "scalatest" % "2.1.4" % "test"
  val pegdown = "org.pegdown" % "pegdown" % "1.0.1" % "test"
  val mockito = "org.mockito" % "mockito-core" % "1.9.5" % "test"

  val commonDeps = Seq(
    scalaz,
    akkaActor,
    akkaSlf4j,
    akkaTestkit,
    scalaLogging,
    logback,
    scalatest,
    pegdown,
    mockito
  )
}

object ScalaFpCalisthenicsBuild extends Build {

  import BuildSettings.buildSettings
  import Dependencies.commonDeps
  import org.scalastyle.sbt.ScalastylePlugin.{Settings => ScalastyleSettings}
  import ScctPlugin.instrumentSettings

  lazy val root = Project("scala-fp-calisthenics",
			  file("."),
                          settings = buildSettings ++ instrumentSettings ++ ScalastyleSettings) settings (
                    libraryDependencies ++= commonDeps,
                    testOptions in Test += Tests.Argument(TestFrameworks.ScalaTest, "-h", "target/report", "-o"))
}
