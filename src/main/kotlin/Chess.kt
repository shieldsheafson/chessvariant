// // fun main() {
// //   val test = Game()//Game("rnbqkbnr/pppp1ppp/8/4p3/4PP2/8/PPPP2PP/RNBQKBNR b KQkq - 0 2")
// //   // println(test.board[3][4]?.possibleMoves(test.board))
// //   while (true) {
// //     test.movePiece()
// //     test.printBoard()
// //     print(test)
// //   }
// // }
  
// class Game(
//   initial: String = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1"
// ) {

//   val initFen = initial.split(" ")
//   val initBoardPos = initFen[0].split("/")
//   var currentPlayer = initFen[1][0] // second index (0) is for conversion to char
//   var castlingRights = initFen[2]
//   var enPassantTargets = initFen[3]
//   var halfMove = initFen[4].toInt()
//   var fullMove = initFen[5].toInt()
  
//   val board = Array(8) { Array<Piece?>(8) { null } }
//   val takenPieces = mutableListOf<Piece?>()

//   init {
//     for (x in initBoardPos.indices) {
      
//       var y = 0
//       for (j in initBoardPos[x]) {

//         if (j.isDigit()) {
//           y += j.toString().toInt()
//         } else {
//           board[x][y] = fenToPiece(j, y, x)
//           y++
//         }

//       }
    
//     }
//   }

//   fun movePiece() {

//     // some more mega ugly code for testing purposes
//     // prolly i should be writing clean code for testing purposes too
//     // a thing to improve on i suppose

//     printBoard()
//     println("Select Piece to Move in Format xcor ycor")
//     var square = readLine()?.split(" ")

//     if (square == null) { // || square.size() != 2) {
//       return
//     }

//     var chosenPiece = Move(square[0].toInt(), square[1].toInt())
//     val possibleMoves = returnPossibleMoves(chosenPiece)
//     printBoard(possibleMoves)

//     if (possibleMoves.size == 0) {
//       println("Choose Piece with Legal Moves")
//       movePiece()
//     }

//     println("Select Square to Move to in Format xcor ycor or Leave Empty to Choose Another Piece")
//     val move = readLine()?.split(" ")

//     if (move == listOf("")) {
//       movePiece()
//     }
//     if (move == null) { // || move.size() != 2) {
//       return
//     }

//     var chosenMove = Move(move[0].toInt(), move[1].toInt())

//     if (chosenMove in possibleMoves) {
//       movePiece(chosenPiece, chosenMove)
//     }
//   }
  
//   fun movePiece(moveFrom: Move, moveTo: Move) {
//     takenPieces += board[moveTo.y][moveTo.x]
//     board[moveTo.y][moveTo.x] = board[moveFrom.y][moveFrom.x]
//     board[moveFrom.y][moveFrom.x] = null
//     changeCurrentPlayer()

//     // check for end of game
//   }

//   // fun checkForEndOfGame() {
//   //   TODO("Check for End of Game")
//   // }

//   fun returnPossibleMoves(move: Move): Set<Move> {

//     if (!moveOnBoard(move)) {
//       println("Move must be on board")
//       return setOf<Move>()
//     }

//     val piece: Piece? = board[move.y][move.x]
//     if (piece == null) {
//       println("No piece on that square")
//       return setOf<Move>()
//     } else if (piece.color != currentPlayer) {
//       println("Not ${piece.color}'s turn")
//       return setOf<Move>()
//     }
    
//     return piece.possibleMoves(board)
//   }

//   fun changeCurrentPlayer() {
//     when (currentPlayer) {
//       'w' -> currentPlayer = 'b'
//       'b' -> {
//         currentPlayer = 'w'
//         incrementTurn()
//       }
//     }
//   }

//   fun incrementTurn() {
//     fullMove++
//   }

//   fun printBoard() {

//     // for testing purposes, kinda ugly code but whateves

//     var printString = "  "
//     for (x in 0..7) {
//       printString += " ---"
//     }
//     printString += "\n"
//     for (y in board.indices) {
//       printString += "$y "
//       for (x in board[y]) {
//         when (x) {
//           null -> printString += "|   "
//           else -> printString += "| $x "
//         }
//       }
//       printString += "|\n  "
//       for (x in board[y]) {
//         printString += " ---"
//       }
//       printString += "\n"
//     }
//     printString += " "
//     for (x in board[0].indices) {
//       printString += "   $x"
//     }
//     println(printString)
//   }

//   fun printBoard(moves: Set<Move>) {

//     // same as printBoard, but modified slightly to show possible moves
//     // idk exactly how ima do it in the final version so i just wanted a
//     // quick and dirty one for testing

//     var printString = "  "
//     for (x in 0..7) {
//       printString += " ---"
//     }
//     printString += "\n"
//     for (y in board.indices) {
//       printString += "$y "
//       for (x in board[y].indices) {
//         when (board[y][x]) {
//           null -> printString += "|   "
//           else -> printString += "| ${board[y][x]} "
//         }
//         if (Move(x, y) in moves) {
//           printString = printString.substring(0, printString.length - 1) + ":"
//         }
//       }
//       printString += "|\n  "
//       for (x in board[y]) {
//         printString += " ---"
//       }
//       printString += "\n"
//     }
//     printString += " "
//     for (x in board[0].indices) {
//       printString += "   $x"
//     }
//     println(printString)
//   }

