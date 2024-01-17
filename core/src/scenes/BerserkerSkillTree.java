package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;

import player.Player;
import storage.Storage;

public class BerserkerSkillTree implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private TextButton homeBtn, resetBtn;
	private TextButton twoHandBtn, oneHandBtn, thickSkinBtn, weaponMasteryBtn, blockAuraBtn,
	eleResistBtn, rendMasteryBtn, lifeStealBtn, poisonRendBtn, ironSkinBtn, bulkUpBtn, healthyBtn,
	sharpenWeaponsBtn, luckyStrikeBtn, blockEfficiencyBtn, bludgeonEnemyBtn, doubleSwingBtn, thornsBtn;
	private Label twoHandLbl, oneHandLbl, thickSkinLbl, weaponMasteryLbl, blockAuraLbl, 
	eleResistLbl, rendMasteryLbl, lifeStealLbl, poisonRendLbl, ironSkinLbl, bulkUpLbl,sharpenWeaponsLbl, 
	luckyStrikeLbl, blockEfficiencyLbl, bludgeonEnemyLbl, doubleSwingLbl, thornsLbl, healthyLbl;
	private Label level, skillPoints, skillDescription;
	private GameScreen gameScreen;
	private boolean pointUsed = false;
	public static int skillPointsUsed = 0;
	private SpriteBatch mapBatch = new SpriteBatch();
	private Texture mapTexture;

	public static int twoHMastery = 0;
	public static int oneHMastery = 0;
	public static int thickSkin = 0;
	public static int weaponMastery = 0;
	public static int blockAura = 0;
	public static int eleResist = 0;
	public static int rendMastery = 0;
	public static int lifeSteal = 0;
	public static int poisonRend = 0;
	public static int ironSkin = 0;
	public static int bulkUp = 0;
	public static int sharpenWeapons = 0;
	public static int luckyStrike = 0;
	public static int blockEfficiency = 0;
	public static int bludgeonEnemy = 0;
	public static int doubleSwing = 0;
	public static int thorns = 0;
	public static int healthy = 0;
	
	public static int twoHandStr = 0;
	public static int oneHandStr = 0;
	public static int dmgResist = 0;
	public static int weaponDmg = 0;
	public static int strength = 0;
	public static int maxHP = 0;
	
	public BerserkerSkillTree(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		storage.createFont();
		
		mapTexture = Storage.assetManager.get("maps/SkillTreeZerker.png", Texture.class);
		mapTexture.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
		
		createComponents();	
	}
	
	private void createComponents() {
		homeBtn = new TextButton("Home", storage.homeBtnStyle);
		homeBtn.setColor(Color.LIGHT_GRAY);
		homeBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.switchToNewState(GameScreen.HOME);
    	    }});
		homeBtn.setSize(150, 40);
		homeBtn.setPosition(vp.getWorldWidth() / 50f, vp.getWorldHeight() / 1.064f);
		stage.addActor(homeBtn);
		
		resetBtn = new TextButton("Reset", storage.homeBtnStyle);
		resetBtn.setColor(Color.LIGHT_GRAY);
		resetBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			resetTree();
    	    }});
		resetBtn.setSize(150, 40);
		resetBtn.setPosition(vp.getWorldWidth() / 8f, vp.getWorldHeight() / 1.064f);
		stage.addActor(resetBtn);
	
		level = new Label("Level: " + Player.getLevel(), storage.labelStyleBlack);
		level.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.15f);
		stage.addActor(level);
				
		skillDescription = new Label("", storage.labelStyle);
		skillDescription.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 1.045f);
		skillDescription.setVisible(false);
		stage.addActor(skillDescription);
		
		skillPoints = new Label("Skill points: " + Player.getSkillPoints(), storage.labelStyleBlack);
		skillPoints.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.2f);
		stage.addActor(skillPoints);
		
		// Skill Tree labels
		twoHandLbl = new Label("2H Mastery (" + twoHMastery + "/5)", storage.labelStyleBlack);
		twoHandLbl.setPosition(vp.getWorldWidth() / 4f, vp.getWorldHeight() / 1.15f);
		
		oneHandLbl = new Label("1H Mastery (" + oneHMastery + "/5)", storage.labelStyleBlack);
		oneHandLbl.setPosition(vp.getWorldWidth() / 2.3f, vp.getWorldHeight() / 1.15f);
		
		thickSkinLbl = new Label("Thick Skin (" + thickSkin + "/5)", storage.labelStyleBlack);
		thickSkinLbl.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 1.15f);
		
		weaponMasteryLbl = new Label("Weapon Mastery (" + weaponMastery + "/3)", storage.labelStyleBlack);
		weaponMasteryLbl.setPosition(vp.getWorldWidth() / 7f, vp.getWorldHeight() / 1.4f);
		
		blockAuraLbl = new Label("Block Aura (" + blockAura + "/1)", storage.labelStyleBlack);
		blockAuraLbl.setPosition(vp.getWorldWidth() / 2.9f, vp.getWorldHeight() / 1.4f);
		
		eleResistLbl = new Label("Elemental Resistance (" + eleResist + "/2)", storage.labelStyleBlack);
		eleResistLbl.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 1.4f);
		
		rendMasteryLbl = new Label("Rend Mastery (" + rendMastery + "/3)", storage.labelStyleBlack);
		rendMasteryLbl.setPosition(vp.getWorldWidth() / 1.35f, vp.getWorldHeight() / 1.4f);
		
		poisonRendLbl = new Label("Poison Rend (" + poisonRend + "/1)", storage.labelStyleBlack);
		poisonRendLbl.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.9f);
		
		lifeStealLbl = new Label("Life Steal (" + lifeSteal + "/1)", storage.labelStyleBlack);
		lifeStealLbl.setPosition(vp.getWorldWidth() / 3.35f, vp.getWorldHeight() / 1.9f);
		
		ironSkinLbl = new Label("Iron Skin (" + ironSkin + "/5)", storage.labelStyleBlack);
		ironSkinLbl.setPosition(vp.getWorldWidth() / 2.1f, vp.getWorldHeight() / 1.9f);
		
		bulkUpLbl = new Label("Bulk Up! (" + bulkUp + "/5)", storage.labelStyleBlack);
		bulkUpLbl.setPosition(vp.getWorldWidth() / 1.5f, vp.getWorldHeight() / 1.9f);
		
		healthyLbl = new Label("Healthy (" + healthy + "/5)", storage.labelStyleBlack);
		healthyLbl.setPosition(vp.getWorldWidth() / 1.2f, vp.getWorldHeight() / 1.9f);
		
		sharpenWeaponsLbl = new Label("Sharpen Weapons (" + sharpenWeapons + "/5)", storage.labelStyleBlack);
		sharpenWeaponsLbl.setPosition(vp.getWorldWidth() / 5f, vp.getWorldHeight() / 2.9f);
		
		luckyStrikeLbl = new Label("Lucky Strike (" + luckyStrike + "/1)", storage.labelStyleBlack);
		luckyStrikeLbl.setPosition(vp.getWorldWidth() / 2.3f, vp.getWorldHeight() / 2.9f);
		
		blockEfficiencyLbl = new Label("Block Efficiency (" + blockEfficiency + "/5)", storage.labelStyleBlack);
		blockEfficiencyLbl.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 2.9f);
		
		bludgeonEnemyLbl = new Label("Bludgeon Enemy (" + bludgeonEnemy + "/1)", storage.labelStyleBlack);
		bludgeonEnemyLbl.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 6.3f);
		
		doubleSwingLbl = new Label("Double Swing (" + doubleSwing + "/1)", storage.labelStyleBlack);
		doubleSwingLbl.setPosition(vp.getWorldWidth() / 2.3f, vp.getWorldHeight() / 6.3f);
		
		thornsLbl = new Label("Thorns (" + thorns + "/1)", storage.labelStyleBlack);
		thornsLbl.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 6.3f);
		
		// Skill Tree buttons		
		twoHandBtn = new TextButton("+", storage.buttonStyle);
		twoHandBtn.setColor(Color.WHITE);
		twoHandBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.skillTreeGains(0);
    			twoHMastery += 1;
    			Player.skillPointUse();
    			twoHandLbl.setText("2H Mastery (" + twoHMastery + "/5)");
    			pointUsed = true;
    			skillPointsUsed++;
    			twoHandStr++;
    	    }});
		twoHandBtn.setSize(50, 50);
		twoHandBtn.setPosition(twoHandLbl.getX() + twoHandLbl.getWidth() / 2.5f, twoHandLbl.getY() - 65f);
		descriptionListener(twoHandBtn, "Increase damage dealt with 2H weapons");
		
		oneHandBtn = new TextButton("+", storage.buttonStyle);
		oneHandBtn.setColor(Color.WHITE);
		oneHandBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.skillTreeGains(1);
    			oneHMastery += 1;
    			Player.skillPointUse();
    			oneHandLbl.setText("1H Mastery (" + oneHMastery + "/5)");
    			pointUsed = true;
    			skillPointsUsed++;
    			oneHandStr++;
    	    }});
		oneHandBtn.setSize(50, 50);
		oneHandBtn.setPosition(oneHandLbl.getX() + oneHandLbl.getWidth() / 2.5f, oneHandLbl.getY() - 65f);
		descriptionListener(oneHandBtn, "Increase damage dealt with 1H weapons");
		
		thickSkinBtn = new TextButton("+", storage.buttonStyle);
		thickSkinBtn.setColor(Color.WHITE);
		thickSkinBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.skillTreeGains(2);
    			thickSkin += 1;
    			Player.skillPointUse();
    			thickSkinLbl.setText("Thick Skin (" + thickSkin + "/5)");
    			pointUsed = true;
    			skillPointsUsed++;
    			dmgResist++;
    	    }});
		thickSkinBtn.setSize(50, 50);
		thickSkinBtn.setPosition(thickSkinLbl.getX() + thickSkinLbl.getWidth() / 2.5f, thickSkinLbl.getY() - 65f);
		descriptionListener(thickSkinBtn, "Increase damage resistance");
		
		weaponMasteryBtn = new TextButton("+", storage.buttonStyle);
		weaponMasteryBtn.setColor(Color.WHITE);
		weaponMasteryBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.skillTreeGains(3);
    			weaponMastery++;
    			Player.skillPointUse();
    			weaponMasteryLbl.setText("Weapon Mastery (" + weaponMastery + "/3)");
    			pointUsed = true;
    			skillPointsUsed++;
    			weaponDmg++;
    	    }});
		weaponMasteryBtn.setSize(50, 50);
		weaponMasteryBtn.setPosition(weaponMasteryLbl.getX() + weaponMasteryLbl.getWidth() / 2.5f, weaponMasteryLbl.getY() - 65f);
		descriptionListener(weaponMasteryBtn, "Increase weapon damage");
		
		blockAuraBtn = new TextButton("+", storage.buttonStyle);
		blockAuraBtn.setColor(Color.WHITE);
		blockAuraBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			blockAura = 1;
    			Player.skillPointUse();
    			blockAuraLbl.setText("Block Aura (" + blockAura + "/1)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		blockAuraBtn.setSize(50, 50);
		blockAuraBtn.setPosition(blockAuraLbl.getX() + blockAuraLbl.getWidth() / 2.5f, blockAuraLbl.getY() - 65f);
		descriptionListener(blockAuraBtn, "Every third enemy attack is blocked");
		
		eleResistBtn = new TextButton("+", storage.buttonStyle);
		eleResistBtn.setColor(Color.WHITE);
		eleResistBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			eleResist++;
    			Player.skillPointUse();
    			eleResistLbl.setText("Elemental Resistance (" + eleResist + "/2)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		eleResistBtn.setSize(50, 50);
		eleResistBtn.setPosition(eleResistLbl.getX() + eleResistLbl.getWidth() / 2.5f, eleResistLbl.getY() - 65f);
		descriptionListener(eleResistBtn, "Increase damage resistence of DoT attacks");
		
		rendMasteryBtn = new TextButton("+", storage.buttonStyle);
		rendMasteryBtn.setColor(Color.WHITE);
		rendMasteryBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			rendMastery++;
    			Player.skillPointUse();
    			rendMasteryLbl.setText("Rend Mastery (" + rendMastery + "/3)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		rendMasteryBtn.setSize(50, 50);
		rendMasteryBtn.setPosition(rendMasteryLbl.getX() + rendMasteryLbl.getWidth() / 2.5f, rendMasteryLbl.getY() - 65f);
		descriptionListener(rendMasteryBtn, "Increase damage over time effects");
		
		lifeStealBtn = new TextButton("+", storage.buttonStyle);
		lifeStealBtn.setColor(Color.WHITE);
		lifeStealBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lifeSteal = 1;
    			Player.skillPointUse();
    			lifeStealLbl.setText("Life Steal (" + lifeSteal + "/1)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		lifeStealBtn.setSize(50, 50);
		lifeStealBtn.setPosition(lifeStealLbl.getX() + lifeStealLbl.getWidth() / 2.5f, lifeStealLbl.getY() - 65f);
		descriptionListener(lifeStealBtn, "Heal for a portion of your attack");
		
		poisonRendBtn = new TextButton("+", storage.buttonStyle);
		poisonRendBtn.setColor(Color.WHITE);
		poisonRendBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			poisonRend = 1;
    			Player.skillPointUse();
    			poisonRendLbl.setText("Poison Rend (" + poisonRend + "/1)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		poisonRendBtn.setSize(50, 50);
		poisonRendBtn.setPosition(poisonRendLbl.getX() + poisonRendLbl.getWidth() / 2.5f, poisonRendLbl.getY() - 65f);
		descriptionListener(poisonRendBtn, "Apply poison when using Rend");
		
		ironSkinBtn = new TextButton("+", storage.buttonStyle);
		ironSkinBtn.setColor(Color.WHITE);
		ironSkinBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.skillTreeGains(2);
    			ironSkin += 1;
    			Player.skillPointUse();
    			ironSkinLbl.setText("Thick Skin (" + ironSkin + "/5)");
    			pointUsed = true;
    			skillPointsUsed++;
    			dmgResist++;
    	    }});
		ironSkinBtn.setSize(50, 50);
		ironSkinBtn.setPosition(ironSkinLbl.getX() + ironSkinLbl.getWidth() / 2.5f, ironSkinLbl.getY() - 65f);
		descriptionListener(ironSkinBtn, "Increase damage resistence");
		
		bulkUpBtn = new TextButton("+", storage.buttonStyle);
		bulkUpBtn.setColor(Color.WHITE);
		bulkUpBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.skillTreeGains(4);
    			bulkUp += 1;
    			Player.skillPointUse();
    			bulkUpLbl.setText("Bulk Up! (" + bulkUp + "/5)");
    			pointUsed = true;
    			skillPointsUsed++;
    			strength++;
    	    }});
		bulkUpBtn.setSize(50, 50);
		bulkUpBtn.setPosition(bulkUpLbl.getX() + bulkUpLbl.getWidth() / 2.5f, bulkUpLbl.getY() - 65f);
		descriptionListener(bulkUpBtn, "Increase player strength");
		
		healthyBtn = new TextButton("+", storage.buttonStyle);
		healthyBtn.setColor(Color.WHITE);
		healthyBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.skillTreeGains(5);
    			healthy += 1;
    			Player.skillPointUse();
    			healthyLbl.setText("Healthy (" + healthy + "/5)");
    			pointUsed = true;
    			skillPointsUsed++;
    			maxHP++;
    	    }});
		healthyBtn.setSize(50, 50);
		healthyBtn.setPosition(healthyLbl.getX() + healthyLbl.getWidth() / 2.5f, healthyLbl.getY() - 65f);
		descriptionListener(healthyBtn, "Increase player's max Health Points");
		
		sharpenWeaponsBtn = new TextButton("+", storage.buttonStyle);
		sharpenWeaponsBtn.setColor(Color.WHITE);
		sharpenWeaponsBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.skillTreeGains(3);
    			sharpenWeapons += 1;
    			Player.skillPointUse();
    			sharpenWeaponsLbl.setText("Sharpen Weapons (" + sharpenWeapons + "/5)");
    			pointUsed = true;
    			skillPointsUsed++;
    			weaponDmg++;
    	    }});
		sharpenWeaponsBtn.setSize(50, 50);
		sharpenWeaponsBtn.setPosition(sharpenWeaponsLbl.getX() + sharpenWeaponsLbl.getWidth() / 2.5f, sharpenWeaponsLbl.getY() - 65f);
		descriptionListener(sharpenWeaponsBtn, "Increase weapon damage");
		
		luckyStrikeBtn = new TextButton("+", storage.buttonStyle);
		luckyStrikeBtn.setColor(Color.WHITE);
		luckyStrikeBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			luckyStrike = 1;
    			Player.skillPointUse();
    			luckyStrikeLbl.setText("Lucky Strike (" + luckyStrike + "/1)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		luckyStrikeBtn.setSize(50, 50);
		luckyStrikeBtn.setPosition(luckyStrikeLbl.getX() + luckyStrikeLbl.getWidth() / 2.5f, luckyStrikeLbl.getY() - 65f);
		descriptionListener(luckyStrikeBtn, "The first attack of a battle is doubled");
		
		blockEfficiencyBtn = new TextButton("+", storage.buttonStyle);
		blockEfficiencyBtn.setColor(Color.WHITE);
		blockEfficiencyBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			blockEfficiency += 1;
    			Player.skillPointUse();
    			blockEfficiencyLbl.setText("Block Efficiency (" + blockEfficiency + "/5)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		blockEfficiencyBtn.setSize(50, 50);
		blockEfficiencyBtn.setPosition(blockEfficiencyLbl.getX() + blockEfficiencyLbl.getWidth() / 2.5f, blockEfficiencyLbl.getY() - 65f);
		descriptionListener(blockEfficiencyBtn, "Chance to reactivate Barrier after it's depleted");
		
		bludgeonEnemyBtn = new TextButton("+", storage.buttonStyle);
		bludgeonEnemyBtn.setColor(Color.WHITE);
		bludgeonEnemyBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			bludgeonEnemy = 1;
    			Player.skillPointUse();
    			bludgeonEnemyLbl.setText("Bludgeon Enemy (" + bludgeonEnemy + "/1)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		bludgeonEnemyBtn.setSize(50, 50);
		bludgeonEnemyBtn.setPosition(bludgeonEnemyLbl.getX() + bludgeonEnemyLbl.getWidth() / 2.5f, bludgeonEnemyLbl.getY() - 65f);
		descriptionListener(bludgeonEnemyBtn, "Every fifth attack with a 2H weapon also stuns the enemy");
		
		doubleSwingBtn = new TextButton("+", storage.buttonStyle);
		doubleSwingBtn.setColor(Color.WHITE);
		doubleSwingBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			doubleSwing = 1;
    			Player.skillPointUse();
    			doubleSwingLbl.setText("Double Swing (" + doubleSwing + "/1)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		doubleSwingBtn.setSize(50, 50);
		doubleSwingBtn.setPosition(doubleSwingLbl.getX() + doubleSwingLbl.getWidth() / 2.5f, doubleSwingLbl.getY() - 65f);
		descriptionListener(doubleSwingBtn, "Every third attack with a 1H weapon hits twice");
		
		thornsBtn = new TextButton("+", storage.buttonStyle);
		thornsBtn.setColor(Color.WHITE);
		thornsBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			thorns = 1;
    			Player.skillPointUse();
    			thornsLbl.setText("Thorns (" + thorns + "/1)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		thornsBtn.setSize(50, 50);
		thornsBtn.setPosition(thornsLbl.getX() + thornsLbl.getWidth() / 2.5f, thornsLbl.getY() - 65f);		
		descriptionListener(thornsBtn, "Add permanent Thorns to the player");
		
		stage.addActor(twoHandBtn);
		stage.addActor(oneHandBtn);
		stage.addActor(thickSkinBtn);		
		stage.addActor(weaponMasteryBtn);
		stage.addActor(blockAuraBtn);
		stage.addActor(eleResistBtn);
		stage.addActor(rendMasteryBtn);
		stage.addActor(lifeStealBtn);
		stage.addActor(poisonRendBtn);
		stage.addActor(ironSkinBtn);
		stage.addActor(bulkUpBtn);
		stage.addActor(healthyBtn);
		stage.addActor(sharpenWeaponsBtn);
		stage.addActor(luckyStrikeBtn);
		stage.addActor(blockEfficiencyBtn);
		stage.addActor(bludgeonEnemyBtn);
		stage.addActor(doubleSwingBtn);
		stage.addActor(thornsBtn);		
		stage.addActor(twoHandLbl);
		stage.addActor(oneHandLbl);
		stage.addActor(thickSkinLbl);
		stage.addActor(weaponMasteryLbl);
		stage.addActor(blockAuraLbl);
		stage.addActor(eleResistLbl);
		stage.addActor(rendMasteryLbl);
		stage.addActor(lifeStealLbl);
		stage.addActor(poisonRendLbl);
		stage.addActor(ironSkinLbl);
		stage.addActor(bulkUpLbl);
		stage.addActor(healthyLbl);
		stage.addActor(sharpenWeaponsLbl);
		stage.addActor(luckyStrikeLbl);
		stage.addActor(blockEfficiencyLbl);
		stage.addActor(bludgeonEnemyLbl);
		stage.addActor(doubleSwingLbl);
		stage.addActor(thornsLbl);
	
		hideUpgradeButtons();		
		checkAvailableUpgrades();
		lockUpgradeButtons();
	}	
	
	private void descriptionListener(final TextButton button, final String description) {
	    button.setColor(Color.WHITE);
	    
	    // Enter listener
	    button.addListener(new InputListener() {
	        @Override
	        public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	            skillDescription.setVisible(true);
	            skillDescription.setText(description);
	        }

	        // Exit listener
	        @Override
	        public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	            skillDescription.setVisible(false);
	        }
	    });
	}
	
	private void resetTree() {
		// Reset tree points
		twoHMastery = 0;
		oneHMastery = 0;
		thickSkin = 0;
		weaponMastery = 0;
		blockAura = 0;
		eleResist = 0;
		rendMastery = 0;
		lifeSteal = 0;
		poisonRend = 0;
		ironSkin = 0;
		bulkUp = 0;
		healthy = 0;
		sharpenWeapons = 0;
		luckyStrike = 0;
		blockEfficiency = 0;
		bludgeonEnemy = 0;
		doubleSwing = 0;
		thorns = 0;
		
		// Reset player stats from skill tree
		Player.setTwoHandStr(Player.getTwoHandStr() - twoHandStr);
		Player.setOneHandStr(Player.getOneHandStr() - oneHandStr);
		Player.setDmgResist(Player.getDmgResist() - dmgResist);
		Player.setWeaponDmg(Player.getWeaponDmg() - weaponDmg);
		Player.setMaxHP(Player.getMaxHP() - maxHP);
		Player.setStrength(Player.getStrength() - strength);
		
		// Reset points
		Player.setSkillPoints(Player.getSkillPoints() + skillPointsUsed);
		skillPointsUsed = 0;
		skillPoints.setText("Skill points: " + Player.getSkillPoints());
		
		hideUpgradeButtons();
		checkAvailableUpgrades();
		lockUpgradeButtons();		
		
		// Reset text
		twoHandLbl.setText("2H Mastery (" + twoHMastery + "/5)");
		oneHandLbl.setText("1H Mastery (" + oneHMastery + "/5)");
		thickSkinLbl.setText("Thick Skin (" + thickSkin + "/5)");
		weaponMasteryLbl.setText("Weapon Mastery (" + weaponMastery + "/3)");
		blockAuraLbl.setText("Block Aura (" + blockAura + "/1)");
		eleResistLbl.setText("Elemental Resistance (" + eleResist + "/2)");
		rendMasteryLbl.setText("Rend Mastery (" + rendMastery + "/3)");
		lifeStealLbl.setText("Life Steal (" + lifeSteal + "/1)");
		poisonRendLbl.setText("Poison Rend (" + poisonRend + "/1)");
		ironSkinLbl.setText("Thick Skin (" + ironSkin + "/5)");
		bulkUpLbl.setText("Bulk Up! (" + bulkUp + "/5)");
		healthyLbl.setText("Healthy (" + healthy + "/5");
		sharpenWeaponsLbl.setText("Sharpen Weapons (" + sharpenWeapons + "/5)");
		luckyStrikeLbl.setText("Lucky Strike (" + luckyStrike + "/1)");
		blockEfficiencyLbl.setText("Block Efficiency (" + blockEfficiency + "/5)");
		bludgeonEnemyLbl.setText("Bludgeon Enemy (" + bludgeonEnemy + "/1)");
		doubleSwingLbl.setText("Double Swing (" + doubleSwing + "/1)");
		thornsLbl.setText("Thorns (" + thorns + "/1)");
	}
	
	private void hideUpgradeButtons() {
		twoHandBtn.setVisible(false);
		oneHandBtn.setVisible(false);
		thickSkinBtn.setVisible(false);
		weaponMasteryBtn.setVisible(false);	
		blockAuraBtn.setVisible(false);
		eleResistBtn.setVisible(false);
		rendMasteryBtn.setVisible(false);
		lifeStealBtn.setVisible(false);
		poisonRendBtn.setVisible(false);
		ironSkinBtn.setVisible(false);
		bulkUpBtn.setVisible(false);	
		healthyBtn.setVisible(false);
		sharpenWeaponsBtn.setVisible(false);	
		luckyStrikeBtn.setVisible(false);
		blockEfficiencyBtn.setVisible(false);
		bludgeonEnemyBtn.setVisible(false);
		doubleSwingBtn.setVisible(false);
		thornsBtn.setVisible(false);
	}
	
	private void checkAvailableUpgrades() {
		if(Player.getSkillPoints() > 0) {
			twoHandBtn.setVisible(true);
			oneHandBtn.setVisible(true);
			thickSkinBtn.setVisible(true);
			
			if(skillPointsUsed >= 5) {
				weaponMasteryBtn.setVisible(true);	
				blockAuraBtn.setVisible(true);
				eleResistBtn.setVisible(true);
				rendMasteryBtn.setVisible(true);
			}
			
			if(skillPointsUsed >= 10) {
				lifeStealBtn.setVisible(true);
				poisonRendBtn.setVisible(true);
				ironSkinBtn.setVisible(true);
				bulkUpBtn.setVisible(true);	
				healthyBtn.setVisible(true);
			}
			
			if(skillPointsUsed >= 15) {
				sharpenWeaponsBtn.setVisible(true);	
				luckyStrikeBtn.setVisible(true);
				blockEfficiencyBtn.setVisible(true);
			}
			
			if(skillPointsUsed >= 20) {
				bludgeonEnemyBtn.setVisible(true);
				doubleSwingBtn.setVisible(true);
				thornsBtn.setVisible(true);
			}		
		}
		
		if(lifeSteal == 1)
			poisonRendBtn.setVisible(false);
		else if(poisonRend == 1)
			lifeStealBtn.setVisible(false);
		
		if(bludgeonEnemy == 1) {
			doubleSwingBtn.setVisible(false);
			thornsBtn.setVisible(false);
		}
		else if(doubleSwing == 1) {
			bludgeonEnemyBtn.setVisible(false);
			thornsBtn.setVisible(false);
		}
		else if(thorns == 1) {
			bludgeonEnemyBtn.setVisible(false);
			doubleSwingBtn.setVisible(false);
		}
	}
	
	private void lockUpgradeButtons() {		
		if(twoHMastery >= 5)
			twoHandBtn.setVisible(false);		
		if(oneHMastery >= 5)
			oneHandBtn.setVisible(false);		
		if(thickSkin >= 5)
			thickSkinBtn.setVisible(false);		
		if(weaponMastery >= 3)
			weaponMasteryBtn.setVisible(false);		
		if(blockAura >= 1)
			blockAuraBtn.setVisible(false);	
		if(eleResist >= 2)
			eleResistBtn.setVisible(false);	
		if(rendMastery >= 3)
			rendMasteryBtn.setVisible(false);	
		if(lifeSteal >= 1)
			lifeStealBtn.setVisible(false);		
		if(poisonRend >= 1)
			poisonRendBtn.setVisible(false);
		if(ironSkin >= 5)
			ironSkinBtn.setVisible(false);	
		if(bulkUp >= 5)
			bulkUpBtn.setVisible(false);
		if(healthy >= 5)
			healthyBtn.setVisible(false);
		if(sharpenWeapons >= 5)
			sharpenWeaponsBtn.setVisible(false);	
		if(luckyStrike >= 1)
			luckyStrikeBtn.setVisible(false);
		if(blockEfficiency >= 5)
			blockEfficiencyBtn.setVisible(false);
		if(bludgeonEnemy >= 1)
			bludgeonEnemyBtn.setVisible(false);
		if(doubleSwing >= 1)
			doubleSwingBtn.setVisible(false);
		if(thorns >= 1)
			thornsBtn.setVisible(false);
	}
	
	public void update() {
		if(Player.getSkillPoints() <= 0)
			hideUpgradeButtons();
		
		if(pointUsed) {
			skillPoints.setText("Skill points: " + Player.getSkillPoints());
			pointUsed = false;
			checkAvailableUpgrades();
			lockUpgradeButtons();			
		}			
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
		
		update();
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
		stage.dispose();
		skin.dispose();
		storage.font.dispose();
	}
}
