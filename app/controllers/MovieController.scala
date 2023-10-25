package controllers

import models.{MovieListItem, NewMovieListItem}
import play.api.libs.json._
import play.api.mvc._

import javax.inject._
import scala.collection.mutable.ListBuffer

@Singleton
class MovieController @Inject()(val controllerComponents: ControllerComponents)
  extends BaseController {

  implicit val moviesListJson: OFormat[MovieListItem] = Json.format[MovieListItem]
  implicit val newMoviesListJson: OFormat[NewMovieListItem] = Json.format[NewMovieListItem]


  private val moviesList = new ListBuffer[MovieListItem]()


  def getAll: Action[AnyContent] = Action {
    if (moviesList.isEmpty) {
      NoContent
    } else {
      Ok(Json.toJson(moviesList))
    }
  }

  def getById(movieId: Long): Action[AnyContent] = Action {
    val foundItem = moviesList.find(_.id == movieId)
    foundItem match {
      case Some(item) => Ok(Json.toJson(item))
      case None => NotFound
    }
  }

  def addNewItem(): Action[AnyContent] = Action { implicit request =>
    val content = request.body
    val jsonObject = content.asJson
    val movieListItem: Option[NewMovieListItem] =
      jsonObject.flatMap(
        Json.fromJson[NewMovieListItem](_).asOpt
      )

    movieListItem match {
      case Some(newItem) =>
        val nextId = moviesList.map(_.id).max + 1
        val toBeAdded = MovieListItem(nextId, newItem.name)
        moviesList += toBeAdded
        Created(Json.toJson(toBeAdded))
      case None => BadRequest
    }
  }

  def changeNameById(movieId: Long, newName: String): Action[AnyContent] = Action {
    val foundItem = moviesList.find(_.id == movieId)
    foundItem match {
      case Some(item) =>
        val newItem = item.copy(name = newName)
        moviesList.remove(item.id.toInt)
        moviesList += newItem
        Ok(Json.toJson(newItem))
      case None => NotFound
    }

  }

  def deleteById(movieId: Long): Action[AnyContent] = Action {
    val foundItem = moviesList.find(_.id == movieId)
    foundItem match {
      case Some(item) =>
        moviesList.remove(item.id.toInt)

        if (moviesList.isEmpty) {
          NoContent
        } else {
          Ok(Json.toJson(moviesList))
        }
      case None => NotFound
    }
  }

}
