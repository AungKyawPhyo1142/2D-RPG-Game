package Entity;

import java.util.Random;
import main.GamePanel;

public class NPC_OldMan extends Entity{
    
    public NPC_OldMan(GamePanel gp) {
        super(gp);
        direction = "down";
        speed=1;
        getImage();
        setDialogue();
    }
    
    public void getImage(){
        
        up1 = setup("npc/oldman_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("npc/oldman_up_2",gp.tileSize,gp.tileSize);

        down1 = setup("npc/oldman_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("npc/oldman_down_2",gp.tileSize,gp.tileSize);

        left1 = setup("npc/oldman_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("npc/oldman_left_2",gp.tileSize,gp.tileSize);

        right1 = setup("npc/oldman_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("npc/oldman_right_2",gp.tileSize,gp.tileSize);
        
    }
    
    public void setDialogue( ) {
        dialogues[0] = "Hello, lad.";
        dialogues[1] = "So, you\'ve come to this island to find\n the treasure.";
        dialogues[2] = "I remember when I was your age.\nFull of passion. But I\'m too old for that now.";
        dialogues[3] = "I hope you found the treasure.";
        dialogues[4] = "I was once a powerful wizard...\nNow I\'ve become a bit old.";
    }
    
    @Override
    public void speak( ) {
        super.speak(); // call the speak() from it's parent (ENTITY)
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

    
}
