// import kotlin.test.Test
// import kotlin.test.assertEquals
// import kotlin.test.assertTrue
// import kotlin.test.assertFails
// import kotlin.test.assertNotEquals

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

// internal class PawnTest {

//   @Test
//   fun testToString() {
//     // the board isn't called in toString() so we don't need multiple
//     val board = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", 8)
//     assertEquals("P", Pawn('w', 0, 0, board).toString())
//     assertEquals("p", Pawn('b', 0, 0, board).toString())
//   }

//   @Test
//   fun testEquals() {
//     val board = Board("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", 8)
//     val otherBoard = Board("rnbqkbnr/pppp1ppp/8/4p3/4P3/8/PPPP1PPP/RNBQKBNR", 8)

//     // self equality
//     assertEquals<Piece>(Pawn('w', 0, 0, board), Pawn('w', 0, 0, board))

//     // should be equal regardless of board
//     assertEquals<Piece>(Pawn('w', 0, 0, board), Pawn('w', 0, 0, otherBoard))
//     assertEquals<Piece>(Pawn('w', 0, 0, otherBoard), Pawn('w', 0, 0, board))

//     assertNotEquals<Piece?>(null, Pawn('w', 0, 0, board))
//     assertNotEquals<Piece?>(Pawn('w', 0, 0, board), null)
//     assertNotEquals<Piece>(Pawn('w', 0, 0, board), Pawn('b', 0, 0, board))
//     assertNotEquals<Piece>(Pawn('b', 0, 0, board), Pawn('w', 0, 0, board))
//     assertNotEquals<Piece>(Pawn('w', 1, 0, board), Pawn('w', 0, 0, board))
//     assertNotEquals<Piece>(Pawn('w', 0, 0, board), Pawn('w', 1, 0, board))
//     assertNotEquals<Piece>(Pawn('w', 0, 1, board), Pawn('w', 0, 0, board))
//     assertNotEquals<Piece>(Pawn('w', 0, 0, board), Pawn('w', 0, 1, board))
//     assertNotEquals<Piece>(Knight('w', 0, 0, board), Pawn('w', 0, 0, board))
//     assertNotEquals<Piece>(Pawn('w', 0, 0, board), Knight('w', 0, 0, board))
//   }
// }