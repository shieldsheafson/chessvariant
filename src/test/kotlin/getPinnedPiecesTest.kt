import chessui.src.main.kotlin.Game
import kotlin.test.Test
import kotlin.test.assertEquals

internal class getPinnedPiecesTest {

  @Test
  fun testGetPinnedPieces() {
    val game = Game("3r3k/6bp/np6/2BpN3/r1PKr2q/2bQP3/8/b5b1 w - - 0 1")

    assertEquals(setOf(34, 28, 44), game.getPinnedPieces(35))
  }
}
