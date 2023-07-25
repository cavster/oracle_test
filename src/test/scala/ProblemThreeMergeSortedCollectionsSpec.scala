import org.scalatest.flatspec.AnyFlatSpec
import ProblemThreeMergeSortedCollections.mergeSorted
import org.scalatest.matchers.should.Matchers
import ProblemThreeMergeSortedCollections.MergeableInstances._

class ProblemThreeMergeSortedCollectionsSpec extends AnyFlatSpec with Matchers {

  "mergeSorted" should "merge two lists and return a sorted list" in {
    val left = List(1, 3, 5)
    val right = List(2, 4, 6)
    val result = mergeSorted(left, right)
    result should be(List(1, 2, 3, 4, 5, 6))
  }

  it should "merge two vectors and return a sorted vector" in {
    val left = Vector(1, 3, 5)
    val right = Vector(2, 4, 6)
    val result = mergeSorted(left, right)
    result should be(Vector(1, 2, 3, 4, 5, 6))
  }

  it should "merge two options and return the smaller one" in {
    val left: Option[Int] = Some(3)
    val right: Option[Int] = Some(2)
    val result = mergeSorted(left, right)
    result should be(Some(2))
  }

  it should "merge two backwards lists and return a sorted backwards list" in {
    val left:BackwardsList[Int] = BWLCons(5, BWLCons(3, BWLNil))
    val right:BackwardsList[Int] = BWLCons(6, BWLCons(4, BWLNil))
    val result = mergeSorted(left, right)
    result should be(BWLCons(6, BWLCons(5, BWLCons(4, BWLCons(3, BWLNil)))))
  }
}
