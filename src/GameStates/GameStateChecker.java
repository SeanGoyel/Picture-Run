package GameStates;

import java.awt.*;
import java.util.Stack;

public class GameStateChecker {
    public Stack<GameState> states;

    public GameStateChecker() {
        states = new Stack<>();
        states.push(new MenuState(this));

    }


    public void tick() {
        states.peek().tick();
    }


    public void draw(Graphics g) {
        states.peek().draw(g);
    }

    public void keyReleased(int k) {
        states.peek().keyReleased(k);
    }


    public void keyPressed(int k) {
        states.peek().keyPressed(k);
    }


}
