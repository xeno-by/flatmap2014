package flatmap

import scalaz._
import scalaz.syntax.functor._
import scalaz.effect._

object Exercise3 {

  // Task 1: Define an algebra for I/O on terminals.
  
  sealed trait Terminal[A]
  case object ReadLine extends Terminal[String]

  type TerminalCoyo[A] = Coyoneda[Terminal, A]
  type TerminalIO[A] = Free.FreeC[Terminal, A]

  object Terminal {
  
    def lift[A](t: Terminal[A]): TerminalIO[A] =
      Free.liftF[TerminalCoyo, A](Coyoneda(t))

    // Task 2: Write convenience functions for each operation.

    def readLine: TerminalIO[String] = lift(ReadLine)
    def writeLine = ???


    def run[A, M[_] : Monad](tio: TerminalIO[A])(interpreter: Terminal ~> M): M[A] = tio.foldMap[M](new (TerminalCoyo ~> M) {
      def apply[A](cy: TerminalCoyo[A]): M[A] =
        interpreter(cy.fi).map(cy.k)
    })

  }

  // Task 3: Write an interpreter which compiles `Terminal` to `IO`.
  
  def terminalToIO: Terminal ~> IO = new (Terminal ~> IO) {
    def apply[A](t: Terminal[A]): IO[A] = t match {
      case ReadLine => IO.readLn
    }
  }

  // Task 4: Write an interpreter which compiles `Terminal` to `State`.
  
  case class Mock(in: List[String], out: List[String])

  type MockState[A] = State[Mock, A]

  def terminalToState: Terminal ~> MockState = ???

  // Task 5: Go crazy ;-)
  
  val program = for {
    x <- Terminal.readLine
    y <- Terminal.readLine
  } yield x + y

}
