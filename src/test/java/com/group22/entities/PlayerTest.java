package com.group22.entities;

import com.group22.GamePanel;
import com.group22.KeyHandler;
import com.group22.Sound;
import com.group22.objects.ObjectDoor;
import com.group22.objects.ObjectKey;
import com.group22.objects.ObjectTrap;
import com.group22.objects.ObjectVaccine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PlayerTest {

    private Player player;
    private GamePanel gamePanel;
    private KeyHandler keyHandler;

    @BeforeEach
    void setUp() {
        gamePanel = new GamePanel();
        keyHandler = gamePanel.keyH;
        player = new Player(gamePanel, keyHandler);

        player.setDefaultValues();

        // Create and add vaccine and key objects to the gamePanel's obj array
        ObjectVaccine vaccine = new ObjectVaccine(gamePanel);
        gamePanel.obj[0] = vaccine; // Add vaccine object at index 0

        ObjectKey key = new ObjectKey(gamePanel);
        gamePanel.obj[1] = key; // Add key object at index 1

        // Initialize zombies
        for (int i = 0; i < gamePanel.zombie.length; i++) {
            gamePanel.zombie[i] = new Zombie(gamePanel, 1); // Assuming '1' is a valid zombie type
        }
    }

    @Test
    void testPlayerInitialization() {
        assertEquals("Carl", player.name, "Player name should be initialized as Carl.");
        assertEquals(0, player.hasKey, "Player should start with no keys.");
        assertEquals(0, player.hasVaccine, "Player should start with no vaccines.");
        assertEquals(6, player.maxLife, "Player max life should be initialized.");
        assertEquals(player.maxLife, player.life, "Player should start with max life.");
    }

    @Test
    void testPlayerMovementUp() {
        keyHandler.upPressed = true;
        int originalY = player.worldY;
        player.update();
        assertEquals(originalY - player.speed, player.worldY, "Player should move up.");
    }

    @Test
    void testPlayerMovementDown() {
        keyHandler.downPressed = true;
        int originalY = player.worldY;
        player.update();
        assertEquals(originalY + player.speed, player.worldY, "Player should move down.");
    }

    @Test
    void testPlayerMovementLeft() {
        keyHandler.leftPressed = true;
        int originalX = player.worldX;
        player.update();
        assertEquals(originalX - player.speed, player.worldX, "Player should move left.");
    }

    @Test
    void testPlayerMovementRight() {
        keyHandler.rightPressed = true;
        int originalX = player.worldX;
        player.update();
        assertEquals(originalX + player.speed, player.worldX, "Player should move right.");
    }

    @Test
    public void testSpriteAnimationUpdate() {
        // Call update method 60 times
        for (int i = 0; i < 60; i++) {
            player.update();
        }
        // Assert that spriteNum cycles through 1 to 3
        assertTrue( player.spriteNum >= 1 && player.spriteNum <= 3, "Sprite number should be between 1 and 3");
    }

    @Test
    void testPlayerDamage() {
        int initialLife = player.life;
        player.interactZombie(0); // Assuming a method to simulate zombie interaction
        assertTrue(player.isDamaged, "Player should be marked as damaged.");
        assertEquals(initialLife - 1, player.life, "Player should lose 1 life.");
    }

    @Test
    void testPlayerPickUpKey() {
        player.pickUpObject(1); // index 1 for the key
        assertEquals(1, player.hasKey, "Player should have 1 key.");
    }

    @Test
    void testPlayerPickUpVaccine() {
        player.pickUpObject(0); // index 0 for the vaccine
        assertEquals(1, player.hasVaccine, "Player should have 1 vaccine.");
    }

    @Test
    void testRestorePosition() {
        player.restorePos();
        assertEquals(gamePanel.tileSize * 39, player.worldX, "Player should be at default X position.");
        assertEquals(gamePanel.tileSize * 21, player.worldY, "Player should be at default Y position.");
        assertEquals("down", player.direction, "Player direction should be reset to down.");
    }

    @Test
    void testPlayerInvincibility() {
        player.invincible = true;
        player.invincibleCounter = 59;
        player.update();
        assertTrue(player.invincible, "Player should still be invincible.");
        assertEquals(60, player.invincibleCounter, "Invincibility counter should increment.");

        player.update();
        assertFalse(player.invincible, "Player should no longer be invincible.");
        assertEquals(0, player.invincibleCounter, "Invincibility counter should reset.");
    }

    @Test
    void testInteractWithZombieWhileHavingVaccine() {
        player.hasVaccine = 1;
        int initialVaccineCount = player.hasVaccine;
        player.interactZombie(0); // Assuming 0 is a valid zombie index
        assertEquals(initialVaccineCount - 1, player.hasVaccine, "Player should use a vaccine.");
        // Verify other state changes if any
    }

    @Test
    void testPlayerInteractionWithZombie() {
        Zombie testZombie = new Zombie(gamePanel, 1); // Assuming 1 is a valid zombie type
        gamePanel.zombie[0] = testZombie; // Add testZombie to the gamePanel's zombie array

        // Simulate interaction without vaccine
        player.interactZombie(0);
        assertTrue(player.isDamaged, "Player should be marked as damaged after zombie interaction.");
        assertEquals(player.maxLife - 1, player.life, "Player's life should decrease by 1.");

        // Reset player's state
        player.isDamaged = false;
        player.life = player.maxLife;
        player.invincible = false;

        // Simulate interaction with vaccine
        player.hasVaccine = 1;
        player.interactZombie(0);
        assertEquals(0, player.hasVaccine, "Player should use a vaccine after interaction with zombie.");
        assertTrue(testZombie.isRemoveThis(), "Zombie should be marked for removal after being cured."); // Changed to assertTrue
    }

    @Test
    void testDrawMethod() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        assertDoesNotThrow(() -> player.draw(mockGraphics), "Draw method should execute without errors.");
    }

    @Test
    void testSpriteBasedOnDirection() {
        // Mock the setup method to avoid real image loading
        Player playerMock = Mockito.spy(player);
        BufferedImage dummyImage = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);

        // Mock the image loading
        Mockito.doReturn(dummyImage).when(playerMock).setup(Mockito.anyString());

        // Set the player's state for testing
        playerMock.direction = "up";
        playerMock.spriteNum = 2;

        // Test if the sprite returned by setup is the mocked image
        BufferedImage expectedSprite = dummyImage;
        BufferedImage actualSprite = playerMock.setup("up2");
        assertEquals(expectedSprite, actualSprite, "The sprite returned by setup should be the mocked image.");
    }

    @Test
    public void GetDamageImage() {
        // Arrange
        Player playerMock = Mockito.spy(player);
        playerMock.damageImage1 = mock(BufferedImage.class);
        playerMock.damageImage2 = mock(BufferedImage.class);
        playerMock.damageImage3 = mock(BufferedImage.class);
    
        // Act & Assert for damageImage1
        playerMock.damageAnimationFrame = 0; // Should return damageImage1
        assertEquals(playerMock.damageImage1, playerMock.getDamageImage(), "Damage image should be damageImage1");
    
        // Act & Assert for damageImage2
        playerMock.damageAnimationFrame = playerMock.damageAnimationDuration / 4 + 1; // Should return damageImage2
        assertEquals(playerMock.damageImage2, playerMock.getDamageImage(), "Damage image should be damageImage2");
    
        // Act & Assert for damageImage3
        playerMock.damageAnimationFrame = (playerMock.damageAnimationDuration * 2) / 3 + 1; // Should return damageImage3
        assertEquals(playerMock.damageImage3, playerMock.getDamageImage(), "Damage image should be damageImage3");
    }

    @Test
    void testPlayerInteractionWithDoor() {
        // Arrange
        ObjectDoor door = new ObjectDoor(gamePanel);
        door.worldX = player.worldX;
        door.worldY = player.worldY;
        gamePanel.obj[3] = door;
        player.hasKey = 2;

        player.pickUpObject(3);

        assertFalse( gamePanel.ui.gameFinished, "Door should not open when player does not have enough keys");

        player.hasKey = 3;
        player.pickUpObject(3);
        assertTrue( gamePanel.ui.gameFinished, "Door should not open when player does not have enough keys");
    }

    @Test
    void testPlayerInteractionWithTrap() {
        // Arrange
        ObjectTrap trap = new ObjectTrap(gamePanel);
        trap.worldX = player.worldX;
        trap.worldY = player.worldY;
        
        // Place the trap in the objects array at the player's position
        gamePanel.obj[4] = trap;
        
        // Assume the player's initial life
        int initialLife = player.life;

        // Act
        player.pickUpObject(4);

        // Assert
        assertTrue(player.isDamaged, "Player should be damaged by the trap");
        assertTrue(player.invincible, "Player should be invincible after trap damage");
        assertEquals(initialLife - 1, player.life, "Player's life should decrease");
    }

    @Test
    void testGameOver() {
        player.life = 0;
        player.update();
        assertEquals(gamePanel.gameOverState, gamePanel.gameState, "Game state should be game over");
    }

    @Test
    void testDrawDamageImage() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        player.isDamaged = true;
        for (int i = 0; i<50; i++){
            player.update();
            player.draw(mockGraphics);
        }
        assertDoesNotThrow(() -> player.draw(mockGraphics));
    }
}
