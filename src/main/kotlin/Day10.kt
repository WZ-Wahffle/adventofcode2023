import java.io.File

const val ERR = -1
const val UP = 0
const val RIGHT = 1
const val DOWN = 2
const val LEFT = 3
const val DONE = 4
fun main()
{
    val fin = File("src/main/resources/Day10Input.txt")
    val map = MutableList(0) { MutableList(0) { ' ' } }
    val lineList = MutableList(0){Pair(0, 0)}
    fin.forEachLine {
        map.addLast(it.toMutableList())
    }

    val yStart = map.indexOf(map.find {
        it.contains('S')
    })
    var location = Pair(map[yStart].indexOf('S'), yStart)
    var length = 0
    var previousDirection: Int = findStartDir(location, map)

    do
    {
        lineList.add(location)
        when (previousDirection)
        {
            UP -> location = Pair(location.first, location.second - 1)
            DOWN -> location = Pair(location.first, location.second + 1)
            LEFT -> location = Pair(location.first - 1, location.second)
            RIGHT -> location = Pair(location.first + 1, location.second)
        }
        length++
        previousDirection = findDir(map[location.second][location.first], previousDirection)


    } while (previousDirection != DONE)

    if (length % 2 != 0)
    {
        println("Furthest point: ${length / 2 + 1}")
    }
    else
    {
        println("Furthest point: ${length / 2}")
    }

    val loc = Pair(map[yStart].indexOf('S'), yStart)
    map[loc.second][loc.first] = findValForS(loc, map)

    for(i in map.indices)
    {
        for(j in map[i].indices)
        {
            if(!lineList.contains(Pair(j, i)))
            {
                map[i][j] = ' '
            }

        }

    }

    lineList.sortBy {
        it.second
    }

    lineList.reverse()

    var sum = 0

    for(i in map.indices)
    {
        var parity = 0
        var unresolvedF = false
        var unresolvedL = false
        val temp = lineList.filter { it.second == i }.sortedBy { it.first }

        for(j in temp.indices)
        {
            when(map[temp[j].second][temp[j].first])
            {
                '|' -> {
                    parity++
                }
                'F' -> unresolvedF = true
                'L' -> unresolvedL = true
                '7' -> {
                    if(unresolvedF)
                    {
                        unresolvedF = false
                    }
                    else
                    {
                        unresolvedL = false
                        parity++
                    }
                }
                'J' -> {
                    if(unresolvedL)
                    {
                        unresolvedL = false
                    }
                    else
                    {
                        unresolvedF = false
                        parity++
                    }
                }

            }

            if(j != temp.size - 1)
            {
                if(!unresolvedL && !unresolvedF && temp[j].first + 1 != temp[j + 1].first && parity % 2 == 1)
                {
                    sum += (temp[j + 1].first - 1) - temp[j].first
                }
            }
        }
    }

    println("The sum of all empty spaces is: $sum")

}

fun findStartDir(loc: Pair<Int, Int>, map: MutableList<MutableList<Char>>): Int
{
    if ("FL-".any {
            map[loc.second][loc.first - 1] == it
        }) return LEFT
    if ("|LJ".any {
            map[loc.second + 1][loc.first] == it
        }) return DOWN
    if ("7J-".any {
            map[loc.second][loc.first + 1] == it
        }) return RIGHT
    if ("|7F".any {
            map[loc.second - 1][loc.first] == it
        }) return UP

    return ERR
}

fun findValForS(loc: Pair<Int, Int>, map: MutableList<MutableList<Char>>): Char
{
    val temp: MutableList<Int> = MutableList(0) { 0 }
    if ("F7|".contains(map[loc.second - 1][loc.first])) temp.add(UP)
    if ("J7-".contains(map[loc.second][loc.first + 1])) temp.add(RIGHT)
    if ("LF-".contains(map[loc.second][loc.first - 1])) temp.add(LEFT)
    if ("JL|".contains(map[loc.second + 1][loc.first])) temp.add(DOWN)

    if (temp.contains(UP) && temp.contains(LEFT)) return 'J'
    if (temp.contains(UP) && temp.contains(RIGHT)) return 'L'
    if (temp.contains(UP) && temp.contains(DOWN)) return '|'
    if (temp.contains(LEFT) && temp.contains(RIGHT)) return '-'
    if (temp.contains(LEFT) && temp.contains(DOWN)) return '7'
    if (temp.contains(DOWN) && temp.contains(RIGHT)) return 'F'

    return '?'
}

fun findDir(c: Char, prev: Int): Int
{
    if (c == 'S') return DONE
    if (c == '|' && prev == UP) return UP
    if (c == '|' && prev == DOWN) return DOWN
    if (c == '-' && prev == RIGHT) return RIGHT
    if (c == '-' && prev == LEFT) return LEFT
    if (c == 'F' && prev == LEFT) return DOWN
    if (c == 'F' && prev == UP) return RIGHT
    if (c == 'L' && prev == LEFT) return UP
    if (c == 'L' && prev == DOWN) return RIGHT
    if (c == '7' && prev == RIGHT) return DOWN
    if (c == '7' && prev == UP) return LEFT
    if (c == 'J' && prev == RIGHT) return UP
    if (c == 'J' && prev == DOWN) return LEFT

    println("No direction found!")
    return ERR
}

