package flatmap

import scalaz._
import scalaz.syntax.functor._
import scalaz.effect._

object Exercise3 {

  // Task 1: Define an algebra for I/O on terminals.
  
  sealed trait Terminal[A]
  case class Pure[A](a: A) extends Terminal[A]
  case object ReadLine extends Terminal[String]
  case class WriteLine(value: String) extends Terminal[Unit]

  type TerminalCoyo[A] = Coyoneda[Terminal, A]
  type TerminalIO[A] = Free.FreeC[Terminal, A]

  object Terminal {
  
    def lift[A](t: Terminal[A]): TerminalIO[A] =
      Free.liftF[TerminalCoyo, A](Coyoneda(t))

    // Task 2: Write convenience functions for each operation.

    def pure[A](a: A): TerminalIO[A] = lift(Pure(a))
    def readLine: TerminalIO[String] = lift(ReadLine)
    def writeLine(s: String): TerminalIO[Unit] = lift(WriteLine(s))


    def run[A, M[_] : Monad](tio: TerminalIO[A])(interpreter: Terminal ~> M): M[A] = tio.foldMap[M](new (TerminalCoyo ~> M) {
      def apply[A](cy: TerminalCoyo[A]): M[A] =
        interpreter(cy.fi).map(cy.k)
    })

  }

  // Task 3: Write an interpreter which compiles `Terminal` to `IO`.
  
  def terminalToIO: Terminal ~> IO = new (Terminal ~> IO) {
    def apply[A](t: Terminal[A]): IO[A] = t match {
      case Pure(a) => IO { a }
      case ReadLine => IO.readLn
      case WriteLine(s) => IO.putStrLn(s)
    }
  }

  // Task 4: Write an interpreter which compiles `Terminal` to `State`.
  
  case class Mock(in: List[String], out: List[String])
  object Mock {
    def read(mock: Mock): (Mock, String) = mock.in match {
      case Nil => (mock, "")
      case h :: t => (mock.copy(in = t), h)
    }
    def write(value: String)(mock: Mock): Mock =
      mock.copy(out = value :: mock.out)
  }


  type MockState[A] = State[Mock, A]

  def terminalToState: Terminal ~> MockState = new (Terminal ~> MockState) {
    def apply[A](t: Terminal[A]): MockState[A] = t match {
      case Pure(a) => Monad[MockState].pure(a)
      case ReadLine => State(Mock.read)
      case WriteLine(s) => State.modify(Mock.write(s))
    }
  }

  // Task 5: Go crazy ;-)
  
  val program = for {
    x <- Terminal.readLine
    spacer <- Terminal.pure(" ")
    y <- Terminal.readLine
    greeting = x + spacer + y
    _ <- Terminal.writeLine(greeting)
  } yield ()

}
