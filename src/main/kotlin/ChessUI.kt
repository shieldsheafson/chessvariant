package chessui.src.main.kotlin

import org.apache.batik.anim.dom.SAXSVGDocumentFactory
import org.apache.batik.anim.dom.SVGDOMImplementation
import org.apache.batik.anim.dom.SVGOMDocument
import org.apache.batik.swing.JSVGCanvas
import org.apache.batik.util.XMLResourceDescriptor
import java.awt.Color
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.IOException
import javax.swing.JComponent

class ChessUI(val game: Game) : JComponent() {
  companion object {
    private const val INITFRAMEWIDTH = 750
    private const val FRAMETITLEBARHEIGHT = 28

    private val DARKSQUARECOLOR = Color(174, 138, 105)
    private val LIGHTSQUARECOLOR = Color(236, 218, 185)
    private val HIGHLIGHTCOLORONE = Color(102, 111, 69)
    private val HIGHLIGHTCOLORTWO = Color(134, 151, 110)
    private val HIGHLIGHTCOLORTHREE = Color(206, 210, 120)
    private val HIGHLIGHTCOLORFOUR = Color(168, 162, 76)
    private val RED = Color(220, 20, 60)

    private const val IMAGEDIR = "ChessPieces"

    private val HIGHLIGHTCOLORONECIRCLE = object {}.javaClass.getResource("/$IMAGEDIR/circle1.svg").file.toString()
    private val HIGHLIGHTCOLORTWOCIRCLE = object {}.javaClass.getResource("/$IMAGEDIR/circle2.svg").file.toString()
  }

  private var frameWidth = INITFRAMEWIDTH / 2
  private var frameHeight = (INITFRAMEWIDTH + FRAMETITLEBARHEIGHT) / 2
  private var squareSize = (INITFRAMEWIDTH / SQUARESPERSIDE) / 2

  private var currentBoardPos = game.board

  private var previouslySelected: Pair<Int, Int>? = null
  private var previouslySelectedPossibleMoves = setOf<Int>()
  private var selectedSquareNum: Int? = null
  private var selectedSquarePossibleMoves = setOf<Int>()

  private val squares = Array<JSVGCanvas>(SQUARESPERSIDE * SQUARESPERSIDE) { JSVGCanvas() }

  init {

    // click stuff
    val getClicks = object : JComponent() {}
    getClicks.setBounds(0, 0, squareSize * SQUARESPERSIDE, squareSize * SQUARESPERSIDE)
    this.add(getClicks)
    getClicks.addMouseListener(
      object : MouseListener {
        override fun mouseClicked(e: MouseEvent) {}

        override fun mouseEntered(e: MouseEvent) {}

        override fun mouseExited(e: MouseEvent) {}

        override fun mousePressed(e: MouseEvent) {}

        override fun mouseReleased(e: MouseEvent) {
          val squareNum = e.getY() / squareSize * SQUARESPERSIDE + e.getX() / squareSize

          if (squareNum > SQUARESPERSIDE * SQUARESPERSIDE - 1 || squareNum < 0) {
            return
          }
          if (currentBoardPos == game.board && selectedSquareNum == squareNum) {
            // purely so you can't spam click one square and cause the ui to glitch out
            // the good solution would be to optimize updateChessUI
            // but thats hard so I'll do it later
            return
          } else if (squareNum in selectedSquarePossibleMoves) {
            game.movePiece(selectedSquareNum!!, squareNum)
            previouslySelected = Pair(selectedSquareNum!!, squareNum)
            previouslySelectedPossibleMoves = selectedSquarePossibleMoves
            selectedSquareNum = null
            selectedSquarePossibleMoves = setOf<Int>()
          } else if (currentBoardPos[squareNum].color == game.currentPlayer) {
            selectedSquareNum = squareNum
            selectedSquarePossibleMoves = game.getPossibleMoves(squareNum)
          } else {
            selectedSquareNum = null
            selectedSquarePossibleMoves = setOf<Int>()
          }

          updateChessUI()
        }
      },
    )

    updateChessUI()
  }

  public fun adjustSize(newWidth: Int, newHeight: Int) {
    frameWidth = Math.min(newWidth, newHeight)
    frameHeight = frameWidth + FRAMETITLEBARHEIGHT
    squareSize = frameWidth / SQUARESPERSIDE
    setSize(frameWidth, frameHeight)
    updateChessUI()
  }

  private fun fenToFileName(fenChar: Char): String {
    return when (fenChar) {
      'k' -> "bK.svg"
      'q' -> "bQ.svg"
      'r' -> "bR.svg"
      'n' -> "bN.svg"
      'b' -> "bB.svg"
      'p' -> "bP.svg"
      'K' -> "wK.svg"
      'Q' -> "wQ.svg"
      'R' -> "wR.svg"
      'N' -> "wN.svg"
      'B' -> "wB.svg"
      'P' -> "wP.svg"
      else -> throw IllegalArgumentException("Invalid FEN character: $fenChar")
    }
  }

