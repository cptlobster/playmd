package helpers.dom

abstract class Heading extends Element:
  val level: Int
  override val tag = s"h$level"
  def auto_id: String = toString.replace(" ", "-")
  override val id = ""

case class H1(content: Content, id: String = "", classes: List[String] = List()) extends Heading:
  override val level: Int = 1
case class H2(content: Content, id: String = "", classes: List[String] = List()) extends Heading:
  override val level: Int = 2
case class H3(content: Content, id: String = "", classes: List[String] = List()) extends Heading:
  override val level: Int = 3
case class H4(content: Content, id: String = "", classes: List[String] = List()) extends Heading:
  override val level: Int = 4
case class H5(content: Content, id: String = "", classes: List[String] = List()) extends Heading:
  override val level: Int = 5
case class H6(content: Content, id: String = "", classes: List[String] = List()) extends Heading:
  override val level: Int = 6
case class Paragraph(content: Content, id: String = "", classes: List[String] = List()) extends Element:
  override val tag: String = "p"