package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.mygdx.game.GameObject;
import com.mygdx.game.Heroreploid;
import com.mygdx.game.Brick;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;

import static com.mygdx.game.Playerstate.*;

public class ShootingAdventure extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    private OrthographicCamera camera;
    private Heroreploid player1;
    private int gamestate=2;
    public String level="testlevel";
    public ArrayList<GameObject> list = new ArrayList<GameObject>();
    public ArrayList<BulletPrototype> bulletlist = new ArrayList<BulletPrototype>();
    public ArrayList<BulletPrototype> deadbulletlist = new ArrayList<BulletPrototype>();

    @Override
    public void create() {

        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1260, 700);
        batch = new SpriteBatch();
        texture = new Texture(Gdx.files.internal("sprite/heroreploid.png"));
        sprite = new Sprite(texture, 0, 0, 128, 128);
        sprite.setPosition(70, 70);
        player1 = new Heroreploid();
        player1.setPosition(300, 300);


    }
    @Override
    public void render() {
switch(this.gamestate) {
    case 1:
        this.mainmenu();break;
    case 2:
        this.maingame();break;
    case 3:
        this.nextlevel();break;
    case 4:
        this.gameover();
}
    }

    public void updateCamera() {
camera.position.x=player1.getHitBox().x;
        camera.position.y=player1.getHitBox().y+64;
camera.update();



        loadlevel(level);
     }
public void mainmenu(){}
public void maingame(){  Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.setProjectionMatrix(camera.combined);
    batch.begin();

    player1.draw(batch);
    for (GameObject t : list) {
        t.draw(batch);
    } for (BulletPrototype bill : bulletlist) {
        bill.draw(batch);
    }




    batch.end();
    //updates
    player1.update(Gdx.graphics.getDeltaTime());
    for (BulletPrototype bill : bulletlist) {
        bill.update(Gdx.graphics.getDeltaTime());
        if(bill.isDead()){this.deadbulletlist.add(bill);}
        for(GameObject t : list){
            if(t.getHitBox().overlaps(bill.getHitBox())) {
                deadbulletlist.add(bill);
            }}
    }
    while(deadbulletlist.size()!=0) {
        bulletlist.remove(deadbulletlist.get(0));
        deadbulletlist.remove(0);
    }
boolean changelevel=false;
    for (int i = 0; i < list.size(); i++) {
        switch (player1.hits(list.get(i).getHitBox())) {
            case GROUND:
                switch (list.get(i).hitAction(GROUND)) {
                    case GROUND:
                        player1.action(GROUND, 0, list.get(i).getHitBox().y + list.get(i).getHitBox().height);
                        break;
                    case DIE:
                        if (list.get(i).getClass() == Spikes.class) {
                            player1.setPosition(100, 466);
                        }
                        break;
                    case DOOR:
                        if(list.get(i).getClass()==Firegate.class) {level="fire";}
                        else if(list.get(i).getClass()==Windgate.class){level="wind";}
                        changelevel=true;
                        break;
                }
                break;
            case HIT_LEFT:
                switch (list.get(i).hitAction(HIT_LEFT)) {
                    case HIT_LEFT:
                        player1.action(HIT_LEFT, list.get(i).getHitBox().x + list.get(i).getHitBox().width + 1, 0);
                        break;
                    case DIE:
                        if (list.get(i).getClass() == Spikes.class) {
                            player1.setPosition(100, 466);
                        }break;
                    case DOOR:
                        if(list.get(i).getClass()==Firegate.class) {level="fire";}
                      else if(list.get(i).getClass()==Windgate.class){level="wind";}
                        changelevel=true;
                        break;
                }
                break;
            case HIT_RIGHT:
                switch (list.get(i).hitAction(HIT_RIGHT)) {
                    case HIT_RIGHT:
                        player1.action(HIT_RIGHT, list.get(i).getHitBox().x - player1.getHitBox().width - 1, 0);
                        break;
                    case DIE:
                        if (list.get(i).getClass() == Spikes.class) {
                            player1.setPosition(100, 466);
                        }
                        break;
                    case DOOR:
                        if(list.get(i).getClass()==Firegate.class) {level="fire";}
                      else if(list.get(i).getClass()==Windgate.class){level="wind";}
                        changelevel=true;
                        break;
                }
break;
            case HIT_CEILING:

                switch (list.get(i).hitAction(HIT_CEILING)) {
                    case HIT_CEILING:
                        player1.action(HIT_CEILING, 0, list.get(i).getHitBox().y - player1.getHitBox().height);
                        break;
                    case DIE:
                        if (list.get(i).getClass() == Spikes.class) {player1.setPosition(100, 466);}
                        break;
                    case DOOR:
                        if(list.get(i).getClass()==Firegate.class) {level="fire";}
                    else if(list.get(i).getClass()==Windgate.class){level="wind";}
                        changelevel=true;
                        break;
                }
                break;
        }
    }




    if(changelevel){

        player1.setPosition(150, 300);
loadlevel(level);
  Brick.level=level;
    }

    updateCamera();
    //controls
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        player1.moveLeft(Gdx.graphics.getDeltaTime());
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        player1.moveRight(Gdx.graphics.getDeltaTime());
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.X) && player1.playerstate != AIR) {
        player1.jump();
        // System.out.println(1.0/Gdx.graphics.getDeltaTime());
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
        for (int i = 0; i < 15; i++) {
            if(!(player1.playerstate==HIT_LEFT||player1.playerstate==HIT_RIGHT)){
                player1.dash(Gdx.graphics.getDeltaTime());}
            else{break;}
        }
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
        if(player1.direction==1){
            bulletlist.add(new Bullet(player1.getHitBox().x+47,player1.getHitBox().y+57,0));}
        else{
            bulletlist.add(new Bullet(player1.getHitBox().x,player1.getHitBox().y+57,Math.toRadians(180)));}
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {
        player1.setPosition(300, 366);player1.velocityY=0;
    }}
    public void loadlevel(String level){
        list.clear();
        FileHandle file=Gdx.files.internal(level);
        StringTokenizer tokens=new StringTokenizer(file.readString());
        while(tokens.hasMoreTokens()) {
            String type=tokens.nextToken();
            if(type.equals("brick")){
                list.add(new Brick(Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
            }
           else if(type.equals("spike")){
                list.add(new Spikes(Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
            }
            else if(type.equals("firegate")){
                list.add(new Firegate(Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
            }
           else if(type.equals("windgate")){
                list.add(new Windgate(Integer.parseInt(tokens.nextToken()),Integer.parseInt(tokens.nextToken())));
            }
        }
//////

    }
    public void nextlevel(){
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        player1.draw(batch);
        for (GameObject t : list) {
            t.draw(batch);
        } for (BulletPrototype bill : bulletlist) {
            bill.draw(batch);
        }

        batch.end();


           // loadlevel(level);
          //  Brick.level=level;

}
    public void gameover(){}
	@Override
	public void dispose () {
		batch.dispose();
		texture.dispose();
	}
	public void delay_sec(long s)
    {
        try {Thread.sleep(1000*s);}
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}