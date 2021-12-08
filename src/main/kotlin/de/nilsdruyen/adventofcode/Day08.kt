package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

//  listOf("a,b,c,e,f,g"), // 0 - 6
//  listOf("c,f"), // 1 - 2*
//  listOf("a,c,d,e,g"), // 2 - 5
//  listOf("a,c,d,f,g"), // 3 - 5
//  listOf("b,c,d,f"), // 4 - 4*
//  listOf("a,b,d,f,g"), // 5 - 5
//  listOf("a,b,d,e,f,g"), // 6 - 6
//  listOf("a,c,f"), // 7 - 3*
//  listOf("a,b,c,d,e,f,g"), // 8 - 7*
//  listOf("a,b,c,d,f,g"), // 9 - 6

fun main() {
  val inputList = readInput("Day08").map { line ->
    val (segments, final) = line.split(" | ")
    DisplayGroup(segments.toGroup(), final.toGroup())
  }

  Day08.part1(inputList)
  Day08.part2(inputList)
}

object Day08 {

  fun part1(inputList: List<DisplayGroup>) {
    val result = inputList.map { group ->
      val uniqueDigits = (0..7).toList().map {
        it to group.segments.count { s -> s.activeFields.length == it }
      }.filter { it.second == 1 }.map { it.first }
      group.result.filter { it.size in uniqueDigits }.map(DisplaySegment::activeFields)
    }.sumOf { it.size }
    println("result: $result")
  }

  fun part2(inputList: List<DisplayGroup>) {
    TODO()
  }
}

fun String.toGroup(): List<DisplaySegment> = split(" ").map(::DisplaySegment)

data class DisplayGroup(val segments: List<DisplaySegment>, val result: List<DisplaySegment>)

data class DisplaySegment(val activeFields: String) {

  val size = activeFields.length
}

