package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed,downPressed,leftPressed,rightPressed;
    
    GamePanel gp;
    
    public KeyHandler(GamePanel gp){
        this.gp = gp;
    }
    
    @Override
    public void keyPressed(KeyEvent ke) {

        int code = ke.getKeyCode();
        
        if(code==KeyEvent.VK_W){
            upPressed=true;
        }
        if(code==KeyEvent.VK_S){
            downPressed=true;
        }
        if(code==KeyEvent.VK_A){
            leftPressed=true;
        }
        if(code==KeyEvent.VK_D){
            rightPressed=true;
        }
        if(code==KeyEvent.VK_SPACE){
            if(gp.gameState==gp.playState){
                gp.gameState = gp.pauseState;
                System.out.println(""+gp.gameState);
            }
            else if(gp.gameState==gp.pauseState){
                gp.gameState = gp.playState;
                System.out.println(""+gp.gameState);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int code = ke.getKeyCode();
        
        if(code==KeyEvent.VK_W){
            upPressed=false;
        }
        else if(code==KeyEvent.VK_S){
            downPressed=false;
        }
        else if(code==KeyEvent.VK_A){
            leftPressed=false;
        }
        else if(code==KeyEvent.VK_D){
            rightPressed=false;
        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }
    
}
