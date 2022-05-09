package main;

import Entity.NPC_OldMan;
import Object.OBJ_Boot;
import Object.OBJ_Chest;
import Object.OBJ_Door;
import Object.OBJ_Key;

public class AssetSetter {
    
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
                
    }
    
    public void setNPC(){
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
    }
    
}
