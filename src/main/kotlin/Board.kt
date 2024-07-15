package chessnt.src.main.kotlin

import kotlin.math.abs

class Board(initialPos: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR") {
  val emptyPiece = Piece()
  private val board = Array<Piece>(TOTALSQUARES) { emptyPiece }
  private val pieceLocations = mutableSetOf<Int>()
  private val kingLocations = mutableMapOf(WHITE to -1, BLACK to -1)

  val startingWhitePawnRow = 6
  val startingBlackPawnRow = 1

  init {
    var i = 0
    for (char in initialPos) {
      if (char.isDigit()) {
        i += char.digitToInt()
      } else if (char != '/') {
        board[i] = Piece(char, i)
        if (board[i].isKing) {
          kingLocations[board[i].color!!] = i
        }
        pieceLocations += i
        i++
      }
    }
  }

  fun movePiece(from: Int, to: Int) {
    if (!onBoard(from) || !onBoard(to)) {
      throw IndexOutOfBoundsException("to: $to from: $from")
    }
    val piece = board[from]

    pieceLocations -= from
    pieceLocations += to

    if (piece.isKing) {
      kingLocations[piece.color!!] = to
    }

    board[from] = emptyPiece
    board[to] = piece
  }

  fun setSquareEmpty(i: Int) {
    if (!onBoard(i)) {
      throw IndexOutOfBoundsException("i: $i")
    }
    pieceLocations -= i
    board[i] = emptyPiece
  }

  operator fun get(i: Int?) = if (i != null) board[i] else emptyPiece

  fun getCol(i: Int) = i % SQUARESPERSIDE

  fun getRow(i: Int) = i / SQUARESPERSIDE

  fun getPieceLocations(): Set<Int> = pieceLocations

  fun getKingLocation(color: Boolean) = kingLocations[color]!!

  fun getSquares(startingSquare: Int, direction: Int): List<Int> {
    val squares = mutableListOf<Int>()

    var i = startingSquare + direction
    while (onBoard(i) && abs(getCol(i) - getCol(i - direction)) < 2 && board[i].isEmpty) {
      squares += i
      i += direction
    }

    if (onBoard(i) && abs(getCol(i) - getCol(i - direction)) < 2) {
      squares += i
    }

    return squares
  }

  fun getPieceLocationsInDirection(startingSquare: Int, direction: Int): List<Int> {
    val pieceLocations = mutableListOf<Int>()

    var i = startingSquare + direction
    while (onBoard(i) && abs(getCol(i) - getCol(i - direction)) < 2) {
      if (!board[i].isEmpty) {
        pieceLocations += i
      }
      i += direction
    }

    return pieceLocations
  }

  fun getSquaresBetween(i: Int, j: Int): List<Int> {
    val smallerIndex = minOf(i, j)
    val largerIndex = maxOf(i, j)
    if (getRow(smallerIndex) == getRow(largerIndex)) {
      return List<Int>(largerIndex - smallerIndex - 1) { smallerIndex + RIGHT + it * RIGHT }
    }
    if (getCol(smallerIndex) == getCol(largerIndex)) {
      return List<Int>(getRow(largerIndex) - getRow(smallerIndex) - 1) { smallerIndex + DOWN + it * DOWN }
    }
    if (getRow(smallerIndex) + getCol(smallerIndex) == getRow(largerIndex) + getCol(largerIndex)) {
      return List<Int>(getRow(largerIndex) - getRow(smallerIndex) - 1) { smallerIndex + DOWNLEFT + it * DOWNLEFT }
    }
    return List<Int>(getRow(largerIndex) - getRow(smallerIndex) - 1) { smallerIndex + DOWNRIGHT + it * DOWNRIGHT }
  }

  fun onBoard(i: Int) = i >= 0 && i < TOTALSQUARES

  fun getFEN(): String {
    var fen = ""

    var sinceLastPiece = 0
    for (i in board.indices) {
      if (board[i].color != null) {
        if (sinceLastPiece != 0) fen += sinceLastPiece.toString()
        fen += board[i].fenChar
        sinceLastPiece = 0
      } else {
        sinceLastPiece++
      }

      if ((i + 1) % 8 == 0) {
        if (sinceLastPiece != 0) fen += sinceLastPiece.toString()
        fen += "/"
        sinceLastPiece = 0
      }
    }

    return fen.substring(0, fen.length - 1)
  }
}
