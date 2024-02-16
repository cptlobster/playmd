package helpers.dom

case class Span(content: List[String | Element], id: String = "", classes: List[String] = List()) extends Element:
  override val tag: String = "span"
case class Bold(content: List[String | Element]) extends Element:
  override val tag: String = "b"
case class Italic(content: List[String | Element]) extends Element:
  override val tag: String = "i"