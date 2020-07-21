package GameStates;


import Entities.Block;
import Entities.LevelEndWall;
import Entities.Player;
import MakeLevel.Platforms;
import javafx.util.Pair;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LevelState extends GameState {
    private Player player;
    private int[][] solids;
    private int[][] blockPositions;
    BufferedImage backGround;
    ArrayList<Block> blocks;

    private ArrayList<Pair> gapPositions;
    private int numberOfGaps;
    private boolean populateBlocks = false;

    private static final double FINISHPOS = 860;
    private static final double STARTINGBUFFER = 27;

    private int ticsUntilDeathState = 80;


    private boolean levelComplete = false;

    private boolean paused = false;
    private boolean dead = false;
    private boolean drawDeadState = false;
    private DeadState deadState;
    private PauseState pausedState;
    private ArrayList<Pair> blockSpeeds;
    private LevelEndWall levelEndWall;


    public LevelState(GameStateChecker gsc, Platforms platforms) {
        super(gsc, platforms);


    }

    @Override
    public void init() {
        levelEndWall = new LevelEndWall();
        this.solids = ((Platforms) obj).getSolids();
        backGround = ((Platforms) obj).getBackGround();


        this.initBlock();

        int startingX = (int) ((Platforms) obj).findStartingPos().getKey();
        player = new Player((int) (startingX + STARTINGBUFFER), 0, 17, 17, solids);
    }

    @Override
    public void tick() {
        if (!dead || ticsUntilDeathState != 0) {
            levelEndWall.tick();
            if (paused) {
                if (!pausedState.isPaused())
                    paused = false;
                return;
            }
            if (player.playerXPos() >= FINISHPOS) {
                levelComplete = true;

            }
            if (populateBlocks) {
                blockSpeeds = new ArrayList<>();
                blockPositions = new int[solids.length][solids[0].length];
                for (Block block : blocks) {
                    block.tick();
                    blockPositions = block.updateBlockPositions(blockPositions);
                    blockSpeeds.add(new Pair(block.getX2(), block.getSpeed()));
                }
            }

            if (!populateBlocks) {
                blockPositions = new int[solids.length][solids[0].length];
            }
            player.tick(blockPositions, blockSpeeds);
            dead = player.isPlayerDead();
            if (dead) {
                ticsUntilDeathState--;
            }
        } else {
            deadState = new DeadState(gsc);
            drawDeadState = true;
            ticsUntilDeathState--;
        }
    }

    @Override
    public void draw(Graphics g) {


        g.drawImage(backGround, 0, 0, null);
        player.draw(g);
        if (populateBlocks) {
            for (Block block : blocks) {
                block.draw(g);
            }
        }
        g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        g.setColor(new Color(64, 129, 255));
        g.drawString("Press B to spawn moving blocks", 10, 540);
        levelEndWall.draw(g);

        if (!dead) {
            if (paused) {
                pausedState.draw(g);
            }
        }

        if (drawDeadState) {

            deadState.draw(g);
        }


    }

    @Override
    public void keyPressed(int k) {
        if (!dead) {
            if (k == KeyEvent.VK_B) {
                populateBlocks = !populateBlocks;
            }
            if (k == KeyEvent.VK_ESCAPE) {
                pausedState = new PauseState(gsc);
                paused = !paused;
            }

            if (paused) {
                pausedState.keyPressed(k);
            }
            player.keyPressed(k);
        }

        if (drawDeadState) {
            deadState.keyPressed(k);
        }


    }

    @Override
    public void keyReleased(int k) {
        player.keyReleased(k);

    }

    public boolean getLevelComplete() {
        return levelComplete;
    }

    private void initBlock() {

        blocks = new ArrayList<>();

        gapPositions = ((Platforms) obj).getGapPositions();
        numberOfGaps = ((Platforms) obj).getNumberOfGaps();

        for (int i = 0; i < numberOfGaps; i++) {
            Pair position = gapPositions.get(i);
            int yHeight = (int) position.getKey();

            Pair bounds = (Pair) position.getValue();
            int x1 = (int) bounds.getKey();
            int x2 = (int) bounds.getValue();

            blocks.add(new Block(yHeight, x1, x2));
        }

    }

}
