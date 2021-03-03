package it.matlice.settings;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ImageLoader implements BiConsumer<Graphics2D, Dimension> {

    private File image_path;
    private Dimension dim;

    public ImageLoader(File image_path, Dimension dim) {
        this.image_path = image_path;
        this.dim = dim;
    }

    public ImageLoader(String image_path, Dimension dim) {
        this.image_path = new File(image_path);
        this.dim = dim;
    }

    public ImageLoader(String image_path, int w, int h) {
        this.image_path = new File(image_path);
        this.dim = new Dimension(w, h);
    }


    /**
     * Performs this operation on the given argument.
     *
     * @param graphics2D the input argument
     */
    @Override
    public void accept(Graphics2D g, Dimension d) {
        try {
            var img = ImageIO.read(this.image_path);
            g.drawImage(img, d.width, d.height, this.dim.width, this.dim.height, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
