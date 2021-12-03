fun main() {
  val inputList = readInput("Day03").map { line ->
    line.map(Char::digitToInt)
  }

  part1(inputList)
  part2(inputList)
}

fun part1(inputList: List<List<Int>>) {
  val digits = inputList.first().size - 1
  val binaryResult = mutableListOf<Int>()

  for (i in 0..digits) {
    val zero = inputList.count { it[i] == 0 }
    val ones = inputList.count { it[i] == 1 }
    if (zero > ones) binaryResult.add(0) else binaryResult.add(1)
  }

  val gammaRate = binaryResult.joinToString(separator = "").toInt(2)
  val epsilonRate = binaryResult.flip().joinToString(separator = "").toInt(2)
  val result = gammaRate * epsilonRate

  println("gamma=$gammaRate")
  println("epsilon=$epsilonRate")
  println("result=$result")
}

fun part2(inputList: List<List<Int>>) {
  val oxygenGeneratorRating = 1
  val CO2ScrubberRating = 1

  val result = oxygenGeneratorRating * CO2ScrubberRating
  println("result=$result")
}

private fun List<Int>.flip(): List<Int> = this.map { if (it == 0) 1 else 0 }
