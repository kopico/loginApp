package myApp;

import java.util.Arrays;
import javax.swing.*;

import com.mongodb.MongoException;
import com.mongodb.client.*;
import com.mongodb.client.model.Filters;

import org.bson.Document;
import org.bson.conversions.Bson;

import java.awt.*;
import java.awt.event.*;

public class LoginPane extends JPanel implements KeyListener, ActionListener {
    JButton login = new JButton("Login");
    JTextField usernameTextField = new JTextField(15);
    JPasswordField passwordField = new JPasswordField(15);

    String username = "";
    char[] password;

    MongoDatabase database;
    MongoCollection<Document> usersCollection;
    MongoClient mongoClient;

    JFrame appFrame;

    public LoginPane(JFrame app) {
        this.appFrame = app;
        JLabel usernameLabel = new JLabel("Username: ");
        JLabel passwordLabel = new JLabel("Password: ");

        usernameTextField.addKeyListener(this);
        passwordField.addKeyListener(this);

        add(usernameLabel);
        add(usernameTextField);
        add(passwordLabel);
        add(passwordField);
        login.addActionListener(this);
        add(login);

        setLayout(new FlowLayout());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == login) {
            boolean outcome = loginStart();
            if (outcome) {
                System.out.println("Login success!");
                JPanel homePane = new Home(username);
                appFrame.remove(this);
                appFrame.add(homePane);
            } else {

            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        Object src = e.getSource();
        if (src == usernameTextField) {
            username = usernameTextField.getText();
        }
        if (src == passwordField) {
            password = passwordField.getPassword();
        }

    }

    String errorMessage = "";

    public boolean loginStart() {
        String uri = "";
        try {
            mongoClient = MongoClients.create(uri);
            database = mongoClient.getDatabase("user_accounts");
            try {
                usersCollection = database.getCollection("user_accounts");
                Bson filter = Filters.eq("username", username);
                FindIterable<Document> queryResult = usersCollection.find(filter);
                try {
                    Document userDoc = queryResult.first();
                    if (Arrays.equals(password, ((String) userDoc.get("password")).toCharArray())) {
                        return true;
                    } else {
                        errorMessage = "Invalid user / password mismatch";
                        return false;
                    }
                } catch (Exception ex) {
                    System.out.println("Exception: " + ex.getMessage());
                    errorMessage = "Invalid user / password mismatch";
                    return false;
                }
            } catch (MongoException me) {
                System.err.println("An error occurred while attempting to run a command: " + me);
                errorMessage = "Invalid user or password mismatch";
                return false;
            }
        } catch (Exception ex) {
            System.err.println("Error: " + ex.getMessage());
            errorMessage = "Connection timeout. Try again later";
            return false;
        }
    }
}
