import java.io.File

fun main() {

    // first day Kotlin thoughts:
    // certainly nothing too out of the ordinary, coming from Java and other C-like languages
    // the lack of semicolons is a little unusual, but nothing to lose sleep over
    // big fan of the more concise syntax, the days of public static void main(String[] args) may finally be behind me!
    // still not used to type inference, but that'll settle in due time
    // enforced null safety, too, I understand why it is necessary, but man, it's something to get used to
    // also, kinda wish there was a "when" statement with a return value like in Java, provided I didn't just miss it when looking for it
    // anyway, new shiny language, I'll need to do another few days in it to get used to it

    val fin = File("src/main/resources/Day1Input.txt").bufferedReader()
    var calibrationValue = 0

    fin.forEachLine {
        val str = it

        val strs = listOf(
            "one",
            "two",
            "three",
            "four",
            "five",
            "six",
            "seven",
            "eight",
            "nine",
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9"
        )
        val firstVal = str.findAnyOf(strs)
        val lastVal = str.findLastAnyOf(strs)

        if (firstVal != null) {
            when (firstVal.second) {
                "one", "1" -> calibrationValue += 10
                "two", "2" -> calibrationValue += 20
                "three", "3" -> calibrationValue += 30
                "four", "4" -> calibrationValue += 40
                "five", "5" -> calibrationValue += 50
                "six", "6" -> calibrationValue += 60
                "seven", "7" -> calibrationValue += 70
                "eight", "8" -> calibrationValue += 80
                "nine", "9" -> calibrationValue += 90
            }
        }

        if (lastVal != null) {
            when (lastVal.second) {
                "one", "1" -> calibrationValue += 1
                "two", "2" -> calibrationValue += 2
                "three", "3" -> calibrationValue += 3
                "four", "4" -> calibrationValue += 4
                "five", "5" -> calibrationValue += 5
                "six", "6" -> calibrationValue += 6
                "seven", "7" -> calibrationValue += 7
                "eight", "8" -> calibrationValue += 8
                "nine", "9" -> calibrationValue += 9
            }
        }

    }

    println(calibrationValue)

}