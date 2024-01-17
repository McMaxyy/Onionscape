package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;
import com.onionscape.game.MusicManager;
import com.onionscape.game.SaveData;

import player.Player;
import storage.Storage;

public class Home implements Screen {
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private TextButton slots, newGame, zerkerTreeBtn, inventory, weaponsBtn, armorBtn, itemsBtn, 
	saveBtn, exitBtn, forestBtn, merchantBtn, playBtn, settingsBtn, nextSong;
	private Label level, playerStats, coins, currentMusic;
	private GameScreen gameScreen; 
	private Inventory inv;
	private SaveData saveData = new SaveData();
	public static boolean apBoost, dpBoost, hpBoost, expBoost, freshLoad = true, story;
	public static int stageLvl = 0;
	private SpriteBatch mapBatch = new SpriteBatch();
	private Texture mapTexture;
	public static boolean newHome = true;
	
	public Home(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		storage.createFont();	
		gameScreen.forestMap = null;
		Merchant.raid = false;
		newHome = false;
		
		mapTexture = Storage.assetManager.get("maps/HomeScreen.png", Texture.class);
		mapTexture.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
		
		if(!story && stageLvl != 0) {
			Inventory.characterTable.clear();
			saveData.loadGame();
		}				
		
		removeBonusStats();
		
		if(hpBoost)
			Player.loseMaxHP(10);
		if(dpBoost)
			Player.loseDR(5);
		if(apBoost)
			Player.loseBonusStr(5);
			
		apBoost = dpBoost = hpBoost = expBoost = false;
				
		stageLvl = 0;
		
		if(storage.getEquippedWeapons().size() > 0) {
			if(storage.getEquippedWeapons().get(0).toString().endsWith("Axe"))
				Player.weaponState = 1;
			else if(storage.getEquippedWeapons().get(0).toString().endsWith("Greataxe"))
				Player.weaponState = 2;
		}
		else
			Player.weaponState = 0;
				
		createComponents();	
	}
	
	private void removeBonusStats() {
		Player.loseMaxHP(Player.getExtraHP());
		Player.loseBonusStr(Player.getExtraAP());
		Player.loseDR(Player.getExtraDP());
		
		Player.setExtraAP(0);
		Player.setExtraDP(0);
		Player.setExtraHP(0);
	}
	
