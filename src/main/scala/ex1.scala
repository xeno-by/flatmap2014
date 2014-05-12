package flatmap

import scalaz._

object Exercise1 {

  sealed trait Failure
  case class NameFailure() extends Failure
  case class ZipCodeFailure() extends Failure
  case class SalaryFailure() extends Failure

  case class Employee private[Exercise1](name: String, zipCode: String, city: String, salary: Int)

  // Task 1: You are given raw employee data. If there's an error, give it back.
  // You'll need to add cases to the `Failure` trait.

  /**
   * @param name must be non-empty
   * @param city must be in the set of cities belonging to the `zipCode`, according to `ZipCodeService`
   * @param salary must be greater than 0 and less than 100000
   */
  def mkEmployee(name: String, zipCode: String, city: String, salary: Int): Failure \/ Employee = {
    import \/._
    def validatedName: Failure \/ String = if (name.isEmpty) left(NameFailure()) else right(name)
    def validatedZipCode: Failure \/ String = ZipCodeService.citiesInZipCode(zipCode)
                                                            .leftMap(_ => ZipCodeFailure())
                                                            .flatMap(cities => if (cities.contains(city)) right(city) else left(ZipCodeFailure()))
    def validatedSalary: Failure \/ Int = if (0 > salary || salary >= 100000) left(new SalaryFailure()) else right(salary)
    for {
      name <- validatedName
      zipCode <- validatedZipCode
      salary <- validatedSalary
    } yield Employee(name, zipCode, city, salary)
  }

  // Task 2: You are given raw employee data. If there's at least one error, return all existing errors back.
  // Don't use Validation#flatMap.

  def mkEmployee2(name: String, zipCode: String, city: String, salary: Int): ValidationNel[Failure, Employee] = {
    import \/._
    import NonEmptyList._
    def validatedName: NonEmptyList[Failure] \/ String = if (name.isEmpty) left(nels(NameFailure())) else right(name)
    def validatedZipCode: NonEmptyList[Failure] \/ String = ZipCodeService.citiesInZipCode(zipCode)
                                                            .leftMap(_ => nels(ZipCodeFailure()))
                                                            .flatMap(cities => if (cities.contains(city)) right(city) else left(nels(ZipCodeFailure())))
    def validatedSalary: NonEmptyList[Failure] \/ Int = if (0 > salary || salary >= 100000) left(nels(SalaryFailure())) else right(salary)
    // val V = Applicative[({type λ[α]=ValidationNel[Failure, α]})#λ]
    // V.apply3(validatedName.validation, validatedZipCode.validation, validatedSalary.validation)(Employee(_, _, city, _))
    import syntax.applicative._
    (validatedName.validation |@| validatedZipCode.validation |@| validatedSalary.validation)(Employee(_, _, city, _))
  }

  // Task 3: You have to fetch the salary data from the `SalaryService`.
  // You'll need to add even more cases to the `Failure` trait.
  // Don't use Validation#flatMap.

  def mkEmployee3(name: String, zipCode: String, city: String): ValidationNel[Failure, Employee] =
    ???

}
