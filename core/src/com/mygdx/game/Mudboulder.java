package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static com.mygdx.game.Playerstate.*;
import static com.mygdx.game.Playerstate.HIT_LEFT;

/**
 * Created by Ros on 06-Dec-16.
 */
public class Mudboulder extends Enemy {
    protected int HP=10;
    protected int maxHP=10;
    protected boolean dead=false;
    protected int damage;
    protected boolean flinching;
    protected int flinchtimer;
    public int direction,interval,intervalmax;

    public Rectangle bottom,breakleft,left,right,breakright,top,full;
    public Sprite sprite;
    public Texture texture;
    public Playerstate playerstate;
    public double velocityX,velocityY;

public Mudboulder (float x, float y,int i){
    playerstate=AIR;direction=0;
    velocityX=10;velocityY=-3;
    full=new Rectangle(0,0,110,109);
    bottom=new Rectangle(0,0,110,22);
    left=new Rectangle(0,8,8,101);
    right=new Rectangle(102,8,8,106);
    top=new Rectangle(8,101,94,8);
    breakleft=new Rectangle(0,0,8,8);
    breakright=new Rectangle(102,0,8,8);
    interval=intervalmax=Math.abs(i);
    texture=new Texture(Gdx.files.internal("sprite/mudboulder.png"));
    sprite=new Sprite(texture,0,0,110,109);
    setPosition(x,y);




}

    @Override
    public Playerstate hits(Rectangle r) {
        if(top.overlaps(r)){
            return HIT_CEILING;
        }
        else if(right.overlaps(r)){
            return HIT_RIGHT;}
        else if(left.overlaps(r)){
            return HIT_LEFT;}
        else if(bottom.overlaps(r)){
            playerstate=GROUND;
            return GROUND;}

        return AIR;
    }

    @Override
    public void action(Playerstate type, double x, double y) {
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

    @Override
    public void update(double delta) {
        velocityY-=30*delta;bottom.y+=velocityY;
       /* switch(direction) {
            case 0:
                if(interval>=velocityX * 2 * delta)
                {setPosition(bottom.x - (velocityX * 2 * delta), bottom.y); System.out.println(interval);}
                else {setPosition(bottom.x - interval, bottom.y);interval=intervalmax;direction=1;
                  }

                break;
            case 1:
                if(interval>=velocityX * 2 * delta)
                {setPosition(bottom.x + (velocityX * 2 * delta), bottom.y);}
                else {setPosition(bottom.x+interval, bottom.y);interval=intervalmax;direction=1;}
                System.out.println("roll right");
                break;
        }*/
        sprite.setPosition(bottom.x,bottom.y);
    }

    @Override
    public Rectangle getHitBox() {
        return full;
    }

    @Override
    public void setPosition(double x, double y) {
        full.x=(float)x;
        full.y=(float)y;

      bottom.x=(float)x;
       bottom.y=(float)y;

        left.x=(float)x;
        left.y=(float)y+8;

       right.x=(float)x+102;
        right.y=(float)y+8;

        top.x=(float)x+8;
        top.y=(float)y+101;


        sprite.setPosition((float)x,(float)y);
    }





    @Override
    public void moveLeft(float delta) {
        setPosition(bottom.x - (velocityX * 2 * delta), bottom.y);
    }

    @Override
    public void moveRight(float delta) {
        setPosition(bottom.x +(velocityX * 2 * delta), bottom.y);
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
        return GROUND;
    }
}
