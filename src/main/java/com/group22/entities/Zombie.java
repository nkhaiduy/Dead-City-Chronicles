package com.group22.entities;

import com.group22.GamePanel;

import com.group22.UtilityTool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * The Zombie class extends the Entity class with properties and methods specific to zombies in the game.
 * It manages zombie animations, movement, AI behavior, and interaction with the player and environment.
 */
public class Zombie extends Entity{
	GamePanel gp;
    public int worldX;
    public int worldY;
    public int speed;
    public String direction;
	
    private int zombieType;
    public int solidAreaDefaultX, solidAreaDefaultY;
    private boolean removeThis = false;

    BufferedImage up1, up2, up3,up4, down1,down2,down3, down4,left1,left2,left3,left4,right1, right2,right3,right4;

    public final int screenX;
    public final int screenY;
    public int actionLockCounter = 0;
    private Map<String, BufferedImage[]> spriteMap;

    /**
     * Constructor for the Zombie class.
     * @param gp The GamePanel object that the zombie will interact with.
     * @param zombieType The type identifier for the zombie.
     */
    public Zombie(GamePanel gp, int zombieType) {
        this.gp = gp;
        this.zombieType = zombieType;


        screenX = gp.screenWidth/2 - (gp.tileSize);
        screenY = gp.screenHeight/2 - (gp.tileSize/2);

        solidArea = new Rectangle(8, 16, 32, 32);

        setDefaultValues();
        getZombieImage();
        initializeSpriteMap();
    }

    /**
     * Sets the default values for the zombie's speed and initial direction.
     * Zombie should be slower than the player.
     */
    public void setDefaultValues(){
        speed = 3; 
        direction = "down"; 
    }
    
