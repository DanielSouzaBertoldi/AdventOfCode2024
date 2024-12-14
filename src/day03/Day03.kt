package day03

import println
import readText
import kotlin.math.abs
import kotlin.math.absoluteValue

private const val DIRECTORY_NAME = "day03"

fun main() {

    fun part1(input: String): Int {
        val regex = "mul\\((\\d+),(\\d+)\\)".toRegex()
        val matches = regex.findAll(input)
        var total = 0

        matches.forEach {
            total += it.groupValues[1].toInt() * it.groupValues[2].toInt()
        }
        return total
    }

    fun part2(input: String): Int {
        val regex = "mul\\((\\d+),(\\d+)\\)".toRegex()
        var isMulEnabled = true
        var total = 0

        var i = 0
        while (i < input.length) {
            when {
                input.startsWith("do()", i) -> {
                    isMulEnabled = true
                    i += 4
                }
                input.startsWith("don't()", i) -> {
                    isMulEnabled = false
                    i += 7
                }
                input.startsWith("mul(", i) && isMulEnabled -> {
                    regex.find(input.substring(i))?.let { match ->
                        total += match.groupValues[1].toInt() * match.groupValues[2].toInt()
                        i += match.range.last + 1
                    }
                }
                else -> i++
            }
        }

        return total
    }

    // Or read a large test input from the `src/Day03_test.txt` file:
    val testInput = readText(DIRECTORY_NAME, "Day03_test")
    check(part1(testInput) == 161)
    check(part2(testInput) == 48)

    // Read the input from the `src/Day03.txt` file.
    val input = readText(DIRECTORY_NAME, "Day03")
    part1(input).println()
    part2(input).println()
}
