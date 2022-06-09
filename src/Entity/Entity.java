package Entity;

import java.awt.AlphaComposite;
import java.awt.Color;
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
    public BufferedImage attackUp1,attackUp2,attackDown1,attackDown2,attackLeft1,attackLeft2,attackRight1,attackRight2;
    public String direction = "down";

    public int spriteCounter = 0;
    public int spriteNum = 1;

    public Rectangle solidArea = new Rectangle(0, 0, 48, 48); // define the default solid area for all the entities
    public int solidAreaDefaultX;
    public int solidAreaDefaultY;
    public boolean collisionOn = false;

    public Rectangle attackArea = new Rectangle(0,0,0,0);
    
    public String dialogues[] = new String[20];
    public int dialogueIndex=0;
    
    GamePanel gp;

    public int actionLockCounter=0;
    
    //CHARACTER STATUS
    public int maxLife;
    public int life;
    public boolean invincible = false;
    public int invincibleCounter = 0;
    
    public boolean alive=true;
    public boolean dying=false;
    public int dyingCounter=0;
    
    boolean hpBarOn = false;
    int hpBarCounter=0;
    
    public BufferedImage image,image2,image3;
    public String name;
    public boolean collision=false;
    public boolean attacking=false;

    // type
    public int type; // 0=player , 1=npc , 2=monster
    public final int type_player=0;
    public final int type_npc = 1;
    public final int type_monster=2;
    public final int type_sword=3;
    public final int type_axe=4;
    public final int type_shield=5;
    public final int type_consumable=6;
    
    // character attributes
    public int level;
    public int strength;
    public int dexerity;
    public int attack;
    public int defense;
    public int exp;
    public int nextLevelExp;
    public int coin;
    public Entity currentWeapon;
    public Entity currentShield;
    
    //item attri
    public int attackValue;
    public int defenseValue;
    public String description="";
    
    public Entity(GamePanel gp) {
        this.gp = gp;
    }

    public BufferedImage setup(String imgPath,int width,int height) {
        UtilityTool uTool = new UtilityTool();
        BufferedImage scaleImage = null;
        try {
            scaleImage = ImageIO.read(getClass().getResourceAsStream("../res/" + imgPath + ".png"));
            scaleImage = uTool.scaledImage(scaleImage, width, height);
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

            // health bar on monster
            double oneScale = (double) gp.tileSize/maxLife; // get the length of the 1hp
            double hpBarValue = oneScale * life;
            
            if(type==2 && hpBarOn==true){

                // outline of hp bar
                g2.setColor(new Color(35,35,35));
                g2.drawRect(screenX-1,screenY-16,gp.tileSize+2,12);
                
                // hp bar
                g2.setColor(new Color(255,0,35));
                g2.fillRect(screenX, screenY-15, (int)hpBarValue, 10);
                
                hpBarCounter++;
                if(hpBarCounter>600){
                    hpBarCounter=0;
                    hpBarOn=false;
                }
                
            }
            
            
            // visual for invincible
            if(invincible==true){
                hpBarOn=true;
                hpBarCounter=0;
                changeAlpha(g2,0.3f);
            }

            if(dying==true){
                dyingAnimation(g2);
            }
            
            g2.drawImage(image, screenX, screenY,gp.tileSize,gp.tileSize, null);

            // reset composite
            changeAlpha(g2,1f);

        }
    }
    
    // dying animation
    public void dyingAnimation(Graphics2D g2){
        
        dyingCounter++;
        
        int i=5;
        
        if(dyingCounter <= i) { changeAlpha(g2,0f); }
        if(dyingCounter > i && dyingCounter <= i*2) { changeAlpha(g2,1f); }
        if(dyingCounter > i*2 && dyingCounter <= i*3) { changeAlpha(g2,0f); }
        if(dyingCounter > i*3 && dyingCounter <= i*4) { changeAlpha(g2,1f); }
        if(dyingCounter > i*4 && dyingCounter <= i*5) { changeAlpha(g2,0f); }
        if(dyingCounter > i*5 && dyingCounter <= i*6) { changeAlpha(g2,1f); }
        if(dyingCounter > i*6 && dyingCounter <= i*7) { changeAlpha(g2,0f); }
        if(dyingCounter > i*7 && dyingCounter <= i*8) { changeAlpha(g2,1f); }
        if(dyingCounter > i*8) { 
            dying=false;
            alive=false;
        }
        
    }

    public void changeAlpha(Graphics2D g2, float alphaValue){
        
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alphaValue));
    
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
    
    // define the reaction of a monster when it get hit
    public void damageReaction(){
        
    }
    
    public void use(Entity entity){
        
    }
    
    public void update(){
        setAction();
        collisionOn=false;
        gp.cChecker.checkTile(this);
        boolean contactPlayer = gp.cChecker.checkPlayer(this);
        gp.cChecker.checkObject(this, true);
        gp.cChecker.checkEntity(this, gp.npc);
        gp.cChecker.checkEntity(this, gp.monsters);
       
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
            
            // invincible state
            if(invincible==true){
                invincibleCounter++;
                if(invincibleCounter>40){
                    invincible=false;
                    invincibleCounter=0;
                }
            }
            

        
        // if player get hits by monster
        if(this.type==type_monster && contactPlayer==true){
           if(gp.player.invincible==false){
               gp.playSE(6);
                int damage = attack - gp.player.defense;
                gp.player.life-=damage;
               gp.player.invincible=true;
           }
        }
        
    }

}
