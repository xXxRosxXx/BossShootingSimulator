package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ros on 12-Dec-16.
 */
public class Turret extends Enemy {
    protected int HP=10;
    protected int maxHP=10;
    protected boolean dead=false;
    protected int damage;
    protected boolean flinching;
    protected int flinchtimer;
    public double interval,intervalmax;

public Rectangle hitbox,hitbox1,hitbox2;
    public Sprite sprite;
    public Texture texture;
    public int direction;

    public Turret (float x, float y,int side) {
        hitbox=hitbox1=new Rectangle(x,y,81,46);hitbox2=new Rectangle(x,y,46,81);
        if(side==4||side==2){hitbox=hitbox1;}
      else if(side==1||side==3){hitbox=hitbox2;}
        direction=side;
        interval=intervalmax=1.7;
        direction=side;
        texture=new Texture(Gdx.files.internal("sprite/turret.png"));
        sprite=new Sprite(texture,0,0,81,46);
        sprite.rotate(90*side-180);
        setPosition(x,y);


    }
    @Override
    public Playerstate hits(Rectangle r) {
        return null;
    }

    @Override
    public void action(Playerstate type, double x, double y) {

    }
    @Override
    public boolean shotby(BulletPrototype b) {
        /*every enemies and bosses will use damage chart so the damage part should be written in enemies
       since some enemies might immune to some weapon(s)*/
        if(b.getClass()==Bullet.class){HP-=1;}
        if(HP<=0){HP=0;return true;}
        else{return false;}
    }
    @Override
    public void update(double delta) {
interval=(interval<delta)?0:interval-delta;
        if(interval==0){interval=intervalmax;}
    }

    @Override
    public Rectangle getHitBox() {
        return hitbox;
    }

    @Override
    public void setPosition(double x, double y) {

        hitbox.x=(float)x;
        hitbox.y=(float)y;

        sprite.setPosition((float)x,(float)y);
    }

    @Override
    public void moveLeft(float delta) {

    }

    @Override
    public void moveRight(float delta) {

    }


    @Override
    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public void jump() {

    }

    @Override
    public Playerstate hitAction(Playerstate side) {
        return null;
    }
    public void delay_sec(int s)
    {
        try {Thread.sleep(1000*s);}
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
