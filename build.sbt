name := "mailer-api"

version := "1.0"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "javax.mail" % "mail" % "1.4",
  "org.jvnet.mock-javamail" % "mock-javamail" % "1.9" % "test",
  "org.scalatest" % "scalatest_2.11" % "3.0.0-M15",
  "com.typesafe" % "config" % "1.3.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7"
)