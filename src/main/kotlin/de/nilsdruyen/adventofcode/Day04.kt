package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day04").map { line ->
    if (line.isNotBlank()) {
      if (line.contains(",")) {
        line.split(",")
      } else {
        line.windowed(size = 2, step = 3)
      }.map { it.trim().toInt() }
    } else emptyList()
  }.filter { it.isNotEmpty() }

  val boards = mutableListOf<Board>().apply {
    val boardInput = inputList.subList(1, inputList.size)
    boardInput.windowed(5, 5).forEach { board ->
      add(Board(board.map { it.map(::BingoNumber) }))
    }
  }

  Day04.part1(inputList.first(), boards)
  println("- - - - -")
  Day04.part2(inputList.first(), boards)
}

object Day04 {

  fun part1(inputList: List<Int>, boards: List<Board>) {
    var endInput = -1

    inputList.forEach { input ->
      if (boards.any { it.hasWon() }) return@forEach
      boards.forEach { it.mark(input) }
      endInput = input
    }

    val winner = boards.first { it.hasWon() }
    val result = winner.unmarkedSum() * endInput

    println("result: $result")
  }

  fun part2(inputList: List<Int>, boards: List<Board>) {
    var endInput = -1

    inputList.forEachIndexed { round, input ->
      if (boards.all { it.hasWon() }) return@forEachIndexed
      boards.forEach { it.mark(input, round) }
      endInput = input
    }

    val lastWinner = boards.maxByOrNull { it.wonRound } ?: error("no last winner ?!")
    val result = lastWinner.unmarkedSum() * endInput

    println("result: $result")
  }
}

data class Board(val rows: List<List<BingoNumber>>, var wonRound: Int = -1) {

  fun hasWon() = checkRows() || checkCols()

  fun mark(number: Int, round: Int = 0) {
    rows.flatten().forEach {
      if (it.value == number) it.marked = true
    }
    if (hasWon() && wonRound == -1) wonRound = round
  }

  private fun checkRows(): Boolean = rows.any { it.all { number -> number.marked } }

  private fun checkCols(): Boolean {
    val cols = mutableListOf<MutableList<BingoNumber>>(
      mutableListOf(),
      mutableListOf(),
      mutableListOf(),
      mutableListOf(),
      mutableListOf()
    )

    rows.forEach {
      it.forEachIndexed { indexNum, bingoNumber ->
        cols[indexNum].add(bingoNumber)
      }
    }

    return cols.any { it.all { number -> number.marked } }
  }

  fun unmarkedSum() = rows.flatten().filter { !it.marked }.sumOf { it.value }
}

data class BingoNumber(
  val value: Int,
  var marked: Boolean = false
)
