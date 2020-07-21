package test;

import MakeLevel.LevelImage;
import MakeLevel.Platforms;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

class testPlatforms {

    File testFile;
    File testFile2;
    LevelImage testLevelImage;
    LevelImage testLevelImage2;

    Platforms testPlatforms;
    Platforms testPlatforms2;

    @BeforeEach
    void setup() {

        testFile = new File("/Users/sean/IdeaProjects/Picture Run/data/test.png");
        testLevelImage = new LevelImage(testFile);
        testPlatforms = new Platforms(testLevelImage);

        testFile2 = new File("/Users/sean/IdeaProjects/Picture Run/data/test2.png");
        testLevelImage2 = new LevelImage(testFile2);
        testPlatforms2 = new Platforms(testLevelImage2);

    }


    @Test
    void testSolids() {
        testPlatforms.findPlatforms();


        for (int y = 0; y < testLevelImage.getHeight(); y++) {
            System.out.println("");
            for (int x = 0; x < testLevelImage.getWidth(); x++) {
                System.out.print(" " + testPlatforms.getSolidVal(x, y));
            }
        }

    }

    @Test
    void testSolidsMultipleLines() {
        testPlatforms2.findPlatforms();

        for (int y = 0; y < testLevelImage2.getHeight(); y++) {
            System.out.println("");
            for (int x = 0; x < testLevelImage2.getWidth(); x++) {
                System.out.print(" " + testPlatforms2.getSolidVal(x, y));
            }
        }

    }

    @Test
    void testGaps() {
        testPlatforms2.findPlatforms();
        testPlatforms2.findGaps();
        assertEquals(3, testPlatforms2.getNumberOfGaps());
    }

    @Test
    void testStartingPos() {
        testPlatforms.findPlatforms();
        testPlatforms.findGaps();
        assertEquals(85, (int) testPlatforms.findStartingPos().getKey());
        assertEquals(56, (int) testPlatforms.findStartingPos().getValue());

    }

}
