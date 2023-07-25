case class User(name: String, age: Int)

object User {
  implicit val ordering: Ordering[User] = Ordering.by(_.age)
}

import scala.annotation.tailrec

object ProblemTwoMergeSortedLists extends App {

  //I am assuming passing a implicit ord: Ordering[A] into the method was ok
  def mergeSortedLists[A](left: List[A], right: List[A])(implicit ord: Ordering[A]): List[A] = {
    @tailrec
    def merge(left: List[A], right: List[A], merged: List[A]): List[A] = {
      (left, right) match {
        case (Nil, _) => merged.reverse ::: right
        case (_, Nil) => merged.reverse ::: left
        case (l :: ls, r :: rs) =>
           merge(ls, right, l :: merged)
      }
    }

    merge(left, right, Nil).sorted
  }

  val list1 = List(User("Colm", 100), User("John", 40), User("Kyle", 23))
  val list2 = List(User("Geoff", 22), User("Andrew", 35))

  val mergedList = mergeSortedLists(list1, list2)

  println(mergedList)
}