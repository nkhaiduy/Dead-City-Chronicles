package com.group22.objects;

import com.group22.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * ObjectTrap representing a trap object to damage players
 */
public class ObjectTrap extends SuperObject{
    GamePanel gp;
    Random rand = new Random();
    /**
     * @param gp The GamePanel instance containing configuration like tile size
     *           and reference to the player for positioning the traps relative to the player's viewport.
     */
    public ObjectTrap(GamePanel gp){
        name = "Trap";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/object/trap.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/object/trap2.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/object/trap3.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }

    /**
     * Draws the trap object on the game panel. This method overrides the SuperObject's draw method
     * The specific trap image drawn is randomly selected.
     *
     * @param g2 The Graphics2D object that provides the drawing methods.
     */
    @Override
    public void draw(Graphics2D g2, GamePanel gp) {
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        if(worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            // Select a random image out of the three available
            BufferedImage trapImage = null;
            int randomNum = rand.nextInt(3); // This will be 0, 1, or 2
            if (randomNum == 0) {
                trapImage = image;
            } else if (randomNum == 1) {
                trapImage = image2;
            } else if (randomNum == 2) {
                trapImage = image3;
            }

            // Draw the selected image
            g2.drawImage(trapImage, screenX, screenY, gp.tileSize, gp.tileSize, null);
        }
    }
}
