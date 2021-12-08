package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

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
        it to group.segments.count { s -> s.size == it }
      }.filter { it.second == 1 }.map { it.first }
      group.result.filter { it.size in uniqueDigits }.map(DisplaySegment::signals)
    }.sumOf { it.size }
    println("result: $result")
  }

  fun part2(inputList: List<DisplayGroup>) {
    println("result: ${inputList.sumOf { it.decode(it.findSignalPattern()) }}")
  }
}

fun String.toGroup(): List<DisplaySegment> = split(" ").map {
  DisplaySegment(it.map { char -> char.toString() })
}

data class DisplaySegment(val signals: List<String>) {

  val size = signals.size
}

data class DisplayGroup(val segments: List<DisplaySegment>, val result: List<DisplaySegment>) {

  fun findSignalPattern(): List<String> {
    val rep = MutableList(7) { "" }
    segments.sortedBy { it.signals.size }.groupBy { it.signals.size }.forEach { (size, segments) ->
      when (size) {
        2 -> { // Number 1
          val chars = segments.first().signals
          rep[2] = chars[0]
          rep[5] = chars[1]
        }
        3 -> { // Number 7
          val chars = segments.first().signals
          rep[0] = chars.firstOrNull { it !in rep } ?: ""
        }
        4 -> { // Number 4
          val chars = segments.first().signals
          val leftChars = chars.filter { it !in rep }
          rep[1] = leftChars[0]
          rep[3] = leftChars[1]
        }
        5 -> { // Numbers 2 3 5
          val parts = segments.map { segment ->
            segment to segment.signals.filter { it !in rep }
          }.sortedBy { it.second.size }

          val one = listOf(rep[2], rep[5])
          val three = parts.first { part -> one.all { it in part.first.signals } }

          rep[6] = parts[0].second[0]

          try {
            assert(three.first.signals.sorted().toNumber(rep) != 3)
          } catch (e: IllegalStateException) {
            val flip1 = rep[1]
            val flip2 = rep[3]
            rep[1] = flip2
            rep[3] = flip1
          }

          rep[4] = parts.filter { it.second.size > 1 }[0].second.first { it != rep[6] }
        }
        6 -> { // Numbers 0 6 9
          val six = segments.filter { it.signals.contains(rep[4]) }
            .filter { it.signals.contains(rep[3]) }
          try {
            assert(six.first().signals.sorted().toNumber(rep) != 6)
          } catch (e: IllegalStateException) {
            val flip1 = rep[2]
            val flip2 = rep[5]
            rep[2] = flip2
            rep[5] = flip1
          }

          assert(segments.count { !it.signals.contains(rep[4]) } == 1) // check if 0 doesn't contain center line digit
          assert(segments.count { !it.signals.contains(rep[3]) } == 1) // six
          assert(segments.count { !it.signals.contains(rep[4]) } == 1) // nine
        }
        7 -> { // Numbers 8
          assert(rep.sorted() == segments.first().signals.sorted())
        }
      }
    }
    return rep
  }

  fun decode(signalPattern: List<String>): Int =
    result.map {
      it.signals.sorted().toNumber(signalPattern)
    }.joinToString("").toInt()
}

fun List<String>.toNumber(signalPattern: List<String>): Int = when (size) {
  2 -> 1
  3 -> 7
  4 -> 4
  5 -> when {
    this == signalPattern.two() -> 2
    this == signalPattern.three() -> 3
    this == signalPattern.five() -> 5
    else -> error("no pattern found for: ${this.joinToString("")}")
  }
  6 -> when {
    this == signalPattern.zero() -> 0
    this == signalPattern.six() -> 6
    this == signalPattern.nine() -> 9
    else -> error("no pattern found for: ${this.joinToString("")}")
  }
  7 -> 8
  else -> error("no pattern found for: ${this.joinToString("")}")
}

fun List<String>.two(): List<String> = toMutableList().apply {
  removeAt(5)
  removeAt(1)
}.sorted()

fun List<String>.three(): List<String> = toMutableList().apply {
  removeAt(4)
  removeAt(1)
}.sorted()

fun List<String>.five(): List<String> = toMutableList().apply {
  removeAt(4)
  removeAt(2)
}.sorted()

fun List<String>.zero(): List<String> = toMutableList().apply { removeAt(3) }.sorted()
fun List<String>.six(): List<String> = toMutableList().apply { removeAt(2) }.sorted()
fun List<String>.nine(): List<String> = toMutableList().apply { removeAt(4) }.sorted()