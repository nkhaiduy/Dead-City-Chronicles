package com.group22;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.Before;
import org.junit.Test;

public class EventHandlerTest {
    private GamePanel gp;
    private EventHandler eventHandler;

    @Before
    public void setUp() {
        gp = new GamePanel();
        eventHandler = new EventHandler(gp);
    }

    @Test
    public void testCheckEvent() {
        // Set up initial conditions for the test
        gp.player.worldX = 0;
        gp.player.worldY = 0;
        eventHandler.previousEventX = 0;
        eventHandler.previousEventY = 0;
        eventHandler.canTouchEvent = true;

        // Assuming a tile size of 2 for simplicity
        gp.tileSize = 2;

        // Perform the checkEvent method
        eventHandler.checkEvent();

        // Assert that canTouchEvent is still true since the player has not moved more than 1 tile away
        assertTrue(eventHandler.canTouchEvent);

        // Move the player more than 1 tile away and perform the checkEvent method again
        gp.player.worldX = 5;
        gp.player.worldY = 5;
        eventHandler.checkEvent();

        // Assert that canTouchEvent is now true since the player has moved more than 1 tile away
        assertTrue(eventHandler.canTouchEvent);

        // Reset player position for future tests
        gp.player.worldX = gp.player.worldY = 0;
    }
    
    @Test
    public void testHit() {
        // Assuming a tile size of 2 for simplicity
        gp.player.worldX = 0;
        gp.player.worldY = 0;
        gp.player.solidArea.x = 0;
        gp.player.solidArea.y = 0;

        assertTrue(eventHandler.hit(0, 0)); // Player is on the event rectangle

        assertFalse(eventHandler.hit(1, 1)); // Player is not on the event rectangle

        // Reset player and event rectangle positions
        gp.player.worldX = gp.player.worldY = 0;
        gp.player.solidArea.x = gp.player.solidArea.y = 0;
        eventHandler.eventRect[0][0].x = eventHandler.eventRect[0][0].eventRectDefaultX;
        eventHandler.eventRect[0][0].y = eventHandler.eventRect[0][0].eventRectDefaultY;
    }

    @Test
    public void testDamage() {
        int initialLife = gp.player.life;
        eventHandler.damage();
        assertEquals(initialLife - 1, gp.player.life);
    }

    @Test
    public void testHeal() {
        int initialLife = gp.player.life;
        eventHandler.heal();
        assertEquals(initialLife + 1, gp.player.life);
    }
}
