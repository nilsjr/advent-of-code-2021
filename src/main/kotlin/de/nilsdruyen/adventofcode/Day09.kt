package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day09test").map { line ->
    line.map { it.toString().toInt() }
  }

  Day09.part1(inputList)
  Day09.part2(inputList)

  // 457164
}

object Day09 {

  fun part1(inputList: List<List<Int>>) {
    val projection = inputList.getPositionsForComparison()
    val numbers = projection.findLowestPoints(inputList)
    println(numbers.sumOf { it.number + 1 })
  }

  fun part2(inputList: List<List<Int>>) {
    val projection = inputList.getPositionsForComparison()
    val numbers = projection.findLowestPoints(inputList)
    val result = numbers.findBasins(inputList)
      .map { it.size }
      .sortedDescending()
      .take(3)
      .also {
        println(it)
      }
      .reduce { a, b -> a * b }

    println(result)
  }
}

data class SmokePosition(val number: Int, val x: Int, val y: Int)

data class PositionComparison(
  val position: SmokePosition,
  val comparison: List<Pair<Int, Int>>
)

fun List<PositionComparison>.findLowestPoints(inputList: List<List<Int>>): List<SmokePosition> {
  return this.filter { position ->
    val number = position.position.number
    val minNeighbour = position.comparison.minOfOrNull { inputList[it.first][it.second] } ?: -1
    number < minNeighbour
  }.map { it.position }
}

fun List<List<Int>>.getPositionsForComparison(): List<PositionComparison> {
  return this.mapIndexed { rowIndex, colList ->
    return@mapIndexed colList.mapIndexed { colIndex, number ->
      val positions = listOfPossiblePositions(rowIndex, colIndex, indices, colList.indices)
      PositionComparison(SmokePosition(number, rowIndex, colIndex), positions)
    }
  }.flatten()
}

data class Basin(val lowest: SmokePosition, val size: Int)

typealias Location = Pair<Int, Int>

fun List<SmokePosition>.findBasins(inputList: List<List<Int>>): List<Basin> = map { position ->
  Basin(position, position.findNeighboursForBasin(inputList).distinct().size)
}

fun SmokePosition.findNeighboursForBasin(inputList: List<List<Int>>): List<Location> {
  val positions = listOfPossiblePositions(x, y, inputList.indices, inputList.first().indices)
  val neighbours = positions.map { (x, y) -> SmokePosition(inputList[x][y], x, y) }
  val validNeighbours = neighbours.filter { it.number < 9 }.filter { number + 1 == it.number }
  return if (validNeighbours.isNotEmpty()) {
    validNeighbours.map { it.findNeighboursForBasin(inputList) }.flatten() + Location(x, y)
  } else listOf(Location(x, y))
}

fun listOfPossiblePositions(x: Int, y: Int, rowIndices: IntRange, colIndices: IntRange): List<Location> = listOf(
  Pair(x - 1, y), Pair(x, y + 1), Pair(x + 1, y), Pair(x, y - 1),
).filter { (x, y) -> x in rowIndices && y in colIndices }