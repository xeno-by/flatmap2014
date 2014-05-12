package flatmap

import org.scalacheck.Properties
import org.scalacheck.Prop._

import scalaz._
import scalaz.syntax.traverse._
import scalaz.std.list._
import scalaz.std.string._
import scalaz.std.option._
import scalaz.scalacheck.ScalazArbitrary._

object Exercise2Spec extends Properties("exercise2") {

  property("insideOut") = forAll { (l: List[Validation[String, Int]]) =>
    Exercise2.insideOut(l) == l.traverseU(_.leftMap(_ :: Nil))
  }

  property("insideOutNoChange") = forAll { (l: List[Validation[String, Int]]) =>
    Exercise2.insideOutNoChange(l) == l.sequenceU
  }

  property("insideOut2") = forAll { (l: List[Validation[String, Int]]) =>
    Exercise2.insideOut2(l) == l.traverseU(_.leftMap(_ :: Nil))
  }

  property("insideOutOption") = forAll { (l: List[Option[Int]]) =>
    Exercise2.insideOutOption(l) == l.sequenceU
  }

}
