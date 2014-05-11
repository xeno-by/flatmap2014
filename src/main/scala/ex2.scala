package flatmap

import scalaz._

object Exercise2 {

  // Task 1: You get a list of `Validation`s. If all are successes, return a list of the values.
  // If at least one is a failure, return the list of all failures.
  
  def insideOut[A, B](list: List[Validation[A, B]]): Validation[List[A], List[B]] =
    ???

  // Task 2: Notice how in Task 1 the error type changed? Maybe there is another variant
  // of the above function which doesn't do that ...

  def insideOutNoChange[A : Semigroup, B](list: List[Validation[A, B]]): Validation[A, List[B]] =
    ???

  // Task 3: Now, can we use Task 2 to implement Task 1?

  def insideOut2[A, B](list: List[Validation[A, B]]): Validation[List[A], List[B]] =
    ???

  // Task 4: Okay, what about Task 2, but for Option?
  // Can you spot the pattern?

  def insideOutOption[A](list: List[Option[A]]): Option[List[A]] =
    ???

}
