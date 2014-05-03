import sbt._

object build extends Build {
  lazy val root = Project("plugins", file(".")) dependsOn(
    uri("git://github.com/hamazy/asciidoc2docx.git")
  )
}
