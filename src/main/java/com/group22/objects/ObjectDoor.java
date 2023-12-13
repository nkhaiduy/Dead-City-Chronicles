package com.group22.objects;

import com.group22.GamePanel;

import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * ObjectDoor representing doors on the map
 */
public class ObjectDoor extends SuperObject {
    GamePanel gp;
    /**
     * @param gp The GamePanel instance which provides context for the Vaccine object,
     *           including the size of tiles which is used to scale the vaccine image.
     */
    public ObjectDoor(GamePanel gp){
        name = "Door";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/object/door.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
