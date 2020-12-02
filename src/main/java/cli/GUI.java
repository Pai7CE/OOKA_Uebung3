package cli;

import javax.swing.*;
import java.awt.*;

public class GUI {

    JFrame frame;
    JTextArea textArea;

    // Used some code from: https://stackoverflow.com/questions/50085582/display-java-console-in-a-external-jframe
    public GUI(String name) {
        frame = new JFrame(name); // init
        textArea = new JTextArea(24,80);

        //frame settings
        frame.setSize(600,600);
        frame.setResizable(false);

        //textarea config
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setEditable(false);

        frame.add(textArea);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void printInGUI(String string){
        textArea.append(string + "\n"); // all outputs get their own line
    }
}
