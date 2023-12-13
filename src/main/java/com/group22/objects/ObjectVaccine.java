package com.group22.objects;

import com.group22.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * ObjectVaccine representing a vaccine object to cure zombies and gain points
 */
public class ObjectVaccine extends SuperObject{
    GamePanel gp;
    /**
     * @param gp The GamePanel instance which provides context for the Vaccine object,
     *           including the size of tiles which is used to scale the vaccine image.
     */
    public ObjectVaccine(GamePanel gp){
        name = "Vaccine";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/object/vaccine.png")); //just a placeholder
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
