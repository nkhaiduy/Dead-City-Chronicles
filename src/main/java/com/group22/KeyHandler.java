package com.group22;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * The KeyHandler class implements the KeyListener interface and handles all keyboard
 * interactions for controlling the game state and player movement.
 */
public class KeyHandler implements KeyListener {

    GamePanel gp;

    public  boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    /**
     * Constructor for KeyHandler that initializes with a GamePanel instance.
     * @param gp The GamePanel instance to which this KeyHandler belongs.
     */
    public KeyHandler(GamePanel gp){
        this.gp = gp;
        
    }
    @Override
    public void keyTyped(KeyEvent e){

    }
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState){
            titleState(code);
        }
        if (gp.gameState == gp.playState){
            playState(code);
        }

        else if (gp.gameState == gp.pauseState){
            pauseState(code);
        }

        else if (gp.gameState == gp.settingState){
            settingState(code);
        }
        else if (gp.gameState == gp.gameOverState){
            gameOverState(code);
        }
        else if(gp.gameState == gp.ruleState) {
        	ruleState(code);
        }
        else if(gp.gameState == gp.winState) {
        	winState(code);
        }
    }

    /**
     * Controls player movement and interactions during the play state.
     * @param code The KeyCode associated with the key event.
     */
    public void playState(int code){
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = true;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.pauseState;
        }
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.settingState;
            gp.previousState = gp.playState;
        }
    }

    public void winState(int code){
        
        if(code == KeyEvent.VK_ENTER){
            gp.gameState = gp.titleState;
            gp.playMusic(0);
            gp.retry();
        }
    }

    /**
     * Controls settings and interactions during the setting state.
     * @param code The KeyCode associated with the key event.
     */
    public void settingState(int code){
        if(code == KeyEvent.VK_ESCAPE){
            gp.gameState = gp.playState;
        }
        if (code == KeyEvent.VK_ENTER){
            enterPressed = true;
        }

        if (gp.ui != null) {

        int maxCommandNum =0;
        switch(gp.ui.subState){
            case 0: maxCommandNum = 4; break;
            case 3: maxCommandNum = 1; break;
        
        }
         
        if (code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            gp.playSE(5);
            if(gp.ui.commandNum <0){
                gp.ui.commandNum = maxCommandNum;
            }
        }
        if (code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            gp.playSE(5);
            if(gp.ui.commandNum > maxCommandNum){
                gp.ui.commandNum =0;
            }
        }
        if (code == KeyEvent.VK_A){
            if (gp.ui.subState ==0){
                if(gp.ui.commandNum ==1 && gp.music.volumeScale > 0){
                    gp.music.volumeScale--;
                    gp.music.checkVolume();
                    gp.playSE(5);

                }
            }
        }
        if (code == KeyEvent.VK_D){
            if (gp.ui.subState ==0){
                if(gp.ui.commandNum ==1 && gp.music.volumeScale < 5){
                    gp.music.volumeScale++;
                    gp.music.checkVolume();
                    gp.playSE(5);
                }
            }
        }
    }
    
    }

    /**
     * Handles key events during the game's pause state, allowing the player to
     * resume the game.
     *
     * @param code The KeyCode associated with the pressed key.
     */
    public void pauseState(int code){
        if(code == KeyEvent.VK_P){
            gp.gameState = gp.playState;
        }
    }
    
    /**
     * Handles key events during the game's game over state, allowing the player to
     * retry or return to the title screen.
     *
     * @param code The KeyCode associated with the pressed key.
     */
    public void gameOverState(int code){
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 1;
            }
            gp.playSE(5);
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 1){
                gp.ui.commandNum = 0;
            }
            gp.playSE(5);
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;
                gp.retry();
            }
            else if(gp.ui.commandNum == 1){
                gp.gameState = gp.titleState;
                gp.retry();
            }
        }
    }
    
    /**
     * Handles key events during the game's title state, allowing navigation through
     * the game's main menu.
     *
     * @param code The KeyCode associated with the pressed key.
     */
    public void titleState(int code){
        if(code == KeyEvent.VK_W){
            gp.ui.commandNum--;
            if(gp.ui.commandNum < 0){
                gp.ui.commandNum = 3;
            }
            gp.playSE(5);
        }
        if(code == KeyEvent.VK_S){
            gp.ui.commandNum++;
            if(gp.ui.commandNum > 3){
                gp.ui.commandNum = 0;
            }
            gp.playSE(5);
        }
        if(code == KeyEvent.VK_ENTER){
            if(gp.ui.commandNum == 0){
                gp.gameState = gp.playState;

            }
            else if(gp.ui.commandNum == 1){
                gp.gameState = gp.ruleState;
            }
             else if(gp.ui.commandNum == 2){
                gp.gameState = gp.settingState;
                gp.previousState = gp.titleState;
            }
             else if(gp.ui.commandNum == 3){
                System.exit(0);
            }
        }
    }
    
    /**
     * Handles key events during the game's rule state, providing information on
     * game rules and controls.
     *
     * @param code The KeyCode associated with the pressed key.
     */
    public void ruleState(int code) {
    	if (code == KeyEvent.VK_W || code == KeyEvent.VK_UP) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 1; 
            }
            gp.playSE(5);
        }
        if (code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 1) {
                gp.ui.commandNum = 0;
            }
            gp.playSE(5);
        }
        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                gp.gameState = gp.titleState; 
            }
        }
    }


    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN){
            downPressed = false;
        }

    }
}
