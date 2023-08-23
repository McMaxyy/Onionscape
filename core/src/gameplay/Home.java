package gameplay;

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
import player.BerserkerSkillTree;
import player.Storage;

public class Home implements Screen {
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private TextButton fight, newGame, levelUp;
	private TextButton twoHandBtn, oneHandBtn, thickSkinBtn;
	private Label twoHandLbl, oneHandLbl, thickSkinLbl;
	private Label level, exp;
	private GameScreen gameScreen;
	private boolean skillTreeVisible = false;
	
	public Home(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
		storage.createFont();
		
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
		
		level = new Label("Level: " + Player.getLevel(), storage.labelStyle);
		level.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.1f);
		stage.addActor(level);
		
		exp = new Label("Exp: " + Player.getExp(), storage.labelStyle);
		exp.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.15f);
		stage.addActor(exp);
		
		// Skill Tree buttons
		levelUp = new TextButton("Skill Tree", storage.buttonStyle);
		levelUp.setColor(Color.LIGHT_GRAY);
		levelUp.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(!skillTreeVisible)
    				showSkillTree();
    			else
    				hideSkillTree();
    	    }});
		levelUp.setSize(250, 100);
		levelUp.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 5f);
		stage.addActor(levelUp);
		
		twoHandBtn = new TextButton("+", storage.buttonStyle);
		twoHandBtn.setColor(Color.WHITE);
		twoHandBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.setTwoHandStr(Player.getTwoHandStr() + 1);
    			BerserkerSkillTree.setTwoHMastery(BerserkerSkillTree.getTwoHMastery() + 1);
    			Player.skillPointUse();
    			twoHandLbl.setText("2H Mastery (" + BerserkerSkillTree.getTwoHMastery() + "/5)");
    	    }});
		twoHandBtn.setSize(50, 50);
		twoHandBtn.setPosition(vp.getWorldWidth() / 20f, vp.getWorldHeight() / 10f);
		
		oneHandBtn = new TextButton("+", storage.buttonStyle);
		oneHandBtn.setColor(Color.WHITE);
		oneHandBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.setOneHandStr(Player.getOneHandStr() + 1);
    			BerserkerSkillTree.setOneHMastery(BerserkerSkillTree.getOneHMastery() + 1);
    			Player.skillPointUse();
    			oneHandLbl.setText("2H Mastery (" + BerserkerSkillTree.getOneHMastery() + "/5)");
    	    }});
		oneHandBtn.setSize(50, 50);
		oneHandBtn.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
		
		thickSkinBtn = new TextButton("+", storage.buttonStyle);
		thickSkinBtn.setColor(Color.WHITE);
		thickSkinBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.setDmgResist(Player.getDmgResist() + 1);
    			BerserkerSkillTree.setThickSkin(BerserkerSkillTree.getThickSkin() + 1);
    			Player.skillPointUse();
    			thickSkinLbl.setText("Thick Skin (" + BerserkerSkillTree.getThickSkin() + "/5)");
    	    }});
		thickSkinBtn.setSize(50, 50);
		thickSkinBtn.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 10f);
		
		// Skill Tree labels
		twoHandLbl = new Label("2H Mastery (" + BerserkerSkillTree.getTwoHMastery() + "/5)", storage.labelStyle);
		twoHandLbl.setPosition(vp.getWorldWidth() / 50f, vp.getWorldHeight() / 6.5f);
		
		oneHandLbl = new Label("1H Mastery (" + BerserkerSkillTree.getOneHMastery() + "/5)", storage.labelStyle);
		oneHandLbl.setPosition(vp.getWorldWidth() / 6f, vp.getWorldHeight() / 6.5f);
		
		thickSkinLbl = new Label("Thick Skin (" + BerserkerSkillTree.getThickSkin() + "/5)", storage.labelStyle);
		thickSkinLbl.setPosition(vp.getWorldWidth() / 3.2f, vp.getWorldHeight() / 6.5f);
	}
	
	private void showSkillTree() {
		stage.addActor(twoHandBtn);
		stage.addActor(twoHandLbl);
		stage.addActor(oneHandBtn);
		stage.addActor(oneHandLbl);
		stage.addActor(thickSkinBtn);
		stage.addActor(thickSkinLbl);
		skillTreeVisible = true;
	}
	
	private void hideSkillTree() {
		twoHandBtn.remove();
		twoHandLbl.remove();
		oneHandBtn.remove();
		oneHandLbl.remove();
		thickSkinBtn.remove();
		thickSkinLbl.remove();
		skillTreeVisible = false;
	}
	
	private void lockUpgradeButtons() {
		twoHandBtn.remove();
		oneHandBtn.remove();
		thickSkinBtn.remove();
	}
	
	public void update() {
		if(Player.getSkillPoints() <= 0)
			lockUpgradeButtons();
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
		update();
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
