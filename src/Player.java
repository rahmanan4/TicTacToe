public class Player {
    private char playerNum;
    private char playerSign;

    public Player(char playerNum){
        this.playerNum = playerNum;
        if (playerNum == '1') {
            playerSign = 'X';
        }
        else{
            playerSign = 'O';
        }
    }

    public char getPlayerNum(){
        return playerNum;
    }

    public char getPlayerSign(){
        return playerSign;
    }
}
