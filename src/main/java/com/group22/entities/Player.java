package com.group22.entities;

import com.group22.GamePanel;
import com.group22.KeyHandler;
import com.group22.UtilityTool;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


/**
 * The Player class represents the player-controlled character in the game. It extends the Entity class
 * with player-specific functionality such as handling input, updating player status, and drawing the player sprite.
 */
public class Player extends Entity{
    GamePanel gp;
    KeyHandler keyH;
    public String name = "Carl";
    public int hasKey = 0;
    public int hasVaccine = 0;
    public  int screenX;
    public  int screenY;

    BufferedImage damageImage1, damageImage2, damageImage3;
    boolean isDamaged = false;
    int damageAnimationDuration = 9; // Duration of damage animation in frames
    int damageAnimationFrame = 0; // Current frame of the damage animation

    public Map<String, BufferedImage> spriteMap = new HashMap<>();
    private Map<String, ObjectAction> objectActions;

    
    @FunctionalInterface
    public interface ObjectAction {
        void execute(int i);
    }
    
    
    /**
     * Constructor that initializes a new Player object with a reference to the GamePanel and KeyHandler.
     * @param gp The game panel that the player exists within.
     * @param keyH The key handler for managing player input.
     */
    public Player(GamePanel gp, KeyHandler keyH){
        this.gp = gp;
        this.keyH = keyH;

        screenX = gp.screenWidth/2 - (gp.tileSize/2);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8,16,32,32); //x y width height

        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        
        setDefaultValues();
        getPlayerImage();
        initializeSpriteMap();
        initializeObjectActions();
    }
    
    /**
     * Initializes the object actions map. This map is used to associate string keys
     * (representing different types of objects in the game, like "Key", "Vaccine", "Door", "Trap")
     * with their corresponding actions.
     * 
     * Each action is represented by a method reference that conforms to the ObjectAction interface.
     * These actions are executed when the player interacts with different objects in the game.
     * 
     */
    private void initializeObjectActions() {
        objectActions = new HashMap<>();
        objectActions.put("Key", this::pickUpKey);
        objectActions.put("Vaccine", this::pickUpVaccine);
        objectActions.put("Door", this::interactDoor);
        objectActions.put("Trap", this::triggerTrap);
    }
    
    /**
     * Handles the action of picking up a key in the game.
     * When the player interacts with a key object, this method is invoked.
     * It increments the number of keys the player has, removes the key object from the game,
     * plays a sound effect, and shows a message to the player.
     *
     * @param i The index of the key object in the game objects array.
     */
    private void pickUpKey(int i) {
        gp.playSE(1);
        hasKey++;
        gp.obj[i] = null;
        gp.ui.showMessage("You got a key");
    }

    /**
     * Handles the action of picking up a vaccine in the game.
     * When the player interacts with a vaccine object, this method is invoked.
     * It increments the number of vaccines the player has, removes the vaccine object from the game,
     * plays a sound effect, and shows a message to the player.
     *
     * @param i The index of the vaccine object in the game objects array.
     */
    private void pickUpVaccine(int i) {
        gp.playSE(1);
        hasVaccine++;
        gp.obj[i] = null;
        gp.ui.showMessage("You got a vaccine");
    }

    /**
     * Handles the interaction with a door in the game.
     * This method checks if the player has the required number of keys to open the door.
     * If the player has enough keys, the game state is set to finished, and the game music stops.
     * Otherwise, a message is displayed indicating more keys are needed.
     *
     * @param i The index of the door object in the game objects array.
     */
    private void interactDoor(int i) {
        if (hasKey != 3){
            gp.ui.showMessage("You need 3 keys to open the door");
        } else if (hasKey == 3){
            gp.ui.gameFinished = true;
            gp.stopMusic();
        }
    }

    /**
     * Activates the trap mechanism when the player interacts with a trap object.
     * If the player is not currently invincible, this method decreases the player's life,
     * sets the player as damaged and invincible, and plays a sound effect.
     *
     * @param i The index of the trap object in the game objects array.
     */
    private void triggerTrap(int i) {
        if (invincible == false){
            life -=1;
            gp.playSE(3);
            isDamaged = true;
            invincible = true;
        }
    }

    /**
     * Initializes the sprite map with various player sprites.
     * The sprite map is a key-value pair storage where each key is a string representing
     * the player's direction and action, and the value is the corresponding sprite image.
     */

    private void initializeSpriteMap() {
    	spriteMap.put("up1", up1);
    	spriteMap.put("up2", up2);
    	spriteMap.put("up3", up3);
    	spriteMap.put("down1", down1);
    	spriteMap.put("down2", down2);
    	spriteMap.put("down3", down3);
    	spriteMap.put("left1", left1);
    	spriteMap.put("left2", left2);
    	spriteMap.put("left3", left3);
    	spriteMap.put("right1", right1);
    	spriteMap.put("right2", right2);
    	spriteMap.put("right3", right3);
    }

    /**
     * Sets default values for player properties like world position, speed, and life.
     */
    public void setDefaultValues(){
        worldX = gp.tileSize * 39;
        worldY = gp.tileSize * 21;
        speed = 5;
        direction = "down";
        maxLife = 6; //2 lives = 1 heart
        life = maxLife;
    }

    /**
     * Restores the player's position and status to default values.
     */
    public void restorePos(){
        worldX = gp.tileSize * 39;
        worldY = gp.tileSize * 21;
        direction = "down";
        life = maxLife;
        invincible = false;
        hasKey = 0;
        hasVaccine = 0;
    }

    /**
     * Loads and assigns player images for various states and directions.
     */
    public void getPlayerImage(){
        damageImage1 = setup("damage");
        damageImage2 = setup("damage2");
        damageImage3 = setup("damage3");
        up1 = setup("run_right0");
        up2 = setup("run_right1");
        up3 = setup("run_right2");
        down1 = setup("run_left0");
        down2 = setup("run_left1");
        down3 = setup("run_left2");
        left1 = setup("run_left0");
        left2 = setup("run_left1");
        left3 = setup("run_left2");
        right1 = setup("run_right0");
        right2 = setup("run_right1");
        right3 = setup("run_right2");
        stop = setup("no_anim_0");
    }

    /**
     * Helper method to load an image and scale it to the appropriate size.
     * @param imageName The name of the image file to load.
     * @return A BufferedImage object representing the loaded and scaled image.
     */
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }
    
    /**
     * Retrieves the appropriate damage image based on the damage animation frame.
     * @return A bufferedImage object representing damaged player sprite.
     */
    public BufferedImage getDamageImage() {
        if (damageAnimationFrame <= damageAnimationDuration / 4) {
            return damageImage1;
        } else if (damageAnimationFrame <= (damageAnimationDuration * 2) / 3) {
            return damageImage2;
        } else {
            return damageImage3;
        }
    }

    /**
     * updates the player's state each frame, handling movement, collision, and interactions.
     */
    
    public void update() {
        handleMovement();
        handleDamageAnimation();
        checkCollisions();
        checkGameState();
        resetInvincibility();
    }

    private void handleMovement() {
        int deltaX = 0;
        int deltaY = 0;
        if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
            deltaX = getDeltaX();
            deltaY = getDeltaY();
            updateSpriteAnimation();
        }

        if (!collisionOn) {
            worldX += deltaX;
            worldY += deltaY;
        }
    }

    private int getDeltaX() {
        if (keyH.leftPressed) {
            direction = "left";
            return -speed;
        } else if (keyH.rightPressed) {
            direction = "right";
            return speed;
        }
        return 0;
    }

    private int getDeltaY() {
        if (keyH.upPressed) {
            direction = "up";
            return -speed;
        } else if (keyH.downPressed) {
            direction = "down";
            return speed;
        }
        return 0;
    }

    private void updateSpriteAnimation() {
        spriteCounter++;
        if (spriteCounter > 12) {
            spriteNum = spriteNum % 3 + 1;
            spriteCounter = 0;
        }
    }

    private void handleDamageAnimation() {
        if (isDamaged) {
            damageAnimationFrame++;
            if (damageAnimationFrame > damageAnimationDuration) {
                isDamaged = false;
                damageAnimationFrame = 0;
            }
        }
    }

    private void checkCollisions() {
        collisionOn = false;
        gp.cChecker.checkTile(this);
        int objectIndex = gp.cChecker.checkObject(this, true);
        pickUpObject(objectIndex);
        int zombieIndex = gp.cChecker.checkEntity(this, gp.zombie);
        interactZombie(zombieIndex);
    }

    private void checkGameState() {
        if (life <= 0) {
            gp.gameState = gp.gameOverState;
            gp.playSE(6);
        }
    }

    private void resetInvincibility() {
        if (invincible) {
            invincibleCounter++;
            if (invincibleCounter > 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }
    }

    /**
     * Handles interactions when the player picks up an object.
     * @param i The index of the object that the player interacts with.
     */
    public void pickUpObject(int i){
    	
    	 if(i != 999){
    	        String objectName = gp.obj[i].name;
    	        ObjectAction action = objectActions.get(objectName);
    	        if (action != null) {
    	            action.execute(i);
    	        }
    	    }
    }

    /**
     * Draws the player's current sprite to the screen based on the player's state and direction.
     * @param g2 The Graphics2D context to draw on.
     */
    public void draw(Graphics2D g2){
        BufferedImage image = null;

        if (isDamaged){
        	image = getDamageImage();
        	damageAnimationFrame++;
            if (damageAnimationFrame > damageAnimationDuration) {
                isDamaged = false;
                damageAnimationFrame = 0;
            }
        } else{
        	String key = direction + spriteNum;
        	image = spriteMap.getOrDefault(key, stop);
        }

        g2.drawImage(image, screenX, screenY, null);
    }

    /**
     * Handles interaction when player collides with zombie
     * @param i The index of the zombie that is interacting with player
     */
    public void interactZombie(int i){
        if(i != 999) {
            if(hasVaccine > 0) {
                // Player has a vaccine, so they do not take damage, and the zombie is cured
                hasVaccine--; 
                gp.zombie[i].setRemoveThis(true); // Mark the zombie for removal
                gp.ui.showMessage("Zombie cured!");
            } else if(!invincible) {
                // Player does not have a vaccine and is not invincible, so they take damage
                life--;
                isDamaged = true;
                invincible = true;
                gp.playSE(3); // Play damage sound effect
            }
        }
    }
    
    }
