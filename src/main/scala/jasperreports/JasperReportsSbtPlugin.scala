package jasperreports

import sbt.Keys._
import sbt.{Def, _}

class JasperReportsSbtPlugin extends AutoPlugin {
  val GroupId = "com.jeduardocosta.jasperreports"
  val ArtifactId = "scalac-jasperreports-plugin"

  val autoImport: JasperReportsKeys.type = JasperReportsKeys
  import autoImport._

  override def trigger = allRequirements

  override def projectSettings: Seq[Def.Setting[_]] = {
    Seq(
      jasperReportsInstance := new JasperReports(streams.value.log),

      jasperReportsCompile := jasperReportsInstance.value.compileFiles(
        jasperReportsInputFolder.value, jasperReportsOutputFolder.value),

      jasperReportsCleanOutputFiles := jasperReportsInstance.value.cleanFiles(jasperReportsOutputFolder.value))
  }
}