package com.onionscape.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import player.Storage;
import scenes.BerserkerSkillTree;
import scenes.FightScene;
import scenes.Home;
import scenes.Inventory;
import scenes.LoadingScreen;

public class GameScreen implements Screen {
	private Game game;
	private FightScene fightScene;
	private Home home;
	private BerserkerSkillTree zerkerTree;
	private Inventory inventory;
	private LoadingScreen loadingScreen;
	private Viewport viewport;
	public static boolean newGame;
	
	private static final int MIN_WIDTH = 1280;
    private static final int MIN_HEIGHT = 720;
    private static final int MAX_WIDTH = 1920;
    private static final int MAX_HEIGHT = 1080;
    
    public static final int LOADING_SCREEN = 0;
    public static final int HOME = 1;
    public static final int ZERKER_TREE = 2;
    public static final int INVENTORY = 3;
    public static final int FIGHT_SCENE = 4;
    private int currentState;

	public GameScreen(Game game) {		
		this.game = game;
		newGame = true;
		viewport = new FitViewport(MAX_WIDTH, MAX_HEIGHT);
	    setCurrentState(LOADING_SCREEN);
	}
	
	public void setCurrentState(int newState) {
	    currentState = newState;
	    switch (currentState) {
		    case LOADING_SCREEN:
	        	this.loadingScreen = new LoadingScreen(viewport, game, this);
	            Gdx.input.setInputProcessor(loadingScreen.stage);
	            break;
	        case FIGHT_SCENE:
	        	this.fightScene = new FightScene(viewport, game, this);
	            Gdx.input.setInputProcessor(fightScene.stage);
	            break;
	        case HOME:
	        	this.home = new Home(viewport, game, this);
	            Gdx.input.setInputProcessor(home.stage);
	            break;
	        case ZERKER_TREE:
	        	this.zerkerTree = new BerserkerSkillTree(viewport, game, this);
	            Gdx.input.setInputProcessor(zerkerTree.stage);
	            break;
	        case INVENTORY:
	        	this.inventory = new Inventory(viewport, game, this);
	            Gdx.input.setInputProcessor(inventory.stage);
	            break;
	    }
	}

	@Override
	public void render(float delta) {
	    Gdx.gl.glClearColor(55 / 255f, 55 / 255f, 55 / 255f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

	    switch (currentState) {
		    case LOADING_SCREEN:
	            loadingScreen.render(delta);
	            break;
	        case FIGHT_SCENE:
	            fightScene.render(delta);
	            break;
	        case HOME:
	            home.render(delta);
	            break;
	        case ZERKER_TREE:
	        	zerkerTree.render(delta);
	            break;
	        case INVENTORY:
	        	inventory.render(delta);
	            break;
	    }
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
		fightScene.dispose();
	}

}

