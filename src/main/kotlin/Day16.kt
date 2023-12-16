import java.io.File
import kotlin.math.max

enum class Direction
{
    LEFT, UP, RIGHT, DOWN
}

var squareSum: MutableSet<Pair<Int, Int>> = mutableSetOf()
var squareSumDirs: MutableSet<Pair<Pair<Int, Int>, Direction>> = mutableSetOf()

fun main()
{
    var largestSum = 0
    val grid: MutableList<String> = mutableListOf()
    File("src/main/resources/Day16Input.txt").bufferedReader().forEachLine {
        grid.add(it)
    }

    println("Sum of tiles in top left corner, heading right: ${startBeamPath(0, 0, Direction.RIGHT, grid)}")

    for (i in grid[0].indices)
    {
        largestSum = max(startBeamPath(i, 0, Direction.DOWN, grid), largestSum)
        largestSum = max(startBeamPath(i, grid.size - 1, Direction.UP, grid), largestSum)
    }


    for (i in grid.indices)
    {
        largestSum = max(startBeamPath(0, i, Direction.RIGHT, grid), largestSum)
        largestSum = max(startBeamPath(grid[0].length - 1, i, Direction.LEFT, grid), largestSum)
    }

    println("Largest sum of tiles across entire border: $largestSum")
}

fun startBeamPath(x: Int, y: Int, dir: Direction, grid: MutableList<String>): Int
{
    continueBeamPath(x, y, dir, grid)
    val ret = squareSum.size
    squareSum = mutableSetOf()
    squareSumDirs = mutableSetOf()
    return ret
}

fun continueBeamPath(x: Int, y: Int, dir: Direction, grid: MutableList<String>)
{
    if (y >= grid.size || x >= grid[0].length || y < 0 || x < 0) return

    squareSum.add(Pair(x, y))
    if (!squareSumDirs.add(Pair(Pair(x, y), dir))) return

    when (grid[y][x])
    {
        '.' ->
        {
            when (dir)
            {
                Direction.LEFT -> continueBeamPath(x - 1, y, dir, grid)
                Direction.UP -> continueBeamPath(x, y - 1, dir, grid)
                Direction.RIGHT -> continueBeamPath(x + 1, y, dir, grid)
                Direction.DOWN -> continueBeamPath(x, y + 1, dir, grid)
            }
        }

        '|' ->
        {
            when (dir)
            {
                Direction.LEFT, Direction.RIGHT ->
                {
                    continueBeamPath(x, y - 1, Direction.UP, grid)
                    continueBeamPath(x, y + 1, Direction.DOWN, grid)
                }

                Direction.UP -> continueBeamPath(x, y - 1, dir, grid)
                Direction.DOWN -> continueBeamPath(x, y + 1, dir, grid)
            }
        }

        '-' ->
        {
            when (dir)
            {
                Direction.LEFT -> continueBeamPath(x - 1, y, dir, grid)
                Direction.RIGHT -> continueBeamPath(x + 1, y, dir, grid)


                Direction.UP, Direction.DOWN ->
                {
                    continueBeamPath(x - 1, y, Direction.LEFT, grid)
                    continueBeamPath(x + 1, y, Direction.RIGHT, grid)
                }
            }
        }

        '/' ->
        {
            when (dir)
            {
                Direction.LEFT -> continueBeamPath(x, y + 1, Direction.DOWN, grid)
                Direction.UP -> continueBeamPath(x + 1, y, Direction.RIGHT, grid)
                Direction.RIGHT -> continueBeamPath(x, y - 1, Direction.UP, grid)
                Direction.DOWN -> continueBeamPath(x - 1, y, Direction.LEFT, grid)
            }
        }

        '\\' ->
        {
            when (dir)
            {
                Direction.LEFT -> continueBeamPath(x, y - 1, Direction.UP, grid)
                Direction.UP -> continueBeamPath(x - 1, y, Direction.LEFT, grid)
                Direction.RIGHT -> continueBeamPath(x, y + 1, Direction.DOWN, grid)
                Direction.DOWN -> continueBeamPath(x + 1, y, Direction.RIGHT, grid)
            }
        }
    }
}