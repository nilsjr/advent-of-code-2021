package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput
import kotlin.math.abs

fun main() {
  val inputList = readInput("Day07").map { line ->
    line.split(",")
  }.flatten().map { it.toInt() }

  Day07.part1(inputList)
  Day07.part2(inputList)
}

object Day07 {

  fun part1(inputList: List<Int>) {
    val fuelConsumption = inputList.minFuelConsumption { position, targetPosition ->
      abs(position - targetPosition)
    }
    println("result: $fuelConsumption")
  }

  fun part2(inputList: List<Int>) {
    val minFuelConsumption = inputList.minFuelConsumption { position, targetPosition ->
      val diff = abs(position - targetPosition)
      var fuelConsumption = 0
      repeat(diff) { step -> fuelConsumption += step + 1 }
      fuelConsumption
    }
    println("result: $minFuelConsumption")
  }
}

fun List<Int>.minFuelConsumption(computation: (position: Int, targetPosition: Int) -> Int): Int {
  val maxPosition = maxOrNull() ?: 0
  val minPosition = minOrNull() ?: 1
  val possiblePositions = (minPosition..maxPosition).toList()
  return possiblePositions.minOfOrNull { targetPosition ->
    // calculate fuel consumption for each possible position
    sumOf { computation(it, targetPosition) }
  } ?: 0
}