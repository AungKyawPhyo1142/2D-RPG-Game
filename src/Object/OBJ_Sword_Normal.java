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
public class OBJ_Sword_Normal extends Entity {

public OBJ_Sword_Normal(GamePanel gp){
    super(gp);
    name = "Normal Sword";
    type = type_sword;
    down1 = setup("/objects/sword_normal",gp.tileSize,gp.tileSize);
    attackValue=1;
    attackArea.width = 36;
    attackArea.height = 36;
    description = "["+name+"]\nAn old sword.";
}
    
}

