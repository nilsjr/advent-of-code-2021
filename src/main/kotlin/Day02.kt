val day02List = readInput("Day02").map {
  val split = it.split(" ")
  Step(split[0].toDirection(), split[1].toInt())
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

fun main() {
  val map = day02List.groupBy { it.direction }

  val horizontal = map[Direction.FORWARD].sum()
  val depth = map[Direction.DOWN].sum() - map[Direction.UP].sum()
  val result = horizontal * depth

  println("horizontal/depth: $horizontal * $depth = $result")
}