package ChessUI.src.main.kotlin

class Game(
  initial: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
) {

  private val squaresPerSide = 8

  private val initFen = initial.split(" ")
  private val initBoardPos = initFen[0]
  var currentPlayer = initFen[1][0] // second index (0) is for conversion to char
    get() = field
    private set
  private var halfMove = initFen[4].toInt()
  private var fullMove = initFen[5].toInt()

  private val board = Board(initBoardPos, initFen[2], initFen[3], squaresPerSide)

  private val takenPieces = mutableListOf<Piece>()

  private var gameEnd = checkForEndOfGame()

  private fun flipCurrentPlayer() {
    if (currentPlayer == 'w') currentPlayer = 'b' else currentPlayer = 'w'
  }

  public fun getSquare(coord: Coord): Square {
    return board.getSquare(coord)
  }

  public fun getPiece(coord: Coord): Piece? {
    return board.getPiece(coord)
  }

  public fun getFirstPartOfFEN(): String {
    return board.getFirstPartOfFEN()
  }

  public fun getPossibleMoves(coord: Coord): Set<Coord> {
    return board.getPossibleMoves(coord)
  }

  public fun movePiece(moveFrom: Coord, moveTo: Coord) {
    if (gameEnd != "") {
      throw IllegalStateException("Game Over: $gameEnd")
    }

    if (halfMove == 100) {
      gameEnd = "Draw"
      throw IllegalStateException("Game Over: $gameEnd")
    }

    if (getPiece(moveFrom)?.color != currentPlayer) {
      throw IllegalArgumentException("Not Players Turn")
    }

    try {
      val takenPiece = board.movePiece(moveFrom, moveTo)
      if (takenPiece != null) {
        if (takenPiece.wasPawn) takenPieces += Pawn(takenPiece.color, 0, 0, board) else takenPieces += takenPiece
      }

      if (!(getPiece(moveFrom) is Pawn) && takenPiece == null) {
        halfMove++
      }

      fullMove++
      flipCurrentPlayer()
      gameEnd = checkForEndOfGame()
      if (gameEnd != "") {
        throw IllegalStateException("Game Over: $gameEnd")
      }
    } catch (e: IllegalArgumentException) {
      throw e
    }
  }

  public fun checkForStaleMate(): Boolean {
    if (currentPlayer == 'w') {
      for (piece in board.whitePieces) {
        if (!getPossibleMoves(piece.getCoord()).isEmpty()) {
          return false
        }
      }
    }

    if (currentPlayer == 'b') {
      for (piece in board.blackPieces) {
        if (!getPossibleMoves(piece.getCoord()).isEmpty()) {
          return false
        }
      }
    }

    return true
  }

  public fun checkForEndOfGame(): String {
    if (!checkForStaleMate()) {
      return ""
    } else if (board.blackKingInCheck()) {
      return "White Won"
    } else if (board.whiteKingInCheck()) {
      return "Black Won"
    } else {
      return "Draw"
    }
  }

  override fun toString(): String {
    return "${getFirstPartOfFEN()} $currentPlayer ${board.castlingRights} ${board.getEnPassantTarget()} $halfMove $fullMove"
  }
}