package com.winfirst.entity;

import com.winfirst.graphics.Animation;
import com.winfirst.tile.Assets;
import com.winfirst.utils.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class NPC extends Creature {

    //Animations
    private Animation animDown, animLeft, animRight, animUp, animStop;
    private Player p;
    private boolean track = false;
    private float pastX;
    private float pastY;
    private final int FOLLOW_BUFFER = 50;

    public NPC(Handler handler, float x, float y, Player p) {
        super(handler, x, y, Player.DEFAULT_CREATURE_WIDTH, Player.DEFAULT_CREATURE_HEIGHT);
        this.p = p;
        this.pastX = p.getX();
        this.pastY = p.getY();

        animDown = new Animation(500, Assets.playerDown);
        animLeft = new Animation(500, Assets.playerLeft);
        animRight = new Animation(500, Assets.playerRight);
        animUp = new Animation(500, Assets.playerUp);
        animStop = new Animation(500, Assets.playerStop);
    }

    @Override
    public void tick() {
        if (track) {
            moveToPlayer();
        }
        this.move();

        //Animations
        animDown.tick();
        animLeft.tick();
        animRight.tick();
        animUp.tick();
        animStop.tick();
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(getCurrentAnimationFrame(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()), width, height, null);
        g.drawString(this.getX() + " " + this.getY(), (int) (x - handler.getGameCamera().getxOffset()), (int) (y - handler.getGameCamera().getyOffset()));
    }

//    private void moveToPlayer() {
//        float xMove = 0;
//        float yMove = 0;
//        System.out.println(this.getY() - p.getY());
//
//        if (this.getX() > p.getX()) {
//            xMove = 4 * -1;
//        } else if (this.getX() < p.getX()) {
//            xMove = 4;
//        } else {
//            xMove = 0;
//        }
//
//        if (this.getY() > p.getY()) {
//            yMove = 4 * -1;
//        } else if (this.getY() < p.getY()) {
//            yMove = 4;
//        } else {
//            yMove = 0;
//        }
//
//        this.setxMove(xMove);
//        this.setyMove(yMove);
//    }

    private void moveToPlayer() {
        if(this.getY() - p.getY() < -36 - FOLLOW_BUFFER) {
            yMove = 4;
        } else if(this.getY() - p.getY() > 102 + FOLLOW_BUFFER) {
            yMove = -4;
        } else {
            yMove = 0;
        }

        if(this.getX() - p.getX() < -30 - FOLLOW_BUFFER) {
            xMove = 4;
        } else if(this.getX() - p.getX() > 30 + FOLLOW_BUFFER) {
            xMove = -4;
        } else {
            xMove = 0;
        }

        this.setxMove(xMove);
        this.setyMove(yMove);
    }

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

    public void setTrack(boolean b) {
        track = b;
    }

}
