package chessnt.src.main.kotlin

import java.awt.GridBagConstraints
import java.awt.GridBagLayout
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
    test.setLayout(GridBagLayout())
    val constraints = GridBagConstraints()
    constraints.gridx = 0
    constraints.gridy = 0
    constraints.fill = GridBagConstraints.BOTH
    constraints.anchor = GridBagConstraints.FIRST_LINE_START
    constraints.weightx = 1.0
    constraints.weighty = 1.0
    // test.setLayout(BoxLayout(test, BoxLayout.X_AXIS))
    // val card = RuleCard(
    //   "Every Piece may move to A7",
    //   object : Rule() {
    //     override val modifiesGetPossibleMoves = true
    //     override val modifiesMovePiece = true
    //     override fun getPossibleMovesModifer(piece: Piece, currentPossibleMoves: MutableSet<Int>, currentFEN: String) {
    //       currentPossibleMoves += 0
    //     }
    //   }
    // )
    // game.rules += card.rule
    val chessui = ChessUI(game)

    test.add(chessui, constraints)
    // test.add(card)

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
  }
}
