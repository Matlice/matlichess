package it.matlice.matlichess.model;

public enum Color {
    WHITE("White", 0),
    BLACK("Black", 1);

    public final int index;
    public final String name;
    private Color(String name, int index){
        this.index = index;
        this.name = name;
    }

    /**
     * Returns the opponent color
     * @return opponent color
     */
    public Color opponent(){
        if(this.index == 0) return BLACK;
        return WHITE;
    }
}
