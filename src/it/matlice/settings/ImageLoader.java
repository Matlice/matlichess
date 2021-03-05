package it.matlice.settings;

import it.matlice.matlichess.view.ScreenLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader implements Drawable {

    private Image img;
    private Dimension dim;

    public ImageLoader(String image_path, Dimension dim) {
        try {
            this.img = ImageIO.read(new File(image_path)).getScaledInstance(dim.width, dim.height, Settings.USE_ANTIALIAS ? Image.SCALE_AREA_AVERAGING : Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dim = dim;
    }


    @Override
    public void accept(Graphics2D g, ScreenLocation l, ScreenLocation offset) {
            g.drawImage(img, l.x - offset.x, l.y - offset.y, this.dim.width, this.dim.height, null);
    }
}
