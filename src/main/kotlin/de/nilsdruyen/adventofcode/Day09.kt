package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day09").map { line ->
    line.map { it.toString().toInt() }
  }.toLocations()

  Day09.part1(inputList)
  Day09.part2(inputList)
}

object Day09 {

  fun part1(inputList: List<Location>) {
    val lowestPoints = inputList.filterLowestPoints()
    println(lowestPoints.sumOf { it.number + 1 })
  }

  fun part2(inputList: List<Location>) {
    val lowestPoints = inputList.filterLowestPoints()
    val result = lowestPoints.findBasins(inputList)
      .map { it.size }
      .sortedDescending()
      .take(3)
      .reduce { a, b -> a * b }

    println(result)
  }
}

data class Location(val number: Int, val colIndex: Int, val rowIndex: Int)
data class Basin(val lowest: Location, val size: Int)

fun Location.adjacentLocations(inputList: List<Location>): List<Location> {
  val width = inputList.maxOf { it.colIndex } + 1
  val indices = 0 until width
  return listOf(
    Pair(colIndex - 1, rowIndex),
    Pair(colIndex, rowIndex + 1),
    Pair(colIndex + 1, rowIndex),
    Pair(colIndex, rowIndex - 1),
  ).filter { (col, row) -> col in indices && row in indices }
    .mapNotNull { inputList.getOrNull(it.first + it.second * width) }
}

fun List<List<Int>>.toLocations(): List<Location> = mapIndexed { rowIndex, colList ->
  colList.mapIndexed { colIndex, number -> Location(number, colIndex, rowIndex) }
}.flatten()

fun List<Location>.filterLowestPoints(): List<Location> = filter { location ->
  location.adjacentLocations(this).all { it.number > location.number }
}

fun List<Location>.findBasins(inputList: List<Location>): List<Basin> = map { position ->
  val basinLocations = position.findNeighboursForBasin(inputList)
  val uniqueBasinLocations = basinLocations.distinct()
  Basin(position, uniqueBasinLocations.size)
}

fun Location.findNeighboursForBasin(inputList: List<Location>): List<Location> {
  val neighbours = this.adjacentLocations(inputList)
  val validNeighbours = neighbours.filter { it.number < 9 }.filter { it.number > number }
  return if (validNeighbours.isNotEmpty()) {
    validNeighbours.map { it.findNeighboursForBasin(inputList) }.flatten()
  } else {
    emptyList()
  } + this
}