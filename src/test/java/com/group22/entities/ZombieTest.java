package com.group22.entities;

import com.group22.GamePanel;
import com.group22.KeyHandler;
import java.awt.Graphics2D;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import junit.framework.TestCase;

/**
 * Unit test for the Zombie class.
 */
public class ZombieTest extends TestCase {
    
    //private Zombie zombie;
    private GamePanel mockGamePanel;
    private Player mockPlayer;
    @Mock
    private KeyHandler mockKeyHandler;


    @Override
    protected void setUp() throws Exception {
        super.setUp();
        MockitoAnnotations.initMocks(this); // Initialize Mockito mocks
        mockGamePanel = new GamePanel(); 
        mockPlayer = new Player(mockGamePanel, mockKeyHandler);
        for (int i = 0; i < mockGamePanel.zombie.length; i++) {
            mockGamePanel.zombie[i] = new Zombie(mockGamePanel, 1);
        }
    }

    public void testZombieConstructor() {
        Zombie zombie = new Zombie(mockGamePanel, 1); // Assuming 1 is a valid zombie type
        // Initialize zombies
        assertNotNull("Zombie object should not be null", zombie);
    }

    public void testSetDefaultValues() {
        Zombie zombie = new Zombie(mockGamePanel, 1);
        zombie.setDefaultValues();
        assertEquals("Default speed should be set", 3, zombie.speed);
        assertEquals("Default direction should be down", "down", zombie.direction);
    }
    

    public void testUpdateCalculatesCorrectDirection() {
        // Manually set the actionLockCounter to 29
        mockGamePanel.zombie[0].actionLockCounter = 29;
    
        // Set up player and zombie positions
        mockPlayer.worldX = 100;
        mockPlayer.worldY = 50;
        mockGamePanel.zombie[0].worldX = 50;
        mockGamePanel.zombie[0].worldY = 50;
    
        // Call update
        mockGamePanel.zombie[0].update();
    
        // Check if the direction is calculated correctly
        String expectedDirection = "right";
        assertEquals(expectedDirection, mockGamePanel.zombie[0].direction);
    }

    public void testSpriteAnimationUpdate() {
        // Call update method 60 times
        for (int i = 0; i < 60; i++) {
            mockGamePanel.zombie[0].update();
        }
        // Assert that spriteNum cycles through 1 to 4
        assertTrue("Sprite number should be between 1 and 4", mockGamePanel.zombie[0].spriteNum >= 1 && mockGamePanel.zombie[0].spriteNum <= 4);
    }

    public void testZombieCollisionWithPlayer() {
        // Place the zombie close to the player but not yet colliding
        mockGamePanel.zombie[0].worldX = mockPlayer.worldX - mockGamePanel.zombie[0].speed;
        mockGamePanel.zombie[0].worldY = mockPlayer.worldY;
    
        // Update should move the zombie into the player's position
        mockGamePanel.zombie[0].update();
    
        // Check if collision is detected
        assertTrue("Zombie should collide with player", mockGamePanel.zombie[0].collisionOn);
    }

    public void testZombieRemovalAfterCollisionWithVaccinatedPlayer() {
        // Place the zombie close to the player but not yet colliding
        mockGamePanel.zombie[0].worldX = mockPlayer.worldX - mockGamePanel.zombie[0].speed;
        mockGamePanel.zombie[0].worldY = mockPlayer.worldY;
        mockPlayer.hasVaccine = 1;
        
    
        // Update should move the zombie into the player's position
        mockGamePanel.zombie[0].update();
        if (mockGamePanel.zombie[0].collisionOn) {
            mockPlayer.interactZombie(0);
        }
    
        // Assert that the zombie is marked for removal
        assertTrue("Zombie should be marked for removal after colliding with a vaccinated player", mockGamePanel.zombie[0].isRemoveThis());
    }

    public void testGetZombieImage() {
        Zombie zombie = new Zombie(mockGamePanel, 1);
        zombie.getZombieImage();
        assertNotNull("Zombie image up1 should not be null", zombie.up1);
        assertNotNull("Zombie image down1 should not be null", zombie.down1);
        assertNotNull("Zombie image down1 should not be null", zombie.right1);
        assertNotNull("Zombie image down1 should not be null", zombie.left1);
    }

    public void testZombieMovement() {
        // Set initial position
        mockGamePanel.zombie[0].worldX = 100;
        mockGamePanel.zombie[0].worldY = 100;

        mockPlayer.worldX = 50;
        mockPlayer.worldY = 60;
    
        // Simulate movement
        for (int i = 0; i < 60; i++) { // simulate 60 frames of movement
            mockGamePanel.zombie[0].update();
        }
    
        // Assert that the zombie has moved
        assertTrue("Zombie should have moved from its original position", 
            mockGamePanel.zombie[0].worldX != 100 || mockGamePanel.zombie[0].worldY != 100);
    }

    public void testZombieMovementOtherDirection() {
        // Set initial position
        mockGamePanel.zombie[0].worldX = 50;
        mockGamePanel.zombie[0].worldY = 60;

        mockPlayer.worldX = 100;
        mockPlayer.worldY = 100;
    
        // Simulate movement
        for (int i = 0; i < 60; i++) { // simulate 60 frames of movement
            mockGamePanel.zombie[0].update();
        }
    
        // Assert that the zombie has moved
        assertTrue("Zombie should have moved from its original position", 
            mockGamePanel.zombie[0].worldX != 50 || mockGamePanel.zombie[0].worldY != 60);
    }

    public void testDrawMethod() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        Zombie zombie = new Zombie(mockGamePanel, 1);
        zombie.worldX = 100;
        zombie.worldY = 100;
        zombie.draw(mockGraphics);
        verify(mockGraphics).drawImage(any(), anyInt(), anyInt(), anyInt(), anyInt(), eq(null));
    }

    public void testDrawMultipleTimes() {
        Graphics2D mockGraphics = mock(Graphics2D.class);
        Zombie zombie = new Zombie(mockGamePanel, 1);
        zombie.worldX = 100;
        zombie.worldY = 100;
        
        //move zombie southwest
        mockPlayer.worldX = 0;
        mockPlayer.worldY = 0;
        for (int i = 0; i < 30; i++) {
            zombie.update();
            zombie.draw(mockGraphics);
        }

        //move zombie northeast
        mockPlayer.worldX = 100;
        mockPlayer.worldY = 100;
        for (int i = 0; i < 30; i++) {
            zombie.update();
            zombie.draw(mockGraphics);
        }


        assertDoesNotThrow(() -> zombie.draw(mockGraphics), "Draw method should execute without errors.");
    }    

}