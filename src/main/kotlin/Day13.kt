import java.io.File
import java.lang.Integer.min

fun main()
{
    val fin = File("src/main/resources/Day13Input.txt").bufferedReader()
    var buffer: MutableList<MutableList<Char>> = mutableListOf()
    var componentSum = 0L

    fin.forEachLine {

        if (it.isEmpty())
        {
            // find row
            val tempIdx = findRowCol(buffer)

            val newBuffer = buffer.toMutableList()

            outer@ for (i in newBuffer.indices)
                for (j in newBuffer[i].indices)
                {
                    newBuffer[i][j] = if (newBuffer[i][j] == '#') '.'
                    else '#'

                    val tempIdx2 = findRowCol(newBuffer, tempIdx)
                    if (tempIdx2 != 0)
                    {
                        componentSum += tempIdx2
                        break@outer
                    }

                    newBuffer[i][j] = if (newBuffer[i][j] == '#') '.'
                    else '#'
                }

            buffer = mutableListOf()

        }
        else
        {
            buffer.add(it.toMutableList())
        }

    }

    println("Sum of indices: $componentSum")

}

fun findRowCol(buffer: MutableList<MutableList<Char>>, ignoredValue: Int = -1): Int
{
    var valid:Boolean
    for (i in 1..<buffer.size)
    {
        valid = true
        for (j in 0..<min(buffer.size - i, i))
        {
            if (buffer[i + j] != buffer[i - j - 1])
            {
                valid = false
                break
            }
        }

        if (valid && (ignoredValue == -1 || ignoredValue != i * 100))
        {
            return 100 * i
        }
    }

    // find col, if there is no row

    for (i in 1..<buffer[0].size)
    {
        valid = true
        for (j in 0..<min(buffer[0].size - i, i))
        {

            buffer.forEach { it2 ->
                if (it2[i + j] != it2[i - j - 1]) valid = false
            }
        }

        if (valid && (ignoredValue == -1 || ignoredValue != i))
        {
            return i
        }
    }

    return 0
}