package main;

import Entity.NPC_OldMan;
import Monster.MON_GreenSlime;
import Object.OBJ_Axe;
import Object.OBJ_Key;
import Object.OBJ_Potion_Red;
import Object.OBJ_Shield_Blue;

public class AssetSetter {
    
    GamePanel gp;
    
    public AssetSetter(GamePanel gp){
        this.gp = gp;
    }

    public void setObject(){
        
        int i=0;
        
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*25;
        gp.obj[i].worldY = gp.tileSize*23;
        i++;
        
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*21;
        gp.obj[i].worldY = gp.tileSize*19;
        i++;
        
        gp.obj[i] = new OBJ_Key(gp);
        gp.obj[i].worldX = gp.tileSize*26;
        gp.obj[i].worldY = gp.tileSize*21;
        i++;
        
        gp.obj[i] = new OBJ_Axe(gp);
        gp.obj[i].worldX = gp.tileSize*33;
        gp.obj[i].worldY = gp.tileSize*21;
        i++;
        
        gp.obj[i] = new OBJ_Shield_Blue(gp);
        gp.obj[i].worldX = gp.tileSize*35;
        gp.obj[i].worldY = gp.tileSize*21;
        i++;
        
        gp.obj[i] = new OBJ_Potion_Red(gp);
        gp.obj[i].worldX = gp.tileSize*22;
        gp.obj[i].worldY = gp.tileSize*27;
        i++; 
        
    }
    
    public void setNPC(){
        
        gp.npc[0] = new NPC_OldMan(gp);
        gp.npc[0].worldX = gp.tileSize*21;
        gp.npc[0].worldY = gp.tileSize*21;
      
    }
    
    public void setMonsters(){
        int i=0;
        // slime 1
        gp.monsters[i] = new MON_GreenSlime(gp);
        gp.monsters[i].worldX = gp.tileSize*23;
        gp.monsters[i].worldY = gp.tileSize*36;
        i++;
        gp.monsters[i] = new MON_GreenSlime(gp);
        gp.monsters[i].worldX = gp.tileSize*23;
        gp.monsters[i].worldY = gp.tileSize*37;
        i++;
        gp.monsters[i] = new MON_GreenSlime(gp);
        gp.monsters[i].worldX = gp.tileSize*24;
        gp.monsters[i].worldY = gp.tileSize*37;
        i++;
        gp.monsters[i] = new MON_GreenSlime(gp);
        gp.monsters[i].worldX = gp.tileSize*34;
        gp.monsters[i].worldY = gp.tileSize*42;
        i++;
        gp.monsters[i] = new MON_GreenSlime(gp);
        gp.monsters[i].worldX = gp.tileSize*38;
        gp.monsters[i].worldY = gp.tileSize*42;
        i++;
    }
    
}
