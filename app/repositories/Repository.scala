package repositories

import scala.concurrent.Future

trait Repository[T] {
  def getAll: Future[Seq[T]]
  def get(id: Int): Future[Option[T]]
  def add(obj: T): Future[Int]
  def update(id: Int, newObj: T): Future[Int]
  def delete(id: Int): Future[Int]
}
