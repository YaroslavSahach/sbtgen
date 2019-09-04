package izumi.sbtgen.model

import scala.language.implicitConversions

sealed trait Version
object Version {

  case class VConst(value: String) extends Version

  case class VExpr(value: String) extends Version

}

sealed trait Scope {
  def jvm: FullDependencyScope = FullDependencyScope(this, Platform.Jvm)
  def js: FullDependencyScope = FullDependencyScope(this, Platform.Js)
  def native: FullDependencyScope = FullDependencyScope(this, Platform.Native)
  def all: FullDependencyScope = FullDependencyScope(this, Platform.All)
}

object Scope {

  case object Runtime extends Scope

  case object Optional extends Scope

  case object Provided extends Scope

  case object Compile extends Scope

  case object Test extends Scope

}

case class ScalaVersion(value: String)

sealed trait Platform

object Platform {

  sealed trait BasePlatform extends Platform

  case object Jvm extends BasePlatform

  case object Js extends BasePlatform

  case object Native extends BasePlatform

  case object All extends Platform


}

case class PlatformEnv(
                        platform: Platform.BasePlatform,
                        language: Seq[ScalaVersion],
                        settings: Seq[SettingDef] = Seq.empty,
                        plugins: Plugins = Plugins(Seq.empty, Seq.empty),
                      )

case class ArtifactId(value: String)

sealed trait LibraryType

object LibraryType {
  case object Invariant extends LibraryType
  case object AutoJvm extends LibraryType
  case object Auto extends LibraryType
}

case class Library(group: String, artifact: String, version: Version, kind: LibraryType)
object Library {
  def apply(group: String, artifact: String, version: String, kind: LibraryType = LibraryType.Auto): Library = new Library(group, artifact, Version.VConst(version), kind)
}

case class FullDependencyScope(scope: Scope, platform: Platform)

case class ScopedLibrary(dependency: Library, scope: FullDependencyScope, compilerPlugin: Boolean = false)

case class ScopedDependency(name: ArtifactId, scope: FullDependencyScope, mergeTestScopes: Boolean = false)

case class Group(name: String)

case class Artifact(
                     name: ArtifactId,
                     libs: Seq[ScopedLibrary],
                     depends: Seq[ScopedDependency],
                     pathPrefix: Seq[String] = Seq.empty,
                     platforms: Seq[PlatformEnv] = Seq.empty,
                     groups: Set[Group] = Set.empty,
                     subGroupId: Option[String] = None,
                     settings: Seq[SettingDef] = Seq.empty,
                     plugins: Plugins = Plugins(Seq.empty, Seq.empty),
                   )

case class Aggregate(
                      name: ArtifactId,
                      artifacts: Seq[Artifact],
                      pathPrefix: Seq[String] = Seq.empty,
                      groups: Set[Group] = Set.empty,
                      defaultPlatforms: Seq[PlatformEnv] = Seq.empty,
                    ) {
  def merge: Aggregate = {
    val newArtifacts = artifacts.map {
      a =>
        val newPlatforms = if (a.platforms.isEmpty) {
          defaultPlatforms
        } else {
          a.platforms
        }
        val newPrefix = if (a.pathPrefix.isEmpty) {
          pathPrefix
        } else {
          a.pathPrefix
        }
        a.copy(platforms = newPlatforms, pathPrefix = newPrefix)
    }
    this.copy(artifacts = newArtifacts)
  }
}



case class Import(value: String)

case class Plugin(name: String, platform: Platform = Platform.All)

case class Plugins(enabled: Seq[Plugin], disabled: Seq[Plugin] = Seq.empty) {
  def ++(o: Plugins): Plugins = {
    Plugins(enabled ++ o.enabled, disabled ++ o.disabled)
  }
}

case class Project(
                    name: ArtifactId,
                    aggregates: Seq[Aggregate],
                    settings: Seq[SettingDef] = Seq.empty,
                    imports: Seq[Import] = Seq.empty,
                    globalLibs: Seq[ScopedLibrary] = Seq.empty,
                    plugins: Plugins = Plugins(Seq.empty, Seq.empty),
                  )

