public class Player {
    private char playerNum;
    private char playerSign;

    public Player(char playerNum){
        this.playerNum = playerNum;
        if (playerNum == '1') {
            playerSign = Constants.PLAYER_ONE_SIGN;
        }
        else{
            playerSign = Constants.PLAYER_TWO_SIGN;
        }
    }

    public char getPlayerNum(){
        return playerNum;
    }

    public char getPlayerSign(){
        return playerSign;
    }
}
