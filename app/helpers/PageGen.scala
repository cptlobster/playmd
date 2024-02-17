package helpers

import com.github.rjeschke.txtmark.Processor

import helpers.FileUtils

abstract class PageGen:
  def assemble: String


case class TextGen(path: String) extends PageGen:
  val content: String = FileUtils.read_file(path)
  def assemble: String = s"<main><pre>$content</pre></main>"

case class HtmlGen(path: String) extends PageGen:
  val content: String = FileUtils.read_file(path)
  override def assemble: String = s"<main>$content</main>"

case class MdGen(path: String) extends PageGen:
  val content: String = FileUtils.read_file(path)
  private val parsed: String = Processor.process(content)
  override def assemble: String = s"<main>$parsed</main>"
  
case class AudioGen(path: String, encoding: String) extends PageGen:
  override def assemble: String = 
    s"""
       |<main>
       |  <audio controls preload="metadata">
       |  <source src="src/$path" type="$encoding"></audio>
       |</main>
       |""".stripMargin
  
case class DirTreeGen(path: String) extends PageGen:
  override def assemble: String =
    s"""
       |<main>
       |  <h1>Not supported</h1>
       |</main>
       |""".stripMargin