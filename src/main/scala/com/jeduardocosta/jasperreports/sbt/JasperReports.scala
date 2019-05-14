package com.jeduardocosta.jasperreports.sbt

import java.io.File

import net.sf.jasperreports.engine.JasperCompileManager
import sbt.IO
import sbt.internal.util.ManagedLogger

import scala.util.{Failure, Success, Try}

class JasperReports(logger: ManagedLogger) {

  def compileFiles(inputPath: String, outputPath: String): Unit = {
    getFilesFromPath(inputPath)
      .filter(_.getName.contains("jrxml"))
      .foreach(file => compileJasperFile(file, outputPath))
  }

  def cleanFiles(path: String): Unit = {
    val files = getFilesFromPath(path)
    IO.delete(files)
  }

  private def compileJasperFile(file: File, outputPath: String): Unit = {
    val jasperPath = getClass.getClassLoader.getResource(file.getAbsolutePath)
    val jasperCompiledFileName = file.getName.replace(".jrxml", ".jasper")
    val jasperCompiledPath = s"$outputPath/$jasperCompiledFileName"

    Try(JasperCompileManager.compileReportToFile(jasperPath.getPath, jasperCompiledPath)) match {
      case Success(_) =>
        logger.debug(s"[sbt-jasper] The jasper file was generated from xmlPath: $jasperPath")
      case Failure(error) =>
        logger.err(s"[sbt-jasper] Failure to generate jasper file from xmlPath: $jasperPath, error: ${error.getStackTrace.mkString}")
    }
  }

  private def getFilesFromPath(path: String): List[File] = {
    Try(new File(path)) match {
      case Success(file) =>
        if (file.exists && file.isDirectory) file.listFiles.filter(_.isFile).toList
        else List.empty
      case Failure(_) =>
        logger.warn(s"[sbt-jasper] It was not possible to extract files from path=$path")
        List.empty
    }
  }
}