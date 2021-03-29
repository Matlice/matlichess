package it.matlice.settings;

import it.matlice.matlichess.view.ScreenLocation;

import java.awt.*;

/**
 * Interface used to draw images, it could be thought of a TriConsumer (which does not exist in Java)
 */
public interface Drawable {
    void accept(Graphics2D g, ScreenLocation d, ScreenLocation offset);

    default void accept(Graphics2D g, ScreenLocation d) {
        this.accept(g, d, new ScreenLocation());
    }
}
