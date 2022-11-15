import mill._, scalalib._
import java.nio.file.Paths

object chisel extends ScalaModule {
  def scalaVersion = "2.13.10"
  val chisel_version = "3.5.5"

  def millSourcePath = super.millSourcePath / os.up

  override def scalacOptions = Seq(
    "-language:reflectiveCalls",
    "-deprecation",
    "-feature",
    "-Xcheckinit",
    "-P:chiselplugin:genBundleElements"
  )

  override def ivyDeps = Agg(
    ivy"edu.berkeley.cs::chisel3:${chisel_version}",
  )

  override def scalacPluginIvyDeps = Agg(
    ivy"edu.berkeley.cs:::chisel3-plugin:${chisel_version}",
  )

  def unmanagedClasspath = T {
    val lib_path = T.ctx.env.get("MILL_LIB") match {
      case Some(lib) => os.Path(Paths.get(lib).toAbsolutePath)
      case None      => millSourcePath / "lib"
    }
    if (!os.exists(lib_path)) Agg()
    else Agg.from(os.list(lib_path).map(PathRef(_)))
  }
}
