import java.io.File

fun main()
{
    val fin = File("src/main/resources/Day9Input.txt").bufferedReader()
    var extrapolatedValuesRight = 0
    var extrapolatedValuesLeft = 0

    fin.forEachLine {
        val lines:MutableList<MutableList<Int>> = arrayListOf()
        lines.add(it.split(" ").map { it2 -> it2.toInt()}.toMutableList())

        var index = 0
        while(!lines[index].all { num -> num == 0 })
        {
            val toAdd = arrayListOf<Int>()
            for(i in 1..<lines[index].size)
            {
                toAdd.add(lines[index][i] - lines[index][i - 1])
            }

            lines.add(toAdd)
            index++
        }

        // extrapolating forward
        for(i in lines)
        {
            extrapolatedValuesRight += i[i.lastIndex]
        }

        // extrapolating backward

        lines.last().add(0, 0)

        for(i in (lines.size - 2).downTo(0))
        {
            lines[i].add(0, lines[i][0] - lines[i + 1][0])
        }

        extrapolatedValuesLeft += lines[0][0]

    }

    println("Extrapolating forward: $extrapolatedValuesRight")
    println("Extrapolating backward: $extrapolatedValuesLeft")
}