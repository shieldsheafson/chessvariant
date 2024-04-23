import chessui.src.main.kotlin.Game
import kotlin.test.Test
import kotlin.test.assertEquals

internal class getAttacksTest {

  @Test
  fun testGetAttacksPawns() {
    val game = Game("1nbqkbnr/p3pppp/8/4p3/3P4/8/P6P/K7 w k - 0 1")

    // white pawns
    assertEquals(setOf(41), game.getAttacks(48))
    assertEquals(setOf(26, 28), game.getAttacks(35))
    assertEquals(setOf(46), game.getAttacks(55))

    // black pawns
    assertEquals(setOf(17), game.getAttacks(8))
    assertEquals(setOf(35, 37), game.getAttacks(28))
    assertEquals(setOf(22), game.getAttacks(15))
  }

  @Test
  fun testGetAttacksRooks() {
    val game = Game("1nbqk1n1/p5pp/3p2r1/2PRp3/3P4/5r2/8/K2R4 w - - 0 1")

    assertEquals(setOf(14, 19, 20, 21, 23, 30, 38, 46, 54, 62), game.getAttacks(22))
    assertEquals(setOf(19, 26, 28, 35), game.getAttacks(27))
    assertEquals(setOf(5, 13, 21, 29, 37, 40, 41, 42, 43, 44, 46, 47, 53, 61), game.getAttacks(45))
    assertEquals(setOf(35, 43, 51, 56, 57, 58, 60, 61, 62, 63), game.getAttacks(59))
  }

  @Test
  fun testGetAttacksKnights() {
    val game = Game("2bqk2n/pn4pp/3p2r1/2PRp3/3Pn3/5r2/3K2N1/N2R4 w - - 0 1")

    assertEquals(setOf(3, 19, 26, 24), game.getAttacks(9))
    assertEquals(setOf(13, 22), game.getAttacks(7))
    assertEquals(setOf(19, 21, 30, 46, 53, 51, 42, 26), game.getAttacks(36))
    assertEquals(setOf(37, 39, 44, 60), game.getAttacks(54))
    assertEquals(setOf(41, 50), game.getAttacks(56))
  }

  @Test
  fun testGetAttacksBishops() {
    val game = Game("2bqk3/1n5p/3p2r1/3R4/3bn3/8/3K2B1/3R3b w - - 0 1")

    assertEquals(setOf(8, 17, 26, 44, 53, 62, 7, 14, 21, 28, 42, 49, 56), game.getAttacks(35))
    assertEquals(setOf(36, 45, 63, 47, 61), game.getAttacks(54))
    assertEquals(setOf(54), game.getAttacks(63))
  }

  @Test
  fun testGetAttacksQueens() {
    val game = Game("2b1k1q1/1n5p/3p2r1/8/3Qn3/1P6/6B1/5K1b w - - 0 1")

    assertEquals(setOf(8, 17, 26, 44, 53, 62, 7, 14, 21, 28, 42, 49, 56, 19, 27, 32, 33, 34, 36, 43, 51, 59), game.getAttacks(35))
    assertEquals(setOf(4, 5, 7, 13, 14, 15, 20, 22, 27, 34, 41), game.getAttacks(6))
  }

  @Test
  fun testGetAttacksKings() {
    val game = Game("2b3qk/1n5p/6r1/3p4/3K4/1P6/6B1/7b w - - 0 1")

    assertEquals(setOf(26, 27, 28, 34, 36, 42, 43, 44), game.getAttacks(35))
    assertEquals(setOf(6, 14, 15), game.getAttacks(7))
  }
}
