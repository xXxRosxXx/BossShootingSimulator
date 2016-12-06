package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class Bullet extends BulletPrototype {
Rectangle hitbox;
double a,time;
int speed;
Texture texture;
    Sprite sprite;
public Bullet(float x, float y, double angle){
time=6;speed=400;
hitbox=new Rectangle(x,y,26,16);
    texture=new Texture(Gdx.files.internal("sprite/bullet.png"));
    sprite=new Sprite(texture,0,0,26,16);
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
        hitbox.x+=speed*(float)Math.cos(a)*delta;
        hitbox.y+=speed*(float)Math.sin(a)*delta;
time-=delta;
    }
    public boolean isDead()
    {if(time<0){return true;}
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
        batch.draw(texture, hitbox.x, hitbox.y, 6, 4);
    }


    @Override
    public Playerstate hitAction(Playerstate side) {
        return null;
    }
}
