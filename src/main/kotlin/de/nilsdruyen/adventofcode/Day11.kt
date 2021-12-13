package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day11test").mapIndexed { rowIndex, line ->
    line.map { it.toString().toInt() }.mapIndexed { colIndex, level ->
      Octupus(colIndex, rowIndex, level)
    }
  }.flatten()

  Day11.part1(inputList)
//  Day11.part2(inputList)
}

object Day11 {

  fun part1(inputList: List<Octupus>) {
    var flashes = 0
    repeat(2) {
      inputList.forEach { it.energyLevel++ }

      while (inputList.any { it.energyLevel >= 10 }) {
        val flashies = inputList.filter { it.energyLevel >= 10 }
        flashes += flashies.size

        flashies.forEach { octupus ->
          octupus.energyLevel = 0
          octupus
            .neighbours(inputList)
            .mapNotNull { inputList.getOrNull(it) }
            .filter { it.energyLevel != 0 }
            .forEach { it.energyLevel++ }
        }
      }

      inputList.print()
      println()
    }

    val matrix = inputList.windowed(10, 10)
    println(matrix[0].joinToString("") { it.energyLevel.toString() } == "8807476555")
    println(matrix[1].joinToString("") { it.energyLevel.toString() } == "5089087054")

    println(flashes)
  }

  fun part2(inputList: List<Octupus>) {
    TODO()
  }
}

data class Octupus(
  val x: Int,
  val y: Int,
  var energyLevel: Int,
)

fun List<List<Octupus>>.forEachOctupus(block: (Octupus) -> Unit) {
  forEach { row -> row.forEach { block(it) } }
}

fun List<List<Octupus>>.filterOctupus(block: (Octupus) -> Boolean) {
  filter { row -> row.all { block(it) } }
}

fun List<Octupus>.print() {
  val width = maxOf { it.y } + 1
  windowed(width, width).take(3).forEach { list ->
    println(list.joinToString("") {
      if (it.energyLevel >= 10) "X" else it.energyLevel.toString()
    })
  }
}

fun Octupus.neighbours(list: List<Octupus>): List<Int> {
  val width = list.maxOf { it.y } + 1
  return listOf(
    // top neighbours
    (y - 1) + (x - 1) * width,
    y + (x - 1) * width,
    (y + 1) + (x - 1) * width,
    // bottom neighbours
    (y - 1) + (x + 1) * width,
    y + (x + 1) * width,
    (y + 1) + (x + 1) * width,
    // left right
    (y - 1) + x * width,
    (y + 1) + x * width,
  )
}