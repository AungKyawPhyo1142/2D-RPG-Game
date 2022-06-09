package Entity;

import Object.OBJ_Key;
import Object.OBJ_Shield_Wood;
import Object.OBJ_Sword_Normal;
import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

public class Player extends Entity {

    KeyHandler keyH;
    public final int screenX;
    public final int screenY;
    public boolean attackCancled = false;

    public ArrayList<Entity> inventory = new ArrayList<>();
    public final int inventorySize = 20;

    public Player(GamePanel gp, KeyHandler keyH) {
        super(gp);
        this.keyH = keyH;
        screenX = (gp.gameWidth / 2) - (gp.tileSize / 2);
        screenY = (gp.gameHeight / 2) - (gp.tileSize / 2);

        solidArea = new Rectangle();
        solidArea.x = 8;
        solidArea.y = 16;
        solidAreaDefaultX = solidArea.x;
        solidAreaDefaultY = solidArea.y;
        solidArea.width = 32;
        solidArea.height = 32;

//        // player's atk range with sword
//        attackArea.width = 36;
//        attackArea.height = 36;
        setDefaultValues();
        getPlayerImage();
        getPlayerAttackImage();
        setItems();
    }

    public void setDefaultValues() {
        worldX = gp.tileSize * 23;
        worldY = gp.tileSize * 21;
        speed = 4;
        direction = "down"; // set the default dir to down, any dir is fine

        // player life
        level = 1;
        strength = 1; // the more str he has, he deals more dmg
        dexerity = 1; // the more dex he has, he recieves less dmg
        exp = 0;
        nextLevelExp = 5; // how much exp needs to level up
        coin = 0;
        currentWeapon = new OBJ_Sword_Normal(gp);
        currentShield = new OBJ_Shield_Wood(gp);
        attack = getAttack();
        defense = getDefense();
        maxLife = 6;
        life = maxLife;

    }

    // set the items to the inventory
    public void setItems() {

        inventory.add(currentWeapon);
        inventory.add(currentShield);
        inventory.add(new OBJ_Key(gp));
        inventory.add(new OBJ_Key(gp));

    }

    public int getAttack() {
        attackArea = currentWeapon.attackArea;
        return strength * currentWeapon.attackValue;
    }

    public int getDefense() {
        return dexerity * currentShield.defenseValue;
    }

    @Override
    public void update() {

        if (attacking == true) {
            attacking();
        } else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.enterPressed) {

            if (keyH.upPressed) {
                direction = "up";
            } else if (keyH.downPressed) {
                direction = "down";
            } else if (keyH.leftPressed) {
                direction = "left";
            } else if (keyH.rightPressed) {
                direction = "right";
            }

            collisionOn = false;
            gp.cChecker.checkTile(this);

            // check obj collision
            int objIndex = gp.cChecker.checkObject(this, true);
            pickUpObject(objIndex);

            // check npc collision
            int npcIndex = gp.cChecker.checkEntity(this, gp.npc);
            interactNPC(npcIndex);

            // check monsters collision
            int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
            contactMonster(monsterIndex);

            // check damage pit
            gp.eHandler.checkEvent();

            // if there is no collison, player can move
            if (collisionOn == false && keyH.enterPressed == false) {
                switch (direction) {
                    case "up":
                        worldY -= speed;
                        break;

                    case "down":
                        worldY += speed;
                        break;

                    case "left":
                        worldX -= speed;
                        break;

                    case "right":
                        worldX += speed;
                        break;
                }
            }

            if (keyH.enterPressed == true && attackCancled == false) {
                gp.playSE(7);
                attacking = true;
                spriteCounter = 0;
            }
            attackCancled = false;
            gp.keyH.enterPressed = false;

            gp.keyH.enterPressed = false;

            spriteCounter++;
            if (spriteCounter > 12) {
                if (spriteNum == 1) {
                    spriteNum = 2;
                } else if (spriteNum == 2) {
                    spriteNum = 1;
                }
                spriteCounter = 0;
            }

        }

