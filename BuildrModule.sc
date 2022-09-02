import mill._
import scalalib._
import scala.util.Properties

trait BuildrModule extends ScalaModule {
  override def scalaVersion = BuildInfo.scalaVersion

  override def ivyDeps =
    super.ivyDeps() ++ BuildInfo.millEmbeddedDeps.map(Dep.parse)

  private def logger = {
    val colors = interp.colors()
    new mill.util.PrintLogger(
      colors != ammonite.util.Colors.BlackWhite,
      false,
      colors.info(),
      colors.error(),
      System.out,
      System.err,
      System.err,
      System.in,
      debugEnabled = true,
      context = ""
    )
  }

  private def evaluator(rootModule: define.BaseModule) = {
    mill.eval.Evaluator(
      Properties
        .envOrNone("AMMONITE_HOME")
        .map(os.Path(_))
        .getOrElse(os.home / ".mill" / "ammonite"),
      os.pwd / "out" / "buildr-eval",
      os.pwd / "out" / "buildr-eval",
      rootModule = rootModule,
      baseLogger = logger
    )
  }

  def loadRuntime(rootModule: define.BaseModule): Unit = {
    val eval  = mill.eval.Evaluator.evalOrThrow(evaluator(rootModule))
    val paths = eval[Seq[PathRef]](runClasspath)
    interp.load.cp(paths.map(_.path))
  }
}
