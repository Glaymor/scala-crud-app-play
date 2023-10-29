package controllers

import models.{Movie, NewMovie}
import play.api.mvc._
import services.MovieService

import javax.inject._
import scala.concurrent.{Await, ExecutionContext}
import play.api.libs.json._

import scala.concurrent.duration.DurationInt
import scala.language.postfixOps

@Singleton
class MovieController @Inject()(val movieService: MovieService, val controllerComponents: ControllerComponents)(implicit val ec: ExecutionContext)
  extends BaseController {

  implicit val ListJson: OFormat[Movie] = Json.format[Movie]
  implicit val newListJson: OFormat[NewMovie] = Json.format[NewMovie]

  def getAll: Action[AnyContent] = Action {
    val result = Await.result(movieService.getAll, 1 minute)
    Ok(Json.toJson(result.toList))
  }

  def get(id: Int): Action[AnyContent] = Action {
    val result = Await.result(movieService.get(id), 1 minute)
    result match {
      case Some(movie) => Ok(Json.toJson(movie))
      case None => NotFound
    }
  }

  def add: Action[AnyContent] = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson
    val newMovie: Option[NewMovie] = jsonObject.flatMap(
      Json.fromJson[NewMovie](_).asOpt
    )

    newMovie match {
      case Some(newMovie) =>
        val movie = Movie(1, newMovie.name)
        val result = Await.result(movieService.add(movie), 1 minute)
        result match {
          case 1 => Ok(Json.toJson("Successfully"))
          case _ => BadRequest
        }
      case None => BadRequest
    }
  }

  def delete(id: Int): Action[AnyContent] = Action {
    val res = Await.result(movieService.delete(id), 1 minute)
    res match {
      case 1 => Ok(Json.toJson("Successfully"))
      case _ => BadRequest
    }
  }

  def update(id: Int): Action[AnyContent] = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson
    val newMovie: Option[NewMovie] = jsonObject.flatMap(
      Json.fromJson[NewMovie](_).asOpt
    )

    newMovie match {
      case Some(newMovie) =>
        val movie = Movie(id, newMovie.name)
        Await.result(movieService.update(id, movie), 1 minute)
        Ok(Json.toJson(movie))
      case None => BadRequest
    }
  }
}
