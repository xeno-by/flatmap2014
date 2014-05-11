package flatmap

import scalaz._
import scalaz.concurrent._
import scalaz.effect._

object Exercise4 {

  // Task 1: You're fetching content from the web (see `WebService`).
  // Hence, your values are wrapped in a `IO`.
  // Find all related items to a given item which a given customer bought.

  def boughtRelated(customerID: Long, itemName: String): IO[List[Long]] =
    ???

  // Task 2: `WebService` has been refactored to be asynchronous and use `Task`.
  // However, now the API exposes errors.
  // Rewrite `boughtRelated` to deal with `Task` and errors.
  
  def boughtRelated2(customerID: Long, itemName: String): Task[WebService.ErrorCode \/ List[Long]] =
    ???

  // Task 3: Strip some boilerplate with `EitherT`!

  def boughtRelated3(customerID: Long, itemName: String): EitherT[Task, WebService.ErrorCode, List[Long]] =
    ???

}
