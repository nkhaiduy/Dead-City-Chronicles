package com.group22.entities;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.*;

/**
 * Entity is an abstract representation of all moving objects within the game world,
 * including the player, enemies, and NPCs. It contains common attributes like position,
 * speed, and graphics, as well as collision detection fields.
 */
public abstract class Entity {
    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, up3, down1, down2, down3,left1, left2, left3, right1, right2,right3,stop;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;
    public int solidAreaDefaultX, solidAreaDefaultY;

    public Rectangle solidArea;
    public boolean collisionOn = false;

    //Character status
    public int maxLife;
    public int life;

    public boolean invincible = false;
    public int invincibleCounter;

    public abstract void update();

    public abstract void draw(Graphics2D g);
}