	private void createComponents() {
		level = new Label("Level: " + Player.getLevel(), storage.labelStyle);
		level.setPosition(vp.getWorldWidth() / 1.5f, vp.getWorldHeight() / 1.45f);
		level.setText("Level: " + Player.getLevel() + "\n\nExp: " + Player.getExp());
		stage.addActor(level);
		
		playerStats = new Label("", storage.labelStyle);
		playerStats.setPosition(vp.getWorldWidth() / 1.5f, vp.getWorldHeight() / 2f);
		if(Player.weaponState == 0)
			playerStats.setText("Attack: " + (Player.getStrength() + Player.getWeaponDmg()) + 
					"\n\nDefense: " + (Player.getDmgResist() + Player.getSkillDmgResist()) + "\n\nHealth: " + (Player.getMaxHP() + Player.getSkillMaxHP()));
		else if(Player.weaponState == 1)
			playerStats.setText("Attack: " + (Player.getStrength() + Player.getWeaponDmg() + Player.getOneHandStr() + Player.getSkillWeaponDmg()) + 
					"\n\nDefense: " + (Player.getDmgResist() + Player.getSkillDmgResist()) + "\n\nHealth: " + (Player.getMaxHP() + Player.getSkillMaxHP()));
		else if(Player.weaponState == 2)
			playerStats.setText("Attack: " + (Player.getStrength() + Player.getWeaponDmg() + Player.getTwoHandStr() + Player.getSkillWeaponDmg()) + 
					"\n\nDefense: " + (Player.getDmgResist() + Player.getSkillDmgResist()) + "\n\nHealth: " + (Player.getMaxHP() + Player.getSkillMaxHP()));
		stage.addActor(playerStats);
		
		coins = new Label("Coins: " + Player.getCoins(), storage.labelStyle);
		coins.setPosition(vp.getWorldWidth() / 1.5f, vp.getWorldHeight() / 3.5f);
		stage.addActor(coins);
		
		nextSong = new TextButton("Next song", storage.homeBtnStyle);
		nextSong.setColor(Color.LIGHT_GRAY);
		nextSong.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			MusicManager.getInstance().playBackgroundMusic();
    			currentMusic.remove();
    			currentMusic = new Label(MusicManager.getInstance().getSongName(), storage.labelStyle);
    			currentMusic.setPosition(nextSong.getX() - currentMusic.getWidth() - 50, nextSong.getY() + nextSong.getHeight() / 4f);
    			stage.addActor(currentMusic);
    	    }});
		nextSong.setSize(200, 50);
		nextSong.setPosition(vp.getWorldWidth() / 1.2f, vp.getWorldHeight() / 32f);
		stage.addActor(nextSong);
		
		currentMusic = new Label(MusicManager.getInstance().getSongName(), storage.labelStyle);
		currentMusic.setPosition(nextSong.getX() - currentMusic.getWidth() - 50, nextSong.getY() + nextSong.getHeight() / 4f);
		stage.addActor(currentMusic);
		
		slots = new TextButton("Slots", storage.homeBtnStyle);
		slots.setColor(Color.LIGHT_GRAY);
		slots.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.switchToNewState(GameScreen.SLOT_GAME);
    	    }});
		slots.setSize(200, 50);
		slots.setPosition(vp.getWorldWidth() / 5.5f, vp.getWorldHeight() / 1.08f);
		stage.addActor(slots);
		
		playBtn = new TextButton("Play", storage.homeBtnStyle);
		playBtn.setColor(Color.LIGHT_GRAY);
		playBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(playBtn.getText().toString().equals("Play")) {
    				newGame.setVisible(true);
        			forestBtn.setVisible(true);
        			slots.setVisible(false);
        			playBtn.setText("Back");
    			}
    			else {
    				newGame.setVisible(false);
	    			forestBtn.setVisible(false);
	    			slots.setVisible(true);
	    			playBtn.setText("Play");
    			}
    	    }});
		playBtn.setSize(200, 50);
		playBtn.setPosition(vp.getWorldWidth() / 20f, vp.getWorldHeight() / 1.08f);
		stage.addActor(playBtn);
		
		newGame = new TextButton("New Game", storage.homeBtnStyle);
		newGame.setColor(Color.LIGHT_GRAY);
		newGame.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			MusicManager.getInstance().playFightMusic();
    			saveData.saveGame();
    			story = false;   	    			
    			storage.equippedArmor(null, "Clear");
    			storage.equippedWeapons(null, "Clear");
    			storage.equippedItems(null, "Clear");
    			storage.equippedArmor(storage.healthyIronHelmet, "Add");
    			storage.equippedArmor(storage.healthyIronChest, "Add");
    			storage.equippedArmor(storage.healthyIronBoots, "Add");
    			storage.equippedWeapons(storage.healthyIronAxe, "Add");
    			storage.equippedWeapons(storage.healthyIronShield, "Add"); 
    			if (inv == null)
    				inv = new Inventory(vp, game, gameScreen);
    			inv.createCharacterGrid();
    			Player.weaponState = 1;   			
    			Player.setStrength(3);
    			Player.setOneHandStr(0);
    			Player.setTwoHandStr(0);
    			Player.setMaxHP(82);
    			Player.setDmgResist(17);
    			Player.setWeaponDmg(2);
    			stageLvl = 1;
    			resetSkills();
    			GameScreen.newGame = true;
    			if(freshLoad) {
    				gameScreen.setCurrentState(GameScreen.INVENTORY);
    				freshLoad = false;
    			}    			
    			gameScreen.switchToNewState(GameScreen.FOREST_MAP_QUICK);
    	    }});
		newGame.setSize(200, 50);
		newGame.setPosition(vp.getWorldWidth() / 3.18f, vp.getWorldHeight() / 1.08f);
		newGame.setVisible(false);
		stage.addActor(newGame);
		
		zerkerTreeBtn = new TextButton("Skill Tree", storage.homeBtnStyle);
		zerkerTreeBtn.setColor(Color.LIGHT_GRAY);
		zerkerTreeBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.switchToNewState(GameScreen.ZERKER_TREE);
    	    }});
		zerkerTreeBtn.setSize(200, 50);
		zerkerTreeBtn.setPosition(vp.getWorldWidth() / 20f, vp.getWorldHeight() / 32f);
		stage.addActor(zerkerTreeBtn);			
		
		inventory = new TextButton("Bag", storage.homeBtnStyle);
		inventory.setColor(Color.LIGHT_GRAY);
		inventory.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.switchToNewState(GameScreen.INVENTORY);
    	    }});
		inventory.setSize(200, 50);
		inventory.setPosition(vp.getWorldWidth() / 5.5f, vp.getWorldHeight() / 32f);
		stage.addActor(inventory);
		
		forestBtn = new TextButton("Forest", storage.homeBtnStyle);
		forestBtn.setColor(Color.LIGHT_GRAY);
		forestBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			GameScreen.newGame = true;
    			ForestMap.newRaid = true;
    			story = true;
    			stageLvl = 1;
    			if(freshLoad || Inventory.characterTable.getChildren().size == 0) {
    				if(Inventory.helmetDP != 0 || Inventory.chestDP != 0 || Inventory.bootsDP != 0 || 
    						Inventory.shieldDP != 0 || Inventory.weaponAP != 0)	
    					Inventory.haveGear = false;
    				else
    					Inventory.haveGear = true;
    				gameScreen.setCurrentState(GameScreen.INVENTORY);
    				freshLoad = false;
    			}
    			gameScreen.switchToNewState(GameScreen.FOREST_MAP);
    	    }});
		forestBtn.setSize(200, 50);
		forestBtn.setPosition(vp.getWorldWidth() / 5.5f, vp.getWorldHeight() / 1.08f);
		forestBtn.setVisible(false);
		stage.addActor(forestBtn);
		
		merchantBtn = new TextButton("Merchant", storage.homeBtnStyle);
		merchantBtn.setColor(Color.LIGHT_GRAY);
		merchantBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(freshLoad) {
    				gameScreen.switchToNewState(GameScreen.INVENTORY);
    				freshLoad = false;
    			}
    			gameScreen.switchToNewState(GameScreen.MERCHANT);
    	    }});
		merchantBtn.setSize(200, 50);
		merchantBtn.setPosition(vp.getWorldWidth() / 3.18f, vp.getWorldHeight() / 32f);
		stage.addActor(merchantBtn);
		
		weaponsBtn = new TextButton("Weapons", storage.homeBtnStyle);
		weaponsBtn.setColor(Color.LIGHT_GRAY);
		weaponsBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			storage.inventoryWeapons(storage.healthyIronGA, "Add");
    			storage.inventoryWeapons(storage.woodenGA, "Add");
    			storage.inventoryWeapons(storage.healthyBronzeGA, "Add");
    			storage.inventoryWeapons(storage.defensiveSteelGA, "Add");
    			storage.inventoryWeapons(storage.healthyIronAxe, "Add");
    			storage.inventoryWeapons(storage.healthyBronzeAxe, "Add");
    			storage.inventoryWeapons(storage.strongSteelAxe, "Add");
    			storage.inventoryWeapons(storage.woodenShield, "Add");
    			storage.inventoryWeapons(storage.woodenAxe, "Add");
    			storage.inventoryWeapons(storage.healthyIronShield, "Add");
    			storage.inventoryWeapons(storage.healthyBronzeShield, "Add");
    			storage.inventoryWeapons(storage.healthySteelShield, "Add");
    	    }});
		weaponsBtn.setSize(200, 50);
		weaponsBtn.setPosition(vp.getWorldWidth() / 25f, vp.getWorldHeight() / 1.5f);
		stage.addActor(weaponsBtn);
		
		armorBtn = new TextButton("Armor", storage.homeBtnStyle);
		armorBtn.setColor(Color.LIGHT_GRAY);
		armorBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			storage.inventoryArmor(storage.healthyIronHelmet, "Add");
    			storage.inventoryArmor(storage.healthyIronChest, "Add");
    			storage.inventoryArmor(storage.healthyIronBoots, "Add");
    			storage.inventoryArmor(storage.healthyBronzeHelmet, "Add");
    			storage.inventoryArmor(storage.healthyBronzeChest, "Add");
    			storage.inventoryArmor(storage.healthyBronzeBoots, "Add");
    			storage.inventoryArmor(storage.healthySteelHelmet, "Add");
    			storage.inventoryArmor(storage.healthySteelChest, "Add");
    			storage.inventoryArmor(storage.healthySteelBoots, "Add");
    	    }});
		armorBtn.setSize(200, 50);
		armorBtn.setPosition(vp.getWorldWidth() / 25f, vp.getWorldHeight() / 1.67f);
		stage.addActor(armorBtn);
		
		itemsBtn = new TextButton("Items", storage.homeBtnStyle);
		itemsBtn.setColor(Color.LIGHT_GRAY);
		itemsBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			storage.inventoryItems(storage.healthPot, "Add");
    			storage.inventoryItems(storage.bomb, "Add");
    			storage.inventoryItems(storage.itemSwing, "Add");
    			storage.inventoryItems(storage.apBoost, "Add");
    			storage.inventoryItems(storage.dpBoost, "Add");
    			storage.inventoryItems(storage.hpBoost, "Add");
    			storage.inventoryItems(storage.expBoost, "Add");
    	    }});
		itemsBtn.setSize(200, 50);
		itemsBtn.setPosition(vp.getWorldWidth() / 25f, vp.getWorldHeight() / 1.9f);
		stage.addActor(itemsBtn);
		
		settingsBtn = new TextButton("SE", storage.homeBtnStyle);
		settingsBtn.setColor(Color.LIGHT_GRAY);
		settingsBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.switchToNewState(GameScreen.SETTINGS);
    	    }});
		settingsBtn.setSize(50, 50);
		settingsBtn.setPosition(vp.getWorldWidth() / 1.21f, vp.getWorldHeight() / 1.08f);
		stage.addActor(settingsBtn);
		
		saveBtn = new TextButton("S", storage.homeBtnStyle);
		saveBtn.setColor(Color.LIGHT_GRAY);
		saveBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			saveData.saveGame();
    	    }});
		saveBtn.setSize(50, 50);
		saveBtn.setPosition(vp.getWorldWidth() / 1.14f, vp.getWorldHeight() / 1.08f);
		stage.addActor(saveBtn);
		
		exitBtn = new TextButton("X", storage.homeBtnStyle);
		exitBtn.setColor(Color.LIGHT_GRAY);
		exitBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Gdx.app.exit();
    			System.exit(0);
    	    }});
		exitBtn.setSize(50, 50);
		exitBtn.setPosition(vp.getWorldWidth() / 1.08f, vp.getWorldHeight() / 1.08f);
		stage.addActor(exitBtn);
	}
	
	private void resetSkills() {
		BerserkerSkillTree.twoHMastery = 0;
		BerserkerSkillTree.oneHMastery = 0;
		BerserkerSkillTree.thickSkin = 0;
		BerserkerSkillTree.weaponMastery = 0;
		BerserkerSkillTree.blockAura = 0;
		BerserkerSkillTree.eleResist = 0;
		BerserkerSkillTree.rendMastery = 0;
		BerserkerSkillTree.lifeSteal = 0;
		BerserkerSkillTree.poisonRend = 0;
		BerserkerSkillTree.ironSkin = 0;
		BerserkerSkillTree.bulkUp = 0;
		BerserkerSkillTree.sharpenWeapons = 0;
		BerserkerSkillTree.luckyStrike = 0;
		BerserkerSkillTree.blockEfficiency = 0;
		BerserkerSkillTree.bludgeonEnemy = 0;
		BerserkerSkillTree.doubleSwing = 0;
		BerserkerSkillTree.thorns = 0;
		BerserkerSkillTree.healthy = 0;		
		BerserkerSkillTree.twoHandStr = 0;
		BerserkerSkillTree.oneHandStr = 0;
		BerserkerSkillTree.dmgResist = 0;
		BerserkerSkillTree.weaponDmg = 0;
		BerserkerSkillTree.strength = 0;
		BerserkerSkillTree.maxHP = 0;
		Player.setSkillDmgResist(0);
		Player.setSkillWeaponDmg(0);
		Player.setSkillMaxHP(0);
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
		mapBatch.setProjectionMatrix(vp.getCamera().combined);
		
		mapBatch.begin();
		mapBatch.draw(mapTexture, 0, 0, GameScreen.SELECTED_WIDTH, GameScreen.SELECTED_HEIGHT);
		mapBatch.end();
		
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
}
