import java.io.File


fun main()
{
    var hashSum = 0L
    val boxes: Array<MutableList<String>> = Array(256) { MutableList(0) { "" } }
    File("src/main/resources/Day15Input.txt").bufferedReader().readLine().split(",").forEach {

        val stringIdx = hashAlgorithm(it.split("=", "-")[0])

        if (it.contains("-"))
        {
            boxes[stringIdx].removeIf { it2 ->
                it.split("-")[0] == it2.split("=")[0]
            }
        }
        else
        {
            boxes[stringIdx].forEach { it2 ->
                if (it2.split("=")[0] == it.split("=")[0]) boxes[stringIdx][boxes[stringIdx].indexOf(it2)] = it
            }

            if (!boxes[stringIdx].contains(it)) boxes[stringIdx].add(it)

        }
    }

    for (i in boxes.indices)
        for (j in boxes[i]) hashSum += (i + 1) * (boxes[i].indexOf(j) + 1) * j.last().digitToInt()

    println(hashSum)
}

fun hashAlgorithm(input: String): Int
{
    var temp = 0
    for (i in input)
    {
        temp += i.code
        temp *= 17
        temp %= 256
    }

    return temp
}