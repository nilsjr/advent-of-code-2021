package de.nilsdruyen.adventofcode

import de.nilsdruyen.adventofcode.base.readInput

fun main() {
  val inputList = readInput("Day13")
  val paper = inputList.takeWhile { it.isNotEmpty() }.map {
    val (x, y) = it.split(",")
    x.toInt() to y.toInt()
  }.toPaper()

  val folds: List<Pair<String, String>> = inputList.takeLastWhile { it.isNotEmpty() }.map {
    val (x, y) = it.drop(11).split("=")
    x to y
  }

  Day13.part1(paper, folds)
  Day13.part2(paper, folds)
}

object Day13 {

  fun part1(paper: MutableList<MutableList<Boolean>>, folds: List<Pair<String, String>>) {
    val foldedPaper = paper.fold(folds, 1)
    println(foldedPaper.sumOf { row -> row.count { it } })
  }

  fun part2(paper: MutableList<MutableList<Boolean>>, folds: List<Pair<String, String>>) {
    val foldedPaper = paper.fold(folds)
    foldedPaper.print()
    println(foldedPaper.sumOf { row -> row.count { it } })
  }
}

fun MutableList<MutableList<Boolean>>.fold(
  folds: List<Pair<String, String>>,
  times: Int = -1
): MutableList<MutableList<Boolean>> {
  var foldedPaper = this
  if (times >= 0) {
    folds.take(times)
  } else {
    folds
  }.forEach {
    val (axis, line) = it
    val index = line.toInt()
    when (axis) {
      "x" -> { // fold vertical
        val foldedPage = foldedPaper.map { row -> row.takeLast(index).reversed() }
        foldedPaper = foldedPaper.map { row ->
          row.take(index).toMutableList()
        }.toMutableList()

        foldedPage.forEachIndexed { row, value ->
          value.forEachIndexed { col, b ->
            foldedPaper[row][col] = b || foldedPaper[row][col]
          }
        }
      }
      "y" -> { // fold horizontal
        val foldedPage = foldedPaper.takeLast(index).reversed()
        foldedPaper = foldedPaper.take(index).toMutableList().apply {
          foldedPage.forEachIndexed { row, value ->
            value.forEachIndexed { col, b ->
              this[row][col] = b || this[row][col]
            }
          }
        }
      }
    }
  }
  return foldedPaper
}

fun List<Pair<Int, Int>>.toPaper(): MutableList<MutableList<Boolean>> {
  val maxRow = maxOf { it.second }
  val maxCol = maxOf { it.first }
  val paper = MutableList((0..maxRow).count()) {
    MutableList((0..maxCol).count()) { false }
  }
  forEach {
    paper[it.second][it.first] = true
  }
  return paper
}

fun List<MutableList<Boolean>>.print() {
  forEach {
    println(it.joinToString("") { dot -> if (dot) "#" else "." })
  }
}