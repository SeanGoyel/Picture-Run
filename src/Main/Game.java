package Main;

import javax.swing.*;
import java.awt.*;

public class Game {

    public static void main(String[] args) {
        JFrame frame = new JFrame("Picture Run");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        frame.add(new GamePanel(), BorderLayout.CENTER);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }
}
