name := """play-veggieburger-api-items"""
organization := "com.bgasparotto"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies += guice

libraryDependencies ++= Seq(
  "org.jooq" % "jooq" % "3.10.5",
  "org.jooq" % "jooq-meta" % "3.10.5",
  "org.jooq" % "jooq-codegen" % "3.10.5"
)