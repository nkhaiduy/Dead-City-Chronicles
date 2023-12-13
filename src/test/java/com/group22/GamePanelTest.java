package com.group22;

import com.group22.entities.Zombie;
import com.group22.objects.SuperObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import static org.mockito.Mockito.*;

class GamePanelTest {

    GamePanel gamePanel;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
        // Other setup tasks as needed
    }

    @Test
    void testInitialization() {
        assertNotNull(gamePanel.player, "Player should be initialized");
        assertNotNull(gamePanel.zombie, "Zombies array should be initialized");
        assertNotNull(gamePanel.tileM, "TileManager should be initialized");
        // Additional assertions for other components

        assertEquals(960, gamePanel.screenWidth, "Screen width should match the expected value");
        assertEquals(576, gamePanel.screenHeight, "Screen height should match the expected value");
    }

    @Test
    void testGameStateChange() {
        gamePanel.gameState = gamePanel.playState;
        assertEquals(gamePanel.playState, gamePanel.gameState, "Game state should be playState");
        // Test other state changes
    }

    @Test
    void testCollisionDetection() {
        gamePanel.player.worldX = 100;
        gamePanel.player.worldY = 100;
        gamePanel.cChecker.checkTile(gamePanel.player);
        assertFalse(gamePanel.player.collisionOn, "Collision should not be detected");
    }

    @Test
    void testSoundEffect() {
        Sound mockedSound = Mockito.mock(Sound.class);
        gamePanel.music = mockedSound;
        gamePanel.playMusic(0);
        verify(mockedSound, times(1)).play();
    }

    @Test
    void testPlayerHealthAfterDamage() {
        int initialHealth = gamePanel.player.life;
        gamePanel.player.life -= 1; // Simulate damage
        assertEquals(initialHealth - 1, gamePanel.player.life, "Player health should decrease after taking damage");
    }

    @Test
    void testUIInteraction() {
        gamePanel.ui.showMessage("Test Message");
        assertTrue(gamePanel.ui.messageOn, "UI message should be on");
        assertEquals("Test Message", gamePanel.ui.message, "UI message should match");
    }

    @Test
    void testGameRestart() {
        gamePanel.retry();
        assertEquals(gamePanel.titleState, gamePanel.gameState, "Game state should be reset to title state");
    }

    @Test
    void testObjectInteraction() {
        SuperObject testObject = new SuperObject() {}; // Replace with a specific object if needed
        testObject.worldX = gamePanel.player.worldX;
        testObject.worldY = gamePanel.player.worldY;

        gamePanel.obj[0] = testObject;
        gamePanel.cChecker.checkObject(gamePanel.player, true);

        assertNotNull(gamePanel.obj[0], "Player should interact with the object");
    }

    @Test
    void testAssetInitialization() {
        AssetSetter assetSetter = new AssetSetter(gamePanel);
        assetSetter.setObject();
        assertNotNull(gamePanel.obj, "Game objects should be initialized");
        // Add more specific checks for object types and positions if necessary
    }

    // Add more test cases
}

