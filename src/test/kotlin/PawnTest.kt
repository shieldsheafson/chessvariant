import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFails
import kotlin.test.assertNotEquals

import ChessUI.src.main.kotlin.Board
import ChessUI.src.main.kotlin.Coord
import ChessUI.src.main.kotlin.Square
import ChessUI.src.main.kotlin.Piece
import ChessUI.src.main.kotlin.King
import ChessUI.src.main.kotlin.Queen
import ChessUI.src.main.kotlin.Rook
import ChessUI.src.main.kotlin.Bishop
import ChessUI.src.main.kotlin.Knight
import ChessUI.src.main.kotlin.Pawn

internal class PawnTest {

  @Test
  fun testToString() {
    val board = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", "KQkq", "-", 8)
    assertEquals("P", Pawn('w', 0, 0, board).toString())
    assertEquals("p", Pawn('b', 0, 0, board).toString())
  }

  @Test
  fun testPossibleMoves() {
    val board = Board("4k3/p2p3p/3P2p1/1Pp1pp2/2PP1Pp1/1P2p3/P3P2P/4K3", "-", "-", 8)

    // white
    val expected1 = setOf(Coord(0, 4), Coord(0, 5))
    assertEquals(expected1, board.getPiece(Coord(0, 6))!!.possibleMoves())

    val expected2 = setOf(Coord(1, 4))
    assertEquals(expected2, board.getPiece(Coord(1, 5))!!.possibleMoves())

    val expected3 = setOf<Coord>()
    assertEquals(expected3, board.getPiece(Coord(2, 4))!!.possibleMoves())

    val expected4 = setOf(Coord(2, 3), Coord(4, 3), Coord(3, 3))
    assertEquals(expected4, board.getPiece(Coord(3, 4))!!.possibleMoves())

    val expected5 = setOf(Coord(4, 3))
    assertEquals(expected5, board.getPiece(Coord(5, 4))!!.possibleMoves())

    board.movePiece(Coord(0, 1), Coord(0, 3))
    val expected6 = setOf(Coord(0, 2), Coord(1, 2))
    assertEquals(expected6, board.getPiece(Coord(1, 3))!!.possibleMoves())

    val expected13 = setOf<Coord>()
    assertEquals(expected13, board.getPiece(Coord(4, 6))!!.possibleMoves())

    // black
    val expected7 = setOf(Coord(7, 3), Coord(7, 2))
    assertEquals(expected7, board.getPiece(Coord(7, 1))!!.possibleMoves())

    val expected8 = setOf(Coord(6, 3))
    assertEquals(expected8, board.getPiece(Coord(6, 2))!!.possibleMoves())

    val expected9 = setOf<Coord>()
    assertEquals(expected9, board.getPiece(Coord(5, 3))!!.possibleMoves())

    val expected10 = setOf(Coord(3, 4), Coord(4, 4), Coord(5, 4))
    assertEquals(expected10, board.getPiece(Coord(4, 3))!!.possibleMoves())

    val expected11 = setOf(Coord(3, 4))
    assertEquals(expected11, board.getPiece(Coord(2, 3))!!.possibleMoves())

    board.movePiece(Coord(7, 6), Coord(7, 4))
    val expected12 = setOf(Coord(7, 5), Coord(6, 5))
    assertEquals(expected12, board.getPiece(Coord(6, 4))!!.possibleMoves())

    val expected14 = setOf<Coord>()
    assertEquals(expected14, board.getPiece(Coord(3, 1))!!.possibleMoves())
  }
  
}