package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;
import com.onionscape.game.MusicManager;
import com.onionscape.game.TextureManager;

import player.Player;
import storage.Storage;

public class LoadingScreen implements Screen {
	Viewport vp;
	Skin skin;
	private Game game;
    public Stage stage;
    private ProgressBar progressBar;
    private GameScreen gameScreen;
    private Storage storage;
    private Label loading;
    private ImageButton logo;
    
    public LoadingScreen(Viewport viewport, Game game, GameScreen gameScreen) {
        this.gameScreen = gameScreen;
        this.game = game;
        stage = new Stage(viewport);
        vp = viewport;
        Gdx.input.setInputProcessor(stage);
        storage = Storage.getInstance();
        storage.createFont();
        skin = storage.skin;
        
        logo = new ImageButton(skin, "logo");
        logo.setBounds(vp.getWorldWidth() / 5.8f, vp.getWorldHeight() / 3f, 1200, 500);
        stage.addActor(logo);

        // Create a progress bar using the default skin
        float progressBarWidth = vp.getWorldWidth() / 2f;
        float progressBarHeight = 50f;
        progressBar = new ProgressBar(0, 1, 0.01f, false, skin, "default-horizontal");

        // Calculate the position for the progress bar at the center of the screen
        float progressBarX = (vp.getWorldWidth() - progressBarWidth) / 2f;
        float progressBarY = (vp.getWorldHeight() - progressBarHeight) / 4f;
        progressBar.setBounds(progressBarX, progressBarY, progressBarWidth, progressBarHeight);

        // Create the loading label
        loading = new Label("Loading...", storage.labelStyle);
        float loadingX = progressBarX + progressBarWidth / 2.5f;
        float loadingY = progressBarY - 70f;
        loading.setPosition(loadingX, loadingY);

        stage.addActor(progressBar);
        stage.addActor(loading);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(10 / 255f, 10 / 255f, 10 / 255f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if (Storage.assetManager.update()) {
            // All assets are loaded
        	MusicManager.getInstance().initialize();
        	TextureManager.getInstance().smoothFilter();
        	gameScreen.setCurrentState(GameScreen.QUEST_LOG);
            gameScreen.setCurrentState(GameScreen.START_SCREEN);
        } else {
            progressBar.setValue(Storage.assetManager.getProgress());
            loading.setText("Loading..." + (int) (progressBar.getValue() * 100) + "%");
        }

        stage.act();
        stage.draw();
    }

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resize(int width, int height) {
		
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
