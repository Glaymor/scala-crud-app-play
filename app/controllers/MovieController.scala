package controllers

import models.{Movie, NewMovie}
import play.api.libs.json._
import play.api.mvc._
import services.MovieService

import javax.inject._
import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

@Singleton
class MovieController @Inject()(val movieService: MovieService,
                                val controllerComponents: ControllerComponents)
                               (implicit val ec: ExecutionContext)
  extends BaseController {

  implicit val ListJson: OFormat[Movie] = Json.format[Movie]
  implicit val newListJson: OFormat[NewMovie] = Json.format[NewMovie]

  def info: Action[AnyContent] = Action {
    Ok(views.html.info())
  }

  def getAll: Action[AnyContent] = Action.async {
    movieService.getAll.map{
      movie => Ok(Json.toJson(movie))
    }
  }

  def get(id: Int): Action[AnyContent] = Action.async {
    movieService.get(id).map {
      case Some(movie) => Ok(Json.toJson(movie))
      case None => NotFound
    }
  }

  def add: Action[AnyContent] = Action.async { implicit request =>
    val content = request.body
    val jsonObject = content.asJson
    val newMovie: Option[NewMovie] = jsonObject.flatMap(
      Json.fromJson[NewMovie](_).asOpt
    )

    newMovie match {
      case Some(newMovie) =>
        val movie = Movie(1, newMovie.name)
        movieService.add(movie).map{
          case 1 => Ok(Json.toJson("Successfully"))
          case _ => BadRequest
        }
      case None => Future{BadRequest}
    }
  }

  def delete(id: Int): Action[AnyContent] = Action.async {
    movieService.delete(id).map{
      case 1 => Ok(Json.toJson("Successfully"))
      case _ => BadRequest
    }
  }

  def update(id: Int): Action[AnyContent] = Action.async { implicit request =>
    val content = request.body
    val jsonObject = content.asJson
    val newMovie: Option[NewMovie] = jsonObject.flatMap(
      Json.fromJson[NewMovie](_).asOpt
    )

    newMovie match {
      case Some(newMovie) =>
        val movie = Movie(id, newMovie.name)
        movieService.update(id, movie).map {
          case 1 => Ok(Json.toJson(movie))
          case _ => BadRequest
        }
      case None => Future{BadRequest}
    }
  }
}
