package GameStates;

import Main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class CongratulationsState extends GameState {
    private String[] options = {"Play Again", "Menu", "Quit"};
    private int selection = 0;
    private int WIDTH = 900;
    private int HEIGHT = 550;


    public CongratulationsState(GameStateChecker gsc) {
        super(gsc);
    }

    @Override
    public void init() {

    }

    @Override
    public void tick() {

    }

    public void draw(Graphics g) {
        g.setColor(new Color(236, 193, 32, 211));
        g.fillRect(0, 0, WIDTH, HEIGHT);


        g.setFont(new Font("Comic Sans", Font.BOLD, 60));
        g.setColor(new Color(123, 192, 255, 229));
        g.drawString("Congratulations", 210, 140);
        g.drawString("You Win!", 300, 240);


        for (int i = 0; i < options.length; i++) {
            if (i == selection) {
                g.setColor(Color.BLACK);
            } else g.setColor(Color.WHITE);


            g.setFont(new Font("Comic Sans", Font.PLAIN, 25));
            if (i == 0) {
                g.drawString(options[i], 380, GamePanel.HEIGHT / 3 + i * 50 + 120);
            } else {
                g.drawString(options[i], 410, GamePanel.HEIGHT / 3 + i * 50 + 120);
            }


        }
    }

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
                gsc.states.push(new LevelStateManager(gsc));
            }


            if (selection == 1) {
                gsc.states.push(new MenuState(gsc));
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









