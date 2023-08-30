package scenes;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;
import player.Storage;
import player.Player;

public class FightScene implements Screen{
	Skin skin;
    Viewport vp;
    String combatText;
    Random rand = new Random();
    private Game game;
    public Stage stage;
    private TextButton attackBtn, endTurn, ability1, ability2, ability3, ability4, homeBtn;
    private Label playerHP, enemyHP, combatLog;
    private int eHP = 50, eDmg = 2;
    private boolean pDead, eDead, btnClicked;
    private Storage storage;
    private static int ab1Uses, ab2Uses, ab3Uses, ab4Uses;
    private Label ab1UseLbl, ab2UseLbl, ab3UseLbl, ab4UseLbl;
    private GameScreen gameScreen;
    private int rendLeft, attackCount = 3, eAttackCount = 0, bludgeonCount = 0, doubleSwingCount = 0;
    private boolean enemyStunned, barrierActive, hardenActive, firstAttack = false;

    public FightScene(Viewport viewport, Game game, GameScreen gameScreen) {
    	this.gameScreen = gameScreen;
    	this.game = game;
        stage = new Stage(viewport);
        vp = viewport;
        Gdx.input.setInputProcessor(stage);  // Set the stage to process inputs
        skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
        storage = Storage.getInstance();
        
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
        
        GameScreen.newGame = false;
    }
    
    public void update() {
    	if(!pDead && !eDead && btnClicked) {
    		playerHP.setText("Player HP: " + Player.getHp() + "/" + Player.getMaxHP());
        	enemyHP.setText("Enemy HP: " + eHP + "/" + 50); 
        	btnClicked = false;
    	}
    	
    	if(attackCount <= 0) {
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
	        if(pDead)
	        	combatLog.setText(combatText + "\n Player died");
	        else
	        	combatLog.setText(combatText + "\n Enemy died");
	        
	        pDead = eDead = false; 		
    	} 		
    }
    
