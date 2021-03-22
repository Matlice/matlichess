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
    public static final String CHESSBOARD_BG_STYLE = "blue_marble.jpg";
    public static final Drawable CHESSBOARD_BG = new ImageLoader("./res/chessboards/" + CHESSBOARD_BG_STYLE, new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE));

    // Selection marker
    public static final Color SELECTION_BG_COLOR = new Color(0, 172, 151, 77);

    // Capture marker
    public static final Color CAPTURE_COLOR = new Color(255, 0, 0, 120);
    public static final int CAPTURE_DIAMETER = 80;

    // Move marker
    public static final Color MOVE_COLOR = new Color(0, 0, 127, 80);
    public static final int MOVE_DIAMETER = 40;

    // Pieces
    public static final String PIECE_STYLE = "cburnett";
    public static final Drawable[] CBURNETT_PIECE = new Drawable[]{
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/wP.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/wB.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/wN.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/wR.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/wQ.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/wK.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/bP.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/bB.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/bN.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/bR.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/bQ.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader("./res/pieces/" + PIECE_STYLE + "/bK.png", new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE))
    };

    // Endgame
    public static final String DRAW_MESSAGE = "The game finished as a draw...";
    public static final String WIN_MESSAGE = "You WON the game, congratulations!";
    public static final String LOST_MESSAGE = "You lost the game, next time will be better";

    public static final String REMATCH_MESSAGE = "Do you want to play again?";

    public static final Object[] DIALOG_OPTIONS = {"Cancel", "Exit", "Yes, again!" };
    public static final int DIALOG_CLOSED_INDEX = -1;
    public static final int CANCEL_OPTION_INDEX = 0;
    public static final int EXIT_OPTION_INDEX = 1;
    public static final int REMATCH_OPTION_INDEX = 2;

    // Anti-aliasing
    public static final boolean USE_ANTIALIAS = true;

    // NETWORK
    public static final int NETWORK_PORT = 42069;

}
