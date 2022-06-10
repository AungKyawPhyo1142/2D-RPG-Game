/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import main.GamePanel;

/**
 *
 * @author khamaye
 */
public class Projectiles extends Entity {

    Entity user;

    public Projectiles(GamePanel gp) {
        super(gp);
    }

    public void set(int worldX, int worldY, String direction, boolean alive, Entity user) {
        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.alive = alive;
        this.user = user;
        this.life = this.maxLife;
    }

    @Override
    public void update() {

    if(user==gp.player){
        int monsterIndex = gp.cChecker.checkEntity(this, gp.monsters);
        if(monsterIndex!=999){
            gp.player.damageMonster(monsterIndex,attack);
            alive=false;
        }
    }
    if(user!=gp.player){
        // later
    }

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

        life--;
        if(life<=0){
            alive = false;
        }
        
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

}
