package flatmap

import org.scalacheck.Properties
import org.scalacheck.Prop._

import scalaz._
import scalaz.syntax.monad._

object Exercise3Spec extends Properties("exercise3") {

  import Exercise3._

  property("read/write") = forAll { (s: String, out: List[String]) =>
    // something with readLine and writeLine
    false
  }

}
