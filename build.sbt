scalaVersion := "2.10.4"

scalacOptions ++= Seq(
  "-deprecation"
)

libraryDependencies ++= Seq(
  "org.scalaz" %% "scalaz-core" % "7.1.0-M6",
  "org.scalaz" %% "scalaz-effect" % "7.1.0-M6",
  "org.scalaz" %% "scalaz-scalacheck-binding" % "7.1.0-M6",
  "org.scalacheck" %% "scalacheck" % "1.11.4"
)

typelevelConsumerSettings
