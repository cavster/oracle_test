import zio.test.Assertion._
import zio.test._
import zio.{Queue, ZIO}
import zio.duration._

object ProblemFiveQueueSorterSpec extends DefaultRunnableSpec {
  def spec: ZSpec[Environment, Failure] = suite("ProblemFiveQueueSorter")(
    testM("streamSorter should return ordered list of elements") {
      val elements = List(1, 4, 2, 3, 6, 5)
      val takeN = elements.length

      for {
        queue1 <- Queue.unbounded[Int]
        queue2 <- Queue.unbounded[Int]
        _ <- ZIO.foreach_(elements)(queue1.offer)
        _ <- ZIO.foreach_(elements)(queue2.offer)
        expectedResult = elements.sorted
        result <- ProblemFiveQueueSorter.streamSorter(queue1, queue2, takeN)
      } yield assert(result)(equalTo(expectedResult))
    },
    testM("streamSorter should handle empty queues") {
      val elements = List(1, 2, 3)
      val takeN = elements.length

      for {
        queue1 <- Queue.unbounded[Int]
        queue2 <- Queue.unbounded[Int]
        expectedResult = List.empty[Int]
        result <- ProblemFiveQueueSorter.streamSorter(queue1, queue2, takeN)
      } yield assert(result)(equalTo(expectedResult))
    },
    testM("streamSorter should handle takeN being greater than the available elements") {
      val elements = List(1, 2, 3)
      val takeN = elements.length + 1

      for {
        queue1 <- Queue.unbounded[Int]
        queue2 <- Queue.unbounded[Int]
        _ <- ZIO.foreach_(elements)(queue1.offer)
        _ <- ZIO.foreach_(elements)(queue2.offer)
        expectedResult = elements.sorted
        result <- ProblemFiveQueueSorter.streamSorter(queue1, queue2, takeN)
      } yield assert(result)(equalTo(expectedResult))
    }
  )
}
