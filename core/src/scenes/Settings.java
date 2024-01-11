package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;
import com.onionscape.game.MusicManager;

import storage.Storage;

public class Settings implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private GameScreen gameScreen; 
	private TextButton fullscreenBtn, borderlessBtn, backBtn, windowedBtn, musicUp, musicDown, sfxUp, sfxDown;
	private ShapeRenderer musicBar = new ShapeRenderer();
	
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
    			Gdx.graphics.setWindowedMode(1280, 720);
    	    }});
		windowedBtn.setSize(300, 100);
		windowedBtn.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 10f);
		stage.addActor(windowedBtn);
		
		musicUp = new TextButton("+", storage.buttonStyle);
		musicUp.setColor(Color.LIGHT_GRAY);
		musicUp.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(MusicManager.musicVol <= 1f) {
    				MusicManager.musicVol += 0.05f;
    				MusicManager.getInstance().changeVolume();
    			}    				
    	    }});
		musicUp.setSize(30, 30);
		musicUp.setPosition(vp.getWorldWidth() / 1.5f, vp.getWorldHeight() / 1.4f);
		stage.addActor(musicUp);
		
		musicDown = new TextButton("-", storage.buttonStyle);
		musicDown.setColor(Color.LIGHT_GRAY);
		musicDown.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(MusicManager.musicVol >= 0.045f) {
    				MusicManager.musicVol -= 0.05f;
    				MusicManager.getInstance().changeVolume();
    			} 
    	    }});
		musicDown.setSize(30, 30);
		musicDown.setPosition(musicUp.getX() - 370, musicUp.getY());
		stage.addActor(musicDown);
		
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
	
	private void drawSoundBar() {
		musicBar.setProjectionMatrix(vp.getCamera().combined);
		musicBar.begin(ShapeRenderer.ShapeType.Filled);
		
		musicBar.setColor(Color.WHITE);
		musicBar.rect(musicUp.getX() - 320, musicUp.getY(), 300, 30);
		
		musicBar.setColor(Color.BLACK);
        float barWidth = (float)MusicManager.musicVol / 1.0f;
        int width = (int)(barWidth * 300);
        width = (width / 10) * 10;
        if(MusicManager.musicVol <= 0.045f)
        	width = 0;
        musicBar.rect(musicUp.getX() - 320, musicUp.getY(), width, 30);
        musicBar.end(); 
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		drawSoundBar();
		
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
