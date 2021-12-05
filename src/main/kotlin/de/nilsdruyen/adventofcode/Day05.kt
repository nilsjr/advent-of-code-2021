package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day05").map { line ->
    val (start, end) = line.split(" -> ")
    val (x, y) = start.split(",").map { it.toInt() }
    val (xEnd, yEnd) = end.split(",").map { it.toInt() }

    val xRange = Pair(x, xEnd).toRangeList()
    val yRange = Pair(y, yEnd).toRangeList()

    if (x == xEnd) {
      // horizontal line
      Line(yRange.map { Segment(x, it) })
    } else if (y == yEnd) {
      // vertical line
      Line(xRange.map { Segment(it, y) })
    } else {
      // diagonal line part2
      if (xRange.size == yRange.size) {
        val segments = xRange.mapIndexed { index, point ->
          Segment(point, yRange[index])
        }
        Line(segments, true)
      } else {
        Line(emptyList())
      }
    }
  }.filter { it.points.isNotEmpty() }

  Day05.part1(inputList)
  Day05.part2(inputList)
}

object Day05 {

  fun part1(inputList: List<Line>) {
    println(inputList.filter { !it.isDiagonal }.countOverlappingPoints())
  }

  fun part2(inputList: List<Line>) {
    println(inputList.countOverlappingPoints())
  }

  private fun List<Line>.countOverlappingPoints(): Int = map { it.points }
    .flatten()
    .groupingBy { it }
    .eachCount()
    .filter { it.value > 1 }.size
}

data class Line(val points: List<Segment>, val isDiagonal: Boolean = false)
data class Segment(val x: Int, val y: Int)

fun Pair<Int, Int>.toRangeList(): List<Int> {
  return if (first > second) (second..first).toList().reversed() else (first..second).toList()
}