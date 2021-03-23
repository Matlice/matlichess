package it.matlice.settings;

import java.awt.*;

/**
 * Class used to save global settings
 */
public class Settings {

    // VIEW

    // Chessboard dimensions
    public static final int CHESSBOARD_SQUARE_SIZE = 90;
    public static final int CHESSBOARD_SIZE = 8*CHESSBOARD_SQUARE_SIZE;

    // Chessboard background
    public static final String CHESSBOARD_BG_STYLE = "blue3.jpg";
    public static final Drawable CHESSBOARD_BG = new ImageLoader(Settings.class.getClassLoader().getResource("chessboards/" + CHESSBOARD_BG_STYLE).getFile(), new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE));

    // Selection marker
    public static final Color SELECTION_BG_COLOR = new Color(0, 172, 151, 77);

    // Capture marker
    public static final Color CAPTURE_COLOR = new Color(255, 0, 0, 120);
    public static final int CAPTURE_DIAMETER = 80;

    // Move marker
    public static final Color MOVE_COLOR = new Color(0, 0, 127, 80);
    public static final int MOVE_DIAMETER = 40;

    // Pieces
    public static final String PIECE_STYLE = "staunty";
    public static final Drawable[] CBURNETT_PIECE = new Drawable[]{
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/wP.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/wB.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/wN.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/wR.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/wQ.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/wK.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/bP.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/bB.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/bN.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/bR.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/bQ.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE + "/bK.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE))
    };

    // Anti-aliasing
    public static final boolean USE_ANTIALIAS = true;

    // NETWORK
    public static final int NETWORK_PORT = 42069;

}
