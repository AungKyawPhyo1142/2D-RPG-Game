package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public boolean upPressed, downPressed, leftPressed, rightPressed, enterPressed, shotKeyPressed;

    GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyPressed(KeyEvent ke) {

        int code = ke.getKeyCode();

        // IF THE GAME STATE IS IN  TITLE-STATE
        if (gp.gameState == gp.titleState) {
            titleState(code);
        } // IF THE GAME STATE IS IN PLAY-STATE
        else if (gp.gameState == gp.playState) {
            playState(code);
        } // IF THE GAMESTATE IS IN PAUSE-STATE
        else if (gp.gameState == gp.pauseState) {
            pauseState(code);
        } // IF THE GAMESTATE IS IN DIALOGUE-STATE
        else if (gp.gameState == gp.dialogueState) {
            dialogueState(code);
        } // IF THE GAMESTATE IS IN CHARACTER STATE
        else if (gp.gameState == gp.characterState) {
            characterState(code);
        }

    }

    public void titleState(int code) {
        if (code == KeyEvent.VK_W) {
            gp.ui.commandNum--;
            if (gp.ui.commandNum < 0) {
                gp.ui.commandNum = 2;
            }
        }

        if (code == KeyEvent.VK_S) {
            gp.ui.commandNum++;
            if (gp.ui.commandNum > 2) {
                gp.ui.commandNum = 0;
            }
        }

        if (code == KeyEvent.VK_ENTER) {
            if (gp.ui.commandNum == 0) {
                // new game
                gp.gameState = gp.playState;
                gp.playMusic(0);
            }
            if (gp.ui.commandNum == 1) {
                // load game
            }
            if (gp.ui.commandNum == 2) {
                // quit game
                System.exit(0);
            }
        }

    }

    public void playState(int code) {
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
        if(code==KeyEvent.VK_F){
            shotKeyPressed=true;
        }

        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.characterState;
        }
    }

    public void pauseState(int code) {
// when you hit space again, go back to playState
        if (code == KeyEvent.VK_SPACE) {
            gp.gameState = gp.playState;
        }
    }

    public void dialogueState(int code) {
        if (code == KeyEvent.VK_ENTER) {
            gp.gameState = gp.playState;
        }

    }

    public void characterState(int code) {
        if (code == KeyEvent.VK_C) {
            gp.gameState = gp.playState;
        }

        if (code == KeyEvent.VK_W) {
            if (gp.ui.slotRow != 0) {
                gp.ui.slotRow--;
                gp.playSE(9);
            }
        }
        if (code == KeyEvent.VK_A) {
            if (gp.ui.slotCol != 0) {
                gp.ui.slotCol--;
                gp.playSE(9);

            }
        }
        if (code == KeyEvent.VK_S) {

            if (gp.ui.slotRow != 3) {
                gp.ui.slotRow++;
                gp.playSE(9);

            }

        }
        if (code == KeyEvent.VK_D) {
            if (gp.ui.slotCol != 4) {
                gp.ui.slotCol++;
                gp.playSE(9);

            }
        }
        if(code == KeyEvent.VK_ENTER){
            gp.player.selectItem();
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
        } else if (code == KeyEvent.VK_F){
            shotKeyPressed = false;
        }

    }

    @Override
    public void keyTyped(KeyEvent ke) {
    }

}
