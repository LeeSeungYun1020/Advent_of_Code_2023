fun main() {
    fun part1(input: List<Game>): Int {
        // r 12, g 13, b 14 이하인 게임 id의 합
        return input.sumOf { game ->
            if (game.cubeSetList.all { it.red <= 12 && it.green <= 13 && it.blue <= 14 }) game.id else 0
        }
    }

    fun part2(input: List<Game>): Int {
        // 게임당 필요한 최소 큐브의 곱 -> 모든 게임 합
        return input.sumOf { game ->
            game.cubeSetList.run {
                maxOf { it.red } * maxOf { it.green } * maxOf { it.blue }
            }
        }
    }

    val input = readInput("Day02").map { it.toGame() }
    part1(input).println()
    part2(input).println()
}

data class CubeSet(val red: Int, val green: Int, val blue: Int)

class Game(val id: Int, val cubeSetList: List<CubeSet>)

fun String.toGame(): Game {
    // Game 1: 3 blue, 4 red; 1 red, 2 green, 6 blue; 2 green
    val (title, contents) = split(": ")
    return Game(title.split(" ")[1].toInt(), contents.split("; ").map { it.toCubeSet() })
}

fun String.toCubeSet(): CubeSet {
    // 3 blue, 4 red
    var red = 0
    var green = 0
    var blue = 0

    split(", ").forEach {
        val (count, color) = it.split(" ")
        when (color) {
            "red" -> red = count.toInt()
            "green" -> green = count.toInt()
            "blue" -> blue = count.toInt()
        }
    }

    return CubeSet(red, green, blue)
}
