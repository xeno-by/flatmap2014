package flatmap

import scalaz._
import scalaz.syntax.either._

object ZipCodeService {

  sealed trait Failure
  case object IllegalZipCode extends Failure

  def citiesInZipCode(zipCode: String): Failure \/ Set[String] =
    if (zipCode matches "[0-9]{5}")
      zipCode.map(n => s"City $n").toSet.right
    else
      IllegalZipCode.left

}
