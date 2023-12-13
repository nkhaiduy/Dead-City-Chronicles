package com.group22;

import com.group22.objects.*;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.util.Objects;

/**
 * The UI class is responsible for rendering the game's user interface, including
 * the HUD (Heads-Up Display), title screen, pause screen, settings screen, and game over screen.
 * It also manages on-screen messages and the display of the player's status such as health and collected items.
 */
public class UI {
    
    GamePanel gp;
    Graphics2D g2;
    Font maruMonica;
    // HUD elements
    BufferedImage heart_full, heart_half, heart_blank;
    BufferedImage keyImage;
    BufferedImage vImage;
    BufferedImage dImage;

    BufferedImage medImage;

    // Title screen elements
    private BufferedImage titleImage;  
    private BufferedImage startButton; 
    private BufferedImage settingButton;
    private BufferedImage ruleButton;
    private BufferedImage exitButton;
    public boolean messageOn = false;
    public String message = "";
    int messageCounter = 0;
    public boolean gameFinished = false;
    public int commandNum =0;
    int subState = 0;

    double playTime;
    DecimalFormat dFormat = new DecimalFormat("#0.00");

    /**
     * Constructor that initializes UI components and loads resources such as images and fonts.
     * @param gp The GamePanel instance this UI belongs to.
     */
    public UI(GamePanel gp) {
        this.gp = gp;

        try {
        	InputStream is = getClass().getResourceAsStream("/font/x12y16pxMaruMonica.ttf");
			maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
		} catch (FontFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			//maruMonica = new Font("Arial", Font.PLAIN, 20);
		}
        
        //new add get all the image for title page
        try {
            titleImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/title.png")));
            startButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/start.png")));
            ruleButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/rule.png")));
            settingButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/setting.png")));
            exitButton = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/object/exit.png")));
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        

        //HUD

        ObjectKey key = new ObjectKey(gp);
        keyImage = key.image;


        ObjectVaccine vaccine = new ObjectVaccine(gp);
        vImage = vaccine.image;

        ObjectMed med = new ObjectMed(gp);
        medImage = med.image;

        ObjectHeart heart = new ObjectHeart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        ObjectDoor door = new ObjectDoor(gp);
        dImage = door.image;
    }

    /**
     * Displays a message on the screen for a short duration.
     * @param text The message text to display.
     */
    public void showMessage(String text){
        message = text;
        messageOn = true;
    }

 

    /**
     * Draws various UI components depending on the current game state.
     * @param g2 The Graphics2D object used for drawing.
     */
    public void draw(Graphics2D g2) {

        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(48F));
        g2.setColor(Color.white); 

        if (gp.gameState == gp.titleState){
            drawTitleScreen();
            playTime = 0;

        }

        if(gp.gameState == gp.playState){
            playTime += (double)1/60;


            if (gameFinished == true){
           
                 gp.gameState= gp.winState;
            
            }

        else {
            drawPlayerLife();
            g2.drawString("Time: " + dFormat.format(playTime)+" s", gp.tileSize*15, 60);

            g2.setFont(maruMonica);
            g2.setFont(g2.getFont().deriveFont(48F));
            g2.setColor(Color.white); 

            g2.drawImage(keyImage, gp.tileSize/2, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasKey, 74, 60);

            g2.drawImage(vImage, gp.tileSize/2 + 150, gp.tileSize/2, gp.tileSize, gp.tileSize, null);
            g2.drawString("x " + gp.player.hasVaccine, 235, 60);            
            
            if(messageOn){

                g2.setFont(g2.getFont().deriveFont(30F));
                g2.drawString(message, gp.tileSize/2, gp.tileSize*5);

                messageCounter++;

                if(messageCounter > 120){
                    messageCounter =0;
                    messageOn = false;
                }
            }
        }

        }
        if (gp.gameState == gp.pauseState){
            drawPauseScreen();
            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize*15, 60);

            drawPlayerLife();

        }
        if (gp.gameState == gp.settingState){
            drawSettingScreen();
            g2.drawString("Time: " + dFormat.format(playTime), gp.tileSize*15, 60);

        }

