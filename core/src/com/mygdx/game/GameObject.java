package com.mygdx.game;

/**
 * Created by Ros on 08-Nov-16.
 */
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
public abstract class GameObject {
    public abstract Playerstate hits(Rectangle r);
    public abstract void action(Playerstate type,double x,double y);
    public abstract void update(double data);
    public abstract Rectangle getHitBox();
    public abstract void setPosition(double x,double y);
    public abstract void moveLeft(float delta);
    public abstract void moveRight(float delta);
    public abstract void draw(SpriteBatch batch);
    public abstract void jump();
    public abstract Playerstate hitAction(Playerstate side);
}