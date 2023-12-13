package com.group22.objects;

import com.group22.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * ObjectMed representing a med pack to heal players
 */
public class ObjectMed extends SuperObject {
    GamePanel gp;
    /**
     * @param gp The GamePanel instance which provides context for the Vaccine object,
     *           including the size of tiles which is used to scale the vaccine image.
     */
    public ObjectMed(GamePanel gp){
        name = "Med";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/object/med.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}