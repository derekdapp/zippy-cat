package com.derkydapps.zippycat.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.derkydapps.zippycat.DesktopInterface;
import com.derkydapps.zippycat.MainGame;


public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Zippy Cat";
	        config.width = 272;
	        config.height = 408;
		new LwjglApplication(new MainGame(new DesktopInterface(), true), config);
	}
}
