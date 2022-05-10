package main;

import Object.OBJ_Key;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UI {
 
    GamePanel gp;

    Font maruMonica;
    
    Graphics2D g2;
    
    public boolean messageOn=false;
    public String message = "";
    
    int messageCounter=0;
    public boolean gameFinished = false;

    
    DecimalFormat dFormat = new DecimalFormat("#0.00");
    
    double playTime=0;

    // Dialogue texts
    public String currentDialogue;
    
    public UI(GamePanel gp){
            this.gp = gp;

        try {            
            InputStream is = getClass().getResourceAsStream("../res/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT,is);
        } catch (FontFormatException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void draw(Graphics2D g2){
        this.g2 = g2;
        
        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);
        
        // IN PLAY STATE
        if(gp.gameState==gp.playState){
            // do smth for the playstate
        }
        
        // IN PAUSE STATE
        if(gp.gameState==gp.pauseState){
            drawPauseScreen();
        }
        
        // IN DIALOGUE STATE
        if(gp.gameState==gp.dialogueState){
            drawDialogueScreen();
        }
        
    }
    
    public void drawDialogueScreen(){
        
        // create a window
        int x = gp.tileSize*2; // two tiles from the left
        int y = gp.tileSize/2; // half of a tile from top
        int width = gp.gameWidth - (gp.tileSize*4);
        int height = gp.tileSize*4;
        drawSubWindow(x,y,width,height);
        
        // draw texts
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,30F));
        x += gp.tileSize;
        y += gp.tileSize;
        
         // use split() to find \n and when it found it, increate y(i.e wrap the text)
        for(String lines : currentDialogue.split("\n")){
            g2.drawString(lines,x,y);         
            y+=40;
        }
        
        
    }
    
    public void drawSubWindow(int x, int y, int width, int height){

        // draw the dialog box
        Color c = new Color(0,0,0,200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);
        
        // draw the border stroke for the dialog box
        c = new Color(255,255,255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5)); // set the width of the stroke
        g2.drawRoundRect(x+5,y+5,width-10,height-10,35,35);
        
    }
    
    public void drawPauseScreen(){
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN,80F));
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
