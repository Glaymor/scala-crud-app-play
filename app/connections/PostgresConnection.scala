package connections

import slick.jdbc.PostgresProfile.api._

object PostgresConnection {

  private val url = "jdbc:postgresql://localhost:5432/postgres"
  private val user = "postgres"
  private val password = "********"
  private val driver = "org.postgresql.Driver"

  val db = Database.forURL(url, user, password, driver = driver)
}
