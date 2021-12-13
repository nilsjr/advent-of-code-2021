package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day11").mapIndexed { rowIndex, line ->
    line.map { it.toString().toInt() }.mapIndexed { colIndex, level ->
      Octopus(rowIndex, colIndex, level)
    }
  }.flatten()

  Day11.part1(inputList)
  Day11.part2(inputList)
}

data class Octopus(val rowIndex: Int, val colIndex: Int, var energyLevel: Int)

object Day11 {

  fun part1(inputList: List<Octopus>) {
    val flashes = mutableListOf<Int>()
    repeat(100) {
      inputList.forEach { it.energyLevel++ }

      var flashCount = 0
      while (inputList.any { it.energyLevel >= 10 }) {
        val flashies = inputList.filter { it.energyLevel >= 10 }
        flashCount += flashies.size

        flashies.forEach { octopus ->
          octopus.energyLevel = 0
          octopus
            .neighbours()
            .mapNotNull { inputList.getOrNull(it) }
            .filter { it.energyLevel != 0 }
            .forEach { it.energyLevel++ }
        }
      }
      flashes.add(flashCount)
    }

    println(flashes.sum())
  }

  fun part2(inputList: List<Octopus>) {
    val flashes = mutableListOf<Int>()
    while (flashes.none { it == 100 }) {
      inputList.forEach { it.energyLevel++ }

      var flashCount = 0
      while (inputList.any { it.energyLevel >= 10 }) {
        val flashies = inputList.filter { it.energyLevel >= 10 }
        flashCount += flashies.size

        flashies.forEach { octopus ->
          octopus.energyLevel = 0
          octopus
            .neighbours()
            .mapNotNull { inputList.getOrNull(it) }
            .filter { it.energyLevel != 0 }
            .forEach { it.energyLevel++ }
        }
      }
      flashes.add(flashCount)
    }

    println(flashes.indexOf(100) + 101) // + 1 (index) + 100 (part 1 steps)
  }
}

fun Octopus.neighbours(): List<Int> {
  val width = 10
  return listOf(
    // top neighbours
    Pair(colIndex - 1, rowIndex - 1),
    Pair(colIndex, rowIndex - 1),
    Pair(colIndex + 1, rowIndex - 1),
    // bottom neighbours
    Pair(colIndex - 1, rowIndex + 1),
    Pair(colIndex, rowIndex + 1),
    Pair(colIndex + 1, rowIndex + 1),
    // left right
    Pair(colIndex - 1, rowIndex),
    Pair(colIndex + 1, rowIndex),
  )
    .filter { it.first in 0..9 && it.second in 0..9 }
    .map { it.first + it.second * width }
}