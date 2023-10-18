package controllers

import javax.inject._
import play.api._
import play.api.mvc._

/**
 * This controller creates an `Action` to handle HTTP requests to any Markdown sourced pages. They will be converted
 * into HTML and sent to the user.
 */
@Singleton
class MarkdownController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  /**
   * Create an Action to render an HTML page.
   *
   * The configuration in the `routes` file means that this method
   * will be called when the application receives a `GET` request with
   * a path of `/`.
   */

  def md_page(path: String) = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.page(path))
  }
}