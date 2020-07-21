package GameStates;

import MakeLevel.LevelImage;
import MakeLevel.Platforms;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class LevelStateManager extends GameState {
    public static final String DIRECTORY = "/Users/sean/IdeaProjects/Picture Run/ImagesToMakeLevel";
    private ArrayList<LevelState> levels;
    private int currentLevel = 0;
    private int numberOfLevels;
    private CongratulationsState congratulationsState;

    private boolean allLevelComplete;


    public LevelStateManager(GameStateChecker gsc) {
        super(gsc);
    }

    @Override
    public void init() {
        levels = new ArrayList<>();
        congratulationsState = new CongratulationsState(gsc);

        File folder = new File(DIRECTORY);
        String[] files = folder.list();
        for (String file : files) {

            File currentImageFile = new File(DIRECTORY + "/" + file);
            LevelImage currentImageLevelImage = new LevelImage(currentImageFile);
            Platforms currentPlatforms = new Platforms(currentImageLevelImage);
            currentPlatforms.findPlatforms();
            currentPlatforms.findGaps();

            levels.add(new LevelState(gsc, currentPlatforms));
        }
        numberOfLevels = levels.size();
    }

    @Override
    public void tick() {
        if (!allLevelComplete)
            if (levels.get(currentLevel).getLevelComplete()) {
                currentLevel++;
                if (currentLevel == numberOfLevels) {
                    allLevelComplete = true;
                }
            }
        if (allLevelComplete) {
            return;
        }
        levels.get(currentLevel).tick();
    }

    @Override
    public void draw(Graphics g) {
        if (allLevelComplete) {
            congratulationsState.draw(g);
            return;
        }
        levels.get(currentLevel).draw(g);
    }

    @Override
    public void keyPressed(int k) {

        if (allLevelComplete) {
            congratulationsState.keyPressed(k);
            return;
        }
        levels.get(currentLevel).keyPressed(k);
    }


    @Override
    public void keyReleased(int k) {
        if (allLevelComplete) {
            return;
        }
        levels.get(currentLevel).keyReleased(k);

    }
}
