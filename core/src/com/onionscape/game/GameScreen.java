package com.onionscape.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import scenes.BerserkerSkillTree;
import scenes.FightScene;
import scenes.ForestMap;
import scenes.Home;
import scenes.Inventory;
import scenes.LoadingScreen;
import scenes.Merchant;
import scenes.RaidTextScenes;
import scenes.Settings;
import scenes.StartScreen;

public class GameScreen implements Screen {
	private Game game;
	private FightScene fightScene;
	private Home home;
	private BerserkerSkillTree zerkerTree;
	private Inventory inventory;
	private LoadingScreen loadingScreen;
	public ForestMap forestMap;
	private RaidTextScenes textScene;
	private StartScreen startScreen;
	private Merchant merchant;
	private Settings settings;
	private Viewport viewport;
	public static boolean newGame = true;
	
	private static final int MIN_WIDTH = 1280;
    private static final int MIN_HEIGHT = 720;
    public static final int MAX_WIDTH = 1920;
    public static final int MAX_HEIGHT = 1080;
    
    public static final int LOADING_SCREEN = 0;
    public static final int HOME = 1;
    public static final int ZERKER_TREE = 2;
    public static final int INVENTORY = 3;
    public static final int FIGHT_SCENE = 4;
    public static final int FOREST_MAP = 5;
    public static final int TEXT_SCENE = 6;
    public static final int START_SCREEN = 7;
    public static final int MERCHANT = 8;
    public static final int SETTINGS = 9;
    private int currentState;

	public GameScreen(Game game) {		
		this.game = game;
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
	        case FOREST_MAP:
	        	if(this.forestMap == null)
	        		this.forestMap = new ForestMap(viewport, game, this);
	            Gdx.input.setInputProcessor(forestMap.stage);
	            break;
	        case TEXT_SCENE:
	        	this.textScene = new RaidTextScenes(viewport, game, this);
	            Gdx.input.setInputProcessor(textScene.stage);
	            break;
	        case START_SCREEN:
	        	this.startScreen = new StartScreen(viewport, game, this);
	            Gdx.input.setInputProcessor(startScreen.stage);
	            break;
	        case MERCHANT:
	        	this.merchant = new Merchant(viewport, game, this);
	            Gdx.input.setInputProcessor(merchant.stage);
	            break;
	        case SETTINGS:
	        	this.settings = new Settings(viewport, game, this);
	            Gdx.input.setInputProcessor(settings.stage);
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
	        case FOREST_MAP:
	        	forestMap.render(delta);
	        	break;
	        case TEXT_SCENE:
	        	textScene.render(delta);
	        	break;
	        case START_SCREEN:
	        	startScreen.render(delta);
	        	break;
	        case MERCHANT:
	        	merchant.render(delta);
	        	break;
	        case SETTINGS:
	        	settings.render(delta);
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
        viewport.getCamera().update(); 
        
        if(fightScene != null)
        	fightScene.resize(width, height);
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
		home.dispose();
		inventory.dispose();
		zerkerTree.dispose();
		loadingScreen.dispose();
		textScene.dispose();
		merchant.dispose();
		settings.dispose();
	}

}

