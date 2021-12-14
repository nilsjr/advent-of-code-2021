package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput
import kotlin.math.roundToLong

fun main() {
  val inputList = readInput("Day14")

  val startTemplate = inputList.first().windowed(2)
  val ruleList = inputList.drop(2).associate {
    val (from, to) = it.split(" -> ")
    from to listOf("${from[0]}$to", "$to${from[1]}")
  }

  Day14.part1(startTemplate, ruleList)
  Day14.part2(startTemplate, ruleList)
}

object Day14 {

  fun part1(startTemplate: List<String>, ruleList: Map<String, List<String>>) {
    println(buildAndCount(startTemplate, ruleList, 10))
  }

  fun part2(startTemplate: List<String>, ruleList: Map<String, List<String>>) {
    println(buildAndCount(startTemplate, ruleList, 40))
  }
}

fun buildAndCount(startTemplate: List<String>, ruleList: Map<String, List<String>>, times: Int): Long {
  var polymer = startTemplate.groupBy { it }.mapValues { it.value.size.toLong() }

  repeat(times) {
    polymer = polymer
      .flatMap { (str, count) -> ruleList.getValue(str).map { it to count } }
      .groupBy { it.first }
      .mapValues { it.value.sumOf { it.second } }
  }

  val countList = polymer
    .flatMap { (chunk, count) -> listOf(chunk[0] to count, chunk[1] to count) }
    .groupBy { it.first }
    .map { char -> (char.value.sumOf { it.second } / 2.0).roundToLong() }
    .sorted()

  return countList.last() - countList.first()
}