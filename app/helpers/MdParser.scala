package helpers

import helpers.dom.*
import helpers.FileUtils

import scala.annotation.tailrec

case class MdParser(filepath: String):
  val content: String = if FileUtils.exists(filepath) then FileUtils.read_file(filepath) else ""

  val lines: List[String] = content.split('\n').map(_.trim).toList

  private def count_heading(contents: String): Int =
    def begin_with_space(contents: String): Boolean = contents.head == ' '
    @tailrec def ch(c: String, n: Int): Int = 
      if n < 6 then
        if c.head == '#' then ch(c.tail, n + 1) else
          if begin_with_space(c) then n else 0
      else 
        if begin_with_space(c) then n else 0
    ch(contents, 0)
    
  private def return_heading(line: String): Heading =
    val level = count_heading(line)
    val content = "^#{1,6} +".r.replaceFirstIn(line, "")
    level match
      case 1 => H1(content)
      case 2 => H2(content)
      case 3 => H3(content)
      case 4 => H4(content)
      case 5 => H5(content)
      case 6 => H6(content)
      
  private def return_paragraph(line: String): Paragraph =
    Paragraph(line)
      
  def parse: List[Element] =
    @tailrec def _parse(ls: List[String], acc: List[Element]): List[Element] = ls match
      case Nil => acc
      case _ =>
        if count_heading(ls.head) != 0 then
          _parse(ls.tail, acc :+ return_heading(ls.head))
        else _parse(ls.tail, acc :+ return_paragraph(ls.head))
    _parse(lines, List())