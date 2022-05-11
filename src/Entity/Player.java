package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;

    
    public Player(GamePanel gp,KeyHandler keyH){
        super(gp);
        this.keyH = keyH;
        screenX = (gp.gameWidth/2) - (gp.tileSize/2);
        screenY = (gp.gameHeight/2) - (gp.tileSize/2);
        
        solidArea = new Rectangle();
        solidArea.x=8;
        solidArea.y=16;
        solidAreaDefaultX=solidArea.x;
        solidAreaDefaultY=solidArea.y;
        solidArea.width=32;
        solidArea.height=32;
        
        setDefaultValues();
        getPlayerImage();
    }
    

    
    public void setDefaultValues(){
        worldX=gp.tileSize*23;
        worldY=gp.tileSize*21;
        speed=4;
        direction = "down"; // set the default dir to down, any dir is fine

        // player life
        maxLife=6;
        life=maxLife;
    }
    
    @Override
    public void update(){

        if(keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed){
        
            if(keyH.upPressed){
                direction="up";
            }
            else if(keyH.downPressed){
                direction="down";
            }
            else if(keyH.leftPressed){
                direction="left";
            }
            else if(keyH.rightPressed){
                direction="right";
            }            

            collisionOn=false;
            gp.cChecker.checkTile(this);

            // check obj collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // check npc collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);
            
            // if there is no collison, player can move
            if(collisionOn==false){
                switch(direction){
                    case "up": worldY-=speed; break;
                        
                    case "down": worldY+=speed; break;
                    
                    case "left": worldX-=speed; break;
                    
                    case "right": worldX+=speed; break;
                }
            }
            
            spriteCounter++;
            if(spriteCounter>12){
                if(spriteNum==1){ spriteNum=2; }
                else if(spriteNum==2){ spriteNum=1; }
                spriteCounter=0;
            }
            
        }
        
    }
    
    public void interactNPC(int i){
        if(i!=999){
//               gp.gameState = gp.dialogueState;
//                gp.npc[i].speak();

            if(gp.keyH.enterPressed==true){
                // player hitting npc and also pressed ENTER
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }
        gp.keyH.enterPressed=false;

    }
    
    public void getPlayerImage(){
        
        up1 = setup("player/boy_up_1");
        up2 = setup("player/boy_up_2");

        down1 = setup("player/boy_down_1");
        down2 = setup("player/boy_down_2");

        left1 = setup("player/boy_left_1");
        left2 = setup("player/boy_left_2");

        right1 = setup("player/boy_right_1");
        right2 = setup("player/boy_right_2");
        
    }
    
    public void pickUpObject(int i){
        if(i!=999){

        }
    }
    
    @Override
    public void draw(Graphics2D g2){
        BufferedImage image=null;

            switch(direction){
                case "up": 
                            if(spriteNum==1){image=up1;}
                            if(spriteNum==2){image=up2;}
                            break;

                case "down": 
                            if(spriteNum==1){image=down1;}
                            if(spriteNum==2){image=down2;}
                            break;

                case "left": 
                            if(spriteNum==1){image=left1;}
                            if(spriteNum==2){image=left2;}
                            break;

                case "right": 
                            if(spriteNum==1){image=right1;}
                            if(spriteNum==2){image=right2;}
                            break;

            }

        g2.drawImage(image,screenX,screenY,null);
        
    }
    
}
