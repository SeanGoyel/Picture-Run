package MakeLevel;

import javafx.util.Pair;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

import static sun.swing.MenuItemLayoutHelper.max;

public class Platforms {
    private LevelImage levelImage;
    private BufferedImage backGround;
    private static int TOLERANCE = 500;
    private int[][] solids;

    private Queue<Pair> queue = new LinkedList<>();
    private Queue<Pair> queueForSolids = new LinkedList<>();
    private int[][] visitedInsideSolid;

    private int[][] visited;

    private int[][] correctedArray;


    private int maxDistance = 50;
    private int numberOfGaps = 0;
    private ArrayList<Pair> gapPositions;

    public Platforms(LevelImage levelImage) {

        correctedArray = new int[levelImage.getWidth()][levelImage.getHeight()];
        solids = new int[levelImage.getWidth()][levelImage.getHeight()];
        visited = new int[levelImage.getWidth()][levelImage.getHeight()];
        visitedInsideSolid = new int[levelImage.getWidth()][levelImage.getHeight()];

        this.levelImage = levelImage;
        backGround = levelImage.img;

        gapPositions = new ArrayList<>();

    }


    //finds positions from the img that constitute as solids
    public void findPlatforms() {

        int[][] visited = new int[levelImage.getWidth()][levelImage.getHeight()];

        queue.add(new Pair(0, 0));
        visited[0][0] = 1;

        while (!queue.isEmpty()) {
            int xPos = (int) queue.peek().getKey();
            int yPos = (int) queue.peek().getValue();

            queue.remove();

            solids[xPos][yPos] = 1;
            westPoint(xPos, yPos);
            eastPoint(xPos, yPos);
            southPoint(xPos, yPos);
            northPoint(xPos, yPos);
        }
        this.removeParticles();
        solids = correctedArray;
    }


    //check west point of x,y to see if it is with the colour difference tolerance
    public void westPoint(int x, int y) {
        if (x - 1 < 0 || visited[x - 1][y] == 1) {
            return;
        }

        visited[x - 1][y] = 1;

        if ((Math.pow(levelImage.getR(x, y) - levelImage.getR(x - 1, y), 2) +
                Math.pow(levelImage.getB(x, y) - levelImage.getB(x - 1, y), 2) +
                Math.pow(levelImage.getG(x, y) - levelImage.getG(x - 1, y), 2)) < TOLERANCE) {
            queue.add(new Pair(x - 1, y));
        }
    }

    //check east point of x,y to see if it is with the colour difference tolerance
    public void eastPoint(int x, int y) {
        if (x + 1 > levelImage.getWidth() - 10 || visited[x + 1][y] == 1) {
            return;
        }

        visited[x + 1][y] = 1;
        if ((Math.pow(levelImage.getR(x, y) - levelImage.getR(x + 1, y), 2) +
                Math.pow(levelImage.getB(x, y) - levelImage.getB(x + 1, y), 2) +
                Math.pow(levelImage.getG(x, y) - levelImage.getG(x + 1, y), 2)) < TOLERANCE) {
            queue.add(new Pair(x + 1, y));
        }
    }

    //check north point of x,y to see if it is with the colour difference tolerance
    public void southPoint(int x, int y) {
        if (y + 1 > levelImage.getHeight() - 2 || visited[x][y + 1] == 1) {
            return;
        }
        visited[x][y + 1] = 1;
        if ((Math.pow(levelImage.getR(x, y) - levelImage.getR(x, y + 1), 2) +
                Math.pow(levelImage.getB(x, y) - levelImage.getB(x, y + 1), 2) +
                Math.pow(levelImage.getG(x, y) - levelImage.getG(x, y + 1), 2)) < TOLERANCE) {
            queue.add(new Pair(x, y + 1));
        }
    }

    //check south point of x,y to see if it is with the colour difference tolerance
    public void northPoint(int x, int y) {
        if (y - 1 < 0 || visited[x][y - 1] == 1) {
            return;
        }
        visited[x][y - 1] = 1;
        if ((Math.pow(levelImage.getR(x, y) - levelImage.getR(x, y - 1), 2) +
                Math.pow(levelImage.getB(x, y) - levelImage.getB(x, y - 1), 2) +
                Math.pow(levelImage.getG(x, y) - levelImage.getG(x, y - 1), 2)) < TOLERANCE) {
            queue.add(new Pair(x, y - 1));
        }
    }


