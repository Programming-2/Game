package com.winfirst.entity;

import com.winfirst.graphics.Animation;
import com.winfirst.projectile.Bullet;
import com.winfirst.projectile.Vector2D;
import com.winfirst.tile.Assets;
import com.winfirst.utils.Handler;
import com.winfirst.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Creature {

    //Animations
    private Animation animDown, animLeft, animRight, animUp, animStop;
    private final int SCALE_FACTOR = 1;

    //Need to give player an inventory
    public Player(Handler handler, float x, float y) {
        super(handler, x, y, Creature.DEFAULT_CREATURE_WIDTH, Creature.DEFAULT_CREATURE_HEIGHT);

        bounds.x = 7;
        bounds.y = 65;
        bounds.width = 18;
        bounds.height = 35;

        //Animations
        animDown = new Animation(500, Assets.playerDown);
        animLeft = new Animation(500, Assets.playerLeft);
        animRight = new Animation(500, Assets.playerRight);
        animUp = new Animation(500, Assets.playerUp);
        animStop = new Animation(500, Assets.playerStop);
    }

    @Override
    public void tick() {
        //Animations
        animDown.tick();
        animLeft.tick();
        animRight.tick();
        animUp.tick();
        animStop.tick();

        //Movement
        getInput();
        move();
        handler.getGameCamera().centerOnEntity(this);
    }

    private void getInput() {
        xMove = 0;
        yMove = 0;

        if (handler.getKeyManager().up) {
            yMove = -speed;
        }
        if (handler.getKeyManager().down) {
            yMove = +speed;
        }
        if (handler.getKeyManager().left) {
            xMove = -speed;
        }
        if (handler.getKeyManager().right) {
            xMove = +speed;
        }

        if (handler.getKeyManager().space){
            System.out.println(handler.getMouseManager().getMouseX() / SCALE_FACTOR);
            System.out.println(handler.getMouseManager().getMouseY() / SCALE_FACTOR);
            //Needs mouse math
            int mouseX = (handler.getMouseManager().getMouseX()) - (int) ((x - handler.getGameCamera().getxOffset()));
            int mouseY = (handler.getMouseManager().getMouseY()) - (int) ((y - handler.getGameCamera().getyOffset()));

            handler.getEntityManager().addEntity(new Bullet(handler, (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), Assets.bullet, new Vector2D(mouseX, mouseY, 10), 4));
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        g.drawString(this.getX() + " " + this.getY(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));

//        		g.setColor(Color.red);
//        		g.fillRect((int) (x + bounds.x - handler.getGameCamera().getxOffset()),
//        				(int) (y + bounds.y - handler.getGameCamera().getyOffset()),
//        				bounds.width, bounds.height);
    }

    //Look at error
    private BufferedImage getCurrentAnimationFrame() {
        if (xMove < 0) {
            return animLeft.getCurrentFrame();
        } else if (xMove > 0) {
            return animRight.getCurrentFrame();
        } else if (yMove < 0) {
            return animUp.getCurrentFrame();
        } else if (yMove > 0) {
            return animDown.getCurrentFrame();
        } else {
            return animStop.getCurrentFrame();
        }
    }

}
