package Object;

import Entity.Entity;
import java.io.IOException;
import javax.imageio.ImageIO;
import main.GamePanel;

public class OBJ_Boot extends Entity {

    public OBJ_Boot(GamePanel gp){
        super(gp);
        name = "Boot";
        image = setup("objects/boots");
    }

    
}
