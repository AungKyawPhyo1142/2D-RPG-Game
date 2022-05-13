/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.awt.Rectangle;

/**
 *
 * @author thawzin
 */
public class EventHandler {
    
    GamePanel gp;
    
    Rectangle eventRect;
    public int eventRectDefaultX,eventRectDefaultY;
    
    
    public EventHandler(GamePanel gp){
        this.gp = gp;
        eventRect = new Rectangle();
        eventRect.x = 23;
        eventRect.y = 23;
        eventRect.width = 2;
        eventRect.height = 2;
        eventRectDefaultX = eventRect.x;
        eventRectDefaultY = eventRect.y;
    }
    
    public void checkEvent( ) {
        
        // sample damage pit
//        if(hit(27,16,"right")==true){ damagePit(gp.dialogueState); }
        
        // teleport pit
        if(hit(27,16,"right")==true){ teleport(gp.dialogueState); }

        
        // sample healing pool
        if(hit(23,12,"up")==true) { healingPool(gp.dialogueState); }
        
    }
    
    public boolean hit(int eventCol, int eventRow, String reqDirection){
        
        boolean hit = false;
        
        // getting player's solidArea current position
        gp.player.solidArea.x = gp.player.worldX + gp.player.solidArea.x;
        gp.player.solidArea.y = gp.player.worldY + gp.player.solidArea.y;
        
        // getting eventRect solidArea position
        eventRect.x = eventCol*gp.tileSize+eventRect.x;
        eventRect.y = eventRow*gp.tileSize+eventRect.y;
        
        // checking if player's solidArea is intersecting with the eventRect's solidArea
        if(gp.player.solidArea.intersects(eventRect)){
            if(gp.player.direction.contentEquals(reqDirection) || 
               reqDirection.contentEquals("any")){
                hit=true;
            }
        }
        
        // after checking collision, reset the solidArea x and y
        gp.player.solidArea.x = gp.player.solidAreaDefaultX;
        gp.player.solidArea.y = gp.player.solidAreaDefaultY;
        eventRect.x = eventRectDefaultX;
        eventRect.y = eventRectDefaultY;
        
        
        return hit;
        
    }
    
    public void damagePit(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "You have fallen inside the pit!";
        gp.player.life -= 1;
    }
    
    public void healingPool(int gameState){
        if(gp.keyH.enterPressed==true){
            gp.gameState = gameState;
            gp.ui.currentDialogue = "You just drank water from the pool.\nYou are recovered!";
            gp.player.life = gp.player.maxLife;
        }
    }
       
    public void teleport(int gameState){
        gp.gameState = gameState;
        gp.ui.currentDialogue = "Teleport!!";
        gp.player.worldX = gp.tileSize*37;
        gp.player.worldY = gp.tileSize*10;
    }
    
}
