package ChessUI.src.main.kotlin

class Square(val x: Int, val y: Int, val color: Char, var piece: Piece?) {
  public fun getCoord(): Coord {
    return Coord(x, y)
  }

  override fun toString(): String {
    return if (piece == null) "$x $y $color  " else "$x $y $color $piece"
  }

  override fun equals(other: Any?): Boolean {
    return when {
      javaClass == other?.javaClass -> {
        other as Square
        getCoord() == other.getCoord() && color == other.color && piece == other.piece
      }
      else -> false
    }
  }
}