package it.matlice.matlichess.view;

import java.awt.*;

public class ScreenLocation extends Point {

    public ScreenLocation(int w, int h){
        super(w, h);
    }

    public ScreenLocation(){super();}

    public ScreenLocation diff(Point d){
        return new ScreenLocation((int) (this.x - d.x), (int)(this.y - d.y));
    }

    public ScreenLocation diff(int x, int y){
        return new ScreenLocation((int) (this.x - x), (int)(this.y - y));
    }
}
