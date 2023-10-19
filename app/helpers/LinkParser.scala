package helpers

import scala.annotation.tailrec

/**
 * Make markdown links relative to the project root.
 */
case class LinkParser(path: String) {
  val split_path: List[String] = path.split("/").toList
  val depth: Int = split_path.size - 1
  def rootMarkdown(original: String): Unit = {
    "\\[.*\\]\\((.*)\\)".r.replaceAllIn(original, rootString("") ++ "$1")
  }
  def rootString(original: String): String = {
    @tailrec def prepend_depth(n: Int, target: String): String = {
      n match {
        case 0 => target
        case _ => prepend_depth(n - 1, "../" ++ target)
      }
    }
    prepend_depth(depth, original)
  }
}
