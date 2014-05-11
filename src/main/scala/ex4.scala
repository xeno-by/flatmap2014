package flatmap

import scalaz._
import scalaz.concurrent._
import scalaz.effect._
import scalaz.syntax.either._
import scalaz.syntax.std.list._

object Exercise4 {

  // Task 1: You're fetching content from the web (see `WebService`).
  // Hence, your values are wrapped in a `IO`.
  // Find all related items to a given item which a given customer bought.

  def boughtRelated(customerID: Long, itemName: String): IO[List[Long]] =
    for {
      itemID <- WebService.lookupItem(itemName)
      related <- WebService.relatedItems(itemID)
      filtered <- related.filterM(WebService.customerBoughtItem(customerID, _))
    } yield filtered

  // Task 2: `WebService` has been refactored to be asynchronous and use `Task`.
  // However, now the API exposes errors.
  // Rewrite `boughtRelated` to deal with `Task` and errors.
  
  def boughtRelated2(customerID: Long, itemName: String): Task[WebService.ErrorCode \/ List[Long]] =
    WebService.lookupItem2(itemName) flatMap {
      case \/-(itemID) =>
        WebService.relatedItems2(itemID) flatMap {
          case \/-(related) =>
            ??? // oh god please stahp
          case -\/(error) =>
            Task.now(error.left)
        }
      case -\/(error) =>
        Task.now(error.left)
    }

  // Task 3: Strip some boilerplate with `EitherT`!

  def boughtRelated3(customerID: Long, itemName: String): EitherT[Task, WebService.ErrorCode, List[Long]] =
    for {
      itemID <- EitherT(WebService.lookupItem2(itemName))
      related <- EitherT(WebService.relatedItems2(itemID))
      // or even a type lambda
      filtered <- Unapply[Applicative, EitherT[Task, WebService.ErrorCode, Any]].TC.filterM(related)(item => EitherT(WebService.customerBoughtItem2(customerID, item)))
    } yield filtered

}
