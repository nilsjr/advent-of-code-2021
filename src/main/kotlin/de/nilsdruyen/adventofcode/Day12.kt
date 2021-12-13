package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day12").map { line ->
    val (from, to) = line.split("-")
    from to to
  }

  Day12.part1(inputList)
//  Day12.part2(inputList)
}

const val START = "start"
const val END = "end"


object Day12 {

  fun part1(inputList: List<Pair<String, String>>) {
    val result = inputList.findPaths()
    println(result.size)
  }

  fun part2(inputList: List<String>) {
    TODO()
  }
}

fun List<Pair<String, String>>.findPaths(): List<String> {
  return map { listOf(it.first, it.second) }
    .flatten()
    .distinct()
    .associateWith { point ->
      mapNotNull { if (it.first == point) it.second else if (it.second == point) it.first else null }
    }
    .combine()
    .filter { it.isNotEmpty() }
    .map { it.joinToString(",") }
    .distinct()
}

fun Map<String, List<String>>.combine(): List<List<String>> {
  return getOrDefault(START, emptyList()).map { listOf(START, it).combine(this) }.flatten()
}

fun List<String>.combine(inputMap: Map<String, List<String>>): List<List<String>> {
  val availablePoints = inputMap.getOrDefault(last(), emptyList())
  val visitedSmallCaves = this.filter { it.toCharArray().all { char -> char.isLowerCase() } }
  return availablePoints.filter { it !in visitedSmallCaves && it != START }.map {
    val path = this + it
    if (it == END) listOf(path) else path.combine(inputMap)
  }.flatten()
}