//   override fun toString(): String {
//     var currentFen = ""
//     var count = 0

//     for (i in board) {

//       for (j in i) {
//         if (j == null) {
//           count++
//         } else if (count > 0) {
//           currentFen = currentFen + count.toString() + j.toString()
//           count = 0
//         } else {
//           currentFen += j.toString()
//         }
//       }

//       if (count > 0) {
//         currentFen += count.toString()
//       }

//       count = 0
//       currentFen += "/"
//     }
    
//     return "${currentFen.substring(0, currentFen.length - 1)} $currentPlayer $castlingRights $enPassantTargets ${halfMove.toString()} ${fullMove.toString()}"
//   }
// }
  
// fun fenToPiece(fenLetter: Char, x: Int, y: Int): Piece {
//   return when (fenLetter) {
//     'K' -> King('w', x, y)
//     'k' -> King('b', x, y)
//     'Q' -> Queen('w', x, y)
//     'q' -> Queen('b', x, y)
//     'R' -> Rook('w', x, y)
//     'r' -> Rook('b', x, y)
//     'B' -> Bishop('w', x, y)
//     'b' -> Bishop('b', x, y)
//     'N' -> Knight('w', x, y)
//     'n' -> Knight('b', x, y)
//     'P' -> Pawn('w', x, y)
//     else -> Pawn('b', x, y)
//   }
// }

// fun moveOnBoard(move: Move): Boolean {
//   return move.x >= 0 && move.x <= 7 && move.y >= 0 && move.y <= 7
// }

// // abstract class Piece(val color: Char, var x: Int, var y: Int) {

// //   abstract fun possibleMoves(board: Array<Array<Piece?>>): Set<Move>

// //   fun returnIllegalMoves(moves: Set<Move>, board: Array<Array<Piece?>>): Set<Move> {
// //     val illegalMoves = mutableSetOf<Move>()
// //     for (move in moves) { // remove moves outside the board

// //       if (!moveOnBoard(move) || board[move.y][move.x]?.color == color) {
// //         illegalMoves += move
// //       }

// //     }

// //     return illegalMoves
// //   }
  
// //   fun findMovesRecursor(moves: MutableSet<Move>, board: Array<Array<Piece?>>, currentMove: Move, direction: Direction) {
// //     val nextMove = direction.nextMove(currentMove)
// //     moves.add(nextMove)
// //     if (!moveOnBoard(nextMove) || board[nextMove.y][nextMove.x] != null) {
// //       return
// //     }
// //     findMovesRecursor(moves, board, nextMove, direction)
// //   }

// //   fun toMove(): Move {
// //     return Move(x, y)
// //   }
// // }

// class King(color: Char, x: Int, y: Int) : Piece(color, x, y) {

//   override fun possibleMoves(board: Array<Array<Piece?>>): Set<Move> {
//     println("$x $y")

//     val moves = mutableSetOf<Move>(
//       Move(x + 1, y - 1), 
//       Move(x + 1, y + 1), 
//       Move(x + 1, y),  
//       Move(x, y - 1), 
//       Move(x, y + 1), 
//       Move(x - 1, y - 1),
//       Move(x - 1, y),
//       Move(x - 1, y + 1)
//     )

//     val illegalMoves = returnIllegalMoves(moves, board)

//     println(illegalMoves)

//     return moves.subtract(illegalMoves)

//   }

//   override fun toString(): String {
//     return when (color) {
//       'w' -> "K"
//       'b' -> "k"
//       else -> ""
//     }
//   }
// }

// class Queen(color: Char, x: Int, y: Int) : Piece(color, x, y) {

//   override fun possibleMoves(board: Array<Array<Piece?>>): Set<Move> {
//     val moves = mutableSetOf<Move>()

//     // straights
//     findMovesRecursor(moves, board, Move(x, y), Direction(' ', '+'))
//     findMovesRecursor(moves, board, Move(x, y), Direction(' ', '-'))
//     findMovesRecursor(moves, board, Move(x, y), Direction('-', ' '))
//     findMovesRecursor(moves, board, Move(x, y), Direction('+', ' '))

//     // diagonals
//     findMovesRecursor(moves, board, Move(x, y), Direction('+', '+'))
//     findMovesRecursor(moves, board, Move(x, y), Direction('+', '-'))
//     findMovesRecursor(moves, board, Move(x, y), Direction('-', '+'))
//     findMovesRecursor(moves, board, Move(x, y), Direction('-', '-'))

//     return moves - returnIllegalMoves(moves, board)
//   }
  
//   override fun toString(): String {
//     return when (color) {
//       'w' -> "Q"
//       'b' -> "q"
//       else -> ""
//     }
//   }
// }

// class Rook(color: Char, x: Int, y: Int) : Piece(color, x, y) {

//   override fun possibleMoves(board: Array<Array<Piece?>>): Set<Move> {
//     val moves = mutableSetOf<Move>()

