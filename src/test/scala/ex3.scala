package flatmap

import org.scalacheck.Properties
import org.scalacheck.Prop._

import scalaz._
import scalaz.syntax.monad._

object Exercise3Spec extends Properties("exercise3") {

  import Exercise3._

  property("read/write") = forAll { (s: String, out: List[String]) =>
    val program = Terminal.readLine >>= Terminal.writeLine
    val init = Mock(in = List(s), out = out)
    val expected = Mock(in = Nil, out = s :: out)
    Terminal.run(program)(terminalToState).exec(init) == expected
  }

}
