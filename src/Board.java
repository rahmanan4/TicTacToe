public class Board {
    private char[][] board = new char[Constants.ROW_DIMENSION][Constants.COL_DIMENSION];
    private int currMove = 1;
    private boolean gameOver = false;
    private char winState;

    public Board(){
        makeBoard();
    }

    private void makeBoard(){
        for (int i = 0; i < Constants.ROW_DIMENSION; i++){
            for (int j = 0; j < Constants.COL_DIMENSION; j++){
                board[i][j] = '_';
            }
        }
    }

    private boolean checkBounds(int row, int col){
        return row >= 0 && row < Constants.ROW_DIMENSION && col >= 0 && col < Constants.COL_DIMENSION;
    }

    public boolean move(char player, String m){
        if (m.length() != 2){
            return false;
        }

        int row = Character.getNumericValue(m.charAt(0));
        int col = Character.getNumericValue(m.charAt(1));

        if (!checkBounds(row, col)){
            return false;
        }

        if (board[row][col] == '_') {
            board[row][col] = player;
            if (currMove == 1) {
                currMove = 2;
            }
            else {
                currMove = 1;
            }
            return true;
        }
        return false;
    }

    public int getCurrMove(){
        return currMove;
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public boolean emptySpaceCheck(){
        for (int i = 0; i < Constants.ROW_DIMENSION; i++){
            for (int j = 0; j < Constants.COL_DIMENSION; j++){
                if (board[i][j] == '_'){
                    return true;
                }
            }
        }
        return false;
    }

    public boolean rowCheck(char sign){
        for (int i = 0; i < Constants.ROW_DIMENSION; i++) {
            int signCounter = 0;
            for (int j = 0; j < Constants.COL_DIMENSION; j++) {
                if (board[i][j] != '_' && board[i][j] == sign) {
                    signCounter++;
                }
            }
            if (signCounter == 3){
                return true;
            }
        }
        return false;
    }

    public boolean colCheck(char sign){
        for (int j = 0; j < Constants.COL_DIMENSION; j++) {
            int signCounter = 0;
            for (int i = 0; i < Constants.ROW_DIMENSION; i++) {
                if (board[i][j] != '_' && board[i][j] == sign) {
                    signCounter++;
                }
            }
            if (signCounter == 3){
                return true;
            }
        }
        return false;
    }

    public boolean diagCheck(char sign){
        int signCounter = 0;
        int i = 0;
        int j = 0;
        while (i < Constants.ROW_DIMENSION || j < Constants.COL_DIMENSION){
            if (board[i][j] == sign){
                signCounter++;
            }
            i++;
            j++;
        }
        if (signCounter == 3){
            return true;
        }

        signCounter = 0;
        i = 0;
        j = 2;
        while (i < Constants.ROW_DIMENSION || j > -1){
            if (board[i][j] == sign){
                signCounter++;
            }
            i++;
            j--;
        }
        return signCounter == 3;
    }

    public void gameOver(char sign){
        if (rowCheck(sign) || colCheck(sign) || diagCheck(sign)){
            winState = sign;
            gameOver = true;
        }
        else if (!emptySpaceCheck()){
            winState = 'd';
            gameOver = true;
        }
    }

    public char getWinState(){
        return winState;
    }

    public void printBoard(){
        for(int i = 0; i < Constants.ROW_DIMENSION; i++){
            for (int j = 0; j < Constants.COL_DIMENSION; j++){
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }
}
