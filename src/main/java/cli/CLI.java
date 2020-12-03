package cli;

import runtime.ComponentAssembler;
import runtime.RuntimeEnvironment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class CLI implements ActionListener {


    JTextField textField;
    JFrame frame;
    JTextArea textArea;
    JScrollPane jScrollPane;
    ComponentAssembler componentAssembler;

    public CLI(String name, ComponentAssembler componentAssembler) {
        this.componentAssembler = componentAssembler;
        // init
        frame = new JFrame(name);
        textArea = new JTextArea(24,80);
        textField = new JTextField();
        textField.addActionListener(this);
        jScrollPane = new JScrollPane(textArea);
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());

        //frame settings
        frame.setSize(600,600);
        frame.setResizable(false);

        //textarea config
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.LIGHT_GRAY);
        textArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        textArea.setEditable(false);

        textArea.append("Commands: \n" +
                "startRE \n" +
                "stopRE \n" +
                "addComponent [full component name] \n" +
                "removeComponent [full component name] \n" +
                "startComponent [full component name] \n" +
                "stopThread [full thread name] \n" +
                "getStates \n \n");

        //scroll pane config
        jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); //scrollbar appears as needed

        //adding components
        jPanel.add(jScrollPane, BorderLayout.CENTER);
        jPanel.add(textField, BorderLayout.SOUTH);
        frame.add(jPanel);

        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void print(String s){
        textArea.append(s + "\n"); // all outputs get their own line
        JScrollBar sb = jScrollPane.getVerticalScrollBar();
        sb.setValue(sb.getMaximum());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String text = textField.getText();
        print(text);
        textField.setText("");
        String[] s = text.split(" ");
        try {
            if (s[0].equals("startRE")) {
                print(componentAssembler.startRE());
            }
            else if(s[0].equals("stopRE")){
                print(componentAssembler.stopRE());
            }
            else if(s[0].equals("addComponent")){
                print(componentAssembler.addComponentRE(s[1]));
            }
            else if(s[0].equals("removeComponent")){
                print(componentAssembler.removeComponentRE(s[1]));
            }
            else if(s[0].equals("startComponent")){
                print(componentAssembler.startComponent(s[1]));
            }
            else if(s[0].equals("stopThread")){
                print(componentAssembler.stopComponent(s[1]));
            }
            else if(s[0].equals("getStates")){
                print(componentAssembler.getStates());
            }
        textArea.append("\n");
        }catch(Exception ee){
            textArea.append("Error at: " +text);
        }

        //Make sure the new text is visible, even if there
        //was a selection in the text area.
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }
}
