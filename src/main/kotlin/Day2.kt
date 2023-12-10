import java.io.File

fun main()
{
    // working with C for such a long time sure makes me appreciate good string methods properly.

    val fin = File("src/main/resources/Day2Input.txt").bufferedReader()
    var validSum = 0
    var powerSum = 0

    val maxReds = 12
    val maxGreens = 13
    val maxBlues = 14

    var minReds: Int
    var minBlues: Int
    var minGreens: Int

    fin.forEachLine {
        val strings = it.split("\\s+".toRegex())
        var valid = true
        minBlues = 0
        minGreens = 0
        minReds = 0

        for (i in strings.indices)
        {
            if (i != 0 && i % 2 == 0)
            {
                when (strings[i + 1]) {
                    "red", "red,", "red;" -> {
                        if (valid) valid = (strings[i].toInt() <= maxReds)
                        minReds = minReds.coerceAtLeast(strings[i].toInt())
                    }

                    "blue", "blue,", "blue;" -> {
                        if (valid) valid = (strings[i].toInt() <= maxBlues)
                        minBlues = minBlues.coerceAtLeast(strings[i].toInt())
                    }

                    "green", "green,", "green;" -> {
                        if (valid) valid = (strings[i].toInt() <= maxGreens)
                        minGreens = minGreens.coerceAtLeast(strings[i].toInt())

                    }
                }
            }
        }

        powerSum += minReds * minGreens * minBlues

        if (valid) validSum += strings[1].replace(":", "").toInt()
    }

    println("Sum of valid game indices: $validSum")
    println("Sum of powers of all games: $powerSum")
}
