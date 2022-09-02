// This is build.sc file.

// 1) If you downloaded `BuildrModule.sc` locally:
import $file.{BuildrModule => bm}

// 1) If loading from github, be sure to reference a commit instead of main branch:
// import $url.{`https://raw.githubusercontent.com/vic/mill-buildr/main/BuildrModule.sc` => bm}

// 2) Define a mill module as usual, in this example `buildr/src/*.scala`
object buildr extends bm.BuildrModule {
  // customize as any other ScalaModule, define custom coursierRepos, add ivyDeps, etc.
}

// 3) Finally compile and load your build definition into the mill runtime.
buildr.loadRuntime(build) // `build` here is the root-module since this file is named `build.sc`

@
// This ammonite marker is *very important* as it separate compilation units.

// All set. Now you can import your compiled mill-modules and
// (for this example see code at `buildr/src/buildr/ProjectOne.scala`)
import _root_.buildr.{ProjectOne, ProjectTwo, ProjectThree}

// That's it. All your `build.sc` file does is define the top level objects.
object one   extends ProjectOne
object two   extends ProjectTwo
object three extends ProjectThree
