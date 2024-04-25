package chessui.src.main.kotlin

import kotlin.math.abs

class Game(initial: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1") {
  var currentPlayer: Boolean = initial.split(" ")[1] == "w"
  private var castlingRights: String = initial.split(" ")[2]
  private var enPassantTarget: Int = enPassantTargetStringToInt(initial.split(" ")[3])
  private var fullMoves: Int = initial.split(" ")[4].toInt()
  private var halfMoves: Int = initial.split(" ")[5].toInt()

  val board = Board(initial.split(" ")[0])

  val attacks = mapOf(WHITE to mutableListOf<Int>(), BLACK to mutableListOf<Int>())
  val movesThatBlockCheck = mutableMapOf(WHITE to List<Int>(TOTALSQUARES) { it }, BLACK to List<Int>(TOTALSQUARES) { it })

  init {
    for (pieceLocation in board.getPieceLocations()) {
      attacks[board[pieceLocation].color!!]!! += getAttacks(pieceLocation, board[pieceLocation])
    }
  }

  fun getPossibleMoves(square: Int): Set<Int> {
    if (!board.onBoard(square)) {
      throw IllegalArgumentException("$square is not a valid square")
    }

    val piece = board[square]

    if (piece.isEmpty) {
      throw IllegalArgumentException("No piece on square: $square")
    }

    val pieceColor = piece.color!!
    if (square in getPinnedPieces(board.getKingLocation(pieceColor))) {
      return setOf()
    }

    val moves = piece.moves.toMutableSet<Int>()
    val possibleMoves = mutableSetOf<Int>()
    val movesToRemove = mutableSetOf<Int>()

    var i = 1
    do {
      for (move in moves) {
        val newMove = square + move * i
        if (!board.onBoard(newMove)) {
          movesToRemove += move
        } else if (abs(board.getCol(newMove) - board.getCol(newMove - move)) > 2) {
          movesToRemove += move
        } else if (board[newMove].color == piece.color) {
          movesToRemove += move
        } else if (!board[newMove].isEmpty) {
          possibleMoves += newMove
          movesToRemove += move
        } else {
          possibleMoves += newMove
        }
      }
      moves -= movesToRemove
      i++
    } while (piece.isFullBoardMover && moves.size != 0)

    // special pawn moves
    if (piece.isPawn) {
      // prevent pawn from capturing in front of itself
      if (possibleMoves.size == 1 && !board[possibleMoves.elementAt(0)].isEmpty) {
        possibleMoves.clear()
      }

      // first move
      if (square == piece.startingSquare && board[
        square +
          piece.moves.elementAt(
              0,
            ),
      ].isEmpty && board[square + piece.moves.elementAt(0) * 2].isEmpty
      ) {
        possibleMoves += square + piece.moves.elementAt(0) * 2
      }

      // attacks and en passant
      if (piece.color == WHITE) {
        if ((board[square + UPRIGHT].color == BLACK || square + UPRIGHT == enPassantTarget) && board.getCol(square) != 7) {
          possibleMoves += square + UPRIGHT
        }
        if ((board[square + UPLEFT].color == BLACK || square + UPLEFT == enPassantTarget) && board.getCol(square) != 0) {
          possibleMoves += square + UPLEFT
        }
      } else {
        if ((board[square + DOWNRIGHT].color == WHITE || square + DOWNRIGHT == enPassantTarget) && board.getCol(square) != 7) {
          possibleMoves += square + DOWNRIGHT
        }
        if ((board[square + DOWNLEFT].color == WHITE || square + DOWNLEFT == enPassantTarget) && board.getCol(square) != 0) {
          possibleMoves += square + DOWNLEFT
        }
      }
    }

    if (piece.isKing) {
      if ('Q' in castlingRights && board[59].isEmpty && board[58].isEmpty && board[57].isEmpty && listOf(59, 58, 57).intersect(attacks[!pieceColor]!!) == setOf<Int>()) {
        possibleMoves += 58
      }
      if ('K' in castlingRights && board[61].isEmpty && board[62].isEmpty && listOf(61, 62).intersect(attacks[!pieceColor]!!) == setOf<Int>()) {
        possibleMoves += 62
      }
      if ('q' in castlingRights && board[1].isEmpty && board[2].isEmpty && board[3].isEmpty && listOf(1, 2, 3).intersect(attacks[!pieceColor]!!) == setOf<Int>()) {
        possibleMoves += 2
      }
      if ('k' in castlingRights && board[5].isEmpty && board[5].isEmpty && listOf(5, 6).intersect(attacks[!pieceColor]!!) == setOf<Int>()) {
        possibleMoves += 6
      }

      for (direction in DIRECTIONS) {

        val pieceLocations = board.getPieceLocationsInDirection(board.getKingLocation(pieceColor), direction)
        val nearestPiece = board[pieceLocations.getOrNull(0) ?: continue]
        if (nearestPiece.isFullBoardMover && -1 * direction in nearestPiece.moves && nearestPiece.color == !pieceColor) {
          possibleMoves -= square + -1 * direction
        }
      }
      return possibleMoves - attacks[!pieceColor]!!
    }
    return possibleMoves.intersect(movesThatBlockCheck[pieceColor]!!)
  }

  fun getAttacks(square: Int, piece: Piece): Set<Int> {
    if (!board.onBoard(square)) {
      throw IllegalArgumentException("$square is not a valid square")
    }

    val moves = piece.moves.toMutableSet<Int>()
    val movesToRemove = mutableSetOf<Int>()

    val attacks = mutableSetOf<Int>()

    if (piece.isPawn) {
      if (piece.color == WHITE) {
        if (board.getCol(square) != 7) {
          attacks += square + UPRIGHT
        }
        if (board.getCol(square) != 0) {
          attacks += square + UPLEFT
        }
      } else {
        if (board.getCol(square) != 7) {
          attacks += square + DOWNRIGHT
        }
        if (board.getCol(square) != 0) {
          attacks += square + DOWNLEFT
        }
      }
      return attacks
    }

    var i = 1
    do {
      for (move in moves) {
        val newMove = square + move * i
        if (!board.onBoard(newMove)) {
          movesToRemove += move
        } else if (abs(board.getCol(newMove) - board.getCol(newMove - move)) > 2) {
          movesToRemove += move
        } else if (board[newMove].color == piece.color) {
          attacks += newMove
          movesToRemove += move
        } else if (!board[newMove].isEmpty) {
          attacks += newMove
          movesToRemove += move
        } else {
          attacks += newMove
        }
      }
      moves -= movesToRemove
      i++
    } while (piece.isFullBoardMover && moves.size != 0)

    return attacks
  }

  fun movePiece(
    from: Int,
    to: Int,
  ) {

    // check for validity

    val piece = board[from]

    if (to == enPassantTarget) {
      if (piece.color == WHITE) {
        board.setSquareEmpty(enPassantTarget + 8)
      } else {
        board.setSquareEmpty(enPassantTarget - 8)
      }
    }
    if (piece.isPawn && to == from + piece.moves.elementAt(0) * 2) {
      enPassantTarget = from + piece.moves.elementAt(0)
    } else {
      enPassantTarget = -1
    }

    if (piece.isKing) {
      var rookFrom = 0
      var rookTo = 0
      if (from - to == -2) {
        rookFrom = from + 3
        rookTo = from + 1
      } else if (from - to == 2) {
        rookFrom = from - 4
        rookTo = from - 1
      }

      movePiece(rookFrom, rookTo)
      currentPlayer = !currentPlayer
    }

    val oldAttacks = getAttacks(from, piece)
    val newAttacks = getAttacks(to, piece)

    oldAttacks.forEach { attacks[currentPlayer]!! -= it }
    attacks[currentPlayer]!! += newAttacks

    if (!board[to].isEmpty) {
      getAttacks(to, board[to]).forEach { attacks[!currentPlayer]!! -= it }
    }

    if (board.getKingLocation(!currentPlayer) in newAttacks) {
      if (piece.fenChar.lowercase() == "n") {
        movesThatBlockCheck[!currentPlayer] = listOf(to)
      } else {
        movesThatBlockCheck[!currentPlayer] = movesThatBlockCheck[!currentPlayer]!!.intersect(board.getSquaresBetween(board.getKingLocation(!currentPlayer), to)).toList()
      }
    }

    board.movePiece(from, to)

    for (direction in DIRECTIONS) {
      val pieceLocationsTo = board.getPieceLocationsInDirection(to, direction)
      val nearestPieceTo = board[pieceLocationsTo.getOrNull(0) ?: continue]
      if (nearestPieceTo.isFullBoardMover && -1 * direction in nearestPieceTo.moves) {
        board.getSquares(to, -1 * direction).forEach { attacks[nearestPieceTo.color!!]!! -= it }
      }
    }

    for (direction in DIRECTIONS) {
      val pieceLocationsFrom = board.getPieceLocationsInDirection(from, direction)
      val nearestPieceFrom = board[pieceLocationsFrom.getOrNull(0) ?: continue]
      if (nearestPieceFrom.isFullBoardMover && -1 * direction in nearestPieceFrom.moves) {
        val attacksToAdd = board.getSquares(from, -1 * direction)
        attacks[nearestPieceFrom.color!!]!! += attacksToAdd
        if (nearestPieceFrom.color == currentPlayer && board.getKingLocation(!currentPlayer) in attacksToAdd) {
          movesThatBlockCheck[!currentPlayer] = movesThatBlockCheck[!currentPlayer]!!.intersect(board.getSquaresBetween(board.getKingLocation(!currentPlayer), pieceLocationsFrom[0])).toList()
        }
      }
    }

    movesThatBlockCheck[currentPlayer] = List<Int>(TOTALSQUARES) { it }
    currentPlayer = !currentPlayer
  }

  fun getPinnedPieces(squareNum: Int): Set<Int> {

    val piece = board[squareNum]
    val pieceColor = piece.color!!

    val pinnedPieces = mutableSetOf<Int>()

    for (direction in DIRECTIONS) {
      val pieceLocations = board.getPieceLocationsInDirection(squareNum, direction)

      if (pieceLocations.size >= 2 &&
        board[pieceLocations[0]].color == pieceColor &&
        board[pieceLocations[1]].color == !pieceColor &&
        board[pieceLocations[1]].isFullBoardMover
      ) {
        pinnedPieces += pieceLocations[0]
      }
    }

    return pinnedPieces
  }

  fun inCheck(): Boolean {
    if (currentPlayer) {
      return board.getKingLocation(WHITE) in attacks[BLACK]!!
    } else {
      return board.getKingLocation(BLACK) in attacks[WHITE]!!
    }
  }

  fun enPassantTargetStringToInt(target: String): Int {
    val alphabet = "abcdefgh"
    return if (target == "-") {
      -1
    } else {
      alphabet.indexOf(target[0]) + abs(target[1].digitToInt() - 8)
    }
  }

  fun enPassantTargetIntToString(target: Int): String {
    val alphabet = "abcdefgh"
    return if (target == -1) {
      "-"
    } else {
      alphabet[target % 8] + (abs((target / 8) - 8)).toString()
    }
  }

  override fun toString(): String {
    return "${board.getFEN()} ${if (currentPlayer) 'w' else 'b'} $castlingRights ${enPassantTargetIntToString(
      enPassantTarget,
    )} $fullMoves $halfMoves"
  }
}
