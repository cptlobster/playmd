package controllers

import com.github.rjeschke.txtmark.Processor
import com.typesafe.config.Config

import java.nio.file.{Files, Paths}

import javax.inject._
import play.api._
import play.api.mvc._
import play.twirl.api.Html

/**
 * This controller creates an `Action` to handle HTTP requests to any Markdown sourced pages. They will be converted
 * into HTML and sent to the user.
 */
@Singleton
class LegacyPageController @Inject()(config: Configuration, val controllerComponents: ControllerComponents) extends BaseController {
  def get_links(path: String): List[Map[String, String]] = {
    val text = config.get[Seq[String]]("links.text")
    val href = config.get[Seq[String]]("links.href")
    val links = text.zip(href)
    (for (l <- links) yield {
      Map("name" -> l._1,
        "href" -> helpers.LinkParser(path).rootString(l._2)
      )
    }).toList
  }
  def assemble(path: String)(content: Html): Html = {
    val title = config.get[String]("title")
    val theme = config.get[String]("theme")
    views.html.main(path, title, theme, get_links(path))(content)
  }

  def exists(path: String): Boolean = Files.exists(Paths.get("public/" ++ path))

  def read_file(path: String): String = {
    val source = scala.io.Source.fromFile("public/" ++ path)
    try source.mkString finally source.close()
  }

  def find_or_404(path: String)(callback: () => Result): Result = {
    if (exists(path)) {
      callback()
    } else {
      NotFound(assemble(path)(Html("<h1>Not found</h1><pre>" ++ path ++ "</pre>")))
    }
  }
  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def read_page(path: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    find_or_404(path)(() => {
      val lines = read_file(path)
      Ok(assemble(path)(Html(s"<pre>$lines</pre>")))
    })
  }

  def read_md(path: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    find_or_404(path)(() => {
      val result = Processor.process(read_file(path))
      Ok(assemble(path)(Html(s"<main>$result</main>")))
    })
  }

  def read_html(path: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    find_or_404(path)(() => {
      val lines = read_file(path)
      Ok(assemble(path)(Html(s"<main>$lines</main>")))
    })
  }

  def play_audio(path: String, encoding: String): Action[AnyContent] = Action { implicit request: Request[AnyContent] =>
    find_or_404(path)(() => {
      Ok(assemble(path)(Html(s"<main><h1>$path</h1><audio controls><source src='$path' type='$encoding'></audio></main>")))
    })
  }
}