
import org.scalatest.flatspec.AnyFlatSpec
import ProblemTwoMergeSortedLists.mergeSortedLists
import org.scalatest.matchers.should.Matchers

class ProblemTwoMergeSortedListsSpec extends AnyFlatSpec with Matchers {

  "mergeSortedLists" should "merge two sorted lists of Users correctly" in {
    val list1 = List(User("Colm", 100), User("John", 40), User("Kyle", 23))
    val list2 = List(User("Geoff", 22), User("Andrew", 35))
    val expectedMergedList = List(
      User("Geoff", 22),
      User("Kyle", 23),
      User("Andrew", 35),
      User("John", 40),
      User("Colm", 100)
    )

    val mergedList = mergeSortedLists(list1, list2)

    mergedList should be(expectedMergedList)
  }

  it should "merge two empty lists correctly" in {
    val list1 = List.empty[User]
    val list2 = List.empty[User]
    val expectedMergedList = List.empty[User]

    val mergedList = mergeSortedLists(list1, list2)

    mergedList should be(expectedMergedList)
  }

  it should "merge an empty list with a non-empty list correctly" in {
    val list1 = List.empty[User]
    val list2 = List(User("Geoff", 22), User("Andrew", 35))
    val expectedMergedList = List(User("Geoff", 22), User("Andrew", 35))

    val mergedList = mergeSortedLists(list1, list2)

    mergedList should be(expectedMergedList)
  }

  it should "merge a non-empty list with an empty list correctly" in {
    val list1 = List(User("Colm", 100), User("John", 40), User("Kyle", 23))
    val list2 = List.empty[User]
    val expectedMergedList = List(User("Kyle", 23), User("John", 40), User("Colm", 100))

    val mergedList = mergeSortedLists(list1, list2)

    mergedList should be(expectedMergedList)
  }

  it should "merge two sorted lists with duplicate elements correctly" in {
    val list1 = List(User("Colm", 100), User("John", 40), User("Kyle", 23), User("Kyle", 23))
    val list2 = List(User("Geoff", 22), User("Andrew", 35), User("John", 40))
    val expectedMergedList = List(
      User("Geoff", 22),
      User("Kyle", 23),
      User("Kyle", 23),
      User("Andrew", 35),
      User("John", 40),
      User("John", 40),
      User("Colm", 100)
    )

    val mergedList = mergeSortedLists(list1, list2)

    mergedList should be(expectedMergedList)
  }
}
