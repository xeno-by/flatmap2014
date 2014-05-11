package flatmap

import scala.util.Random

import scalaz._
import scalaz.concurrent._
import scalaz.effect._
import scalaz.syntax.either._

object WebService {

  // for Task 1

  def lookupItem(itemName: String): IO[Long] =
    IO {
      Random.nextInt(1000)
    }

  def relatedItems(itemID: Long): IO[List[Long]] =
    IO {
      List.fill(Random.nextInt(10))(Random.nextInt(1000))
    }

  def customerBoughtItem(customerID: Long, itemID: Long): IO[Boolean] =
    IO {
      Random.nextBoolean()
    }


  // for Task 2

  sealed trait ErrorCode
  case object NotFound extends ErrorCode
  case object PermissionDenied extends ErrorCode

  def lookupItem2(itemName: String): Task[ErrorCode \/ Long] =
    Task.delay {
      questionably {
        Random.nextInt(1000)
      }
    }

  def relatedItems2(itemID: Long): Task[ErrorCode \/ List[Long]] =
    Task.delay {
      questionably {
        List.fill(Random.nextInt(10))(Random.nextInt(1000))
      }
    }

  def customerBoughtItem2(customerID: Long, itemID: Long): Task[ErrorCode \/ Boolean] =
    Task.delay {
      questionably {
        Random.nextBoolean()
      }
    }


  // internal

  private def questionably[A](value: => A): ErrorCode \/ A = {
    val result = math.random
    if (result < 0.1)
      NotFound.left
    else if (result < 0.2)
      PermissionDenied.left
    else
      value.right
  }

}
