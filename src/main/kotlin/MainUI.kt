package chessui.src.main.kotlin

import javax.swing.BoxLayout
import javax.swing.JFrame
import javax.swing.JPanel

class MainUI(val game: Game) : JFrame() {
  companion object {
    private const val INITFRAMEWIDTH = 750
    private const val FRAMETITLEBARHEIGHT = 28
  }

  private var frameWidth = INITFRAMEWIDTH
  private var frameHeight = INITFRAMEWIDTH + FRAMETITLEBARHEIGHT

  init {
    val test = JPanel()
    test.setLayout(BoxLayout(test, BoxLayout.X_AXIS))
    val card = RuleCard(
      "Every Piece may move to A7",
      object : Rule() {
        override val modifiesGetPossibleMoves = true
        override val modifiesMovePiece = true
        override fun getPossibleMovesModifer(piece: Piece, currentPossibleMoves: MutableSet<Int>, currentFEN: String) {
          currentPossibleMoves += 0
        }
      }
    )
    game.rules += card.rule
    val chessui = ChessUI(game)
    test.add(chessui)
    test.add(card)

    this.add(test)

    // // auto adjust to square on resize
    // this.addComponentListener(
    //   object : ComponentAdapter() {
    //     override fun componentResized(componentEvent: ComponentEvent) {
    //       if (frameWidth != getContentPane().getWidth() || frameHeight != getContentPane().getHeight()) {
    //         frameWidth = Math.min(getContentPane().getWidth(), getContentPane().getHeight())
    //         frameHeight = frameWidth + FRAMETITLEBARHEIGHT
    //         setSize(frameWidth, frameHeight)
    //         chessui.adjustSize(getContentPane().getWidth() / 2, getContentPane().getHeight() / 2)
    //       }
    //     }
    //   },
    // )

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE)
    setTitle("Chessn't")
    pack()
    setVisible(true)
    setSize(frameWidth, frameHeight)
  }
}
