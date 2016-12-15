package com.mygdx.game;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 * Created by Ros on 15-Dec-16.
 */
public class SoundManager {
    public static Sound basicbullet;
    public static Sound die;
    public static Sound hv;
    public static Sound hurt;
    public static Sound jump;
    public static void create(){
        basicbullet= Gdx.audio.newSound(Gdx.files.internal("sound/MMX4SFX/ST08_01_00004.wav"));
       die = Gdx.audio.newSound(Gdx.files.internal("sound/MMX4SFX/ST5_1_1_00004.wav"));
        hv= Gdx.audio.newSound(Gdx.files.internal("sound/MMX4SFX/PL00_U_00016.wav"));
        hurt=Gdx.audio.newSound(Gdx.files.internal("sound/MMX4SFX/PL00_U_00033.wav"));
        jump=Gdx.audio.newSound(Gdx.files.internal("sound/MMX4SFX/PL00_U_00039.wav"));
    }
    public static void dispose(){
        basicbullet.dispose();hv.dispose();
        hurt.dispose();die.dispose();jump.dispose();
    }
}
