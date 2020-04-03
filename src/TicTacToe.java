import java.util.Scanner;

public class TicTacToe {
    public static void main(String[] args){
        //TicTacToeGUI t = new TicTacToeGUI();

        Player p1 = new Player(Constants.PLAYER_ONE_NUM);
        Player p2 = new Player(Constants.PLAYER_TWO_NUM);
        Board b = new Board();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic Tac Toe!");
        b.printBoard();

        while (!b.isGameOver()) {
            System.out.println("Player " + b.getCurrMove() + " Move:");
            // input must be two numbers in the range 0-2, e.g. 00, 21, etc.
            String nextMove = scanner.nextLine();
            boolean isMove = b.move(p1.getPlayerSign(), nextMove);
            while (!isMove) {
                System.out.println("Invalid move. Try again.");
                nextMove = scanner.nextLine();
                isMove = b.move(p1.getPlayerSign(), nextMove);
            }
            b.printBoard();
            b.gameOver(p1.getPlayerSign());

            if (b.isGameOver()){
                break;
            }

            System.out.println("Player " + b.getCurrMove() + " Move:");
            nextMove = scanner.nextLine();
            isMove = b.move(p2.getPlayerSign(), nextMove);
            b.move(p2.getPlayerNum(), nextMove);
            while (!isMove) {
                System.out.println("Invalid move. Try again.");
                nextMove = scanner.nextLine();
                isMove = b.move(p2.getPlayerSign(), nextMove);
            }
            b.printBoard();
            b.gameOver(p2.getPlayerSign());
        }
        if (b.getWinState() == Constants.PLAYER_ONE_SIGN){
            System.out.println("Player 1 Wins!");
        }
        else if (b.getWinState() == Constants.PLAYER_TWO_SIGN){
            System.out.println("Player 2 Wins!");
        }
        else{
            System.out.println("Draw!");
        }
    }
}
