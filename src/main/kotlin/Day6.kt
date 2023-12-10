import java.io.File
import kotlin.math.sqrt

fun main()
{
    one()
    two()
}

private fun one()
{
    val fin = File("src/main/resources/Day6Input.txt").bufferedReader()
    var temp = fin.readLine().replace(Regex("\\w+:"), "").split(" ").toMutableList()
    temp.removeAll {
        it.isEmpty() || it.contains(Regex("\\W"))
    }

    val times = temp.map { it.toInt() }

    temp = fin.readLine().replace(Regex("\\w+:"), "").split(" ").toMutableList()
    temp.removeAll {
        it.isEmpty() || it.contains(Regex("\\W"))
    }

    val distances = temp.map {it.toInt()}

    val counts = ArrayList<Int>()

    for(index in times.indices)
    {
        var count = 0
        for(i in 1..times[index])
        {
            if(i * (times[index] - i) > distances[index]) count++
        }

        counts.add(count)
    }

    var prod = 1
    for(i in counts) prod *= i

    println("Total amount of winnable configurations: $prod")
}

private fun two()
{
    val fin = File("src/main/resources/Day6Input.txt").bufferedReader()
    val time = fin.readLine().replace(Regex("\\w+:"), "").replace(" ", "").toLong()
    val distance = fin.readLine().replace(Regex("\\w+:"), "").replace(" ", "").toLong()

    var count = 0

    // the task can be respelled as a polynomial, such that -i^2 + time * i - distance > 0.
    // since the highest present degree of i -2, this will be a local maximum,
    // and since negative polynomials of degree 2 all tend towards negative infinity in both
    // x directions, we can say for certain that all the targeted points lie between the roots.
    // this is a very significant improvement in speed, because the roots cover a range of merely
    // a few million while the full range of inputs is ~10^15, which would probably take me a few
    // hours to brute force.

    val roots = findRootsApprox(time, -distance)

    for(i in roots.first..roots.second)
    {
        if(i * (time - i) > distance) count++
    }

    println("Total amount of winnable configurations: $count")
}

private fun findRootsApprox(b:Long, c:Long) :Pair<Long, Long>
{
    val solA = (-b + sqrt((b * b - (-4) * c).toDouble())) / (-2) * 0.99
    val solB = (-b - sqrt((b * b - (-4) * c).toDouble())) / (-2) * 1.01

    // the result isn't entirely accurate since my value underneath the root is slightly
    // larger than maximum floating point precision for integers (2^52 if I recall correctly),
    // but it doesn't entirely matter since all I need is a rough estimate to narrow down the search area,
    // hence the "*0.99" and "*1.01", to be a little more generous in case the floating point
    // imprecision puts me over

    return Pair(solA.toLong(), solB.toLong())
}