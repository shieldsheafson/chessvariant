package ChessUI.src.main.kotlin

class Direction(val x: Char, val y: Char) {
  // defines the direction movement by x and y
  // + means down or to the right, - the opposite
  // and the empty char no movement in that axis

  fun nextMove(currentCoord: Coord): Coord {

    // returns the next scquare if the pieces moves
    // one in the direction

    return when {
      x == '+' && y == '+' -> Coord(currentCoord.x + 1, currentCoord.y + 1)
      x == '+' && y == ' ' -> Coord(currentCoord.x + 1, currentCoord.y)
      x == '+' && y == '-' -> Coord(currentCoord.x + 1, currentCoord.y - 1)
      x == ' ' && y == '+' -> Coord(currentCoord.x, currentCoord.y + 1)
      x == ' ' && y == '-' -> Coord(currentCoord.x, currentCoord.y - 1)
      x == '-' && y == '+' -> Coord(currentCoord.x - 1, currentCoord.y + 1)
      x == '-' && y == ' ' -> Coord(currentCoord.x - 1, currentCoord.y)
      else -> Coord(currentCoord.x - 1, currentCoord.y - 1)
    }
  }
}