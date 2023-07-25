import zio.{IO, Queue, Ref}
import zio.duration._

object ProblemFiveQueueSorter extends App {
  def streamSorter[A](
                       queue1: Queue[A],
                       queue2: Queue[A],
                       takeN: Int
                     ): IO[Nothing, List[A]] = {
    def takeFromQueue(q: Queue[A], remaining: Int): IO[Nothing, List[A]] = {
      if (remaining <= 0) IO.succeed(List.empty[A])
      else q.take.flatMap(a => takeFromQueue(q, remaining - 1).map(a :: _))
    }

    for {
      list1 <- takeFromQueue(queue1, takeN)
      list2 <- takeFromQueue(queue2, takeN - list1.length)
    } yield (list1 ++ list2)
  }
}
