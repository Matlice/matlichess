package it.matlice.matlichess;

public enum GameState {
    PLAYING(null),
    DRAW(null),
    BLACK_WIN("Black"),
    WHITE_WIN("White");

    private String winner;

    GameState(String s) {
        this.winner = s;
    }

    public String getWinnerString() {
        return winner;
    }
}
