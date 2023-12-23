import kotlin.math.pow

fun main() {
    fun part1(input: List<Card>): Int {
        return input.sumOf { card ->
            card.my.count { card.win.contains(it) }.takeIf { it > 0 }?.let { 2.0.pow(it - 1) } ?: 0.0
        }.toInt()
    }

    fun part2(input: List<Card>): Int {
        val countList = input.map { card -> card.my.count { card.win.contains(it) } }
        val ticketList = MutableList(countList.size) { 1 }

        for (i in countList.indices) {
            for (j in 1..countList[i]) {
                if (i + j > countList.lastIndex) break
                ticketList[i + j] += ticketList[i]
            }
        }
        return ticketList.sum()
    }

    val input = readInput("Day04").map { it.toCard() }
    part1(input).println()
    part2(input).println()
}

data class Card(val my: List<Int>, val win: List<Int>)

fun String.toCard(): Card {
    // Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
    val (_, myString, winString) = split(":", "|").map { it.trim() }
    return Card(
            my = myString.split(" ").filter { it.isNotBlank() }.map { it.toInt() },
            win = winString.split(" ").filter { it.isNotBlank() }.map { it.toInt() }
    )
}
