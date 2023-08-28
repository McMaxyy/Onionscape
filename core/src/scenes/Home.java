package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;

import player.Player;
import player.Storage;

public class Home implements Screen {
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private TextButton fight, newGame, zerkerTreeBtn;
	private TextButton oneHand, twoHand;
	private Label level, exp;
	private GameScreen gameScreen;
	
	public Home(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
		storage.createFont();
		Player.weaponState = 0;
		
		createComponents();	
	}	
	
	private void createComponents() {
		fight = new TextButton("Fight", storage.buttonStyle);
		fight.setColor(Color.LIGHT_GRAY);
		fight.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.setCurrentState(GameScreen.FIGHT_SCENE);
    	    }});
		fight.setSize(150, 100);
		fight.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 2f);
		stage.addActor(fight);
		
		newGame = new TextButton("New Game", storage.buttonStyle);
		newGame.setColor(Color.LIGHT_GRAY);
		newGame.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			GameScreen.newGame = true;
    			gameScreen.setCurrentState(GameScreen.FIGHT_SCENE);
    	    }});
		newGame.setSize(150, 100);
		newGame.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 2f);
		stage.addActor(newGame);
		
		zerkerTreeBtn = new TextButton("Skill Tree", storage.buttonStyle);
		zerkerTreeBtn.setColor(Color.LIGHT_GRAY);
		zerkerTreeBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.setCurrentState(GameScreen.ZERKER_TREE);
    	    }});
		zerkerTreeBtn.setSize(150, 100);
		zerkerTreeBtn.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 2f);
		stage.addActor(zerkerTreeBtn);
		
		level = new Label("Level: " + Player.getLevel(), storage.labelStyle);
		level.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.1f);
		stage.addActor(level);
		
		exp = new Label("Exp: " + Player.getExp(), storage.labelStyle);
		exp.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.15f);
		stage.addActor(exp);	
		
		oneHand = new TextButton("OneHand", storage.buttonStyle);
		oneHand.setColor(Color.LIGHT_GRAY);
		oneHand.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.weaponState = 1;
    	    }});
		oneHand.setSize(150, 100);
		oneHand.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 10f);
		stage.addActor(oneHand);
		
		twoHand = new TextButton("TwoHand", storage.buttonStyle);
		twoHand.setColor(Color.LIGHT_GRAY);
		twoHand.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.weaponState = 2;
    	    }});
		twoHand.setSize(150, 100);
		twoHand.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 5f);
		stage.addActor(twoHand);
	}
	
	public void dispose() {
		stage.dispose();
		skin.dispose();
		storage.font.dispose();
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
}
