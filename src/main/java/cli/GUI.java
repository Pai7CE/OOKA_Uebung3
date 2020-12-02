package cli;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class GUI {

    JFrame frame;

    public GUI() {
        initCLI();
    }
    // Used some code from: https://stackoverflow.com/questions/50085582/display-java-console-in-a-external-jframe
    public void initCLI(){
        frame = new JFrame("CLI"); // init
        JTextArea textArea = new JTextArea(24,80);

        //frame settings
        frame.setSize(600,600);
        frame.setResizable(false);

        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        System.setOut(new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                textArea.append(String.valueOf((char) b));
            }
        }));

        frame.add(textArea);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }
}
