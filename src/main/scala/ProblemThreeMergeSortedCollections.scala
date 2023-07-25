import scala.language.higherKinds

sealed trait BackwardsList[+A]
case object BWLNil extends BackwardsList[Nothing]
case class BWLCons[A](last: A, init: BackwardsList[A]) extends BackwardsList[A]

object ProblemThreeMergeSortedCollections extends App {

  trait Mergeable[F[_]] {
    def mergeSorted[A](left: F[A], right: F[A])(implicit ord: Ordering[A]): F[A]
  }

  object MergeableInstances {
    implicit val listMergeable: Mergeable[List] = new Mergeable[List] {
      override def mergeSorted[A](left: List[A], right: List[A])(implicit ord: Ordering[A]): List[A] =
        (left ++ right).sorted(ord)
    }

    implicit val vectorMergeable: Mergeable[Vector] = new Mergeable[Vector] {
      override def mergeSorted[A](left: Vector[A], right: Vector[A])(implicit ord: Ordering[A]): Vector[A] =
        (left ++ right).sorted(ord)
    }

    implicit val optionMergeable: Mergeable[Option] = new Mergeable[Option] {
      //should I have combined them some how instead of using ordering
      override def mergeSorted[A](left: Option[A], right: Option[A])(implicit ord: Ordering[A]): Option[A] =
        (left, right) match {
          case (Some(a), Some(b)) => Some(ord.min(a, b))
          case (a, b) => a.orElse(b)
        }
    }


    implicit def bwListMergeable[A]: Mergeable[BackwardsList] = new Mergeable[BackwardsList] {
      override def mergeSorted[A](left: BackwardsList[A], right: BackwardsList[A])(implicit ord: Ordering[A]): BackwardsList[A] =
        (left, right) match {
          case (BWLNil, r) => r
          case (l: BWLCons[A], r: BWLCons[A]) =>
            mergeLists(mergeSorted(l.init, r), l.last)
          case _ => throw new IllegalArgumentException("Invalid BackwardsList")
        }

      private def mergeLists[A](list: BackwardsList[A], value: A)(implicit ord: Ordering[A]): BackwardsList[A] = list match {
        case BWLNil => BWLCons(value, BWLNil)
        case l: BWLCons[A] =>
          if (ord.gt(value, l.last))
            BWLCons(value, list)
          else
            BWLCons(l.last, mergeLists(l.init, value))
      }
    }
  }

  def mergeSorted[F[_], A](left: F[A], right: F[A])(implicit mergeable: Mergeable[F], ord: Ordering[A]): F[A] =
    mergeable.mergeSorted(left, right)

  // Usage example:
  import MergeableInstances._

  val x1 = List(1, 2, 3)
  val x2 = List(4, 5, 6)
  val xs = mergeSorted(x1, x2)
  println(xs) // Output: List(1, 2, 3, 4, 5, 6)

  val v1 = Vector(1, 2, 3)
  val v2 = Vector(4, 5, 6)
  val vs = mergeSorted(v1, v2)
  println(vs) // Output: Vector(1, 2, 3, 4, 5, 6)

  val bl1:BackwardsList[Int] = BWLCons(1, BWLCons(2, BWLCons(3, BWLNil)))
  val bl2:BackwardsList[Int] = BWLCons(4, BWLCons(5, BWLCons(6, BWLNil)))
  val bl3 = mergeSorted(bl1, bl2)
  println(bl3) // Output: BWLCons(1, BWLCons(2, BWLCons(3, BWLCons(4, BWLCons(5, BWLCons(6, BWLNil))))))

}
