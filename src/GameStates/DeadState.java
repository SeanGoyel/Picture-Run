package GameStates;

import Main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;


public class DeadState extends GameState {
    private String[] options = {"Try Again", "Menu", "Quit"};
    private int selection = 0;
    private int WIDTH = 900;
    private int HEIGHT = 550;


    public DeadState(GameStateChecker gsc) {
        super(gsc);
    }

    @Override
    public void init() {

    }

    @Override
    public void tick() {

    }

    public void draw(Graphics g) {
        g.setColor(new Color(186, 15, 21, 211));
        g.fillRect(WIDTH - 600, HEIGHT - 450, WIDTH - (2 * (WIDTH - 600)), HEIGHT - (2 * (HEIGHT - 450)));


        g.setFont(new Font("Comic Sans", Font.BOLD, 25));
        g.setColor(Color.BLACK);
        g.drawString("Game Over", 380, 160);


        for (int i = 0; i < options.length; i++) {
            if (i == selection) {
                g.setColor(Color.GREEN);
            } else g.setColor(Color.WHITE);

            g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
            if (i == 0) {
                g.drawString(options[i], 400, GamePanel.HEIGHT / 3 + i * 50 + 60);
            } else {
                g.drawString(options[i], 420, GamePanel.HEIGHT / 3 + i * 50 + 60);
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









