package main;

import Object.OBJ_Key;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

public class UI {
 
    GamePanel gp;
    Font arial40;
    Font arial80B;
    
    Graphics2D g2;
    
    public boolean messageOn=false;
    public String message = "";
    
    int messageCounter=0;
    public boolean gameFinished = false;

    
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    
    double playTime=0;
    
    public UI(GamePanel gp){
        this.gp = gp;
        arial40 = new Font("Arial",Font.PLAIN,40);
        arial80B = new Font("Arial",Font.BOLD,80);
    }
    
    public void draw(Graphics2D g2){
        this.g2 = g2;
        
        g2.setFont(arial40);
        g2.setColor(Color.white);
        
        if(gp.gameState==gp.playState){
            // do smth for the playstate
        }
        if(gp.gameState==gp.pauseState){
            drawPauseScreen();
        }
        
    }
    
    public void drawPauseScreen(){
        g2.setFont(arial80B);
        String text = "PAUSED";
        int x,y;
        x = getXCenteredText(text);
        y = gp.gameHeight/2;
        g2.drawString(text, x, y);
    }
    
    private int getXCenteredText(String text){
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();            
        int x = gp.gameWidth/2 - textLength/2;
        return x;
    }
    
    public void showMessage(String text){
        message = text;
        messageOn=true;
    }
    
}
