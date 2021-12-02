fun main() {
  val day01List = readInput("Day01").map { it.toInt() }

  val countIncreasedNumbers = day01List.windowed(2).count {
    it[0] < it[1]
  }

  println("part1 count: $countIncreasedNumbers")

  val countSumsLargerThanPrev = day01List.windowed(3)
    .map { it.sum() }
    .windowed(2)
    .count { it[0] < it[1] }

  println("part2 count: $countSumsLargerThanPrev")
}