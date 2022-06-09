package main;

import Entity.Entity;
import Object.OBJ_Heart;
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
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UI {

    GamePanel gp;

    Font maruMonica;

    Graphics2D g2;

    public boolean messageOn = false;

    ArrayList<String> message = new ArrayList<>();
    ArrayList<Integer> messageCounter = new ArrayList<>();

    public boolean gameFinished = false;

    // for the menu selection
    public int commandNum = 0;

    DecimalFormat dFormat = new DecimalFormat("#0.00");

    double playTime = 0;

    // Dialogue texts
    public String currentDialogue;

    // player status
    BufferedImage heart_full, heart_half, heart_blank;

    public int slotCol = 0;
    public int slotRow = 0;

    public UI(GamePanel gp) {
        this.gp = gp;

        try {
            InputStream is = getClass().getResourceAsStream("../res/font/MaruMonica.ttf");
            maruMonica = Font.createFont(Font.TRUETYPE_FONT, is);
        } catch (FontFormatException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
        }

        // create HUD Object
        Entity heart = new OBJ_Heart(gp);
        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;
    }

    public void draw(Graphics2D g2) {
        this.g2 = g2;

        g2.setFont(maruMonica);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(Color.white);

        // IN TITLE STATE
        if (gp.gameState == gp.titleState) {
            drawTitleScreen();
        }

        // IN PLAY STATE
        if (gp.gameState == gp.playState) {
            drawPlayerLife();
            drawMessage();
        }

        // IN PAUSE STATE
        if (gp.gameState == gp.pauseState) {
            drawPauseScreen();
            drawPlayerLife();
        }

        // IN DIALOGUE STATE
        if (gp.gameState == gp.dialogueState) {
            drawPlayerLife();
            drawDialogueScreen();
        }

        // IN CHARACTER STATE
        if (gp.gameState == gp.characterState) {
            drawCharacterScreen();
            drawInventory();
        }

    }

    public void drawMessage() {
        int messageX = gp.tileSize;
        int messageY = gp.tileSize * 4;
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 32F));

        for (int i = 0; i < message.size(); i++) {
            if (message.get(i) != null) {

                g2.setColor(Color.black);
                g2.drawString(message.get(i), messageX + 2, messageY + 2);

                g2.setColor(Color.white);
                g2.drawString(message.get(i), messageX, messageY);

                int counter = messageCounter.get(i) + 1; // msgCounter++
                messageCounter.set(i, counter); // set counter to the array
                messageY += 50;

                if (messageCounter.get(i) > 180) { // 3s
                    message.remove(i);
                    messageCounter.remove(i);
                }

            }
        }

    }

    public void drawPlayerLife() {
        int x = gp.tileSize / 2;
        int y = gp.tileSize / 2;
        int i = 0;

        // draw blank heart
        while (i < gp.player.maxLife / 2) {
            g2.drawImage(heart_blank, x, y, gp.tileSize, gp.tileSize, null);
            i++;
            x += gp.tileSize;
        }

        //reset
        x = gp.tileSize / 2;
        y = gp.tileSize / 2;
        i = 0;

        //draw current life
        while (i < gp.player.life) {
            g2.drawImage(heart_half, x, y, gp.tileSize, gp.tileSize, null);
            i++;
            if (i < gp.player.life) {
                g2.drawImage(heart_full, x, y, gp.tileSize, gp.tileSize, null);
            }
            i++;
            x += gp.tileSize;
        }

    }

    public void drawTitleScreen() {

        // BACKGROUND COLOR
        g2.setColor(new Color(0, 0, 0));
        g2.fillRect(0, 0, gp.gameWidth, gp.gameHeight);

        // DISPLAYING THE GAME TITLE
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 98F));
        String text = "Adventure Island";
        int x = getXCenteredText(text);
        int y = gp.tileSize * 3;

        // DRAW TEXT SHADOW
        g2.setColor(Color.GRAY);
        g2.drawString(text, x + 5, y + 5);

        // DRAW THE TEXT
        g2.setColor(Color.white);
        g2.drawString(text, x, y);

        // DRAW CHARACTER IMAGE IN CENTER
        x = gp.gameWidth / 2 - (gp.tileSize * 2) / 2;
        y += gp.tileSize * 2;
        g2.drawImage(gp.player.down1, x, y, gp.tileSize * 2, gp.tileSize * 2, null);

        // ADDING MENU ITEMS        
        // New Game
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "New Game";
        x = getXCenteredText(text);
        y += gp.tileSize * 3.5;
        g2.drawString(text, x, y);

        // draw '>' one tile away from the text
        if (commandNum == 0) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // Load Game
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "Load Game";
        x = getXCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        if (commandNum == 1) {
            g2.drawString(">", x - gp.tileSize, y);
        }

        // Quit Game
        g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
        text = "Quit";
        x = getXCenteredText(text);
        y += gp.tileSize;
        g2.drawString(text, x, y);

        if (commandNum == 2) {
            g2.drawString(">", x - gp.tileSize, y);
        }

    }

    public void drawInventory() {
        //frame
        int frameX = gp.tileSize * 9;
        int frameY = gp.tileSize;
        int frameWidth = gp.tileSize * 6;
        int frameHeight = gp.tileSize * 5;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // slot
        final int slotXStart = frameX + 20;
        final int slotYStart = frameY + 20;
        int slotX = slotXStart;
        int slotY = slotYStart;
        
        int slotSize = gp.tileSize+3;

        // draw player items
        for (int i = 0; i < gp.player.inventory.size(); i++) {
            
            if(gp.player.inventory.get(i)==gp.player.currentWeapon||
               gp.player.inventory.get(i)==gp.player.currentShield){
                g2.setColor(new Color(240,190,90));
                g2.fillRoundRect(slotX, slotY, gp.tileSize, gp.tileSize, 10, 10);
            }
            
            g2.drawImage(gp.player.inventory.get(i).down1, slotX, slotY, null);
            slotX += slotSize;
            if (i == 4 || i == 9 || i == 14) {
                slotX = slotXStart;
                slotY += slotSize;
            }
        }

        // cursor
        int cursorX = slotXStart + slotSize * slotCol;
        int cursorY = slotYStart + slotSize * slotRow;
        int cursorWidth = gp.tileSize;
        int cursorHeight = gp.tileSize;

        // draw cursor
        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(cursorX, cursorY, cursorWidth, cursorHeight, 10, 10);

        // descp

        int dFrameX = frameX;
        int dFrameY = frameY+frameHeight;
        int dFrameWidth = frameWidth;
        int dFrameHeight = gp.tileSize*3;
        
        
        int textX=dFrameX+20;
        int textY=dFrameY+gp.tileSize;
        g2.setFont(g2.getFont().deriveFont(28F));
        
        int itemIndex = getItemIndexOnSlot();
        
        if(itemIndex < gp.player.inventory.size()){
            drawSubWindow(dFrameX,dFrameY,dFrameWidth,dFrameHeight);
            for(String line:gp.player.inventory.get(itemIndex).description.split("\n")){
            g2.drawString(line,textX,textY);
            textY+=32;
            }
        }
        
    }

    public int getItemIndexOnSlot(){
        int itemIndex=slotCol+(slotRow*5);
        return itemIndex;
    }
    
    public void drawDialogueScreen() {

        // create a window
        int x = gp.tileSize * 2; // two tiles from the left
        int y = gp.tileSize / 2; // half of a tile from top
        int width = gp.gameWidth - (gp.tileSize * 4);
        int height = gp.tileSize * 4;
        drawSubWindow(x, y, width, height);

        // draw texts
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 30F));
        x += gp.tileSize;
        y += gp.tileSize;

        // use split() to find \n and when it found it, increate y(i.e wrap the text)
        for (String lines : currentDialogue.split("\n")) {
            g2.drawString(lines, x, y);
            y += 40;
        }

    }

    public void drawCharacterScreen() {

        // create frame
        final int frameX = gp.tileSize;
        final int frameY = gp.tileSize;
        final int frameWidth = gp.tileSize * 5;
        final int frameHeight = gp.tileSize * 10;
        drawSubWindow(frameX, frameY, frameWidth, frameHeight);

        // text
        g2.setColor(Color.WHITE);
        g2.setFont((g2.getFont().deriveFont(32F)));
        int textX = frameX + 20;
        int textY = frameY + gp.tileSize;
        final int lineHeight = 35;

        // names
        g2.drawString("Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Life", textX, textY);
        textY += lineHeight;
        g2.drawString("Strength", textX, textY);
        textY += lineHeight;
        g2.drawString("Dexerity", textX, textY);
        textY += lineHeight;
        g2.drawString("Attack", textX, textY);
        textY += lineHeight;
        g2.drawString("Defense", textX, textY);
        textY += lineHeight;
        g2.drawString("Exp", textX, textY);
        textY += lineHeight;
        g2.drawString("Next Level", textX, textY);
        textY += lineHeight;
        g2.drawString("Coin", textX, textY);
        textY += lineHeight + 20;
        g2.drawString("Weapon", textX, textY);
        textY += lineHeight + 15;
        g2.drawString("Shield", textX, textY);
        textY += lineHeight;

        // values
        int tailX = (frameX + frameWidth) - 30;

        // reset textY
        textY = frameY + gp.tileSize;
        String value;

        value = String.valueOf(gp.player.level);
        textX = getXAlignRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.life + "/" + gp.player.maxLife);
        textX = getXAlignRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.strength);
        textX = getXAlignRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.dexerity);
        textX = getXAlignRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.attack);
        textX = getXAlignRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.defense);
        textX = getXAlignRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.exp);
        textX = getXAlignRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.nextLevelExp);
        textX = getXAlignRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        value = String.valueOf(gp.player.coin);
        textX = getXAlignRight(value, tailX);
        g2.drawString(value, textX, textY);
        textY += lineHeight;

        // display weapon
        g2.drawImage(gp.player.currentWeapon.down1, tailX - gp.tileSize, textY - 14, null);
        textY += 48;

        g2.drawImage(gp.player.currentShield.down1, tailX - gp.tileSize, textY - 14, null);
        textY += 48;

    }

    public void drawSubWindow(int x, int y, int width, int height) {

        // draw the dialog box
        Color c = new Color(0, 0, 0, 200);
        g2.setColor(c);
        g2.fillRoundRect(x, y, width, height, 35, 35);

        // draw the border stroke for the dialog box
        c = new Color(255, 255, 255);
        g2.setColor(c);
        g2.setStroke(new BasicStroke(5)); // set the width of the stroke
        g2.drawRoundRect(x + 5, y + 5, width - 10, height - 10, 35, 35);

    }

    public void drawPauseScreen() {
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 80F));
        String text = "PAUSED";
        int x, y;
        x = getXCenteredText(text);
        y = gp.gameHeight / 2;
        g2.drawString(text, x, y);
    }

    private int getXCenteredText(String text) {
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = gp.gameWidth / 2 - textLength / 2;
        return x;
    }

    private int getXAlignRight(String text, int tailX) {
        int textLength = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        int x = tailX - textLength;
        return x;

    }

    public void addMessage(String text) {
        message.add(text);
        messageCounter.add(0);
    }

}
