package cli;

import javax.swing.*;
import java.awt.*;

public class GUI {

    JFrame frame;
    JTextArea textArea;
    JScrollPane jScrollPane;

    public GUI(String name) {
        // init
        frame = new JFrame(name);
        textArea = new JTextArea(24,80);
        jScrollPane = new JScrollPane(textArea);

        //frame settings
        frame.setSize(600,600);
        frame.setResizable(false);

        //textarea config
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setEditable(false);

        //scroll pane config
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); //scrollbar appears as needed

        //adding components
        frame.add(jScrollPane);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }


    public void print(String s){
        textArea.append(s + "\n"); // all outputs get their own line
        JScrollBar sb = jScrollPane.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());

    }
}
