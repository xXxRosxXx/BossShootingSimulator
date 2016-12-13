package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ros on 13-Dec-16.
 */
public class Heatvision extends BulletPrototype {
    Rectangle hitbox;
    double a,time;
    Texture texture[];
    Sprite sprite;
    int frame,speed;
    static String name="Heat Vision";

    public Heatvision(float x, float y,double angle){
        a=angle;
        texture=new Texture[3];
        texture[0]=new Texture(Gdx.files.internal("sprite/heatvision1.png"));
        texture[1]=new Texture(Gdx.files.internal("sprite/heatvision2.png"));
        texture[2]=new Texture(Gdx.files.internal("sprite/heatvision3.png"));
        time=3.8;speed=600;
        hitbox=new Rectangle(x,y,900,13);

        if(frame==0) {sprite=new Sprite(texture[0],0,0,900,13);}
        else if(frame==1) {sprite=new Sprite(texture[1],0,0,900,13);}
        else if(frame==2) {sprite=new Sprite(texture[2],0,0,900,13);} setPosition(x,y);
}

    @Override
    public Playerstate hits(Rectangle r) {
        return null;
    }

    @Override
    public void action(Playerstate type, double x, double y) {

    }

    @Override
    public void update(double delta) {
        frame=(frame<texture.length-1)?frame+1:0;
        hitbox.x+=speed*(float)Math.cos(a)*delta;
        hitbox.y+=speed*(float)Math.sin(a)*delta;
        time-=delta;
    sprite=new Sprite(texture[frame],0,0,18,18);
    }

    @Override
    public boolean isDead() {
        if(time<0){return true;}
        return false;
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox;
    }

    @Override
    public void setPosition(double aa, double b) {
        hitbox.x=(float)aa;hitbox.y=(float)b;
        sprite.setPosition((float)aa,(float)b);
    }

    @Override
    public void moveLeft(float delta) {

    }

    @Override
    public void moveRight(float delta) {

    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(sprite, hitbox.x, hitbox.y,900,13);
    }

    @Override
    public Playerstate hitAction(Playerstate side) {
        return null;
    }
}
