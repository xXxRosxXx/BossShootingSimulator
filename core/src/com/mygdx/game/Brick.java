package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static com.mygdx.game.Playerstate.GROUND;

/**
 * Created by Ros on 08-Nov-16.
 */
public class Brick extends GameObject {
    Rectangle hitbox;
    Sprite sprite;
    Texture texture;
static String level="testlevel";

    public Brick(int x,int y){

        if(level.equals("testlevel")||level.equals("fire")){
            hitbox=new Rectangle(x,y,128,128);
        texture=new Texture(Gdx.files.internal("sprite/brick_volcano.png"));
        sprite=new Sprite(texture,0,0,128,128);
        }
       else if(level.equals("wind")){
            hitbox=new Rectangle(x+31,y+30,128,128);
            texture=new Texture(Gdx.files.internal("sprite/brick_cloud.png"));
            sprite=new Sprite(texture,0,0,186,179);
        }
        if(level.equals("leaf")){
            hitbox=new Rectangle(x,y,128,128);
            texture=new Texture(Gdx.files.internal("sprite/brick_wood.png"));
            sprite=new Sprite(texture,0,0,128,128);
        }
        if(level.equals("poision")){
            hitbox=new Rectangle(x,y,128,128);
            texture=new Texture(Gdx.files.internal("sprite/brick_poision.png"));
            sprite=new Sprite(texture,0,0,128,136);
        }
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
    public void update(double data) {

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