package ChessUI.src.main.kotlin

class King(color: Char, x: Int, y: Int, board: Board, wasPawn: Boolean = false) : Piece(color, x, y, board, wasPawn) {

  var inCheck = false

  override fun possibleMoves(): Set<Coord> {

    val moves = mutableSetOf<Coord>(
      Coord(x + 1, y - 1), 
      Coord(x + 1, y + 1), 
      Coord(x + 1, y),  
      Coord(x, y - 1), 
      Coord(x, y + 1), 
      Coord(x - 1, y - 1),
      Coord(x - 1, y),
      Coord(x - 1, y + 1)
    )

    if (!hasMoved && !inCheck) {
      if (board.getPiece(Coord(7, y))?.hasMoved == false) {
        moves += Coord(6, y)
      }
      if (board.getPiece(Coord(0, y))?.hasMoved == false) {
        moves += Coord(2, y)
      }
    }

    return moves - returnIllegalMoves(moves)

  }

  override fun toString(): String {
    return if (color == 'w') "K" else "k"
  }
}