//     findMovesRecursor(moves, board, Move(x, y), Direction(' ', '+'))
//     findMovesRecursor(moves, board, Move(x, y), Direction(' ', '-'))
//     findMovesRecursor(moves, board, Move(x, y), Direction('-', ' '))
//     findMovesRecursor(moves, board, Move(x, y), Direction('+', ' '))

//     return moves - returnIllegalMoves(moves, board)
//   }
  
//   override fun toString(): String {
//     return when (color) {
//       'w' -> "R"
//       'b' -> "r"
//       else -> ""
//     }
//   }
// }

// class Knight(color: Char, x: Int, y: Int) : Piece(color, x, y) {

//   override fun possibleMoves(board: Array<Array<Piece?>>): Set<Move> {
//     val moves = mutableSetOf<Move>(
//       Move(x + 2, y + 1), 
//       Move(x + 2, y - 1), 
//       Move(x + 1, y - 2), 
//       Move(x - 1, y - 2),
//       Move(x + 2, y + 2), 
//       Move(x - 2, y + 2), 
//       Move(x + 2, y + 1), 
//       Move(x + 2, y - 1)
//     )

//     return moves - returnIllegalMoves(moves, board)
//   }

//   override fun toString(): String {
//     return when (color) {
//       'w' -> "N"
//       'b' -> "n"
//       else -> ""
//     }
//   }
// }

// class Bishop(color: Char, x: Int, y: Int) : Piece(color, x, y) {

//   override fun possibleMoves(board: Array<Array<Piece?>>): Set<Move> {
//     val moves = mutableSetOf<Move>()

//     findMovesRecursor(moves, board, Move(x, y), Direction('+', '+'))
//     findMovesRecursor(moves, board, Move(x, y), Direction('+', '-'))
//     findMovesRecursor(moves, board, Move(x, y), Direction('-', '+'))
//     findMovesRecursor(moves, board, Move(x, y), Direction('-', '-'))

//     return moves - returnIllegalMoves(moves, board)
//   }

//   override fun toString(): String {
//     return when (color) {
//       'w' -> "B"
//       'b' -> "b"
//       else -> ""
//     }
//   }
// }

// class Pawn(color: Char, x: Int, y: Int) : Piece(color, x, y) {

//   override fun possibleMoves(board: Array<Array<Piece?>>): Set<Move> {
//     val moves = mutableSetOf<Move>()

//     if (color == 'b') {
//       if (y == 1 && moveOnBoard(Move(y + 2, x)) && board[y + 1][x] == null && board[y + 2][x] == null) {
//         moves += Move(x, y + 2)
//       }
//       if (moveOnBoard(Move(y + 1, x)) && board[y + 1][x] == null) {
//         moves += Move(x, y + 1)
//       }
//       if (moveOnBoard(Move(y + 1, x + 1)) && board[y + 1][x + 1] != null) {
//         moves += Move(y + 1, x + 1)
//       }
//       if (moveOnBoard(Move(y + 1, x - 1)) && board[y + 1][x - 1] != null) {
//         moves += Move(y + 1, x - 1)
//       }
//     } else {
//       if (y == 6 && moveOnBoard(Move(y - 2, x)) && board[y - 1][x] == null && board[y - 2][x] == null) {
//         moves += Move(x, y - 2)
//       }
//       if (moveOnBoard(Move(y - 1, x)) && board[y - 1][x] == null) {
//         moves += Move(x, y - 1)
//       }
//       if (moveOnBoard(Move(y - 1, x + 1)) && board[y - 1][x + 1] != null) {
//         moves += Move(y - 1, x + 1)
//       }
//       if (moveOnBoard(Move(y - 1, x - 1)) && board[y - 1][x - 1] != null) {
//         moves += Move(y - 1, x - 1)
//       }
//     }

//     return moves - returnIllegalMoves(moves, board)
//   }

//   override fun toString(): String {
//     return when (color) {
//       'w' -> "P"
//       'b' -> "p"
//       else -> ""
//     }
//   }
// }

// // class Direction(val x: Char, val y: Char) {
// //   // defines the direction movement by x and y
// //   // + means down or to the right, - the opposite
// //   // and the empty char no movement in that axis

// //   fun nextMove(currentMove: Move): Move {

// //     // returns the next scquare if the pieces moves
// //     // one in the direction

// //     return when {
// //       x == '+' && y == '+' -> Move(currentMove.x + 1, currentMove.y + 1)
// //       x == '+' && y == ' ' -> Move(currentMove.x + 1, currentMove.y)
// //       x == '+' && y == '-' -> Move(currentMove.x + 1, currentMove.y - 1)
// //       x == ' ' && y == '+' -> Move(currentMove.x, currentMove.y + 1)
// //       x == ' ' && y == '-' -> Move(currentMove.x + 1, currentMove.y - 1)
// //       x == '-' && y == '+' -> Move(currentMove.x - 1, currentMove.y + 1)
// //       x == '-' && y == ' ' -> Move(currentMove.x - 1, currentMove.y)
// //       else -> Move(currentMove.x - 1, currentMove.y - 1)
// //     }
// //   }
// // }

// data class Move(val x: Int, val y: Int)