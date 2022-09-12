package myApp;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    JPanel currentPane;

    public App() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 675);
        setLayout(new BorderLayout());

        JPanel currentPane = new LoginPane(this);
        add(currentPane, BorderLayout.CENTER);
        setLocation(40, 20);
        setVisible(true);
    }

    public static void main(String[] arguments) {
        new App();
    }

}
