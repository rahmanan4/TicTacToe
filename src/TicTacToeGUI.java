import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import static java.util.Map.entry;


public class TicTacToeGUI {
    // create a frame aka a window
    private final JFrame gameFrame;
    // create a board panel which is a class defined below
    private final BoardPanel boardPanel;
    private JLabel playerTurn = new JLabel();
    private JLabel gameStatus = new JLabel();

    private Board ticTacToeBoard;

    public TicTacToeGUI(Board b) {
        this.ticTacToeBoard = b;

        // actually instantiate a JFrame to the JFrame being held in the TicTacToeGUI class
        this.gameFrame = new JFrame(Constants.TITLE);

        // adds a border layout where you can then throw various things at various locations of the board.
        this.gameFrame.setLayout(new BorderLayout());

        // adds the menu bar to the game window
        this.gameFrame.setJMenuBar(createMenuBar());

        // make the window dimensions the size of OUTER_FRAME_DIMENSION
        this.gameFrame.setSize(Constants.OUTER_FRAME_DIMENSION);

        this.boardPanel = new BoardPanel();
        // Throws the board panel in the center of the window
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        playerTurn.setText("Player " + ticTacToeBoard.getCurrMove() + "'s Move");
        this.gameFrame.add(playerTurn, BorderLayout.NORTH);

        gameStatus.setText("Is game over: " + ticTacToeBoard.isGameOver());
        this.gameFrame.add(gameStatus, BorderLayout.SOUTH);

        // allows you to see the board
        this.gameFrame.setVisible(true);

    }

    private JMenuBar createMenuBar(){
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;
    }

    private JMenu createFileMenu(){
        // create file tab within menu bar
        final JMenu fileMenu = new JMenu(Constants.JMENU_TAB1_NAME);
        // create exit option underneath file tab
        final JMenuItem exit = new JMenuItem(Constants.JMENU_TAB1_JMENU_ITEM1);
        // create action listener to perform some function aka close the program
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        // add the exit JMenuItem object to the JMenu object
        fileMenu.add(exit);
        return fileMenu;
    }

    public Board getBoard() {
        return ticTacToeBoard;
    }

    private class BoardPanel extends JPanel{
        // store all TilePanels into a list of Tile Panels called boardTiles
        final List<TilePanel> boardTiles;

        BoardPanel(){
            super(new GridLayout(Constants.ROW_DIMENSION, Constants.COL_DIMENSION));
            // stores boardTiles in ArrayList
            this.boardTiles = new ArrayList<>();
            // Tic Tac Toe will have 9 tiles, so create 9 TilePanels
            for(int i = 0; i < Constants.NUM_TILES; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                // first add the tilePanel to the ArrayList, then add it to the frame (window)
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            // set the dimensions of the board, where board dimensions is less than or equal to outer frame
            setPreferredSize(Constants.BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class TilePanel extends JPanel{
        // when BoardPanel constructor is called, it feeds i from 0 to 8 as the tile id
        private final int tileId;
        private final Map<Integer, String> mapping = Map.ofEntries(
                entry(0, "00"),
                entry(1, "01"),
                entry(2, "02"),
                entry(3, "10"),
                entry(4, "11"),
                entry(5, "12"),
                entry(6, "20"),
                entry(7, "21"),
                entry(8, "22")
        );
        private final ImageIcon iconX;
        private final ImageIcon iconO;

        TilePanel(final BoardPanel boardPanel, final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(Constants.TILE_PANEL_DIMENSION);
            assignTileColor();
            iconX = createTileIcon(Constants.PLAYER_ONE_SIGN);
            iconO = createTileIcon(Constants.PLAYER_TWO_SIGN);

            addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    char currMove;
                    boolean isValid;
                    if (ticTacToeBoard.getCurrMove() == 1){
                        currMove = Constants.PLAYER_ONE_SIGN;
                        isValid = ticTacToeBoard.move(currMove, mapping.get(tileId));
                        if (isValid){
                            assignTileIcon(iconX);
                        }
                    }
                    else {
                        currMove = Constants.PLAYER_TWO_SIGN;
                        isValid = ticTacToeBoard.move(currMove, mapping.get(tileId));
                        if (isValid){
                            assignTileIcon(iconO);
                        }
                    }

                    ticTacToeBoard.gameOver(currMove);
                    if (ticTacToeBoard.isGameOver()){
                        gameStatus.setText("Is game over? " + ticTacToeBoard.isGameOver());
                        playerTurn.setText("No moves left.");

                    }
                    else {
                        playerTurn.setText("Player " + ticTacToeBoard.getCurrMove() + "'s Move");
                    }
                    //ticTacToeBoard.printBoard();
                    validate();
                }

                @Override
                public void mousePressed(MouseEvent e) { }

                @Override
                public void mouseReleased(MouseEvent e) { }

                @Override
                public void mouseEntered(MouseEvent e) { }

                @Override
                public void mouseExited(MouseEvent e) { }
            });
            validate();
        }

        private void assignTileColor(){
            if(tileId % 2 == 0){
                //setBackground(Color.lightGray);
                setBackground(Color.white);
            }
            else{
                setBackground(Color.white);
            }
        }

        private ImageIcon createTileIcon(char c){
            ImageIcon icon = null;
            if (c == Constants.PLAYER_ONE_SIGN) {
                try {
                    BufferedImage bImage = ImageIO.read(new File("/Users/adilrahman/IdeaProjects/TicTacToe/X.png"));
                    icon = new ImageIcon(bImage);
                    Image image = icon.getImage();
                    Image newImg = image.getScaledInstance(Constants.ICON_WIDTH, Constants.ICON_HEIGHT, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newImg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else if (c == Constants.PLAYER_TWO_SIGN){
                try {
                    BufferedImage bImage = ImageIO.read(new File("/Users/adilrahman/IdeaProjects/TicTacToe/O.png"));
                    icon = new ImageIcon(bImage);
                    Image image = icon.getImage();
                    Image newImg = image.getScaledInstance(Constants.ICON_WIDTH, Constants.ICON_HEIGHT, Image.SCALE_SMOOTH);
                    icon = new ImageIcon(newImg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return icon;
        }

        private void assignTileIcon(ImageIcon icon){
            this.removeAll();
            JLabel label = new JLabel(icon);
            add(label);
        }
    }
}
