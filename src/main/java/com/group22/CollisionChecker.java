package com.group22;


import com.group22.entities.Entity;
import com.group22.entities.Zombie;

import java.awt.Rectangle;

/**
 * The CollisionChecker class is responsible for detecting and managing collisions
 * between entities and tiles or objects within the game world.
 */
public class CollisionChecker {

    GamePanel gp;

    /**
     * Constructor for CollisionChecker.
     * @param gp The GamePanel instance that the collisions are checked within.
     */
    public CollisionChecker(GamePanel gp){
        this.gp = gp;
    }

    /**
     * Checks collisions with tiles for the given entity based on their solid area and direction of movement.
     * @param entity The entity whose tile collision is being checked.
     */
    public void checkTile(Entity entity){

        int entityLeftWorldX = entity.worldX + entity.solidArea.x;
        int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.worldY + entity.solidArea.y;
        int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/gp.tileSize;
        int entityRightCol = entityRightWorldX/gp.tileSize;
        int entityTopRow = entityTopWorldY/gp.tileSize;
        int entityBottomRow = entityBottomWorldY/gp.tileSize;

        int tileNum1, tileNum2;

        switch(entity.direction){
            case "up":
                entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
                tileNum1 = gp.tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = gp.tileM.mapTileNum[entityRightCol][entityBottomRow];
                if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true){
                    entity.collisionOn = true;
                }
                break;
        }
    }


    /**
     * Checks for collisions between the specified entity and game objects, determining if the player is affected.
     * @param entity The entity to check collisions for.
     * @param player A boolean flag to indicate if the player is the entity being checked.
     * @return Returns the index of the object collided with, or 999 if no collision occurred.
     */
    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gp.obj.length; i++) {
            if (gp.obj[i] != null) {
                //Get entity's solid area pos
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get obj's solid area pos
                gp.obj[i].solidArea.x = gp.obj[i].worldX + gp.obj[i].solidArea.x;
                gp.obj[i].solidArea.y = gp.obj[i].worldY + gp.obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up": //print for test remove later
                        entity.solidArea.y -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }
                        //System.out.println("up collision");
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }

                        //System.out.println("down collision");
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {

                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                        }

                        //System.out.println("left collision");
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                        if (entity.solidArea.intersects(gp.obj[i].solidArea)) {
                            if (gp.obj[i].collision == true) {
                                entity.collisionOn = true;
                            }
                            if (player == true) {
                                index = i;
                            }
                            //System.out.println("right collision");
                            break;
                        }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                gp.obj[i].solidArea.x = gp.obj[i].solidAreaDefaultX;
                gp.obj[i].solidArea.y = gp.obj[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    /**
     * Checks for a collision in a specified next position (nextX, nextY) for the given entity.
     * @param entity The entity to check future collision for.
     * @param nextX The next x-coordinate the entity is attempting to move to.
     * @param nextY The next y-coordinate the entity is attempting to move to.
     * @return Returns true if a collision is detected in the next position.
     */
    public boolean checkCollision(Entity entity, int nextX, int nextY) {
        boolean isCollisionDetected = false;
        int entityLeftCol = (nextX + entity.solidArea.x) / gp.tileSize;
        int entityRightCol = (nextX + entity.solidArea.x + entity.solidArea.width) / gp.tileSize;
        int entityTopRow = (nextY + entity.solidArea.y) / gp.tileSize;
        int entityBottomRow = (nextY + entity.solidArea.y + entity.solidArea.height) / gp.tileSize;
    
        if (isSolidTile(entityLeftCol, entityTopRow) || isSolidTile(entityRightCol, entityTopRow) ||
            isSolidTile(entityLeftCol, entityBottomRow) || isSolidTile(entityRightCol, entityBottomRow)) {
            isCollisionDetected = true;
        } else {
            isCollisionDetected = false;
        }
        return isCollisionDetected;
    }
    
    private boolean isSolidTile(int col, int row) {
        int tileNum = gp.tileM.mapTileNum[col][row];
        return gp.tileM.tile[tileNum].collision;
    }

    /**
     * Checks for a collision between a specified entity and any zombie in the provided array.
     * @param entity The entity to check for collisions with zombies.
     * @param target The array of zombies to check against the entity.
     * @return Returns the index of the zombie collided with, or 999 if no collision occurred.
     */
    public int checkEntity(Entity entity, Zombie[] target) {
      int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                //Get entiti's solid area pos
                entity.solidArea.x = entity.worldX + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
                //Get obj's solid area pos
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (entity.direction) {
                    case "up": //print for test remove later
                        entity.solidArea.y -= entity.speed;
                        break;
                    case "down":
                        entity.solidArea.y += entity.speed;
                        break;
                    case "left":
                        entity.solidArea.x -= entity.speed;
                        break;
                    case "right":
                        entity.solidArea.x += entity.speed;
                            break;
                }
                if (entity.solidArea.intersects(target[i].solidArea)) {
                    if(target[i] != entity) {
                        entity.collisionOn = true;
                        index = i;
                    }
                }
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        return index;
    }

    public boolean checkPlayer(Entity entity, int nextX, int nextY) {
        // Calculate the entity's next solid area position
        Rectangle nextSolidArea = new Rectangle(nextX + entity.solidArea.x,
                                                nextY + entity.solidArea.y,
                                                entity.solidArea.width,
                                                entity.solidArea.height);
    
        // Get the player's solid area position
        Rectangle playerSolidArea = new Rectangle(gp.player.worldX + gp.player.solidArea.x,
                                                  gp.player.worldY + gp.player.solidArea.y,
                                                  gp.player.solidArea.width,
                                                  gp.player.solidArea.height);
    
        // Check if the entity's next solid area intersects with the player's solid area
        return nextSolidArea.intersects(playerSolidArea);
    }

    /**
     * Checks for collisions between a specified zombie and other zombies in the provided array.
     * @param zombie The zombie to check for collisions with other zombies.
     * @param target The array of zombies to check against the specified zombie.
     */
    public void checkEntity(Zombie zombie, Zombie[] target) {
    //   int index = 999;

        for (int i = 0; i < target.length; i++) {
            if (target[i] != null) {
                //Get entiti's solid area pos
                zombie.solidArea.x = zombie.worldX + zombie.solidArea.x;
                zombie.solidArea.y = zombie.worldY + zombie.solidArea.y;
                //Get obj's solid area pos
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

                switch (zombie.direction) {
                    case "up": //print for test remove later
                        zombie.solidArea.y -= zombie.speed;
                        break;
                    case "down":
                        zombie.solidArea.y += zombie.speed;
                        break;
                    case "left":
                        zombie.solidArea.x -= zombie.speed;
                        break;
                    case "right":
                        zombie.solidArea.x += zombie.speed;
                            break;
                }
                if (zombie.solidArea.intersects(target[i].solidArea)) {
                    if(target[i] != zombie) {
                        zombie.collisionOn = true;
                        //index = i;
                    }
                }
                zombie.solidArea.x = zombie.solidAreaDefaultX;
                zombie.solidArea.y = zombie.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;
            }
        }
        // return index;
    }
    
}