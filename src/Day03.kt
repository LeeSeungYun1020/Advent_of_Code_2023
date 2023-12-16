fun main() {
    val input = readInput("Day03")

    fun isAdjacent(x: Int, y: Int): Boolean {
        for (i in x - 1..x + 1) {
            if (i < 0 || i > input.lastIndex) continue
            for (j in y - 1..y + 1) {
                if (j < 0 || j > input.lastIndex) continue
                if (!input[i][j].let { it.isDigit() || it == '.' }) {
                    return true
                }
            }
        }
        return false
    }

    fun part1(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { x, line ->
            var number = 0
            var adjacent = false

            fun init() {
                number = 0
                adjacent = false
            }

            fun validate() {
                if (adjacent) {
                    sum += number
                }
                init()
            }

            line.forEachIndexed { y, char ->
                when {
                    char.isDigit() -> {
                        // 숫자 / 계산
                        number = number * 10 + char.digitToInt()
                        if (!adjacent && isAdjacent(x, y)) {
                            adjacent = true
                        }
                    }

                    number != 0 -> {
                        // 숫자 -> 다른 녀석 / 정산
                        validate()
                    }

                    else -> {
                        // 아님 / 초기화
                        init()
                    }
                }
            }
            if (number != 0) {
                validate()
                init()
            }
        }
        return sum
    }

    fun findUniqueNumber(board: List<MutableList<Boolean>>, x: Int, y: Int, px: Int, py: Int): Int {
        val line = input[px]
        val fx = x - px + 1
        val fy = py - y + 1
        var number = line[py].digitToInt()
        var position = 10

        if (board[fx][fy]) {
            return -1
        }
        board[fx][fy] = true

        for (i in py - 1 downTo 0) {
            if (!line[i].isDigit()) break
            if (i - y + 1 >= 0) board[fx][i - y + 1] = true
            number += line[i].digitToInt() * position
            position *= 10
        }
        for (i in py + 1..input.first().lastIndex) {
            if (!line[i].isDigit()) break
            if (i - y + 1 <= 2) board[fx][i - y + 1] = true
            number = number * 10 + line[i].digitToInt()
        }
        return number
    }

    fun part2(input: List<String>): Int {
        var sum = 0
        input.forEachIndexed { x, line ->
            line.forEachIndexed { y, char ->
                val find = List(3) { MutableList(3) { false } }
                val numbers = mutableListOf<Int>()
                // gear
                if (char == '*') {
                    // 주변 탐색해서 숫자 찾아야
                    for (i in x - 1..x + 1) {
                        if (i < 0 || i > input.lastIndex) continue
                        for (j in y - 1..y + 1) {
                            if (j < 0 || j > input.first().lastIndex) continue
                            if (input[i][j].isDigit()) {
                                findUniqueNumber(find, x, y, i, j).takeIf { it != -1 }?.let { numbers.add(it) }
                            }
                        }
                    }
                }
                if (numbers.size == 2) {
                    sum += numbers.first() * numbers.last()
                }
            }
        }
        return sum
    }

    part1(input).println()
    part2(input).println()
}