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
import com.onionscape.game.SaveData;

import player.Player;
import storage.Storage;

public class Home implements Screen {
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private TextButton fight, newGame, zerkerTreeBtn, inventory, weaponsBtn, armorBtn, itemsBtn, 
	saveBtn, loadBtn, forestBtn, merchantBtn;
	private Label level, exp, coins;
	private GameScreen gameScreen; 
	private Inventory inv;
	private SaveData saveData = new SaveData();
	public static boolean apBoost, dpBoost, hpBoost, expBoost, freshLoad = true, story;
	public static int stageLvl = 0;
	
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
		
		if(!story && stageLvl != 0) {
			Inventory.characterTable.clear();
			saveData.loadGame();
		}
		
		if(hpBoost)
			Player.setMaxHP(Player.getMaxHP() - 10);
		if(dpBoost)
			Player.setDmgResist(Player.getDmgResist() - 5);
		if(apBoost)
			Player.setWeaponDmg(Player.getWeaponDmg() - 5);
			
		apBoost = dpBoost = hpBoost = expBoost = false;
		
		createComponents();	
		stageLvl = 0;
		
		if(storage.getEquippedWeapons().size() > 0) {
			if(storage.getEquippedWeapons().get(0).toString().endsWith("Axe"))
				Player.weaponState = 1;
			else if(storage.getEquippedWeapons().get(0).toString().endsWith("Greataxe"))
				Player.weaponState = 2;
		}
		else
			Player.weaponState = 0;
	}
	
	private void createComponents() {
		level = new Label("Level: " + Player.getLevel(), storage.labelStyle);
		level.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.1f);
		stage.addActor(level);
		
		exp = new Label("Exp: " + Player.getExp(), storage.labelStyle);
		exp.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.15f);
		stage.addActor(exp);
		
		coins = new Label("Coins: " + Player.getCoins(), storage.labelStyle);
		coins.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.2f);
		stage.addActor(coins);
		
		fight = new TextButton("Fight", storage.buttonStyle);
		fight.setColor(Color.LIGHT_GRAY);
		fight.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			saveData.saveGame();
    			story = false;
    			storage.equippedArmor(null, "Clear");
    			storage.equippedWeapons(null, "Clear");
    			storage.equippedItems(null, "Clear");   			
    			Player.weaponState = 1;
    			storage.equippedArmor(storage.healthyIronHelmet, "Add");
    			storage.equippedArmor(storage.healthyIronChest, "Add");
    			storage.equippedArmor(storage.healthyIronBoots, "Add");
    			storage.equippedWeapons(storage.healthyIronAxe, "Add");
    			storage.equippedWeapons(storage.healthyIronShield, "Add");
    			stageLvl = 1;
    			if(freshLoad) {
    				gameScreen.setCurrentState(GameScreen.INVENTORY);
    				freshLoad = false;
    			}
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
//    			Player.gainDR(storage.ironHelmet.getDefense() + storage.ironChest.getDefense() +
//    					storage.ironBoots.getDefense() + storage.healthyIronShield.getWeaponDmg());
//    			Player.gainMaxHP(storage.healthyIronHelmet.getBonusStat() + storage.healthyIronChest.getBonusStat() +
//    					storage.healthyIronBoots.getBonusStat() + storage.healthyIronAxe.getBonusStat() +
//    					storage.healthyIronShield.getBonusStat());
//    			Player.gainWeaponDmg(storage.healthyIronAxe.getWeaponDmg());
    			stageLvl = 1;
    			GameScreen.newGame = true;
    			if(freshLoad) {
    				gameScreen.setCurrentState(GameScreen.INVENTORY);
    				freshLoad = false;
    			}
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
		
		forestBtn = new TextButton("Forest", storage.buttonStyle);
		forestBtn.setColor(Color.LIGHT_GRAY);
		forestBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			GameScreen.newGame = true;
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
    			gameScreen.setCurrentState(GameScreen.FOREST_MAP);
    	    }});
		forestBtn.setSize(150, 100);
		forestBtn.setPosition(vp.getWorldWidth() / 1.2f, vp.getWorldHeight() / 2f);
		stage.addActor(forestBtn);
		
		merchantBtn = new TextButton("Merchant", storage.buttonStyle);
		merchantBtn.setColor(Color.LIGHT_GRAY);
		merchantBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(freshLoad) {
    				gameScreen.setCurrentState(GameScreen.INVENTORY);
    				freshLoad = false;
    			}
    			gameScreen.setCurrentState(GameScreen.MERCHANT);
    	    }});
		merchantBtn.setSize(150, 100);
		merchantBtn.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 5f);
		stage.addActor(merchantBtn);
		
		weaponsBtn = new TextButton("Weapons", storage.buttonStyle);
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
    			storage.inventoryWeapons(storage.healthySteelAxe, "Add");
    			storage.inventoryWeapons(storage.woodenShield, "Add");
    			storage.inventoryWeapons(storage.woodenAxe, "Add");
    			storage.inventoryWeapons(storage.healthyIronShield, "Add");
    			storage.inventoryWeapons(storage.healthyBronzeShield, "Add");
    			storage.inventoryWeapons(storage.healthySteelShield, "Add");
    	    }});
		weaponsBtn.setSize(150, 100);
		weaponsBtn.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 3.35f);
		stage.addActor(weaponsBtn);
		
		armorBtn = new TextButton("Armor", storage.buttonStyle);
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
    			storage.inventoryItems(storage.itemSwing, "Add");
    			storage.inventoryItems(storage.apBoost, "Add");
    			storage.inventoryItems(storage.dpBoost, "Add");
    			storage.inventoryItems(storage.hpBoost, "Add");
    			storage.inventoryItems(storage.expBoost, "Add");
    	    }});
		itemsBtn.setSize(150, 100);
		itemsBtn.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 10f);
		stage.addActor(itemsBtn);
		
		saveBtn = new TextButton("Save", storage.buttonStyle);
		saveBtn.setColor(Color.LIGHT_GRAY);
		saveBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			saveData.saveGame();
    	    }});
		saveBtn.setSize(150, 100);
		saveBtn.setPosition(vp.getWorldWidth() / 1.2f, vp.getWorldHeight() / 1.2f);
		stage.addActor(saveBtn);
		
		loadBtn = new TextButton("Exit", storage.buttonStyle);
		loadBtn.setColor(Color.LIGHT_GRAY);
		loadBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Gdx.app.exit();
    			System.exit(0);
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
