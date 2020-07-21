package GameStates;

import Main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;

public class AddImagesState extends GameState {
    private String[] options = {"Load Images", "Remove All Images", "Back"};
    private int selection = 0;
    private BufferedImage exampleImage;


    public AddImagesState(GameStateChecker gsc) {
        super(gsc);


    }

    @Override
    public void init() {

        try {
            exampleImage = ImageIO.read(new File("/Users/sean/IdeaProjects/Picture Run/data/ExampleLevel.jpeg"));

            BufferedImage resized = new BufferedImage(320, 180, exampleImage.getType());
            Graphics2D g = resized.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage(exampleImage, 0, 0, 320, 180, 0, 0, exampleImage.getWidth(),
                    exampleImage.getHeight(), null);
            g.dispose();

            exampleImage = resized;

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void tick() {

    }

    @Override
    public void draw(Graphics g) {


        g.setColor(new Color(123, 196, 230));
        g.fillRect(0, 0, GamePanel.WIDTH, GamePanel.HEIGHT);
        g.setColor(new Color(14, 93, 203, 255));
        g.fillRect(0, 50, 400, 459);

        g.drawImage(exampleImage, 420, 315, null);

        g.setColor(Color.white);
        g.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        int spacing = 30;
        g.drawString("Take pictures of the images you wish to use.", 420, 72);
        g.drawString("Assure that the top left corner of each image is ", 420, 72 + spacing);
        g.drawString("the same colour as the background. Use everyday", 420, 72 + 2 * spacing);
        g.drawString("objects to construct unique and challenging levels. ", 420, 72 + 3 * spacing);
        g.drawString("If enough space is left between objects you can ", 420, 72 + 4 * spacing);
        g.drawString("press 'B' in game to spawn in new blocks. Then", 420, 72 + 5 * spacing);
        g.drawString("drag and drop them into the file by pressing Load", 420, 72 + 6 * spacing);
        g.drawString("New Images. An example is below.", 420, 72 + 7 * spacing);


        for (int i = 0; i < options.length; i++) {
            if (i == selection) {
                g.setColor(new Color(243, 236, 13, 248));
            } else g.setColor(Color.WHITE);

            g.setFont(new Font("Comic Sans", Font.PLAIN, 40));
            g.drawString(options[i], 10, (GamePanel.HEIGHT / 3 + i * 95));

        }
    }

    @Override
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

                try {
                    Desktop.getDesktop().open(new File("/Users/sean/IdeaProjects/Picture Run/ImagesToMakeLevel"));
                } catch (Exception e) {
                }

            }

            if (selection == 1) {

                File dir = new File("/Users/sean/IdeaProjects/Picture Run/ImagesToMakeLevel");
                for (File file : dir.listFiles())
                    if (!file.isDirectory())
                        file.delete();
            }

            if (selection == 2) {
                gsc.states.push(new MenuState(gsc));
            }

        }

    }


    @Override
    public void keyReleased(int k) {

    }

}


