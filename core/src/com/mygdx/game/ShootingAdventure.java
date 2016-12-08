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
    //button goes here
    private Rectangle clickStart;
   private Texture textureStart;
    private Sprite spriteStart;

    //


    private SpriteBatch batch;
    private Texture texture;
    private Sprite sprite;
    boolean changelevel=false;
    public boolean duplicatestopper;
    private OrthographicCamera camera;
    private Heroreploid player1;
    private int gamestate=1;
    public String level="testlevel";
    public ArrayList<GameObject> list = new ArrayList<GameObject>();
    public ArrayList<Enemy> enemies = new ArrayList<Enemy>();
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
         clickStart=new Rectangle(0,0,158,52);
        textureStart=new Texture(Gdx.files.internal("sprite/button_start.png"));
       spriteStart=new Sprite(textureStart,0,0,158,52);
        spriteStart.setPosition(350,250);
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
        this.gameover();break;
}
    }

    public void updateCamera() {//HUD must stay with player in screen
camera.position.x=player1.getHitBox().x;
        camera.position.y=player1.getHitBox().y+64;
camera.update();



     }
public void mainmenu(){
    Gdx.gl.glClearColor(1, 1, 1, 1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.setProjectionMatrix(camera.combined);
    batch.begin();

spriteStart.draw(batch);
    batch.end();
    camera.position.x=400;camera.position.y=240;camera.update();
    clickStart.x=350;clickStart.y=250;
    if(Gdx.input.isTouched()){gamestate=2;changelevel=true;
   }
}
public void maingame() {
    Gdx.gl.glClearColor(1,1,1,1);
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    batch.setProjectionMatrix(camera.combined);
    loadlevel(level);loadmonster(level);
    batch.begin();

    player1.draw(batch);
    for (GameObject t : list) {
        t.draw(batch);
    }
    for (BulletPrototype bill : bulletlist) {
        bill.draw(batch);
    }
    for (Enemy foe : enemies) {
        foe.draw(batch);
    }
    batch.end();

    //updates
    player1.update(0.04);
    for(Enemy foe : enemies){foe.update(0.04);}

    for (BulletPrototype bill : bulletlist) {
        bill.update(0.04);
        if(bill.isDead()){this.deadbulletlist.add(bill);}
        for(GameObject t : list){
            if(t.getHitBox().overlaps(bill.getHitBox())) {
                deadbulletlist.add(bill);
            }}
            //when bullets hits enemies
        for(Enemy foe : enemies){

            if(foe.getHitBox().overlaps(bill.getHitBox())) {
                if(foe.shotby(bill)){enemies.remove(foe);}
                deadbulletlist.add(bill);
            }}


    }
    while(deadbulletlist.size()!=0) {
        bulletlist.remove(deadbulletlist.get(0));
        deadbulletlist.remove(0);
    }

    for (GameObject i:list) {
        switch (player1.hits(i.getHitBox())) {
            case GROUND:
                switch (i.hitAction(GROUND)) {
                    case GROUND:
                        player1.action(GROUND, 0,i.getHitBox().y +i.getHitBox().height);
                        break;
                    case DIE:
                        if (i.getClass() == Spikes.class) {
                            player1.setPosition(100, 466);
                        }
                        break;
                    case DOOR:
                        if (i.getClass() == Firegate.class) {
                            level = "fire";
                        } else if (i.getClass() == Windgate.class) {
                            level = "wind";
                        } else if (i.getClass() == Poisiongate.class) {
                            level = "poision";
                        } else if (i.getClass() == Leafgate.class) {
                            level = "leaf";
                        }
                        changelevel = true;
                        break;
                }
                break;
            case HIT_LEFT:
                switch (i.hitAction(HIT_LEFT)) {
                    case GROUND:
                        player1.action(HIT_LEFT,i.getHitBox().x +i.getHitBox().width + 11, 0);
                        break;
                    case DIE:
                        if (i.getClass() == Spikes.class) {
                            player1.setPosition(100, 466);
                        }
                        break;
                    case DOOR:
                        if (i.getClass() == Firegate.class) {
                            level = "fire";
                        } else if (i.getClass() == Windgate.class) {
                            level = "wind";
                        } else if (i.getClass() == Poisiongate.class) {
                            level = "poision";
                        } else if (i.getClass() == Leafgate.class) {
                            level = "leaf";
                        }
                        changelevel = true;
                        break;
                }
                break;
            case HIT_RIGHT:
                switch (i.hitAction(HIT_RIGHT)) {
                    case GROUND:
                        player1.action(HIT_RIGHT,i.getHitBox().x - player1.getHitBox().width - 11, 0);
                        break;
                    case DIE:
                        if (i.getClass() == Spikes.class) {
                            player1.setPosition(100, 466);
                        }
                        break;
                    case DOOR:
                        if (i.getClass() == Firegate.class) {
                            level = "fire";
                        } else if (i.getClass() == Windgate.class) {
                            level = "wind";
                        } else if (i.getClass() == Poisiongate.class) {
                            level = "poision";
                        } else if (i.getClass() == Leafgate.class) {
                            level = "leaf";
                        }
                        changelevel = true;
                        break;
                }
                break;
            case HIT_CEILING:

                switch (i.hitAction(HIT_CEILING)) {
                    case GROUND:
                        player1.action(HIT_CEILING, 0,i.getHitBox().y - player1.getHitBox().height);
                        break;
                    case DIE:
                        if (i.getClass() == Spikes.class) {
                            player1.setPosition(100, 466);
                        }
                        break;
                    case DOOR:
                        if (i.getClass() == Firegate.class) {
                            level = "fire";
                        } else if (i.getClass() == Windgate.class) {
                            level = "wind";
                        } else if (i.getClass() == Poisiongate.class) {
                            level = "poision";
                        } else if (i.getClass() == Leafgate.class) {
                            level = "leaf";
                        }
                        changelevel = true;
                        break;
                }
                break;
        }
        for (Enemy foe : enemies) {
            switch (foe.hits(i.getHitBox())) {
                case GROUND:
                    foe.action(GROUND, 0,i.getHitBox().y +i.getHitBox().height);
                    break;
                case HIT_LEFT:
                    switch (i.hitAction(HIT_LEFT)) {
                        case GROUND:
                            foe.action(HIT_LEFT,i.getHitBox().x +i.getHitBox().width + 11, 0);
                        if(foe.getClass()==Mudboulder.class){
                    foe.direction=1;foe.interval=foe.intervalmax;
                       }
                }
                    break;
                case HIT_RIGHT:
                    switch (i.hitAction(HIT_RIGHT)) {
                        case GROUND:
                           foe.action(HIT_RIGHT,i.getHitBox().x - player1.getHitBox().width - 11, 0);
                        if (foe.getClass() == Mudboulder.class) {
                            foe.direction = 0;
                            foe.interval = foe.intervalmax;                        }

                }
                        break;



                case HIT_CEILING:
                    foe.action(HIT_CEILING, 0,i.getHitBox().y - foe.getHitBox().height);
                    break;

            }
        }
    }



    if(changelevel){
        player1.velocityY=0;
loadlevel(level);loadmonster(level);
  Brick.level=level;
        changelevel=false;

    }

    updateCamera();
    //controls
    if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
        player1.moveLeft(0.04f);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        player1.moveRight(0.04f);
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.X) && player1.playerstate != AIR) {
       player1.jump();//System.out.println(enemies.size());
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
        for (int j = 0; j <33; j++) {
            if(!(player1.playerstate==HIT_LEFT||player1.playerstate==HIT_RIGHT)){
                player1.dash(0.04f);}
            else{break;}
        }
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.C)) {
        if(player1.direction==1){
            bulletlist.add(new Bullet(player1.bottom.x+47,player1.bottom.y+57,0));}
        else{
            bulletlist.add(new Bullet(player1.bottom.x,player1.bottom.y+57,Math.toRadians(180)));}
    }
    if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {gamestate=1;level="testlevel";
        list.clear();deadbulletlist.clear();bulletlist.clear();enemies.clear();
        player1.setPosition(400, 300); player1.velocityY=0;
    }//test recall

}
    public void loadlevel(String level){
        //objects
        if (changelevel) {
            list.clear();
            FileHandle file=Gdx.files.internal(level);
            StringTokenizer tokens=new StringTokenizer(file.readString());
        while(tokens.hasMoreTokens()) {
            String type = tokens.nextToken();
            if (type.equals("brick")) {
                list.add(new Brick(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken())));
            } else if (type.equals("spike")) {
                list.add(new Spikes(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken())));
            } else if (type.equals("firegate")) {
                list.add(new Firegate(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken())));
            } else if (type.equals("windgate")) {
                list.add(new Windgate(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken())));
            } else if (type.equals("poisiongate")) {
                list.add(new Poisiongate(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken())));
            } else if (type.equals("leafgate")) {
                list.add(new Leafgate(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken())));
            }
        }
        }
    }


    //monsters
    public void loadmonster (String level) {
        if (changelevel) {
            deadbulletlist.clear();bulletlist.clear();enemies.clear();
            FileHandle file = Gdx.files.internal(level);
            StringTokenizer tokens = new StringTokenizer(file.readString());
            file = Gdx.files.internal(level + "_enemy");
            while (tokens.hasMoreTokens()) {
                String type =  tokens.nextToken();
                if (type.equals("mudboulder")) {
                    enemies.add(new Mudboulder(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
                            Integer.parseInt(tokens.nextToken())));
                }
            }
            if (level.equals("fire")) {
                player1.setPosition(328, 3016);
            }
        }
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


}
    public void gameover(){}
	@Override
	public void dispose () {
		batch.dispose();
		texture.dispose();
	}
	public void delay_sec(int s)
    {
        try {Thread.sleep(1000*s);}
        catch (InterruptedException e){
            e.printStackTrace();
        }
    }
}