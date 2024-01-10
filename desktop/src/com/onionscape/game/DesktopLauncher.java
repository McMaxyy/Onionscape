package com.onionscape.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher{
    
    private static boolean decorated = false; // Default to decorated

    public static void main(String[] arg) {
    	Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setForegroundFPS(60);
        config.setTitle("Onionscape");

        // Set the windowed mode to the screen size
        config.setWindowedMode(Lwjgl3ApplicationConfiguration.getDisplayMode().width,
                Lwjgl3ApplicationConfiguration.getDisplayMode().height);

        // Set the decoration status in the configuration
        config.setDecorated(decorated);
        config.setWindowIcon("player/MapIcon.png");

        int samples = 32;
        config.setBackBufferConfig(8, 8, 8, 8, 16, 0, samples);

        new Lwjgl3Application(new Boot(), config);
    }
}