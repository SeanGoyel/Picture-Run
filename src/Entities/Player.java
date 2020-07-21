package Entities;

import Main.GamePanel;
import javafx.util.Pair;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Player {

    private boolean right = false, left = false, jumping = false, falling = true;
    private double x, y;
    private int width, height;

    private double jumpSpeed = 5;
    private double currentJumpingSpeed = jumpSpeed;

    private double fallSpeed = 4;
    private double currentFallSpeed = .1;
    private int[][] solids;
    private int[][] blockPositions;

    private boolean dead = false;

    private Color playerColor = Color.BLUE;
    private int changeColourRate = 5;
    private int everyNTimes = 4;
    private int currentBlueVal = 255;
    private Color outlineColor;
    private Color innerColor;

    private boolean onBlock;


    public Player(int x, int y, int width, int height, int[][] solids) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solids = solids;
        outlineColor = Color.blue;

    }

    public void tick(int[][] blockPositions, ArrayList<Pair> blockSpeeds) {
        this.blockPositions = blockPositions;

        if (!dead && (this.ranIntoFromLeft() || this.ranIntoFromRight() && (jumping || falling))) {
            dead = true;
        }

        if (!dead) {


            if (right) {
                if (!isPossibleToMoveRight()) {
                    right = false;
                }
            }

            if (left) {
                if (!isPossibleToMoveLeft()) {
                    left = false;
                }
            }

            //Keep player in bounds
            if (x > GamePanel.WIDTH - width) {
                x = GamePanel.WIDTH - width;
                right = false;
            }


            if (jumping) {
                y -= currentJumpingSpeed;
                if (isTopCollision((int) x, (int) y)) {
                    jumping = false;
                }
                currentJumpingSpeed -= .2;
                if (currentJumpingSpeed <= 0) {
                    currentJumpingSpeed = jumpSpeed;
                    jumping = false;

                }
            }

            //Conditions to not be falling
            if (y > 0 &&
                    x < GamePanel.WIDTH-40 &&
                    x > 0 &&
                    isBottomCollision((int) x, (int) y + 1)) {
                falling = false;
            } else {
                falling = true;
            }

            if (onBlock) {
                for (Pair pair : blockSpeeds) {
                    if (x <= (int) pair.getKey()) {
                        x += (int) pair.getValue();
                        break;
                    }
                }
            }


            if (falling) {
                y += currentFallSpeed;
                if (currentFallSpeed < fallSpeed) {
                    currentFallSpeed += .1;
                }
            }

            if (!falling) {
                y = Math.floor(y);
                currentFallSpeed = 0.1;
            }

            //Update player color
            outlineColor = (new Color(0, 255, currentBlueVal));
            innerColor = (new Color(0, currentBlueVal, 255));

            if (currentBlueVal - changeColourRate < 0 || currentBlueVal - changeColourRate > 255) {
                changeColourRate *= -1;
            }
            currentBlueVal -= changeColourRate;

            if (y + height >= GamePanel.HEIGHT-1) {
                dead = true;
            }

            //If player is dead
        } else {
            playerColor = Color.RED;
            if (everyNTimes == 4) {
                width--;
                height--;
                everyNTimes = 0;
            }
            everyNTimes++;
            y += currentFallSpeed;
            if (currentFallSpeed < fallSpeed) {
                currentFallSpeed += .1;
            }

        }
    }


    public void draw(Graphics g) {
        if (!dead) {
            g.setColor(outlineColor);
            g.fillRect(((int) x) - 2, ((int) y) - 2, width + 4, height + 4);
        }
        g.setColor(playerColor);
        g.fillRect((int) x, (int) y, width, height);

        if (!dead) {
            g.setColor(innerColor);
            g.fillRect(((int) x) + 4, ((int) y) + 4, width - 8, height - 8);
        }


    }


    //If player can be moved right then move player otherwise return false
    public boolean isPossibleToMoveRight() {
        if (solids[(int) x + width + 5][(int) y + height] == 0 &&
                blockPositions[(int) x + width + 5][(int) y + height] != 2) {
            x += 5;
            return true;
        }
        for (int i = 1; i < 7; i++) {

            if (solids[(int) x + width + 5][(int) y + height - i - 1] == 0 &&
                    blockPositions[(int) x + width + 5][(int) y + height - i - 1] != 2) {
                y -= i;
                x += 5;
                return true;
            }
        }

        return false;

    }

    //If player can be moved left then move player otherwise return false
    public boolean isPossibleToMoveLeft() {
        if (x - 5 < 0) {
            return false;
        }

        if (solids[(int) x - 5][(int) y + height] == 0 &&
                blockPositions[(int) x - 5][(int) y + height] != 2) {
            x -= 5;

            return true;
        }
        for (int i = 1; i < 7; i++) {

            if (solids[(int) x - 5][(int) y + height - i] == 0 &&
                    blockPositions[(int) x - 5][(int) y + height - i] != 2) {
                y -= i;
                x -= 5;
                return true;
            }
        }

        return false;
    }


    public boolean isBottomCollision(int x, int y) {

        for (int i = 0; i < width; i++) {

            if (blockPositions[x + i][y + height] == 2) {
                onBlock = true;
                return true;
            } else {
                onBlock = false;
            }

            if (solids[x + i][y + height] == 1) {
                return true;
            }
        }

        return false;

    }

    public boolean isTopCollision(int x, int y) {
        if (y < 0) {
            return true;
        }

        for (int i = 0; i < width; i++) {
            if (solids[x + i][y] == 1 || blockPositions[x + i][y] == 2) {
                return true;
            }
        }

        return false;

    }

    //If the player is making contact with five more pixels on its right, return true
    public boolean ranIntoFromRight() {
        int tolerance = 0;
        for (int i = 0; i < height; i++) {
            if (solids[(int) x + width][(int) y + i] == 1 || blockPositions[(int) x + width][(int) y + i] == 2) {
                tolerance++;
                if (tolerance == 5) {
                    return true;
                }
            }
        }

        return false;

    }

    //If the player is making contact with five more pixels on its left, return true
    public boolean ranIntoFromLeft() {
        int tolerance = 0;
        for (int i = 0; i < height; i++) {
            if (solids[(int) x][(int) y + i] == 1 || blockPositions[(int) x][(int) y + i] == 2) {
                tolerance++;
                if (tolerance == 5) {
                    return true;
                }
            }
        }

        return false;

    }

    public void keyReleased(int k) {
        if (k == KeyEvent.VK_RIGHT) {
            right = false;
        }

        if (k == KeyEvent.VK_LEFT) {
            left = false;
        }
    }

    public void keyPressed(int k) {
        if (k == KeyEvent.VK_RIGHT) {
            right = true;
        }

        if (k == KeyEvent.VK_LEFT) {
            left = true;

        }

        if (k == KeyEvent.VK_SPACE) {
            jumping = true;

        }
    }

    public boolean isPlayerDead() {
        return dead;
    }
    public double playerXPos() {
        return x;
    }



}


