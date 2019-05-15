package jasperreports

import sbt._

object JasperReportsKeys {
  lazy val jasperReportsInstance = taskKey[JasperReports]("Holds instance of JasperReports")

  lazy val jasperReportsCompile = taskKey[Unit]("Generates jasper files using jrxml files")
  lazy val jasperReportsCleanOutputFiles = taskKey[Unit]("Cleans jasper files")

  lazy val jasperReportsInputPath = settingKey[String]("Directory where jrxml files will be read")
  lazy val jasperReportsOutputPath = settingKey[String]("Directory where jasper files will be written")
}
