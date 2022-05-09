package main;

import Entity.Entity;
import Entity.Player;
import Object.SuperObject;
import Tile.TileManager;
import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{

    // game Settings
    final int originalTileSize=16; // 16px x 16px
    final int scale=3;
    public final int tileSize= scale * originalTileSize;
    
    public final int maxScreenCol = 16; // 16tiles vertically
    public final int maxScreenRow = 12; // 12titles horizontally
    
    public final int gameWidth = tileSize * maxScreenCol;
    public final int gameHeight = tileSize * maxScreenRow;
    
    public Sound music = new Sound(); // background music
    public Sound se = new Sound(); // sound effects
    
    public UI ui = new UI(this);
    
    Thread gameThread;
    
    // KeyEvents
    KeyHandler keyH = new KeyHandler(this);
    
    // GAME STATE
    public int gameState;
    public final int playState=1;
    public final int pauseState=2;
    
    // FPS
    final int FPS = 60;
    
    // set default player position
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;
    
    // call the player class
    public Player player = new Player(this,keyH);
    
    // Tiles
    TileManager tileM = new TileManager(this);
    
    // WorldMap Settings
    public final int maxWorldCol=50;
    public final int maxWorldRow=50;
    public final int worldWidth=tileSize*maxWorldCol;
    public final int worldHeight=tileSize*maxWorldRow;
    
    // Collision Checker
    public CollisionChecker cChecker = new CollisionChecker(this);
    
    // Objects
    public SuperObject obj[] = new SuperObject[10];
    AssetSetter aSetter = new AssetSetter(this);
    
    //NPC
    public Entity npc[] = new Entity[10];
    
    public GamePanel( ){
        this.setPreferredSize(new Dimension(gameWidth,gameHeight));
        this.setDoubleBuffered(true);
        this.setBackground(Color.black);
        startGame();
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }
    
    public void setUpGame(){
        aSetter.setObject();
        aSetter.setNPC();
        playMusic(0);
        stopMusic();
        gameState = playState;
    }
    
    public void startGame(){
        gameThread = new Thread(this); // pass the whole gamePanel into thread
        gameThread.start();
    }
    
    @Override
    public void run() {
        
        double drawInterval = 1000000000/FPS;
        double delta=0;
        long lastTime = System.nanoTime();
        long currentTime;
        
        while(gameThread!=null){
           currentTime = System.nanoTime();
           delta += (currentTime-lastTime)/drawInterval;
           lastTime = currentTime;
           
           if(delta>=1){
               update();
               repaint();
               delta--;
           }
           
        }
    }
    
    public void update(){
        
        if(gameState==playState){
                player.update();   
                for(int i=0;i<npc.length;i++){
                    if(npc[i]!=null){
                        npc[i].update();
                    }
                }
        }
        if(gameState==pauseState){
            // do something for the pauseState
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        //Tile
        tileM.draw(g2);
        
        //Objects
        for(int i=0;i<obj.length;i++){
            if(obj[i]!=null){
                obj[i].draw(g2, this);
            }
        }
        
        // NPC
        for(int i=0;i<npc.length;i++){
            if(npc[i]!=null){
                npc[i].draw(g2);
            }
        }
        
        //Player
        player.draw(g2);
        
        //UI
        ui.draw(g2);
        
        g2.dispose();
    }
    
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    
    public void stopMusic(){
        music.stop();
    }
    
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
    
}