name := "ScalaIntro"

version := "0.1"

scalaVersion := "2.12.8"

libraryDependencies ++= {
  Seq(
    "org.scalatest" %% "scalatest" % "3.0.5" % "test",
    "au.com.bytecode" % "opencsv" % "2.4"
  )
}