    /**
     * Loads and scales the image for the zombie.
     * @param imageName Name of the image file.
     * @return The scaled BufferedImage.
     */
    public BufferedImage setup(String imageName){
        UtilityTool uTool = new UtilityTool();
        BufferedImage image = null;

        try{
            image = ImageIO.read(getClass().getResourceAsStream(imageName + ".png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch (IOException e){
            e.printStackTrace();
        }
        return image;
    }

    /**
     * Loads and sets up the zombie images for different directions and animations.
     */
    public void getZombieImage() {
        String basePath = "/zombie" + zombieType + "/zombie" + zombieType + "_";
        up1 = setup(basePath + "run_right0");
        up2 = setup(basePath + "run_right1");
        up3 = setup(basePath + "run_right2");
        up4 = setup(basePath + "run_right3");
        
        down1 = setup(basePath + "run_left0");
        down2 = setup(basePath + "run_left1");
        down3 = setup(basePath + "run_left2");
        down4 = setup(basePath + "run_left3");

        left1 = setup(basePath + "run_left0");
        left2 = setup(basePath + "run_left1");
        left3 = setup(basePath + "run_left2");
        left4 = setup(basePath + "run_left3");
        
        right1 = setup(basePath + "run_right0");
        right2 = setup(basePath + "run_right1");
        right3 = setup(basePath + "run_right2");
        right4 = setup(basePath + "run_right3");
    }
    
    /**
     * Updates the state and position of the zombie each frame.
     * Handles collision detection, animation state, and direction logic for the Zombie.
     */
    public void update() {
        int xDistance = gp.player.worldX - worldX;
        int yDistance = gp.player.worldY - worldY;
        String xDirection = xDistance > 0 ? "right" : "left";
        String yDirection = yDistance > 0 ? "down" : "up";

        setZombieDirection(xDistance, yDistance, xDirection, yDirection);

        int nextX = worldX;
        int nextY = worldY;

        switch (direction) {
            case "up": nextY -= speed; break;
            case "down": nextY += speed; break;
            case "left": nextX -= speed; break;
            case "right": nextX += speed; break;
        }

        handleCollision(xDistance, yDistance, nextX, nextY);
        updateAnimation();
    }
    
    /**
     * Sets the direction for a zombie based on the distances in the x and y directions from the player.
     * This method implements a simple AI for the zombie, where the zombie moves in the direction
     * of the greatest distance from the player. This logic is applied every 30 actions.
     *
     * @param xDistance the distance between the zombie and the player in the x-axis.
     * @param yDistance the distance between the zombie and the player in the y-axis.
     * @param xDirection the direction the zombie should move in the x-axis.
     * @param yDirection the direction the zombie should move in the y-axis.
     */
    private void setZombieDirection(int xDistance, int yDistance, String xDirection, String yDirection) {
        actionLockCounter ++;
        if (actionLockCounter == 30){
            if (Math.abs(xDistance) > Math.abs(yDistance)) {
                direction = xDirection;
            } else {
                direction = yDirection;
            }
            actionLockCounter = 0;
        }
    }

    /**
     * Handles collision detection and response for entities
     * This method checks for collisions with the player and other entities, and updates the state
     * accordingly. If a collision with the player occurs, it checks the player's status (like having a vaccine
     * or being invincible) and updates the player's state (like reducing life or curing the zombie). 
     * The method also determines the movement of the entity after checking for possible collisions in its
     * intended direction.
     *
     * @param xDistance The horizontal distance between this entity and its target or player.
     * @param yDistance The vertical distance between this entity and its target or player.
     * @param nextX The next x-coordinate position for this entity.
     * @param nextY The next y-coordinate position for this entity.
     */
    private void handleCollision(int xDistance, int yDistance, int nextX, int nextY) {
        collisionOn = gp.cChecker.checkCollision(this, nextX, nextY);
        boolean playerCollision = gp.cChecker.checkPlayer(this, nextX, nextY);
        gp.cChecker.checkEntity(this, gp.zombie);

        if (playerCollision){
            collisionOn = true;
            if (gp.player.hasVaccine == 0 && gp.player.invincible == false){
                gp.player.isDamaged = true;
                gp.player.life -=1;
                gp.player.invincible = true;
                gp.playSE(3);
            }else if (gp.player.hasVaccine > 0){
                gp.player.hasVaccine--; 
                this.setRemoveThis(true); 
                gp.ui.showMessage("Zombie cured!");
            }
        }

        if (collisionOn) {
            boolean canMoveVertically = !gp.cChecker.checkCollision(this, worldX, worldY + (yDistance > 0 ? speed : -speed));
            boolean canMoveHorizontally = !gp.cChecker.checkCollision(this, worldX + (xDistance > 0 ? speed : -speed), worldY);
            
            if ((direction.equals("left") || direction.equals("right")) && canMoveVertically) {
                direction = (yDistance > 0) ? "down" : "up";
            } else if ((direction.equals("up") || direction.equals("down")) && canMoveHorizontally) {
                direction = (xDistance > 0) ? "right" : "left";
            }
        } else {
            worldX = nextX;
            worldY = nextY;
        }
    }
    
    /**
     * Updates the animation of a sprite by cycling through its different states.
     * The method tracks the number of frames that have passed and changes the sprite's image
     * every 12 frames. The sprite's image cycles through four different states.
     * This method ensures that the animation of the sprite is updated consistently to reflect
     * these changes in its appearance.
     */
    private void updateAnimation() {
        spriteCounter++;
        if(spriteCounter > 12){
            if(spriteNum == 1) {
                spriteNum = 2;
            }else if (spriteNum == 2){
                spriteNum = 3;
            }
            else if (spriteNum == 3){
                spriteNum = 4;
            }
            else if (spriteNum == 4){
                spriteNum = 1;
            }
            spriteCounter = 0;
        }
    }
    
    /**
     * Initializes the spriteMap with arrays of BufferedImages for each direction.
     * This method creates a new HashMap for spriteMap and populates it with key-value pairs.
     * Each key is a string representing a direction ("up", "down", "left", "right"),
     * and the associated value is an array of BufferedImages for that direction.
     * 
     * This method should be called during the construction of the Zombie object to ensure
     * that the spriteMap is properly initialized before it's used in the draw method.
     */
    private void initializeSpriteMap() {
        spriteMap = new HashMap<>();
        spriteMap.put("up", new BufferedImage[]{up1, up2, up3, up4});
        spriteMap.put("down", new BufferedImage[]{down1, down2, down3, down4});
        spriteMap.put("left", new BufferedImage[]{left1, left2, left3, left4});
        spriteMap.put("right", new BufferedImage[]{right1, right2, right3, right4});
    }
    
    /**
     * Draws the zombie on the screen.
     * @param g2 Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {
        int playerScreenX = gp.screenWidth / 2 - (gp.tileSize / 2);
        int playerScreenY = gp.screenHeight / 2 - (gp.tileSize / 2);

        int zombieScreenX = worldX - gp.player.worldX + playerScreenX;
        int zombieScreenY = worldY - gp.player.worldY + playerScreenY;

        BufferedImage[] images = spriteMap.get(direction);
        BufferedImage image = null;

        if (images != null && spriteNum >= 1 && spriteNum <= images.length) {
            image = images[spriteNum - 1];
        }

        if (image != null) {
            g2.drawImage(image, zombieScreenX, zombieScreenY, gp.tileSize, gp.tileSize, null);
        }
    }

    /**
     * Sets the status of whether this zombie should be removed.
     * @param status The removal status of the zombie.
     */
    public void setRemoveThis(boolean status) {
        removeThis = status;
    }

    /**
     * Checks if this zombie is marked for removal.
     * @return The removal status of the zombie.
     */
    public boolean isRemoveThis() {
        return removeThis;
    }


}