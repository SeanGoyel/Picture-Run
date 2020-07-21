package GameStates;

import Main.GamePanel;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PauseState extends GameState {
    private String[] options = {"Return to game", "Restart", "Menu", "Quit"};
    private int selection = 0;
    private boolean paused = true;
    private int WIDTH = 900;
    private int HEIGHT = 550;


    public PauseState(GameStateChecker gsc) {
        super(gsc);
    }

    @Override
    public void init() {
    }

    @Override
    public void tick() {
    }

    public void draw(Graphics g) {
        g.setColor(new Color(16, 24, 52, 104));
        g.fillRect(WIDTH - 600, HEIGHT - 450, WIDTH - (2 * (WIDTH - 600)), HEIGHT - (2 * (HEIGHT - 450)));
        g.setFont(new Font("Comic Sans", Font.BOLD, 25));
        g.setColor(Color.BLUE);
        g.drawString("Paused", 410, 150);

        for (int i = 0; i < options.length; i++) {
            if (i == selection) {
                g.setColor(Color.GREEN);
            } else g.setColor(Color.WHITE);

            g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
            if (i == 0) {
                g.drawString(options[i], 380, GamePanel.HEIGHT / 3 + i * 50 + 60);
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
                paused = false;
            }
            if (selection == 1) {
                gsc.states.push(new LevelStateManager(gsc));
            }
            if (selection == 2) {
                gsc.states.push(new MenuState(gsc));
            }
            if (selection == 3) {
                System.exit(0);
            }
        }
    }

    @Override
    public void keyReleased(int k) {
    }

    public boolean isPaused() {
        return paused;
    }

}







