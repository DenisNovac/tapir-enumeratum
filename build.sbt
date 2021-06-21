lazy val root = project
  .in(file("."))
  .settings(
    name := "tapir-enum",
    version := "0.1.0",
    scalaVersion := "2.13.6",
    libraryDependencies += "io.circe"                    %% "circe-core"       % "0.14.1",
    libraryDependencies += "io.circe"                    %% "circe-generic"    % "0.14.1",
    libraryDependencies += "com.beachape"                %% "enumeratum"       % "1.7.0",
    libraryDependencies += "com.beachape"                %% "enumeratum-circe" % "1.7.0",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-core"       % "0.18.0-M17",
    libraryDependencies += "com.softwaremill.sttp.tapir" %% "tapir-enumeratum" % "0.18.0-M17"
  )
