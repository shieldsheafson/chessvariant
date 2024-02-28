import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

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

internal class BasicPiece(color: Char, x: Int, y: Int, board: Board, wasPawn: Boolean = false) : Piece(color, x, y, board, wasPawn) {
  override fun possibleMoves(): Set<Coord> {
    return setOf<Coord>()
  }
  override fun toString(): String {
    return "BasicPiece"
  }
}

internal class TestPiece {

  @Test
  fun testReturnIllegalMoves() {

  }

  @Test
  fun testEquals() {
    val board = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", "KQkq", "-", 8)

    assertEquals(Pawn('w', 0, 0, board), Pawn('w', 0, 0, board))
    assertNotEquals(Pawn('w', 0, 0, board), Pawn('w', 1, 0, board))
    assertNotEquals(Pawn('w', 0, 0, board), Pawn('w', 0, 1, board))
    assertNotEquals(Pawn('w', 0, 0, board), Pawn('b', 0, 0, board))
    assertNotEquals<Piece>(Pawn('w', 0, 0, board), Knight('w', 0, 0, board))
    assertNotEquals<Piece?>(Pawn('w', 0, 0, board), null)

    assertEquals(Knight('w', 0, 0, board), Knight('w', 0, 0, board))
    assertNotEquals(Knight('w', 0, 0, board), Knight('w', 1, 0, board))
    assertNotEquals(Knight('w', 0, 0, board), Knight('w', 0, 1, board))
    assertNotEquals(Knight('w', 0, 0, board), Knight('b', 0, 0, board))
    assertNotEquals<Piece>(Knight('w', 0, 0, board), Pawn('w', 0, 0, board))
    assertNotEquals<Piece?>(Knight('w', 0, 0, board), null)

    assertEquals(Bishop('w', 0, 0, board), Bishop('w', 0, 0, board))
    assertNotEquals(Bishop('w', 0, 0, board), Bishop('w', 1, 0, board))
    assertNotEquals(Bishop('w', 0, 0, board), Bishop('w', 0, 1, board))
    assertNotEquals(Bishop('w', 0, 0, board), Bishop('b', 0, 0, board))
    assertNotEquals<Piece>(Bishop('w', 0, 0, board), Pawn('w', 0, 0, board))
    assertNotEquals<Piece?>(Bishop('w', 0, 0, board), null)

    assertEquals(Rook('w', 0, 0, board), Rook('w', 0, 0, board))
    assertNotEquals(Rook('w', 0, 0, board), Rook('w', 1, 0, board))
    assertNotEquals(Rook('w', 0, 0, board), Rook('w', 0, 1, board))
    assertNotEquals(Rook('w', 0, 0, board), Rook('b', 0, 0, board))
    assertNotEquals<Piece>(Rook('w', 0, 0, board), Pawn('w', 0, 0, board))
    assertNotEquals<Piece?>(Rook('w', 0, 0, board), null)

    assertEquals(Queen('w', 0, 0, board), Queen('w', 0, 0, board))
    assertNotEquals(Queen('w', 0, 0, board), Queen('w', 1, 0, board))
    assertNotEquals(Queen('w', 0, 0, board), Queen('w', 0, 1, board))
    assertNotEquals(Queen('w', 0, 0, board), Queen('b', 0, 0, board))
    assertNotEquals<Piece>(Queen('w', 0, 0, board), Pawn('w', 0, 0, board))
    assertNotEquals<Piece?>(Queen('w', 0, 0, board), null)

    assertEquals(King('w', 0, 0, board), King('w', 0, 0, board))
    assertNotEquals(King('w', 0, 0, board), King('w', 1, 0, board))
    assertNotEquals(King('w', 0, 0, board), King('w', 0, 1, board))
    assertNotEquals(King('w', 0, 0, board), King('b', 0, 0, board))
    assertNotEquals<Piece>(King('w', 0, 0, board), Pawn('w', 0, 0, board))
    assertNotEquals<Piece?>(King('w', 0, 0, board), null)
  }

}