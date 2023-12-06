package scenes;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;

import player.Player;
import storage.Abilities;
import storage.Enemy;
import storage.Items;
import storage.Storage;

public class FightScene implements Screen{
	Skin skin;
    Viewport vp;
    String combatText;
    Random rand = new Random();
    private Game game;
    public Stage stage;
    private TextButton attackBtn, endTurn, ability1, ability2, ability3, ability4, homeBtn;
    private TextButton reward1, reward2, reward3;
    private Label playerHPLbl, enemyHPLbl, combatLog, enemyNameLbl;
    private Texture charTexture, enemyTexture, mapTexture, barrierBuffTex, bleedBuffTex, enrageBuffTex,
    poisonBuffTex, stunBuffTex, hardenBuffTex;
    private SpriteBatch charBatch = new SpriteBatch();
    private SpriteBatch enemyBatch = new SpriteBatch();
    private SpriteBatch weaponBatch = new SpriteBatch();
    private SpriteBatch shieldBatch = new SpriteBatch();
    private SpriteBatch gameOverBatch = new SpriteBatch();
    private SpriteBatch helmetBatch = new SpriteBatch();
	private SpriteBatch chestBatch = new SpriteBatch();
	private SpriteBatch bootsBatch = new SpriteBatch();
	private SpriteBatch mapBatch = new SpriteBatch();
	private SpriteBatch playerHealthBatch = new SpriteBatch();
	private SpriteBatch enemyHealthBatch = new SpriteBatch();
	private SpriteBatch buffBatch = new SpriteBatch();
	private SpriteBatch debuffBatch = new SpriteBatch();
    private Sprite charSprite, enemySprite, weaponSprite, shieldSprite, helmetSprite, chestSprite, bootsSprite;
    private int enemyHP, enemyDamage, enemyValue, enemyMaxHP, expValue;
    private String enemyName, eAbility1, eAbility2, eAbility3, loot;
    private boolean pDead, eDead, btnClicked, turnEnded, playerTurn = true, gameOver, playerAttack, twoHand;
    private Storage storage;
    private static int ab1Uses, ab2Uses, ab3Uses, ab4Uses;
    private Label ab1UseLbl, ab2UseLbl, ab3UseLbl, ab4UseLbl;
    private GameScreen gameScreen;
    private int rendLeft, attackCount = 3, eAttackCount = 0, bludgeonCount = 0, doubleSwingCount = 0,
    		weakenLeft, thornsLeft, enrageLeft;
    private boolean enemyStunned, barrierActive, hardenActive, firstAttack = false, 
    		riposteActive, firstLoad = true;
    private int eRendLeft, ePoisonLeft, eEnrageLeft;
    private boolean eHardenActive, playerStunned, eBarrierActive;
    java.util.List<Items> equippedItems;
    Table itemTable = new Table();
    Table abilitySwapTable = new Table();
    private float time = 0, enemyClickTime = 0f, rotationTime = 0f;
    private float scaleSpeed = 4f; 
    private float heightChar, heightEnemy, heightWeapon = 0;
    private float baseYChar, baseYEnemy;
    private float weaponRotation = 20f; // starting rotation
    private float rotationSpeed = -10f; // rotation speed per frame (adjust as needed)
    private float rotationInterval = 0.017f;
    private int timer = 0;   
    public static boolean normal, elite, boss;
    private ShapeRenderer playerHealthBar = new ShapeRenderer();
    private ShapeRenderer enemyHealthBar = new ShapeRenderer();
    private Image[] buffBar = new Image[3];
    private Image[] debuffBar = new Image[3];
    
    public FightScene(Viewport viewport, Game game, GameScreen gameScreen) {
    	this.gameScreen = gameScreen;
    	this.game = game;
        stage = new Stage(viewport);
        vp = viewport;
        Gdx.input.setInputProcessor(stage);  // Set the stage to process inputs      
        storage = Storage.getInstance();
        skin = storage.skin;
        loadTextures();
        
        // Initialize sprite stuff
        charTexture = Inventory.onionTexture;
        charSprite = new Sprite(charTexture);
        charSprite.setPosition(vp.getWorldWidth() / 20f, vp.getWorldHeight() / 2f);
        heightChar = 450;
        baseYChar = charSprite.getY();
        charSprite.setSize(275, heightChar);
        charSprite.setOrigin(0, 0);
        
        System.out.println("State: " + Player.weaponState);
        
        // Check if the player has the Lucky Strike mastery activated
        if(BerserkerSkillTree.luckyStrike == 1)
        	firstAttack = true;
        
        if(GameScreen.newGame) {
        	Player.newGame();
        }
        
        if(Player.weaponState == 0)
        	Player.setWeaponDmg(0);
        
        storage.createFont();       
        createComponents();
        componentParameters(); 
        createInventoryGrid();
        
        // Check if coming to a raid with depleted ability
        if(ab1Uses <= 0) {
			ability1.setTouchable(Touchable.disabled);
    		ability1.setColor(Color.GRAY);
		}
		if(ab2Uses <= 0) {
			ability2.setTouchable(Touchable.disabled);
    		ability2.setColor(Color.GRAY);
		}
		if(ab3Uses <= 0) {
			ability3.setTouchable(Touchable.disabled);
    		ability3.setColor(Color.GRAY);
		}
		if(ab4Uses <= 0) {
			ability4.setTouchable(Touchable.disabled);
    		ability4.setColor(Color.GRAY);
		}  
		
		System.out.println("Wep dmg: " + Player.getWeaponDmg());
		System.out.println("Str: " + Player.getStrength());
		System.out.println("Dmg res: " + Player.getDmgResist());
    }
    
    private void loadTextures() {
    	mapTexture = Storage.assetManager.get("maps/ForestFight.png", Texture.class);
        mapTexture.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);  
        barrierBuffTex = Storage.assetManager.get("buffs/Barrier.png", Texture.class);
        barrierBuffTex.setFilter(TextureFilter.MipMap,TextureFilter.Nearest); 
        bleedBuffTex = Storage.assetManager.get("buffs/Bleed.png", Texture.class);
        bleedBuffTex.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
        enrageBuffTex = Storage.assetManager.get("buffs/Enrage.png", Texture.class);
        enrageBuffTex.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
        poisonBuffTex = Storage.assetManager.get("buffs/Poison.png", Texture.class);
        poisonBuffTex.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
        stunBuffTex = Storage.assetManager.get("buffs/Stun.png", Texture.class);
        stunBuffTex.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
        hardenBuffTex = Storage.assetManager.get("buffs/Harden.png", Texture.class);
        hardenBuffTex.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
        
        buffBar[0] = new Image(barrierBuffTex);
        buffBar[1] = new Image(enrageBuffTex);
        buffBar[2] = new Image(hardenBuffTex);
        
