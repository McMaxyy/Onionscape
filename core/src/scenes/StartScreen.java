package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;
import com.onionscape.game.SaveData;

import storage.Storage;

public class StartScreen implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private GameScreen gameScreen; 
	private SpriteBatch mapBatch = new SpriteBatch();
	private Texture mapTexture;
	private TextButton newGame, loadGame, settings, quit;
	private SaveData saveData = new SaveData();
	
	public StartScreen(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		mapTexture = Storage.assetManager.get("maps/StartScreen.png", Texture.class);
		mapTexture.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
		
		createComponents();
	}

	private void createComponents() {		
		newGame = new TextButton("New Game", storage.buttonStyle);
		newGame.setColor(Color.LIGHT_GRAY);
		newGame.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.setCurrentState(GameScreen.HOME);
    	    }});
		newGame.setSize(350, 100);
		newGame.setPosition(vp.getWorldWidth() / 1.5f, vp.getWorldHeight() / 3f);
		stage.addActor(newGame);	
		
		loadGame = new TextButton("Load Game", storage.buttonStyle);
		loadGame.setColor(Color.LIGHT_GRAY);
		loadGame.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			saveData.loadGame();
    			gameScreen.setCurrentState(GameScreen.HOME);
    	    }});
		loadGame.setSize(350, 100);
		loadGame.setPosition(vp.getWorldWidth() / 1.5f, vp.getWorldHeight() / 4.5f);
		stage.addActor(loadGame);	
		
		settings = new TextButton("Settings", storage.buttonStyle);
		settings.setColor(Color.LIGHT_GRAY);
		settings.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			
    	    }});
		settings.setSize(150, 100);
		settings.setPosition(vp.getWorldWidth() / 1.5f, vp.getWorldHeight() / 9f);
		stage.addActor(settings);	
		
		quit = new TextButton("Quit", storage.buttonStyle);
		quit.setColor(Color.LIGHT_GRAY);
		quit.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Gdx.app.exit();
    			System.exit(0);
    	    }});
		quit.setSize(150, 100);
		quit.setPosition(vp.getWorldWidth() / 1.297f, vp.getWorldHeight() / 9f);
		stage.addActor(quit);	
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		mapBatch.setProjectionMatrix(vp.getCamera().combined);
		
		mapBatch.begin();
		mapBatch.draw(mapTexture, 0, 0, 1920, 1080);
		mapBatch.end();		
		
		stage.act();
		stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
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
