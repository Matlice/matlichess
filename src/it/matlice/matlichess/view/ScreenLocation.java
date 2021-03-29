package it.matlice.matlichess.view;

import java.awt.*;

/**
 * Utility class that adds new methods to {@link Point}
 */
public class ScreenLocation extends Point {

    public ScreenLocation(int w, int h) {
        super(w, h);
    }

    public ScreenLocation() {
        super();
    }

    public ScreenLocation diff(Point d) {
        return new ScreenLocation(this.x - d.x, this.y - d.y);
    }

    public ScreenLocation diff(int x, int y) {
        return new ScreenLocation(this.x - x, this.y - y);
    }
}
