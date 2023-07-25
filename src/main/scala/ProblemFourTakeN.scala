import zio._
import zio.stream._

object ProblemFourTakeN extends App {

  def takeNSorted[A](
                      initial: A,
                      producer1: A => IO[Throwable, Option[A]],
                      producer2: A => IO[Throwable, Option[A]],
                      takeN: Int
                    )(implicit ord: Ordering[A]): IO[Throwable, List[A]] = {
    def loop(previous: A, n: Int, acc: List[A]): ZStream[Any, Throwable, A] =
      if (n <= 0) ZStream.fromIterable(acc)
      else {
        val producer = if (n % 2 == 0) producer1 else producer2
        ZStream
          .fromEffect(producer(previous))
          .collectSome
          .flatMap(next => loop(next, n - 1, next :: acc))
      }

    loop(initial, takeN, List(initial))
      .runCollect
      .map(_.toList.sorted)
  }

override def run(args: List[String]): URIO[zio.ZEnv, ExitCode] =
    takeNSorted(0, generateNext, generateNext, 10)
      .flatMap(list => ZIO.effectTotal(println(list)))
      .exitCode

  def generateNext(previous: Int): IO[Throwable, Option[Int]] =
    ZIO.succeed(Some(previous + 1))

}
