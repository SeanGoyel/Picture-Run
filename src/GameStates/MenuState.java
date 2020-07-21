package GameStates;

import Main.GamePanel;
import MakeLevel.LevelImage;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class MenuState extends GameState {

    private String[] options = {"Load Images", "Start", "Quit"};
    private int selection = 0;


    public MenuState(GameStateChecker gsc) {
        super(gsc);
    }

    @Override
    public void init() {

    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(Graphics g) {

        File testFile = new File("/Users/sean/IdeaProjects/Picture Run/data/StartBackground.jpg");
        LevelImage testLevelImage = new LevelImage(testFile);
        BufferedImage img = testLevelImage.img;

        g.drawImage(img, 0, 0, null);

        for (int i = 0; i < options.length; i++) {
            if (i == selection) {
                g.setColor(Color.GREEN);
            } else g.setColor(Color.BLACK);

            g.setFont(new Font("Comic Sans", Font.PLAIN, 60));
            if (i == 0) {
                g.drawString(options[i], GamePanel.WIDTH / 2 - 175, GamePanel.HEIGHT / 3 + i * 90);
            } else {
                g.drawString(options[i], GamePanel.WIDTH / 2 - 80, GamePanel.HEIGHT / 3 + i * 90);
            }
        }
    }

    @Override
    public void keyPressed(int k) {

        if (k == KeyEvent.VK_DOWN) {
            selection++;

            if (selection >= options.length) {
                selection = 0;
            }
        }

        if (k == KeyEvent.VK_UP) {
            selection--;

            if (selection < 0) {
                selection = options.length - 1;
            }
        }

        if (k == KeyEvent.VK_ENTER) {
            if (selection == 0) {
                gsc.states.push(new AddImagesState(gsc));
            }
            if (selection == 1) {
                gsc.states.push(new LevelStateManager(gsc));
            }
            if (selection == 2) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(int k) {
    }
}
