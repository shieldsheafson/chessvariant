package ChessUI.src.main.kotlin

abstract class Piece(val color: Char, var x: Int, var y: Int, val board: Board, val wasPawn: Boolean = false) {

  var hasMoved = false

  abstract fun possibleMoves(): Set<Coord>

  fun returnIllegalMoves(moves: Set<Coord>): Set<Coord> {
    val illegalMoves = mutableSetOf<Coord>()
    for (move in moves) { // remove moves outside the board and moves that take a piece of the same color

      if (!board.moveOnBoard(move) || board?.getPiece(move)?.color == color) {
        illegalMoves += move
      }

    }

    return illegalMoves
  }
  
  fun findMovesRecursor(moves: MutableSet<Coord>, currentCoord: Coord, direction: Direction) {
    val nextMove = direction.nextMove(currentCoord)
    moves.add(nextMove)
    if (!board.moveOnBoard(nextMove) || board?.getPiece(nextMove) != null) {
      return
    }
    findMovesRecursor(moves, nextMove, direction)
  }

  fun getCoord(): Coord {
    return Coord(x, y)
  }

  override fun equals(other: Any?): Boolean {
    return when {
      javaClass == other?.javaClass -> {
        other as Piece
        color == other.color && getCoord() == other.getCoord()
      }
      else -> false
    }
  }
}