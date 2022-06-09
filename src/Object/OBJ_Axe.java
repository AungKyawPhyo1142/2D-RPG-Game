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
public class OBJ_Axe extends Entity{

    public OBJ_Axe(GamePanel gp){
        super(gp);
        name = "Woodcutter's Axe";
        type = type_axe;
        down1 = setup("objects/axe",gp.tileSize,gp.tileSize);
        attackValue=2;
        attackArea.width = 30;
        attackArea.height = 30;
        description = "["+name+"]\nA bit rusty but still\ncan cut some tree.";

    }
    
}


