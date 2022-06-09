/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import Entity.Entity;
import main.GamePanel;

/**
 *
 * @author khamaye
 */
public class OBJ_Potion_Red extends Entity{
    int value=5;
    GamePanel gp;
    public OBJ_Potion_Red(GamePanel gp){
        super(gp);
        this.gp = gp;
        name = "Red Potion";
        type = type_consumable;
        name = "Red Portion";
        down1 = setup("objects/potion_red",gp.tileSize,gp.tileSize);
        description = "["+name+"]\nHeal your life by"+value+".";
    }
    public void use(Entity entity){
        gp.gameState = gp.dialogueState;
        gp.ui.currentDialogue = "You drink the"+name+".\nYour life has been recovered by "+value+".";
        entity.life+=value;
        if(gp.player.life > gp.player.maxLife){
            gp.player.life = gp.player.maxLife;
        }
        gp.playSE(2);
    }
}
