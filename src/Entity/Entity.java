package Entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.UtilityTool;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // define the default solid area for all the entities
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;

    public String dialogues[] = new String[20];
    public int dialogueIndex=0;
    
    GamePanel gp;

    public int actionLockCounter=0;
    
    //CHARACTER STATUS
    public int maxLife;
    public int life;
    
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imgPath) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaleImage = null;
        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream("../res/" + imgPath + ".png"));
            scaleImage = uTool.scaledImage(scaleImage, gp.tileSize, gp.tileSize);
        } catch (IOException ie) {
            System.out.println(ie);
        }
        return scaleImage;
    }

    public void draw(Graphics2D g2) {
        BufferedImage image = null;
        int screenX = worldX - gp.player.worldX + gp.player.screenX;
        int screenY = worldY - gp.player.worldY + gp.player.screenY;

        // for the rendering efficiency
        if (worldX + gp.tileSize > gp.player.worldX - gp.player.screenX
                && worldX - gp.tileSize < gp.player.worldX + gp.player.screenX
                && worldY + gp.tileSize > gp.player.worldY - gp.player.screenY
                && worldY - gp.tileSize < gp.player.worldY + gp.player.screenY) {

            switch (direction) {
                case "up":
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                    break;

                case "down":
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                    break;

                case "left":
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                    break;

                case "right":
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                    break;

            }

            g2.drawImage(image, screenX, screenY,gp.tileSize,gp.tileSize, null);

        }
    }
    
    // define the speech of a character
    public void speak(){

        // if the dialogues(String) in an array is end, which is null, go back to start
        if (dialogues[dialogueIndex] == null) {
            dialogueIndex = 0;
        }
        gp.ui.currentDialogue = dialogues[dialogueIndex];
        dialogueIndex++;

        // set the direction of the NPC to the opposite of the player
        switch (gp.player.direction) {
            case "up":
                direction = "down";
                break;
            case "down":
                direction = "up";
                break;
            case "left":
                direction = "right";
                break;
            case "right":
                direction = "left";
                break;
        }
    }
    
    // define the behaviour of a character
    public void setAction(){
        
    }
    
    public void update(){
        setAction();
        collisionOn=false;
        gp.cChecker.checkTile(this);
        gp.cChecker.checkPlayer(this);
       
        // if there is no collison, can move
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
