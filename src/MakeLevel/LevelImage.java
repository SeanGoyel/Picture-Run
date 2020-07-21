package MakeLevel;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class LevelImage {
    int height;
    int width;
    public BufferedImage img = null;


    public LevelImage(File file) {

        try {
            img = ImageIO.read(file);


            BufferedImage resized = new BufferedImage(900, 550, img.getType());
            Graphics2D g = resized.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(img, 0, 0, 900, 550, 0, 0, img.getWidth(),
                    img.getHeight(), null);
            g.dispose();

            img = resized;


        } catch (Exception e) {
            e.printStackTrace();
        }

        width = img.getWidth();
        height = img.getHeight();


    }


    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public Color getColor(int x, int y) {
        return new Color(img.getRGB(x, y));
    }

    public int getR(int x, int y) {
        return this.getColor(x, y).getRed();

    }

    public int getG(int x, int y) {
        return this.getColor(x, y).getGreen();
    }

    public int getB(int x, int y) {
        return this.getColor(x, y).getBlue();
    }


}
