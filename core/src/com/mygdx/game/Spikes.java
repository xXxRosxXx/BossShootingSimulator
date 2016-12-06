package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static com.mygdx.game.Playerstate.*;

/**
 * Created by Ros on 11-Nov-16.
 */
public class Spikes extends GameObject {
Rectangle hitbox;
Sprite sprite;
    Texture texture;



    public Spikes(int x,int y){

hitbox=new Rectangle(x,y,128,22);
        texture=new Texture(Gdx.files.internal("sprite/spikes.png"));
        sprite=new Sprite(texture,0,0,128,22);
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
        if(side==GROUND||side==HIT_LEFT||side==HIT_RIGHT||side==HIT_CEILING){return DIE;}
        return GROUND;
    }
}