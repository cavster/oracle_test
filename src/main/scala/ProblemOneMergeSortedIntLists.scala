object ProblemOneMergeSortedIntLists extends App {


  def mergeSortedIntLists(left: List[Int], right: List[Int]): List[Int] = {
    @annotation.tailrec
    def merge(left: List[Int], right: List[Int], merged: List[Int]): List[Int] = {
      (left, right) match {
        case (Nil, _) => merged.reverse ::: right
        case (_, Nil) => merged.reverse ::: left
        case (l :: ls, r :: rs) =>
          if (l <= r) merge(ls, right, l :: merged)
          else merge(left, rs, r :: merged)
      }
    }

    merge(left, right, Nil)
  }

  print(mergeSortedIntLists(List(1,2,3),List(1,2,3,4,5)))


}
