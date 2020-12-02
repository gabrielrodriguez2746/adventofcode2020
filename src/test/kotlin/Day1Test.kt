import org.junit.Test
import kotlin.test.assertEquals


class Day1Test {

    @Test
    fun `GIVEN input WHEN productOfTwoExpensesMatch2020 THE return expected`() {
        val input = longArrayOf(1721, 979, 366, 299, 675, 1456)
        val expected = 514579L
        val result = productOfTwoExpensesMatch2020(input)
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN input WHEN productOfThreeExpensesMatch2020 THE return expected`() {
        val input = longArrayOf(1721, 979, 366, 299, 675, 1456)
        val expected = 241861950L
        val result = productOfThreeExpensesMatch2020(input)
        assertEquals(expected, result)
    }
}