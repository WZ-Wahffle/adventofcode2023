import java.io.File

fun main()
{
    one()
    two()
}

private fun one()
{
    val fin = File("src/main/resources/Day8Input.txt").bufferedReader()
    val instructions = fin.readLine()

    val list: HashMap<String, Pair<String, String>> = HashMap()

    fin.readLine()

    fin.forEachLine {
        list[it.substring(0..2)] = Pair(it.substring(7..9), it.substring(12..14))
    }

    var steps = 0
    var cur = "AAA"

    while (cur != "ZZZ")
    {
        cur = if (instructions[steps % instructions.length] == 'R')
        {
            list[cur]?.second ?: "ERR"
        }
        else
        {
            list[cur]?.first ?: "ERR"
        }

        steps++
    }
    println(steps)

}

private fun two()
{
    val fin = File("src/main/resources/Day8Input.txt").bufferedReader()
    val instructions = fin.readLine()

    val list: HashMap<String, Pair<String, String>> = HashMap()
    val startingValues: MutableList<String> = MutableList(0) { "" }

    fin.readLine()

    fin.forEachLine {
        list[it.substring(0..2)] = Pair(it.substring(7..9), it.substring(12..14))
        if (it.elementAt(2) == 'A') startingValues.addLast(it.substring(0..2))
    }

    val lengths = MutableList(0) { 0 }

    for (i in startingValues)
    {
        var cur = i
        var steps = 0
        while (cur.elementAt(2) != 'Z')
        {
            cur = if (instructions[steps % instructions.length] == 'R')
            {
                list[cur]?.second ?: "ERR"
            }
            else
            {
                list[cur]?.first ?: "ERR"
            }

            steps++
        }

        lengths.addLast(steps)
    }

    var lcm:Long = lengths[0].toLong()

    for(i in 1..<lengths.size)
    {
        lcm = lcm(lcm, lengths[i].toLong())
    }

    println(lcm)
}

private fun lcm(i1:Long, i2:Long) : Long
{
    return i1 * i2 / gcd(i1, i2)
}

private fun gcd(i1:Long, i2:Long) : Long
{
    var first = i1
    var second = i2

    while(first % second != 0L)
    {
        val newFirst = second
        val newSecond = first % second

        first = newFirst
        second = newSecond
    }

    return second
}