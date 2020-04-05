import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import static java.util.Map.entry;


public class TicTacToeGUI {

    private JFrame gameFrame;
    private BoardPanel boardPanel;
    private JLabel turnStatus = new JLabel("", SwingConstants.CENTER);

    private Board ticTacToeBoard;

    public TicTacToeGUI() {
        this.ticTacToeBoard = new Board();

        this.gameFrame = new JFrame(Constants.TITLE);
        this.gameFrame.setLayout(new BorderLayout());
        this.gameFrame.setJMenuBar(createMenuBar());
        this.gameFrame.setSize(Constants.OUTER_FRAME_DIMENSION);

        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        turnStatus.setText("Player " + ticTacToeBoard.getCurrMove() + "'s Move");
        this.gameFrame.add(turnStatus, BorderLayout.NORTH);

        this.gameFrame.setVisible(true);
    }

    private JMenuBar createMenuBar(){
        final JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        return menuBar;
    }

    private JMenu createFileMenu(){
        final JMenu fileMenu = new JMenu(Constants.JMENU_TAB1_NAME);

        final JMenuItem restart = new JMenuItem(Constants.JMENU_TAB1_JMENU_ITEM1);
        restart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                resetGameFrame();
            }
        });

        final JMenuItem exit = new JMenuItem(Constants.JMENU_TAB1_JMENU_ITEM2);
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(restart);
        fileMenu.add(exit);
        return fileMenu;
    }

    private void resetGameFrame(){
        this.gameFrame.remove(boardPanel);

        this.ticTacToeBoard = new Board();
        this.boardPanel = new BoardPanel();
        this.gameFrame.add(this.boardPanel, BorderLayout.CENTER);

        turnStatus.setText("Player " + ticTacToeBoard.getCurrMove() + "'s Move");
        this.gameFrame.add(turnStatus, BorderLayout.NORTH);

        this.gameFrame.setVisible(true);
    }


    private class BoardPanel extends JPanel{
        BoardPanel(){
            super(new GridLayout(Constants.ROW_DIMENSION, Constants.COL_DIMENSION));

            // Tic Tac Toe will have 9 tiles, so create 9 TilePanels
            for(int i = 0; i < Constants.NUM_TILES; i++){
                final TilePanel tilePanel = new TilePanel(this, i);
                add(tilePanel);
            }

            // set the dimensions of the board, where board dimensions is less than or equal to outer frame
            setPreferredSize(Constants.BOARD_PANEL_DIMENSION);
            validate();
        }
    }


    private class TilePanel extends JPanel{
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
            setBackground(Color.white);
            setBorder(BorderFactory.createLineBorder(Color.black, 2, true));
            iconX = createTileIcon(Constants.PLAYER_ONE_SIGN);
            iconO = createTileIcon(Constants.PLAYER_TWO_SIGN);

            addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if (!ticTacToeBoard.isGameOver()) {
                        char currMove;
                        boolean isValid;
                        if (ticTacToeBoard.getCurrMove() == 1) {
                            currMove = Constants.PLAYER_ONE_SIGN;
                            isValid = ticTacToeBoard.move(currMove, mapping.get(tileId));
                            if (isValid) {
                                assignTileIcon(iconX);
                            }
                        }
                        else {
                            currMove = Constants.PLAYER_TWO_SIGN;
                            isValid = ticTacToeBoard.move(currMove, mapping.get(tileId));
                            if (isValid) {
                                assignTileIcon(iconO);
                            }
                        }

                        ticTacToeBoard.gameOver(currMove);
                        if (ticTacToeBoard.isGameOver()) {
                            char winState = ticTacToeBoard.getWinState();
                            if (winState == Constants.DRAW_SIGN){
                                turnStatus.setText("Draw!");
                            }
                            else {
                                turnStatus.setText(ticTacToeBoard.getWinState() + " Wins!");
                            }
                        }
                        else {
                            turnStatus.setText("Player " + ticTacToeBoard.getCurrMove() + "'s Move");
                        }
                        validate();
                    }
                }
            });
            validate();
        }

        private ImageIcon createTileIcon(char c){
            ImageIcon icon = null;
            if (c == Constants.PLAYER_ONE_SIGN) {
                try {
                    BufferedImage bImage = ImageIO.read(new File(Constants.X_ICON_FILE_PATH));
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
                    BufferedImage bImage = ImageIO.read(new File(Constants.O_ICON_FILE_PATH));
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
            label.setOpaque(false);
            add(label);
        }
    }
}
