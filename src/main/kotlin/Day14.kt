import java.io.File

const val north = 0
const val west = 1
const val south = 2
const val east = 3

fun main()
{
    val fin = File("src/main/resources/Day14Input.txt").bufferedReader()
    val map:MutableList<MutableList<Char>> = mutableListOf()
    val seenResults:MutableList<Int> = mutableListOf()

    fin.readLine()

    fin.forEachLine {
        map.add(it.toMutableList())
    }

    var tempVal:Int
    var cycleCount = 0
    while(true)
    {
        cycleCount++
        rotateGraph(north, map)
        rotateGraph(west, map)
        rotateGraph(south, map)
        rotateGraph(east, map)
        tempVal = calculateLoad(map)
        if(seenResults.contains(tempVal)) break
        else seenResults.add(tempVal)
    }

    println("Total load after 1000000000 (or rather, $cycleCount) cycles: ${calculateLoad(map)}")
}

fun rotateGraph(direction:Int, map:MutableList<MutableList<Char>>)
{
    var madeMove = true

    val fallX = if(direction == north) -1 else if(direction == south) 1 else 0
    val fallY = if(direction == west) -1 else if(direction == east) 1 else 0

    while(madeMove)
    {
        madeMove = false
        for (j in (if(direction == west) 1 else 0)..<(if(direction == east) map[0].size - 1 else map[0].size))
        {
            for (i in (if(direction == north) 1 else 0)..<(if(direction == south) map.size - 1 else map.size))
            {
                if (map[i][j] == 'O' && map[i + fallX][j + fallY] == '.')
                {
                    map[i][j] = '.'
                    map[i + fallX][j + fallY] = 'O'
                    madeMove = true
                }
            }
        }
    }
}

fun calculateLoad(map:MutableList<MutableList<Char>>) : Int
{
    var retVal = 0
    for(i in map.indices)
    {
        for (j in map[i].indices)
        {
            if (map[i][j] == 'O') retVal += map.size - i
        }
    }

    return retVal
}