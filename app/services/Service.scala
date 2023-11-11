package services

import scala.concurrent.Future

trait Service[T] {
  def get(id: Int): Future[Option[T]]
  def add(obj: T): Future[Int]
  def update(id: Int, obj: T): Future[Int]
  def delete(id: Int): Future[Int]
  def getAll: Future[Seq[T]]
}
