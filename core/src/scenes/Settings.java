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
	private TextButton fullscreenBtn, borderlessBtn, backBtn, windowedBtn;
	
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
		    	Gdx.graphics.setUndecorated(true);
		        Graphics.DisplayMode currentMode = Gdx.graphics.getDisplayMode();
		        Gdx.graphics.setWindowedMode(currentMode.width, currentMode.height);
		    }
		});
		borderlessBtn.setSize(300, 100);
		borderlessBtn.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 5f);
		stage.addActor(borderlessBtn);
		
		windowedBtn = new TextButton("Windowed", storage.buttonStyle);
		windowedBtn.setColor(Color.LIGHT_GRAY);
		windowedBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Gdx.graphics.setUndecorated(false);
    			Gdx.graphics.setWindowedMode(1600, 900);
    	    }});
		windowedBtn.setSize(300, 100);
		windowedBtn.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 10f);
		stage.addActor(windowedBtn);
		
		backBtn = new TextButton("Return", storage.buttonStyle);
		backBtn.setColor(Color.LIGHT_GRAY);
		backBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(Home.newHome)
    				gameScreen.setCurrentState(GameScreen.START_SCREEN);
    			else
    				gameScreen.setCurrentState(GameScreen.HOME);
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
