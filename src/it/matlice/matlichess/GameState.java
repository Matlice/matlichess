package it.matlice.matlichess;

/**
 * Enum to represent the state of a chess game
 * The game could be ended as win (for both players), as a draw or not finished yet
 */
public enum GameState {
    PLAYING(null),
    DRAW(null),
    BLACK_WIN("Black"),
    WHITE_WIN("White");

    private String winner;

    GameState(String s) {
        this.winner = s;
    }

    /**
     * Returns the String representation of the winning player, null if still playing or draw
     * @return
     */
    public String getWinnerString() {
        return winner;
    }
}
