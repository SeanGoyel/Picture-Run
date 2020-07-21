package Main;

import GameStates.GameStateChecker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GamePanel extends JPanel implements Runnable, KeyListener {

    public static final int HEIGHT = 550;
    public static final int WIDTH = 900;


    private Thread thread;
    private boolean isRunning = false;

    private int FPS = 60;
    private int target = 1000 / FPS;


    private GameStateChecker gsc;


    public GamePanel() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        addKeyListener(this);
        setFocusable(true);

        start();
    }

    private void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();


    }

    public void run() {

        gsc = new GameStateChecker();
        long wait, elapsed, start;
        while (isRunning) {

            start = System.nanoTime();

            tick();
            repaint();

            elapsed = System.nanoTime() - start;

            wait = target - elapsed / 1000000;

            if (wait < 0) {
                wait = 6;
            }

            try {
                Thread.sleep(wait);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

    public void tick() {

        gsc.tick();

    }

    public void paint(Graphics g) {
        super.paint(g);

        g.clearRect(0, 0, WIDTH, HEIGHT);
        gsc.draw(g);
    }

    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {

        gsc.keyPressed(e.getKeyCode());

    }

    @Override
    public void keyReleased(KeyEvent e) {
        gsc.keyReleased(e.getKeyCode());

    }


}
