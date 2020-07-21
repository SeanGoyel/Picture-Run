package test;

import MakeLevel.LevelImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;


class testLevelImage {

    File testFile;
    LevelImage testLevelImage;

    @BeforeEach
    void setup() {

        testFile = new File("/Users/sean/IdeaProjects/Picture Run/data/test.png");
        testLevelImage = new LevelImage(testFile);

    }

    @Test
    public void testConstructor() {
        assertEquals(550, testLevelImage.getHeight());
        assertEquals(900, testLevelImage.getWidth());
    }

    @Test
    public void testRGBVals() {
        assertEquals(252, testLevelImage.getR(25, 26));
        assertEquals(252, testLevelImage.getG(25, 26));
        assertEquals(252, testLevelImage.getB(25, 26));
    }


}
