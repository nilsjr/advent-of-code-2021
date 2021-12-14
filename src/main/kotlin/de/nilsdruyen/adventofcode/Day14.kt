package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day14test")
  val template = inputList.first().windowed(2, 1)
  val insertions = inputList.drop(2).map {
    val (from, to) = it.split(" -> ")
    from to to
  }.toMap()

  Day14.part1(template, insertions)
  Day14.part2(template, insertions)
}

object Day14 {

  fun part1(inputList: List<String>, insertions: Map<String, String>) {
    val charCount = inputList.buildAndCount(insertions, 10)
    println(charCount.maxOf { it.value } - charCount.minOf { it.value })
  }

  fun part2(inputList: List<String>, insertions: Map<String, String>) {
    val charCount = inputList.buildAndCount(insertions, 20)
    println(charCount.maxOf { it.value } - charCount.minOf { it.value })
  }
}

fun List<String>.buildAndCount(insertions: Map<String, String>, times: Int): Map<String, Int> {
  val charCount = mutableMapOf<String, Int>()

  forEach { part ->
    println(part)
    var chunk = part.map { it.toString() }
    repeat(times) {
      chunk = buildList {
        chunk.windowed(2, 1).forEach { (first, second) ->
          val inBetween = insertions.getOrDefault("$first$second", "")
          if (isNotEmpty() && last() == first) {
            add(inBetween)
            add(second)
          } else {
            add(first)
            add(inBetween)
            add(second)
          }
        }
      }
      println(it)
    }
    chunk.countChars().forEach { (t, u) ->
      charCount[t] = u + (charCount[t] ?: 0)
    }
  }
  return charCount
}

fun List<String>.countChars(): Map<String, Int> = joinToString("").groupingBy { it.toString() }.eachCount()

