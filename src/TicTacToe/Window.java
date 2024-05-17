package TicTacToe;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import ArtificialIntelligence.*;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class Window extends JFrame {

    private static final int WIDTH = 600;   // width of the run window
    private static final int HEIGHT = 643;  // height of the run window
    private Board board;                                       // variable for board
    private Panel panel;                                       // variable for panel
    private BufferedImage imageBackground, imageX, imageO;     // variables of three images

    private enum Mode {             // enum contain two variables of play state
        Player, AI
    }
    private Mode mode;              // variable mode

    /*
      the center location of each of the cells is stored here! and identifying which cell the player has clicked on!
     */
    
    private Point[] cells;

    
    private static final int DISTANCE = 100; // the distance away from the center of a cell that will register as a click on this cell

    /*
      construct the run window
     */
    private Window() {
        this(Mode.AI);
    }

    /*
      Construct the run window
     
     * @param mode the game mode (Player vs. Player or Player vs. AI)
     */
    private Window(Mode mode) {
        this.mode = mode;
        board = new Board();
        loadCells();
        panel = createPanel();
        setWindowProperties();
        loadImages();
    }

    /*
      Load the locations of the center of each of the 9 cells from 0 to 8
     */
    private void loadCells() {
        cells = new Point[9];

        cells[0] = new Point(109, 109);
        cells[1] = new Point(299, 109);
        cells[2] = new Point(489, 109);
        cells[3] = new Point(109, 299);
        cells[4] = new Point(299, 299);
        cells[5] = new Point(489, 299);
        cells[6] = new Point(109, 489);
        cells[7] = new Point(299, 489);
        cells[8] = new Point(489, 489);
    }

    /*
      Set the size, title, visibility etc... for the run window
     */
    private void setWindowProperties() {
        setResizable(false);
        pack();
        setTitle("X-O Game Play By AI");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    /*
      Create the panel that will be used for drawing Tic Tac Toe to the screen.
     
      @return the panel with the specified dimensions and mouse listener
     */
    private Panel createPanel() {
        Panel panel = new Panel();
        Container cp = getContentPane();
        cp.add(panel);
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.addMouseListener(new MyMouseAdapter());
        return panel;
    }

    /*
      Load the image of the background.png and the images of the X.png and O.png
     */
    private void loadImages() {
        imageBackground = getImage("background");
        imageX = getImage("x");
        imageO = getImage("o");
    }

    /*
      helper method for grabbing the images from the disk and throw execption if it doesn't loaded
     
     * @param path the name of the image
     * @return the image that was grabbed
     */
    private static BufferedImage getImage(String path) {

        BufferedImage image;

        try {
            path = ".." + File.separator + "Assets" + File.separator + path + ".png";
            image = ImageIO.read(Window.class.getResource(path));
        } catch (IOException ex) {
            throw new RuntimeException("Image could not be loaded!");
        }

        return image;
    }

    /*
      used for drawing X-O to the screen
     */
    private class Panel extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            paintTicTacToe((Graphics2D) g);
        }

        /*
          The main painting method that paints everything in screen
         
         * @param g the Graphics object that will perform the panting
         */
        private void paintTicTacToe(Graphics2D g) {
            setProperties(g);
            paintBoard(g);
            paintWinner(g);
        }

        /*
          Set the rendering hints of the Graphics object
         
         * @param g the Graphics object to set the rendering hints on
         */
        private void setProperties(Graphics2D g) {
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g.drawImage(imageBackground, 0, 0, null);

            // The first time a string is drawn it tends to lag.
            // Drawing something trivial at the beginning loads the font up.
            // Better to lag at the beginning than during the middle of the game.
            g.drawString("", 0, 0);
        }

        /*
          Paints the background image and the X's and O's
         
         * @param g the Graphics object that will perform the panting
         */
        private void paintBoard(Graphics2D g) {
            Board.State[][] boardArray = board.toArray();

            int offset = 20;

            for (int y = 0; y < 3; y++) {
                for (int x = 0; x < 3; x++) {
                    if (boardArray[y][x] == Board.State.X) {
                        g.drawImage(imageX, offset + 190 * x, offset + 190 * y, null);
                    } else if (boardArray[y][x] == Board.State.O) {
                        g.drawImage(imageO, offset + 190 * x, offset + 190 * y, null);
                    }
                }
            }
        }

        /*
          Paints who won to the screen or Balance in the center of window
         
         * @param g the Graphics object that will perform the panting
         */
        private void paintWinner(Graphics2D g) {
            if (board.isGameOver()) {
                g.setColor(new Color(255, 255, 0));
                g.setFont(new Font("Playfair Display", Font.PLAIN, 50));

                String s;

                if (board.getWinner() == Board.State.Blank) {
                    s = "Balance!";
                } else {
                    s = board.getWinner() + " Wins!";
                }

                g.drawString(s, 300 - getFontMetrics(g.getFont()).stringWidth(s) / 2, 315);

            }
        }
    }

    /*
      for detecting the mouse clicks
     */
    private class MyMouseAdapter extends MouseAdapter {

        @Override
        public void mousePressed(MouseEvent e) {
            super.mouseClicked(e);

            if (board.isGameOver()) {
                board.reset();
                panel.repaint();
            } else {
                playMove(e);
            }

        }

        /*
           plays the move that the user clicks, if the move is valid.
         
         * @param e the MouseEvent that the user performed
         */
        private void playMove(MouseEvent e) {
            int move = getMove(e.getPoint());

            if (!board.isGameOver() && move != -1) {
                boolean validMove = board.move(move);
                if (mode == Mode.AI && validMove && !board.isGameOver()) {
                    Algorithms.alphaBetaAdvanced(board);
                }
                panel.repaint();
            }
        }

        /*
            translate the mouse click position to an index on the board.
     
         */
        private int getMove(Point point) {
            for (int i = 0; i < cells.length; i++) {
                if (distance(cells[i], point) <= DISTANCE) {
                    return i;
                }
            }
            return -1;
        }

        /*
          Distance between two points, Used for determining if the player has
          pressed on a cell to play a move
         */
        private double distance(Point p1, Point p2) {
            double xDiff = p1.getX() - p2.getX();
            double yDiff = p1.getY() - p2.getY();

            double xDiffSquared = xDiff * xDiff;
            double yDiffSquared = yDiff * yDiff;

            return Math.sqrt(xDiffSquared + yDiffSquared);
        }
    }

    public static void main(String[] args) throws StaleProxyException {           // main method
           
        
        if (args.length == 1) {
            System.out.println("Game Play Mode: Player VS Player");
            SwingUtilities.invokeLater(() -> new Window(Mode.Player));
        } else {
            System.out.println("Game Play Mode: Player VS AI");
            SwingUtilities.invokeLater(() -> new Window(Mode.AI));
        }
        
        
        jade.core.Runtime r = jade.core.Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController main1 = r.createMainContainer(p);
        
        AgentController rma = main1.createNewAgent("jade", "jade.tools.rma.rma", null);
        rma.start();
        
        AgentController Player = main1.createNewAgent("Player", "TicTacToe.Player_Agent", null);
        Player.start();
        
        AgentController AI = main1.createNewAgent("AI", "TicTacToe.AI_Agent", null);
        AI.start();
        

    }

}
