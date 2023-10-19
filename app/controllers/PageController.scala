package controllers

import com.github.rjeschke.txtmark.Processor
import com.typesafe.config.Config

import javax.inject._
import play.api._
import play.api.mvc._
import play.twirl.api.Html

/**
 * This controller creates an `Action` to handle HTTP requests to any Markdown sourced pages. They will be converted
 * into HTML and sent to the user.
 */
@Singleton
class PageController @Inject()(config: Configuration, val controllerComponents: ControllerComponents) extends BaseController {
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

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */
  def read_page(path: String) = Action { implicit request: Request[AnyContent] =>
    val source = scala.io.Source.fromFile("public/" ++ path)
    val lines = try source.mkString finally source.close()
    val title = config.get[String]("title")
    Ok(views.html.main(title, get_links(path))(Html("<pre>" ++ lines ++ "</pre>")))
  }

  def read_md(path: String) = Action { implicit request: Request[AnyContent] =>
    val source = scala.io.Source.fromFile("public/" ++ path)
    val lines = try source.mkString finally source.close()
    val result = Processor.process(lines)
    val title = config.get[String]("title")
    Ok(views.html.main(title, get_links(path))(Html("<main>" ++ result ++ "</main>")))
  }

  def read_html(path: String) = Action { implicit request: Request[AnyContent] =>
    val source = scala.io.Source.fromFile("public/" ++ path)
    val lines = try source.mkString finally source.close()
    val title = config.get[String]("title")
    Ok(views.html.main(title, get_links(path))(Html("<main>" ++ lines ++ "</main>")))
  }
}