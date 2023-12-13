package com.group22;

import javax.swing.JFrame;

/**
 * The Main class serves as the entry point for the game application.
 * It sets up the main window and initializes the game.
 */
public class Main {

    public static JFrame window;
    /**
     * The main method that starts the game by creating the game window, setting its properties,
     * and initializing game components.
     * @param args Command line arguments passed to the program.
     */
    public static void main(String[] args) {
        window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Dead City: Chronicles");

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}
