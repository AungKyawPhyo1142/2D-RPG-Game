/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Object;

import Entity.Projectiles;
import main.GamePanel;

/**
 *
 * @author khamaye
 */
public class OBJ_Fireball extends Projectiles {
    GamePanel gp;
    
    public OBJ_Fireball(GamePanel gp){
        super(gp);
        this.gp = gp;
        name="Fireball";
        speed=5;
        maxLife=80;
        life=maxLife;
        attack = 2;
        useCost=1;
        alive=false;
        getImage();
    }
    
    public void getImage( ){
    
        up1 = setup("Projectiles/fireball_up_1",gp.tileSize,gp.tileSize);
        up2 = setup("Projectiles/fireball_up_2",gp.tileSize,gp.tileSize);
        
        down1 = setup("Projectiles/fireball_down_1",gp.tileSize,gp.tileSize);
        down2 = setup("Projectiles/fireball_down_2",gp.tileSize,gp.tileSize);
        
        left1 = setup("Projectiles/fireball_left_1",gp.tileSize,gp.tileSize);
        left2 = setup("Projectiles/fireball_left_2",gp.tileSize,gp.tileSize);

        right1 = setup("Projectiles/fireball_right_1",gp.tileSize,gp.tileSize);
        right2 = setup("Projectiles/fireball_right_2",gp.tileSize,gp.tileSize);
    
    }
}
