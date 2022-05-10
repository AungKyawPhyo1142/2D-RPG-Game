package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        int code = ke.getKeyCode();
        
        // IF THE GAME STATE IS IN PLAY-STATE
        if (gp.gameState == gp.playState) {
            
            if (code == KeyEvent.VK_W) {
                upPressed = true;
            }
            if (code == KeyEvent.VK_S) {
                downPressed = true;
            }
            if (code == KeyEvent.VK_A) {
                leftPressed = true;
            }
            if (code == KeyEvent.VK_D) {
                rightPressed = true;
            }
            if (code == KeyEvent.VK_SPACE) {
                gp.gameState = gp.pauseState;
            }
            if (code == KeyEvent.VK_ENTER) {
                enterPressed = true;
             }

        }
        
        // IF THE GAMESTATE IS IN PAUSE-STATE
        else if(gp.gameState == gp.pauseState){
            // when you hit space again, go back to playState
            if (code == KeyEvent.VK_SPACE) {
                gp.gameState = gp.playState;
            }
        }
        
        // IF THE GAMESTATE IS IN DIALOGUE-STATE
        else if(gp.gameState == gp.dialogueState){
            if(code==KeyEvent.VK_ENTER){
                gp.gameState = gp.playState;
            }
        }
        
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        int code = ke.getKeyCode();

        if (code == KeyEvent.VK_W) {
            upPressed = false;
        } else if (code == KeyEvent.VK_S) {
            downPressed = false;
        } else if (code == KeyEvent.VK_A) {
            leftPressed = false;
        } else if (code == KeyEvent.VK_D) {
            rightPressed = false;
        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

}
