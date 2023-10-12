package com.onionscape.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import com.onionscape.game.Boot;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("Onionscape");
		config.setWindowedMode(1280, 720);
		int samples = 32;
		config.setBackBufferConfig(8, 8, 8, 8, 16, 0, samples);
		new Lwjgl3Application(new Boot(), config);
	}
}
