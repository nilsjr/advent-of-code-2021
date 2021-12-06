package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day06").map { line ->
    line.split(",").map { it.toInt() }
  }.flatten().groupBy { it }

  val groupFishCountByDayState = (0..8).toList().map {
    // count fishies by internal timer
    inputList.filter { pair -> pair.key == it }.map { it.value }.flatten().count()
  }.map { it.toULong() }

  Day06.part1(groupFishCountByDayState)
  Day06.part2(groupFishCountByDayState)
}

object Day06 {

  fun part1(inputList: List<ULong>) {
    var resultList = inputList.toList()

    repeat(80) { resultList = resultList.processDay() }

    println("result: ${resultList.sumOf { it }}")
  }

  fun part2(inputList: List<ULong>) {
    var resultList = inputList.toList()

    repeat(256) { resultList = resultList.processDay() }

    println("result: ${resultList.sumOf { it }}")
  }
}

fun List<ULong>.processDay(): List<ULong> {
  val newFishies = this[0]

  return mutableListOf<ULong>().apply {
    repeat(8) {
      if (it == 6) {
        add(this@processDay[it + 1] + newFishies)
      } else {
        add(this@processDay[it + 1])
      }
    }
    this.add(8, newFishies)
  }
}