package day02

import println
import readText
import kotlin.math.abs
import kotlin.math.absoluteValue

private const val DIRECTORY_NAME = "day02"
private const val UNKNOWN = "UNKNOWN"
private const val INCREASING = "INCREASING"
private const val DECREASING = "DECREASING"

fun main() {
    var previousNum: Int
    var isSafe: Boolean
    var levelsType: String

    fun part1(input: String): Int {
        var safeReports = 0

        outer@for (line in input.lines()) {
            isSafe = false
            previousNum = 0
            levelsType = UNKNOWN

            inner@for (num in line.split(" ")) {
                val number = num.toInt()
                val currentLevelDifference = (previousNum - number).absoluteValue

                if (currentLevelDifference < 1 || currentLevelDifference > 3 && previousNum != 0) {
                    isSafe = false
                    break@inner
                }

                if (levelsType == UNKNOWN && previousNum != 0) {
                    when {
                        previousNum > number -> levelsType = DECREASING
                        previousNum < number -> levelsType = INCREASING
                    }
                }
                else if (number > previousNum && levelsType == DECREASING) {
                    isSafe = false
                    break@inner
                }
                else if (number < previousNum && levelsType == INCREASING) {
                    isSafe = false
                    break@inner
                }
                previousNum = number

                isSafe = true
            }

            if (isSafe) safeReports += 1
        }

        return safeReports
    }

    fun part2(input: String): Int {
        var safeReports = 0
        fun hasDuplicates(list: List<Int>): Boolean {
            return list.size != list.toSet().size
        }

        fun isStrictlyIncreasing(list: List<Int>): Boolean {
            for (i in 0 until list.size - 1) {
                if (list[i] >= list[i + 1]) return false
            }
            return true
        }

        fun isStrictlyDecreasing(list: List<Int>): Boolean {
            for (i in 0 until list.size - 1) {
                if (list[i] <= list[i + 1]) return false
            }
            return true
        }

        fun getMaxDifference(list: List<Int>): Int {
            var maxDiff = Int.MIN_VALUE
            for (i in 0 until list.size - 1) {
                val diff = abs(list[i] - list [i + 1])
                maxDiff = maxOf(maxDiff, diff)
            }
            return maxDiff
        }

        outer@for (line in input.lines()) {
            val numberList = line.split(" ").map { it.toInt() }

            inner@for (i in numberList.indices) {
                val subList = numberList.filterIndexed { index, _ -> index != i }

                if (!hasDuplicates(subList) && isStrictlyIncreasing(subList) || isStrictlyDecreasing(subList)) {
                    val localMaxDiff = getMaxDifference(subList)

                    if (localMaxDiff in 1..3) {
                        safeReports += 1
                        break@inner
                    }
                }
            }
        }

        return safeReports
    }

    // Or read a large test input from the `src/Day02_test.txt` file:
    val testInput = readText(DIRECTORY_NAME, "Day02_test")
    check(part1(testInput) == 2)
//    check(part2(testInput) == 4)

    // Read the input from the `src/Day02.txt` file.
    val input = readText(DIRECTORY_NAME, "Day02")
//    part1(input).println()
    part2(input).println()
}
