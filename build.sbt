name := """play-veggieburger-api-items"""
organization := "com.bgasparotto"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.12.4"

libraryDependencies += guice

libraryDependencies += javaJdbc

libraryDependencies += "com.h2database" % "h2" % "1.4.192"

libraryDependencies ++= Seq(
  "org.jooq" % "jooq" % "3.10.5",
  "org.jooq" % "jooq-meta" % "3.10.5",
  "org.jooq" % "jooq-codegen" % "3.10.5"
)

// DbUnit for persitence unit testing.
libraryDependencies += "org.dbunit" % "dbunit" % "2.5.4" % "test"