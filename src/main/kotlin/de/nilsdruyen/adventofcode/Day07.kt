package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput
import kotlin.math.abs
import kotlin.math.roundToInt

fun main() {
  val inputList = readInput("Day07").map { line ->
    line.split(",")
  }.flatten().map { it.toInt() }

  Day07.part1(inputList)
  Day07.part2(inputList)
}

object Day07 {

  fun part1(inputList: List<Int>) {
    val median = medianOf(inputList)
    val fuelConsumption = inputList.sumOf { abs(it - median) }
    println("result: $fuelConsumption")
  }

  fun part2(inputList: List<Int>) {
    TODO()
  }
}

private fun medianOf(numbers: List<Int>): Int {
  val list = numbers.sorted()
  return if (list.size % 2 == 0) {
    ((list[list.size / 2] + list[list.size / 2 - 1]) / 2).toDouble()
  } else {
    (list[list.size / 2]).toDouble()
  }.roundToInt()
}
