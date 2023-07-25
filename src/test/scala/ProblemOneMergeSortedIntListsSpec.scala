
import org.scalatest.flatspec.AnyFlatSpec
import ProblemOneMergeSortedIntLists.mergeSortedIntLists
import org.scalatest.matchers.should.Matchers


class ProblemOneMergeSortedIntListsSpec extends AnyFlatSpec with Matchers {

  "mergeSortedIntLists" should "merge two sorted lists into a sorted one" in {
    val left = List(1, 3, 5, 7)
    val right = List(2, 4, 6, 8)
    val expected = List(1, 2, 3, 4, 5, 6, 7, 8)
    mergeSortedIntLists(left, right) should be(expected)
  }

  it should "merge two sorted lists of different sizes" in {
    val left = List(1, 3, 5, 7)
    val right = List(2, 4, 6)
    val expected = List(1, 2, 3, 4, 5, 6, 7)
    mergeSortedIntLists(left, right) should be(expected)
  }

  it should "merge two sorted lists with duplicate elements" in {
    val left = List(1, 3, 5, 7, 7, 9)
    val right = List(2, 4, 6, 7, 8)
    val expected = List(1, 2, 3, 4, 5, 6, 7, 7, 7, 8, 9)
    mergeSortedIntLists(left, right) should be(expected)
  }

  it should "merge an empty list with a non-empty list" in {
    val left = List.empty[Int]
    val right = List(1, 2, 3)
    val expected = List(1, 2, 3)
    mergeSortedIntLists(left, right) should be(expected)
  }

  it should "merge two empty lists into an empty list" in {
    val left = List.empty[Int]
    val right = List.empty[Int]
    val expected = List.empty[Int]
    mergeSortedIntLists(left, right) should be(expected)
  }

  it should "not cause StackOverflowError with large input lists" in {
    val left = (1 to 100000).toList
    val right = (100001 to 200000).toList

    val expected = (1 to 200000).toList

    val result = mergeSortedIntLists(left, right)
    result should be(expected)

    noException should be thrownBy mergeSortedIntLists(left, right)
  }
}