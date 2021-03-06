package izumi.sbtgen

import izumi.sbtgen.model._

object Defaults {
  final val SharedOptions = Seq(
    "publishMavenStyle" in SettingScope.Build := true,
    "scalacOptions" in SettingScope.Build ++= Seq(
      "-encoding", "UTF-8",
      "-target:jvm-1.8",
      "-feature",
      "-unchecked",
      "-deprecation",
      "-language:higherKinds",
    ),
    "javacOptions" in SettingScope.Build ++= Seq(
      "-encoding", "UTF-8",
      "-source", "1.8",
      "-target", "1.8",
      "-deprecation",
      "-parameters",
      "-Xlint:all",
      "-XDignore.symbol.file"
    ),
    "scalacOptions" in SettingScope.Build ++= Seq(
      """s"-Xmacro-settings:product-version=${version.value}"""".raw,
      """s"-Xmacro-settings:product-group=${organization.value}"""".raw,
      """s"-Xmacro-settings:sbt-version=${sbtVersion.value}"""".raw,
    )
  )

  final val Scala212Options = Seq(
    "-Xsource:2.13"
    , "-Ybackend-parallelism", math.max(1, sys.runtime.availableProcessors() / 2).toString
    , "-explaintypes" // Explain type errors in more detail.

    , "-Ypartial-unification" // 2.12 only
    , "-Yno-adapted-args" // 2.12 only

    , "-Xlint:adapted-args" // Warn if an argument list is modified to match the receiver.
    , "-Xlint:by-name-right-associative" // By-name parameter of right associative operator.
    , "-Xlint:constant" // Evaluation of a constant arithmetic expression results in an error.
    , "-Xlint:delayedinit-select" // Selecting member of DelayedInit.
    , "-Xlint:doc-detached" // A Scaladoc comment appears to be detached from its element.
    , "-Xlint:inaccessible" // Warn about inaccessible types in method signatures.
    , "-Xlint:infer-any" // Warn when a type argument is inferred to be `Any`.
    , "-Xlint:missing-interpolator" // A string literal appears to be missing an interpolator id.
    , "-Xlint:nullary-override" // Warn when non-nullary `def f()' overrides nullary `def f'.
    , "-Xlint:nullary-unit" // Warn when nullary methods return Unit.
    , "-Xlint:option-implicit" // Option.apply used implicit view.
    , "-Xlint:package-object-classes" // Class or object defined in package object.
    , "-Xlint:poly-implicit-overload" // Parameterized overloaded implicit methods are not visible as view bounds.
    , "-Xlint:private-shadow" // A private field (or class parameter) shadows a superclass field.
    , "-Xlint:stars-align" // Pattern sequence wildcard must align with sequence component.
    , "-Xlint:type-parameter-shadow" // A local type parameter shadows a type already in scope.
    , "-Xlint:unsound-match" // Pattern match may not be typesafe.

    , "-opt-warnings:_" //2.12 only
    , "-Ywarn-extra-implicit"        // Warn when more than one implicit parameter section is defined.
    , "-Ywarn-unused:_"              // Enable or disable specific `unused' warnings: `_' for all, `-Ywarn-unused:help' to list choices.
    , "-Ywarn-adapted-args" // Warn if an argument list is modified to match the receiver.
    , "-Ywarn-dead-code" // Warn when dead code is identified.
    , "-Ywarn-inaccessible" // Warn about inaccessible types in method signatures.
    , "-Ywarn-infer-any" // Warn when a type argument is inferred to be `Any`.
    , "-Ywarn-nullary-override" // Warn when non-nullary `def f()' overrides nullary `def f'.
    , "-Ywarn-nullary-unit" // Warn when nullary methods return Unit.
    , "-Ywarn-numeric-widen" // Warn when numerics are widened.
    //, "-Ywarn-self-implicit"
    , "-Ywarn-unused-import" // Warn when imports are unused.
    , "-Ywarn-value-discard" // Warn when non-Unit expression results are unused.
  )

  final val Scala213Options = Seq(
    //        "-Xsource:3.0", // is available
    //        "-Xsource:2.14", // Delay -Xsource:2.14 due to spurious warnings https://github.com/scala/bug/issues/11639
    "-Xsource:2.13",
    "-Ybackend-parallelism", math.max(1, sys.runtime.availableProcessors() / 2).toString,
    "-explaintypes", // Explain type errors in more detail.

    "-Xlint:_",

    "-Wunused:_",
    "-Wdead-code",
    "-Wextra-implicit",
    "-Wnumeric-widen",
    "-Woctal-literal",
    //        "-Wself-implicit", // Spurious warnings for any top-level implicit, including scala.language._
    "-Wvalue-discard",
  )

  final val SbtGenPlugins = Seq(
    SbtPlugin("io.7mind.izumi.sbt", "sbt-izumi", Version.SbtGen),
    SbtPlugin("io.7mind.izumi.sbt", "sbt-izumi-deps", Version.SbtGen),
  )

  final val SbtMeta = Seq(
    "scalacOptions" ++= Seq(
      """s"-Xmacro-settings:scala-version=${scalaVersion.value}"""".raw,
      """s"-Xmacro-settings:scala-versions=${crossScalaVersions.value.mkString(":")}"""".raw,
    )
  )
}
