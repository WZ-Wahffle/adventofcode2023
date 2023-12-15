import java.io.File
import kotlin.collections.HashMap


fun main()
{
    // This solution is a testament to the potential of using DP for a solution to a problem.
    // The original iterative solution I wrote would take a few seconds on the first task and would likely have
    // taken years to run through the second part, but it was still quite slow. Rewriting it with a recursive
    // solution made things better, but without caching it was still really slow and barely processed 100 lines
    // of part 2 in 4 hours. Attempt 3 of a recursive approach was only marginally faster at first, but after adding
    // primitive caching the time to solve shot down from a few tens of hours to a mind-bogglingly low 150 ms.
    // I think I may now become one of those people that try to solve anything and everything with recursion.

    val fin = File("src/main/resources/Day12Input.txt").bufferedReader()

    var count = 0L

    fin.forEachLine {
        val inputString = it.split(" ")[0]
        val inputNumbers = it.split(" ")[1].split(",").map { it2 -> it2.toInt() }

        val inputString2 = "$inputString?$inputString?$inputString?$inputString?$inputString"
        val inputNumbers2 = inputNumbers + inputNumbers + inputNumbers + inputNumbers + inputNumbers

        val cache: HashMap<Pair<Int, Int>, Long> = hashMapOf()

        count += evalString(inputString2, inputNumbers2, 0, 0, cache)
    }

    println(count)
}

fun evalString(inputString: String, inputNumbers: List<Int>, inputStringOffset: Int, inputNumbersOffset: Int,
               cache: HashMap<Pair<Int, Int>, Long>): Long
{
    if (cache.containsKey(Pair(inputStringOffset, inputNumbersOffset))) return cache[Pair(inputStringOffset,
        inputNumbersOffset)]!!

    if (inputString.length <= inputStringOffset && inputNumbers.size == inputNumbersOffset - 1) return 1
    if (inputNumbers.size <= inputNumbersOffset && !inputString.substring(inputStringOffset).contains("#")) return 1
    if (inputNumbers.size <= inputNumbersOffset) return 0
    if (inputString.length <= inputStringOffset) return 0

    val cacheIdx = Pair(inputStringOffset, inputNumbersOffset)

    return when (inputString[inputStringOffset])
    {
        '.' ->
        {
            cache[cacheIdx] =
                evalString(inputString, inputNumbers, inputStringOffset + 1, inputNumbersOffset, cache)
            return cache[cacheIdx]!!
        }

        '#' ->
        {
            // if a dot exists afterward
            if (inputStringOffset + inputNumbers[inputNumbersOffset] < inputString.length && !inputString.substring(
                    inputStringOffset, inputStringOffset + inputNumbers[inputNumbersOffset])
                    .contains(".") && inputString[inputStringOffset + inputNumbers[inputNumbersOffset]] != '#')
            {
                cache[cacheIdx] =
                    evalString(inputString, inputNumbers, inputStringOffset + inputNumbers[inputNumbersOffset] + 1,
                        inputNumbersOffset + 1, cache)
                return cache[cacheIdx]!!
            }
            // if there is no dot
            else if (inputStringOffset + inputNumbers[inputNumbersOffset] == inputString.length && !inputString.substring(
                    inputStringOffset, inputStringOffset + inputNumbers[inputNumbersOffset]).contains("."))
            {
                cache[cacheIdx] =
                    evalString(inputString, inputNumbers, inputStringOffset + inputNumbers[inputNumbersOffset],
                        inputNumbersOffset + 1, cache)
                return cache[cacheIdx]!!
            }
            else
            {
                0
            }
        }

        '?' ->
        {
            if (inputStringOffset + inputNumbers[inputNumbersOffset] < inputString.length && !inputString.substring(
                    inputStringOffset, inputStringOffset + inputNumbers[inputNumbersOffset])
                    .contains(".") && inputString[inputStringOffset + inputNumbers[inputNumbersOffset]] != '#')
            {
                cache[cacheIdx] =
                    evalString(inputString, inputNumbers, inputStringOffset + inputNumbers[inputNumbersOffset] + 1,
                        inputNumbersOffset + 1, cache) + evalString(inputString, inputNumbers, inputStringOffset + 1,
                        inputNumbersOffset, cache)
                return cache[cacheIdx]!!
            }
            else if (inputStringOffset + inputNumbers[inputNumbersOffset] == inputString.length && !inputString.substring(
                    inputStringOffset, inputStringOffset + inputNumbers[inputNumbersOffset]).contains("."))
            {
                cache[cacheIdx] =
                    evalString(inputString, inputNumbers, inputStringOffset + inputNumbers[inputNumbersOffset],
                        inputNumbersOffset + 1, cache)
                return cache[cacheIdx]!!
            }
            else
            {
                cache[cacheIdx] =
                    evalString(inputString, inputNumbers, inputStringOffset + 1, inputNumbersOffset, cache)
                return cache[cacheIdx]!!
            }
        }

        else ->
        {
            println("bad")
            0
        }
    }
}
