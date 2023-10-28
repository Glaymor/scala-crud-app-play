package repositories

import connections.PostgresConnection
import models.Movie

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}


class MovieRepository@Inject()()(implicit val ec: ExecutionContext) {

  import slick.jdbc.PostgresProfile.api._

  private class MovieTable(tag: Tag) extends Table[Movie](tag, "movies") {

    def id = column[Int]("id", O.PrimaryKey, O.AutoInc)

    def name = column[String]("name")

    // mapping function to the case class
    override def * = (id, name) <> ((Movie.apply _).tupled, Movie.unapply)
  }
  // API entry point
  private lazy val movieTable = TableQuery[MovieTable]


  def getAll: Future[Seq[Movie]] = {
    PostgresConnection.db.run(movieTable.result)
  }

  def get(id: Int): Future[Option[Movie]] = {
    PostgresConnection.db.run(movieTable.filter(_.id === id).result.headOption)
  }

  def add(movie: Movie): Future[Int] = {
    PostgresConnection.db.run(movieTable += movie)
  }

  def update(id: Int, newMovie: Movie): Future[Int] = {
    PostgresConnection.db.run(movieTable.filter(_.id === id).update(newMovie))
  }

  def delete(id: Int): Future[Int] = {
    PostgresConnection.db.run(movieTable.filter(_.id === id).delete)
  }

}