        if (gp.gameState == gp.gameOverState){
            drawGameOverScreen();
            playTime =0;
        }
        if (gp.gameState == gp.ruleState) {
        	drawRulePage();
        }
        if (gp.gameState == gp.winState) {
        	drawWinScreen();
            //gp.retry(); 
            gameFinished = false;
        }
    
    }
    
    //method for scale the image
    private BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        if (targetWidth <= 0 || targetHeight <= 0) {
            throw new IllegalArgumentException("Target width and height must be > 0");
        }
        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = scaledImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose();
        return scaledImage;
    }

    public void drawWinScreen(){
         g2.setFont(g2.getFont().deriveFont(50F));
            g2.setColor(Color.white); 

            String text;
            int textLength;
            int x;
            int y; 

            text = "You won";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y= gp.screenHeight/2 - (gp.tileSize * 3);
            g2.drawString(text, x, y);

            text = "Your Time is: " + dFormat.format(playTime) + " s";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y= gp.screenHeight/2 + (gp.tileSize * 4);
            g2.drawString(text, x, y);

            g2.setFont(g2.getFont().deriveFont(100F));
            g2.setColor(Color.yellow);
            text = "Congratulations";
            textLength = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();

            x = gp.screenWidth/2 - textLength/2;
            y= gp.screenHeight/2 + (gp.tileSize * 2);
            g2.drawString(text, x, y);

    }
    
    //draw title screen
    public void drawTitleScreen() {
    	int x = gp.screenWidth/4;
    	int y = gp.tileSize/2;
    	
    	BufferedImage originalImage = titleImage;
    	BufferedImage scaledTitle = scaleImage(originalImage,480,270); //you can set the pixel size of the image
    	BufferedImage originalImage1 = startButton;
    	BufferedImage scaledStart = scaleImage(originalImage1,70,33);
    	BufferedImage originalImage2 = ruleButton;
    	BufferedImage scaledRule = scaleImage(originalImage2,70,33);
    	BufferedImage originalImage3 = settingButton;
    	BufferedImage scaledSetting = scaleImage(originalImage3,70,33);
    	BufferedImage originalImage4 = exitButton;
    	BufferedImage scaledExit = scaleImage(originalImage4,70,33);
    	// draw the title

        if (g2 != null) {

        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 50F));

        g2.drawImage(scaledTitle, x, y, null);
        //draw start button
        g2.drawImage(scaledStart, x+gp.tileSize*4, y+gp.tileSize*6, null);
        if(commandNum == 0){
            g2.drawString(">",x+gp.tileSize*3, y+320);
        }
        //draw rule button
        g2.drawImage(scaledRule, x+gp.tileSize*4, y+gp.tileSize*7, null);
        if(commandNum == 1){
            g2.drawString(">",x+gp.tileSize*3, y+370);
        }
        //draw setting button
        g2.drawImage(scaledSetting, x+gp.tileSize*4, y+gp.tileSize*8, null);
        if(commandNum == 2){
            g2.drawString(">",x+gp.tileSize*3, y+420);
        }
        //draw exit button
        g2.drawImage(scaledExit, x+gp.tileSize*4, y+gp.tileSize*9, null);
        if(commandNum == 3){
            g2.drawString(">",x+gp.tileSize*3, y+470);
        }
    }
    }

    public void drawPlayerLife(){

        int x = gp.tileSize*7;
        int y = gp.tileSize/2;
        int i = 0;

        //draw max
        while(i < gp.player.maxLife/2){
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += gp.tileSize;
        }

        //reset values
        x = gp.tileSize*7;
        y = gp.tileSize/2;
        i = 0;

        //draw current life
        while(i < gp.player.life){
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < gp.player.life){
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += gp.tileSize;
        }
    }

    public void drawPlayerName() {
        String playerName = gp.player.name;
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 20F));
        int textWidth = g2.getFontMetrics().stringWidth(playerName);
        int textHeight = g2.getFontMetrics().getAscent();
        int x = gp.player.screenX + (gp.player.solidArea.width / 2) - (textWidth / 2);
        int y = gp.player.screenY;
        g2.setColor(Color.WHITE);
        g2.drawString(playerName, x, y);
    }

    public void drawPauseScreen(){
        String text = "PAUSED";
        int x = getXforCenteredText(text);
        int y = gp.screenHeight/2;

        g2.drawString(text, x, y);
    }

    public void drawSettingScreen(){
        g2.setColor(Color.white);
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(40F));


        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        switch (subState) {
            case 0:
                setting_top(frameX, frameY);
                break;
            case 1:
                fullScreenNoti(frameX, frameY);
                break;
            case 2:
                setting_control(frameX, frameY);
                break;
            case 3:
                endGame(frameX, frameY);
                break;
        }

        gp.keyH.enterPressed = false;

    }

    public void setting_top(int frameX, int frameY){
        int textX;
        int textY;

        String text = "Settings";
        textX = getXforCenteredText(text);
        textY = frameY + gp.tileSize;
        g2.drawString(text, textX, textY);

        //Fullscreen on/off
        textX = frameX +gp.tileSize;
        textY += gp.tileSize*2;
        g2.drawString("Fullscreen", textX, textY);
        if(commandNum ==0){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed){
                gp.fullScreenOn = !gp.fullScreenOn;
                subState = 1;
            }
        }

        //Sound
        textY += gp.tileSize;
        g2.drawString("Sound", textX, textY);
        if(commandNum ==1){
            g2.drawString(">", textX -25, textY);
        }

        //Control
        textY += gp.tileSize;
        g2.drawString("Control", textX, textY);
        if(commandNum ==2){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed){
                subState = 2;
                commandNum =0;
            }
        }       

        //Quit
        textY += gp.tileSize;
        g2.drawString("Quit Game", textX, textY);
        if(commandNum ==3){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed){
                subState = 3;
                commandNum = 0;    
            }
        }

        //Resume
        textY += gp.tileSize*2;
        g2.drawString("Back", textX, textY);
        if(commandNum ==4){
            g2.drawString(">", textX -25, textY);
            if(gp.keyH.enterPressed){
                if (gp.previousState == gp.playState){
                    gp.gameState = gp.playState;
                    commandNum = 0;
                }
                else if(gp.previousState == gp.titleState){
                    gp.gameState=gp.titleState;
                    commandNum = 0;
                }
                
            }
        }

        //Fullscreen checkbox 
        textX = frameX + (int)gp.tileSize*5;
        textY = frameY + gp.tileSize*2 + 24;
        g2.setStroke(new BasicStroke(3));
        g2.drawRect(textX, textY, 24, 24);
        if(gp.fullScreenOn){
            g2.fillRect(textX, textY, 24, 24);
        }

        //Volume
        textY += gp.tileSize;
        g2.drawRect(textX, textY, 120, 24);
        int volumeWidth = 24*gp.music.volumeScale;
        g2.fillRect(textX, textY, volumeWidth, 24);
    
    }
    
    public void drawGameOverScreen(){
        g2.setColor(new Color(0,0,0,150));
        g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);

        int x;
        int y;
        String text;
        //g2.setFont(arial_40);
        g2.setFont(g2.getFont().deriveFont(Font.BOLD,110f));

        text = "Game Over";
        g2.setColor(Color.black);
        x= getXforCenteredText(text);
        y= gp.tileSize*4;
        g2.drawString(text, x, y);

        g2.setColor(Color.white);
        g2.drawString(text, x-4, y-4);

        //Retry
        g2.setFont(g2.getFont().deriveFont(50f));
        text = "Retry";
        x= getXforCenteredText(text);
        y += gp.tileSize*4;
        g2.drawString(text, x, y);
        if(commandNum ==0){
            g2.drawString(">", x -40, y);
        }

        //Back to title
        text = "Quit";
        x= getXforCenteredText(text);
        y += 60;
        g2.drawString(text, x, y);
        if(commandNum ==1){
            g2.drawString(">", x-40, y);
        }
    }

    public void fullScreenNoti(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize*3;

        String text = "The change will take \neffect after restarting \nthe game.";
        for(String line: text.split("\n")){
            g2.drawString(line, textX-35, textY);
            textY += 40;
        }

        //back
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if (commandNum ==0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed==true){
                subState =0;
            }
        }
    }

    public void setting_control(int frameX, int frameY){
        int textX;
        int textY;
        
        String text = "Control";
        textX = getXforCenteredText(text);
        textY = frameY +gp.tileSize;
        g2.drawString(text, textX, textY);

        textX = frameX + gp.tileSize;
        textY += gp.tileSize;
        g2.drawString("Move", textX, textY); textY+=gp.tileSize;
        g2.drawString("Confirm", textX, textY); textY+=gp.tileSize;
        g2.drawString("Pause", textX, textY); textY+=gp.tileSize;
        g2.drawString("Settings", textX, textY); textY+=gp.tileSize;

        textX = frameX + gp.tileSize*6;
        textY = frameY + gp.tileSize*2;
        g2.drawString("WASD", textX -80, textY); textY+=gp.tileSize;
        g2.drawString("ENTER", textX - 80, textY); textY+=gp.tileSize;
        g2.drawString("P", textX - 80, textY); textY+=gp.tileSize;
        g2.drawString("ESCAPE", textX - 80, textY); textY+=gp.tileSize;

        //Back
        textX = frameX + gp.tileSize;
        textY = frameY + gp.tileSize*9;
        g2.drawString("Back", textX, textY);
        if (commandNum ==0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState =0;
            }
        }

    }

    public void endGame(int frameX, int frameY){
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize;

        
        String text = "Quit the game and return \nto the title screen?";
        for(String line: text.split("\n")){
            g2.drawString(line, textX-35, textY);
            textY += 40;
        }

        //YES
        String text2 = "Yes";
        textX = getXforCenteredText(text2);
        textY += gp.tileSize*3;
        g2.drawString(text2, textX, textY);
        if (commandNum == 0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState =0;
                gp.gameState = gp.titleState;
                gp.retry();
                playTime = 0;
            }
        }


        //NO
        String text3 = "No";
        textX = getXforCenteredText(text3);
        textY += gp.tileSize;
        g2.drawString(text3, textX, textY);
        if (commandNum ==1){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed){
                subState =0;
                commandNum = 3;
            }
        }
    }
    
    
    public void drawRulePage() {
    	g2.setColor(Color.white);
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(40F));


        int frameX = gp.tileSize*6;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize*8;
        int frameHeight = gp.tileSize*10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // Initialize variables for text positioning
        int textX = frameX + gp.tileSize;
        int textY = frameY + gp.tileSize;
        
        
        String title = "Rules";
        textX = getXforCenteredText(title);
        textY = frameY + gp.tileSize;
        g2.drawString(title, textX, textY);
        
        g2.setFont(g2.getFont().deriveFont(25F));

        // Start listing the rules
        textY += gp.tileSize; // Adjust the Y position for the first rule

        String[] rules = {
            "Use WASD or arrow keys to move.",
            "Get Vaccine can kill a zombie.",
            "Try to collect 3 keys",
            "then escape the city",
            "After get keys go to the home",
            "the north west of the map",
            "DON'T DIE!"
            // Add as many rules as needed
        };

        // Iterate through the rules array and draw each rule
        for (String rule : rules) {
            g2.drawString(rule, frameX + gp.tileSize, textY);
            textY += gp.tileSize; // Increment Y position for each rule
        }
        g2.setFont(g2.getFont().deriveFont(40F));
        g2.drawString("Back", textX, textY);
        if (commandNum ==0){
            g2.drawString(">", textX-25, textY);
            if(gp.keyH.enterPressed==true){
                gp.gameState = gp.titleState;
            }
        }
    }


    public int getXforCenteredText(String text){
        int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.screenWidth/2 - length/2;
        return x;
    }

    /**
     * Draws a sub-window for nested menu items such as settings and rules.
     * @param x The x-coordinate of the sub-window's top left corner.
     * @param y The y-coordinate of the sub-window's top left corner.
     * @param width The width of the sub-window.
     * @param height The height of the sub-window.
     */
    public void drawSubWindow(int x, int y, int width, int height){
        Color c = new Color(71, 53, 44, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x+5, y+5, width-10, height-10, 25, 25);

    }
}
