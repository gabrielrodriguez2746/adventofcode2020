import org.junit.Test
import kotlin.test.assertEquals

class Day3Test {

    private val input = listOf(
        "..##.......",
        "#...#...#..",
        ".#....#..#.",
        "..#.#...#.#",
        ".#...##..#.",
        "..#.##.....",
        ".#.#.#....#",
        ".#........#",
        "#.##...#...",
        "#...##....#",
        ".#..#...#.#",
    ).map { it.toCharArray() }.toTypedArray()

    @Test
    fun `GIVEN example input WHEN transverseTheMapAndCountTrees THEN return expected`() {
        val expected = 7
        val result = transverseTheMapAndCountTrees(input, Slope(3, 1), MapCoordinate(0, 0))
        assertEquals(expected, result)
    }

    @Test
    fun `GIVEN example input WHEN transverseTheMapAndCountTrees with different combinations THEN return expected`() {
        val expected1 = 2
        val expected2 = 7
        val expected3 = 3
        val expected4 = 4
        val expected5 = 2
        val expected = 336
        val result1 = transverseTheMapAndCountTrees(input, Slope(1, 1), MapCoordinate(0, 0))
        val result2 = transverseTheMapAndCountTrees(input, Slope(3, 1), MapCoordinate(0, 0))
        val result3 = transverseTheMapAndCountTrees(input, Slope(5, 1), MapCoordinate(0, 0))
        val result4 = transverseTheMapAndCountTrees(input, Slope(7, 1), MapCoordinate(0, 0))
        val result5 = transverseTheMapAndCountTrees(input, Slope(1, 2), MapCoordinate(0, 0))
        val result = result1 * result2 * result3 * result4 * result5
        assertEquals(expected1, result1)
        assertEquals(expected2, result2)
        assertEquals(expected3, result3)
        assertEquals(expected4, result4)
        assertEquals(expected5, result5)
        assertEquals(expected, result)
    }
}