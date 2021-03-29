package it.matlice.matlichess;

/**
 * Enum to represent the state of a chess game
 * The game could be ended as win (for both players), as a draw or not finished yet
 */
public enum GameState {
    PLAYING("Game still playing...", null),
    DRAW("The game ended as a draw", null),
    BLACK_WIN("Black won the game", "Black"),
    WHITE_WIN("White won the game", "White");

    private String winner;
    private String endStatement;

    GameState(String endStatement, String s) {
        this.endStatement = endStatement;
        this.winner = s;
    }

    /**
     * Returns the ending statement to print
     * @return
     */
    public String getEndStatement() {
        return endStatement;
    }

    /**
     * Returns the String representation of the winning player, null if still playing or draw
     * @return
     */
    public String getWinnerString() {
        return winner;
    }
}
