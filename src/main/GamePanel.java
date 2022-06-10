package main;

import Entity.Entity;
import Entity.Player;
import Tile.TileManager;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
    public EventHandler eHandler = new EventHandler(this);
    
    Thread gameThread;
    
    // KeyEvents
    public KeyHandler keyH = new KeyHandler(this);
    
    // GAME STATE
    public int gameState;
    public final int titleState=0;
    public final int playState=1;
    public final int pauseState=2;
    public final int dialogueState=3;
    public final int characterState=4;
    
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
    public Entity obj[] = new Entity[10];
    AssetSetter aSetter = new AssetSetter(this);
    
    //NPC
    public Entity npc[] = new Entity[10];
    
    // Monsters
    public Entity monsters[] = new Entity[10];
    
    ArrayList<Entity> entityList = new ArrayList<>();
    public ArrayList<Entity> projectileList = new ArrayList<>();
    
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
        aSetter.setMonsters();
        gameState = titleState;
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
                
                // update player
                player.update();   
                
                // update NPC
                for(int i=0;i<npc.length;i++){
                    if(npc[i]!=null){
                        npc[i].update();
                    }
                }
                
                // update Monsters
                for(int i=0;i<monsters.length;i++){
                    if(monsters[i]!=null){
                        if(monsters[i].alive==true && monsters[i].dying==false){
                            monsters[i].update();
                        }
                        if(monsters[i].alive==false){
                            monsters[i] = null;
                        }
                    }
                }

                // update projectile
                for(int i=0;i<projectileList.size();i++){
                    if(projectileList.get(i)!=null){
                        if(projectileList.get(i).alive==true){
                            projectileList.get(i).update();
                        }
                        if(projectileList.get(i).alive==false){
                            projectileList.remove(i);
                        }
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
        
        // TITLE
        if(gameState==titleState){
            ui.draw(g2);
        }
        
        // OTHERS
        else {
            //Tile
            tileM.draw(g2);

            // adding entities to arraylist
            // adding player
            entityList.add(player);
            
            // adding npc
            for(int i=0;i<npc.length;i++){
                if(npc[i]!=null){
                    entityList.add(npc[i]);
                }
            }
            
            // adding objects
            for(int i=0;i<obj.length;i++){
                if(obj[i]!=null){
                    entityList.add(obj[i]);
                }
            }
            
            // adding monsters
            for(int i=0;i<monsters.length;i++){
                if(monsters[i]!=null){
                    entityList.add(monsters[i]);
                }
            }

            // adding project
            for(int i=0;i<projectileList.size();i++){
                if(projectileList.get(i)!=null){
                    entityList.add(projectileList.get(i));
                }
            }
            
            // sort the entities according to worldY
            Collections.sort(entityList,new Comparator<Entity>(){
                
                @Override
                public int compare(Entity e1, Entity e2) {
                    int result = Integer.compare(e1.worldY, e2.worldY);
                    return result;
                }
                
            });
            
            // Draw all the entities from the list
            for(int i=0;i<entityList.size();i++){
                entityList.get(i).draw(g2);
            }
            
            // clear the arrayList
            entityList.clear();
            
            //UI
            ui.draw(g2);  
            
            
        }
        
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
