package com.group22.objects;

import com.group22.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * ObjectKey representing a key for players to gather in order to finish the game.
 */
public class ObjectKey extends SuperObject {
    GamePanel gp;
    /**
     * @param gp The GamePanel instance which provides context for the Vaccine object,
     *           including the size of tiles which is used to scale the vaccine image.
     */
    public ObjectKey(GamePanel gp){
        name = "Key";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/object/key.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
        collision = true;
    }
}
