package myApp;

import javax.swing.*;
import java.awt.*;

public class Home extends JPanel {
    String username;

    public Home(String username) {
        this.username = username;
        System.out.println("Building home pane");
        JPanel pane = new JPanel();
        JTextPane textPane = new JTextPane();
        textPane.add("Welcome! " + username, new JLabel());
        setSize(1200, 675);
        pane.add(textPane);
        pane.setLayout(new BorderLayout());
        JScrollPane scrollPane = new JScrollPane();
        add(pane);
        add(scrollPane);
        setLayout(new BorderLayout());
        setVisible(true);
    }
}
