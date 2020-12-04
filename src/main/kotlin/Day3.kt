/**
--- Day 3: Toboggan Trajectory ---
With the toboggan login problems resolved, you set off toward the airport. While travel by toboggan might be easy, it's certainly not safe: there's very minimal steering and the area is covered in trees. You'll need to see which angles will take you near the fewest trees.

Due to the local geology, trees in this area only grow on exact integer coordinates in a grid. You make a map (your puzzle input) of the open squares (.) and trees (#) you can see. For example:

..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#
These aren't the only trees, though; due to something you read about once involving arboreal genetics and biome stability, the same pattern repeats to the right many times:

..##.........##.........##.........##.........##.........##.......  --->
#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....#..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..#...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.##.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........#.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...##....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
You start on the open square (.) in the top-left corner and need to reach the bottom (below the bottom-most row on your map).

The toboggan can only follow a few specific slopes (you opted for a cheaper model that prefers rational numbers); start by counting all the trees you would encounter for the slope right 3, down 1:

From your starting position at the top-left, check the position that is right 3 and down 1. Then, check the position that is right 3 and down 1 from there, and so on until you go past the bottom of the map.

The locations you'd check in the above example are marked here with O where there was an open square and X where there was a tree:

..##.........##.........##.........##.........##.........##.......  --->
#..O#...#..#...#...#..#...#...#..#...#...#..#...#...#..#...#...#..
.#....X..#..#....#..#..#....#..#..#....#..#..#....#..#..#....#..#.
..#.#...#O#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#..#.#...#.#
.#...##..#..X...##..#..#...##..#..#...##..#..#...##..#..#...##..#.
..#.##.......#.X#.......#.##.......#.##.......#.##.......#.##.....  --->
.#.#.#....#.#.#.#.O..#.#.#.#....#.#.#.#....#.#.#.#....#.#.#.#....#
.#........#.#........X.#........#.#........#.#........#.#........#
#.##...#...#.##...#...#.X#...#...#.##...#...#.##...#...#.##...#...
#...##....##...##....##...#X....##...##....##...##....##...##....#
.#..#...#.#.#..#...#.#.#..#...X.#.#..#...#.#.#..#...#.#.#..#...#.#  --->
In this example, traversing the map using this slope would cause you to encounter 7 trees.

Starting at the top-left corner of your map and following a slope of right 3 and down 1, how many trees would you encounter?

--- Part Two ---
Time to check the rest of the slopes - you need to minimize the probability of a sudden arboreal stop, after all.

Determine the number of trees you would encounter if, for each of the following slopes, you start at the top-left corner and traverse the map all the way to the bottom:

Right 1, down 1.
Right 3, down 1. (This is the slope you already checked.)
Right 5, down 1.
Right 7, down 1.
Right 1, down 2.
In the above example, these slopes would find 2, 7, 3, 4, and 2 tree(s) respectively; multiplied together, these produce the answer 336.

What do you get if you multiply together the number of trees encountered on each of the listed slopes?


 **/

fun main() {
    val input = "Day3Input".readInput().map { it.toCharArray() }.toTypedArray()
    val goingDown31 = transverseTheMapAndCountTrees(input, Slope(3, 1), MapCoordinate(0, 0))
    val goingDown11 = transverseTheMapAndCountTrees(input, Slope(1, 1), MapCoordinate(0, 0))
    val goingDown51 = transverseTheMapAndCountTrees(input, Slope(5, 1), MapCoordinate(0, 0))
    val goingDown71 = transverseTheMapAndCountTrees(input, Slope(7, 1), MapCoordinate(0, 0))
    val goingDown12 = transverseTheMapAndCountTrees(input, Slope(1, 2), MapCoordinate(0, 0))
    val allPossibilities = goingDown31 * goingDown11 * goingDown51 * goingDown71 * goingDown12
    println("The count of trees in have seen in my way down was:: $goingDown31")
    println("The count of trees multiplying the possibilities all the way down was:: $allPossibilities")
}

/**
 * Given a Matrix of char representing a "map" with two possible values '.' and '#'
 * transverse the map with a given slope and count the number of tress until the end
 * Important: If you left without more path to go right just "duplicate the matrix"
 * Important: You move to the right and down, no more possibilities
 * Assumption: Trust the path has always this two values
 * Assumption: The slope right is not bigger than the length of the "initial map" (without duplicate it)
 * Assumption: The "down" slope always is possible with the length of the path
 * Prejudice: I am afraid of playing with matrix :'(
 * Order: will say "n" as the length of the input and the input as a matrix
 */
internal fun transverseTheMapAndCountTrees(
    map: Array<CharArray>,
    slope: Slope,
    initialCoordinates: MapCoordinate
): Int {
    // Count the number of trees while moving in the map with the slope
    var treesCount = 0
    // consider the initial coordinate to iterate
    var whileIMovePosition = MapCoordinate(initialCoordinates.x, initialCoordinates.y)
    val treePathLength = map.first().size
    while (whileIMovePosition.y < map.size) {
        // If the character in my x position is '#' count a tree :D
        val (positionX, positionY) = whileIMovePosition
        val treePath = map[positionY]
        if (treePath[positionX] == '#') {
            treesCount++
        }
        // Adjust the current position , If "I left out move" to the right reset the x position (repeat the map pattern)
        whileIMovePosition = MapCoordinate((positionX + slope.right) % treePathLength, positionY + slope.down)
    }
    return treesCount

}

internal data class Slope(val right: Int, val down: Int)
internal data class MapCoordinate(val x: Int, val y: Int)