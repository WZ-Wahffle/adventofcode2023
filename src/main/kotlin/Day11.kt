import java.io.File
import kotlin.math.abs

fun main()
{
    val fin = File("src/main/resources/Day11Input.txt").bufferedReader()
    val map:MutableList<String> = MutableList(0){""}
    val galaxyMap:MutableList<Pair<Int, Int>> = MutableList(0){Pair(0, 0)}
    val spacesMapRows:MutableList<Int> = MutableList(0){0}
    val spacesMapCols:MutableList<Int> = MutableList(0){0}

    var index = 0
    fin.forEachLine {
        map.addLast(it)

        if(!it.contains('#'))
        {
            spacesMapRows.add(index)
        }
        index++
    }

    for(i in map[0].indices.reversed())
    {
        var contains = false

        for(j in map.indices)
        {
            if(map[j][i] == '#') contains = true
        }

        if(!contains)
        {
            spacesMapCols.add(i)
        }
    }

    for(i in map.indices)
        for(j in map[i].indices)
        {
            if(map[i][j] == '#') galaxyMap.add(Pair(j, i))
        }

    var sum:Long = 0

    val newMap = galaxyMap.map {
        var temp = it
        for(i in spacesMapRows)
        {
            if(it.second > i)
                temp = Pair(temp.first, temp.second + 999999)
        }

        for(i in spacesMapCols)
        {
            if(it.first > i)
                temp = Pair(temp.first + 999999, temp.second)
        }

        temp
    }

    for(i in newMap.indices)
        for(j in newMap.indices)
        {
            if(j > i)
            {
                sum += abs(newMap[i].first - newMap[j].first) + abs(newMap[i].second - newMap[j].second)
                println("${newMap[i]} ${newMap[j]} $sum")
            }
        }

    println(sum)
}