  private fun squareIsWhite(squareNum: Int): Boolean {
    return squareNum % 2 == 0
  }

  private fun highlightWithInverseCircle(squareNum: Int) {
    // some truly awful awful code, but I really just wanted it to work
    // I shall come back and clean it up eventually
    val piece = currentBoardPos[squareNum]
    if (piece.color == null) {
      throw IllegalArgumentException("Implement Later")
    }
    if (!squareIsWhite(selectedSquareNum!!)) {
      squares[squareNum].setBackground(DARKSQUARECOLOR)
    } else {
      squares[squareNum].setBackground(LIGHTSQUARECOLOR)
    }
    try {
      val svgNS = SVGDOMImplementation.SVG_NAMESPACE_URI
      val parser = XMLResourceDescriptor.getXMLParserClassName()
      val f = SAXSVGDocumentFactory(parser)
      val uri = object {}.javaClass.getResource("/$IMAGEDIR/${fenToFileName(piece.fenChar)}").file.toString()
      val doc = f.createDocument(uri)
      val svgRoot = doc.getDocumentElement()
      for (i in listOf("M 0 0 L 0 10 A 25 25 0 0 1 10 0 z", "M 45 0 L 45 10 A 25 25 0 0 0 35 0 z", "M 45 45 L 45 35 A 25 25 0 0 1 35 45 z", "M 0 45 L 0 35 A 25 25 0 0 0 10 45 z")) {
        val path = doc.createElementNS(svgNS, "path")
        path.setAttributeNS(null, "d", i)
        if (squareIsWhite(squareNum)) {
          path.setAttributeNS(null, "fill", "rgb(134, 151, 110)")
        } else {
          path.setAttributeNS(null, "fill", "rgb(102, 111, 69)")
        }
        svgRoot.appendChild(path)
      }
      squares[squareNum].setSVGDocument(doc as SVGOMDocument)
    } catch (ex: IOException) {
      throw ex
    }
  }

  private fun updateChessUI() {
    currentBoardPos = game.board

    // did it with absolute positioning cause I am a silly little guy
    // who couldn't figure out how to make layout mangers do what he wanted
    for (i in 0 until SQUARESPERSIDE) {
      for (j in 0 until SQUARESPERSIDE) {
        val currentSquareNum = j * 7 + i
        val square = squares[currentSquareNum]

        // add piece image to GUI
        if (currentBoardPos[currentSquareNum].color != null) {
          square.setURI(
            object {}.javaClass.getResource(
              "/$IMAGEDIR/${fenToFileName(currentBoardPos[currentSquareNum].fenChar)}",
            ).file.toString(),
          )
        } else {
          square.setURI(null)
        }

        if (squareIsWhite(currentSquareNum)) square.setBackground(LIGHTSQUARECOLOR) else square.setBackground(DARKSQUARECOLOR)

        square.setBounds(i * squareSize, j * squareSize, squareSize, squareSize)
        this.add(square)
      }
    }

    // highlights of previously selected piece
    if (previouslySelected != null) {
      if (!squareIsWhite(previouslySelected!!.first)) {
        squares[previouslySelected!!.first].setBackground(HIGHLIGHTCOLORFOUR)
      } else {
        squares[previouslySelected!!.first].setBackground(HIGHLIGHTCOLORTHREE)
      }
      if (!squareIsWhite(previouslySelected!!.second)) {
        squares[previouslySelected!!.second].setBackground(HIGHLIGHTCOLORFOUR)
      } else {
        squares[previouslySelected!!.second].setBackground(HIGHLIGHTCOLORTHREE)
      }
    }

    // highlights of selected piece
    if (selectedSquareNum != null) {
      if (squareIsWhite(selectedSquareNum!!)) {
        squares[selectedSquareNum!!].setBackground(HIGHLIGHTCOLORTWO)
      } else {
        squares[selectedSquareNum!!].setBackground(HIGHLIGHTCOLORONE)
      }

      val possibleMoves = selectedSquarePossibleMoves
      for (move in possibleMoves) {
        if (currentBoardPos[move].color == null) {
          if (!squareIsWhite(move)) squares[move].setURI(HIGHLIGHTCOLORONECIRCLE) else squares[move].setURI(HIGHLIGHTCOLORTWOCIRCLE)
        } else {
          highlightWithInverseCircle(move)
        }
      }
    }

    if (game.inCheck(game.currentPlayer)) {
      squares[game.board.getKingLocation(game.currentPlayer)].setBackground(RED)
    }
    // for (i in game.board.getPieceLocations()) {
    //   squares[i].setBackground(Color(173, 192, 222))
    // }
    // for (i in game.attacks[BLACK]!!) {
    //   squares[i].setBackground(Color(240, 146, 163))
    // }
    // for (i in game.getPinnedPieces(game.board.getKingLocation(WHITE))) {
    //   squares[i].setBackground(Color(115, 6, 25))
    // }
  }
}
