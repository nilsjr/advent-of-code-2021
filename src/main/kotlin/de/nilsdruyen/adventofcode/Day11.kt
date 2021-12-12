package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day11test").mapIndexed { rowIndex, line ->
    line.map { it.toString().toInt() }.mapIndexed { colIndex, level ->
      Octupus(colIndex, rowIndex, level)
    }
  }

  Day11.part1(inputList)
//  Day11.part2(inputList)
}

object Day11 {

  fun part1(inputList: List<List<Octupus>>) {
    repeat(2) {
      inputList.forEach { row -> row.forEach { it.increaseEnergyLevel() } }

      while (inputList.any { row -> row.any { it.canFlash() } }) {
//        println(inputList.count { it.canFlash() })
//        println("flash")
        inputList.print()
        println()
        inputList.forEach { row -> row.forEach { it.flash(inputList) } }
      }

      inputList.forEach { row -> row.forEach { it.reset() } }
    }

//    val matrix = inputList.windowed(10, 10)
//    println(matrix[0].joinToString("") { it.energyLevel.toString() } == "8807476555")
//    println(matrix[1].joinToString("") { it.energyLevel.toString() } == "5089087054")
//    println(inputList.sumOf { it.flashCount })
  }

  fun part2(inputList: List<Octupus>) {
    TODO()
  }
}

data class Octupus(
  val x: Int,
  val y: Int,
  var energyLevel: Int,
  var flashed: Boolean = false,
  var flashCount: Int = 0
) {

  fun increaseEnergyLevel() {
    if (energyLevel <= 9) energyLevel++
  }

  fun canFlash(): Boolean = energyLevel > 9 && !flashed

//  fun flash(inputList: List<Octupus>) {
//    if (energyLevel > 9 && !flashed) {
//      flashed = true
//      flashCount++
//      this.neighboursFrom(inputList).forEach { it.increaseEnergyLevel() }
//    }
//  }

  fun flash(inputList: List<List<Octupus>>) {
    if (energyLevel > 9 && !flashed) {
      flashed = true
      flashCount++
      this.neighboursFrom(inputList).forEach { it.increaseEnergyLevel() }
    }
  }

  fun reset() {
    if (energyLevel > 9) {
      energyLevel = 0
      flashed = false
    }
  }
}

//fun List<Octupus>.print() {
//  val width = maxOf { it.y } + 1
//  windowed(width, width).forEach { list ->
//    println(list.joinToString("") {
//      if (it.energyLevel >= 10) "X" else it.energyLevel.toString()
//    })
//  }
//}

fun List<List<Octupus>>.print() {
  forEach { row ->
    println(row.joinToString("") {
      if (it.energyLevel >= 10) "X" else it.energyLevel.toString()
    })
  }
}

//fun Octupus.neighboursFrom(list: List<Octupus>): List<Octupus> {
//  val width = list.maxOf { it.y } + 1
//  return listOfNotNull(
//    // top neighbours
//    list.getOrNull((y - 1) + (x - 1) * width),
//    list.getOrNull(y + (x - 1) * width),
//    list.getOrNull((y + 1) + (x - 1) * width),
//    // bottom neighbours
//    list.getOrNull((y - 1) + (x + 1) * width),
//    list.getOrNull(y + (x + 1) * width),
//    list.getOrNull((y + 1) + (x + 1) * width),
//    // left right
//    list.getOrNull((y - 1) + x * width),
//    list.getOrNull((y + 1) + x * width),
//  ).filter { !it.canFlash() }
//}

fun Octupus.neighboursFrom(list: List<List<Octupus>>): List<Octupus> {
  return listOfNotNull(
    list.getOrNull(x - 1)?.getOrNull(y - 1),
    list.getOrNull(x - 1)?.getOrNull(y),
    list.getOrNull(x - 1)?.getOrNull(y + 1),
    list.getOrNull(x + 1)?.getOrNull(y - 1),
    list.getOrNull(x + 1)?.getOrNull(y),
    list.getOrNull(x + 1)?.getOrNull(y + 1),
    list.getOrNull(x)?.getOrNull(y - 1),
    list.getOrNull(x)?.getOrNull(y + 1),
  ).filter { it.energyLevel <= 9 }.also { println("$x $y $energyLevel neighbours: ${it.size}") }
}