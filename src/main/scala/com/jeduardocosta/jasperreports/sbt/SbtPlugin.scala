package com.jeduardocosta.jasperreports.sbt

import java.io.File

import net.sf.jasperreports.engine.JasperCompileManager
import sbt.{Def, _}
import sbt.Keys._

import scala.util.{Failure, Success, Try}

class SbtPlugin extends AutoPlugin {
  val GroupId = "com.jeduardocosta.jasperreports"
  val ArtifactId = "scalac-jasperreports-plugin"

  object autoImport {
    val jasperReportsInstance = taskKey[JasperReports]("Holds instance of JasperReports")

    lazy val jasperReportsCompile = taskKey[Unit]("Generates jasper files using jrxml files")
    lazy val jasperReportsCleanOutputFiles = taskKey[Unit]("Cleans jasper files")

    lazy val jasperReportsInputPath = settingKey[String]("Directory where jrxml files will be read")
    lazy val jasperReportsOutputPath = settingKey[String]("Directory where jasper files will be written")
  }

  import autoImport._

  override def trigger = allRequirements

  override def projectSettings: Seq[Def.Setting[_]] = {
    Seq(
      jasperReportsInstance := new JasperReports(streams.value.log),

      jasperReportsCompile := jasperReportsInstance.value.compileFiles(
        jasperReportsInputPath.value, jasperReportsOutputPath.value),

      jasperReportsCleanOutputFiles := jasperReportsInstance.value.cleanFiles(jasperReportsOutputPath.value))
  }

  private def performCompileFiles(path: String): Unit = {
    getFilesFromPath(path)
      .filter(_.getName.contains("jrxml"))
      .foreach(compileJasperFile)
  }

  private def performCleanOutputFiles(path: String): Unit = {
    val files = getFilesFromPath(path)
    IO.delete(files)
  }

  private def compileJasperFile(file: File): Unit = {
    val jasperPath = getClass.getClassLoader.getResource(file.getAbsolutePath)
    val jasperCompiledFileName = file.getName.replace(".jrxml", ".jasper")
    val jasperCompiledPath = s"$jasperReportsOutputPath/$jasperCompiledFileName"

    Try(JasperCompileManager.compileReportToFile(jasperPath.getPath, jasperCompiledPath)) match {
      case Success(_) =>
        //streams.value.log.debug(s"[sbt-jasper] The jasper file was generated from xmlPath: $jasperPath")
      case Failure(error) =>
        //streams.value.log.err(s"[sbt-jasper] Failure to generate jasper file from xmlPath: $jasperPath, error: ${error.getStackTrace.mkString}")
    }
  }

  private def getFilesFromPath(path: String): List[File] = {
    Try(new File(path)) match {
      case Success(file) =>
        if (file.exists && file.isDirectory) file.listFiles.filter(_.isFile).toList
        else List.empty
      case Failure(_) =>
        //streams.value.log.warn(s"[sbt-jasper] It was not possible to extract files from path=$path")
        List.empty
    }
  }
}