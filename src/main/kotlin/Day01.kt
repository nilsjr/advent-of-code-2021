val day01List = readInput("Day01").map { it.toInt() }

fun main() {
  val countIncreasedNumbers = day01List.windowed(2).count {
    it[0] < it[1]
  }

  println("count: $countIncreasedNumbers")
}
