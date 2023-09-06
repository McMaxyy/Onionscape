package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;
import com.onionscape.game.SaveData;

import player.Player;
import player.Storage;

public class Home implements Screen {
	Skin skin;
	Viewport vp;
	Json json = new Json();
	String jsonString;
	public Stage stage;
	private Storage storage;
	private Game game;
	private TextButton fight, newGame, zerkerTreeBtn, inventory, weaponsBtn, armorBtn, itemsBtn, 
	saveBtn, loadBtn;
	private Label level, exp;
	private GameScreen gameScreen; 
	
	public Home(Viewport viewport, Game game, GameScreen gameScreen) {
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
	
	private void saveGame() {
		SaveData saveData = new SaveData();
		saveData.maxHP = Player.getMaxHP();
		saveData.strength = Player.getStrength();
		saveData.playerArmor = storage.getPlayerArmor();
		saveData.playerWeapons = storage.getPlayerWeapons();
		
		jsonString = json.toJson(saveData);		
		FileHandle file = Gdx.files.local("saveData.json");
		file.writeString(jsonString, false);
	}
	
	private void loadGame() {
		SaveData loadedData = null;
		FileHandle file = Gdx.files.local("saveData.json");
		if (file.exists()) {
		    String readJson = file.readString();
		    loadedData = json.fromJson(SaveData.class, readJson);
		}
		
		if (loadedData != null) {
		    Player.setMaxHP(loadedData.maxHP);
		    Player.setStrength(loadedData.strength);
		    storage.playerArmor = loadedData.playerArmor;
		    storage.playerWeapons = loadedData.playerWeapons;
		}
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
		
		inventory = new TextButton("Bag", storage.buttonStyle);
		inventory.setColor(Color.LIGHT_GRAY);
		inventory.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.setCurrentState(GameScreen.INVENTORY);
    	    }});
		inventory.setSize(150, 100);
		inventory.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 3f);
		stage.addActor(inventory);
		
		weaponsBtn = new TextButton("Weapons", storage.buttonStyle);
		weaponsBtn.setColor(Color.LIGHT_GRAY);
		weaponsBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			storage.inventoryWeapons(storage.ironGreataxe, "Add");
    			storage.inventoryWeapons(storage.ironAxe, "Add");
    			storage.inventoryWeapons(storage.woodenShield, "Add");
    	    }});
		weaponsBtn.setSize(150, 100);
		weaponsBtn.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 3f);
		stage.addActor(weaponsBtn);
		
		armorBtn = new TextButton("Armor", storage.buttonStyle);
		armorBtn.setColor(Color.LIGHT_GRAY);
		armorBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			storage.inventoryArmor(storage.ironHelmet, "Add");
    			storage.inventoryArmor(storage.ironChest, "Add");
    			storage.inventoryArmor(storage.ironBoots, "Add");
    	    }});
		armorBtn.setSize(150, 100);
		armorBtn.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 5f);
		stage.addActor(armorBtn);
		
		itemsBtn = new TextButton("Items", storage.buttonStyle);
		itemsBtn.setColor(Color.LIGHT_GRAY);
		itemsBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			storage.inventoryItems(storage.healthPot, "Add");
    			storage.inventoryItems(storage.bomb, "Add");
    	    }});
		itemsBtn.setSize(150, 100);
		itemsBtn.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 10f);
		stage.addActor(itemsBtn);
		
		saveBtn = new TextButton("Save", storage.buttonStyle);
		saveBtn.setColor(Color.LIGHT_GRAY);
		saveBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			saveGame();
    	    }});
		saveBtn.setSize(150, 100);
		saveBtn.setPosition(vp.getWorldWidth() / 1.2f, vp.getWorldHeight() / 1.2f);
		stage.addActor(saveBtn);
		
		loadBtn = new TextButton("Load", storage.buttonStyle);
		loadBtn.setColor(Color.LIGHT_GRAY);
		loadBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			loadGame();
    	    }});
		loadBtn.setSize(150, 100);
		loadBtn.setPosition(vp.getWorldWidth() / 1.2f, vp.getWorldHeight() / 1.4f);
		stage.addActor(loadBtn);
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
