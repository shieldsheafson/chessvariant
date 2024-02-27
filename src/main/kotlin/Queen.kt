package ChessUI.src.main.kotlin

class Queen(color: Char, x: Int, y: Int, board: Board, wasPawn: Boolean = false) : Piece(color, x, y, board, wasPawn) {

  override fun possibleMoves(): Set<Coord> {
    val moves = mutableSetOf<Coord>()

    // straights
    findMovesRecursor(moves, Coord(x, y), Direction(' ', '+'))
    findMovesRecursor(moves, Coord(x, y), Direction(' ', '-'))
    findMovesRecursor(moves, Coord(x, y), Direction('-', ' '))
    findMovesRecursor(moves, Coord(x, y), Direction('+', ' '))

    // diagonals
    findMovesRecursor(moves, Coord(x, y), Direction('+', '+'))
    findMovesRecursor(moves, Coord(x, y), Direction('+', '-'))
    findMovesRecursor(moves, Coord(x, y), Direction('-', '+'))
    findMovesRecursor(moves, Coord(x, y), Direction('-', '-'))

    return moves - returnIllegalMoves(moves)
  }
  
  override fun toString(): String {
    return if (color == 'w') "Q" else "q"
  }
}