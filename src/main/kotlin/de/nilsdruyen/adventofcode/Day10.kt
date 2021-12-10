package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day10").map { line ->
    line.map { it }
  }

  Day10.part1(inputList)
  Day10.part2(inputList)
}

object Day10 {

  fun part1(inputList: List<List<Char>>) {
    val invalidChunks = inputList.mapNotNull(List<Char>::findCorruption)

    val result = invalidChunks.groupBy { it }
      .map { entry -> entry.key.corruptionPoints * entry.value.size }
      .sum()

    println(result)
  }

  fun part2(inputList: List<String>) {
    TODO()
  }
}

fun List<Char>.findCorruption(): Chunk? {
  val startChunks = ArrayDeque<Chunk>()
  forEach { char ->
    when (char) {
      in chunks.map { it.startIdentifier } -> {
        startChunks.add(chunks.first { it.startIdentifier == char })
      }
      in chunks.map { it.endIdentfier } -> {
        val endChunk = chunks.first { it.endIdentfier == char }
        if (startChunks.last() == endChunk) startChunks.removeLast() else return endChunk
      }
    }
  }
  return null
}

fun List<Char>.incompleteLines(): ArrayDeque<Chunk>? {
  val startChunks = ArrayDeque<Chunk>()
  forEach { char ->
    when (char) {
      in chunks.map { it.startIdentifier } -> {
        startChunks.add(chunks.first { it.startIdentifier == char })
      }
      in chunks.map { it.endIdentfier } -> {
        val endChunk = chunks.first { it.endIdentfier == char }
        if (startChunks.last() == endChunk) startChunks.removeLast() else return null
      }
    }
  }
  return startChunks
}

sealed class Chunk(
  var startIdentifier: Char,
  var endIdentfier: Char,
  val corruptionPoints: Int,
  val incompletePoints: Int
) {

  object Parenthese : Chunk('(', ')', 3, 1)
  object Bracket : Chunk('[', ']', 57, 2)
  object Brace : Chunk('{', '}', 1197, 3)
  object GreaterOrSmaller : Chunk('<', '>', 25137, 4)
}

val chunks = listOf(Chunk.Parenthese, Chunk.Bracket, Chunk.Brace, Chunk.GreaterOrSmaller)

