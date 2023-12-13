package com.group22.tile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

import com.group22.GamePanel;
import com.group22.UtilityTool;

// under package Tile;

public class TileManager{

    GamePanel gp;
    public Tile[] tile;
    //need to create the map package and import data text file into pack.
    public int mapTileNum[][];

    public TileManager(GamePanel gp){

        this.gp = gp;
        tile = new Tile[200]; //create 10 kinds of tiles, such as water tile,wall tile

        mapTileNum = new int[gp.maxWorldCol][gp.maxWorldRow];


        getTileImage();
        
        loadMap("/map/world02.txt");
    }

    // grab image from the pack
    public void getTileImage(){
        setup(0, "ground1", false);
        setup(1, "wall1", true);
        setup(2, "bush", true);
        setup(3, "road_cross", false);
        setup(4, "road_horizontal", false);
        setup(5, "road_ld_corner", false);
        setup(6, "road_lu_corner", false);
        setup(7, "road_rd_corner", false);
        setup(8, "road_ru_corner", false);
        setup(9, "road_T_down", false);
        setup(10, "road_T_left", false);
        setup(11, "road_T_right", false);
        setup(12, "road_T_up", false);
        setup(13, "road_vertical", false);
        setup(14, "fence_down", true);
        setup(15, "fence_ld_corner", true);
        setup(16, "fence_left", true);
        setup(17, "fence_lu_corner", true);
        setup(18, "fence_rd_corner", true);
        setup(19, "fence_right", true);
        setup(20, "fence_ru_corner", true);
        setup(21, "fence_up", true);
        setup(22, "haystack1", true);
        setup(23, "haystack2", true);
        setup(24, "haystack3", true);
        setup(25, "haystack4", true);
        setup(26, "haystack5", true);
        setup(27, "haystack6", true);
        setup(28, "house_1", true);
        setup(29, "house_2", true);
        setup(30, "house_3", true);
        setup(31, "house_4", true);
        setup(32, "house_5", true);
        setup(33, "house_6", true);
        setup(34, "house_7", true);
        setup(35, "house_8", true);
        setup(36, "house_9", true);
        setup(37, "house_10", true);
        setup(38, "house_11", true);
        setup(39, "house_12", true);
        setup(40, "house_13", true);
        setup(41, "house_14", true);
        setup(42, "house_15", true);
        setup(43, "house_16", true);
        setup(44, "house_17", true);
        setup(45, "house_18", true);
        setup(46, "house_19", true);
        setup(47, "house_20", true);
        setup(48, "house_21", true);
        setup(49, "house_22", true);
        setup(50, "house_23", true);
        setup(51, "house_24", true);
        setup(52, "house_25", true);
        setup(53, "house_26", true);
        setup(54, "house_27", true);
        setup(55, "house_28", true);
        setup(56, "house_29", true);
        setup(57, "house2_1", true);
        setup(58, "house2_2", true);
        setup(59, "house2_3", true);
        setup(60, "house2_4", true);
        setup(61, "house2_5", true);
        setup(62, "house2_6", true);
        setup(63, "house2_7", true);
        setup(64, "house2_8", true);
        setup(65, "house2_9", true);
        setup(66, "house2_10", true);
        setup(67, "house2_11", true);
        setup(68, "house2_12", true);
        setup(69, "house2_13", true);
        setup(70, "house2_14", true);
        setup(71, "house2_15", true);
        setup(72, "house2_16", true);
        setup(73, "house2_17", true);
        setup(74, "house2_18", true);
        setup(75, "house2_19", true);
        setup(76, "house2_20", true);
        setup(77, "house2_21", true);
        setup(78, "house2_22", true);
        setup(79, "house2_23", true);
        setup(80, "house2_24", true);
        setup(81, "house2_25", true);
        setup(82, "house2_26", true);
        setup(83, "house2_27", true);
        setup(84, "house2_28", true);
        setup(85, "house2_29", true);
        setup(86, "car_1", true);
        setup(87, "car_2", true);
        setup(88, "car2_1", true);
        setup(89, "car2_2", true);
    }

    public void setup(int index, String imageName, boolean collision){
        UtilityTool uTool = new UtilityTool();

        try{
           tile[index]  = new Tile();
           if(imageName.contains("house")) {
               tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/house/" + imageName + ".png"));
           } else if (imageName.contains("car")) {
               tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/car/" + imageName + ".png"));
           } else if(imageName.contains("haystack")) {
               tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/haystack/" + imageName + ".png"));
           } else if(imageName.contains("road")) {
               tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road/" + imageName + ".png"));
           } else if(imageName.contains("fence")){
               tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/fence/" + imageName + ".png"));
           } else{
               tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + imageName + ".png"));
           }
           tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
           tile[index].collision = collision;
        }catch(IOException e){
            e.printStackTrace();
         }
    }

    //load the map from the text you created
    public void loadMap(String filePath){
        try{
            InputStream is = getClass().getResourceAsStream(filePath);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

        int col = 0;
        int row = 0;

        while(col < gp.maxWorldCol && row < gp.maxWorldRow){

            String line = br.readLine();

            while(col < gp.maxWorldCol){
                String numbers[] = line.split(" ");

                int num = Integer.parseInt(numbers[col]);

                mapTileNum[col][row] = num;
                col++;
            }

            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }
        br.close();

        }catch(Exception e){
            e.printStackTrace();
        }
    }


    
    public void draw(Graphics2D g2){
        //g2.drawImage(tile[0].image,0,0, gp.tileSize,gp.tileSze,null); // 0,0 is the coordinate, means x,y
        //g2.drawImage(tile[1].image,0,0, gp.tileSize,gp.tileSze,null);
        //g2.drawImage(tile[2].image,0,0, gp.tileSize,gp.tileSze,null);

        int worldCol = 0;
        int worldRow = 0;
       

        while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow){

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * gp.tileSize;
            int worldY = worldRow * gp.tileSize;
            int screenX = worldX - gp.player.worldX + gp.player.screenX;
            int screenY = worldY - gp.player.worldY + gp.player.screenY;

            if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX &&
                worldX - gp.tileSize < gp.player.worldX + gp.player.screenX &&
                worldY + gp.tileSize > gp.player.worldY - gp.player.screenY &&
                worldY - gp.tileSize < gp.player.worldY + gp.player.screenY){

                //g2.drawImage(tile[tileNum].image,screenX,screenY/*gp.tileSize,gp.tileSize*/,null);
                g2.drawImage(tile[tileNum].image,screenX,screenY ,null);

            }
            
            worldCol++;
           
            if(worldCol == gp.maxWorldCol){
                worldCol = 0;
                worldRow++;
                
            }
        }
            
        //saving the map data to a text file

    }

}