package com.group22;

import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    @Test
    void testMainWindowProperties() {
        SwingUtilities.invokeLater(() -> {
            // Run the application in the EDT
            Main.main(new String[]{});

            // Retrieve the created JFrame
            JFrame window = (JFrame)Frame.getFrames()[0];

            // Assertions on the main window properties
            assertEquals("Dead City: Chronicles", window.getTitle());
            assertTrue(window.isVisible());
            assertFalse(window.isResizable());


            // Clean up the window
            window.dispose();
        });
    }

    @Test
    void testGamePanelInitialization() {
        SwingUtilities.invokeLater(() -> {
            // Run the application in the EDT
            Main.main(new String[]{});

            // Retrieve the created JFrame and its content pane
            JFrame window = (JFrame)Frame.getFrames()[0];
            Container contentPane = window.getContentPane();

            assertTrue(contentPane.getComponent(0) instanceof GamePanel);
            window.dispose();
        });
    }

    // Add more tests as needed
}
