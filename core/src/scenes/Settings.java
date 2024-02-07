package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Graphics.Monitor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;
import com.onionscape.game.MusicManager;
import com.onionscape.game.ScreenShake;

import storage.Storage;

@SuppressWarnings("unused")
public class Settings implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private GameScreen gameScreen; 
	private TextButton fullscreenBtn, borderlessBtn, backBtn, windowedBtn, musicUp, musicDown, sfxUp, sfxDown;
	private ShapeRenderer musicBar = new ShapeRenderer();
	private ShapeRenderer sfxBar = new ShapeRenderer();
	private SpriteBatch mapBatch = new SpriteBatch();
	private Texture mapTexture;
	private Label musicLbl, sfxLbl, screenModeLbl, screenShakeLbl;
	private CheckBox screenShake;
	private static boolean shake = true;
	
	public Settings(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		storage.createFont();
		
		mapTexture = Storage.assetManager.get("maps/SettingsScreen.png", Texture.class);
		mapTexture.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
		
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
		fullscreenBtn.setSize(200, 50);
		fullscreenBtn.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 1.7f);
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
		borderlessBtn.setSize(200, 50);
		borderlessBtn.setPosition(vp.getWorldWidth() / 2.24f, vp.getWorldHeight() / 1.7f);
		stage.addActor(borderlessBtn);
		
		windowedBtn = new TextButton("Windowed", storage.buttonStyle);
		windowedBtn.setColor(Color.LIGHT_GRAY);
		windowedBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Gdx.graphics.setUndecorated(false);
    			Gdx.graphics.setWindowedMode(1280, 720);
    	    }});
		windowedBtn.setSize(200, 50);
		windowedBtn.setPosition(vp.getWorldWidth() / 1.79f, vp.getWorldHeight() / 1.7f);
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
		musicUp.setPosition(vp.getWorldWidth() / 1.68f, vp.getWorldHeight() / 2.2f, Align.center);
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
		
		sfxUp = new TextButton("+", storage.buttonStyle);
		sfxUp.setColor(Color.LIGHT_GRAY);
		sfxUp.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(MusicManager.sfxVol <= 1f) {
    				MusicManager.sfxVol += 0.05f;
    				if(MusicManager.sfxVol <= 0.0f)
    					MusicManager.sfxVol = 0.0f; 
    			}    				
    	    }});
		sfxUp.setSize(30, 30);
		sfxUp.setPosition(vp.getWorldWidth() / 1.68f, vp.getWorldHeight() / 2.7f, Align.center);
		stage.addActor(sfxUp);
		
		sfxDown = new TextButton("-", storage.buttonStyle);
		sfxDown.setColor(Color.LIGHT_GRAY);
		sfxDown.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(MusicManager.sfxVol >= 0.045f) {
    				MusicManager.sfxVol -= 0.05f;
    				if(MusicManager.sfxVol <= 0.0f)
    					MusicManager.sfxVol = 0.0f; 
    			} 
    	    }});
		sfxDown.setSize(30, 30);
		sfxDown.setPosition(sfxUp.getX() - 370, sfxUp.getY());
		stage.addActor(sfxDown);
		
		backBtn = new TextButton("Return", storage.homeBtnStyle);
		backBtn.setColor(Color.LIGHT_GRAY);
		backBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(Home.newHome)
    				gameScreen.switchToNewState(GameScreen.START_SCREEN);
    			else if(Merchant.raid)
    				gameScreen.switchToNewState(GameScreen.FOREST_MAP);
    			else
    				gameScreen.switchToNewState(GameScreen.HOME);
    	    }});
		backBtn.setSize(150, 40);
		backBtn.setPosition(vp.getWorldWidth() / 50f, vp.getWorldHeight() / 1.064f);
		stage.addActor(backBtn);
		
		musicLbl = new Label("Music Volume", storage.labelStyleBlack);
		musicLbl.setSize(300, 30);
		float centerX = (musicUp.getX() + musicDown.getRight()) / 1.88f;
		musicLbl.setPosition(centerX - musicLbl.getWidth() / 2f, musicUp.getY() + musicUp.getHeight() + 10);
		stage.addActor(musicLbl);
		
		sfxLbl = new Label("Sound Effects Volume", storage.labelStyleBlack);
		sfxLbl.setSize(300, 30);
		centerX = (sfxUp.getX() + sfxDown.getRight()) / 2;
		sfxLbl.setPosition(centerX - sfxLbl.getWidth() / 2f, sfxUp.getY() + sfxUp.getHeight() + 10);
		stage.addActor(sfxLbl);
		
		screenModeLbl = new Label("Display Mode", storage.labelStyleBlack);
		screenModeLbl.setPosition(borderlessBtn.getX() + 20, borderlessBtn.getY() + borderlessBtn.getHeight() + 20);
		stage.addActor(screenModeLbl);		
		
		screenShake = new CheckBox("", storage.skin);
		screenShake.setPosition(vp.getWorldWidth() / 1.7f, vp.getWorldHeight() / 4f);
		screenShake.setChecked(shake);
		screenShake.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(shake) {
    				shake = false;
    				ScreenShake.rumblePower = 0;
    			}
    			else {
    				shake = true;
    				ScreenShake.rumblePower = 2;
    			}
    	    }});
		stage.addActor(screenShake);
		
		screenShakeLbl = new Label("Screen shake", storage.labelStyleBlack);
		screenShakeLbl.setPosition(screenShake.getX() - screenShakeLbl.getWidth() * 2, screenShake.getY());
		stage.addActor(screenShakeLbl);
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
	
	private void drawSfxBar() {
		sfxBar.setProjectionMatrix(vp.getCamera().combined);
		sfxBar.begin(ShapeRenderer.ShapeType.Filled);
		
		sfxBar.setColor(Color.WHITE);
		sfxBar.rect(sfxUp.getX() - 320, sfxUp.getY(), 300, 30);
		
		sfxBar.setColor(Color.BLACK);
        float barWidth = (float)MusicManager.sfxVol / 1.0f;
        int width = (int)(barWidth * 300);
        width = (width / 10) * 10;
        if(MusicManager.sfxVol <= 0.045f)
        	width = 0;
        sfxBar.rect(sfxUp.getX() - 320, sfxUp.getY(), width, 30);
        sfxBar.end(); 
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		mapBatch.setProjectionMatrix(vp.getCamera().combined);
		
		mapBatch.begin();
		mapBatch.draw(mapTexture, 0, 0, GameScreen.SELECTED_WIDTH, GameScreen.SELECTED_HEIGHT);
		mapBatch.end();
		
		drawSoundBar();
		drawSfxBar();
		
		if(Gdx.input.isKeyPressed(Keys.F5)) {
			for(Actor actor : stage.getActors()) {
				actor.addAction(Actions.removeActor());
			}
			
			createComponents();
		}
		
		stage.act();
		stage.draw();	
	}

	@Override
	public void resize(int width, int height) {
		musicBar.setProjectionMatrix(vp.getCamera().combined);
		sfxBar.setProjectionMatrix(vp.getCamera().combined);
		mapBatch.setProjectionMatrix(vp.getCamera().combined);
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
