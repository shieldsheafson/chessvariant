package ChessUI.src.main.java;

import java.awt.Image;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingWorker;
import javax.swing.GroupLayout.Alignment;

import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.lang.Math;
import java.lang.IllegalArgumentException;

import java.util.Map;
import java.util.HashMap;

import java.util.Set;
import java.util.HashSet;

import static javax.swing.GroupLayout.Alignment.BASELINE;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;

import ChessUI.src.main.kotlin.Game;
import ChessUI.src.main.kotlin.Board;
import ChessUI.src.main.kotlin.Coord;

public class ChessUI extends javax.swing.JFrame {

    /**
     * Creates new form ChessUI
     */
    public ChessUI() {
        initComponents();
        initalizeImageFileNamesToFEN();
        loadimages.execute();

        game = new Game();
        fen = game.getFirstPartOfFEN();

        // Board board = new Board(fen, SQUARESPERSIDE);
        // System.out.println(board.getFirstPartOfFEN());
        // String test = "";
        // String square;
        // for (int i = 0; i < 8; i++) {
        //     for (int j = 0; j < 8; j++) {
        //         square = board.getSquare(new Coord(j, i)).toString();
        //         test += square;
        //         test += " | ";
        //     }
        //     test += "\n";
        // }
        // System.out.println(test);
        // Game game = new Game();
        // squaresLabels[4][4].setIcon(createImage(highlightColorTwo));
    }
    
    private void initalizeImageFileNamesToFEN() {
       imageFileNameToFEN.put("white king.png", 'K');
       imageFileNameToFEN.put("white queen.png", 'Q');
       imageFileNameToFEN.put("white bishop.png", 'B');
       imageFileNameToFEN.put("white knight.png", 'N');
       imageFileNameToFEN.put("white rook.png", 'R');
       imageFileNameToFEN.put("white pawn.png", 'P');
       imageFileNameToFEN.put("black king.png", 'k');
       imageFileNameToFEN.put("black queen.png", 'q');
       imageFileNameToFEN.put("black bishop.png", 'b');
       imageFileNameToFEN.put("black knight.png", 'n');
       imageFileNameToFEN.put("black rook.png", 'r');
       imageFileNameToFEN.put("black pawn.png", 'p');
    }
    
