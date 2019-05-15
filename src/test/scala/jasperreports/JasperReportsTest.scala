package jasperreports

import java.io.File

import org.scalatest.{BeforeAndAfter, FeatureSpec, Matchers}

class JasperReportsTest extends FeatureSpec
  with Matchers
  with BeforeAndAfter {

  val inputFolder = "/input"
  val outputFolder = "/output"
  val log = new LogMock()

  feature("Compile files") {
    scenario("Try to compile files from input path") {
      new JasperReports(log).compileFiles(inputFolder, outputFolder)

      val outputFilePath = getClass
        .getClassLoader
        .getResource("")
        .getPath
        .concat(s"$outputFolder/sample.jasper")

      val file = new File(outputFilePath)
      file.isFile shouldBe true
      file.exists shouldBe true
      file.delete shouldBe true
    }
  }

  feature("Clean files") {
    scenario("Try to clean files from path") {
      val jasperReports = new JasperReports(log)
      jasperReports.compileFiles(inputFolder, outputFolder)

      val outputPath = getClass
        .getClassLoader
        .getResource("")
        .getPath
        .concat(outputFolder)

      jasperReports.cleanFiles(outputPath)

      val outputFilePath = outputPath.concat("/sample.jasper")

      new File(outputFilePath).exists shouldBe false
    }
  }
}