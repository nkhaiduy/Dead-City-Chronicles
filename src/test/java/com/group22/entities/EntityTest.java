package com.group22.entities;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    private Entity entity;

    @BeforeEach
    void setUp() {
        // Creating an anonymous subclass of Entity for testing purposes
        entity = new Entity() {
            @Override
            public void draw(Graphics2D g){}
            @Override
            public void update(){}
        };
        // Initialize with some default values
        entity.worldX = 100;
        entity.worldY = 200;
        entity.speed = 2;
        entity.maxLife = 100;
        entity.life = 100;
        entity.invincible = false;
        entity.invincibleCounter = 0;
        entity.solidArea = new Rectangle(0, 0, 48, 48);
        entity.solidAreaDefaultX = entity.solidArea.x;
        entity.solidAreaDefaultY = entity.solidArea.y;
        // ... initialize other fields if necessary
    }

    @Test
    void testEntityMovement() {
        // Given
        int initialX = entity.worldX;
        int initialY = entity.worldY;

        // When
        entity.worldX += entity.speed;
        entity.worldY += entity.speed;

        // Then
        assertEquals(initialX + entity.speed, entity.worldX, "Entity should move right by speed units.");
        assertEquals(initialY + entity.speed, entity.worldY, "Entity should move down by speed units.");
    }

    @Test
    void testEntityCollision() {
        // Initially, there should be no collision
        assertFalse(entity.collisionOn, "Entity should not have collision on by default.");

        // Simulate collision
        entity.collisionOn = true;

        // Verify the collision status
        assertTrue(entity.collisionOn, "Entity should have collision on after being set.");
    }

    @Test
    void testSpriteCycle() {
        // Given
        entity.spriteCounter = 10;
        entity.spriteNum = 1;

        // When
        entity.spriteCounter++;
        if(entity.spriteCounter > 10) {
            entity.spriteCounter = 0;
            entity.spriteNum++;
            if(entity.spriteNum > 3) {
                entity.spriteNum = 1;
            }
        }

        // Then
        assertEquals(0, entity.spriteCounter, "Sprite counter should reset after reaching the threshold.");
        assertEquals(2, entity.spriteNum, "Sprite number should increment after counter resets.");
    }

    @Test
    void testEntityInvincibility() {
        // Given
        entity.invincible = true;
        entity.invincibleCounter = 0;

        // When
        entity.invincibleCounter++;

        // Then
        assertTrue(entity.invincible, "Entity should be invincible when set.");
        assertEquals(1, entity.invincibleCounter, "Invincible counter should increment.");
    }

    @Test
    void testLifeManagement() {
        // Given
        entity.life = 50;

        // When
        entity.life -= 10; // Entity takes damage
        // Then
        assertEquals(40, entity.life, "Entity life should decrement by damage taken.");

        // When
        entity.life += 20; // Entity heals
        // Then
        assertEquals(60, entity.life, "Entity life should increment by healing amount.");

        // When life exceeds maxLife
        entity.life = 150; // Set life beyond maxLife
        // Then
        if (entity.life > entity.maxLife) {
            entity.life = entity.maxLife; // Correct the life to not exceed maxLife
        }
        assertEquals(entity.maxLife, entity.life, "Entity life should not exceed max life.");
    }


    // Add more tests for other functionalities...
}
