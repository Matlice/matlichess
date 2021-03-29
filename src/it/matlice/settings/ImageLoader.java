package it.matlice.settings;

import it.matlice.matlichess.view.ScreenLocation;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Class used to load an image from resources and scale it to the wanted dimension
 */
public class ImageLoader implements Drawable {

    private final Dimension dim;
    private Image img;

    /**
     * Constructor to load the image from resources path
     *
     * @param image_path the path of the image
     * @param dim        the wanted dimension for the image
     */
    public ImageLoader(String image_path, Dimension dim) {
        try {
            this.img = ImageIO.read(new File(image_path)).getScaledInstance(dim.width, dim.height, Settings.USE_ANTIALIAS ? Image.SCALE_AREA_AVERAGING : Image.SCALE_DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.dim = dim;
    }

    /**
     * Draws directly the loaded image to a Graphics2D
     *
     * @param g      the Graphics2D to print to
     * @param l      the location to start printing to
     * @param offset the offset referred to the base l Location
     */
    @Override
    public void accept(Graphics2D g, ScreenLocation l, ScreenLocation offset) {
        g.drawImage(img, l.x - offset.x, l.y - offset.y, this.dim.width, this.dim.height, null);
    }
}
