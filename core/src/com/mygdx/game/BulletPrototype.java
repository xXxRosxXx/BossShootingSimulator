package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public abstract class BulletPrototype {

        public abstract Playerstate hits(Rectangle r);
        public abstract void action(Playerstate type, double x, double y);
        public abstract void update(double delta);
        public abstract boolean isDead();
        public abstract Rectangle getHitBox();
        public abstract void setPosition(double aa, double b);
        public abstract void moveLeft(float delta);
        public abstract void moveRight(float delta);
        public abstract void draw(SpriteBatch batch);
        public abstract Playerstate hitAction(Playerstate side);


}
