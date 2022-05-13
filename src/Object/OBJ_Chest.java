package Object;

import Entity.Entity;
import main.GamePanel;

public class OBJ_Chest extends Entity{

    public OBJ_Chest(GamePanel gp){
        super(gp);
        name = "Chest";
        image = setup("objects/chest");

    }
    
}
