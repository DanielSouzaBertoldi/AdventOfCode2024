package day04

import println
import readInput

private const val DIRECTORY_NAME = "day04"
private val expectedCharOrder = listOf('M', 'A', 'S')

fun main() {

    fun part1(lines: List<String>): Int {
        var found = 0

        fun checkHorizontalForward(line: String, idx: Int) {
            var checkingIdx = idx
            for (expectedChar in expectedCharOrder) {
                checkingIdx += 1
                if (line.getOrNull(checkingIdx) == null) return
                if (line[checkingIdx] != expectedChar) return
            }
            found++
        }

        fun checkHorizontalBackwards(line: String, idx: Int) {
            var checkingIdx = idx
            for (expectedChar in expectedCharOrder) {
                checkingIdx -= 1
                if (line.getOrNull(checkingIdx) == null) return
                if (line[checkingIdx] != expectedChar) return
            }
            found++
        }

        fun checkVerticalDown(lines: List<String>, idx: Int, outerIdx: Int) {
            var checkingIdx = outerIdx
            for (expectedChar in expectedCharOrder) {
                checkingIdx += 1
                if (lines.getOrNull(checkingIdx) == null) return
                if (lines[checkingIdx][idx] != expectedChar) return
            }
            found++
        }

        fun checkVerticalUp(lines: List<String>, idx: Int, outerIdx: Int) {
            var checkingIdx = outerIdx
            for (expectedChar in expectedCharOrder) {
                checkingIdx -= 1
                if (lines.getOrNull(checkingIdx) == null) return
                if (lines[checkingIdx][idx] != expectedChar) return
            }
            found++
        }

        fun checkDiagonal(lines: List<String>, xIdx: Int, yIdx: Int, dx: Int, dy: Int): Boolean {
            var xIdxCheck = xIdx
            var yIdxCheck = yIdx
            for (expectedChar in expectedCharOrder) {
                xIdxCheck += dx
                yIdxCheck += dy

                if (lines.getOrNull(yIdxCheck)?.getOrNull(xIdxCheck) != expectedChar) {
                    return false
                }
            }
            return true
        }

        fun checkDiagonals(lines: List<String>, xIdx: Int, yIdx: Int) {
            val directions = listOf(
                1 to -1, // up-right
                -1 to -1, // up-left
                1 to 1, // down-right
                -1 to 1, //down-left
            )

            for ((dx, dy) in directions) {
                if (checkDiagonal(lines, xIdx, yIdx, dx, dy)) found++
            }
        }

        lines.forEachIndexed { linesIdx, line ->
            for ((lineIdx, char) in line.withIndex()) {
                if (char == 'X') {
                    checkHorizontalForward(line, lineIdx)
                    checkHorizontalBackwards(line, lineIdx)
                    checkVerticalDown(lines, lineIdx, linesIdx)
                    checkVerticalUp(lines, lineIdx, linesIdx)
                    checkDiagonals(lines, lineIdx, linesIdx)
                }
            }
        }
        return found
    }

    fun part2(lines: List<String>): Int {
        fun checkDiagonals(lines: List<String>, initPosition: Pair<Int, Int>): Boolean {
            if (
                lines.getOrNull(initPosition.first - 1)?.getOrNull(initPosition.second + 1) != null &&
                lines.getOrNull(initPosition.first - 1)?.getOrNull(initPosition.second - 1) != null &&
                lines.getOrNull(initPosition.first + 1)?.getOrNull(initPosition.second + 1) != null &&
                lines.getOrNull(initPosition.first + 1)?.getOrNull(initPosition.second - 1) != null
            ) {
                if (lines[initPosition.first - 1][initPosition.second + 1] == 'S') {
                    if (lines[initPosition.first + 1][initPosition.second - 1] != 'M') return false
                } else if (lines[initPosition.first - 1][initPosition.second + 1] == 'M') {
                    if (lines[initPosition.first + 1][initPosition.second - 1] != 'S') return false
                } else {
                    return false
                }

                // up-left verify then check down-right
                if (lines[initPosition.first - 1][initPosition.second - 1] == 'S') {
                    if (lines[initPosition.first + 1][initPosition.second + 1] != 'M') return false
                } else if (lines[initPosition.first - 1][initPosition.second - 1] == 'M') {
                    if (lines[initPosition.first + 1][initPosition.second + 1] != 'S') return false
                } else {
                    return false
                }

                return true
            }
            return false
        }

        var found = 0
        lines.forEachIndexed { y, line ->
            for ((x, char) in line.withIndex()) {
                if (char == 'A') {
                    if(checkDiagonals(lines, y to x)) found++
                }
            }
        }
        return found
    }

    // Or read a large test input from the `src/Day04_test.txt` file:
    val testInput = readInput(DIRECTORY_NAME, "Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day04.txt` file.
    val input = readInput(DIRECTORY_NAME, "Day04")
    part1(input).println()
    part2(input).println()
}
