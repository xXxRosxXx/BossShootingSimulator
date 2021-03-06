package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import static com.mygdx.game.Playerstate.*;

/**
 * Created by Ros on 07-Nov-16.
 */
public class Heroreploid extends GameObject {
    public Rectangle bottom,left,right,top,full;
    public Rectangle groundpointer,leftpointer,rightpointer;
    public Sprite sprite;
    ///
    WeaponManager weaponlist;
    public Texture texture;
    public Playerstate playerstate;
    public int direction,HP,maxHP;
    public double velocityX,velocityY;
    public boolean flinching;
    public double flinchtimer;
    boolean isDashing=false;
    public Heroreploid(){
        flinchtimer=0;flinching=false;
        HP=maxHP=38;
        playerstate=AIR;direction=1;
        velocityX=100;velocityY=0;
        groundpointer=leftpointer=rightpointer=null;
        full=new Rectangle(0,0,44,109);
        bottom=new Rectangle(6,0,32,8);
        left=new Rectangle(0,8,8,92);
        right=new Rectangle(36,8,8,92);
        top=new Rectangle(9,101,26,8);

        texture=new Texture(Gdx.files.internal("sprite/heroreploid.png"));
        sprite=new Sprite(texture,0,0,44,109);
        this.setPosition(55,288);
    }
    public Playerstate hits(Rectangle r){
        if(top.overlaps(r)){
            return HIT_CEILING;
        }
        if(bottom.overlaps(r)){
            playerstate=GROUND;groundpointer=r;
            return GROUND;}
        if(right.overlaps(r)){
            rightpointer=r;return HIT_RIGHT;}
        if(left.overlaps(r)){
            leftpointer=r;return HIT_LEFT;}



        return AIR;
    }
    public void action(Playerstate type,double x,double y){

        if(type==HIT_CEILING){
            velocityY=-5;
            setPosition(full.x,y);
        }
        if(type==HIT_LEFT){
        }
        if(type==GROUND){
            velocityY=0;
            setPosition(full.x,y);
        }
        if(type==HIT_RIGHT){
            setPosition(x-5,full.y);
        }


    }
    public void update(double delta){
        if(flinchtimer==0){flinching=false;}if(full.y<-555)HP=0;
        else{flinching=true;flinchtimer=(flinchtimer>=delta)?flinchtimer-delta:0;}
        velocityY-=30*delta;full.y+=velocityY;
        sprite.setPosition(full.x,full.y);if(velocityY==0){playerstate=AIR;}
        if(playerstate==AIR||velocityY<0){setPosition(sprite.getX(),sprite.getY());}
    }


    public void setPosition(double x,double y){
        full.x=(float)x;
        full.y=(float)y;

        bottom.x=(float)x+4;
        bottom.y=(float)y;

        left.x=(float)x;
        left.y=(float)y+8;

        right.x=(float)x+36;
        right.y=(float)y+8;

        top.x=(float)x+8;
        top.y=(float)y+101;
        sprite.setPosition((float)x,(float)y);
    }
    public void moveLeft(float delta){
        if(!isDashing) {
            setPosition(full.x - (velocityX * 2 * delta),full.y);
            if (direction == 1) {
                direction = 0;
                sprite.flip(true, false);
            }
        }
    }
    public void moveRight(float delta){
        if(!isDashing) {
            setPosition(full.x + (velocityX * 2 * delta),full.y);
            if (direction == 0) {
                direction = 1;
                sprite.flip(true, false);
            }
        }
    }
    public void jump(){
        velocityY=20;playerstate=AIR;

    }

    @Override
    public Playerstate hitAction(Playerstate side) {
        return GROUND;
    }
    public void draw(SpriteBatch batch){sprite.draw(batch);}
    public Rectangle getHitBox(){
        return full;
    }
    public void delay_sec(double s)
    {
        try {Thread.sleep((int)(1000*s));}
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
