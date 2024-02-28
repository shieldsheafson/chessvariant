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

internal class BasicPiece(color: Char, x: Int = 0, y: Int = 0, board: Board, wasPawn: Boolean = false) : Piece(color, x, y, board, wasPawn) {
  override fun possibleMoves(): Set<Coord> {
    return setOf<Coord>()
  }
  override fun toString(): String {
    return "BasicPiece"
  }
}

internal class TestPiece {

  @Test
  fun testGetCoord() {
    val board = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", "KQkq", "-", 8)
    for (i in 0 until 7) {
      for (j in 0 until 7) {
        if (board.getPiece(Coord(i, j)) != null)
        assertEquals(Coord(i, j), board.getPiece(Coord(i, j))?.getCoord())
      }
    }
  }

  @Test
  fun testReturnIllegalMoves() {

    // test out of bounds
    val board1 = Board("k7/8/8/8/8/8/8/K7", "", "-", 8) // have to include a king, otherwise board throws an error
    val testPiece1 = BasicPiece('w', board = board1)
    val moves1 = setOf(Coord(0, 0), Coord(-1, 0), Coord(0, -1), Coord(-1, -1), Coord(4, 4), Coord(7, 7), Coord(7, 8), Coord(8, 7), Coord(8, 8))
    val expected1 = setOf(Coord(-1, 0), Coord(0, -1), Coord(-1, -1), Coord(7, 8), Coord(8, 7), Coord(8, 8)) // all out of bounds
    assertEquals(expected1, testPiece1.returnIllegalMoves(moves1))

    // test same color
    val board2 = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", "KQkq", "-", 8)
    val testPiece2 = BasicPiece('b', board = board2)
    val moves2 = setOf(Coord(0, 0), Coord(1, 1), Coord(7, 7), Coord(6, 6), Coord(4, 4), Coord(5, 5))
    val expected2 = setOf(Coord(0, 0), Coord(1, 1,)) // both black pieces
    assertEquals(expected2, testPiece2.returnIllegalMoves(moves2))
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