package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
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
    private Rectangle iconsquare,clickStart;
    private Texture textureStart;
    private Sprite spriteStart;
    private BitmapFont ammostatus;
    //

    private BitmapFont livesfont;
    private SpriteBatch batch;
    private Texture texture,icontexture;
    private Sprite sprite,iconstatus,hpbar;
    boolean changelevel=false;
    private OrthographicCamera camera;
    private Heroreploid player1;
    private int gamestate=1;
    public String level="testlevel";
    Texture textureleaf,hptexture;
    Sprite backgroundleaf;
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
        textureleaf =new Texture(Gdx.files.internal("sprite/leafbackground.png"));
        backgroundleaf=new Sprite(textureleaf,0,0,9664,2952);
        player1.weaponlist=new WeaponManager(5);
        player1.weaponlist.weaponindex=0;
        player1.weaponlist.ammo[0]=player1.weaponlist.maxammo[0]=511;
        player1.weaponlist.ammo[1]=player1.weaponlist.maxammo[1]=15;
        player1.weaponlist.ammo[2]=player1.weaponlist.maxammo[2]=20;
        player1.weaponlist.ammo[3]=player1.weaponlist.maxammo[3]=20;
        player1.weaponlist.ammo[4]= player1.weaponlist.maxammo[4]=20;
        player1.weaponlist.isuseammo[0]=false;player1.weaponlist.isuseammo[1]=true;
        player1.weaponlist.isuseammo[2]=true;player1.weaponlist.isuseammo[3]=true;
        player1.weaponlist.isuseammo[4]=true;
        player1.setPosition(300, 300);
        clickStart=new Rectangle(0,0,158,52);
        icontexture=new Texture(Gdx.files.internal("sprite/icontexture.png"));

        iconstatus=new Sprite(icontexture,46,38,20,20);
        iconstatus.setPosition(340,370);
        hpbar=new Sprite(new Texture(Gdx.files.internal("sprite/hpbar.png")),0,0,7,114);
        textureStart=new Texture(Gdx.files.internal("sprite/button_start.png"));
        spriteStart=new Sprite(textureStart,0,0,158,52);
        spriteStart.setPosition(350,250);
          hptexture=new Texture(Gdx.files.internal("sprite/hpbar.png"));
        ammostatus=new BitmapFont(Gdx.files.internal("font.fnt"),Gdx.files.internal("font.png"),false);
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
        camera.position.y=player1.getHitBox().y+100;
        iconstatus.setX(player1.full.x-490);iconstatus.setY(player1.full.y+170);
        hpbar.setX(player1.full.x-530);hpbar.setY(player1.full.y+150);
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
        if(Gdx.input.isTouched()){gamestate=2;Brick.level=level="testlevel";changelevel=true;
        }
    }
    public void maingame() {
        Gdx.gl.glClearColor(1,1,1,1);
      Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);
        loadlevel(level);loadmonster(level);
        batch.begin();
//if(level=="leaf"){batch.draw(textureleaf,0,0);}
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
        iconstatus.draw(batch);
        if(!player1.weaponlist.isuseammo[player1.weaponlist.weaponindex]){
            ammostatus.draw(batch,"Inf/Inf",player1.full.x-530,player1.full.y+150);
        } else{
            ammostatus.draw(batch,(int)player1.weaponlist.ammo[player1.weaponlist.weaponindex]+"/"+
                    (int)player1.weaponlist.maxammo[player1.weaponlist.weaponindex],player1.full.x-530,player1.full.y+150);
        }
        hpbar.draw(batch);
        batch.end();

        //updates
        player1.update(0.04);
        hpbar=new Sprite(hptexture,0,3*(player1.maxHP-player1.HP),7,3*player1.HP);
        switch(player1.weaponlist.weaponindex) {
            case 0:
                iconstatus=new Sprite(icontexture,46,38,20,20);break;
            case 1:
                iconstatus=new Sprite(icontexture,73,38,20,20);break;
            case 2:
                iconstatus=new Sprite(icontexture,100,39,20,20);break;
            case 3:
                iconstatus=new Sprite(icontexture,126,37,20,20);break;
            case 4:
                iconstatus=new Sprite(icontexture,151,37,20,20);break;
        }
        for(Enemy foe : enemies){
            foe.update(0.04);
        if(foe.getClass()==Turret.class){
            Turret t=(Turret)foe;
            if(t.interval==t.intervalmax){
            EnemyBullet b1=new EnemyBullet(t.hitbox.x,t.hitbox.y,Math.toRadians(90*t.direction-90),"normal");
            bulletlist.add(b1);
            EnemyBullet b2=new EnemyBullet(t.hitbox.x,t.hitbox.y,Math.toRadians(90*t.direction-45),"normal");
            bulletlist.add(b2);
            EnemyBullet b3=new EnemyBullet(t.hitbox.x,t.hitbox.y,Math.toRadians(90*t.direction-135),"normal");
            bulletlist.add(b3);}
        }
        }
