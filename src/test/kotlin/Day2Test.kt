import org.junit.Test
import kotlin.test.assertEquals


class Day2Test {

    @Test
    fun `GIVEN input WHEN validPasswordsCounter THE return expected`() {
        val input = listOf("6-10 a: abcdeabcdeabcde", "3-10 b: abcdeabcdeabcde", "1-3 b: abcdeabcdeabcde")
        val expected = 2
        val result = validPasswordsCounter(input)
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN input WHEN validPasswordsCounterNewCompany THE return expected`() {
        val input = listOf("6-10 a: abcdeabcdeabcde", "3-10 b: abcdeabcdeabcde", "1-3 b: abcdeabcdeabcde")
        val expected = 1
        val result = validPasswordsCounterNewCompany(input)
        assertEquals(expected, result)
    }
}