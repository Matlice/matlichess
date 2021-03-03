package it.matlice.settings;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Settings {

    public static final int CHESSBOARD_SIZE = 720;
    public static final int MARKER_DIAMETER = 45;
    public static final BiConsumer<Graphics2D, Dimension> CHESSBOARD_BG = new ImageLoader("./res/blue_marble.jpg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE));

}
