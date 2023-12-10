import java.io.File

fun main()
{
    // I wasted a lot of time manually working through values until I worked out that I accidentally
    // switched up two columns, after that it was mostly smooth sailing. The second half was brute
    // force and only required slight modifications, but the only "hardship" with it was me needing to
    // rerun it twice, which took around 5-10 minutes per iteration, which isn't entirely surprising
    // considering the program has to crunch through presumably a few tens of billions of numbers.
    // Oh well, I'm done now, certainly a fun little challenge, excited for things to come.

    one()
    two()
}

private fun one()
{
    val fin =
        File("src/main/resources/Day5Input.txt").bufferedReader().readText().split(Regex("[\\D]{2,}")).map { it2 ->
            it2.split(Regex("\\s")).toMutableList().filter() {
                it.isNotEmpty()
            }.map {
                it.toLong()
            }
        }

    fin.removeFirst()
    val seeds = fin.removeFirst().toMutableList()


    for (seedIndex in seeds.indices)
    {
        println("------------------------next seed----------------------")
        for (map in fin)
        {
            for (mapIndex in map.indices)
            {
                if (mapIndex % 3 == 0)
                {
                    if (map[mapIndex + 1] <= seeds[seedIndex] && map[mapIndex + 1] + map[mapIndex + 2] > seeds[seedIndex])
                    {
                        println(
                            "Mapped ${seeds[seedIndex]} to ${seeds[seedIndex] + (map[mapIndex] - map[mapIndex + 1])}, Map ${
                                fin.indexOf(
                                    map
                                )
                            }, Line ${mapIndex / 3}, ${map[mapIndex + 1]} - ${map[mapIndex + 1] + map[mapIndex + 2]}"
                        )
                        seeds[seedIndex] += (map[mapIndex] - map[mapIndex + 1])
                        break
                    }

                }
            }
        }
    }
    println("smallest: ${seeds.minOf { it }}, largest: ${seeds.maxOf { it }}")
}

private fun two()
{
    val fin =
        File("src/main/resources/Day5Input.txt").bufferedReader().readText().split(Regex("[\\D]{2,}")).map { it2 ->
            it2.split(Regex("\\s")).toMutableList().filter() {
                it.isNotEmpty()
            }.map {
                it.toLong()
            }
        }

    fin.removeFirst()
    val seeds = fin.removeFirst().toMutableList()

    var minVal = Long.MAX_VALUE

    for (seedRangeIndex in seeds.indices)
    {
        if (seedRangeIndex % 2L == 0L)
        {
            println("${seedRangeIndex * 5}% completed")
            for (seed in seeds[seedRangeIndex]..seeds[seedRangeIndex] + seeds[seedRangeIndex + 1])
            {
                var temp = seed
                for (map in fin)
                {
                    for (mapIndex in map.indices)
                    {
                        if (mapIndex % 3 == 0)
                        {
                            if (map[mapIndex + 1] <= temp && map[mapIndex + 1] + map[mapIndex + 2] > temp)
                            {
                                temp += (map[mapIndex] - map[mapIndex + 1])
                                break
                            }

                        }
                    }

                }
                if (temp < minVal) minVal = temp
            }
        }
    }
    println("smallest: $minVal")
}