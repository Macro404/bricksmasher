package com.bricksmasher.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.bricksmasher.BrickSmasher;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Bricksmasher";
		config.width = 800;
		config.height = 480;
		new LwjglApplication(new BrickSmasher(), config);
	}
}
