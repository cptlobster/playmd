package helpers.dom

import play.twirl.api.Html

import scala.annotation.tailrec

type Content = String | Element | List[String | Element]
abstract class Element:
  val content: Content
  val id: String = ""
  val classes: List[String] = List()
  val tag: String
  private val start: String =
    "<" + 
      tag +
      (if id.nonEmpty then s" id=\"$id\"" else "") +
      (if classes.nonEmpty then s" class=\"${classes.mkString(" ")}\"" else "") +
      ">"
  private val end: String = s"</$tag>"
  def assemble(autoid_headings: Boolean = false): String =
    val _content: List[String | Element] = content match
      case l: List[String | Element] => l
      case i: String => List(i)
      case i: Element => List(i)
    start +
      _content.map {
        case x: String => x
        case e: Element => e.assemble(autoid_headings)
      }
    + end
  override def toString: String = content match
    case l: List[String | Element] => l.map(_.toString).mkString(" ")
    case i: String => i
    case i: Element => i.toString