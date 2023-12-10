import java.io.File
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

fun main()
{
    // wow, quite a step-up in difficulty from day 2. Can't say I expected this to become quite so
    // thought-provoking this early on. The second part was significantly easier since my method was already
    // laid out quite well for it, so I just turned the whole thing into a big hash map and called it a day.
    // already a big fan of this variety of ranged loops, haven't had a chance to use them until now
    // due primarily to being a C/C++ developer. So far, very excited about Kotlin, and equally so for
    // the days to come.

    val fin = File("src/main/resources/Day3Input.txt").bufferedReader()
    val arr = ArrayList<String>()
    var sum = 0
    var gearSum = 0
    val potentialGears: MutableMap<Pair<Int, Int>, ArrayList<String>> = HashMap()

    fin.forEachLine {
        arr.add(it + ".")
    }

    for(i in arr.indices)
    {
        var digit = ""

        for(j in arr[i].indices)
        {
            if(arr[i][j].isDigit())
            {
                digit += arr[i][j]
            }
            else if(!arr[i][j].isDigit() && j > 0 && arr[i][j - 1].isDigit())
            {
                if(processSubstring(arr, digit, i, j, potentialGears)) sum += digit.toInt()
                digit = ""
            }
        }

    }

    for(i in potentialGears)
    {
        if(i.value.size == 2)
        {
            gearSum += i.value[0].toInt() * i.value[1].toInt()
        }
    }
    println("Sum of part numbers: $sum")
    println("Sum of gear ratios: $gearSum")
}

fun processSubstring(arr: ArrayList<String>, substr: String, iEnd: Int, jEnd: Int, potentialGears: MutableMap<Pair<Int, Int>, ArrayList<String>>): Boolean
{
    if(substr == "") return false

    for(i in Math.max(iEnd - 1, 0)..Math.min(iEnd + 1, arr.size - 1)) {
        for (j in Math.max(jEnd - substr.length - 1, 0)..Math.min(jEnd, arr[i].length - 1)) {

            if (arr[i][j] != '.' && !arr[i][j].isDigit())
            {
                if(arr[i][j] == '*')
                {
                    var temp = potentialGears[Pair(i, j)]
                    if(temp == null)
                    {
                        temp = ArrayList()
                    }
                    temp.add(substr)
                    potentialGears[Pair(i, j)] = temp
                }
                return true
            }
        }
    }

    return false

}