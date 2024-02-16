package helpers

import java.nio.file.{Files, Paths}

object FileUtils:
  def exists(path: String): Boolean = Files.exists(Paths.get("public/" ++ path))
  def read_file(path: String): String =
    val source = scala.io.Source.fromFile("public/" ++ path)
    try source.mkString finally source.close()

