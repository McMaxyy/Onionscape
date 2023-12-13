package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;

import storage.Storage;

public class Settings implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private GameScreen gameScreen; 
	private TextButton fullscreenBtn, borderlessBtn, backBtn, res1, res2;
	
	public Settings(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		storage.createFont();
		
		createComponents();
	}

	private void createComponents() {
		res1 = new TextButton("1080p", storage.buttonStyle);
		res1.setColor(Color.LIGHT_GRAY);
		res1.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			GameScreen.SELECTED_HEIGHT = 1080;
    			GameScreen.SELECTED_WIDTH = 1920;
    			gameScreen.resize(1920, 1080);
    			gameScreen.setCurrentState(GameScreen.SETTINGS);   			
    	    }});
		res1.setSize(300, 100);
		res1.setPosition(vp.getWorldWidth() / 4f, vp.getWorldHeight() / 3f);
		stage.addActor(res1);
		
		res2 = new TextButton("Ass res", storage.buttonStyle);
		res2.setColor(Color.LIGHT_GRAY);
		res2.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			GameScreen.SELECTED_HEIGHT = 1200;
    			GameScreen.SELECTED_WIDTH = 1920;
    			gameScreen.resize(1920, 1200);
    			gameScreen.setCurrentState(GameScreen.SETTINGS);   			
    	    }});
		res2.setSize(300, 100);
		res2.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 3f);
		stage.addActor(res2);
		
		fullscreenBtn = new TextButton("Fullscreen", storage.buttonStyle);
		fullscreenBtn.setColor(Color.LIGHT_GRAY);
		fullscreenBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
    			Gdx.graphics.setFullscreenMode(currentMode);
    	    }});
		fullscreenBtn.setSize(300, 100);
		fullscreenBtn.setPosition(vp.getWorldWidth() / 4f, vp.getWorldHeight() / 5f);
		stage.addActor(fullscreenBtn);
		
		borderlessBtn = new TextButton("Borderless", storage.buttonStyle);
		borderlessBtn.setColor(Color.LIGHT_GRAY);
		borderlessBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
    			Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
    	    }});
		borderlessBtn.setSize(300, 100);
		borderlessBtn.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 5f);
		stage.addActor(borderlessBtn);
		
		backBtn = new TextButton("Return", storage.buttonStyle);
		backBtn.setColor(Color.LIGHT_GRAY);
		backBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.setCurrentState(GameScreen.START_SCREEN);
    	    }});
		backBtn.setSize(200, 150);
		backBtn.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.2f);
		stage.addActor(backBtn);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		stage.act();
		stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
