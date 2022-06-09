package Monster;

import Entity.Entity;
import java.util.Random;
import main.GamePanel;

public class MON_GreenSlime extends Entity{
 
    GamePanel gp;
    
    public MON_GreenSlime(GamePanel gp){
        super(gp);
        this.gp = gp;
        type=type_monster;
        direction="down";
        name="Green Slime";
        speed=1;
        maxLife=6;
        attack=3;
        defense=0;
        exp=2;
        life=maxLife;
        solidArea.x = 3;
        solidArea.y = 18;
        solidArea.width=42;
        solidArea.height=30;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        getImage();
    }
    
    public void getImage(){
        
        up1 = setup("monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        up2 = setup("monster/greenslime_down_2",gp.tileSize,gp.tileSize);

        down1 = setup("monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("monster/greenslime_down_2",gp.tileSize,gp.tileSize);

        left1 = setup("monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        left2 = setup("monster/greenslime_down_2",gp.tileSize,gp.tileSize);

        right1 = setup("monster/greenslime_down_1",gp.tileSize,gp.tileSize);
        right2 = setup("monster/greenslime_down_2",gp.tileSize,gp.tileSize);
        
    }
    
    @Override
    public void setAction(){
        
        actionLockCounter++;
        
        // do all the ai movement every 2s
        if(actionLockCounter==120){
            Random random = new Random();
            int i = random.nextInt(100)+1; // pick a num from 1 to 100

            if(i<=25){ direction="up"; }
            if(i>25 && i<=50) { direction="down"; }
            if(i>50 && i<=75) { direction="left"; }
            if(i>75 && i<=100) { direction="right"; }            
    
            actionLockCounter=0;
        }

    }
    
    // when monster get hit, it moves to the opposite of the player
    @Override
    public void damageReaction( ) {
        
        actionLockCounter=0;
        direction = gp.player.direction;
        
    }
    
}
