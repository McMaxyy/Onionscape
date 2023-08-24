package player;

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

public class BerserkerSkillTree implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private TextButton homeBtn, resetBtn;
	private TextButton twoHandBtn, oneHandBtn, thickSkinBtn, weaponMasteryBtn, blockAuraBtn,
	eleResistBtn, rendMasteryBtn, lifeStealBtn, poisonRendBtn, ironSkinBtn, bulkUpBtn,
	sharpenWeaponsBtn, luckyStrikeBtn, blockEfficiencyBtn, bludgeonEnemyBtn, doubleSwingBtn, heavyArmorBtn;
	private Label twoHandLbl, oneHandLbl, thickSkinLbl, weaponMasteryLbl, blockAuraLbl, 
	eleResistLbl, rendMasteryLbl, lifeStealLbl, poisonRendLbl, ironSkinLbl, bulkUpLbl,sharpenWeaponsLbl, 
	luckyStrikeLbl, blockEfficiencyLbl, bludgeonEnemyLbl, doubleSwingLbl, heavyArmorLbl;
	private Label level, skillPoints;
	private GameScreen gameScreen;
	private boolean pointUsed = false;
	private static int skillPointsUsed = 0;

	private static int twoHMastery = 0;
	private static int oneHMastery = 0;
	private static int thickSkin = 0;
	private static int weaponMastery = 0;
	private static int blockAura = 0;
	private static int eleResist = 0;
	private static int rendMastery = 0;
	private static int lifeSteal = 0;
	private static int poisonRend = 0;
	private static int ironSkin = 0;
	private static int bulkUp = 0;
	private static int sharpenWeapons = 0;
	private static int luckyStrike = 0;
	private static int blockEfficiency = 0;
	private static int bludgeonEnemy = 0;
	private static int doubleSwing = 0;
	private static int heavyArmor = 0;
	
	public BerserkerSkillTree(Viewport viewport, Game game, GameScreen gameScreen) {
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
		homeBtn = new TextButton("Home", storage.buttonStyle);
		homeBtn.setColor(Color.LIGHT_GRAY);
		homeBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.setCurrentState(GameScreen.HOME);
    	    }});
		homeBtn.setSize(130, 80);
		homeBtn.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.25f);
		stage.addActor(homeBtn);
		
		resetBtn = new TextButton("Reset", storage.buttonStyle);
		resetBtn.setColor(Color.LIGHT_GRAY);
		resetBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			resetTree();
    	    }});
		resetBtn.setSize(130, 80);
		resetBtn.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.4f);
		stage.addActor(resetBtn);
	
		level = new Label("Level: " + Player.getLevel(), storage.labelStyle);
		level.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.05f);
		stage.addActor(level);
		
		skillPoints = new Label("Skill points: " + Player.getSkillPoints(), storage.labelStyle);
		skillPoints.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.1f);
		stage.addActor(skillPoints);
		
		// Skill Tree labels
		twoHandLbl = new Label("2H Mastery (" + twoHMastery + "/5)", storage.labelStyle);
		twoHandLbl.setPosition(vp.getWorldWidth() / 4f, vp.getWorldHeight() / 1.15f);
		
		oneHandLbl = new Label("1H Mastery (" + oneHMastery + "/5)", storage.labelStyle);
		oneHandLbl.setPosition(vp.getWorldWidth() / 2.3f, vp.getWorldHeight() / 1.15f);
		
		thickSkinLbl = new Label("Thick Skin (" + thickSkin + "/5)", storage.labelStyle);
		thickSkinLbl.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 1.15f);
		
		weaponMasteryLbl = new Label("Weapon Mastery (" + weaponMastery + "/3)", storage.labelStyle);
		weaponMasteryLbl.setPosition(vp.getWorldWidth() / 7f, vp.getWorldHeight() / 1.4f);
		
		blockAuraLbl = new Label("Block Aura (" + blockAura + "/1)", storage.labelStyle);
		blockAuraLbl.setPosition(vp.getWorldWidth() / 2.9f, vp.getWorldHeight() / 1.4f);
		
		eleResistLbl = new Label("Elemental Resistance (" + eleResist + "/2)", storage.labelStyle);
		eleResistLbl.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 1.4f);
		
		rendMasteryLbl = new Label("Rend Mastery (" + rendMastery + "/3)", storage.labelStyle);
		rendMasteryLbl.setPosition(vp.getWorldWidth() / 1.35f, vp.getWorldHeight() / 1.4f);
		
		poisonRendLbl = new Label("Poison Rend (" + poisonRend + "/1)", storage.labelStyle);
		poisonRendLbl.setPosition(vp.getWorldWidth() / 7f, vp.getWorldHeight() / 1.9f);
		
		lifeStealLbl = new Label("Life Steal (" + lifeSteal + "/1)", storage.labelStyle);
		lifeStealLbl.setPosition(vp.getWorldWidth() / 2.9f, vp.getWorldHeight() / 1.9f);
		
		ironSkinLbl = new Label("Iron Skin (" + ironSkin + "/5)", storage.labelStyle);
		ironSkinLbl.setPosition(vp.getWorldWidth() / 1.8f, vp.getWorldHeight() / 1.9f);
		
		bulkUpLbl = new Label("Bulk Up! (" + bulkUp + "/5)", storage.labelStyle);
		bulkUpLbl.setPosition(vp.getWorldWidth() / 1.35f, vp.getWorldHeight() / 1.9f);
		
		sharpenWeaponsLbl = new Label("Sharpen Weapons (" + sharpenWeapons + "/5)", storage.labelStyle);
		sharpenWeaponsLbl.setPosition(vp.getWorldWidth() / 5f, vp.getWorldHeight() / 2.9f);
		
		luckyStrikeLbl = new Label("Lucky Strike (" + luckyStrike + "/1)", storage.labelStyle);
		luckyStrikeLbl.setPosition(vp.getWorldWidth() / 2.3f, vp.getWorldHeight() / 2.9f);
		
		blockEfficiencyLbl = new Label("Block Efficiency (" + blockEfficiency + "/5)", storage.labelStyle);
		blockEfficiencyLbl.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 2.9f);
		
		bludgeonEnemyLbl = new Label("Bludgeon Enemy (" + bludgeonEnemy + "/1)", storage.labelStyle);
		bludgeonEnemyLbl.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 6.3f);
		
		doubleSwingLbl = new Label("Double Swing (" + doubleSwing + "/1)", storage.labelStyle);
		doubleSwingLbl.setPosition(vp.getWorldWidth() / 2.3f, vp.getWorldHeight() / 6.3f);
		
		heavyArmorLbl = new Label("Heavy Armor (" + heavyArmor + "/1)", storage.labelStyle);
		heavyArmorLbl.setPosition(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 6.3f);
		
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
    	    }});
		twoHandBtn.setSize(50, 50);
		twoHandBtn.setPosition(twoHandLbl.getX() + twoHandLbl.getWidth() / 2.5f, twoHandLbl.getY() - 65f);
		
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
    	    }});
		oneHandBtn.setSize(50, 50);
		oneHandBtn.setPosition(oneHandLbl.getX() + oneHandLbl.getWidth() / 2.5f, oneHandLbl.getY() - 65f);
		
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
    	    }});
		thickSkinBtn.setSize(50, 50);
		thickSkinBtn.setPosition(thickSkinLbl.getX() + thickSkinLbl.getWidth() / 2.5f, thickSkinLbl.getY() - 65f);
		
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
    	    }});
		weaponMasteryBtn.setSize(50, 50);
		weaponMasteryBtn.setPosition(weaponMasteryLbl.getX() + weaponMasteryLbl.getWidth() / 2.5f, weaponMasteryLbl.getY() - 65f);
		
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
    	    }});
		ironSkinBtn.setSize(50, 50);
		ironSkinBtn.setPosition(ironSkinLbl.getX() + ironSkinLbl.getWidth() / 2.5f, ironSkinLbl.getY() - 65f);
		
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
    	    }});
		bulkUpBtn.setSize(50, 50);
		bulkUpBtn.setPosition(bulkUpLbl.getX() + bulkUpLbl.getWidth() / 2.5f, bulkUpLbl.getY() - 65f);
		
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
    	    }});
		sharpenWeaponsBtn.setSize(50, 50);
		sharpenWeaponsBtn.setPosition(sharpenWeaponsLbl.getX() + sharpenWeaponsLbl.getWidth() / 2.5f, sharpenWeaponsLbl.getY() - 65f);
		
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
		
		blockEfficiencyBtn = new TextButton("+", storage.buttonStyle);
		blockEfficiencyBtn.setColor(Color.WHITE);
		blockEfficiencyBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.skillTreeGains(4);
    			blockEfficiency += 1;
    			Player.skillPointUse();
    			blockEfficiencyLbl.setText("Block Efficiency (" + blockEfficiency + "/5)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		blockEfficiencyBtn.setSize(50, 50);
		blockEfficiencyBtn.setPosition(blockEfficiencyLbl.getX() + blockEfficiencyLbl.getWidth() / 2.5f, blockEfficiencyLbl.getY() - 65f);
		
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
		
		heavyArmorBtn = new TextButton("+", storage.buttonStyle);
		heavyArmorBtn.setColor(Color.WHITE);
		heavyArmorBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.skillTreeGains(5);
    			heavyArmor = 1;
    			Player.skillPointUse();
    			heavyArmorLbl.setText("Heavy Armor (" + heavyArmor + "/1)");
    			pointUsed = true;
    			skillPointsUsed++;
    	    }});
		heavyArmorBtn.setSize(50, 50);
		heavyArmorBtn.setPosition(heavyArmorLbl.getX() + heavyArmorLbl.getWidth() / 2.5f, heavyArmorLbl.getY() - 65f);		
		
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
		stage.addActor(sharpenWeaponsBtn);
		stage.addActor(luckyStrikeBtn);
		stage.addActor(blockEfficiencyBtn);
		stage.addActor(bludgeonEnemyBtn);
		stage.addActor(doubleSwingBtn);
		stage.addActor(heavyArmorBtn);		
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
		stage.addActor(sharpenWeaponsLbl);
		stage.addActor(luckyStrikeLbl);
		stage.addActor(blockEfficiencyLbl);
		stage.addActor(bludgeonEnemyLbl);
		stage.addActor(doubleSwingLbl);
		stage.addActor(heavyArmorLbl);
	
		hideUpgradeButtons();
		lockUpgradeButtons();
		checkAvailableUpgrades();
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
		sharpenWeapons = 0;
		luckyStrike = 0;
		blockEfficiency = 0;
		bludgeonEnemy = 0;
		doubleSwing = 0;
		heavyArmor = 0;
		
		// Reset player stats from skill tree
		Player.setTwoHandStr(0);
		Player.setOneHandStr(0);
		Player.setDmgResist(0);
		Player.setWeaponDmg(0);
		
		// Reset points
		Player.setSkillPoints(Player.getSkillPoints() + skillPointsUsed);
		skillPointsUsed = 0;
		skillPoints.setText("Skill points: " + Player.getSkillPoints());
		
		hideUpgradeButtons();
		lockUpgradeButtons();
		checkAvailableUpgrades();
		
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
		sharpenWeaponsLbl.setText("Sharpen Weapons (" + sharpenWeapons + "/5)");
		luckyStrikeLbl.setText("Lucky Strike (" + luckyStrike + "/1)");
		blockEfficiencyLbl.setText("Block Efficiency (" + blockEfficiency + "/5)");
		bludgeonEnemyLbl.setText("Bludgeon Enemy (" + bludgeonEnemy + "/1)");
		doubleSwingLbl.setText("Double Swing (" + doubleSwing + "/1)");
		heavyArmorLbl.setText("Heavy Armor (" + heavyArmor + "/1)");
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
		sharpenWeaponsBtn.setVisible(false);	
		luckyStrikeBtn.setVisible(false);
		blockEfficiencyBtn.setVisible(false);
		bludgeonEnemyBtn.setVisible(false);
		doubleSwingBtn.setVisible(false);
		heavyArmorBtn.setVisible(false);
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
			}
			
			if(skillPointsUsed >= 15) {
				sharpenWeaponsBtn.setVisible(true);	
				luckyStrikeBtn.setVisible(true);
				blockEfficiencyBtn.setVisible(true);
			}
			
			if(skillPointsUsed >= 20) {
				bludgeonEnemyBtn.setVisible(true);
				doubleSwingBtn.setVisible(true);
				heavyArmorBtn.setVisible(true);
			}		
		}
		
		if(lifeSteal == 1)
			poisonRendBtn.setVisible(false);
		else if(poisonRend == 1)
			lifeStealBtn.setVisible(false);
		
		if(bludgeonEnemy == 1) {
			doubleSwingBtn.setVisible(false);
			heavyArmorBtn.setVisible(false);
		}
		else if(doubleSwing == 1) {
			bludgeonEnemyBtn.setVisible(false);
			heavyArmorBtn.setVisible(false);
		}
		else if(heavyArmor == 1) {
			bludgeonEnemyBtn.setVisible(false);
			doubleSwingBtn.setVisible(false);
		}
	}
	
	private void lockUpgradeButtons() {		
		if(twoHMastery >= 5)
			twoHandBtn.setVisible(false);		
		if(oneHMastery >= 5)
			twoHandBtn.setVisible(false);		
		if(thickSkin >= 5)
			twoHandBtn.setVisible(false);		
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
		if(heavyArmor >= 1)
			heavyArmorBtn.setVisible(false);
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
	@Override
	public void dispose() {
		stage.dispose();
		skin.dispose();
		storage.font.dispose();
	}
}
