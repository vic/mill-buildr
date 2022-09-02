# [Mill](https://github.com/com-lihaoyi/mill) buildr module.

An small [Mill module](https://github.com/vic/mill-buildr/blob/main/BuildrModule.sc) that lets you define 
big project structures as a regular scala project itself.

Forget about large `build.sc` files or magic-importing lots of different `.sc` files, 
and use a normal `.scala` project you and your favorite IDE can understand and navigate easily.

## Build History

[![Build history](https://buildstats.info/github/chart/vic/mill-buildr?branch=main)](https://github.com/vic/mill-buildr/actions)

## Motivation

Splitting large `build.sc` files into different re-usable modules should be easy, and IDE navigation and code-completion should just work
as with any other regular scala project.


<details>



One of the cool things about Mill is that its build definitions are loaded from ammonite-magic-infused `build.sc` file.

However when projects innevitably grow big you might find the need to split that huge `build.sc` of yours into smaller `.sc` files.

Perhaps some `.sc` files for shared settings (eg, versions/ivyDeps/mvnRepos, scalac-options, common tasks, reusable mill-modules) 
and maybe others for the many components that make your big project (eg, models, daos, service-ifaces, web-server, etc).


However some IDEs/editors have difficulties finding code references between `.sc` files and not providing code-completion can
hurt developer experience specially for those who still need the IDE to help with completions and moving around files.


This tiny project was a hack we found thanks to ammonite magic imports that allowed us to split the build definition into a
scala project on its own right. So that anyone who knows a little of scala can edit the project definition comfortably from their IDE.


</details>

## Usage

All you need from this repo is a single file: `BuildrModule.sc`. 
You can either [`download it`](https://raw.githubusercontent.com/vic/mill-buildr/main/BuildrModule.sc) alongside your
`build.sc` file. 
Or choose to import it directly from github - if doing so, please ensure to reference it on a particular commit -.

Then, define a `buildr` project where all your project modules will live as `.scala` files.
And finally load the `buildr` runtime so you can define top-level objects in mill.


<!-- Keep updated with the raw contents of build.sc from this repo. -->
```scala
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

@ // This ammonite marker is *very important* as it separate compilation units.

// All set. Now you can import your compiled mill-modules and
// (for this example see code at `buildr/src/buildr/ProjectOne.scala`)
import _root_.buildr.{ProjectOne, ProjectTwo, ProjectThree}

// That's it. All your `build.sc` file does is define the top level objects.
object one extends ProjectOne
object two extends ProjectTwo
object three extends ProjectThree
```

See the full example on this repository's `build.sc` file. Try running `mill '__.{one,two}'`
