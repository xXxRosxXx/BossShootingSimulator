package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static com.mygdx.game.Playerstate.BOSS;

/**
 * Created by Ros on 15-Dec-16.
 */
public class Bossgate extends GameObject {
    Rectangle hitbox;
    Sprite sprite;
    Texture texture;
    public Bossgate(int x,int y,int flip){
        texture=new Texture(Gdx.files.internal("sprite/bossgate.png"));
if(flip==0){hitbox=new Rectangle(x,y,60,128);sprite=new Sprite(texture,0,0,60,128);}
else if(flip==1){hitbox=new Rectangle(x,y,128,60);sprite=new Sprite(texture,0,0,60,128);sprite.rotate(90);}
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
    public void setPosition(double x, double y) {
        hitbox.x=(float)x;hitbox.y=(float)y;
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
        return BOSS;
    }
}
