package com.group22;


/**
 * The EventHandler class is responsible for managing events that occur within the game.
 * It uses a grid of event rectangles to determine if an event should be triggered based on the player's position.
 */
public class EventHandler {
    GamePanel gp;
    int previousEventX, previousEventY;
    boolean canTouchEvent = true;
    EventRect[][] eventRect;

    /**
     * Constructor for EventHandler which initializes a grid of event rectangles.
     * @param gp Reference to the GamePanel for access to game settings and utilities.
     */
    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];

        int col = 0;
        int row = 0;
        while(col < gp.maxWorldCol && row < gp.maxWorldRow){
            eventRect[col][row] = new EventRect();
            eventRect[col][row].x = 23;
            eventRect[col][row].y = 23;
            eventRect[col][row].width = 2;
            eventRect[col][row].height = 2;
            eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
            eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;

            col++;
            if(col == gp.maxWorldCol){
                col = 0;
                row++;
            }
        }

    }

    /**
     * Checks for any events that are occurring and performs the necessary actions if an event is triggered.
     */
    public void checkEvent(){
        //check if the player character is more than 1 tile away from the last event
        int xDistance = Math.abs(gp.player.worldX - previousEventX);
        int yDisance = Math.abs(gp.player.worldY - previousEventY);
        int distance = Math.max(xDistance, yDisance);
        if(distance > gp.tileSize){
            canTouchEvent = true;
        }

        if(canTouchEvent){
            //add stuff that hit the player here and also heal player
            //eg: if(hit(27, 16) == true) damage();
            //above are our trap objects locations
            if(hit(25, 13)) damage();
            if(hit(60, 11)) damage();
            if(hit(51, 13)) damage();
            if(hit(62, 27)) damage();
            if(hit(40, 34)) damage();
            if(hit(21, 35)) damage();
            if(hit(51, 34)) damage();
            if(hit(41, 14)) damage();
            if(hit(21, 14)) damage();
            if(hit(22, 28)) damage();
        }
    }

    /**
     * Checks if a particular grid square designated by column and row is currently being hit by the player.
     * @param col Column index for the event rectangle to check.
     * @param row Row index for the event rectangle to check.
     * @return Returns true if the player's solid area intersects the event rectangle.
     */
    public boolean hit(int col, int row){
        boolean hit = false;
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
        eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;

        if(gp.player.solidArea.intersects(eventRect[col][row])){
            hit = true;

            previousEventX = gp.player.worldX;
            previousEventY = gp.player.worldY;
        }

        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
        eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;

        return hit;
    }

    /**
     * Executes damage logic, reducing player life and showing a message.
     */
    public void damage(){
        gp.player.life -= 1;
        gp.ui.showMessage("You're getting hurt");
        gp.playSE(3);
        canTouchEvent = false;
    }

    /**
     * Executes healing logic, increasing player life and showing a message.
     */
    public void heal(){
        gp.player.life += 1;
        gp.ui.showMessage("boosted health");
        gp.playSE(1);
        canTouchEvent = false;
    }
}
