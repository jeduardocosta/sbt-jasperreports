package jasperreports

import java.io.File

import net.sf.jasperreports.engine.JasperCompileManager
import sbt.util.Logger

import scala.util.{Failure, Success, Try}

class JasperReports(logger: Logger) {

  def compileFiles(inputFolder: String, outputFolder: String): Unit = {
    getFilesFromFolder(inputFolder)
      .filter(_.getName.endsWith(".jrxml"))
      .foreach(file => compileJasperFile(file, outputFolder))
  }

  def cleanFiles(path: String): Unit = {
    Try(new File(path))
      .toOption
      .map(_.listFiles().map(_.delete()))
  }

  private def getFilesFromFolder(folder: String): List[File] = {
    try {
      val resourcePath = getClass.getResource(folder)
      val file = new File(resourcePath.getPath)

      if (file.exists && file.isDirectory) file.listFiles.filter(_.isFile).toList
      else List.empty
    } catch {
      case _: Exception =>
        logger.warn(s"[sbt-jasper] It was not possible to extract files from folder=$folder")
        List.empty
    }
  }

  private def compileJasperFile(file: File, outputPath: String): Unit = {
    val jasperInputPath = file.getAbsolutePath
    val jasperCompiledFileName = file.getName.replace(".jrxml", ".jasper")

    val jasperCompiledPath = getClass
      .getClassLoader
      .getResource("")
      .getPath
      .concat(s"$outputPath/$jasperCompiledFileName")

    Try(JasperCompileManager.compileReportToFile(jasperInputPath, jasperCompiledPath)) match {
      case Success(_) =>
        logger.debug(s"[sbt-jasper] The jasper file was generated from input path: $jasperInputPath")
      case Failure(error) =>
        logger.err(s"[sbt-jasper] Failure to generate jasper file from input path: $jasperInputPath, error: ${error.getStackTrace.mkString}")
    }
  }
}