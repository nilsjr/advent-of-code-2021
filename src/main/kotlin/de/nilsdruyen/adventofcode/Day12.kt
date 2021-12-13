package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day12").map { line ->
    val (from, to) = line.split("-")
    from to to
  }

  Day12.part1(inputList)
  Day12.part2(inputList)
}

const val START = "start"
const val END = "end"

object Day12 {

  fun part1(inputList: List<Pair<String, String>>) {
    val result = inputList.findPaths(false)
    println(result.size)
  }

  fun part2(inputList: List<Pair<String, String>>) {
    val result = inputList.findPaths(true)
    println(result.size)
  }
}

fun List<Pair<String, String>>.findPaths(part2: Boolean): List<String> {
  return map { listOf(it.first, it.second) }
    .flatten()
    .distinct()
    .associateWith { point ->
      mapNotNull { if (it.first == point) it.second else if (it.second == point) it.first else null }
    }
    .combine(part2)
    .filter { it.isNotEmpty() }
    .map { it.joinToString(",") }
    .distinct()
}

fun Map<String, List<String>>.combine(part2: Boolean): List<List<String>> {
  return getOrDefault(START, emptyList()).map { listOf(START, it).combine(this, part2) }.flatten()
}

fun List<String>.combine(inputMap: Map<String, List<String>>, part2: Boolean): List<List<String>> {
  val availablePoints = inputMap.getOrDefault(last(), emptyList())
  val visitedSmallCaves = filterLowerCase()
  val possibleCaves = if (part2) {
    val visitedTwice = this.filterLowerCase().map { lowercaseString ->
      lowercaseString to count { it == lowercaseString }
    }.firstOrNull { it.second == 2 }?.first ?: ""

    if (visitedTwice.isNotEmpty()) {
      availablePoints.filter { it !in visitedSmallCaves && it != visitedTwice }
    } else {
      availablePoints
    }
  } else {
    availablePoints.filter { it !in visitedSmallCaves }
  }

  return possibleCaves.filter { it != START }.map {
    val path = this + it
    if (it == END) listOf(path) else path.combine(inputMap, part2)
  }.flatten()
}

fun String.isLowerCase(): Boolean = toCharArray().all(Char::isLowerCase)

fun List<String>.filterLowerCase(): List<String> = filter(String::isLowerCase)