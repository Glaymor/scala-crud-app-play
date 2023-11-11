package services

import models.Movie
import repositories.MovieRepository

import javax.inject.Inject
import scala.concurrent.Future


class MovieService@Inject()(val repository: MovieRepository) extends Service[Movie] {

  override def get(id: Int): Future[Option[Movie]] = repository.get(id)

  override def add(movie: Movie): Future[Int] = repository.add(movie)

  override def update(id: Int, movie: Movie): Future[Int] = repository.update(id, movie)

  override def delete(id: Int): Future[Int] = repository.delete(id)

  override def getAll: Future[Seq[Movie]] = repository.getAll
}
