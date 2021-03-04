package it.matlice.settings;

import it.matlice.matlichess.view.ScreenLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageLoader implements Drawable {

    private BufferedImage img;
    private Dimension dim;

    public ImageLoader(File image_path, Dimension dim) {
        try {
            this.img = ImageIO.read(image_path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dim = dim;
    }

    public ImageLoader(String image_path, Dimension dim) {
        try {
            this.img = ImageIO.read(new File(image_path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dim = dim;
    }

    public ImageLoader(String image_path, int w, int h) {
        try {
            this.img = ImageIO.read(new File(image_path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dim = new Dimension(w, h);
    }


    @Override
    public void accept(Graphics2D g, ScreenLocation l, ScreenLocation offset) {
            g.drawImage(img, l.x - offset.x, l.y - offset.y, this.dim.width, this.dim.height, null);
    }
}
