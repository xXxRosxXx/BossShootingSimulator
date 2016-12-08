package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ros on 06-Dec-16.
 */
public abstract class Enemy{
    protected int HP;
    protected int maxHP;
    protected boolean dead;
    protected int damage;
protected boolean flinching;
protected int flinchtimer;
    public int direction,interval,intervalmax;

    public abstract Playerstate hits(Rectangle r);
    public abstract void action(Playerstate type,double x,double y);
    public abstract void update(double data);
    public abstract Rectangle getHitBox();
    public abstract void setPosition(double x,double y);
    public abstract void moveLeft(float delta);
    public abstract void moveRight(float delta);
    public abstract boolean shotby(BulletPrototype b);
    public abstract void draw(SpriteBatch batch);
    public abstract void jump();
    public abstract Playerstate hitAction(Playerstate side);




public boolean isDead(){return dead;}
public int getDamage(){return damage;}
public void hit (int damage){
if(!dead&&!flinching) {
HP=(damage>HP)?0:HP-damage;
if(HP==0){dead=true;}
flinching=true;

}
}



}
