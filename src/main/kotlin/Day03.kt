fun main() {
  val inputList = readInput("Day03").map { line ->
    line.map(Char::digitToInt)
  }

  part1(inputList)
  println("- - - - - - - -")
  part2(inputList)
}

fun part1(inputList: List<List<Int>>) {
  println("Part 1")

  val digits = inputList.first().size - 1
  val binaryResult = mutableListOf<Int>()

  for (i in 0..digits) {
    binaryResult.add(inputList.mostCommonBit(i))
  }

  val gammaRate = binaryResult.joinToString(separator = "").toInt(2)
  val epsilonRate = binaryResult.flip().joinToString(separator = "").toInt(2)
  val result = gammaRate * epsilonRate

  println("gamma=$gammaRate")
  println("epsilon=$epsilonRate")
  println("result=$result")
}

fun part2(inputList: List<List<Int>>) {
  println("Part 2")

  val oxygenRating = findOxygenRating(inputList)
  val scrubberRating = findScrubberRating(inputList)

  println("ratings: $oxygenRating / $scrubberRating")

  val result = oxygenRating * scrubberRating
  println("result=$result")
}

fun findOxygenRating(list: List<List<Int>>): Int {
  val digits = list.first().size - 1
  var filteredList = list
  var index = 0

  while (filteredList.size > 1 && index <= digits) {
    val bit = filteredList.mostCommonBit(index)
    filteredList = filteredList.filter { it[index] == bit }
    index++
  }

  return filteredList.first().joinToString(separator = "").toInt(2)
}

fun findScrubberRating(list: List<List<Int>>): Int {
  val digits = list.first().size - 1
  var filteredList = list
  var index = 0

  while (filteredList.size > 1 && index <= digits) {
    val bit = filteredList.leastCommonBit(index)
    filteredList = filteredList.filter { it[index] == bit }
    index++
  }

  return filteredList.first().joinToString(separator = "").toInt(2)
}

private fun List<List<Int>>.mostCommonBit(index: Int): Int {
  val zero = this.count { it[index] == 0 }
  val ones = this.count { it[index] == 1 }
  return if (zero > ones) 0 else 1
}

private fun List<List<Int>>.leastCommonBit(index: Int): Int {
  val zero = this.count { it[index] == 0 }
  val ones = this.count { it[index] == 1 }
  return if (zero > ones) 1 else 0
}

private fun List<Int>.flip(): List<Int> = this.map { if (it == 0) 1 else 0 }
