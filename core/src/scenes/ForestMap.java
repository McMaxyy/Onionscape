package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import storage.Storage;

public class ForestMap implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private GameScreen gameScreen; 
	private SpriteBatch mapBatch = new SpriteBatch();
	private SpriteBatch onionBatch = new SpriteBatch();
	private Texture mapTexture;
	private TextButton bottom1, bottom2, bottom3, bottom4, bottom5, bottom6, bottom7, bottom8,
	bottom9, bottom10, bottom11, bottom12, bottom13, bottom14;
	private TextButton[] buttons = new TextButton[35];
	
	public ForestMap(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		mapTexture = Storage.assetManager.get("maps/ForestMap.png", Texture.class);
		mapTexture.setFilter(TextureFilter.Linear,TextureFilter.Nearest);
		
		createComponents();
	}
	
	private void createComponents() {
		bottom1 = new TextButton("1", storage.buttonStyle);
		bottom1.setColor(Color.LIGHT_GRAY);
		bottom1.setBounds(vp.getWorldWidth() / 1.865f, vp.getWorldHeight() / 4f, 80, 80);
		buttons[0] = bottom1;
		
		bottom2 = new TextButton("2", storage.buttonStyle);
		bottom2.setColor(Color.LIGHT_GRAY);
		bottom2.setBounds(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 13.4f, 80, 80);
		buttons[1] = bottom2;
		
		bottom3 = new TextButton("3", storage.buttonStyle);
		bottom3.setColor(Color.LIGHT_GRAY);
		bottom3.setBounds(vp.getWorldWidth() / 5.2f, vp.getWorldHeight() / 13.4f, 80, 80);
		buttons[2] = bottom3;
		
		bottom4 = new TextButton("4", storage.buttonStyle);
		bottom4.setColor(Color.LIGHT_GRAY);
		bottom4.setBounds(vp.getWorldWidth() / 100f, vp.getWorldHeight() / 13.4f, 80, 80);
		buttons[3] = bottom4;
		
		bottom5 = new TextButton("5", storage.buttonStyle);
		bottom5.setColor(Color.LIGHT_GRAY);
		bottom5.setBounds(vp.getWorldWidth() / 6.78f, vp.getWorldHeight() / 4f, 80, 80);
		buttons[4] = bottom5;
		
		bottom6 = new TextButton("6", storage.buttonStyle);
		bottom6.setColor(Color.LIGHT_GRAY);
		bottom6.setBounds(vp.getWorldWidth() / 13f, vp.getWorldHeight() / 2.55f, 80, 80);
		buttons[5] = bottom6;
		
		bottom7 = new TextButton("7", storage.buttonStyle);
		bottom7.setColor(Color.LIGHT_GRAY);
		bottom7.setBounds(vp.getWorldWidth() / 100f, vp.getWorldHeight() / 2.55f, 80, 80);
		buttons[6] = bottom7;
		
		bottom8 = new TextButton("8", storage.buttonStyle);
		bottom8.setColor(Color.LIGHT_GRAY);
		bottom8.setBounds(vp.getWorldWidth() / 4.25f, vp.getWorldHeight() / 4f, 80, 80);
		buttons[7] = bottom8;
		
		bottom9 = new TextButton("9", storage.buttonStyle);
		bottom9.setColor(Color.LIGHT_GRAY);
		bottom9.setBounds(vp.getWorldWidth() / 1.63f, vp.getWorldHeight() / 13.4f, 80, 80);
		buttons[8] = bottom9;
		
		bottom10 = new TextButton("10", storage.buttonStyle);
		bottom10.setColor(Color.LIGHT_GRAY);
		bottom10.setBounds(vp.getWorldWidth() / 1.455f, vp.getWorldHeight() / 6f, 80, 80);
		buttons[9] = bottom10;
		
		bottom11 = new TextButton("11", storage.buttonStyle);
		bottom11.setColor(Color.LIGHT_GRAY);
		bottom11.setBounds(vp.getWorldWidth() / 1.25f, vp.getWorldHeight() / 13.4f, 80, 80);
		buttons[10] = bottom11;
		
		bottom12 = new TextButton("12", storage.buttonStyle);
		bottom12.setColor(Color.LIGHT_GRAY);
		bottom12.setBounds(vp.getWorldWidth() / 1.05f, vp.getWorldHeight() / 13.4f, 80, 80);
		buttons[11] = bottom12;
		
		bottom13 = new TextButton("13", storage.buttonStyle);
		bottom13.setColor(Color.LIGHT_GRAY);
		bottom13.setBounds(vp.getWorldWidth() / 1.345f, vp.getWorldHeight() / 2.8f, 80, 80);
		buttons[12] = bottom13;
		
		bottom14 = new TextButton("14", storage.buttonStyle);
		bottom14.setColor(Color.LIGHT_GRAY);
		bottom14.setBounds(vp.getWorldWidth() / 1.075f, vp.getWorldHeight() / 2.8f, 80, 80);
		buttons[13] = bottom14;
		
		stage.addActor(bottom1);
		stage.addActor(bottom2);
		stage.addActor(bottom3);
		stage.addActor(bottom4);
		stage.addActor(bottom5);
		stage.addActor(bottom6);
		stage.addActor(bottom7);
		stage.addActor(bottom8);
		stage.addActor(bottom9);
		stage.addActor(bottom10);
		stage.addActor(bottom11);
		stage.addActor(bottom12);
		stage.addActor(bottom13);
		stage.addActor(bottom14);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		mapBatch.setProjectionMatrix(vp.getCamera().combined);
		onionBatch.setProjectionMatrix(vp.getCamera().combined);
		
		mapBatch.begin();
		mapBatch.draw(mapTexture, 0, 0, 1920, 1080);
		mapBatch.end();
		
		onionBatch.begin();
		onionBatch.draw(Inventory.onionTexture, vp.getWorldWidth() / 2.1f, vp.getWorldHeight() / 14f, 100, 160);
		onionBatch.end();
		
		stage.act();
		stage.draw();
		
		if(Gdx.input.isKeyPressed(Keys.F5)) {
			for(int i = 0; i < buttons.length; i++) {
				if(buttons[i] != null)
					buttons[i].remove();
			}
			createComponents();
		}
	}

	@Override
	public void resize(int width, int height) {
		mapBatch.setProjectionMatrix(vp.getCamera().combined);	
		onionBatch.setProjectionMatrix(vp.getCamera().combined);
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