    private void updateUI() {
        char[][] board = fenTo2dCharArray();

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {

                // reset square highlights
                if (game.getSquare(new Coord(i, j)).getColor() == 'w') {
                    getSquareUsingCoord(new Coord(i, j)).setBackground(lightSquareColor);
                } else {
                    getSquareUsingCoord(new Coord(i, j)).setBackground(darkSquareColor);
                }

                // update label icons
                if (FENToIcon.containsKey(board[i][j])) {
                    squaresLabels[i][j].setIcon(FENToIcon.get(board[i][j]));
                } else {
                    squaresLabels[i][j].setIcon(null);
                }

            }
        }
    }

    private char[][] fenTo2dCharArray() {

        // fen is only the first part of a full fen as the gui doesn't need the other game info
        char[][] board = new char[SQUARESPERSIDE][SQUARESPERSIDE];
        String[] initBoardPos = fen.split("/");

        for (int i = 0; i < initBoardPos.length; i++) {
            
            // i is the vertical position in board and in fen
            // k is the horizantal position in board
            // j is the horizantal position in fen
            // must be different since the board always is 8x8
            // but the fen isn't
            // ex rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR
            // is 8 rows, but either 8 cols or 1 col depending on the row

            int k = 0;
            for (int j = 0; j < initBoardPos[i].length(); j++) {
                if (Character.isDigit(initBoardPos[i].charAt(j))) {
                    k += Character.getNumericValue(initBoardPos[i].charAt(j));
                } else {
                    board[i][k] = initBoardPos[i].charAt(j);
                    k++;
                }
            }
        }

        return board;
    }

    private void resizeImages() {
        for (Map.Entry<String, Character> entry : imageFileNameToFEN.entrySet()) {
            ImageIcon unscaledIcon;
            unscaledIcon = createImageIcon(imagedir + entry.getKey());
            ImageIcon icon = new ImageIcon(unscaledIcon.getImage().getScaledInstance(squareSize, squareSize, Image.SCALE_SMOOTH));
            FENToIcon.put(imageFileNameToFEN.get(entry.getKey()), icon);
        }
        FENToIcon.put('h', createCircle(highlightColorTwo));
    }

    private ImageIcon createCircle(Color color) {
        BufferedImage bImg = new BufferedImage(squareSize, squareSize,
            BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bImg.createGraphics();
        RenderingHints rh = new RenderingHints(
             RenderingHints.KEY_TEXT_ANTIALIASING,
             RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHints(rh);
        g2.setColor(color);
        g2.fillOval(squareSize/2 - squareSize/8, squareSize/2 - squareSize/8, squareSize/8*2, squareSize/8*2);
        g2.dispose();
        return new ImageIcon(bImg);
      }

    private Coord getSquareLocation(JPanel square) {
        for (int i = 0; i < SQUARESPERSIDE; i++) {
            for (int j =0; j < SQUARESPERSIDE; j++) {
                if (square == squares[i][j]) {
                    return new Coord(j, i);
                }
            }
        }

        throw new IllegalArgumentException("Square not Found");
    }

    private JPanel getSquareUsingCoord(Coord coord) {
        return squares[coord.getY()][coord.getX()];
    }
    
    private JLabel getLabelUsingCoord(Coord coord) {
        return squaresLabels[coord.getY()][coord.getX()];
    }
    
    public void onSquareClicked(JPanel square) {
        Coord squareCoord = getSquareLocation(square);
        if (currentSquareSelectedPossibleMoves != null && currentSquareSelectedPossibleMoves.contains(squareCoord)) {
            try {
                game.movePiece(currentSquareSelected, squareCoord);
                fen = game.getFirstPartOfFEN();
                updateUI();
                currentSquareSelectedPossibleMoves = null;
            } catch (Exception e) {
                fen = game.getFirstPartOfFEN();
                updateUI();
                currentSquareSelectedPossibleMoves = null;
                System.out.println(e.getMessage());
            }
        } else if (game.getPiece(squareCoord) == null || game.getCurrentPlayer() != game.getPiece(squareCoord).getColor()) {
            currentSquareSelected = null;
            updateUI();
        } else if (currentSquareSelected == null) {
            currentSquareSelected = squareCoord;
            currentSquareSelectedPossibleMoves = game.getPossibleMoves(currentSquareSelected);
            for (Coord coord : currentSquareSelectedPossibleMoves) {
                getLabelUsingCoord(coord).setIcon(FENToIcon.get('h'));
            }
            getSquareUsingCoord(squareCoord).setBackground(highlightColorTwo);
        } else {
            updateUI();
            currentSquareSelected = squareCoord;
            currentSquareSelectedPossibleMoves = game.getPossibleMoves(currentSquareSelected);
            for (Coord coord : currentSquareSelectedPossibleMoves) {
                getLabelUsingCoord(coord).setIcon(FENToIcon.get('h'));
            }
            getSquareUsingCoord(squareCoord).setBackground(highlightColorTwo);
        }
    }

    private SwingWorker<Void, Void> loadimages = new SwingWorker<Void, Void>() {
        
        @Override
        protected Void doInBackground() throws Exception {
            resizeImages();
            return null;
        }   
        
        @Override
        protected void done() {
            updateUI();
        }
    };
    
    protected ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent componentEvent) {
                if (frameWidth != getContentPane().getWidth() || frameHeight != getContentPane().getHeight()) {
                    frameWidth = Math.min(getContentPane().getWidth(), getContentPane().getHeight());
                    frameHeight = frameWidth + frameTitleBarHeight;
                    squareSize = frameWidth / SQUARESPERSIDE;

                    // this may or may not be abuse of a mechanic
                    // it works and does pretty much exactly what I want
                    // but it also seems like it might be considered bad coding practice
                    // hopefully I can find a cleaner solution in another iteration
                    new SwingWorker<Void, Void>() {
                        @Override
                        protected Void doInBackground() throws Exception {
                            resizeImages();
                            return null;
                        }   
                        
                        @Override
                        protected void done() {
                            updateUI();
                        }
                    }.execute();
                    setSize(frameWidth, frameHeight);
                }
            }
        });

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        // each of these groups will contain SQUARESPERSIDE parallel groups
        GroupLayout.SequentialGroup groupOfRows = layout.createSequentialGroup();
        GroupLayout.SequentialGroup groupOfCols = layout.createSequentialGroup();

        // arrays of ParallelGroups of JPanels
        GroupLayout.ParallelGroup[] rowsOfSquares = new GroupLayout.ParallelGroup[SQUARESPERSIDE];
        GroupLayout.ParallelGroup[] colsOfSquares = new GroupLayout.ParallelGroup[SQUARESPERSIDE];

        boolean isWhite = true;
        for (int i = 0; i < SQUARESPERSIDE; i++) {
            rowsOfSquares[i] = layout.createParallelGroup();

            for (int j = 0; j < SQUARESPERSIDE; j++) {

                // create new column if column is not already initialized
                if (colsOfSquares[j] == null) {
                    colsOfSquares[j] = layout.createParallelGroup();
                }
                
                squares[i][j] = new JPanel(new BorderLayout());

                // idk how to tell swing I want my panels to always be squares
                // this works but its dumb, hopefully I can find a better way
                squares[i][j].setPreferredSize(new Dimension(100000, 100000));

                squares[i][j].addMouseListener(new SquareMouseListener());

                squares[i][j].addMouseListener(null);
                squaresLabels[i][j] = new JLabel();

                // center label and add it to panel's layout manager
                squaresLabels[i][j].setHorizontalAlignment(JLabel.CENTER);
                squaresLabels[i][j].setVerticalAlignment(JLabel.CENTER);
                squares[i][j].add(squaresLabels[i][j], BorderLayout.CENTER);

                if (isWhite) {
                    squares[i][j].setBackground(lightSquareColor);
                } else {
                    squares[i][j].setBackground(darkSquareColor);
                }

                // add panel to overall layout
                rowsOfSquares[i].addComponent(squares[i][j]);
                colsOfSquares[j].addComponent(squares[i][j]);

                isWhite = !isWhite;
            }

            // needs to switch colors every new row as well
            // otherwise it is just columns of dark light dark light
            isWhite = !isWhite;

            // since we know each row will contain all the panels it should
            // after each completion of the inner loop
            // we can just add it to the vertical sequential group now
            groupOfRows.addGroup(rowsOfSquares[i]);
        }

        // since the cols will only finished after the above loop has completely finished
        // we must have another loop after to add each finished column to the layout group
        for (int i = 0; i < SQUARESPERSIDE; i++) {
            groupOfCols.addGroup(colsOfSquares[i]);
        }

        layout.setVerticalGroup(groupOfRows);
        layout.setHorizontalGroup(groupOfCols);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        setTitle("Chess");
        pack();
        setSize(frameWidth, frameHeight);
    }

    private static final int frameTitleBarHeight = 28;
    private static final int SQUARESPERSIDE = 8;

    private int frameWidth = 700;
    private int frameHeight = frameWidth + frameTitleBarHeight;
    private int squareSize = frameWidth / SQUARESPERSIDE;

    private final Color darkSquareColor = new Color(174, 138, 105);
    private final Color lightSquareColor = new Color(236, 218, 185);
    private final Color highlightColorOne = new Color(102, 111, 69);
    private final Color highlightColorTwo = new Color(134, 151, 110);
    
    private Map<String, Character> imageFileNameToFEN = new HashMap<>();
    private Map<Character, ImageIcon> FENToIcon = new HashMap<>();

    private JPanel[][] squares = new JPanel[SQUARESPERSIDE][SQUARESPERSIDE];
    private JLabel[][] squaresLabels = new JLabel[SQUARESPERSIDE][SQUARESPERSIDE];
    
    private String imagedir = "/ChessPieces/";

    public String fen = "";

    private Game game;
    private Coord currentSquareSelected;
    private Set<Coord> currentSquareSelectedPossibleMoves;
}
