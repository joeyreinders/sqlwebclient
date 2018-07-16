package net.sqlwebclient.view;

import javax.swing.*;

public class SplashScreen extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;

    public SplashScreen() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
    }

    public static void main(String[] args) {
        SplashScreen dialog = new SplashScreen();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
