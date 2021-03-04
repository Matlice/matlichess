package it.matlice.settings;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Settings {

    public static final int CHESSBOARD_SIZE = 720;
    public static final int MARKER_DIAMETER = 45;
    public static final BiConsumer<Graphics2D, Dimension> CHESSBOARD_BG = new ImageLoader("./res/blue_marble.jpg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE));
    public static final BiConsumer<Graphics2D, Dimension>[] CBURNETT_PIECE = new BiConsumer[]{
            new ImageLoader("./res/pieces/wP.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/wB.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/wN.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/wR.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/wQ.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/wK.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/bP.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/bB.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/bN.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/bR.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/bQ.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE)),
            new ImageLoader("./res/pieces/bK.svg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE))
    };

}