///updates for bullets
        for (BulletPrototype bill : bulletlist) {
            bill.update(0.04);
            if(bill.isDead()){this.deadbulletlist.add(bill);}
            for(GameObject t : list){
                if(t.getHitBox().overlaps(bill.getHitBox())&&(bill.getClass()!=Heatvision.class)) {
                    deadbulletlist.add(bill);
                }}
            //when bullets hits enemies
            for(int i=0;i<enemies.size();i++){
if(bill.getClass()!=EnemyBullet.class){
                if(enemies.get(i).getHitBox().overlaps(bill.getHitBox())) {
                    if(enemies.get(i).shotby(bill)){enemies.remove(enemies.get(i));}
                   if(bill.getClass()!=Heatvision.class) deadbulletlist.add(bill);
                }
}
            }
if(bill.getClass()==EnemyBullet.class) {
    if(bill.getHitBox().overlaps(player1.getHitBox())){
        if(player1.flinchtimer==0){player1.HP-=(player1.HP>=3)?3:player1.HP;player1.flinchtimer=0.8;}
        deadbulletlist.add(bill);
    }
}
        }
        ///
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
                           player1.HP=0;player1.setPosition(100, 466);
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
                        case BOSS:
                            if(level=="fire"){player1.setPosition(4352,640);}
                            else if(level=="leaf"){player1.setPosition(6144,512);}
                            player1.playerstate=AIR;
                            break;
                    }
                    break;
                case HIT_LEFT:
                    switch (i.hitAction(HIT_LEFT)) {
                        case GROUND:
                            player1.action(HIT_LEFT,i.getHitBox().x +i.getHitBox().width , 0);
                            break;
                        case DIE:
                            if (i.getClass() == Spikes.class) {
                                player1.HP=0;player1.setPosition(100, 466);
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
                        case BOSS:
                            if(level=="fire"){player1.setPosition(4352,640);}
                            else if(level=="leaf"){player1.setPosition(6144,512);}
                            player1.playerstate=AIR;
                            break;
                    }
                    break;
                case HIT_RIGHT:
                    switch (i.hitAction(HIT_RIGHT)) {
                        case GROUND:
                            player1.action(HIT_RIGHT,i.getHitBox().x - player1.getHitBox().width, 0);
                            break;
                        case DIE:
                            if (i.getClass() == Spikes.class) {
                                player1.HP=0;player1.setPosition(100, 466);
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
                        case BOSS:
                            if(level=="fire"){player1.setPosition(4352,640);}
                            else if(level=="leaf"){player1.setPosition(6144,512);}
                            player1.playerstate=AIR;
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
                                player1.setPosition(0, -600);
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
                        case BOSS:
                            if(level=="fire"){player1.setPosition(4352,640);}
                            else if(level=="leaf"){player1.setPosition(6144,512);}
                            player1.playerstate=AIR;
                            break;
                    }
                    break;
            }
            for (Enemy foe : enemies) {
                if (foe.getClass() != Turret.class&&foe.getClass() != Woodblock.class) {
                    switch (foe.hits(i.getHitBox())) {
                        case GROUND:
                            foe.action(GROUND, 0, i.getHitBox().y + i.getHitBox().height);
                            break;
                        case HIT_LEFT:
                            switch (i.hitAction(HIT_LEFT)) {
                                case GROUND:
                                    foe.action(HIT_LEFT, i.getHitBox().x + i.getHitBox().width + 11, 0);
                                    if (foe.getClass() == Mudboulder.class) {
                                        foe.direction = 1;
                                        foe.interval = foe.intervalmax;
                                    }
                            }
                            break;
                        case HIT_RIGHT:
                            switch (i.hitAction(HIT_RIGHT)) {
                                case GROUND:
                                    foe.action(HIT_RIGHT, i.getHitBox().x - player1.getHitBox().width - 11, 0);
                                    if (foe.getClass() == Mudboulder.class) {
                                        foe.direction = 0;
                                        foe.interval = foe.intervalmax;
                                    }

                            }
                            break;


                        case HIT_CEILING:
                            foe.action(HIT_CEILING, 0, i.getHitBox().y - foe.getHitBox().height);
                            break;
                    }

                }
                if (foe.getClass() != Woodblock.class) {
if(player1.getHitBox().overlaps(foe.getHitBox())&&player1.flinchtimer==0) {
player1.HP-=(player1.HP>=4)?4:player1.HP;player1.flinchtimer=1.5;
}
                }
                else{
                    switch(player1.hits(foe.getHitBox())) {
                        case GROUND:
                            player1.action(GROUND, 0,i.getHitBox().y +i.getHitBox().height);
                            break;
                        case HIT_LEFT:
                            player1.action(HIT_LEFT,i.getHitBox().x +i.getHitBox().width , 0);
                            break;
                        case HIT_RIGHT:
                            player1.action(HIT_RIGHT,i.getHitBox().x - player1.getHitBox().width, 0);
                        case HIT_CEILING:
                            player1.action(HIT_CEILING, 0,i.getHitBox().y - player1.getHitBox().height);

                    }
                }
            }
        }



        if(changelevel){
            player1.velocityY=0;
            loadlevel(level);loadmonster(level);
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
            player1.jump();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            for (int j = 0; j <33; j++) {
                if(!(player1.playerstate==HIT_LEFT||player1.playerstate==HIT_RIGHT)){
                    player1.dash(0.04f);}
                else{break;}
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {player1.weaponlist.changeweaponleft();
           System.out.println(player1.weaponlist.weaponindex+"/"+player1.weaponlist.arsenal);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {player1.weaponlist.changeweaponright();
           System.out.println(player1.weaponlist.weaponindex+"/"+player1.weaponlist.arsenal);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.C)
                &&(!player1.weaponlist.isuseammo[player1.weaponlist.weaponindex]
      ||(player1.weaponlist.isuseammo[player1.weaponlist.weaponindex]&&
                player1.weaponlist.ammo[player1.weaponlist.weaponindex]>0) ))
        {
            if(player1.weaponlist.isuseammo[player1.weaponlist.weaponindex]){
                player1.weaponlist.ammo[player1.weaponlist.weaponindex]-=1;}
            switch(player1.weaponlist.weaponindex) {
                case 0:
                if (player1.direction == 1) {
                    bulletlist.add(new Bullet(player1.full.x + 47, player1.full.y + 57, 0, false));
                } else {
                    Bullet xbuster = new Bullet(player1.full.x, player1.full.y + 57, Math.toRadians(180), true);
                    bulletlist.add(xbuster);
                }
                break;
                case 1:
                    if (player1.direction == 1) {
                        bulletlist.add(new Heatvision(player1.full.x, player1.full.y + 82,0));
                    } else {
                        Heatvision hv = new Heatvision(player1.full.x-900, player1.full.y +82,Math.toRadians(180));
                        bulletlist.add(hv);
                    }
                    break;
            }
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.B)) {gamestate=1;level="testlevel";player1.HP=player1.maxHP;
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
                } else if (type.equals("bossgate")) {
                    list.add(new Bossgate(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
                            Integer.parseInt(tokens.nextToken())));
                }

                Brick.level=level;
            }
        }
    }


    //monsters
    public void loadmonster (String level) {
        if (changelevel) {
            deadbulletlist.clear();bulletlist.clear();enemies.clear();
            FileHandle file = Gdx.files.internal(level+"_enemy");
            StringTokenizer tokens = new StringTokenizer(file.readString());
            while (tokens.hasMoreTokens()) {
                String type =  tokens.nextToken();
                if (type.equals("mudboulder")) {
                    enemies.add(new Mudboulder(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
                            Integer.parseInt(tokens.nextToken())));
                }
               else if (type.equals("turret")) {
                    enemies.add(new Turret(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken()),
                            Integer.parseInt(tokens.nextToken())));
                }
                else if (type.equals("woodblock")) {
                    enemies.add(new Woodblock(Integer.parseInt(tokens.nextToken()), Integer.parseInt(tokens.nextToken())));
                }
            }

            if (level.equals("fire")) {
                player1.setPosition(328, 3016);
            }
           else if (level.equals("leaf")) {
                player1.setPosition(640,2176);
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