package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import static com.mygdx.game.Playerstate.DOOR;

/**
 * Created by Ros on 06-Dec-16.
 */
public class Poisiongate extends GameObject{
    Rectangle hitbox;
    Sprite sprite;
    Texture texture;


    public Poisiongate(int x, int y) {
        hitbox = new Rectangle(x, y, 119, 161);
        texture = new Texture(Gdx.files.internal("sprite/poisiongate.png"));
        sprite = new Sprite(texture, 0, 0, 119, 161);
        setPosition(x, y);
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
        hitbox.x=(float)x;hitbox.y=(float)y;sprite.setPosition((float)x,(float)y);
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
        return DOOR;
    }
}
