package ChessUI.src.main.java;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingUtilities;

public class SquareMouseListener implements MouseListener {

  @Override
  public void mouseClicked(MouseEvent e) {
    JPanel panelClicked = (JPanel) e.getSource();
    ChessUI frame = (ChessUI) SwingUtilities.getWindowAncestor(panelClicked);
    frame.onSquareClicked(panelClicked);
  }

  @Override
  public void mouseEntered(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseExited(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mousePressed(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public void mouseReleased(MouseEvent e) {
    // TODO Auto-generated method stub
    
  }
}