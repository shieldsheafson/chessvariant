package chessnt.src.main.kotlin

import chessnt.src.main.java.SingleComponentAspectRatioKeeperLayout
import org.apache.batik.anim.dom.SAXSVGDocumentFactory
import org.apache.batik.anim.dom.SVGDOMImplementation
import org.apache.batik.anim.dom.SVGOMDocument
import org.apache.batik.swing.JSVGCanvas
import org.apache.batik.util.XMLResourceDescriptor
import java.awt.BorderLayout
import java.awt.Color
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.io.IOException
import javax.swing.JComponent
import javax.swing.JPanel

class ChessUI(val game: Game) : JComponent() {
  companion object {
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

  private var previouslySelected: Pair<Int, Int>? = null
  private var currentlySelected: Int? = null
  private var currentlySelectedPossibleMoves = setOf<Int>()

  private val squares = Array<JSVGCanvas>(SQUARESPERSIDE * SQUARESPERSIDE) { JSVGCanvas() }

  init {
    this.setLayout(BorderLayout())

    val boardPanel = JPanel()
    boardPanel.setLayout(GridBagLayout())

    // wrapper panel maintains a square aspect ratio
    val wrapperPanel = JPanel(SingleComponentAspectRatioKeeperLayout())
    wrapperPanel.add(boardPanel)
    this.add(wrapperPanel)

    // both weigths are one cause every square should have an equal weight
    // and if I don't give a weight the squares don't resize correctly
    val constraints = GridBagConstraints()
    constraints.weightx = 1.0
    constraints.weighty = 1.0
    constraints.fill = GridBagConstraints.BOTH

    for (i in squares.indices) {
      val square = squares[i]
      val piece = game.getPieceAt(i)

      val row = i / SQUARESPERSIDE
      val col = i % SQUARESPERSIDE

      // place square in grid
      constraints.gridx = col
      constraints.gridy = row

      // add piece image to square
      if (piece.isEmpty) {
        square.setURI(null)
      } else {
        square.setURI(
          object {}.javaClass.getResource(
            "/$IMAGEDIR/${fenToFileName(piece.fenChar)}",
          ).file.toString(),
        )
      }

      // color square background
      if (squareIsWhite(i)) {
        square.setBackground(LIGHTSQUARECOLOR)
      } else {
        square.setBackground(DARKSQUARECOLOR)
      }

      // click stuff
      square.addMouseListener(
        object : MouseListener {
          override fun mouseClicked(e: MouseEvent) {}

          override fun mouseEntered(e: MouseEvent) {}

          override fun mouseExited(e: MouseEvent) {}

          override fun mousePressed(e: MouseEvent) {}

          override fun mouseReleased(e: MouseEvent) {
            onClick(row, col)
          }
        },
      )

      boardPanel.add(square, constraints)
    }
  }

  private fun onClick(row: Int, col: Int) {
    val clicked = row * SQUARESPERSIDE + col

    if (clicked in currentlySelectedPossibleMoves) {
      // move piece
      game.movePiece(currentlySelected!!, clicked)
      previouslySelected = Pair(currentlySelected!!, clicked)
      currentlySelected = null
    } else if (game.getPieceAt(clicked).color == game.currentPlayer) {
      // select piece
      currentlySelected = clicked
      currentlySelectedPossibleMoves = game.getPossibleMoves(clicked)
    } else {
      // deselect
      currentlySelected = null
      currentlySelectedPossibleMoves = setOf<Int>()
    }

    updateChessUI()
  }

  private fun updateChessUI() {
    for (i in squares.indices) {
      val square = squares[i]
      val piece = game.getPieceAt(i)

      // update image
      if (piece.isEmpty) {
        square.setURI(null)
      } else {
        square.setURI(
          object {}.javaClass.getResource(
            "/$IMAGEDIR/${fenToFileName(piece.fenChar)}",
          ).file.toString(),
        )
      }

      // color square background
      if (squareIsWhite(i)) {
        square.setBackground(LIGHTSQUARECOLOR)
      } else {
        square.setBackground(DARKSQUARECOLOR)
      }
    }

    // highlight previously selected piece
    if (previouslySelected != null) {
      if (squareIsWhite(previouslySelected!!.first)) {
        squares[previouslySelected!!.first].setBackground(HIGHLIGHTCOLORTHREE)
      } else {
        squares[previouslySelected!!.first].setBackground(HIGHLIGHTCOLORFOUR)
      }
      if (squareIsWhite(previouslySelected!!.second)) {
        squares[previouslySelected!!.second].setBackground(HIGHLIGHTCOLORTHREE)
      } else {
        squares[previouslySelected!!.second].setBackground(HIGHLIGHTCOLORFOUR)
      }
    }

    // highlights for selected piece
    if (currentlySelected != null) {
      // hightlight selected piece
      if (squareIsWhite(currentlySelected!!)) {
        squares[currentlySelected!!].setBackground(HIGHLIGHTCOLORTWO)
      } else {
        squares[currentlySelected!!].setBackground(HIGHLIGHTCOLORONE)
      }

      // highlight selected piece's possible moves
      for (move in currentlySelectedPossibleMoves) {
        if (game.getPieceAt(move).isEmpty) {
          if (squareIsWhite(move)) {
            squares[move].setURI(HIGHLIGHTCOLORTWOCIRCLE)
          } else {
            squares[move].setURI(HIGHLIGHTCOLORONECIRCLE)
          }
        } else {
          highlightWithInverseCircle(move)
        }
      }
    }

    // highlight king if in check
    if (game.inCheck(game.currentPlayer)) {
      squares[game.getKingLocation(game.currentPlayer)].setBackground(RED)
    }
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
    val piece = game.getPieceAt(squareNum)
    if (piece.color == null) {
      throw IllegalArgumentException("Implement Later")
    }
    if (!squareIsWhite(currentlySelected!!)) {
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
}
