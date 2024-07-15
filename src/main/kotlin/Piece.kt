package chessnt.src.main.kotlin

class Piece(val fenChar: Char = ' ', val startingSquare: Int = -1) {
  val isEmpty = fenChar == ' '
  val color: Boolean? = if (isEmpty) null else fenChar.isUpperCase()
  val isKing = fenChar.lowercase() == "k"
  val isPawn = fenChar.lowercase() == "p"
  val isFullBoardMover = fenChar.lowercase() in "qrb"
  val moves: Set<Int>

  init {
    moves = when {
      fenChar.lowercase() == "k" -> setOf(UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT)
      fenChar.lowercase() == "q" -> setOf(UP, DOWN, LEFT, RIGHT, UPLEFT, UPRIGHT, DOWNLEFT, DOWNRIGHT)
      fenChar.lowercase() == "r" -> setOf(UP, DOWN, LEFT, RIGHT)
      fenChar.lowercase() == "b" -> setOf(UPRIGHT, UPLEFT, DOWNRIGHT, DOWNLEFT)
      fenChar.lowercase() == "n" -> setOf(-15, -17, 15, 17, 10, 6, -10, -6)
      fenChar == 'p' -> setOf(DOWN)
      fenChar == 'P' -> setOf(UP)
      fenChar == ' ' -> setOf<Int>()
      else -> throw IllegalArgumentException("Bad fenChar: $fenChar")
    }
  }
}
