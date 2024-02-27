package ChessUI.src.main.kotlin

import kotlin.math.abs

class Board(firstPartOfFEN: String, castlingRightsInit: String, enPassantTargetInit: String, val squaresPerSide: Int) {

  val board = Array(squaresPerSide) { y -> Array<Square>(squaresPerSide) { x -> Square(x, y, determineColor(x, y), null) }}
  var castlingRights = castlingRightsInit
    private set
  var enPassantTarget = if (enPassantTargetInit == "-") null else Coord("abcdefgh".indexOf(enPassantTargetInit[0]), (enPassantTargetInit[1].digitToInt() - 8) * -1)
  val whitePieces = mutableSetOf<Piece>()
  val blackPieces = mutableSetOf<Piece>()
  lateinit var whiteKing: King
    private set
  lateinit var blackKing: King
    private set

  init {
    val initBoardPos = firstPartOfFEN.split("/")
    for (y in initBoardPos.indices) {
      
      var x = 0
      for (i in initBoardPos[y]) {
        if (i.isDigit()) {
          x += i.toString().toInt()
        } else {
          val piece = fenToPiece(i, x, y)

          if (piece.color == 'w') {
            whitePieces += piece
            if (piece is King) {
              whiteKing = piece
            }
          } else {
            blackPieces += piece
            if (piece is King) {
              blackKing = piece
            }
          }

          board[y][x] = Square(x, y, determineColor(x, y), piece)
          x++
        }
      }
    }

    if (!this::blackKing.isInitialized || !this::whiteKing.isInitialized) {
      throw IllegalArgumentException("Illegal Starting Posistion, FEN did not contain a king")
    }
  }

  private fun determineColor(x: Int, y: Int): Char  {
    return when {
      x % 2 == 0 && y % 2 == 0 -> 'w'
      x % 2 != 0 && y % 2 == 0 -> 'b'
      x % 2 == 0 && y % 2 != 0 -> 'b'
      else -> 'w'
    }
  }

  private fun fenToPiece(fenLetter: Char, x: Int, y: Int): Piece {
    return when (fenLetter) {
      'K' -> King('w', x, y, this)
      'k' -> King('b', x, y, this)
      'Q' -> Queen('w', x, y, this)
      'q' -> Queen('b', x, y, this)
      'R' -> Rook('w', x, y, this)
      'r' -> Rook('b', x, y, this)
      'B' -> Bishop('w', x, y, this)
      'b' -> Bishop('b', x, y, this)
      'N' -> Knight('w', x, y, this)
      'n' -> Knight('b', x, y, this)
      'P' -> Pawn('w', x, y, this)
      else -> Pawn('b', x, y, this)
    }
  }
  
  public fun getPiece(coord: Coord): Piece? {
    return getSquare(coord).piece
  }

  public fun getSquare(coord: Coord): Square {
    return board[coord.y][coord.x]
  }

  public fun getPossibleMoves(coord: Coord): Set<Coord> {
    val piece = getPiece(coord)
    var possibleMoves = piece?.possibleMoves() ?: setOf<Coord>()

    for (move in possibleMoves) {

      val testBoard = Board(getFirstPartOfFEN(), castlingRights, getEnPassantTarget(), squaresPerSide)
      testBoard.movePiece(coord, move)
      testBoard.checkForCheck()
      if (testBoard.whiteKing.inCheck && getPiece(coord)?.color == 'w') {
        possibleMoves -= move
      } 
      if (testBoard.blackKing.inCheck && getPiece(coord)?.color == 'b') {
        possibleMoves -= move
      } 
    }

    return possibleMoves
  }

  public fun getFirstPartOfFEN(): String {
    var currentFen = ""
    var count = 0

    for (i in board) {

      for (j in i) {
        if (j.piece == null) {
          count++
        } else if (count > 0) {
          currentFen = currentFen + count.toString() + j.piece.toString()
          count = 0
        } else {
          currentFen += j.piece.toString()
        }
      }

      if (count > 0) {
        currentFen += count.toString()
      }

      count = 0
      currentFen += "/"
    }
    
    return "${currentFen.substring(0, currentFen.length - 1)}"
  }

  public fun getEnPassantTarget(): String {
    return if (enPassantTarget == null) "-" else "${"abcdefgh"[enPassantTarget!!.x]}${abs(enPassantTarget!!.y - 8)}"
  }