        debuffBar[0] = new Image(bleedBuffTex);
        debuffBar[1] = new Image(poisonBuffTex);
        debuffBar[2] = new Image(stunBuffTex);
    }
    
    private void newEnemy() {    	
        int randomEnemy;
        if(Home.stageLvl == 1) {
        	if(normal) {
            	randomEnemy = rand.nextInt(5);
            	switch(randomEnemy) {
                case 0:
                    setEnemyAttributes(storage.wolf, "enemies/Wolfie.png");
                    break;
                case 1:
                    setEnemyAttributes(storage.spider, "enemies/Spider.png");
                    break;
                case 2:
                    setEnemyAttributes(storage.bear, "enemies/Bear.png");
                    break;
                case 3:
                    setEnemyAttributes(storage.monkey, "enemies/Monkey.png");
                    break;
                case 4:
                	setEnemyAttributes(storage.wasp, "enemies/Wasp.png");
                    break;
            	}
            }
            else if(elite && !RaidTextScenes.mimic) {
            	randomEnemy = rand.nextInt(2);
            	switch(randomEnemy) {
                case 0:
                    setEnemyAttributes(storage.forestGuardian, "enemies/ForestGuardian.png");
                    break;
                case 1:
                    setEnemyAttributes(storage.mimicTree, "enemies/MimicTree.png");
                    break;
            	}
        	}
            else if(RaidTextScenes.mimic) {
            	setEnemyAttributes(storage.mimicTree, "enemies/MimicTree.png");
            	RaidTextScenes.mimic = false;
            }       	
            else {
                setEnemyAttributes(storage.boar, "enemies/Boar.png");
            }
        }       
    }

    private void setEnemyAttributes(Enemy enemy, String texturePath) {
    	if(!Home.story) {
    		enemyMaxHP = enemy.getMaxHP() / (Home.stageLvl + 1);
    		enemyValue = enemy.getValue() / (Home.stageLvl + 1);
    		enemyDamage = enemy.getAttackPower() / (Home.stageLvl + 1);
    	}
    	else {
    		enemyMaxHP = enemy.getMaxHP();
    		enemyValue = enemy.getValue();
    		enemyDamage = enemy.getAttackPower();
    	}
        enemyName = enemy.getEnemyName();    
        expValue = enemy.getExp();
        eAbility1 = enemy.getAbility1();
        eAbility2 = enemy.getAbility2();
        if(enemy.getAbility3() != null)
        	eAbility3 = enemy.getAbility3();
        enemyHP = enemyMaxHP;
        enemyTexture = Storage.assetManager.get(texturePath, Texture.class);
        enemyTexture.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
        enemyNameLbl.setText(enemyName);
        
        enemySprite = new Sprite(enemyTexture);
        enemySprite.setPosition(vp.getWorldWidth() / 1.4f, vp.getWorldHeight() / 2f);
        heightEnemy = 500;
        baseYEnemy = enemySprite.getY();
        enemySprite.setSize(500, heightEnemy);
        enemySprite.setOrigin(0, 0);
    }
    
    public void update() {
    	if(!pDead && !eDead && btnClicked || turnEnded) {
    		playerHPLbl.setText(Player.getHp() + "/" + Player.getMaxHP());
        	enemyHPLbl.setText(enemyHP + "/" + enemyMaxHP); 
        	btnClicked = false;
    	}
    	
    	if(firstLoad) {
    		enemyHPLbl.setText(enemyHP + "/" + enemyMaxHP);   
    		firstLoad = false;
    	}
        	 		
    	
    	if(attackCount <= 0) {
    		playerTurn = false;
    		
    		attackBtn.setTouchable(Touchable.disabled);
    		attackBtn.setColor(Color.GRAY);
    		ability1.setTouchable(Touchable.disabled);
    		ability1.setColor(Color.GRAY);
    		ability2.setTouchable(Touchable.disabled);
    		ability2.setColor(Color.GRAY);
    		ability3.setTouchable(Touchable.disabled);
    		ability3.setColor(Color.GRAY);
    		ability4.setTouchable(Touchable.disabled);
    		ability4.setColor(Color.GRAY);
    	}
    	else {
    		attackBtn.setTouchable(Touchable.enabled);
    		attackBtn.setColor(Color.LIGHT_GRAY);
    		if(ab1Uses > 0) {
    			ability1.setTouchable(Touchable.enabled);
        		ability1.setColor(Color.LIGHT_GRAY);
    		}
    		if(ab2Uses > 0) {
    			ability2.setTouchable(Touchable.enabled);
        		ability2.setColor(Color.LIGHT_GRAY);
    		}
    		if(ab3Uses > 0) {
    			ability3.setTouchable(Touchable.enabled);
        		ability3.setColor(Color.LIGHT_GRAY);
    		}
    		if(ab4Uses > 0) {
    			ability4.setTouchable(Touchable.enabled);
        		ability4.setColor(Color.LIGHT_GRAY);
    		}
    	}    		
    	  	
    	if(pDead || eDead) {
    		attackBtn.setTouchable(Touchable.disabled);
    		attackBtn.setText("");
    		endTurn.setTouchable(Touchable.disabled);
    		endTurn.setText("");
    		ability1.setTouchable(Touchable.disabled);
    		ability1.setText("");
    		ability2.setTouchable(Touchable.disabled);
    		ability2.setText("");
    		ability3.setTouchable(Touchable.disabled);
    		ability3.setText("");
    		ability4.setTouchable(Touchable.disabled);
    		ability4.setText("");
    		
    		newLine();
	        if(pDead) {
	        	combatLog.setText(combatText + "\n Player died");
	        	homeBtn.clearListeners();
	        	homeBtn.addListener(new ClickListener() {
	        		@Override
	        	    public void clicked(InputEvent event, float x, float y) {
	        			Player.gainCoins(Player.getRaidCoins());
	    				Player.setRaidCoins(0);
	        			stage.clear();
	        			if(!Home.story) {
	        				Player.setStrength(3);
	            			Player.setOneHandStr(0);
	            			Player.setTwoHandStr(0);
	            			Player.setMaxHP(70);
	            			Player.setDmgResist(0);
	            			Player.setWeaponDmg(0);
	        			}
	        			gameScreen.setCurrentState(GameScreen.HOME);
	        	    }});
	        }	        	
	        else {
	        	combatLog.setText("\n Enemy died");	
	        	chooseReward();	
	        	if(Home.story) {
	        		Player.gainExp(expValue);
		        	if(Home.expBoost) {
		        		expValue *= 2;
		        		Player.gainExp(expValue * 2);
		        	}	        		
		        	else
		        		Player.gainExp(expValue);
		        	newLine();
		        	combatLog.setText(combatText + "\n Gained " + expValue + "exp");
		        	Player.checkExp();
	        	}        	
	        }
	        
	        gameOver = true;
	        pDead = eDead = false; 		
    	} 		
    }
    
    private void chooseReward() {
    	int x;
    	
    	x = rand.nextInt(1, 11);
    	switch(x) {
    	case 1:
    	case 2:
    	case 3:
    		reward1.setText("Health Potion");
    		break;
    	case 4:
    	case 5:
    	case 6:
    		reward1.setText("Bomb");
    		break;
    	case 7:
    		reward1.setText("Attack Boost");
    		break;
    	case 8:
    		reward1.setText("Defense Boost");
    		break;
    	case 9:
    		reward1.setText("Health Boost");
    		break;
    	case 10:
    		reward1.setText("Experience Boost");
    		break;
    	}
    	
    	x = rand.nextInt(1, 15);
    	switch(x) {
    	case 1:
    		reward2.setText("Swing");
    		break;
    	case 2:
    		reward2.setText("Rend");
    		break;
    	case 3:
    		reward2.setText("Whirlwind");
    		break;
    	case 4:
    		reward2.setText("Ground Breaker");
    		break;
    	case 5:
    		reward2.setText("Bash");
    		break;
    	case 6:
    		reward2.setText("Barrier");
    		break;
    	case 7:
    		reward2.setText("Harden");
    		break;
    	case 8:
    		reward2.setText("Mend");
    		break;
    	case 9:
    		reward2.setText("Hilt Bash");
    		break;
    	case 10:
    		reward2.setText("Barbed Armor");
    		break;
    	case 11:
    		reward2.setText("Enrage");
    		break;
    	case 12:
    		reward2.setText("Riposte");
    		break;
    	case 13:
    		reward2.setText("Stab");
    		break;
    	case 14:
    		reward2.setText("Decapitate");
    		break;
    	}
    	
    	reward3.setText(enemyValue + " Coins");
    	
    	stage.addActor(reward1);
    	stage.addActor(reward2);
    	stage.addActor(reward3);
    }
    
    private void rewardClick(TextButton button) {
    	if(storage.getEquippedItems().size() < 14) {
    		switch(button.getText().toString()) {
        	case "Swing":
        		storage.equippedItems(storage.itemSwing, "Add");
        		break;
        	case "Rend":
        		storage.equippedItems(storage.itemRend, "Add");
        		break;
        	case "Whirlwind":
        		storage.equippedItems(storage.itemWhirlwind, "Add");
        		break;
        	case "Ground Breaker":
        		storage.equippedItems(storage.itemGroundBreaker, "Add");
        		break;
        	case "Bash":
        		storage.equippedItems(storage.itemBash, "Add");
        		break;
        	case "Barrier":
        		storage.equippedItems(storage.itemBarrier, "Add");
        		break;
        	case "Harden":
        		storage.equippedItems(storage.itemHarden, "Add");
        		break;
        	case "Mend":
        		storage.equippedItems(storage.itemMend, "Add");
        		break;
        	case "Hilt Bash":
        		storage.equippedItems(storage.itemHiltBash, "Add");
        		break;
        	case "Barbed Armor":
        		storage.equippedItems(storage.itemBarbedArmor, "Add");
        		break;
        	case "Enrage":
        		storage.equippedItems(storage.itemEnrage, "Add");
        		break;
        	case "Riposte":
        		storage.equippedItems(storage.itemRiposte, "Add");
        		break;
        	case "Stab":
        		storage.equippedItems(storage.itemStab, "Add");
        		break;
        	case "Decapitate":
        		storage.equippedItems(storage.itemDecapitate, "Add");
        		break;
        	case "Health Potion":
        		storage.equippedItems(storage.healthPot, "Add");
        		break;
			case "Bomb":
				storage.equippedItems(storage.bomb, "Add");
        		break;		
			case "Attack Boost":
				storage.equippedItems(storage.apBoost, "Add");
        		break;
			case "Defense Boost":
				storage.equippedItems(storage.dpBoost, "Add");
        		break;
			case "Health Boost":
				storage.equippedItems(storage.hpBoost, "Add");
        		break;
			case "Experience Boost":
				storage.equippedItems(storage.expBoost, "Add");
        		break;
        	}
    		
    		itemTable.clear();
    		createInventoryGrid();
    	}   	

    	reward1.setVisible(false);
    	reward2.setVisible(false);
    	reward3.setVisible(false);
    }
    
    private void onButtonClicked(TextButton button) {
    	playerAttack = false;
    	timer = 0;
    	weaponRotation = 20f;
    	
    	attackCount--;
    	playerAttack = true;
    	
    	switch(button.getName().toString()) {
    	case "Attack":
    		if(rand.nextInt(4) != 0)
    			playerAttack(0);
    		else {
    			newLine();
    			combatLog.setText(combatText + "\n Player's attack missed");
    		}  	
	        break;
    	case "Swing":
    		if(rand.nextInt(4) != 0)
    			playerAttack(1);
    		else {
    			newLine();
    			combatLog.setText(combatText + "\n Player's attack missed");
    		}   		
    		break;
    	case "Rend":
    		if(rand.nextInt(4) != 0) {
    			rendLeft = 3;
    			newLine();
    			combatLog.setText(combatText + "\n Bleed applied to enemy");
    		}   			
    		else {
    			newLine();
    			combatLog.setText(combatText + "\n Player failed to apply bleed");
    		}  			
    		break;
    	case "Whirlwind":
    		if(rand.nextInt(4) != 0)
    			playerAttack(3);
    		else {
    			newLine();
    			combatLog.setText(combatText + "\n Player's attack missed");
    		}  			
    		break;
    	case "Ground Breaker":
    		if(rand.nextInt(4) != 0)
    			playerAttack(4);
    		else {
    			newLine();
    			combatLog.setText(combatText + "\n Player's attack missed");
    		}  			
    		break;
    	case "Bash":
    		if(rand.nextInt(4) != 0)
    			playerAttack(5);
    		else {
    			newLine();
    			combatLog.setText(combatText + "\n Player's attack missed");
    		}  			
    		break;
    	case "Barrier":
    		newLine();
    		if(rand.nextInt(4) != 0) {
    			barrierActive = true;    			
    			combatLog.setText(combatText + "\n Player activated Barrier");
    		}
    		else
    			combatLog.setText(combatText + "\n Player failed to use Barrier"); 			
    		break;
    	case "Harden":
    		newLine();
    		if(rand.nextInt(4) != 0) {
    			hardenActive = true;    			
    			combatLog.setText(combatText + "\n Player activated Harden");
    		}
    		else
    			combatLog.setText(combatText + "\n Player failed to use Harden");			
    		break;
    	case "Mend":
    		newLine();
			combatLog.setText(combatText + "\n Player used heal");
    		Player.gainHP(storage.mend.getAttackPower());
			if(Player.getHp() > Player.getMaxHP())
				Player.setHp(Player.getMaxHP());
			break;
    	case "Hilt Bash":
    		newLine();
    		if(rand.nextInt(3) != 0) {
    			weakenLeft = 2;  			
    			combatLog.setText(combatText + "\n Enemy weakened");
    		}
    		else
    			combatLog.setText(combatText + "\n Failed to weaken enemy");
    		break;
    	case "Barbed Armor":
    		newLine();
    		if(rand.nextInt(3) != 0) {
    			thornsLeft = 2;
    			combatLog.setText(combatText + "\n Player activated Thorns");
    		}
    		else
    			combatLog.setText(combatText + "\n Failed to activate Thorns");
    		break;
    	case "Enrage":
    		newLine();
    		if(rand.nextInt(4) != 0) {
    			enrageLeft = 2;
    			combatLog.setText(combatText + "\n Player enraged, increasing damage dealt");
    		}
    		else
    			combatLog.setText(combatText + "\n Player failed to enrage");
    		break;
    	case "Riposte":
    		newLine();
    		if(rand.nextInt(4) != 0) {
    			riposteActive = true;
    			combatLog.setText(combatText + "\n Player will reflect the next attack");
    		}
    		else
    			combatLog.setText(combatText + "\n Failed to activate Riposte");
    		break;
    	case "Stab":
    		if(rand.nextInt(4) != 0)
    			playerAttack(13);
    		else {
    			newLine();
    			combatLog.setText(combatText + "\n Player's attack missed");
    		}
    		break;
    	case "Decapitate":
    		if(rand.nextInt(5) != 0)
    			playerAttack(14);
    		else {
    			newLine();
    			combatLog.setText(combatText + "\n Player's attack missed");
    		}
    		break;
    	}
    }
    
    private void playerAttack(int x) {    	
    	int temp = 0;
    	boolean hit = false;
    	switch(x) {
    	case 0:
    		temp += rand.nextInt(1, Player.getStrength() + 1);  		 
    		hit = true;
    		break;
    	case 1:
    		temp += Player.getStrength() + rand.nextInt(1, storage.swing.getAttackPower() + 1);
    		hit = true;
    		break;
    	case 3:
    		for(int i = 0; i < 3; i++)
    			temp += rand.nextInt(1, storage.whirlwind.getAttackPower() + 1);
    		temp += Player.getStrength();
    		hit = true;
    		break;
    	case 4:
    		enemyStunned = true;
    		temp += Player.getStrength() + rand.nextInt(1, storage.groundBreaker.getAttackPower() + 1);
    		hit = true;
    		break;
    	case 5:
    		enemyStunned = true;
    		temp += Player.getStrength() + rand.nextInt(1, storage.bash.getAttackPower() + 1);
    		hit = true;
    		break;
    	case 13:
    		temp += Player.getStrength() + rand.nextInt(1, storage.stab.getAttackPower() + 1);
    		hit = true;
    		break;
    	case 14:
    		temp += Player.getStrength() + rand.nextInt(1, storage.decapitate.getAttackPower() + 1);
    		hit = true;
    		break;
    	}
    	
    	// Add weapon damage
    	if(Player.weaponState == 1) {
    		doubleSwingCount++;
    		temp += Player.getOneHandStr() + Player.getWeaponDmg();
    		if(doubleSwingCount == 3 && BerserkerSkillTree.doubleSwing == 1) {
    			doubleSwingCount = 0;
    			temp *= 2;
    		}
    	}   		
    	else if(Player.weaponState == 2) {
    		bludgeonCount++;
    		temp += Player.getTwoHandStr() + Player.getWeaponDmg();
    		if(bludgeonCount == 5  && BerserkerSkillTree.bludgeonEnemy == 1) {
    			bludgeonCount = 0;
    			enemyStunned = true;
    		}
    	} 
    	
        newLine();
        
        if(hit) {
        	if(enrageLeft > 0) {
        		temp += storage.enrage.getAttackPower();
        		enrageLeft--;
        	}        		
        	if(firstAttack)
        		temp *= 2;  
        	if(weakenLeft > 0) {
        		int weak = temp / 4;
        		temp += weak;
        		weakenLeft--;
        	}
        	
        	if(eHardenActive) {
        		temp /= 2;
        		eHardenActive = false;
        	}
        	
        	if(!eBarrierActive)
        		enemyHP -= temp;
        	
        	if(x == 0 && !eBarrierActive)
        		combatLog.setText(combatText + "\n Player hit the enemy for " + temp + " damage");
        	else if(x == 1 || x == 3 || x == 4 || x == 5 || x == 13 || x == 14 && !eBarrierActive)
        		combatLog.setText(combatText + "\n " + getAbilityName(x) + " hit the enemy for " + temp + " damage");
        	else if(eBarrierActive)
        		combatLog.setText(combatText + "\n Enemy blocked the attack");
        	
        	// Life steal mastery
        	if(BerserkerSkillTree.lifeSteal == 1) {
        		if(Player.getHp() < Player.getMaxHP()) {
        			Player.gainHP(temp / 3);
        			if(Player.getHp() > Player.getMaxHP())
        				Player.setHp(Player.getMaxHP());
        		}      		
        	}      		
        }
               
        if(enemyHP <= 0)
        	eDead = true;  
        if(eBarrierActive)
        	eBarrierActive = false;
        
        firstAttack = false;
    }
    
    private void bleedHit() {
    	// Bleed hit after ending turn
        if(rendLeft > 0 && !eBarrierActive) {
        	int temp = storage.rend.getAttackPower() + BerserkerSkillTree.rendMastery;
        	enemyHP -= temp;
        	newLine();
        	combatLog.setText(combatText + "\n Rend hit the enemy for " + temp + " damage");
        	
        	// Poison Rend mastery
        	if(BerserkerSkillTree.poisonRend == 1) {
            	enemyHP -= storage.rend.getAttackPower();
        		newLine();
            	combatLog.setText(combatText + "\n Enemy hit with poison for " + storage.rend.getAttackPower() + " damage");
        	}
   	
        	rendLeft--;
        	
        	if(enemyHP <= 0)
            	eDead = true; 
        }  
    }
    
    private void enemyAttack(int attack) {
    	String attackType = null;
    	
    	// Increase attack counter for the Block Aura mastery
    	if(!barrierActive && BerserkerSkillTree.blockAura == 1)
    		eAttackCount++;    	
    	if(eAttackCount == 3) {
    		barrierActive = true;
    		eAttackCount = 0;
    	}  
    	   	
    	switch(attack) {
    	case 0:
    	case 1:
    	case 2:
    		attackType = "Attack";
    		break;
    	case 3:
    		attackType = eAbility1;
    		break;
    	case 4:
    		attackType = eAbility2;
    		break;
    	case 5:
    		attackType = eAbility3;
    		break;
    	}
    	
    	if(!attackType.equals("Attack")) {
    		switch(attackType) {
    		case "Bleed":
    			if(eRendLeft > 0)
    				attackType = "Attack";
    			break;
    		case "Poison":
    			if(ePoisonLeft > 0)
    				attackType = "Attack";
    			break;
    		case "Enrage":
    			if(eEnrageLeft > 0)
    				attackType = "Attack";
    			break;
    		case "Harden":
    			if(eHardenActive)
    				attackType = "Attack";
    			break;
    		case "Stun":
    			if(playerStunned)
    				attackType = "Attack";
    			break;
    		case "Barrier":
    			if(eBarrierActive)
    				attackType = "Attack";
    			break;
    		}
    	}
    	
//    	System.out.println(attackType);
    	
    	if(attackType.equals("Attack")) {
    		int temp = enemyDamage;
    		if(eEnrageLeft > 0) {
    			temp += temp / 3;
    			eEnrageLeft--;
    		}
    			
        	temp -= Player.getDmgResist(); // Lower damage via damage resist stat    	
        	
        	if(hardenActive) {
        		hardenActive = false;
        		temp = temp / 2;
        	} 
        	
        	if(temp <= 0)
        		temp = 1;
      	
        	if(!barrierActive && !enemyStunned) {
        		if(!riposteActive) {
        			if(thornsLeft > 0) {
                		temp -= storage.barbedArmor.getAttackPower();
                		thornsLeft--;
                	}        			
        			
        			Player.loseHP(temp);
                    newLine();
                    combatLog.setText(combatText + "\n Enemy attacked for " + temp + " damage");
                    
                    if(BerserkerSkillTree.thorns == 1 || thornsLeft > 0) {                	              		
                    	temp /= 2;
                    	if(temp <= 0)
                    		temp = 1;
                    	enemyHP -= temp;
                    	newLine();
                        combatLog.setText(combatText + "\n Enemy hit with Thorns for " + temp + " damage");
                    }
        		}
        		else {
        			riposteActive = false;
        			enemyHP -= temp;
        			newLine();
        			combatLog.setText(combatText + "\n Player reflected the attack \n and hit for " + temp + " damage");
        		}
        	}
        	else if(barrierActive){
        		newLine();
        		combatLog.setText(combatText + "\n Player blocked the enemy's attack");
        		switch(BerserkerSkillTree.blockEfficiency) {
        		case 0:
        			barrierActive = false;
        			break;
        		case 1:
        			if(rand.nextInt(1, 100) <= 10)
        				break;
        			else
        				barrierActive = false;
        			break;
        		case 2:
        			if(rand.nextInt(1, 100) <= 15)
        				break;
        			else
        				barrierActive = false;
        			break;
        		case 3:
        			if(rand.nextInt(1, 100) <= 20)
        				break;
        			else
        				barrierActive = false;
        			break;
        		case 4:
        			if(rand.nextInt(1, 100) <= 25)
        				break;
        			else
        				barrierActive = false;
        			break;
        		case 5:
        			if(rand.nextInt(1, 100) <= 30)
        				break;
        			else
        				barrierActive = false;
        			break;    		
        		} 
        	}
        	else if(enemyStunned){
        		newLine();
        		combatLog.setText(combatText + "\n Enemy stunned and cannot attack");
        		enemyStunned = false;
        	}
    	}
    	else {
    		newLine();
    		switch(attackType) {
    		case "Bleed":
    			eRendLeft = 3;
    			combatLog.setText(combatText + "\n Enemy hit the player with bleed");
    			break;
    		case "Enrage":
    			eEnrageLeft = 3;
    			combatLog.setText(combatText + "\n Enemy is enraged");
    			break;
    		case "Poison":
    			ePoisonLeft = 3;
    			combatLog.setText(combatText + "\n Enemy poisoned the player");
    			break;
    		case "Harden":
    			eHardenActive = true;
    			combatLog.setText(combatText + "\n Enemy increased its defenses");
    			break;
    		case "Stun":
    			playerStunned = true;
    			combatLog.setText(combatText + "\n Enemy stunned the player");
    			break;
    		case "Barrier":
    			eBarrierActive = true;
    			combatLog.setText(combatText + "\n Enemy activated Barrier");
    			break;
    		}
    	}
    	
    	if(eRendLeft > 0 && eRendLeft < 3) {
    		Player.loseHP(3);
            newLine();
            combatLog.setText(combatText + "\n Player hit with bleed for " + 3 + " damage");
            eRendLeft--;
    	}
    	
    	if(ePoisonLeft > 0 && ePoisonLeft < 3) {
    		Player.loseHP(3);
            newLine();
            combatLog.setText(combatText + "\n Player hit with poison for " + 3 + " damage");
            ePoisonLeft--;
    	}
    	
    	if(eRendLeft == 3)
    		eRendLeft--;
    	if(eEnrageLeft == 3)
    		eEnrageLeft--;
    	if(ePoisonLeft == 3)
    		ePoisonLeft--;
    		       
        if(Player.getHp() <= 0)
        	pDead = true;  
        if(enemyHP <= 0)
        	eDead = true;
        
        if(playerStunned) {
        	playerStunned = false;
        	enemyAttack(rand.nextInt(5));        	
        }        	
        else
        	playerTurn = true;
    }   
    
    private void setAbility(TextButton button, int abID, Label abUseLbl) {
    	switch(abID) {
    	case 1:   		
    		button.setLabel(abUseLbl);    		
    		button.setText(storage.swing.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.swing.getAbilityName()); 
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 2:
    		button.setLabel(abUseLbl);    		
    		button.setText(storage.rend.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.rend.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 3:
    		button.setLabel(abUseLbl);
    		button.setText(storage.whirlwind.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.whirlwind.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 4:
    		button.setLabel(abUseLbl);
    		button.setText(storage.groundBreaker.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.groundBreaker.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 5:
    		button.setLabel(abUseLbl);
    		button.setText(storage.bash.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.bash.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 6:
    		button.setLabel(abUseLbl);
    		button.setText(storage.barrier.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.barrier.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 7:
    		button.setLabel(abUseLbl);
    		button.setText(storage.harden.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.harden.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 8:
    		button.setLabel(abUseLbl);
    		button.setText(storage.mend.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.mend.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 9:
    		button.setLabel(abUseLbl);
    		button.setText(storage.hiltBash.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.hiltBash.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 10:
    		button.setLabel(abUseLbl);
    		button.setText(storage.barbedArmor.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.barbedArmor.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 11:
    		button.setLabel(abUseLbl);
    		button.setText(storage.enrage.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.enrage.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 12:
    		button.setLabel(abUseLbl);
    		button.setText(storage.riposte.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.riposte.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 13:
    		button.setLabel(abUseLbl);
    		button.setText(storage.stab.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.stab.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	case 14:
    		button.setLabel(abUseLbl);
    		button.setText(storage.decapitate.getAbilityName() + "\n (" + button.getLabel() + ")");
    		button.setName(storage.decapitate.getAbilityName());
    		button.getLabel().setAlignment(Align.center);
    		break;
    	}
    }
    
    private int setUsesLeft(int abID) {
    	int x = 0;
    	switch(abID) {
    	case 1:
    		x = storage.swing.getUsesLeft();
    		break;
    	case 2:
    		x = storage.rend.getUsesLeft();
    		break;
    	case 3:
    		x = storage.whirlwind.getUsesLeft();
    		break;
    	case 4:
    		x = storage.groundBreaker.getUsesLeft();
    		break;
    	case 5:
    		x = storage.bash.getUsesLeft();
    		break;
    	case 6:
    		x = storage.barrier.getUsesLeft();
    		break;
    	case 7:
    		x = storage.harden.getUsesLeft();
    		break;
    	case 8:
    		x = storage.mend.getUsesLeft();
    		break;
    	case 9:
    		x = storage.hiltBash.getUsesLeft();
    		break;
    	case 10:
    		x = storage.barbedArmor.getUsesLeft();
    		break;
    	case 11:
    		x = storage.enrage.getUsesLeft();
    		break;
    	case 12:
    		x = storage.riposte.getUsesLeft();
    		break;
    	case 13:
    		x = storage.stab.getUsesLeft();
    		break;
    	case 14:
    		x = storage.decapitate.getUsesLeft();
    		break;
    	}
    	
    	return x;
    }
    
    private String getAbilityName(int ID) {
    	switch(ID) {
    	case 1:
    		return "Swing";
    	case 2:
    		return "Rend";
    	case 3:
    		return "Whirlwind";
    	case 4:
    		return "Ground Breaker";
    	case 5:
    		return "Bash";
    	case 6:
    		return "Barrier";
    	case 7:
    		return "Harden";
    	case 8:
    		return "Mend";
    	case 9:
    		return "Hilt Bash";
    	case 10:
    		return "Barbed Armor";
    	case 11:
    		return "Enrage";
    	case 12:
    		return "Riposte";
    	case 13:
    		return "Stab";
    	case 14:
    		return "Decapitate";
    	default:
    		return "";
    	}
    }
    
    private void createComponents() {    	    	
    	playerHPLbl = new Label(Player.getHp() + "/" + Player.getMaxHP(), storage.labelStyle);
    	enemyHPLbl = new Label(enemyHP + "/" + enemyMaxHP, storage.labelStyle);
    	enemyNameLbl = new Label(enemyName, storage.labelStyle);
    	
    	combatLog = new Label("", storage.labelStyleSmol);
    	
    	attackBtn = new TextButton("Attack", storage.buttonStyleBig);
    	attackBtn.setColor(Color.GRAY);
    	attackBtn.setName("Attack");
    	attackBtn.addListener(new ClickListener() {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) {
    	    	onButtonClicked((TextButton) event.getListenerActor());  	        
    	        btnClicked = true;
    	    }});
    	
    	ability1 = new TextButton("Ability1", storage.buttonStyle);
    	if(GameScreen.newGame)
    		ab1Uses = setUsesLeft(Player.getAbID1());
    	ab1UseLbl = new Label("", storage.labelStyle);
		ab1UseLbl.setName("Uses left: " + ab1Uses);
    	setAbility(ability1, Player.getAbID1(), ab1UseLbl);    	
    	ability1.addListener(new ClickListener() {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) {
    	    	ab1Uses -= 1;
    	    	ab1UseLbl.setName("Uses left: " + ab1Uses);
    	    	ability1.setLabel(ab1UseLbl);
    	    	ability1.setText(getAbilityName(Player.getAbID1()) + "\n (" + ability1.getLabel() + ")");
    	    	ability1.getLabel().setAlignment(Align.center);
    	    	onButtonClicked((TextButton) event.getListenerActor());  
    	    	if(ab1Uses <= 0) {
    	    		ability1.setTouchable(Touchable.disabled);
    	    		ability1.setColor(Color.GRAY);
    	    	}
    	        btnClicked = true;
    	    }});
    	
    	ability2 = new TextButton("Ability2", storage.buttonStyle);
    	if(GameScreen.newGame)
    		ab2Uses = setUsesLeft(Player.getAbID2());
    	ab2UseLbl = new Label("", storage.labelStyle);
		ab2UseLbl.setName("Uses left: " + ab2Uses);
    	setAbility(ability2, Player.getAbID2(), ab2UseLbl);
    	ability2.addListener(new ClickListener() {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) {
    	    	ab2Uses -= 1;
    	    	ab2UseLbl.setName("Uses left: " + ab2Uses);
    	    	ability2.setLabel(ab2UseLbl);
    	    	ability2.setText(getAbilityName(Player.getAbID2()) + "\n (" + ability2.getLabel() + ")");
    	    	ability2.getLabel().setAlignment(Align.center);
    	    	onButtonClicked((TextButton) event.getListenerActor());  
    	    	if(ab2Uses <= 0) {
    	    		ability2.setTouchable(Touchable.disabled);
    	    		ability2.setColor(Color.GRAY);
    	    	}
    	        btnClicked = true;
    	    }});
    	
    	ability3 = new TextButton("Ability3", storage.buttonStyle);
    	if(GameScreen.newGame)
    		ab3Uses = setUsesLeft(Player.getAbID3());
    	ab3UseLbl = new Label("", storage.labelStyle);
    	ab3UseLbl.setName("Uses left: " + ab3Uses);
    	setAbility(ability3, Player.getAbID3(), ab3UseLbl);
    	ability3.addListener(new ClickListener() {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) {
    	    	ab3Uses -= 1;
    	    	ab3UseLbl.setName("Uses left: " + ab3Uses);
    	    	ability3.setLabel(ab3UseLbl);
    	    	ability3.setText(getAbilityName(Player.getAbID3()) + "\n (" + ability3.getLabel() + ")");
    	    	ability3.getLabel().setAlignment(Align.center);
    	    	onButtonClicked((TextButton) event.getListenerActor());  
    	    	if(ab3Uses <= 0) {
    	    		ability3.setTouchable(Touchable.disabled);
    	    		ability3.setColor(Color.GRAY);
    	    	}
    	        btnClicked = true;
    	    }});
    	
    	ability4 = new TextButton("Ability4", storage.buttonStyle);
    	if(GameScreen.newGame)
    		ab4Uses = setUsesLeft(Player.getAbID4());
    	ab4UseLbl = new Label("", storage.labelStyle);
    	ab4UseLbl.setName("Uses left: " + ab4Uses);
    	setAbility(ability4, Player.getAbID4(), ab4UseLbl);
    	ability4.addListener(new ClickListener() {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) {
    	    	ab4Uses -= 1;
    	    	ab4UseLbl.setName("Uses left: " + ab4Uses);
    	    	ability4.setLabel(ab4UseLbl);
    	    	ability4.setText(getAbilityName(Player.getAbID4()) + "\n (" + ability4.getLabel() + ")");
    	    	ability4.getLabel().setAlignment(Align.center);
    	    	onButtonClicked((TextButton) event.getListenerActor());   
    	    	if(ab4Uses <= 0) {
    	    		ability4.setTouchable(Touchable.disabled);
    	    		ability4.setColor(Color.GRAY);
    	    	}
    	        btnClicked = true;
    	    }});
        
    	endTurn = new TextButton("End Turn", storage.buttonStyle);
    	endTurn.setColor(Color.LIGHT_GRAY);
    	endTurn.addListener(new ClickListener() {
        	@Override
    	    public void clicked(InputEvent event, float x, float y) {      		
        		bleedHit();
        		if(!eDead) {
        			attackCount = 3;
        			if(eAbility3 == null)
            			enemyAttack(rand.nextInt(5));
            		else
            			enemyAttack(rand.nextInt(6));
        	        turnEnded = true;
        		}      		
    	    }});
    	
    	homeBtn = new TextButton("Return", storage.buttonStyle);
    	homeBtn.setColor(Color.LIGHT_GRAY);
    	homeBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			stage.clear();
    			if(Merchant.raid) {
    				GameScreen.newGame = false;
    				gameScreen.setCurrentState(GameScreen.FOREST_MAP);
    			}
    			else {
    				Player.gainCoins(Player.getRaidCoins());
    				Player.setRaidCoins(0);
    				Player.setStrength(3);
        			Player.setOneHandStr(0);
        			Player.setTwoHandStr(0);
        			Player.setMaxHP(70);
        			Player.setDmgResist(0);
        			Player.setWeaponDmg(0);
    				gameScreen.setCurrentState(GameScreen.HOME);
    			}    				
    	    }});
    	
    	reward1 = new TextButton("", storage.buttonStyle);
    	reward1.setColor(Color.LIGHT_GRAY);
    	reward1.addListener(new ClickListener() {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) {
    	    	rewardClick((TextButton) event.getListenerActor());
    	    	loot = reward1.getText().toString();
    	    	combatLog.setText(combatText + "\n Gained " + loot);
    	    }});
    	
    	reward2 = new TextButton("", storage.buttonStyle);
    	reward2.setColor(Color.LIGHT_GRAY);
    	reward2.addListener(new ClickListener() {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) {
    	    	rewardClick((TextButton) event.getListenerActor()); 
    	    	loot = reward2.getText().toString();
    	    	combatLog.setText(combatText + "\n Gained " + loot);
    	    }});
    	
    	reward3 = new TextButton("", storage.buttonStyle);
    	reward3.setColor(Color.LIGHT_GRAY);
    	reward3.addListener(new ClickListener() {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) {
    	    	Player.gainRaidCoins(enemyValue);  
    	    	loot = enemyValue + " Coins";
    	    	newLine();
	        	combatLog.setText(combatText + "\n Gained " + loot);
	        	reward1.setVisible(false);
	        	reward2.setVisible(false);
	        	reward3.setVisible(false);
    	    }});
    }
    
    private void componentParameters() {
    	playerHPLbl.setPosition(vp.getWorldWidth() / 3.7f, vp.getWorldHeight() / 1.07f);
    	
    	enemyHPLbl.setPosition(vp.getWorldWidth() / 1.7f, vp.getWorldHeight() / 1.07f);
    	
    	enemyNameLbl.setPosition(enemyHPLbl.getX(), enemyHPLbl.getY() + 50f);
    	
    	homeBtn.setSize(150, 100);
    	homeBtn.setPosition(vp.getWorldWidth() / 1.1f, vp.getWorldHeight() / 10f);
    	
    	combatLog.setColor(Color.BLACK);
    	Container<Label> container = new Container<Label>(combatLog);
    	container.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("white.png"))));
    	container.setBounds(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 1.8f, 600, 300);
    	container.align(Align.topLeft);
    	combatLog.setSize(600, 300);
    	
        attackBtn.setSize(550, 120); 
        attackBtn.setPosition(vp.getWorldWidth() / 5.2f - attackBtn.getWidth() / 2f,  vp.getWorldHeight() / 2.4f - attackBtn.getHeight() / 2f);
        
        endTurn.setSize(200, 80); 
        endTurn.setPosition(container.getX() + container.getWidth() / 2f - endTurn.getWidth() / 2f, vp.getWorldHeight() / 2.65f);
        
        ability1.setSize(250, 150); 
        ability1.setPosition(vp.getWorldWidth() / 8.7f - ability1.getWidth() / 2f, vp.getWorldHeight() / 3.8f - ability1.getHeight() / 2f);
        
        ability2.setSize(250, 150); 
        ability2.setPosition(vp.getWorldWidth() / 3.7f - ability2.getWidth() / 2f, vp.getWorldHeight() / 3.8f - ability2.getHeight() / 2f);
        
        ability3.setSize(250, 150); 
        ability3.setPosition(vp.getWorldWidth() / 8.7f - ability3.getWidth() / 2f, vp.getWorldHeight() / 9.8f - ability3.getHeight() / 2f);
        
        ability4.setSize(250, 150); 
        ability4.setPosition(vp.getWorldWidth() / 3.7f - ability4.getWidth() / 2f, vp.getWorldHeight() / 9.8f - ability4.getHeight() / 2f);
        
        reward1.setSize(250, 250);
        reward1.setPosition(vp.getWorldWidth() / 5f - reward1.getWidth() / 2f, vp.getWorldHeight() / 2f - reward1.getHeight() / 2f);
        
        reward2.setSize(250, 250);
        reward2.setPosition(vp.getWorldWidth() / 2f - reward2.getWidth() / 2f, vp.getWorldHeight() / 2f - reward2.getHeight() / 2f);
        
        reward3.setSize(250, 250);
        reward3.setPosition(vp.getWorldWidth() / 1.4f - reward3.getWidth() / 2f, vp.getWorldHeight() / 2f - reward3.getHeight() / 2f);
        
        stage.addActor(attackBtn);
        stage.addActor(endTurn);
        stage.addActor(playerHPLbl);
        stage.addActor(enemyHPLbl);
        stage.addActor(enemyNameLbl);
        stage.addActor(container);
        stage.addActor(ability1);
        stage.addActor(ability2);
        stage.addActor(ability3);
        stage.addActor(ability4);
        stage.addActor(homeBtn);       
    }
    
    private void createInventoryGrid() {
    	equippedItems = storage.getEquippedItems();
    	itemTable.defaults().size(100, 100);
    	itemTable.setName("itemTable");
    	
    	int itemIndex = 0;
    	
    	for(int i = 0; i < 2; i++) {
    		for(int j = 0; j < 7; j++) {
    			boolean emptySlot = false;
    			String itemName = "";
    			Texture slotTexture = null;
    			
    			if (itemIndex < equippedItems.size()) {
	                Items item = equippedItems.get(itemIndex);
	                slotTexture = setSlotImage(item.getItemName(), "Item");
	                itemIndex++;
	                itemName = item.getItemName();	                
	            }
    			 else {
 	                slotTexture = setSlotImage("", "");
 	                emptySlot = true;
 	            }
    			
    			final Image inventorySlotImage = new Image(slotTexture);
    			itemTable.add(inventorySlotImage).pad(3);
    			
    			if(emptySlot) {
	                emptySlot = false;
	                inventorySlotImage.setName("Empty");
	            }
	            else {
	                inventorySlotImage.setName(itemName);                    
	            } 
    			
    			final String item = itemName;
    			
    			inventorySlotImage.addListener(new ClickListener() {
	                @Override
	                public void clicked(InputEvent event, float x, float y) {
	                	if(!gameOver)
	                		handleInventoryClick(inventorySlotImage, item);
	                }				
	            });   			
    		}
    		
    		itemTable.row();
    	}
    	
    	itemTable.setPosition(vp.getWorldWidth() / 1.8f, vp.getWorldHeight() / 5.5f, Align.center);
	    stage.addActor(itemTable);
    }
    
    private void handleInventoryClick(Image slot, String itemName) {
    	if(playerTurn) {
    		if(!slot.getName().equals("Empty")) {
    			switch(itemName) {
        		case "Health Potion":
    	    		storage.equippedItems(storage.healthPot, "Remove");
    	    		newLine();
    				combatLog.setText(combatText + "\n Player used a Health potion");
    	    		Player.gainHP(Player.getMaxHP() / 5);
    				if(Player.getHp() > Player.getMaxHP())
    					Player.setHp(Player.getMaxHP());
    				attackCount--;
    	    		break;
    	    	case "Bomb":
    	    		storage.equippedItems(storage.bomb, "Remove");
    	    		newLine();
    				combatLog.setText(combatText + "\n Player threw a Bomb which dealt " + enemyHP / 10 + " damage");
    				enemyHP -= enemyHP / 10;
    				attackCount--;
    	    		break;
    	    	case "Attack Boost":
    	    		storage.equippedItems(storage.apBoost, "Remove");
    	    		Home.apBoost = true;
    	    		Player.gainWeaponDmg(5);
    	    		newLine();
    	    		combatLog.setText(combatText + "\n Player boosted their attack");
    	    		break;
    	    	case "Defense Boost":
    	    		storage.equippedItems(storage.dpBoost, "Remove");
    	    		Home.dpBoost = true;
    	    		Player.setDmgResist(Player.getDmgResist() + 5);
    	    		newLine();
    	    		combatLog.setText(combatText + "\n Player boosted their defenses");
    	    		break;
    	    	case "Health Boost":
    	    		storage.equippedItems(storage.hpBoost, "Remove");
    	    		Home.hpBoost = true;
    	    		Player.gainMaxHP(10);
    	    		if(Player.getHp() < Player.getMaxHP())
    	    			Player.gainHP(10);
    	    		if(Player.getHp() > Player.getMaxHP())
    	    			Player.setHp(Player.getMaxHP());
    	    		newLine();
    	    		combatLog.setText(combatText + "\n Player gained additional Health Points");
    	    		break;
    	    	case "Experience Boost":
    	    		storage.equippedItems(storage.expBoost, "Remove");
    	    		Home.expBoost = true;
    	    		newLine();
    	    		combatLog.setText(combatText + "\n Player activated an Experience Boost");
    	    		break;
    	    	default:   	       	    		
    	    		createAbilityGrid(itemName);
    	    		break;
    	    	}
        		
        		btnClicked = true;        		
        		itemTable.clear();
        		createInventoryGrid();
    		}	
    	}  	
    }
    
    private void createAbilityGrid(final String ability) {
    	abilitySwapTable.defaults().size(150, 100);
    	abilitySwapTable.setName("abilitySwapTable");
    	
    	TextButton abilityLabel1 = new TextButton(ability1.getName(), skin);
    	TextButton abilityLabel2 = new TextButton(ability2.getName(), skin);
    	TextButton abilityLabel3 = new TextButton(ability3.getName(), skin);
    	TextButton abilityLabel4 = new TextButton(ability4.getName(), skin);
    	
		abilitySwapTable.add(abilityLabel1).pad(3);
		abilitySwapTable.add(abilityLabel2).pad(3);		
		abilitySwapTable.row();
		abilitySwapTable.add(abilityLabel3).pad(3);
		abilitySwapTable.add(abilityLabel4).pad(3);	
		
		abilityLabel1.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				swapAbility(ability1, ability, ab1UseLbl);
			}
		});
		abilityLabel2.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				swapAbility(ability2, ability, ab2UseLbl);
			}
		});
		abilityLabel3.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				swapAbility(ability3, ability, ab3UseLbl);
			}
		});
		abilityLabel4.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				swapAbility(ability4, ability, ab4UseLbl);
			}
		});
    	
    	abilitySwapTable.setPosition(vp.getWorldWidth() / 1.15f, vp.getWorldHeight() / 3f, Align.center);
	    stage.addActor(abilitySwapTable);
    }
    
    private void swapAbility(final TextButton button, String ability, final Label abilityLabel) {    	
    	int uses = 0, ab = 0;
    	
    	if(button == ability1)
    		ab = 1;
    	else if(button == ability2)
    		ab = 2;
    	else if(button == ability3)
    		ab = 3;
    	else if(button == ability4)
    		ab = 4;
    		
    	switch(ability) {
    	case "Swing":
    		uses = handleAbility(ability, storage.itemSwing, storage.swing, abilityLabel, button);
    		break;
    	case "Rend":
    		uses = handleAbility(ability, storage.itemRend, storage.rend, abilityLabel, button);
    		break;
    	case "Whirlwind":
    		uses = handleAbility(ability, storage.itemWhirlwind, storage.whirlwind, abilityLabel, button);
    		break;
    	case "Ground Breaker":
    		uses = handleAbility(ability, storage.itemGroundBreaker, storage.groundBreaker, abilityLabel, button);
    		break;
    	case "Bash":
    		uses = handleAbility(ability, storage.itemBash, storage.bash, abilityLabel, button);
    		break;
    	case "Barrier":
    		uses = handleAbility(ability, storage.itemBarrier, storage.barrier, abilityLabel, button);
    		break;
    	case "Harden":
    		uses = handleAbility(ability, storage.itemHarden, storage.harden, abilityLabel, button);
    		break;
    	case "Mend":
    		uses = handleAbility(ability, storage.itemMend, storage.mend, abilityLabel, button);
    		break;
    	case "Hilt Bash":
    		uses = handleAbility(ability, storage.itemHiltBash, storage.hiltBash, abilityLabel, button);
    		break;
    	case "Barbed Armor":
    		uses = handleAbility(ability, storage.itemBarbedArmor, storage.barbedArmor, abilityLabel, button);
    		break;
    	case "Enrage":
    		uses = handleAbility(ability, storage.itemEnrage, storage.enrage, abilityLabel, button);
    		break;
    	case "Riposte":
    		uses = handleAbility(ability, storage.itemRiposte, storage.riposte, abilityLabel, button);
    		break;
    	case "Stab":
    		uses = handleAbility(ability, storage.itemStab, storage.stab, abilityLabel, button);
    		break;
    	case "Decapitate":
    		uses = handleAbility(ability, storage.itemDecapitate, storage.decapitate, abilityLabel, button);
    		break;
    	}
    	
    	switch(ab) {
    	case 1:
    		Player.setAbID1(storage.emptyAbility.getID());
    		ab1Uses = uses;
    		break;
    	case 2:
    		Player.setAbID2(storage.emptyAbility.getID());
    		ab2Uses = uses;
    		break;
    	case 3:
    		Player.setAbID3(storage.emptyAbility.getID());
    		ab3Uses = uses;
    		break;
    	case 4:
    		Player.setAbID4(storage.emptyAbility.getID());
    		ab4Uses = uses;
    		break;
    	}
    	
    	itemTable.clear();
    	createInventoryGrid();
    	abilitySwapTable.clear();
    }
    
    private int handleAbility(String ability, Items item, Abilities abilityObject, Label abilityLabel, TextButton button) {
        storage.equippedItems(item, "Remove");
        storage.swapAbilities(abilityObject);
        int uses = setUsesLeft(abilityObject.getID());
        abilityLabel.setName("Uses left: " + uses);
        setAbility(button, abilityObject.getID(), abilityLabel);
        return uses;
    }
    
    private Texture setSlotImage(String itemName, String type) {
    	if(type == "Item") {
			switch(itemName) {
			case "Health Potion":
				return Inventory.healthPotionTexture;
			case "Bomb":
				return Inventory.bombTexture;
			case "Swing":
				return Inventory.swingTexture;
			case "Rend":
				return Inventory.rendTexture;
			case "Whirlwind":
				return Inventory.whirlwindTexture;
			case "Ground Breaker":
				return Inventory.groundBreakerTexture;
			case "Bash":
				return Inventory.bashTexture;
			case "Barrier":
				return Inventory.barrierTexture;
			case "Harden":
				return Inventory.hardenTexture;
			case "Mend":
				return Inventory.mendTexture;
			case "Hilt Bash":
				return Inventory.hiltBashTexture;
			case "Barbed Armor":
				return Inventory.barbedArmorTexture;
			case "Enrage":
				return Inventory.enrageTexture;
			case "Riposte":
				return Inventory.riposteTexture;
			case "Stab":
				return Inventory.stabTexture;
			case "Decapitate":
				return Inventory.decapitateTexture;
			case "Attack Boost":
				return Inventory.apTexture;
			case "Defense Boost":
				return Inventory.dpTexture;
			case "Health Boost":
				return Inventory.hpTexture;
			case "Experience Boost":
				return Inventory.expTexture;
			default:
				return Inventory.inventorySlotTexture;
			}
		}
		else
			return Inventory.inventorySlotTexture;
    }
    
    public void newLine() {
    	combatText = combatLog.getText().toString();
        int lines = combatText.split("\n").length;
        if(lines >= 10) {
            int index = combatText.indexOf("\n");
            combatText = combatText.substring(index+1);
        }        
    }
    
    private void drawHealthBar() {
    	// Player Health bar
    	playerHealthBar.setProjectionMatrix(vp.getCamera().combined);
    	playerHealthBar.begin(ShapeRenderer.ShapeType.Filled);

    	playerHealthBar.setColor(Color.BLACK);
    	playerHealthBar.rect(playerHPLbl.getX() - playerHPLbl.getWidth() / 4f, playerHPLbl.getY() + 0.1f, 300, 30);

    	playerHealthBar.setColor(Color.RED);
        float barWidth = (float)Player.getHp() / (float)Player.getMaxHP();
        int width = (int)(barWidth * 300);
        width = (width / 10) * 10;
        if(width < 10)
        	width = 0;
        playerHealthBar.rect(playerHPLbl.getX() - playerHPLbl.getWidth() / 4f, playerHPLbl.getY() + 0.1f, width, 30);
        playerHealthBar.end(); 
        
        // Enemy Health bar
        enemyHealthBar.setProjectionMatrix(vp.getCamera().combined);
        enemyHealthBar.begin(ShapeRenderer.ShapeType.Filled);

        enemyHealthBar.setColor(Color.BLACK);
        enemyHealthBar.rect(enemyHPLbl.getX() - enemyHPLbl.getWidth() / 4f, enemyHPLbl.getY() + 0.1f, 300, 30);

        enemyHealthBar.setColor(Color.RED);
        barWidth = (float)enemyHP / (float)enemyMaxHP;
        width = (int)(barWidth * 300);
        width = (width / 10) * 10;
        if(width < 10)
        	width = 0;
        enemyHealthBar.rect(enemyHPLbl.getX() - enemyHPLbl.getWidth() / 4f, enemyHPLbl.getY() + 0.1f, width, 30);
        enemyHealthBar.end();
    }
    
    private void drawBuffs() {
    	float x1 = playerHPLbl.getX() - playerHPLbl.getWidth() / 4f;
    	float x2 = enemyHPLbl.getX() - enemyHPLbl.getWidth() / 4f;
    	float y = playerHPLbl.getY() - 30f;
    	buffBatch.begin();
    	
    	if(barrierActive)
    		buffBatch.draw(barrierBuffTex, x1, y, 25, 25);
    	
    	if(enrageLeft > 0)
    		buffBatch.draw(enrageBuffTex, x1 + 30, y, 25, 25);
    	
    	if(hardenActive)
    		buffBatch.draw(hardenBuffTex, x1 + 60, y, 25, 25);
    	
    	if(eBarrierActive)
    		buffBatch.draw(barrierBuffTex, x2, y, 25, 25);
    	
    	if(eEnrageLeft > 0)
    		buffBatch.draw(enrageBuffTex, x2 + 30, y, 25, 25);
    	
    	if(eHardenActive)
    		buffBatch.draw(hardenBuffTex, x2 + 60, y, 25, 25);
    	
    	buffBatch.end();
    }
    
    private void drawDebuffs() {
    	float x1 = playerHPLbl.getX() - playerHPLbl.getWidth() / 4f;
    	float x2 = enemyHPLbl.getX() - enemyHPLbl.getWidth() / 4f;
    	float y = playerHPLbl.getY() - 30f;
    	debuffBatch.begin();
    	
    	if(rendLeft > 0)
    		debuffBatch.draw(bleedBuffTex, x2 + 90, y, 25, 25);
    	
    	if(rendLeft > 0 && BerserkerSkillTree.poisonRend == 1)
    		debuffBatch.draw(poisonBuffTex, x2 + 120, y, 25, 25);
    	
    	if(enemyStunned)
    		debuffBatch.draw(stunBuffTex, x2 + 150, y, 25, 25);
    	
    	if(eRendLeft > 0)
    		debuffBatch.draw(bleedBuffTex, x1 + 90, y, 25, 25);
    	
    	if(ePoisonLeft > 0)
    		debuffBatch.draw(poisonBuffTex, x1 + 120, y, 25, 25);
    	
    	if(playerStunned)
    		debuffBatch.draw(stunBuffTex, x1 + 150, y, 25, 25);
    	
    	debuffBatch.end();
    }
    
    @Override
    public void dispose() {
        stage.dispose();
        skin.dispose();
        storage.font.dispose();
        charTexture.dispose();
        enemyTexture.dispose();
        charBatch.dispose();
        enemyBatch.dispose();
        weaponBatch.dispose();
        shieldBatch.dispose();
        helmetBatch.dispose();
        chestBatch.dispose();
        bootsBatch.dispose();
    }

	@Override
	public void show() {
		
	}
	
	@Override
    public void render(float delta) {	
		if(firstLoad)
			newEnemy();
		
		String weapon = null;
		String shield = null;
		String helmet = null;
		String chest = null;
		String boots = null;
		String weaponPiece = "Empty";
		String shieldPiece = "Empty";
		String helmetPiece = "Empty";
		String chestPiece = "Empty";
		String bootsPiece = "Empty";
		Texture weaponTexture = null;	
		Texture shieldTexture = null;
		Texture helmetTexture = null;	
		Texture chestTexture = null;
		Texture bootsTexture = null;			
		
		if(storage.getEquippedWeapons().size() > 0) {	
			Actor weaponItem = Inventory.characterTable.getChildren().get(3);
			Actor shieldItem = Inventory.characterTable.getChildren().get(4);
						
			weaponPiece = weaponItem.getName();
			shieldPiece = shieldItem.getName();
			
			if(!weaponPiece.equals("Empty")) {
				String[] words = weaponPiece.split(" ");
				weapon = words[words.length - 2] + " " + words[words.length - 1];				
			}
			if(!shieldPiece.equals("Empty")) {
				String[] words = shieldPiece.split(" ");
				shield = words[words.length - 2] + " " + words[words.length - 1];				
			}			
		}
		
		if(storage.getEquippedArmor().size() > 0) {
			Actor helmetTable = Inventory.characterTable.getChildren().get(0);
			Actor chestTable = Inventory.characterTable.getChildren().get(1);
			Actor bootsTable = Inventory.characterTable.getChildren().get(2);
			
			helmetPiece = helmetTable.getName();
			chestPiece = chestTable.getName();
			bootsPiece = bootsTable.getName();
			
			if(!helmetPiece.equals("Empty")) {
				String[] words = helmetPiece.split(" ");
				helmet = words[words.length - 2] + " " + words[words.length - 1];				
			}
			if(!chestPiece.equals("Empty")) {
				String[] words = chestPiece.split(" ");
				chest = words[words.length - 2] + " " + words[words.length - 1];				
			}
			if(!bootsPiece.equals("Empty")) {
				String[] words = bootsPiece.split(" ");
				boots = words[words.length - 2] + " " + words[words.length - 1];				
			}
		}
		
		charBatch.setProjectionMatrix(vp.getCamera().combined);
	    enemyBatch.setProjectionMatrix(vp.getCamera().combined);
	    gameOverBatch.setProjectionMatrix(vp.getCamera().combined);
	    weaponBatch.setProjectionMatrix(vp.getCamera().combined);
	    shieldBatch.setProjectionMatrix(vp.getCamera().combined);
	    helmetBatch.setProjectionMatrix(vp.getCamera().combined);
		chestBatch.setProjectionMatrix(vp.getCamera().combined);
		bootsBatch.setProjectionMatrix(vp.getCamera().combined);
		buffBatch.setProjectionMatrix(vp.getCamera().combined);
		debuffBatch.setProjectionMatrix(vp.getCamera().combined);
		
	    time += delta; // Increment time by frame time
	    rotationTime += delta;
	    float scaleFactor = 1f + 0.015f * (float) Math.sin(time * scaleSpeed); // Adjust scaling factor and speed here
	    float newHeightChar = heightChar * scaleFactor;
	    float newHeightEnemy = heightEnemy * scaleFactor;
	    float newHeightWeapon = heightWeapon * scaleFactor;
	    float newHeightShield = 210 * scaleFactor;
	    float newHeightChest = 125 * scaleFactor;
	    float newHeightBoots = 50 * scaleFactor;	 
	    float helmetMoveSpeed = 4f;
	    float helmetMoveAmount = 3f;
	    float newHelmetY = (vp.getWorldHeight() / 1.37f) + helmetMoveAmount * (float) Math.sin(time * helmetMoveSpeed);
	    
	    if (rotationTime >= rotationInterval) { // check if it's time to rotate again
	        weaponRotation += rotationSpeed;
	        rotationTime = 0f;
	        timer++;
	        if(timer >= 6) {
	        	playerAttack = false;
	        	timer = 0;
	        	weaponRotation = 20f;
	        }	        	
	    }
	    
	    mapBatch.begin();
		mapBatch.draw(mapTexture, 0, 0, GameScreen.SELECTED_WIDTH, GameScreen.SELECTED_HEIGHT);
		mapBatch.end();
		
		if(!gameOver) {
			charSprite.setSize(275, newHeightChar); // Set new height
		    charSprite.setY(baseYChar); // Adjust Y to keep the sprite's bottom at the same position
		    enemySprite.setSize(500,  newHeightEnemy);
		    enemySprite.setY(baseYEnemy);
		}
		else {
			charSprite.setSize(275, 450); // Set new height
		    enemySprite.setSize(500,  500);
		}
	    
	    
	    // Character sprite
    	charBatch.begin();
	    charSprite.draw(charBatch); // Draw sprite with adjusted scale
	    charBatch.end();    		
	    
	    if(!helmetPiece.equals("Empty")) {
			switch(helmet) {
			case "Iron Helmet":
				helmetTexture = Inventory.eIronHelmetTexture;
				break;
			case "Bronze Helmet":
				helmetTexture = Inventory.eBronzeHelmetTexture;
				break;
			case "Steel Helmet":
				helmetTexture = Inventory.eSteelHelmetTexture;
				break;
			}			
			
			helmetSprite = new Sprite(helmetTexture);
		    helmetSprite.setOrigin(0, 0);
		    if(!gameOver)
		    	helmetSprite.setPosition(vp.getWorldWidth() / 29f, newHelmetY);
		    else
		    	helmetSprite.setPosition(vp.getWorldWidth() / 29f, vp.getWorldHeight() / 1.37f);		    	
		    helmetSprite.setSize(290, 290);

		    helmetBatch.begin();
		    helmetSprite.draw(helmetBatch);
		    helmetBatch.end();
		}
		
		if(!chestPiece.equals("Empty")) {
			switch(chest) {
			case "Iron Chest":
				chestTexture = Inventory.eIronChestTexture;
				break;
			case "Bronze Chest":
				chestTexture = Inventory.eBronzeChestTexture;
				break;
			case "Steel Chest":
				chestTexture = Inventory.eSteelChestTexture;
				break;
			}
			
			chestSprite = new Sprite(chestTexture);
			chestSprite.setPosition(vp.getWorldWidth() / 20f, vp.getWorldHeight() / 1.79f);				
			if(!gameOver)
				chestSprite.setSize(275, newHeightChest);
			else
				chestSprite.setSize(275, 125);			
			chestBatch.begin();
			chestSprite.draw(chestBatch);		
			chestBatch.end();
		}
		
		if(!bootsPiece.equals("Empty")) {
			switch(boots) {
			case "Iron Boots":
				bootsTexture = Inventory.eIronBootsTexture;
				break;
			case "Bronze Boots":
				bootsTexture = Inventory.eBronzeBootsTexture;
				break;
			case "Steel Boots":
				bootsTexture = Inventory.eSteelBootsTexture;
				break;
			}
			
			bootsSprite = new Sprite(bootsTexture);
			bootsSprite.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.995f);				
			if(!gameOver)
				bootsSprite.setSize(133, newHeightBoots);
			else
				bootsSprite.setSize(133, 50);		
			bootsBatch.begin();
			bootsSprite.draw(bootsBatch);		
			bootsBatch.end();
		}
	    
	    // Weapon sprite
	    if(!weaponPiece.equals("Empty")) {
			switch(weapon) {
			case "Iron Greataxe":
				weaponTexture = Inventory.eIronGreataxeTexture;
				twoHand = true;
				break;
			case "Wooden Greataxe":
				weaponTexture = Inventory.eWoodenGreataxeTexture;
				twoHand = true;
				break;
			case "Wooden Axe":
				weaponTexture = Inventory.eWoodenAxeTexture;
				twoHand = false;
				break;
			case "Iron Axe":
				weaponTexture = Inventory.eIronAxeTexture;
				twoHand = false;
				break;
			case "Bronze Greataxe":
				weaponTexture = Inventory.eBronzeGreataxeTexture;
				twoHand = true;
				break;
			case "Bronze Axe":
				weaponTexture = Inventory.eBronzeAxeTexture;
				twoHand = false;
				break;
			case "Steel Greataxe":
				weaponTexture = Inventory.eSteelGreataxeTexture;
				twoHand = true;
				break;
			case "Steel Axe":
				weaponTexture = Inventory.eSteelAxeTexture;
				twoHand = false;
				break;
			}
			
			weaponSprite = new Sprite(weaponTexture);
			weaponSprite.setOrigin(0, 0);
			
			if(twoHand) {
				heightWeapon = 350;
				weaponSprite.setPosition(vp.getWorldWidth() / 7f, vp.getWorldHeight() / 2f);
				if(!gameOver)
					weaponSprite.setSize(310,  newHeightWeapon);
				else
					weaponSprite.setSize(310,  heightWeapon);
				weaponSprite.setY(weaponSprite.getY());
			}
			else {
				heightWeapon = 190;
				weaponSprite.setPosition(vp.getWorldWidth() / 5.25f, vp.getWorldHeight() / 1.75f);
				if(!gameOver)
					weaponSprite.setSize(190,  newHeightWeapon);
				else
					weaponSprite.setSize(190,  heightWeapon);
				weaponSprite.setY(weaponSprite.getY());			
			}
					
			if(!playerAttack) {
				weaponBatch.begin();
				if(twoHand)
					weaponSprite.setRotation(20f);
				else
					weaponSprite.setRotation(15f);
				weaponSprite.draw(weaponBatch);		
				weaponBatch.end();
			}
			else {              
	            weaponSprite.setRotation(weaponRotation);
	            weaponBatch.begin();
	            weaponSprite.draw(weaponBatch);
	            weaponBatch.end();
	        }			
		}
	    
	    if(!shieldPiece.equals("Empty")) {
	    	switch(shield) {
	    	case "Wooden Shield":
				shieldTexture = Inventory.eWoodenShieldTexture;
				break;
			case "Iron Shield":
				shieldTexture = Inventory.eIronShieldTexture;
				break;
			case "Bronze Shield":
				shieldTexture = Inventory.eBronzeShieldTexture;
				break;
			case "Steel Shield":
				shieldTexture = Inventory.eSteelShieldTexture;
				break;
	    	}
	    	
	    	shieldSprite = new Sprite(shieldTexture);
	    	shieldSprite.setOrigin(0, 0);
	    	shieldSprite.setPosition(vp.getWorldWidth() / 18f, vp.getWorldHeight() / 1.9f);
	    	if(!gameOver)
	    		shieldSprite.setSize(160, newHeightShield);
	    	else
	    		shieldSprite.setSize(160, 210);
	    	shieldSprite.setY(shieldSprite.getY());
	    	
	    	shieldBatch.begin();
	    	shieldSprite.draw(shieldBatch);
	    	shieldBatch.end();
	    }
		
	    // Enemy sprite
	    if(!turnEnded) {
	    	enemyClickTime = 0f;
	    	enemyBatch.begin();
			enemySprite.draw(enemyBatch);
			enemyBatch.end();
	    }
	    else {
	    	if(enemyClickTime < 0.1f) {
	    		enemyBatch.begin();
	    		enemyBatch.draw(enemyTexture, vp.getWorldWidth() / 1.45f, vp.getWorldHeight() / 2f, 500, 500);
	    		enemyBatch.end();
	    		enemyClickTime += delta;
	    	}
	    	else
	    		turnEnded = false;	    		
	    }
	    
	    drawHealthBar();
	    drawBuffs();
	    drawDebuffs();
	    
    	update();
        stage.act();
        stage.draw();
    }

	@Override
	public void resize(int width, int height) {
		charBatch.setProjectionMatrix(vp.getCamera().combined);
	    enemyBatch.setProjectionMatrix(vp.getCamera().combined);
	    gameOverBatch.setProjectionMatrix(vp.getCamera().combined);
	    weaponBatch.setProjectionMatrix(vp.getCamera().combined);
	    shieldBatch.setProjectionMatrix(vp.getCamera().combined);
	    helmetBatch.setProjectionMatrix(vp.getCamera().combined);
		chestBatch.setProjectionMatrix(vp.getCamera().combined);
		bootsBatch.setProjectionMatrix(vp.getCamera().combined);
		mapBatch.setProjectionMatrix(vp.getCamera().combined);
		playerHealthBatch.setProjectionMatrix(vp.getCamera().combined);
		enemyHealthBatch.setProjectionMatrix(vp.getCamera().combined);
		buffBatch.setProjectionMatrix(vp.getCamera().combined);
		debuffBatch.setProjectionMatrix(vp.getCamera().combined);
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