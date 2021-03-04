package it.matlice.settings;

import it.matlice.matlichess.model.Location;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.function.BiConsumer;

public class ImageLoader implements BiConsumer<Graphics2D, Location> {

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
     * @param g the input argument
     * @param l Location where to draw the image
     */
    @Override
    public void accept(Graphics2D g, Location l) {
        try {
            var img = ImageIO.read(this.image_path);

            int xCoord = l.col() * Settings.CHESSBOARD_SIZE/8;
            int yCoord = (7-l.row()) * Settings.CHESSBOARD_SIZE/8;

            g.drawImage(img, xCoord, yCoord, this.dim.width, this.dim.height, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
