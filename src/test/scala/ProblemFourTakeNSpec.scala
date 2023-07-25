import zio._
import zio.stream._
import zio.duration._
import zio.test._
import zio.test.Assertion._
import zio.clock._
import zio.test.environment.{TestEnvironment, TestClock}

object ProblemFourTakeNSpec extends DefaultRunnableSpec {
  def spec: ZSpec[TestEnvironment, Any] = suite("ProblemFourTakeN")(
    testM("produces an ordered list of elements") {
      val producer1: Int => IO[Throwable, Option[Int]] = _ => ZIO.succeed(Some(2))
      val producer2: Int => IO[Throwable, Option[Int]] = _ => ZIO.succeed(Some(1))

      for {
        result <- ProblemFourTakeN.takeNSorted(0, producer1, producer2, 3)
      } yield assert(result)(equalTo(List(0, 1, 1, 2)))
    }

  )
}
