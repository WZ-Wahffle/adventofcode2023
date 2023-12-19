import java.io.File
import java.lang.Exception

val proc:HashMap<String, MutableList<Process>> = hashMapOf()
class Process(private val toCheckIdx:Int, private val less:Boolean, private val number:Int, private val ifMatch:String, private val isLast:Boolean)
{
    fun eval(limits:MutableList<Int>, idx:Int, key:String) : Long
    {
        if(isLast)
        {
            if(ifMatch == "A") return (limits[1] - limits[0] + 1).toLong() * (limits[3] - limits[2] + 1).toLong() * (limits[5] - limits[4] + 1).toLong() * (limits[7] - limits[6] + 1).toLong()

            if(ifMatch == "R") return 0

            return proc[ifMatch]!![0].eval(limits.toMutableList(), 0, ifMatch)
        }

        val newListMatches = limits.toMutableList()
        val newListMisses = limits.toMutableList()

        if(less)
        {
            if(newListMatches[2 * toCheckIdx + 1] > number) newListMatches[2 * toCheckIdx + 1] = number - 1
            if(newListMisses[2 * toCheckIdx] < number - 1) newListMisses[2 * toCheckIdx] = number
        }
        else
        {
            if(newListMatches[2 * toCheckIdx] < number + 1) newListMatches[2 * toCheckIdx] = number + 1
            if(newListMisses[2 * toCheckIdx + 1] > number) newListMisses[2 * toCheckIdx + 1] = number
        }

        return proc[key]!![idx + 1].eval(newListMisses, idx + 1, key) + proc[ifMatch]!![0].eval(newListMatches, 0, ifMatch)

    }
}
fun main()
{
    val fin = File("src/main/resources/Day19Input.txt").bufferedReader()

    while(true)
    {
        val line = fin.readLine().replace("{", " ").replace("}", " ")
        if(line.isEmpty()) break
        val temp = line.split(" ")
        val key = temp[0]
        proc[key] = mutableListOf()
        val processes = temp[1].split(",")

        for(i in processes)
        {
            if(!i.contains(':'))
            {
                proc[key]!!.add(Process(0, false, 0, i, true))
                break
            }
            val toCheckIdx = when(i[0])
            {
                'x' -> 0
                'm' -> 1
                'a' -> 2
                's' -> 3
                else -> throw Exception("Incorrect first char: '${i[0]}'")
            }

            val less = i[1] == '<'

            val number = i.substringBefore(':').substring(2).toInt()

            val ifMatch = i.substringAfter(':')

            proc[key]!!.add(Process(toCheckIdx, less, number, ifMatch, false))
        }
    }

    proc["A"] = mutableListOf(Process(0, false, 0, "A", true))
    proc["R"] = mutableListOf(Process(0, false, 0, "R", true))

    println(proc["in"]!![0].eval(mutableListOf(1, 4000, 1, 4000, 1, 4000, 1, 4000), 0, "in"))
}

