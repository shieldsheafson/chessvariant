// import kotlin.test.Test
// import kotlin.test.assertEquals
// import kotlin.test.assertTrue
// import kotlin.test.assertFails
// import kotlin.test.assertFailsWith

// import ChessUI.src.main.kotlin.Board
// import ChessUI.src.main.kotlin.Coord
// import ChessUI.src.main.kotlin.Square
// import ChessUI.src.main.kotlin.Piece
// import ChessUI.src.main.kotlin.King
// import ChessUI.src.main.kotlin.Queen
// import ChessUI.src.main.kotlin.Rook
// import ChessUI.src.main.kotlin.Bishop
// import ChessUI.src.main.kotlin.Knight
// import ChessUI.src.main.kotlin.Pawn

// internal class BoardTest {

//   @Test
//   fun testIllegalFENS() {
//     assertFailsWith<IllegalArgumentException>() {
//       Board("rnbq1bnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", 8)
//     }
//     assertFailsWith<IllegalArgumentException>() {
//       Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQ1BNR", 8)
//     }
//   }

//   @Test
//   fun testSquaresAreCorrect1() {

//     val board = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", 8)

//     val coord1 = Coord(7, 7)
//     assertEquals('w', board.getSquare(coord1).color)
//     assertEquals(coord1, board.getSquare(coord1).getCoord())
//     assertTrue { board.getPiece(coord1) is Rook}

//     val coord2 = Coord(0, 0)
//     assertEquals('w', board.getSquare(coord2).color)
//     assertEquals(coord2, board.getSquare(coord2).getCoord())
//     assertTrue { board.getPiece(coord2) is Rook }

//     val coord3 = Coord(4, 4)
//     assertEquals('w', board.getSquare(coord3).color)
//     assertEquals(coord3, board.getSquare(coord3).getCoord())
//     assertTrue { board.getPiece(coord3) == null }

//     val coord4 = Coord(3, 4)
//     assertEquals('b', board.getSquare(coord4).color)
//     assertEquals(coord4, board.getSquare(coord4).getCoord())
//     assertTrue { board.getPiece(coord4) == null }

//     val coord5 = Coord(4, 5)
//     assertEquals('b', board.getSquare(coord5).color)
//     assertEquals(coord5, board.getSquare(coord5).getCoord())
//     assertTrue { board.getPiece(coord5) == null }

//     val coord6 = Coord(5, 5)
//     assertEquals('w', board.getSquare(coord6).color)
//     assertEquals(coord6, board.getSquare(coord6).getCoord())
//     assertTrue { board.getPiece(coord6) == null }
//   }

//   @Test
//   fun testSquaresAreCorrect2() {

//     val board = Board("8/K2p4/3p3p/1P1P4/2B2Pp1/4BN1k/pP3P2/2n5", 8)

//     val coord1 = Coord(7, 7)
//     assertEquals('w', board.getSquare(coord1).color)
//     assertEquals(coord1, board.getSquare(coord1).getCoord())
//     assertTrue { board.getPiece(coord1) == null}

//     val coord2 = Coord(0, 0)
//     assertEquals('w', board.getSquare(coord2).color)
//     assertEquals(coord2, board.getSquare(coord2).getCoord())
//     assertTrue { board.getPiece(coord2) == null }

//     val coord3 = Coord(4, 4)
//     assertEquals('w', board.getSquare(coord3).color)
//     assertEquals(coord3, board.getSquare(coord3).getCoord())
//     assertTrue { board.getPiece(coord3) == null }

//     val coord4 = Coord(3, 4)
//     assertEquals('b', board.getSquare(coord4).color)
//     assertEquals(coord4, board.getSquare(coord4).getCoord())
//     assertTrue { board.getPiece(coord4) == null }

//     val coord5 = Coord(4, 5)
//     assertEquals('b', board.getSquare(coord5).color)
//     assertEquals(coord5, board.getSquare(coord5).getCoord())
//     assertTrue { board.getPiece(coord5) is Bishop }

//     val coord6 = Coord(5, 5)
//     assertEquals('w', board.getSquare(coord6).color)
//     assertEquals(coord6, board.getSquare(coord6).getCoord())
//     assertTrue { board.getPiece(coord6) is Knight }
//   }

//   @Test
//   fun testMoveOnBoard() {
//     val board = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", 8)

//     val expected1 = true
//     assertEquals(expected1, board.moveOnBoard(Coord(0, 0)))

//     val expected2 = true
//     assertEquals(expected2, board.moveOnBoard(Coord(7, 7)))

//     val expected3 = true
//     assertEquals(expected3, board.moveOnBoard(Coord(0, 7)))

//     val expected4 = true
//     assertEquals(expected4, board.moveOnBoard(Coord(7, 0)))

//     val expected5 = true
//     assertEquals(expected5, board.moveOnBoard(Coord(4, 5)))

//     val expected6 = true
//     assertEquals(expected6, board.moveOnBoard(Coord(6, 2)))

//     val expected7 = false
//     assertEquals(expected7, board.moveOnBoard(Coord(-1, 2)))

//     val expected8 = false
//     assertEquals(expected8, board.moveOnBoard(Coord(2, -1)))

//     val expected9 = false
//     assertEquals(expected9, board.moveOnBoard(Coord(8, 2)))

//     val expected10 = false
//     assertEquals(expected10, board.moveOnBoard(Coord(3, 8)))

//     val expected11 = false
//     assertEquals(expected11, board.moveOnBoard(Coord(8, 8)))
//   }

//   @Test
//   fun testGetFirstPartOfFEN() {
//     val board1 = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", 8)
//     val expected1 = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR"
//     assertEquals(expected1, board1.getFirstPartOfFEN())

//     val board2 = Board("1b3N2/4K2P/1B1rpn2/1k5P/7p/5p2/2Q1R1R1/3r4", 8)
//     val expected2 = "1b3N2/4K2P/1B1rpn2/1k5P/7p/5p2/2Q1R1R1/3r4"
//     assertEquals(expected2, board2.getFirstPartOfFEN())

//     val board3 = Board("8/8/P2Pp1Kp/4Ppp1/5k2/n2p1P1P/n1R4r/8", 8)
//     val expected3 = "8/8/P2Pp1Kp/4Ppp1/5k2/n2p1P1P/n1R4r/8"
//     assertEquals(expected3, board3.getFirstPartOfFEN())

//     val board4 = Board("8/5p2/2Pp1PB1/P3Pq1n/1b3P2/RK3k2/PN6/8", 8)
//     val expected4 = "8/5p2/2Pp1PB1/P3Pq1n/1b3P2/RK3k2/PN6/8"
//     assertEquals(expected4, board4.getFirstPartOfFEN())
//   }

//   // @Test
//   // fun testMovePiecePawn() {
//   //   val board1 = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", 8)
//   //   board1.movePiece(Coord(4, 6), Coord())
//   //   assertEquals(expected1, board1.getFirstPartOfFEN())
//   // }
// }