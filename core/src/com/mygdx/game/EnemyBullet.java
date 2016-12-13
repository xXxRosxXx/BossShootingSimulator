package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ros on 12-Dec-16.
 */
public class EnemyBullet extends BulletPrototype {
    Rectangle hitbox;
    double a,time;
    int speed;
    Texture texture[];
    Sprite sprite;
    int frame;
    public EnemyBullet(float x, float y, double angle,boolean left){

        texture=new Texture[2];
        texture[0]=new Texture(Gdx.files.internal("sprite/enemyshot1.png"));
        texture[1]=new Texture(Gdx.files.internal("sprite/enemyshot2.png"));
        time=5;speed=270;
       if(frame==0) {sprite=new Sprite(texture[0],0,0,18,18);hitbox=new Rectangle(x,y,18,18);}
        else if(frame==1) {sprite=new Sprite(texture[1],0,0,16,16);hitbox=new Rectangle(x,y,16,16);}
        a=angle;setPosition(x,y);
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
        if(frame==0) {sprite=new Sprite(texture[0],0,0,18,18);hitbox=new Rectangle(hitbox.x,hitbox.y,18,18);}
        else if(frame==1) {sprite=new Sprite(texture[1],0,0,16,16);hitbox=new Rectangle(hitbox.x,hitbox.y,16,16);}
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
        batch.draw(sprite, hitbox.x, hitbox.y,26,16);
    }

    @Override
    public Playerstate hitAction(Playerstate side) {
        return null;
    }
}
