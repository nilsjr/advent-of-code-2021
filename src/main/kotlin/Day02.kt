fun main() {
  val day02List = readInput("Day02").map {
    val split = it.split(" ")
    Step(split[0].toDirection(), split[1].toInt())
  }

  part1(day02List)
  part2(day02List)
}

fun part1(inputList: List<Step>) {
  val map = inputList.groupBy { it.direction }

  val horizontal = map[Direction.FORWARD].sum()
  val depth = map[Direction.DOWN].sum() - map[Direction.UP].sum()
  val result = horizontal * depth

  println("part1 result: $horizontal * $depth = $result")
}

fun part2(inputList: List<Step>) {
  var aim = 0
  var horizontal = 0
  var depth = 0

  inputList.forEach {
    when (it.direction) {
      Direction.DOWN -> aim += it.depth
      Direction.UP -> aim -= it.depth
      Direction.FORWARD -> {
        horizontal += it.depth
        depth += (aim * it.depth)
      }
    }
  }

  println("part2 result: $horizontal * $depth = ${horizontal * depth}")
}

data class Step(val direction: Direction, val depth: Int)

enum class Direction {
  FORWARD, DOWN, UP,
}

fun String.toDirection(): Direction = when (this) {
  "forward" -> Direction.FORWARD
  "down" -> Direction.DOWN
  "up" -> Direction.UP
  else -> error("unknown direction: $this")
}

fun List<Step>?.sum(): Int = this?.sumOf { it.depth } ?: 0