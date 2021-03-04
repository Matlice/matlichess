package it.matlice.matlichess;

public enum PieceColor {
    WHITE("White", 0),
    BLACK("Black", 1);

    public final int index;
    public final String name;

    PieceColor(String name, int index) {
        this.index = index;
        this.name = name;
    }

    /**
     * Returns the opponent color
     *
     * @return opponent color
     */
    public PieceColor opponent() {
        if (this.index == 0) return BLACK;
        return WHITE;
    }
}
