package it.matlice.settings;

import it.matlice.matlichess.view.ScreenLocation;

import java.awt.*;

public interface Drawable {
    public void accept(Graphics2D g, ScreenLocation d, ScreenLocation offset);
    default void accept(Graphics2D g, ScreenLocation d){
        this.accept(g, d, new ScreenLocation());
    }
}
