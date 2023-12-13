package com.group22.objects;

import com.group22.GamePanel;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * ObjectHeart representing hearts for players' health to gather
 */
public class ObjectHeart extends SuperObject {
    GamePanel gp;
    /**
     * @param gp The GamePanel instance which provides context for the Vaccine object,
     *           including the size of tiles which is used to scale the vaccine image.
     */
    public ObjectHeart(GamePanel gp){
        name = "Heart";
        try{
            image = ImageIO.read(getClass().getResourceAsStream("/object/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/object/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/object/heart_blank.png"));
            image = uTool.scaleImage(image, gp.tileSize, gp.tileSize);
            image2 = uTool.scaleImage(image2, gp.tileSize, gp.tileSize);
            image3 = uTool.scaleImage(image3, gp.tileSize, gp.tileSize);
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
