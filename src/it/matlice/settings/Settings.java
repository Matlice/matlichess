package it.matlice.settings;

import it.matlice.matlichess.model.Location;

import java.awt.*;
import java.util.function.BiConsumer;

public class Settings {

    public static final int CHESSBOARD_SIZE = 720;
    public static final int MARKER_DIAMETER = 45;
    public static final BiConsumer<Graphics2D, Location> CHESSBOARD_BG = new ImageLoader("./res/blue_marble.jpg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE));
    public static final BiConsumer<Graphics2D, Location>[] CBURNETT_PIECE = new BiConsumer[]{
            new ImageLoader("./res/pieces/cburnett/wP.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/wB.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/wN.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/wR.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/wQ.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/wK.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/bP.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/bB.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/bN.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/bR.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/bQ.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8)),
            new ImageLoader("./res/pieces/cburnett/bK.png", new Dimension(CHESSBOARD_SIZE/8, CHESSBOARD_SIZE/8))
    };

}
