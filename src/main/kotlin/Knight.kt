package ChessUI.src.main.kotlin

class Knight(color: Char, x: Int, y: Int, board: Board, wasPawn: Boolean = false) : Piece(color, x, y, board, wasPawn) {

  override fun possibleMoves(): Set<Coord> {
    val moves = mutableSetOf<Coord>(
      Coord(x + 2, y + 1), 
      Coord(x + 2, y - 1), 
      Coord(x - 2, y + 1), 
      Coord(x - 2, y - 1),
      Coord(x + 1, y + 2), 
      Coord(x - 1, y + 2), 
      Coord(x + 1, y - 2), 
      Coord(x - 1, y - 2)
    )

    return moves - returnIllegalMoves(moves)
  }

  override fun toString(): String {
    return if (color == 'w') "N" else "n"
  }
}