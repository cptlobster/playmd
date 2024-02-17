package controllers

import com.typesafe.config.Config

import javax.inject.*
import play.api.*
import play.api.mvc.*
import play.twirl.api.Html
import helpers._

/**
 * This controller creates an `Action` to handle HTTP requests to any Markdown sourced pages. They will be converted
 * into HTML and sent to the user.
 */
@Singleton
class PageController @Inject()(config: Configuration, val controllerComponents: ControllerComponents) extends BaseController:
  def get_links(path: String): List[Map[String, String]] =
    val text = config.get[Seq[String]]("links.text")
    val href = config.get[Seq[String]]("links.href")
    val links = text.zip(href)
    (for (l <- links) yield {
      Map("name" -> l._1,
        "href" -> LinkParser(path).rootString(l._2)
      )
    }).toList
  
  def assemble(path: String)(content: Html): Html =
    val title = config.get[String]("title")
    views.html.main(title, get_links(path))(content)

  def find_or_404(path: String)(callback: () => Result): Result =
    if FileUtils.exists(path) then callback()
    else NotFound(assemble(path)(Html("<h1>File not found</h1><pre>" ++ path ++ "</pre>")))

  private def get_generator(path: String): PageGen =
    if FileUtils.is_dir(path) then DirTreeGen(path) else
      val exp = "\\.([a-zA-Z0-9]+)$".r.findAllIn(path).matchData.toList.last.group(1)
      exp match
        case "md" => MdGen(path)
        case "html" => HtmlGen(path)
        case "mp3" => AudioGen(path, "audio/mpeg")
        case "ogg" => AudioGen(path, "audio/ogg")
        case "wav" => AudioGen(path, "audio/wav")
        case _ => TextGen(path)

  def read_page(path: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    find_or_404(path)(() => {
      val lines = FileUtils.read_file(path)
      Ok(assemble(path)(Html(s"<pre>$lines</pre>")))
    })
  }