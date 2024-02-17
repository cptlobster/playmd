package helpers

import java.nio.file.{FileSystems, Files, Paths}
import scala.jdk.CollectionConverters._

object FileUtils:
  def exists(path: String): Boolean = Files.exists(pubpath(path))
  def is_dir(path: String): Boolean = Files.isDirectory(pubpath(path))
  def list_dir(path: String): List[String] =
    val fp = FileSystems.getDefault.getPath("public/" ++ path)
    Files.list(fp).iterator().asScala.map(f => f.toString).toList

  private def pubpath(path: String) = {
    Paths.get("public/" ++ path)
  }

  def read_file(path: String): String =
    val source = scala.io.Source.fromFile("public/" ++ path)
    try source.mkString finally source.close()
