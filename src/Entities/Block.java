package Entities;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


public class Block {
    private BufferedImage blockImg;
    private int speed = 1;
    private int x;
    private int yHeight;
    private int height;
    private int width;


    //x bounds
    private int x1;
    private int x2;


    public Block(int yHeight, int x1, int x2) {

        File file = new File("/Users/sean/IdeaProjects/Picture Run/data/Block.png");

        try {
            blockImg = ImageIO.read(file);

            BufferedImage resized = new BufferedImage(30, 30, blockImg.getType());
            Graphics2D g = resized.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(blockImg, 0, 0, 30, 30, 0, 0, blockImg.getWidth(),
                    blockImg.getHeight(), null);
            g.dispose();

            blockImg = resized;


        } catch (Exception e) {
            e.printStackTrace();
        }

        width = blockImg.getWidth();
        height = blockImg.getHeight();

        this.x1 = x1;
        this.x2 = x2 - width;
        this.yHeight = yHeight;
        x = x1;
    }

    public void tick() {

        if (x + speed >= x2) {
            speed *= -1;
        }

        if (x + speed < x1) {
            speed *= -1;
        }
        x = x + speed;

    }


    public void draw(Graphics g) {
        g.drawImage(blockImg, x, yHeight, null);
    }

    public int[][] updateBlockPositions(int[][] solids) {

        for (int i = x; i < width + x; i++) {
            for (int j = yHeight; j < yHeight + height; j++) {

                solids[i][j] = 2;
            }
        }
        return solids;
    }

    public int getX2() {
        return x2;
    }

    public int getSpeed() {
        return speed;
    }


}
