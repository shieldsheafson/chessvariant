import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFails
import kotlin.test.assertFailsWith

import ChessUI.src.main.kotlin.Board
import ChessUI.src.main.kotlin.Square
import ChessUI.src.main.kotlin.Coord
import ChessUI.src.main.kotlin.Piece
import ChessUI.src.main.kotlin.King
import ChessUI.src.main.kotlin.Queen
import ChessUI.src.main.kotlin.Rook
import ChessUI.src.main.kotlin.Bishop
import ChessUI.src.main.kotlin.Knight
import ChessUI.src.main.kotlin.Pawn

internal class SquareTest {

  @Test
  fun testGetCoord() {
    val board = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", "KQkq", "-", 8)
    for (i in 0 until 7) {
      for (j in 0 until 7) {
        assertEquals(Coord(i, j), board.getSquare(Coord(i, j)).getCoord())
      }
    }
  }

  @Test
  fun testEquals() {
    
  }
}