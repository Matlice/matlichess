package it.matlice.settings;

import java.awt.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class Settings {

    public static final int CHESSBOARD_SIZE = 720;
    public static final BiConsumer<Graphics2D, Dimension> CHESSBOARD_BG = new ImageLoader("./res/chessboard_horsey.jpg", new Dimension(CHESSBOARD_SIZE, CHESSBOARD_SIZE));

}
