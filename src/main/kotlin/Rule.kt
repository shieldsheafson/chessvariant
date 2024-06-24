package chessui.src.main.kotlin

abstract class Rule() {
  abstract val modifiesGetPossibleMoves: Boolean
  abstract val modifiesMovePiece: Boolean
  abstract fun getPossibleMovesModifer(piece: Piece, currentPossibleMoves: MutableSet<Int>, currentFEN: String)
}
