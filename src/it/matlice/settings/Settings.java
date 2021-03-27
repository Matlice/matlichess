package it.matlice.settings;

import java.awt.*;

/**
 * Class used to save global settings
 */
public class Settings {
    // GAME

    public static final String STARTING_POSITION_FEN = "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

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
    public static final String PIECE_STYLE_NAME = "staunty";
    public static final Drawable[] PIECES = new Drawable[]{
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/wP.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/wB.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/wN.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/wR.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/wQ.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/wK.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/bP.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/bB.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/bN.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/bR.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/bQ.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE)),
            new ImageLoader(Settings.class.getClassLoader().getResource("pieces/" + PIECE_STYLE_NAME + "/bK.png").getFile(), new Dimension(CHESSBOARD_SQUARE_SIZE, CHESSBOARD_SQUARE_SIZE))
    };

    // Endgame
    public static final String DRAW_MESSAGE = "The game finished as a draw...";
    public static final String GENERIC_WIN_MESSAGE = "%s won the game!";
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