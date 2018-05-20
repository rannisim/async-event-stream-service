name := "AsyncEventStreamService"

version := "1.0"

lazy val `asynceventstreamservice` = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.6"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  guice)

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  