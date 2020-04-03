import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class TicTacToeGUI {
    // create a frame aka a window
    private final JFrame gameFrame;
    // create a board panel which is a class defined below
    private final BoardPanel boardPanel;

    private final static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,600);
    private final static Dimension BOARD_PANEL_DIMENSION = new Dimension(100, 100);
    private final static Dimension TILE_PANEL_DIMENSION = new Dimension(10, 10);

    public TicTacToeGUI() {
        // actually instantiate a JFrame to the JFrame being held in the TicTacToeGUI class
        this.gameFrame = new JFrame("Tic Tac Toe");
        // adds a border layout where you can then throw various things at various locations of the board.
        this.gameFrame.setLayout(new BorderLayout());
        // adds the menu bar to the game window
        this.gameFrame.setJMenuBar(createMenuBar());
        // make the window dimensions the size of OUTER_FRAME_DIMENSION
        this.gameFrame.setSize(OUTER_FRAME_DIMENSION);

        this.boardPanel = new BoardPanel();
        // Throws the board panel in the center of the window
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);
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
        final JMenu fileMenu = new JMenu("File");
        // create exit option underneath file tab
        final JMenuItem exit = new JMenuItem("Exit");
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

    private class BoardPanel extends JPanel{
        // store all TilePanels into a list of Tile Panels called boardTiles
        final List<TilePanel> boardTiles;

        BoardPanel(){
            super(new GridLayout(Board.ROW_DIMENSION,3));
            // stores boardTiles in ArrayList
            this.boardTiles = new ArrayList<>();
            // Tic Tac Toe will have 9 tiles, so create 9 TilePanels
            for(int i = 0; i < 9; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                // first add the tilePanel to the ArrayList, then add it to the frame (window)
                this.boardTiles.add(tilePanel);
                add(tilePanel);
            }
            // set the dimensions of the board, where board dimensions is less than or equal to outer frame
            setPreferredSize(BOARD_PANEL_DIMENSION);
            validate();
        }
    }

    private class TilePanel extends JPanel{
        // when BoardPanel constructor is called, it feeds i from 0 to 8 as the tile id
        private final int tileId;

        TilePanel(final BoardPanel boardPanel, final int tileId){
            super(new GridBagLayout());
            this.tileId = tileId;
            setPreferredSize(TILE_PANEL_DIMENSION);
            assignTileColor();
            validate();
        }

        private void assignTileColor(){
            if(tileId % 2 == 0){
                setBackground(Color.LIGHT_GRAY);
            }
            else{
                setBackground(Color.white);
            }
        }
    }
}
