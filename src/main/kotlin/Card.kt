package chessnt.src.main.kotlin

import java.awt.Color
import javax.swing.BorderFactory
import javax.swing.BoxLayout
import javax.swing.JComponent
import javax.swing.JLabel

abstract class Card(type: String, description: String) : JComponent() {
  init {
    this.setLayout(BoxLayout(this, BoxLayout.Y_AXIS))
    val typeLabel = JLabel(type)
    val descriptionLabel = JLabel(description)
    this.add(typeLabel)
    this.add(descriptionLabel)
    this.setBorder(BorderFactory.createLineBorder(Color.black))
  }
}

class RuleCard(description: String, val rule: Rule) : Card("Rule", description)

class PieceCard(description: String, val piece: Char) : Card("Piece", description)

class GoalCard(description: String, val goal: Goal) : Card("Goal", description)
