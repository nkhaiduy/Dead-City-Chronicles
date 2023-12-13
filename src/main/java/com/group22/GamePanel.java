package com.group22;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import com.group22.tile.TileManager;
import com.group22.objects.SuperObject;
import com.group22.entities.Player;
import com.group22.entities.Zombie;



/**
 * The GamePanel class serves as the main controller for the game. It is responsible for handling
 * the game loop, rendering graphics, and managing the game's state. This class extends JPanel and
 * implements Runnable for threading capabilities.
 */
public class GamePanel extends JPanel implements Runnable {
    // Game world and screen settings
    private final int originalTileSize = 16;
    public int scale = 3; //this is to scale 16
    public int tileSize = originalTileSize * scale;

    public final int maxScreenCol = 20; // make 20 for 16:9 ratio, 16 for 4:3
    public final int maxScreenRow = 12; //22
    public int screenWidth = tileSize * maxScreenCol; //1920
    public int screenHeight = tileSize * maxScreenRow; //1056

    //FULL SCREEN
    int screenWidth2 = screenWidth;
    int screenHeight2 = screenHeight;
    BufferedImage tempScreen;
    Graphics2D g2;
    public boolean fullScreenOn = false;

    //WORLD SETTINGS
    public final int maxWorldCol = 78; //60 original
    public final int maxWorldRow = 44; //33 original
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;

    //FPS
    int FPS = 60;
    public KeyHandler keyH = new KeyHandler(this);
    Thread gameThread;
    public CollisionChecker cChecker = new CollisionChecker(this);
    public AssetSetter aSetter = new AssetSetter(this);

    //Sound
    Sound music = new Sound();
    Sound se = new Sound();

    //Entity and Object
    public Player player = new Player(this, keyH);
    public Zombie[] zombie = new Zombie[20];
    public SuperObject[] obj = new SuperObject[20]; //how many objects we can show

    //Tile
    TileManager tileM = new TileManager(this);

    //default player's position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    //UI
    public UI ui = new UI(this);

    //Game State
    public int gameState;
    public int previousState;
    public final int titleState = 0;
    public final int playState = 1;
    public final int pauseState = 2;
    public final int settingState = 3;
    public final int gameOverState = 4;
    public final int ruleState = 5;
    public final int winState = 6;

    public EventHandler eHandler = new EventHandler(this);

    /**
     * Constructs a GamePanel instance. This setup includes initializing the game environment,
     * such as setting screen size and background color.
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        //this.setBackground(Color.black);
        this.setBackground(new java.awt.Color(71, 53, 44));
        this.setDoubleBuffered(true);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    /**
     * Initializes the game by setting up game objects, zombies, music, and the game's display settings.
     */
    public void setupGame() {
        aSetter.setObject();
        aSetter.setZombie();
        playMusic(0);

        gameState = titleState;

        tempScreen = new BufferedImage(screenWidth, screenHeight, BufferedImage.TYPE_INT_ARGB);
        g2 = (Graphics2D) tempScreen.getGraphics();

        setFullScreen();
    }

    /**
     * Resets the game to its initial state. This is typically used after a game over scenario.
     */
    public void retry() {
        player.setDefaultValues();
        player.restorePos();
        aSetter.setObject();
        aSetter.setZombie();
    }
    
    /**
     * Configures the game display for full-screen mode.
     */
    public void setFullScreen() {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        gd.setFullScreenWindow(Main.window);

        screenWidth2 = Main.window.getWidth();
        screenHeight2 = Main.window.getHeight();
    }
    
    /**
     * Starts the main game loop in a new thread.
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = (double) 1000000000 / FPS; //0.016666 s
        double nextDrawTime = System.nanoTime() + drawInterval;

        while (gameThread != null) {
            update();
            drawToTempScreen();
            drawToScreen();
            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime = remainingTime / 1000000;

                if (remainingTime < 0) {
                    remainingTime = 0;
                }

                Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;

            } catch (InterruptedException e) {
                //throw new RuntimeException(e);
                e.printStackTrace();
            }
        }
    }
    /**
     * Updates the game state, including player and zombie movements, and other in-game interactions.
     */
    public void update() {

        if (gameState == playState) {
            player.update();
            for (int i = 0; i < zombie.length; i++) {
                if (zombie[i] != null && zombie[i].direction != null) {
                    if (zombie[i].isRemoveThis()) {
                        zombie[i] = null;
                    } else {
                        zombie[i].update();
                    }
                }
            }
        }
        if (gameState == pauseState) {

        }
      
    }
    /**
     * Draws the current game state to a temporary screen buffer.
     */
    public void drawToTempScreen() {

        if (gameState == titleState) {
            tileM.draw(g2);
            ui.draw(g2);
        } else {
            tileM.draw(g2);
         
            //Object
            for (int i = 0; i < obj.length; i++) {
                if (obj[i] != null) {
                    obj[i].draw(g2, this);
                }
            }
            //Player
            player.draw(g2);
            ui.drawPlayerName();
            //Zombie1
            for (int i = 0; i < zombie.length; i++) {
                if (zombie[i] != null) {
                    zombie[i].draw(g2);
                }
            }

            //UI
            ui.draw(g2);
           
        }
    }
    
    /**
     * Draws the contents of the temporary screen buffer to the actual screen.
     */
    public void drawToScreen() {
        Graphics g = getGraphics();
        g.drawImage(tempScreen, 0, 0, screenWidth2, screenHeight2, null);
        g.dispose();
    }

    /**
     * Starts playing the background music.
     *
     * @param i The index of the music track to play.
     */
    public void playMusic(int i) {
        music.setFile(i);
        music.play();
        music.loop();
    }

    /**
     * Stops the background music.
     */
    public void stopMusic() {
        music.stop();
    }

    /**
     * Plays a sound effect once.
     *
     * @param i The index of the sound effect to play.
     */
    public void playSE(int i) {
        se.setFile(i);
        se.play();
    }
    
    /**
     * Retrieves the current player instance in the game.
     *
     * @return The current Player object.
     */
    public Player getPlayer() {
        return player;
    }
}
