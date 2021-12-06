package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day06").map { line ->
    line.split(",").map { it.toInt() }
  }.flatten().groupBy { it }

  val groupFishiesByDayState = (0..8).toList().map {
    // count fishies by internal timer
    inputList.filter { pair -> pair.key == it }.map { it.value }.flatten().count()
  }.map { it.toULong() }

  Day06.part1(groupFishiesByDayState)
  Day06.part2(groupFishiesByDayState)
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
      if (it == 6) { // resetted fishies + new fishies with day state 7
        add(this@processDay[it + 1] + newFishies)
      } else {
        add(this@processDay[it + 1]) // move fishies on step closer to duplication
      }
    }
    this.add(8, newFishies) // add new fishies
  }
}