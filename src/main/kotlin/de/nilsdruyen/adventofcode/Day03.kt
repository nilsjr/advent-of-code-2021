package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day03").map { line ->
    line.map(Char::digitToInt)
  }

  Day03.part1(inputList)
  Day03.part2(inputList)
}

object Day03 {

  fun part1(inputList: List<List<Int>>) {
    val digits = inputList.first().indices
    val binaryResult = mutableListOf<Int>()

    for (i in digits) {
      binaryResult.add(inputList.mostCommonBit(i))
    }

    val gammaRate = binaryResult.joinToString(separator = "").toInt(2)
    val epsilonRate = binaryResult.flip().joinToString(separator = "").toInt(2)

    println("part 1 result=${gammaRate * epsilonRate}")
  }

  fun part2(inputList: List<List<Int>>) {
    val oxygenRating = findRating(inputList, List<List<Int>>::mostCommonBit)
    val scrubberRating = findRating(inputList, List<List<Int>>::leastCommonBit)
    println("part 2 result=${oxygenRating * scrubberRating}")
  }

  private fun findRating(list: List<List<Int>>, bitSelector: (list: List<List<Int>>, index: Int) -> Int): Int {
    val digits = list.first().size - 1
    var filteredList = list
    var index = 0

    while (filteredList.size > 1 && index <= digits) {
      val bit = bitSelector(filteredList, index)
      filteredList = filteredList.filter { it[index] == bit }
      index++
    }

    return filteredList.first().joinToString(separator = "").toInt(2)
  }
}

fun List<List<Int>>.mostCommonBit(index: Int): Int {
  val (zeros, ones) = countBits(index)
  return if (zeros > ones) 0 else 1
}

fun List<List<Int>>.leastCommonBit(index: Int): Int {
  val (zeros, ones) = countBits(index)
  return if (zeros > ones) 1 else 0
}

private fun List<List<Int>>.countBits(index: Int): Pair<Int, Int> =
  Pair(count { it[index] == 0 }, count { it[index] == 1 })

private fun List<Int>.flip(): List<Int> = this.map { if (it == 0) 1 else 0 }