package com.group22;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.awt.event.KeyEvent;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class KeyHandlerTest {

    KeyHandler keyHandler;
    GamePanel mockGamePanel;
    GamePanel realGamePanel;

    @BeforeEach
    void setUp() {
        mockGamePanel = Mockito.mock(GamePanel.class);
        realGamePanel = new GamePanel(); // Create a real instance to get the playState value
        // Set the gameState of the mock GamePanel to the playState value from the real instance.
        mockGamePanel.gameState = realGamePanel.playState;
        keyHandler = new KeyHandler(mockGamePanel);
        mockGamePanel.ui = Mockito.mock(UI.class);
    }

    @Test
    void testKeyPressUp() {
        KeyEvent keyEvent = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'U');
        keyHandler.keyPressed(keyEvent);
        assertTrue(keyHandler.upPressed, "Up key should be pressed");
    }

    @Test
    void testKeyPressDown() {
        KeyEvent keyEvent = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'D');
        keyHandler.keyPressed(keyEvent);
        assertTrue(keyHandler.downPressed, "Down key should be pressed");
    }

    @Test
    void testKeyPressLeft() {
        KeyEvent keyEvent = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'L');
        keyHandler.keyPressed(keyEvent);
        assertTrue(keyHandler.leftPressed, "Left key should be pressed");
    }

    @Test
    void testKeyPressRight() {
        KeyEvent keyEvent = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'R');
        keyHandler.keyPressed(keyEvent);
        assertTrue(keyHandler.rightPressed, "Right key should be pressed");
    }

    @Test
    void testKeyReleaseDown() {
        KeyEvent keyEvent = new KeyEvent(mockGamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_DOWN, 'D');
        keyHandler.keyReleased(keyEvent);
        assertFalse(keyHandler.downPressed, "Down key should be released");
    }

    @Test
    void testKeyReleaseRight() {
        KeyEvent keyEvent = new KeyEvent(mockGamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_RIGHT, 'R');
        keyHandler.keyReleased(keyEvent);
        assertFalse(keyHandler.rightPressed, "Right key should be released");
    }

    @Test
    void testKeyReleasedUp() {
        KeyEvent keyEvent = new KeyEvent(mockGamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'U');
        keyHandler.keyReleased(keyEvent);
        assertFalse(keyHandler.upPressed, "Up key should be released");
    }

    @Test
    void testKeyReleasedLeft() {
        KeyEvent keyEvent = new KeyEvent(mockGamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_LEFT, 'L');
        keyHandler.keyReleased(keyEvent);
        assertFalse(keyHandler.leftPressed, "Left key should be released");
    }

    @Test
    void testTogglePausePlay() {
        // Set initial game state to play
        mockGamePanel.gameState = realGamePanel.playState;

        // Simulate pressing the 'P' key to pause
        KeyEvent keyEventPause = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        keyHandler.keyPressed(keyEventPause);
        assertEquals(realGamePanel.pauseState, mockGamePanel.gameState, "Game state should be pauseState after pressing 'P'");

        // Simulate pressing the 'P' key again to resume play
        KeyEvent keyEventPlay = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_P, 'P');
        keyHandler.keyPressed(keyEventPlay);
        assertEquals(realGamePanel.playState, mockGamePanel.gameState, "Game state should be playState after pressing 'P' again");
    }

    @Test
    void testEscapeKeyPressInPlayState() {
        mockGamePanel.gameState = realGamePanel.playState;

        KeyEvent keyEvent = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, 'E');
        keyHandler.keyPressed(keyEvent);
        assertEquals(realGamePanel.settingState, mockGamePanel.gameState, "Game state should be settingState after pressing Escape in playState");
    }

    @Test
    public void testSettingStateEscapeKey() {
        realGamePanel.ui.subState = 0;

        mockGamePanel.gameState = realGamePanel.settingState;

        KeyEvent keyEventEscape = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ESCAPE, KeyEvent.CHAR_UNDEFINED);

        keyHandler.keyPressed(keyEventEscape);

        assertEquals(mockGamePanel.playState, mockGamePanel.gameState);
    }

    @Test
    public void testSettingStateEnterKey() {
        mockGamePanel.gameState = realGamePanel.settingState;

        KeyEvent keyEventEnter = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);

        keyHandler.keyPressed(keyEventEnter);

        assertTrue(keyHandler.enterPressed);
    }

    @Test
    public void testSettingStateWKey() {
        // Assume initial commandNum is 2
        mockGamePanel.gameState = realGamePanel.settingState;

        mockGamePanel.ui.subState = 0;
        mockGamePanel.ui.commandNum = 2;

        KeyEvent keyEventW = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');

        keyHandler.keyPressed(keyEventW);

        assertEquals(1, mockGamePanel.ui.commandNum);
        assertFalse(keyHandler.enterPressed); // Ensure enterPressed is not affected
    }

    @Test
    public void testSettingStateSKey() {
        // Assume initial commandNum is 2
        mockGamePanel.gameState = realGamePanel.settingState;

        mockGamePanel.ui.subState = 0;
        mockGamePanel.ui.commandNum = 2;

        KeyEvent keyEventS = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');

        keyHandler.keyPressed(keyEventS);

        assertEquals(3, mockGamePanel.ui.commandNum);
        assertFalse(keyHandler.enterPressed); // Ensure enterPressed is not affected
    }

    public void testSettingStateAKey() {
        // Assume initial subState is 0 and commandNum is 1
        mockGamePanel.gameState = realGamePanel.settingState;

        KeyEvent keyEventA = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_A, 'A');

        keyHandler.keyPressed(keyEventA);

        mockGamePanel.ui.subState = 0;
        mockGamePanel.ui.commandNum = 1;
        mockGamePanel.music.volumeScale = 3;

        

        assertEquals(2, mockGamePanel.music.volumeScale);
        assertFalse(keyHandler.enterPressed); // Ensure enterPressed is not affected
    }

    public void testSettingStateDKey() {
        // Assume initial subState is 0 and commandNum is 1
        mockGamePanel.gameState = realGamePanel.settingState;

        
        KeyEvent keyEventD = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_D, 'D');

        keyHandler.keyPressed(keyEventD);
        
        mockGamePanel.ui.subState = 0;
        mockGamePanel.ui.commandNum = 1;
        mockGamePanel.music.volumeScale = 3;


        assertEquals(4, mockGamePanel.music.volumeScale);
        assertFalse(keyHandler.enterPressed); 
    }

    @Test
    public void testGameOverStateWKey() {
        // Assume initial commandNum is 1
        mockGamePanel.gameState = realGamePanel.gameOverState;

        mockGamePanel.ui.commandNum = 1;

        KeyEvent keyEventW = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');

        keyHandler.keyPressed(keyEventW);

        assertEquals(0, mockGamePanel.ui.commandNum);

    }

    @Test
    public void testGameOverStateSKey() {
        // Assume initial commandNum is 0
         mockGamePanel.gameState = realGamePanel.gameOverState;

        mockGamePanel.ui.commandNum = 0;

        KeyEvent keyEventS = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');

        keyHandler.keyPressed(keyEventS);

        assertEquals(1, mockGamePanel.ui.commandNum);
    }

    @Test
    public void testGameOverStateEnterKey() {
        // Assume initial commandNum is 0
        mockGamePanel.gameState = realGamePanel.gameOverState;

        mockGamePanel.ui.commandNum = 0;
        KeyEvent keyEventEnter = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        keyHandler.keyPressed(keyEventEnter);

        assertTrue(mockGamePanel.gameState == mockGamePanel.playState);
    }

    @Test
    public void testGameOverStateEnterKey2() {
        // Assume initial commandNum is 0
        mockGamePanel.gameState = realGamePanel.gameOverState;

        mockGamePanel.ui.commandNum = 1;
        KeyEvent keyEventEnter = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);
        keyHandler.keyPressed(keyEventEnter);

        assertTrue(mockGamePanel.gameState == mockGamePanel.titleState);
    }

    @Test
    public void testTitleStateWKey() {
        // Assume initial commandNum is 3
        mockGamePanel.gameState = realGamePanel.titleState;

        mockGamePanel.ui.commandNum = 3;

        KeyEvent keyEventW = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');

        keyHandler.keyPressed(keyEventW);

        assertEquals(2, mockGamePanel.ui.commandNum);
    }

    @Test
    public void testTitleStateSKey() {

        mockGamePanel.gameState = realGamePanel.titleState;

        // Assume initial commandNum is 0
        mockGamePanel.ui.commandNum = 0;

        KeyEvent keyEventS = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');

        keyHandler.keyPressed(keyEventS);

        assertEquals(1, mockGamePanel.ui.commandNum);
    }

    @Test
    public void testTitleStateEnterKey() {
        // Assume initial commandNum is 0
        mockGamePanel.gameState = realGamePanel.titleState;

        mockGamePanel.ui.commandNum = 0;

        KeyEvent keyEventEnter = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);

        keyHandler.keyPressed(keyEventEnter);

        assertTrue(mockGamePanel.gameState == mockGamePanel.playState);
    }

    @Test
    public void testTitleStateEnterKey2() {
        // Assume initial commandNum is 0
        mockGamePanel.gameState = realGamePanel.titleState;

        mockGamePanel.ui.commandNum = 1;

        KeyEvent keyEventEnter = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);

        keyHandler.keyPressed(keyEventEnter);

        assertTrue(mockGamePanel.gameState == mockGamePanel.ruleState);
    }

     @Test
    public void testTitleStateEnterKey3() {
        // Assume initial commandNum is 0
        mockGamePanel.gameState = realGamePanel.titleState;

        mockGamePanel.ui.commandNum = 2;

        KeyEvent keyEventEnter = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);

        keyHandler.keyPressed(keyEventEnter);

        assertTrue(mockGamePanel.gameState == mockGamePanel.settingState);
        assertTrue(mockGamePanel.previousState == mockGamePanel.titleState);

    }
    
    @Test
    public void testRuleStateWKey() {
        mockGamePanel.gameState = realGamePanel.ruleState;

        // Assume initial commandNum is 0
        mockGamePanel.ui.commandNum = 0;

        KeyEvent keyEventW = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_W, 'W');

        keyHandler.keyPressed(keyEventW);

        assertEquals(1, mockGamePanel.ui.commandNum);
        assertFalse(keyHandler.enterPressed); // Ensure enterPressed is not affected
    }

    @Test
    public void testRuleStateSKey() {
        mockGamePanel.gameState = realGamePanel.ruleState;

        // Assume initial commandNum is 1
        mockGamePanel.ui.commandNum = 1;

        KeyEvent keyEventS = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_S, 'S');

        keyHandler.keyPressed(keyEventS);

        assertEquals(0, mockGamePanel.ui.commandNum);
        assertFalse(keyHandler.enterPressed); // Ensure enterPressed is not affected
    }

    @Test
    public void testRuleStateEnterKey() {
        mockGamePanel.gameState = realGamePanel.ruleState;

        // Assume initial commandNum is 0
        mockGamePanel.ui.commandNum = 0;

        KeyEvent keyEventEnter = new KeyEvent(mockGamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED);

        keyHandler.keyPressed(keyEventEnter);

        assertEquals(mockGamePanel.titleState, mockGamePanel.gameState);
    }

    // Additional test cases can be added for other keys and game states
}
