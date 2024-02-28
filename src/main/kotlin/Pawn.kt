package ChessUI.src.main.kotlin

class Pawn(color: Char, x: Int, y: Int, board: Board, wasPawn: Boolean = false) : Piece(color, x, y, board, wasPawn) {

  init {
    if (color == 'w' && y != 6) {
      hasMoved = true
    } else if (color == 'b' && y != 1) {
      hasMoved = true
    }
  }

  override fun possibleMoves(): Set<Coord> {
    val moves = mutableSetOf<Coord>()

    if (color == 'b') {
      if (!hasMoved && board.moveOnBoard(Coord(x, y + 2)) && board.getPiece(Coord(x, y + 1)) == null && board.getPiece(Coord(x, y + 2))  == null) {
        moves += Coord(x, y + 2)
      }
      if (board.moveOnBoard(Coord(x, y + 1)) && board.getPiece(Coord(x, y + 1)) == null) {
        moves += Coord(x, y + 1)
      }
      if (board.moveOnBoard(Coord(x + 1, y + 1)) && board.getPiece(Coord(x + 1, y + 1)) != null) {
        moves += Coord(x + 1, y + 1)
      }
      if (board.moveOnBoard(Coord(x - 1, y + 1)) && board.getPiece(Coord(x - 1, y + 1)) != null) {
        moves += Coord(x - 1, y + 1)
      }
      if (Coord(x + 1, y + 1) == board.enPassantTarget || Coord(x - 1, y + 1) == board.enPassantTarget) {
        moves += board.enPassantTarget!!
      }
    } else {
      if (!hasMoved && board.moveOnBoard(Coord(x, y - 2)) && board.getPiece(Coord(x, y - 1)) == null && board.getPiece(Coord(x, y - 2))  == null) {
        moves += Coord(x, y - 2)
      }
      if (board.moveOnBoard(Coord(x, y - 1)) && board.getPiece(Coord(x, y - 1)) == null) {
        moves += Coord(x, y - 1)
      }
      if (board.moveOnBoard(Coord(x + 1, y - 1)) && board.getPiece(Coord(x + 1, y - 1)) != null) {
        moves += Coord(x + 1, y - 1)
      }
      if (board.moveOnBoard(Coord(x - 1, y - 1)) && board.getPiece(Coord(x - 1, y - 1)) != null) {
        moves += Coord(x - 1, y - 1)
      }
      if (Coord(x + 1, y - 1) == board.enPassantTarget || Coord(x - 1, y - 1) == board.enPassantTarget) {
        moves += board.enPassantTarget!!
      }
    }

    return moves - returnIllegalMoves(moves)
  }

  override fun toString(): String {
    return if (color == 'w') "P" else "p"
  }
}