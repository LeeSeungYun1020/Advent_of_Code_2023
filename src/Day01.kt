fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf {
            val first = it.find { c -> c.isDigit() }?.digitToInt() ?: throw IllegalArgumentException()
            val last = it.findLast { c -> c.isDigit() }?.digitToInt() ?: throw IllegalArgumentException()
            first * 10 + last
        }
    }

    fun part2(input: List<String>): Int {
        val numbers = listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4, "five" to 5, "six" to 6, "seven" to 7, "eight" to 8, "nine" to 9)
        return input.sumOf { line ->
            // (index, number)
            val firstDigit = line.indexOfFirst { c -> c.isDigit() }.takeIf { it != -1 }?.let { it to line[it].digitToInt() }
            val lastDigit = line.indexOfLast { c -> c.isDigit() }.takeIf { it != -1 }?.let { it to line[it].digitToInt() }
            val firstLetter = numbers.map { number -> line.indexOf(number.first) to number.second }.filter { it.first != -1 }.minByOrNull { it.first }
            val lastLetter = numbers.map { number -> line.lastIndexOf(number.first) to number.second }.filter { it.first != -1 }.maxByOrNull { it.first }

            val first = when {
                firstDigit == null && firstLetter == null -> throw IllegalArgumentException()
                firstDigit == null -> firstLetter!!.second
                firstLetter == null -> firstDigit.second
                else -> if (firstDigit.first < firstLetter.first) firstDigit.second else firstLetter.second
            }

            val last = when {
                lastDigit == null && lastLetter == null -> throw IllegalArgumentException()
                lastDigit == null -> lastLetter!!.second
                lastLetter == null -> lastDigit.second
                else -> if (lastDigit.first > lastLetter.first) lastDigit.second else lastLetter.second
            }

            first * 10 + last
        }
    }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}




