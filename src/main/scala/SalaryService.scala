package flatmap

import scalaz._
import scalaz.syntax.either._

object SalaryService {

  sealed trait Failure
  case object UnknownEmployee extends Failure
  case object AccessDenied extends Failure

  def getSalary(name: String): Failure \/ Int = name match {
    case "Scrooge McDuck" => AccessDenied.left
    case "Donald Duck"    => 10000.right
    case "Daisy Duck"     => 20000.right
    case _                => UnknownEmployee.left
  }

}
