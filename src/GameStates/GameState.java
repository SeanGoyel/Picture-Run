package GameStates;

import java.awt.*;

public abstract class GameState {
    protected GameStateChecker gsc;
    protected Object obj;

    protected GameState(GameStateChecker gsc) {
        this.gsc = gsc;
        init();
    }

    protected GameState(GameStateChecker gsc, Object obj) {
        this.gsc = gsc;
        this.obj = obj;
        init();
    }

    public abstract void init();

    public abstract void tick();

    public abstract void draw(Graphics g);

    public abstract void keyPressed(int k);

    public abstract void keyReleased(int k);


}
