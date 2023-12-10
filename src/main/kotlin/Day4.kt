import java.io.File

fun main()
{
    val fin = File("src/main/resources/Day4Input.txt").bufferedReader()
    val lines = fin.readLines()

    val counts = arrayOfNulls<Int>(lines.size)
    for(i in counts.indices) counts[i] = 1
    var index = 0

    lines.forEach() {
        var count = 0

        val strList = it.substring(10..<it.length).split(' ').toMutableList()
        strList.removeAll(listOf("", " ", "  "))
        val args = arrayOfNulls<Int>(10)

        for(i in strList.indices)
        {
            if(i < 10)
            {
                args[i] = strList[i].toInt()
            }
            if(i > 10)
            {
                if(args.contains(strList[i].toInt()))
                {
                    count++
                }
            }

        }

        println("Card ${index + 1} has $count matches, incrementing cards from ${index + 2} to ${index + count + 1} by ${counts[index]}...")
        for(j in index + 1..index + count)
        {
            counts[j] = counts[j]!! + counts[index]!!
        }

        index++
    }

    var cardSum = 0
    counts.forEach { cardSum += it!! }
    println("Total number of cards: ${cardSum}")

}