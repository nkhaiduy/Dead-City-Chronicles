package com.group22;

import com.group22.objects.ObjectDoor;

import com.group22.objects.ObjectKey;
import com.group22.objects.ObjectTrap;
import com.group22.objects.ObjectVaccine;
import com.group22.entities.Zombie;


/**
 * The AssetSetter class is responsible for initializing and placing game objects
 * and zombies within the game world. It sets the initial positions and properties
 * of interactable items like vaccines, traps, doors, keys, and enemy entities (zombies).
 */
public class AssetSetter {
    GamePanel gp;

    /**
     * AssetSetter is a class responsible for placing and initializing game objects and zombies within the game world.
     * It sets the initial positions and properties of interactable items and enemy entities.
     */
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }
    
    /**
     * Sets up the game objects in the game world. This includes placing vaccines, traps,
     * doors, and keys at predetermined positions in the game map.
     */
    public void setObject(){ //here we are making 2 vaccine we should chnage that later and add other objects too
        gp.obj[0] = new ObjectVaccine(gp);
        gp.obj[0].worldX = 23 * gp.tileSize; //change this to anywhere we want to put the object
        gp.obj[0].worldY = 7 * gp.tileSize; //change this as well

        gp.obj[1] = new ObjectVaccine(gp);
        gp.obj[1].worldX = 23 * gp.tileSize; //change this to anywhere we want to put the object
        gp.obj[1].worldY = 33 * gp.tileSize; //change this as well

        gp.obj[2] = new ObjectVaccine(gp);
        gp.obj[2].worldX = 12 * gp.tileSize; 
        gp.obj[2].worldY = 25 * gp.tileSize;

        gp.obj[3] = new ObjectTrap(gp);
        gp.obj[3].worldX = 13 * gp.tileSize;
        gp.obj[3].worldY = 25 * gp.tileSize;

        gp.obj[4] = new ObjectTrap(gp);
        gp.obj[4].worldX = 11 * gp.tileSize;
        gp.obj[4].worldY = 60 * gp.tileSize;

        gp.obj[5] = new ObjectTrap(gp);
        gp.obj[5].worldX = 13 * gp.tileSize;
        gp.obj[5].worldY = 51 * gp.tileSize;

        gp.obj[6] = new ObjectTrap(gp);
        gp.obj[6].worldX = 27 * gp.tileSize;
        gp.obj[6].worldY = 62 * gp.tileSize;

        gp.obj[7] = new ObjectTrap(gp);
        gp.obj[7].worldX = 34 * gp.tileSize;
        gp.obj[7].worldY = 40 * gp.tileSize;

        gp.obj[8] = new ObjectTrap(gp);
        gp.obj[8].worldX = 35 * gp.tileSize;
        gp.obj[8].worldY = 21 * gp.tileSize;

        gp.obj[9] = new ObjectTrap(gp);
        gp.obj[9].worldX = 34 * gp.tileSize;
        gp.obj[9].worldY = 51 * gp.tileSize;

        gp.obj[10] = new ObjectTrap(gp);
        gp.obj[10].worldX = 14 * gp.tileSize;
        gp.obj[10].worldY = 41 * gp.tileSize;

        gp.obj[11] = new ObjectTrap(gp);
        gp.obj[11].worldX = 14 * gp.tileSize;
        gp.obj[11].worldY = 21 * gp.tileSize;

        gp.obj[12] = new ObjectTrap(gp);
        gp.obj[12].worldX = 28 * gp.tileSize;
        gp.obj[12].worldY = 22 * gp.tileSize;

        gp.obj[13] = new ObjectDoor(gp);
        gp.obj[13].worldX = 18 * gp.tileSize;
        gp.obj[13].worldY = 11 * gp.tileSize;

        gp.obj[14] = new ObjectKey(gp);
        gp.obj[14].worldX = 60 * gp.tileSize;
        gp.obj[14].worldY = 10 * gp.tileSize;

        gp.obj[15] = new ObjectKey(gp);
        gp.obj[15].worldX = 49 * gp.tileSize;
        gp.obj[15].worldY = 30 * gp.tileSize;

        gp.obj[16] = new ObjectKey(gp);
        gp.obj[16].worldX = 10 * gp.tileSize;
        gp.obj[16].worldY = 28 * gp.tileSize;


    }
    /**
     * Initializes and places zombies in the game world. Each zombie is given a specific
     * position and speed. The method sets up various zombies with different behaviors.
     * 3 = fast
     * 2 = medium speed
     * 1 = slow
     */
    public void setZombie() {
        gp.zombie[0] = new Zombie(gp, 1);
        gp.zombie[0].worldX = gp.tileSize * 15;
        gp.zombie[0].worldY = gp.tileSize * 15;
        gp.zombie[0].speed = 1;

        gp.zombie[1] = new Zombie(gp, 1);
        gp.zombie[1].worldX = gp.tileSize * 20;
        gp.zombie[1].worldY = gp.tileSize * 19;
        gp.zombie[1].speed = 1;

        gp.zombie[2] = new Zombie(gp, 1);
        gp.zombie[2].worldX = gp.tileSize * 20;
        gp.zombie[2].worldY = gp.tileSize * 21;
        gp.zombie[2].speed = 1;

        gp.zombie[3] = new Zombie(gp, 1);
        gp.zombie[3].worldX = gp.tileSize * 10;
        gp.zombie[3].worldY = gp.tileSize * 27;
        gp.zombie[3].speed = 1;

        gp.zombie[4] = new Zombie(gp, 1);
        gp.zombie[4].worldX = gp.tileSize * 10;
        gp.zombie[4].worldY = gp.tileSize * 26;
        gp.zombie[4].speed = 1;

        gp.zombie[5] = new Zombie(gp, 1);
        gp.zombie[5].worldX = gp.tileSize * 42;
        gp.zombie[5].worldY = gp.tileSize * 16;
        gp.zombie[5].speed = 1;

        gp.zombie[6] = new Zombie(gp, 1);
        gp.zombie[6].worldX = gp.tileSize * 41;
        gp.zombie[6].worldY = gp.tileSize * 15;
        gp.zombie[6].speed = 1;

        gp.zombie[7] = new Zombie(gp, 1);
        gp.zombie[7].worldX = gp.tileSize * 39;
        gp.zombie[7].worldY = gp.tileSize * 24;
        gp.zombie[7].speed = 1;

        gp.zombie[8] = new Zombie(gp, 3);
        gp.zombie[8].worldX = gp.tileSize * 50;
        gp.zombie[8].worldY = gp.tileSize * 10;
        gp.zombie[8].speed = 4;

        gp.zombie[9] = new Zombie(gp, 3);
        gp.zombie[9].worldX = gp.tileSize * 50;
        gp.zombie[9].worldY = gp.tileSize * 12;
        gp.zombie[9].speed = 4;

        gp.zombie[10] = new Zombie(gp, 3);
        gp.zombie[10].worldX = gp.tileSize * 60;
        gp.zombie[10].worldY = gp.tileSize * 15;
        gp.zombie[10].speed = 4;

        gp.zombie[11] = new Zombie(gp, 1);
        gp.zombie[11].worldX = gp.tileSize * 12;
        gp.zombie[11].worldY = gp.tileSize * 25;
        gp.zombie[11].speed = 1;

        gp.zombie[12] = new Zombie(gp, 1);
        gp.zombie[12].worldX = gp.tileSize * 12;
        gp.zombie[12].worldY = gp.tileSize * 28;
        gp.zombie[12].speed = 1;

        gp.zombie[13] = new Zombie(gp, 1);
        gp.zombie[13].worldX = gp.tileSize * 13;
        gp.zombie[13].worldY = gp.tileSize * 28;
        gp.zombie[13].speed = 1;

        gp.zombie[14] = new Zombie(gp, 2);
        gp.zombie[14].worldX = gp.tileSize * 60;
        gp.zombie[14].worldY = gp.tileSize * 11;

        gp.zombie[15] = new Zombie(gp, 2);
        gp.zombie[15].worldX = gp.tileSize * 61;
        gp.zombie[15].worldY = gp.tileSize * 8;

        gp.zombie[16] = new Zombie(gp, 2);
        gp.zombie[16].worldX = gp.tileSize * 58;
        gp.zombie[16].worldY = gp.tileSize * 12;

        gp.zombie[17] = new Zombie(gp, 2);
        gp.zombie[17].worldX = gp.tileSize * 54;
        gp.zombie[17].worldY = gp.tileSize * 30;

        gp.zombie[18] = new Zombie(gp, 2);
        gp.zombie[18].worldX = gp.tileSize * 63;
        gp.zombie[18].worldY = gp.tileSize * 33;
    }
}