    private void onButtonClicked(TextButton button) {
    	attackCount--;
    	
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
    		if(rand.nextInt(4) != 0) {
    			barrierActive = true;
    			newLine();
    			combatLog.setText(combatText + "\n Player activated Barrier");
    		}
    		else {
    			newLine();
    			combatLog.setText(combatText + "\n Player failed to use Barrier");
    		}  			
    		break;
    	case "Harden":
    		if(rand.nextInt(4) != 0) {
    			hardenActive = true;
    			newLine();
    			combatLog.setText(combatText + "\n Player activated Harden");
    		}
    		else {
    			newLine();
    			combatLog.setText(combatText + "\n Player failed to use Harden");
    		}  			
    		break;
    	}
    }
    
    private void playerAttack(int x) {    	
    	int temp = 0;
    	boolean hit = false;
    	switch(x) {
    	case 0:
    		temp += Player.getStrength();    		 
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
    	case 6:
    		barrierActive = true;
    		break;
    	case 7:
    		hardenActive = true;
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
        	if(firstAttack)
        		temp *= 2;
        	eHP -= temp;
        	if(x == 0)
        		combatLog.setText(combatText + "\n Player hit the enemy for " + temp + " damage");
        	else if(x == 1 || x == 3 || x == 4 || x == 5)
        		combatLog.setText(combatText + "\n " + getAbilityName(x) + " hit the enemy for " + temp + " damage");
        	
        	// Life steal mastery
        	if(BerserkerSkillTree.lifeSteal == 1) {
        		if(Player.getHp() < Player.getMaxHP()) {
        			Player.gainHP(temp / 3);
        			if(Player.getHp() > Player.getMaxHP())
        				Player.setHp(Player.getMaxHP());
        		}      		
        	}      		
        }
        
        // Bleed hits after a succesful attack
        if(rendLeft > 0 && x != 6 && x != 7) {
        	temp = storage.rend.getAttackPower() + BerserkerSkillTree.rendMastery;
        	eHP -= temp;
        	newLine();
        	combatLog.setText(combatText + "\n Rend hit the enemy for " + temp + " damage");
        	
        	// Poison Rend mastery
        	if(BerserkerSkillTree.poisonRend == 1) {
            	eHP -= storage.rend.getAttackPower();
        		newLine();
            	combatLog.setText(combatText + "\n Enemy hit with poison for " + storage.rend.getAttackPower() + " damage");
        	}
   	
        	rendLeft--;
        }        
        
        if(eHP <= 0) {
        	eDead = true;
        	Player.gainExp(20);
        	Player.checkExp();
        } 
        
        firstAttack = false;
    }
    
    private void enemyAttack() {
    	// Increase attack counter for the Block Aura mastery
    	if(!barrierActive && BerserkerSkillTree.blockAura == 1)
    		eAttackCount++;
    	
    	if(eAttackCount == 3) {
    		barrierActive = true;
    		eAttackCount = 0;
    	}    		
    	
    	int temp = eDmg;
    	if(hardenActive) {
    		hardenActive = false;
    		temp = temp / 2;
    	}  		
    	
    	// Lower damage via damage resist stat
    	temp -= Player.getDmgResist();
    	if(temp <= 0)
    		temp = 1;
    	
    	if(!barrierActive && !enemyStunned) {
    		Player.loseHP(temp);
            newLine();
            combatLog.setText(combatText + "\n Enemy attacked for " + temp + " damage");
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
    		System.out.println(barrierActive);
    	}
    	else if(enemyStunned){
    		newLine();
    		combatLog.setText(combatText + "\n Enemy stunned and cannot attack");
    		enemyStunned = false;
    	}
    		       
        if(Player.getHp() <= 0)
        	pDead = true;   	
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
    	default:
    		return "";
    	}
    }
    
    private void createComponents() {
    	playerHP = new Label("Player HP: " + Player.getHp() + "/" + Player.getMaxHP(), storage.labelStyle);
    	enemyHP = new Label("Enemy HP: " + eHP + "/" + 50, storage.labelStyle);
    	
    	combatLog = new Label("", storage.labelStyle);
    	
    	attackBtn = new TextButton("Attack", storage.buttonStyle);
    	attackBtn.setName("Attack");
    	attackBtn.addListener(new ClickListener() {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) {
    	    	onButtonClicked((TextButton) event.getListenerActor());  	        
    	        btnClicked = true;
    	    }});
    	
    	ability1 = new TextButton("Ability1", storage.buttonStyle);
    	if(gameScreen.newGame)
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
    	if(gameScreen.newGame)
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
    	if(gameScreen.newGame)
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
    	if(gameScreen.newGame)
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
        		attackCount = 3;
        		enemyAttack();
    	        btnClicked = true;
    	    }});
    	
    	homeBtn = new TextButton("Home", storage.buttonStyle);
    	homeBtn.setColor(Color.RED);
    	homeBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			stage.clear();
        		gameScreen.setCurrentState(GameScreen.HOME);
    	    }});
    }
    
    private void componentParameters() {
    	playerHP.setPosition(vp.getWorldWidth() / 1.7f, vp.getWorldHeight() / 2.5f);
    	
    	enemyHP.setPosition(vp.getWorldWidth() / 1.7f, vp.getWorldHeight() / 1.07f);
    	
    	homeBtn.setSize(150, 100);
    	homeBtn.setPosition(vp.getWorldWidth() / 1.7f, vp.getWorldHeight() / 5f);
    	
    	combatLog.setColor(Color.BLACK);
    	Container<Label> container = new Container<Label>(combatLog);
    	container.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("white.png"))));
    	container.setBounds(vp.getWorldWidth() / 20f, vp.getWorldHeight() / 1.85f, 800, 450);
    	container.align(Align.topLeft);
    	combatLog.setWidth(container.getWidth());
    	
        attackBtn.setSize(550, 150); 
        attackBtn.setPosition(vp.getWorldWidth() / 5.2f - attackBtn.getWidth() / 2f,  vp.getWorldHeight() / 2.3f - attackBtn.getHeight() / 2f);
        
        endTurn.setSize(200, 80); 
        endTurn.setPosition(vp.getWorldWidth() / 1.7f, vp.getWorldHeight() / 3.3f);
        
        ability1.setSize(250, 150); 
        ability1.setPosition(vp.getWorldWidth() / 8.7f - ability1.getWidth() / 2f, vp.getWorldHeight() / 3.7f - ability1.getHeight() / 2f);
        
        ability2.setSize(250, 150); 
        ability2.setPosition(vp.getWorldWidth() / 3.7f - ability2.getWidth() / 2f, vp.getWorldHeight() / 3.7f - ability2.getHeight() / 2f);
        
        ability3.setSize(250, 150); 
        ability3.setPosition(vp.getWorldWidth() / 8.7f - ability3.getWidth() / 2f, vp.getWorldHeight() / 9.7f - ability3.getHeight() / 2f);
        
        ability4.setSize(250, 150); 
        ability4.setPosition(vp.getWorldWidth() / 3.7f - ability4.getWidth() / 2f, vp.getWorldHeight() / 9.7f - ability4.getHeight() / 2f);
        
        stage.addActor(attackBtn);
        stage.addActor(endTurn);
        stage.addActor(playerHP);
        stage.addActor(enemyHP);
        stage.addActor(container);
        stage.addActor(ability1);
        stage.addActor(ability2);
        stage.addActor(ability3);
        stage.addActor(ability4);
        stage.addActor(homeBtn);       
    }
    
    public void newLine() {
    	combatText = combatLog.getText().toString();
        int lines = combatText.split("\n").length;
        if(lines >= 13) {
            int index = combatText.indexOf("\n");
            combatText = combatText.substring(index+1);
        }        
    }
    
    @Override
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