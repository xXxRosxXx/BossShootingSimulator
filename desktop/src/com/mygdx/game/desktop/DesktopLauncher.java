package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.ShootingAdventure;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title="Shooting Simulator";
		config.width=1260;config.height=700;
        ShootingAdventure game=new ShootingAdventure();
		new LwjglApplication(game,config);

	}
}