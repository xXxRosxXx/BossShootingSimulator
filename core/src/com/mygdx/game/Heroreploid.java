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
    public Sprite sprite;

    public Texture texture;
    public Playerstate playerstate;
    public int lives,direction;
    public double velocityX,velocityY;
    boolean isDashing=false;
    public Heroreploid(){
        playerstate=AIR;direction=1;
        velocityX=100;velocityY=0;
        full=new Rectangle(0,0,47,114);
        bottom=new Rectangle(0,0,47,22);
        left=new Rectangle(0,8,8,106);
        right=new Rectangle(39,8,8,106);
        top=new Rectangle(8,106,31,8);

        texture=new Texture(Gdx.files.internal("sprite/heroreploid.png"));
        sprite=new Sprite(texture,0,0,47,114);
        this.setPosition(55,288);
    }
    public Playerstate hits(Rectangle r){
         if(bottom.overlaps(r)){
            playerstate=GROUND;
            return GROUND;}
       if(top.overlaps(r)){
            return HIT_CEILING;
        }
        if(right.overlaps(r)){
            return HIT_RIGHT;}
       if(left.overlaps(r)){
            return HIT_LEFT;}
        return AIR;
    }
    public void action(Playerstate type,double x,double y){
        if(type==GROUND){
            velocityY=0;
            setPosition(bottom.x,y);}
        if(type==HIT_CEILING){
            velocityY-=5;
            setPosition(bottom.x,y);
        }
        if(type==HIT_LEFT){
            setPosition(x+10,bottom.y);
        }
        if(type==HIT_RIGHT){
            setPosition(x-10,bottom.y);
        }


    }
    public void update(double delta){
        velocityY-=30*delta;bottom.y+=velocityY;
        sprite.setPosition(bottom.x,bottom.y);
    }


    public void setPosition(double x,double y){
        full.x=(float)x;
        full.y=(float)y;

        bottom.x=(float)x;
        bottom.y=(float)y;

        left.x=(float)x;
        left.y=(float)y+8;

        right.x=(float)x+39;
        right.y=(float)y+8;

        top.x=(float)x+8;
        top.y=(float)y+106;
        sprite.setPosition((float)x,(float)y);
    }
    public void moveLeft(float delta){
        if(!isDashing) {
            setPosition(bottom.x - (velocityX * 2 * delta), bottom.y);
            if (direction == 1) {
                direction = 0;
                sprite.flip(true, false);
            }
        }
    }
    public void moveRight(float delta){
        if(!isDashing) {
            setPosition(bottom.x + (velocityX * 2 * delta), bottom.y);
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

    public void dash(float delta){

        isDashing=true;
        if(direction==0){setPosition(bottom.x-(velocityX*4*delta),bottom.y);delay_sec(0.033);}
        else{setPosition(bottom.x+(velocityX*4*delta),bottom.y);delay_sec(0.033);}
isDashing=false;

    }
    public void draw(SpriteBatch batch){sprite.draw(batch);}
    public Rectangle getHitBox(){
        return full;
    }
    public void delay_sec(double s)
    {
        long s2=(int)(1000*s);
        try {Thread.sleep(s2);}
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}