        if (invincible == true) {
            invincibleCounter++;
            if (invincibleCounter >= 60) {
                invincible = false;
                invincibleCounter = 0;
            }
        }

    }

    public void interactNPC(int i) {
        if (gp.keyH.enterPressed == true) {
            if (i != 999) {
                attackCancled = true;
                // player hitting npc and also pressed ENTER
                gp.gameState = gp.dialogueState;
                gp.npc[i].speak();
            }
        }

    }

    public void contactMonster(int i) {
        if (i != 999) {
            if (invincible == false) {
                gp.playSE(6);
                int damage = gp.monsters[i].attack - defense;
                life -= damage;
                invincible = true;
            }
        }
    }

    public void attacking() {

        spriteCounter++;

        if (spriteCounter <= 5) {
            spriteNum = 1;
        }

        if (spriteCounter > 5 && spriteCounter <= 25) {
            spriteNum = 2;

            // save the current data
            int currentWorldX = worldX;
            int currentWorldY = worldY;
            int solidAreaWidth = solidArea.width;
            int solidAreaHeight = solidArea.height;

            // adjust worldX,Y for the atk Area
            switch (direction) {
                case "up":
                    worldY -= attackArea.height;
                    break;
                case "down":
                    worldY += attackArea.height;
                    break;
                case "left":
                    worldX -= attackArea.width;
                    break;
                case "right":
                    worldX += attackArea.width;
                    break;
            }

            // attack area becomes solidArea
            solidArea.width = attackArea.width;
            solidArea.height = attackArea.height;

            int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
            damageMonster(monsterIndex);

            // reset 
            worldX = currentWorldX;
            worldY = currentWorldY;
            solidArea.width = solidAreaWidth;
            solidArea.height = solidAreaHeight;

        }

        if (spriteCounter > 25) {
            spriteNum = 1;
            spriteCounter = 0;
            attacking = false;
        }

    }

    public void getPlayerImage() {

        up1 = setup("player/boy_up_1", gp.tileSize, gp.tileSize);
        up2 = setup("player/boy_up_2", gp.tileSize, gp.tileSize);

        down1 = setup("player/boy_down_1", gp.tileSize, gp.tileSize);
        down2 = setup("player/boy_down_2", gp.tileSize, gp.tileSize);

        left1 = setup("player/boy_left_1", gp.tileSize, gp.tileSize);
        left2 = setup("player/boy_left_2", gp.tileSize, gp.tileSize);

        right1 = setup("player/boy_right_1", gp.tileSize, gp.tileSize);
        right2 = setup("player/boy_right_2", gp.tileSize, gp.tileSize);

    }

    public void getPlayerAttackImage() {

        if (currentWeapon.type == type_sword) {

            attackUp1 = setup("player/boy_attack_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("player/boy_attack_up_2", gp.tileSize, gp.tileSize * 2);

            attackDown1 = setup("player/boy_attack_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("player/boy_attack_down_2", gp.tileSize, gp.tileSize * 2);

            attackLeft1 = setup("player/boy_attack_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("player/boy_attack_left_2", gp.tileSize * 2, gp.tileSize);

            attackRight1 = setup("player/boy_attack_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("player/boy_attack_right_2", gp.tileSize * 2, gp.tileSize);
        }
        
        if (currentWeapon.type == type_axe) {

            attackUp1 = setup("player/boy_axe_up_1", gp.tileSize, gp.tileSize * 2);
            attackUp2 = setup("player/boy_axe_up_2", gp.tileSize, gp.tileSize * 2);

            attackDown1 = setup("player/boy_axe_down_1", gp.tileSize, gp.tileSize * 2);
            attackDown2 = setup("player/boy_axe_down_2", gp.tileSize, gp.tileSize * 2);

            attackLeft1 = setup("player/boy_axe_left_1", gp.tileSize * 2, gp.tileSize);
            attackLeft2 = setup("player/boy_axe_left_2", gp.tileSize * 2, gp.tileSize);

            attackRight1 = setup("player/boy_axe_right_1", gp.tileSize * 2, gp.tileSize);
            attackRight2 = setup("player/boy_axe_right_2", gp.tileSize * 2, gp.tileSize);
        }

    }

    public void pickUpObject(int i) {
        if (i != 999) {
            String text;
            if (inventory.size() != inventorySize) {
                inventory.add(gp.obj[i]);
                gp.playSE(1);
                text = "Got a " + gp.obj[i].name + "!";
            } else {
                text = "You cannot carry anymore!";
            }
            gp.ui.addMessage(text);
            gp.obj[i] = null;
        }
    }

    public void damageMonster(int i) {
        if (i != 999) {
            if (gp.monsters[i].invincible == false) {
                gp.playSE(5);

                int damage = attack - gp.monsters[i].defense;

                if (damage < 0) {
                    damage = 0;
                }

                gp.monsters[i].life -= damage;
                gp.ui.addMessage(damage + " damage");
                gp.monsters[i].invincible = true;
                gp.monsters[i].damageReaction();
            }

            if (gp.monsters[i].life <= 0) {
                gp.monsters[i].dying = true;
//                gp.ui.addMessage("Killed the monster!");
                exp += gp.monsters[i].exp;
                checkLevelUp();
            }

        } else {

        }
    }

    public void checkLevelUp() {
        if (exp >= nextLevelExp) {
            level++;
            nextLevelExp = nextLevelExp * 2;
            maxLife += 2;
            strength++;
            dexerity++;
            attack = getAttack();
            defense = getDefense();
            gp.playSE(8);
            gp.gameState = gp.dialogueState;
            gp.ui.currentDialogue = "You're Level " + level + " now!\n" + "You feel stronger!";
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        switch (direction) {
            case "up":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = up1;
                    }
                    if (spriteNum == 2) {
                        image = up2;
                    }
                }
                if (attacking == true) {
                    tempScreenY = screenY - gp.tileSize;
                    if (spriteNum == 1) {
                        image = attackUp1;
                    }
                    if (spriteNum == 2) {
                        image = attackUp2;
                    }
                }
                break;

            case "down":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = down1;
                    }
                    if (spriteNum == 2) {
                        image = down2;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackDown1;
                    }
                    if (spriteNum == 2) {
                        image = attackDown2;
                    }
                }
                break;

            case "left":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = left1;
                    }
                    if (spriteNum == 2) {
                        image = left2;
                    }
                }
                if (attacking == true) {

                    tempScreenX = screenX - gp.tileSize;

                    if (spriteNum == 1) {
                        image = attackLeft1;
                    }
                    if (spriteNum == 2) {
                        image = attackLeft2;
                    }
                }
                break;

            case "right":
                if (attacking == false) {
                    if (spriteNum == 1) {
                        image = right1;
                    }
                    if (spriteNum == 2) {
                        image = right2;
                    }
                }
                if (attacking == true) {
                    if (spriteNum == 1) {
                        image = attackRight1;
                    }
                    if (spriteNum == 2) {
                        image = attackRight2;
                    }
                }
                break;

        }

        // visual for invincible
        if (gp.player.invincible == true) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        }

        g2.drawImage(image, tempScreenX, tempScreenY, null);

        // reset composite
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

    }

    public void selectItem() {
        int itemIndex = gp.ui.getItemIndexOnSlot();
        if (itemIndex < inventory.size()) {
            Entity selectedItem = inventory.get(itemIndex);
            if (selectedItem.type == type_sword || selectedItem.type == type_axe) {
                currentWeapon = selectedItem;
                attack = getAttack();
                getPlayerAttackImage();
            }
            if (selectedItem.type == type_shield) {
                currentShield = selectedItem;
                defense = getDefense();
            }
            if (selectedItem.type == type_consumable) {
                // later use
                selectedItem.use(this);
                inventory.remove(itemIndex);
            }
        }
    }

}
