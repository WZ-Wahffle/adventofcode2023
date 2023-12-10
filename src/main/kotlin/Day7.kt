import java.io.File

fun main()
{
    // function one was modified in situ because I was getting tired of scrolling, so only function 2 for today
    two()
}

private fun two()
{
    val strings = File("src/main/resources/Day7Input.txt").bufferedReader().readLines().toMutableList()
    for(i in strings.indices)
    {
        for(j in i + 1..<strings.size)
        {
            if(!isFirstSmaller(strings[i].substring(0..4), strings[j].substring(0..4)))
            {
                val temp = strings[i]
                strings[i] = strings[j]
                strings[j] = temp
            }
        }
    }

    var sum:Long = 0
    for(i in strings.indices)
    {
        println("${strings[i]} ${findType(strings[i].substring(0..4))}")
        sum += strings[i].replace(Regex("\\w{5} "), "").toLong() * (i + 1)
    }

    println(sum)
}

fun isFirstSmaller(s1:String, s2:String) : Boolean
{
    if(findType(s1) != findType(s2))
    {
        return findType(s1) < findType(s2)
    }

    for(i in s1.indices)
    {
        if(s1[i] != s2[i])
        {
            return valueOf(s1[i]) < valueOf(s2[i])
        }
    }

    return true
}

fun findType(s1:String) : Int
{
    val spread:Array<Int> = Array(13){0}

    for(i in s1) when(i)
    {
        'J' -> spread[0]++
        '2' -> spread[1]++
        '3' -> spread[2]++
        '4' -> spread[3]++
        '5' -> spread[4]++
        '6' -> spread[5]++
        '7' -> spread[6]++
        '8' -> spread[7]++
        '9' -> spread[8]++
        'T' -> spread[9]++
        'Q' -> spread[10]++
        'K' -> spread[11]++
        'A' -> spread[12]++
    }

    when(spread.copyOfRange(1, spread.size).max() + spread[0])
    {
        5 -> return 6
        4 -> return 5
        3 -> {
            if(spread.count{it == 2} == 2 || (spread.indexOf(2) != -1 && spread.indexOf(3) != -1))
            {
                return 4
            }

            return 3
        }
        2 -> {
            if(spread.count { it == 2 } == 2)
            {
                return 2
            }

            return 1
        }
        1 -> return 0
        else -> return -1
    }
}

fun valueOf(c:Char) : Int
{
    return when(c)
    {
        'J' -> 0
        '2' -> 1
        '3' -> 2
        '4' -> 3
        '5' -> 4
        '6' -> 5
        '7' -> 6
        '8' -> 7
        '9' -> 8
        'T' -> 9
        'Q' -> 10
        'K' -> 11
        'A' -> 12
        else -> -1
    }
}