  public fun moveOnBoard(move: Coord): Boolean {
    return move.x >= 0 && move.x < squaresPerSide && move.y >= 0 && move.y < squaresPerSide
  }

  // returns piece taken
  public fun movePiece(moveFrom: Coord, moveTo: Coord): Piece? {
    if (!moveOnBoard(moveFrom) || !moveOnBoard(moveTo)) throw IllegalArgumentException("Square Not on Board")
    val pieceToBeMoved = getPiece(moveFrom) ?: throw IllegalArgumentException("No Piece to Move")
    val possibleMoves = pieceToBeMoved.possibleMoves().toMutableSet()

    if (moveTo in possibleMoves) {
      var takenPiece = getPiece(moveTo)

      pieceToBeMoved.x = moveTo.x
      pieceToBeMoved.y = moveTo.y
      pieceToBeMoved.hasMoved = true

      getSquare(moveTo).piece = pieceToBeMoved
      getSquare(moveFrom).piece = null

      // check for enPassant capture
      if (pieceToBeMoved is Pawn && moveTo == enPassantTarget) {
        if (pieceToBeMoved.color == 'w') {
          takenPiece = getPiece(Coord(moveTo.x, moveTo.y + 1))
          getSquare(Coord(moveTo.x, moveTo.y + 1)).piece = null
        } else {
          takenPiece = getPiece(Coord(moveTo.x, moveTo.y - 1))
          getSquare(Coord(moveTo.x, moveTo.y - 1)).piece = null
        }
      }

      // check for castle in order to also move the rook
      if (pieceToBeMoved is King && abs(moveFrom.x - moveTo.x) == 2) {
        if (moveFrom.x - moveTo.x > 0) {
          val rook = getPiece(Coord(0, pieceToBeMoved.y))
          getSquare(Coord(0, pieceToBeMoved.y)).piece = null
          getSquare(Coord(moveFrom.x - 1, moveFrom.y)).piece = rook
        } else {
          val rook = getPiece(Coord(squaresPerSide - 1, pieceToBeMoved.y))
          getSquare(Coord(squaresPerSide - 1, pieceToBeMoved.y)).piece = null
          getSquare(Coord(moveFrom.x + 1, moveFrom.y)).piece = rook
        }
      }

      // add/remove enPassantTarget
      if (pieceToBeMoved is Pawn && abs(moveTo.y - moveFrom.y) == 2) {
        if (pieceToBeMoved.color == 'w') {
          enPassantTarget = Coord(moveTo.x, moveTo.y + 1)
        } else {
          enPassantTarget = Coord(moveTo.x, moveTo.y - 1)
        }
      } else {
        enPassantTarget = null
      }

      // promotion
      if (pieceToBeMoved is Pawn && (pieceToBeMoved.y == squaresPerSide - 1 || pieceToBeMoved.y == 0)) {
        getSquare(moveTo).piece = Queen(pieceToBeMoved.color, pieceToBeMoved.x, pieceToBeMoved.y, this, true)
      }
      
      // gross but compiles, I'll come back and think of more elegant solution
      if (takenPiece != null) if (takenPiece.color == 'w') whitePieces -= takenPiece else blackPieces -= takenPiece

      checkForCheck()

      return takenPiece
    } else {
      throw IllegalArgumentException("Move not Possible")
    }
  }
  
  public fun checkForCheck() {
    blackKing.inCheck = false
    whiteKing.inCheck = false

    for (piece in blackPieces) {
      if (whiteKing.getCoord() in piece.possibleMoves()) {
        whiteKing.inCheck = true
      }
    }

    for (piece in whitePieces) {
      if (blackKing.getCoord() in piece.possibleMoves()) {
        blackKing.inCheck = true
      }
    }
  }

  public fun whiteKingInCheck(): Boolean {
    return whiteKing.inCheck
  }

  public fun blackKingInCheck(): Boolean {
    return blackKing.inCheck
  }

  override fun equals(other: Any?): Boolean {
    return when {
      javaClass == other?.javaClass -> {
        other as Board
        boardBoardEquals(other)
      }
      else -> false
    }
  }

  private fun boardBoardEquals(otherBoard: Board): Boolean {
    if (squaresPerSide != otherBoard.squaresPerSide) {
      return false
    }

    for (i in board.indices) {
      for (j in board[i].indices) {
        if (getSquare(Coord(j, i)) != otherBoard.getSquare(Coord(j, i))) {
          return false
        }
      }
    }

    return true
  }
}