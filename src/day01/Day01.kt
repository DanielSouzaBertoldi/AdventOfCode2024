package day01

import println
import readInput
import kotlin.math.absoluteValue

private const val DIRECTORY_NAME = "day01"

fun main() {
    fun part1(input: List<String>): Int {
        var leftMap = mutableMapOf<Int, Int>()
        var rightMap = mutableMapOf<Int, Int>()
        var totalDifference = 0

        for (numbers in input) {
            val splitNumbers = numbers.split("   ")
            val leftNumber = splitNumbers[0].toInt()
            val rightNumber = splitNumbers[1].toInt()

            leftMap[leftNumber] = leftMap[leftNumber]?.plus(1) ?: 1
            rightMap[rightNumber] = rightMap[rightNumber]?.plus(1) ?: 1
        }

        leftMap = leftMap.toSortedMap()
        rightMap = rightMap.toSortedMap()

        repeat(leftMap.entries.sumOf { it.value }) {
            totalDifference += (leftMap.keys.first() - rightMap.keys.first()).absoluteValue

            leftMap.entries.first().let { entry ->
                leftMap[entry.key] = entry.value.dec()
                if (entry.value == 0) leftMap.remove(entry.key)
            }
            rightMap.entries.first().let { entry ->
                rightMap[entry.key] = entry.value.dec()
                if (entry.value == 0) rightMap.remove(entry.key)
            }
        }

        return totalDifference
    }

    fun part2(input: List<String>): Int {
        var leftMap = mutableMapOf<Int, Int>()
        var rightMap = mutableMapOf<Int, Int>()
        var similarityScore = 0

        for (numbers in input) {
            val splitNumbers = numbers.split("   ")
            val leftNumber = splitNumbers[0].toInt()
            val rightNumber = splitNumbers[1].toInt()

            leftMap[leftNumber] = leftMap[leftNumber]?.plus(1) ?: 1
            rightMap[rightNumber] = rightMap[rightNumber]?.plus(1) ?: 1
        }

        leftMap = leftMap.toSortedMap()
        rightMap = rightMap.toSortedMap()

        for (entry in leftMap.entries) {
            similarityScore += entry.value * (rightMap[entry.key]?.times(entry.key) ?: 0)
        }

        return similarityScore
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput(DIRECTORY_NAME, "Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput(DIRECTORY_NAME, "Day01")
    part1(input).println()
    part2(input).println()
}
