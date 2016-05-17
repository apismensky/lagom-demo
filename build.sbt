organization in ThisBuild := "sample.octanner"

lazy val userApi = project("user-api")
   .settings(
     version := "1.0-SNAPSHOT",
     libraryDependencies += lagomJavadslApi
   )
  .dependsOn(imageApi)

lazy val userImpl = project("user-impl")
   .enablePlugins(LagomJava)
   .settings(
     version := "1.0-SNAPSHOT",
     libraryDependencies += lagomJavadslPersistence
   )
  .dependsOn(userApi, utils, imageApi)

lazy val imageApi = project("image-api")
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomJavadslApi
  )

lazy val imageImpl = project("image-impl")
  .enablePlugins(LagomJava)
  .settings(
    version := "1.0-SNAPSHOT"
  )
  .dependsOn(imageApi, utils)

lazy val utils = project("utils")
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomJavadslApi
  )

def project(id: String) = Project(id, base = file(id))
  .settings(
    scalaVersion := "2.11.8",
    scalacOptions in Compile += "-Xexperimental", // this enables Scala lambdas to be passed as Java SAMs
    libraryDependencies ++= Seq(
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.7.3" // actually, only api projects need this
    ),
    javacOptions in compile ++= Seq("-encoding", "UTF-8", "-source", "1.8", "-target", "1.8", "-Xlint:unchecked", "-Xlint:deprecation")
  )
  .settings(jacksonParameterNamesJavacSettings: _*) // applying it to every project even if not strictly needed.

// See https://github.com/FasterXML/jackson-module-parameter-names
lazy val jacksonParameterNamesJavacSettings = Seq(
  javacOptions in compile += "-parameters"
)

