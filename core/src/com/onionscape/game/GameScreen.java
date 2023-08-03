package com.onionscape.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import gameplay.FightScene;

public class GameScreen implements Screen {
	private Game game;
	private FightScene fightScene;
	private Viewport viewport;
	
	private static final int MIN_WIDTH = 1280;
    private static final int MIN_HEIGHT = 720;
    private static final int MAX_WIDTH = 1920;
    private static final int MAX_HEIGHT = 1080;

	public GameScreen(Game game) {		
		this.game = game;
		viewport = new FitViewport(MAX_WIDTH, MAX_HEIGHT);
		this.fightScene = new FightScene(viewport);		
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(64/255f, 64/255f, 64/255f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// Render FightScene
		fightScene.render();
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		// Make sure the width and height are within our minimum and maximum values
        int finalWidth = Math.min(MAX_WIDTH, Math.max(MIN_WIDTH, width));
        int finalHeight = Math.min(MAX_HEIGHT, Math.max(MIN_HEIGHT, height));

        // Update the viewport with the adjusted width and height
        viewport.update(finalWidth, finalHeight, true);
        viewport.apply();
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

}