    //removes positions identified as solids if some point in a solid is not surrounded by 5 solid pixels in the left
    //and right directions
    public void removeParticles() {
        for (int xPos = 6; xPos < backGround.getWidth() - 10; xPos++) {
            for (int yPos = 6; yPos < backGround.getHeight() - 20; yPos++) {
                int neighbourPixels = 0;
                if (solids[xPos][yPos] == 1) {

                } else {
                    boolean insideSolid = true;
                    for (int i = 1; i < 6; i++) {
                        if (!(solids[xPos + i][yPos] == 0 && solids[xPos - i][yPos] == 0)) {
                            insideSolid = false;
                        }
                    }

                    if (insideSolid) {
                        this.fillInsideSolid(xPos, yPos);
                    }

                }

            }

        }

    }


    //preforms BFS fill on positions in the array that are identified as solids
    public void fillInsideSolid(int x, int y) {

        queueForSolids.add(new Pair(x, y));

        visitedInsideSolid[x][y] = 1;

        while (!queueForSolids.isEmpty()) {

            int xPos = (int) queueForSolids.peek().getKey();
            int yPos = (int) queueForSolids.peek().getValue();

            queueForSolids.remove();

            correctedArray[xPos][yPos] = 1;
            westPointForInside(xPos, yPos);
            eastPointForInside(xPos, yPos);
            southPointForInside(xPos, yPos);
            northPointForInside(xPos, yPos);
        }
    }

    //check south point of x,y to see if it is a solid
    public void southPointForInside(int x, int y) {
        if (y + 1 > levelImage.getHeight() - 2 || visitedInsideSolid[x][y + 1] == 1) {
            return;
        }
        visitedInsideSolid[x][y + 1] = 1;
        if (solids[x][y + 1] == 0) {
            queueForSolids.add(new Pair(x, y + 1));
        }
    }

    //check north point of x,y to see if it is a solid
    public void northPointForInside(int x, int y) {
        if (y - 1 < 0 || visitedInsideSolid[x][y - 1] == 1) {
            return;
        }
        visitedInsideSolid[x][y - 1] = 1;
        if (solids[x][y - 1] == 0) {
            queueForSolids.add(new Pair(x, y - 1));
        }
    }

    //check west point of x,y to see if it is a solid
    public void westPointForInside(int x, int y) {
        if (x - 1 < 0 || visitedInsideSolid[x - 1][y] == 1) {
            return;
        }
        visitedInsideSolid[x - 1][y] = 1;
        if (solids[x - 1][y] == 0) {
            queueForSolids.add(new Pair(x - 1, y));
        }
    }

    //check ast point of x,y to see if it is a solid
    public void eastPointForInside(int x, int y) {
        if (x + 1 > levelImage.getWidth() - 2 || visitedInsideSolid[x + 1][y] == 1) {
            return;
        }
        visitedInsideSolid[x + 1][y] = 1;
        if (solids[x + 1][y] == 0) {
            queueForSolids.add(new Pair(x + 1, y));
        }
    }

    //find first platform from the left side of the image
    public Pair findStartingPos() {
        Pair retPair = null;

        for (int xPos = 25; xPos < backGround.getWidth(); xPos++) {
            if (retPair != null) {
                break;
            }
            for (int yPos = 0; yPos < backGround.getHeight() - 40; yPos++) {
                if (solids[xPos][yPos] == 1) {
                    retPair = new Pair(xPos, yPos);
                    break;
                }

            }
        }
        return retPair;

    }

    //Finds positions in the maps with platforms that are  at least maxPlatformDistance apart, returns the
    //x cords for the gap between them and the y cord for the lower platform and the number of these gaps
    public void findGaps() {
        Pair startPoint = this.findStartingPos();
        int distBetween = 0;
        boolean gap = false;
        int startingX = 0, prevX = 0;
        int startingY = 0;

        for (int xPos = (int) startPoint.getKey(); xPos < backGround.getWidth(); xPos++) {
            for (int yPos = 0; yPos < backGround.getHeight() - 20; yPos++) {

                if (solids[xPos][yPos] == 1) {
                    if (gap) {
                        numberOfGaps++;
                        gapPositions.add(new Pair(max(startingY, yPos), new Pair(startingX, prevX)));
                    }
                    startingY = yPos;
                    distBetween = 0;
                    gap = false;
                    startingX = xPos;
                    break;
                }
                if (yPos == backGround.getHeight() - 20 - 1) {
                    distBetween++;
                }

                if (distBetween >= maxDistance) {
                    prevX = xPos;
                    gap = true;
                }
            }
        }
    }


    public int getSolidVal(int x, int y) {
        return solids[x][y];

    }

    public int[][] getSolids() {
        return solids;
    }

    public BufferedImage getBackGround() {
        return backGround;
    }

    public int getNumberOfGaps() {
        return numberOfGaps;
    }

    public ArrayList<Pair> getGapPositions() {
        return gapPositions;
    }
}
