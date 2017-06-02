package com.winfirst.projectile;

import com.winfirst.entity.Entity;
import com.winfirst.tile.Tile;
import com.winfirst.utils.Handler;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Bullet extends Projectile{

    private int dmg;

    public Bullet(Handler h, int startX, int startY, BufferedImage image, Vector2D vect, int dmg){
        super(h, startX, startY, image, vect, 8, 8);
        this.dmg = dmg;
    }

    @Override
    public void tick() {
        super.getVector().tick();
        super.setX(super.getVector().getX());
        super.setY(super.getVector().getY());

        collisionCheck();
    }

    //Checks for collision with a solid tile
    protected boolean collisionWithTile(int x, int y) {
        return handler.getWorld().getTile(x, y).isSolid();
    }

    public void collisionCheck() {
        if (checkEntityCollision(handler.getGameCamera().getxOffset() + 10, handler.getGameCamera().getyOffset() + 10)) {
            this.getHandler().getEntityManager().removeEntity(this);
        }
        if (getVector().getXVol() > 0) {//Moving right
            int tx = (int) (x + getVector().getXVol() + bounds.x + bounds.width + super.getHandler().getGameCamera().getxOffset()) / Tile.TILEWIDTH;

            if (collisionWithTile(tx, (int) (super.getVector().getY() + bounds.y + super.getHandler().getGameCamera().getyOffset()) / Tile.TILEHEIGHT) || collisionWithTile(tx, (int) (super.getVector().getY() + bounds.y + bounds.height + super.getHandler().getGameCamera().getyOffset()) / Tile.TILEHEIGHT)) {

            }
        } else if (getVector().getXVol() < 0) {//Moving left
            int tx = (int) (x + getVector().getXVol() + bounds.x + super.getHandler().getGameCamera().getxOffset()) / Tile.TILEWIDTH;

            if (collisionWithTile(tx, (int) (super.getVector().getY() + bounds.y + super.getHandler().getGameCamera().getyOffset()) / Tile.TILEHEIGHT) || collisionWithTile(tx, (int) (super.getVector().getY() + bounds.y + bounds.height + super.getHandler().getGameCamera().getyOffset()) / Tile.TILEHEIGHT)) {
                this.getHandler().getEntityManager().removeEntity(this);
            }
        }
        if (getVector().getYVol() < 0) {//Up
            int ty = (int) (y + getVector().getYVol() + bounds.y + super.getHandler().getGameCamera().getyOffset() + 25) / Tile.TILEHEIGHT;

            if (collisionWithTile((int) (super.getVector().getX() + bounds.x + super.getHandler().getGameCamera().getxOffset()) / Tile.TILEWIDTH, ty) || collisionWithTile((int) (super.getVector().getX() + bounds.x + bounds.width + super.getHandler().getGameCamera().getxOffset()) / Tile.TILEWIDTH, ty)) {
                this.getHandler().getEntityManager().removeEntity(this);
            }

        } else if (getVector().getYVol() > 0) {//Down
            int ty = (int) (y + getVector().getYVol() + bounds.y + bounds.height + super.getHandler().getGameCamera().getyOffset()) / Tile.TILEHEIGHT;

            if (collisionWithTile((int) (super.getVector().getX() + bounds.x + super.getHandler().getGameCamera().getxOffset()) / Tile.TILEWIDTH, ty) || collisionWithTile((int) (super.getVector().getX() + bounds.x + bounds.width + super.getHandler().getGameCamera().getxOffset()) / Tile.TILEWIDTH, ty)) {
                this.getHandler().getEntityManager().removeEntity(this);
            }
        }
    }

    public void onCollision() {

    }

    @Override
    public void render(Graphics g) {
        g.drawImage(super.getImage(), (int) super.getX(), (int) super.getY(), null);
    }
}
