package Entities;

import Main.GamePanel;

import java.awt.*;

//Colour changing wall at the end of each level
public class LevelEndWall {
    private int changeColorByR = 1;
    private int changeColorByG = 2;
    private int changeColorByB = 1;
    private int currentColorValR = 100;
    private int currentColorValG = 0;
    private int currentColorValB = 100;


    public LevelEndWall() {
    }

    public void tick() {
        if (currentColorValR + changeColorByR > 100 || currentColorValR + changeColorByR < 0) {
            changeColorByR *= -1;
        }
        currentColorValR += changeColorByR;

        if (currentColorValG + changeColorByG > 100 || currentColorValG + changeColorByG < 0) {
            changeColorByG *= -1;
        }
        currentColorValG += changeColorByG;

        if (currentColorValB + changeColorByB > 100 || currentColorValB + changeColorByB < 0) {
            changeColorByB *= -1;
        }
        currentColorValB += changeColorByB;
    }


    public void draw(Graphics g) {
        g.setColor(Color.YELLOW);
        g.fillRect(GamePanel.WIDTH-30, 0, 30, GamePanel.HEIGHT);

        g.setColor(new Color(currentColorValR, currentColorValG, currentColorValB));
        g.fillRect(GamePanel.WIDTH-25, 5, 20, GamePanel.HEIGHT-10);


    }


}


