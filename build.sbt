name := "OracleTest"

version := "0.1"

scalaVersion :=  "2.12.15"

libraryDependencies ++= Seq(
  "org.scalactic" %% "scalactic" % "3.2.10",
  "org.scalatest" %% "scalatest" % "3.2.10" % "test",
  "dev.zio" %% "zio" % "1.0.12",
  "dev.zio" %% "zio-streams" % "1.0.12",
  "dev.zio" %% "zio-test" % "1.0.12" % "test",
  "dev.zio" %% "zio-test-sbt" % "1.0.12" % "test"
)