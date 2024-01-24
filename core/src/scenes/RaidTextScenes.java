package scenes;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;

import player.Player;
import storage.Storage;

public class RaidTextScenes implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private GameScreen gameScreen; 
	private TextButton option1, option2, option3, backBtn;
	private Label title, text, rewards;
	private Random rand = new Random();
	private int encNum, btnSelected;
	private float centerX;
	public static boolean mimic, enrage, weaken, poison;
	private SpriteBatch mapBatch = new SpriteBatch();
	private Texture mapTexture;
	
	public RaidTextScenes(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		storage.createFont();
		centerX = vp.getWorldWidth() / 2f;
//		GameScreen.newGame = false;
		
		if(ForestMap.encounter == 2)
			mapTexture = Storage.assetManager.get("maps/TreasureEncounterScreen.png", Texture.class);
		else
			mapTexture = Storage.assetManager.get("maps/RandomEncounterScreen.png", Texture.class);
		mapTexture.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
		
		createComponents();
		setScene();
		setEncounter();
	}
	
	private void setScene() {
		if(ForestMap.encounter == 2) {
			title.setText("Treasure");
			option1.setVisible(true);
			option2.setVisible(true);
		}
		else {
			title.setText("Random Encounter");
			option1.setVisible(true);
			option2.setVisible(true);
			option3.setVisible(true);
		}
	}
	
	private void setEncounter() {
		text.setPosition(centerX - text.getWidth() / 2, vp.getWorldHeight() / 4f);
		
		if(ForestMap.encounter == 2) {
			encNum = rand.nextInt(1, 12);
			if(encNum != 11) {
				option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
				option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
			}			
		}			
		else
			encNum = rand.nextInt(31, 66);
		
		switch(encNum) {
		case 1:
			text.setText("You found a treasure chest, are you going to open it?");
			option1.setText("Yes");
			option2.setText("No");
			break;
		case 2:
			text.setText("You found a treasure chest, are you going to open it?");
			option1.setText("Yes");
			option2.setText("No");
			break;
		case 3:
			text.setText("You found a treasure chest, are you going to open it?");
			option1.setText("Yes");
			option2.setText("No");
			break;
		case 4:
			text.setText("As you arrive at a cave, you start feeling tired. You could use a quick "
					+ "power nap to regain your sense. Are you gonna rest?");
			option1.setText("Yes");
			option2.setText("No");
			break;
		case 5:
			text.setText("As you arrive at a cave, you start feeling tired. You could use a quick "
					+ "power nap to regain your sense. Are you gonna rest?");
			option1.setText("Yes");
			option2.setText("No");
			break;
		case 6:
			option1.setPosition(vp.getWorldWidth() / 2f - option1.getWidth() / 2, vp.getWorldHeight() / 10f);
			text.setText("You stumble upon a shiny object on the ground.");
			option1.setText("Pick it up");
			option2.setVisible(false);
			break;
		case 7:
			text.setText("You find a mysterious vial with unknown properties, will you drink it?");
			option1.setText("Yes");
			option2.setText("No");
			break;
		case 8:
			text.setText("You find a mysterious vial with unknown properties, will you drink it?");
			option1.setText("Yes");
			option2.setText("No");
			break;
		case 9:
			text.setText("You find a scroll floating in the wind and manage to snatch it out of the air");
			option1.setVisible(false);
			option2.setVisible(false);
			rewards.setText("+ 5 Experience Points");
			Player.gainExp(5);
			Player.checkExp();
			backBtn.setVisible(true);
			break;
		case 10:
			text.setText("You find a strange deformity, offering you a random boost, will you take it?");
			option1.setText("Yes");
			option2.setText("No");
			break;
		case 11:
			int x1 = rand.nextInt(3);
			int x2 = rand.nextInt(3);
			int x3 = rand.nextInt(3);
			option3.setVisible(true);
			text.setText("You find an odd vending machine, offering you an ability");	
			if(x1 == 0)
				option1.setText("Swing");
			else if(x1 == 1)
				option1.setText("Rend");
			else if(x1 == 2)
				option1.setText("Whirlwind");
			
			if(x2 == 0)
				option1.setText("Hilt Bash");
			else if(x2 == 1)
				option1.setText("Ground Breaker");
			else if(x2 == 2)
				option1.setText("Bash");
			
			if(x3 == 0)
				option1.setText("Mend");
			else if(x3 == 1)
				option1.setText("Barrier");
			else if(x3 == 2)
				option1.setText("Barbed Armor");
			break;
		case 31:
			text.setText("You stumble upon a suspicious looking fella and he tells you he's a "
					+ "travelling merchant, what will you do?");
			option1.setText("Run away");
			option2.setText("Ask him about his wares");
			option3.setText("Kill him (stranger danger)");
			break;
		case 32:
			option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
			option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
			text.setText("You notice that there's a light shining from a crevice, will you "
					+ "attempt to investigate what it is?");
			option1.setText("Yes");
			option2.setText("No");
			option3.setVisible(false);
			break;
		case 33:
			text.setText("You stumble upon a suspicious looking fella and he tells you he's a "
					+ "travelling merchant, what will you do?");
			option1.setText("Run away");
			option2.setText("Ask him about his wares");
			option3.setText("Kill him (stranger danger)");
			break;
		case 34:
			option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
			option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
			text.setText("You notice that there's a light shining from a crevice, will you "
					+ "attempt to investigate what it is?");
			option1.setText("Yes");
			option2.setText("No");
			option3.setVisible(false);
			break;
		case 35:
			text.setText("You don't notice a pile of grass on the ground as you walk towards "
					+ "it, resulting in the ground caving in and you falling into a spike trap.");
			rewards.setText("- 5 Health Points");
			Player.loseHP(5);
			backBtn.setVisible(true);
			option1.setVisible(false);
			option2.setVisible(false);
			option3.setVisible(false);
			break;
		case 36:
			option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
			option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
			text.setText("You hear shrieking in the distance, it sounded like someone in "
					+ "distress. Will you go check it out?");
			option1.setText("Yes");
			option2.setText("No");
			option3.setVisible(false);
			break;
		case 37:
			option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
			option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
			text.setText("You hear shrieking in the distance, it sounded like someone in "
					+ "distress. Will you go check it out?");
			option1.setText("Yes");
			option2.setText("No");
			option3.setVisible(false);
			break;
		case 38:
			text.setText("You find a box hanging from a tree - it certainly looks suspicious. "
					+ "You ask yourself why a box would be hanging from a tree.");
			option1.setText("Climb up and get the box");
			option2.setText("Throw rocks at it");
			option3.setText("Burn the tree");
			break;
		case 39:
			text.setText("You find a box hanging from a tree - it certainly looks suspicious. "
					+ "You ask yourself why a box would be hanging from a tree.");
			option1.setText("Climb up and get the box");
			option2.setText("Throw rocks at it");
			option3.setText("Burn the tree");
			break;
		case 40:
			text.setText("You find a box hanging from a tree - it certainly looks suspicious. "
					+ "You ask yourself why a box would be hanging from a tree.");
			option1.setText("Climb up and get the box");
			option2.setText("Throw rocks at it");
			option3.setText("Burn the tree");
			break;
		case 41:
			option1.setPosition(vp.getWorldWidth() / 2f - option1.getWidth() / 2, vp.getWorldHeight() / 10f);
			text.setText("You sit down to take a breather, falling asleep, only to be "
					+ "rudely awoken by an angry figure standing in front of you");
			option1.setText("Squint eyes to see who it is");
			option2.setVisible(false);
			option3.setVisible(false);
			break;
		case 42:
			option1.setPosition(vp.getWorldWidth() / 2f - option1.getWidth() / 2, vp.getWorldHeight() / 10f);
			text.setText("You sit down to take a breather, falling asleep, only to be "
					+ "awoken by a figure standing in front of you");
			option1.setText("Squint eyes to see who it is");
			option2.setVisible(false);
			option3.setVisible(false);
			break;
		case 43:
		case 44:
		case 45:
		case 46:
			option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
			option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
			text.setText("You find an abandoned building, but get an eerie feeling upon approaching, what will you do?");
			option3.setVisible(false);
			option1.setText("Explore");
			option2.setText("Go past it");
			break;
		case 47:
		case 48:
		case 49:
		case 50:
			text.setText("You stumble upon a deformed figure, wearing a wizard's hat, you're unsure of whether they're good or bad");
			option1.setText("Talk to them");
			option2.setText("Flick the hat off of their head");
			option3.setText("Kill them");
			break;
		case 51:
		case 52:
		case 53:
			text.setText("You meet an entity, offering to train you, what will you do?");
			option1.setText("Train with them");
			option2.setText("Attack them");
			option3.setText("Reject their offer");
			break;
		case 54:
		case 55:
		case 56:
			option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
			option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
			text.setText("You find a worn down training dummy, will you take some time to train yourself?");
			option1.setText("Yes");
			option2.setText("No");
			option3.setVisible(false);
			break;
		case 57:
			option1.setPosition(vp.getWorldWidth() / 2f - option1.getWidth() / 2, vp.getWorldHeight() / 10f);
			text.setText("You're fatigued, which causes you to trip and fall");
			option1.setText("Pick yourself back up");
			option2.setVisible(false);
			option3.setVisible(false);
			break;
		case 58:
			option1.setPosition(vp.getWorldWidth() / 2f - option1.getWidth() / 2, vp.getWorldHeight() / 10f);
			text.setText("You find a river, finally you can take a short break");
			option1.setText("Sit down");
			option2.setVisible(false);
			option3.setVisible(false);
			break;
		case 59:
		case 60:
			option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
			option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
			text.setText("You fall down a hole in the ground and are confused as to what to do");
			option3.setVisible(false);
			option1.setText("Break down and cry");
			option2.setText("Climb out of it");
			break;
		case 61:
		case 62:
		case 63:
		case 64:
			option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
			option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
			text.setText("You find some weights, they're all rusty, but potentially useful");
			option3.setVisible(false);
			option1.setText("Train");
			option2.setText("Leave them");
			break;
		case 65:
			text.setText("You stumble upon an abnormally strong figure, that offers to train you");
			option1.setText("Food training");
			option2.setText("Run laps");
			option3.setText("Be a punching dummy");
			break;
		}
	}
	
	private void createComponents() {
		title = new Label("", storage.labelStyleBiggerBlack);
	    title.setSize(vp.getWorldWidth(), 100);
	    title.setAlignment(Align.center);
	    title.setPosition(0, vp.getWorldHeight() / 1.2f);
	    stage.addActor(title);
	    
	    text = new Label("", storage.labelStyleBigBlack);
	    text.setSize(1200, 600);
	    text.setWrap(true);
	    text.setAlignment(Align.center);
	    text.setPosition(centerX - text.getWidth() / 2, vp.getWorldHeight() / 4f);
	    stage.addActor(text);
	    
	    rewards = new Label("", storage.labelStyleBigBlack);
	    rewards.setSize(800, 200);
	    rewards.setWrap(true);
	    rewards.setPosition(centerX - text.getWidth() / 2, vp.getWorldHeight() / 4f); 
	    stage.addActor(rewards);
		
		option1 = new TextButton("", storage.buttonStyle);
		option1.setColor(Color.LIGHT_GRAY);
		option1.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			btnSelected = 1;
    			optionActions();
    	    }});
		option1.setSize(500, 100);
		option1.setPosition(vp.getWorldWidth() / 20f, vp.getWorldHeight() / 10f);
		stage.addActor(option1);
		
		option2 = new TextButton("", storage.buttonStyle);
		option2.setColor(Color.LIGHT_GRAY);
		option2.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			btnSelected = 2;
    			optionActions();
    	    }});
		option2.setSize(500, 100);
		option2.setPosition(vp.getWorldWidth() / 1.58f - option2.getWidth(), vp.getWorldHeight() / 10f);
		stage.addActor(option2);
		
		option3 = new TextButton("", storage.buttonStyle);
		option3.setColor(Color.LIGHT_GRAY);
		option3.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			btnSelected = 3;
    			optionActions();
    	    }});
		option3.setSize(500, 100);
		option3.setPosition(vp.getWorldWidth() / 1.05f - option3.getWidth(), vp.getWorldHeight() / 10f);
		stage.addActor(option3);
		
		backBtn = new TextButton("Return", storage.buttonStyle);
		backBtn.setColor(Color.LIGHT_GRAY);
		backBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.switchToNewState(GameScreen.FOREST_MAP);
    	    }});
		backBtn.setSize(250, 100);
		backBtn.setPosition(vp.getWorldWidth() / 2f - backBtn.getWidth() / 2, vp.getWorldHeight() / 10f);
		backBtn.setVisible(false);
		stage.addActor(backBtn);	

		option3.setVisible(false);
	}

	private void optionActions() {
		String lostItem;
		text.setPosition(centerX - text.getWidth() / 2, vp.getWorldHeight() / 3f);
		
		backBtn.setVisible(true);
		option1.setVisible(false);
		option2.setVisible(false);
		option3.setVisible(false);
		
		// Option 1
		if(btnSelected == 1) {
			switch(encNum) {
			case 1:
				text.setText("You found 5 gold and a Health potion in the chest!");
				Player.gainRaidCoins(5);
				if(storage.getEquippedItems().size() < 14) {
					storage.equippedItems(storage.healthPot, "Add");
					rewards.setText("+ 5 Coins \n" + "+ 1 Health Potion");
				}							
				else
					rewards.setText("+ 5 Coins");
				break;
			case 2:
				text.setText("It turned out to be a mimic chest, you lost 5HP");
				Player.loseHP(5);
				rewards.setText("- 5 Health Points");
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 3:
				text.setText("The chest contained gold");
				rewards.setText("+ 15 Coins");
				Player.gainRaidCoins(15);
				break;
			case 4:
				text.setText("You took a nap and now feel refreshed. Naps are good for health, so you"
						+ " heal for a small amount");
				rewards.setText("+ 5 Health Points");
				Player.gainHP(5);
				if(Player.getHp() > Player.getMaxHP())
					Player.setHp(Player.getMaxHP());
				break;
			case 5:
				text.setText("The nap was very refreshing, but as you wake up, there's a monster "
						+ "sleeping a bit further down the cave. You notice some coins next to it "
						+ "and you go pick them up, but end up waking up the monster as well.");
				rewards.setText("+ 5 Health Points \n" + "+ 10 Coins");
				Player.gainHP(5);
				if(Player.getHp() > Player.getMaxHP())
					Player.setHp(Player.getMaxHP());
				Player.gainRaidCoins(10);
				backBtn.clearListeners();
				backBtn.setText("To battle!");
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 6:
				text.setText("Your curiosity pays off");
				switch(rand.nextInt(4)) {
				case 0:
					rewards.setText("+ 5 Coins");
					Player.gainRaidCoins(5);
					break;
				case 1:
					rewards.setText("+ 1 Health Potion");
					if(storage.getEquippedItems().size() < 14)
						storage.equippedItems(storage.healthPot, "Add");
					break;
				case 2:
					rewards.setText("+ 1 Bomb");
					if(storage.getEquippedItems().size() < 14)
						storage.equippedItems(storage.bomb, "Add");
					break;
				case 3:
					rewards.setText("+ 1 Throwing Knife");
					if(storage.getEquippedItems().size() < 14)
						storage.equippedItems(storage.throwingKnife, "Add");
					break;
				}
				break;
			case 7:
				text.setText("You feel stronger after drinking the vial. You can take anyone on!");
				rewards.setText("Player enraged");
				enrage = true;
				break;
			case 8:
				text.setText("You feel like your body is weaker after drinking the vial");
				rewards.setText("Player weakened");
				weaken = true;
			case 10:
				if(rand.nextInt(6) < 4) {
					addBooster();
					text.setText("The bottle you drank was legit");
				}
				else {
					poison = true;
					rewards.setText("Player poisoned");
					text.setText("The bottle you drank was full of poison");
				}					
				break;
			case 11:
				if(storage.getEquippedItems().size() < 14) {
					text.setText("You gain an ability!");
					rewards.setText("+ " + option1.getText().toString());
					if(option1.getText().toString().equals("Swing"))
						storage.equippedItems(storage.itemSwing, "Add");
					else if(option1.getText().toString().equals("Rend"))
						storage.equippedItems(storage.itemRend, "Add");
					else if(option1.getText().toString().equals("Whirlwind"))
						storage.equippedItems(storage.itemWhirlwind, "Add");
				}
				else
					text.setText("Full inventory, cannot obtain an ability");
				break;
			case 31:
				text.setText("The suspicious person turned out to be a bandit, good thing you're "
						+ "quick on your feet, so you manage to get away");
				break;
			case 32:
				text.setText("You move to the crevice to investigate it and find a pile of gold "
						+ "hidden there, looks like your detective instinct paid off!");
				Player.gainRaidCoins(10);
				rewards.setText("+ 10 Coins");
				break;
			case 33:
				text.setText("The suspicious looking person is yelling that he's not "
						+ "a bandit, but you've rushed away too quickly to hear it.");
				break;
			case 34:
				text.setText("There was an enemy hiding in the crevice, good luck.");
				backBtn.clearListeners();
				backBtn.setText("To battle!");
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 36:
				option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
				option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
				text.setText("The loud sound came from someone screaming for help, will "
						+ "you attempt to help them?");
				option1.setVisible(true);
				option2.setVisible(true);
				backBtn.setVisible(false);
				option1.setText("Yes");
				option2.setText("No");
				option1.clearListeners();
				option2.clearListeners();
				option1.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				option2.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			gameScreen.switchToNewState(GameScreen.FOREST_MAP);
		    	    }});
				break;
			case 37:
				text.setText("The loud sounds were caused by a group of mutants having "
						+ "a campfire party. You join them.");
				rewards.setText("+ 5 Health Points");
				Player.gainHP(5);
				if(Player.getHp() > Player.getMaxHP())
					Player.setHp(Player.getMaxHP());
				break;
			case 38:
				text.setText("You climb up the tree to get the box, but the branch breaks, "
						+ "causing you to fall down and hurt yourself");
				rewards.setText("- 5 Health Points");
				Player.loseHP(5);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 39:
				text.setText("You climb up the tree to get the box, but the branch breaks, "
						+ "causing you to fall down and hurt yourself");
				rewards.setText("- 5 Health Points");
				Player.loseHP(5);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 40:
				text.setText("You climb up the tree, pick up the box and climb back down "
						+ "before checking to see your rewards.");
				rewards.setText("+ 5 Coins" + "\n+ 1 Bomb");
				Player.gainRaidCoins(5);
				storage.equippedItems(storage.bomb, "Add");
				break;
			case 41:
				text.setText("The angry figure turned out to be a monster, which attacks you.");
				backBtn.setText("To battle");
				backBtn.clearListeners();
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 42:
				text.setText("The person standing in front of you turned out to be "
						+ "friendly. They proceed to give you a booster to help you on your journey.");
				if(storage.getEquippedItems().size() < 14) {
					addBooster();
				}
				else
					rewards.setText("No space in inventory");
				break;	
			case 43:
				text.setText("You found a treasure, but upon looting, an enemy jumps out and attacks you");
				rewards.setText("+ 10 Coins");
				Player.gainRaidCoins(10);
				backBtn.setText("To battle");
				backBtn.clearListeners();
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 44:
				text.setText("You find treasure!");
				rewards.setText("+ 5 Coins");
				Player.gainRaidCoins(5);
				break;
			case 45:
				text.setText("You find a Health Potion");				
				if(storage.getEquippedItems().size() < 14) {
					rewards.setText("+ 1 Health Potion");
					storage.equippedItems(storage.healthPot, "Add");
				}
				else
					rewards.setText("Full inventory");
				break;
			case 46:
				text.setText("You find treasure, but as you're clumsy, you trip and fall, hurting yourself in the process");
				if(storage.getEquippedItems().size() < 14) {
					rewards.setText("+ 5 Coins" + "\n+ 1 Health Potion" + "\n- 5 Health Points");
					storage.equippedItems(storage.healthPot, "Add");
					Player.gainRaidCoins(5);
					Player.loseHP(5);
					if(Player.getHp() <= 0)
						Player.setHp(1);
				}
				else {
					rewards.setText("+ 5 Coins" + "\n-5 Health Points");
					Player.gainRaidCoins(5);
					Player.loseHP(5);
					if(Player.getHp() <= 0)
						Player.setHp(1);
				}
				break;
			case 47:
				text.setText("The figure turned out to be an enemy");
				backBtn.setText("To battle");
				backBtn.clearListeners();
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 48:
				text.setText("They give you an item dear to their heart");
				if(rand.nextInt(10) == 9 && storage.getEquippedItems().size() < 14) {
					rewards.setText("+ 1 Ability Refill Potion");
					storage.equippedItems(storage.abilityRefill, "Add");
				}
				else if(storage.getEquippedItems().size() < 14) {
					rewards.setText("+ 1 Health Potion");
					storage.equippedItems(storage.healthPot, "Add");
				}
				else {
					rewards.setText("+ 10 Coins");
					Player.gainRaidCoins(10);
				}
				break;
			case 49:
				option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
				option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
				text.setText("They ask you to help them out with a nuisance");
				option1.setVisible(true);
				option2.setVisible(true);
				backBtn.setVisible(false);
				option1.setText("Yes");
				option2.setText("No");
				option1.clearListeners();
				option2.clearListeners();
				option1.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				option2.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			gameScreen.switchToNewState(GameScreen.FOREST_MAP);
		    	    }});
				break;
			case 50:
				text.setText("You attempt to talk to them, but they bite you in reply");
				rewards.setText("- 5 Health Points");
				Player.loseHP(5);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 51:
				text.setText("You decide to train with the entiry, boosting one of your stats");
				int x = rand.nextInt(3);
				if(x == 0) {
					rewards.setText("+ 3 Max Health Points");
					Player.gainExtraHP(3);
					Player.gainHP(3);
				}
				else if(x == 1) {
					rewards.setText("+ 1 Strength");
					Player.gainExtraAP(1);
				}
				else {
					rewards.setText("+ 2 Damage Resist");
					Player.gainExtraDP(2);
				}
				break;
			case 52:
				text.setText("The training took quite a toll on your body and you learned nothing");
				rewards.setText("- 3 Health Points");
				Player.loseHP(3);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 53:
				text.setText("You train until you pass out from exhaustion");
				rewards.setText("+ 1 Strength" + "\n+ 1 Damage Resist" + "\n-2 Health Points");
				Player.gainExtraAP(1);
				Player.gainExtraDP(1);
				Player.loseHP(2);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 54:
				text.setText("You decide to punch the dummy with your bare hands, even you're bewildered by your choice");
				rewards.setText("+ 1 Strength" + "\n-1 Health Points");
				Player.gainExtraAP(1);
				Player.loseHP(1);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 55:
				text.setText("You swing your weapon at the dummy for a long time, eventually learning Swing");
				if(storage.getEquippedItems().size() < 14) {
					rewards.setText("+ Swing");
					storage.equippedItems(storage.itemSwing, "Add");
				}
				else
					rewards.setText("Full inventory");
				break;
			case 56:
				text.setText("You hit the dummy with your weapon, causing a piece of it to fly into your eye");
				rewards.setText("- 3 Health Points");
				Player.loseHP(3);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 57:
				text.setText("You shouldn't overworkd yourself!");
				rewards.setText("- 5 Health Points");
				Player.loseHP(5);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 58:
				text.setText("You sit by the river bank, relaxing and taking a whiff of fresh air");
				rewards.setText("+ 5 Health Points");
				Player.gainHP(5);
				if(Player.getHp() > Player.getMaxHP())
					Player.setHp(Player.getMaxHP());
				break;
			case 59:
				text.setText("You break down and cry, wondering why such horrible things happen to you."
						+ " You remain in the hole for the whole day");
				rewards.setText("- 3 Health Points");
				Player.loseHP(3);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 60:
				text.setText("You fall asleep from crying so much, finally getting some rest");
				rewards.setText("+ 2 Max Health Points");
				Player.gainExtraHP(2);
				Player.gainHP(2);
				break;
			case 61:
				text.setText("You decide to take some time off of raiding and train your body");
				rewards.setText("+ 2 Strength");
				Player.gainExtraAP(2);
				break;
			case 62:
				text.setText("You use the weights to train yourself, but as they're rusty, you get sick from it");
				rewards.setText("+ 1 Strength" + "\nPlayer poisoned");
				Player.gainExtraAP(1);
				poison = true;
				break;
			case 63:
				text.setText("You train yourself to exhaustion");
				rewards.setText("+ 1 Strength" + "\nPlayer weakened");
				Player.gainExtraAP(1);
				weaken = true;
				break;
			case 64:
				text.setText("You choose a weird and unexplainable method of training with the weights");
				rewards.setText("+ 2 Damage Resist");
				Player.gainExtraDP(2);
				break;
			case 65:
				text.setText("Your training paid off, expanding your Health pool");
				rewards.setText("+ 2 Max Health Points");
				Player.gainExtraHP(2);
				Player.gainHP(2);
				break;
			}
		}	
		
		// Option 2
		if(btnSelected == 2) {
			switch(encNum) {
			case 1:
				text.setText("You walk away from the chest, not daring to open it");
				break;
			case 2:
				text.setText("You walk away from the chest, not daring to open it");
				break;
			case 3:
				text.setText("You attempt to walk away from the chest, but it sucks you in, throwing "
						+ "you into a battle");
				backBtn.clearListeners();
				backBtn.setText("To battle");
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 4:
				text.setText("You're pretty fatigued, should've rested for a bit");
				rewards.setText("- 2 Health Points");
				Player.loseHP(2);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 5:
				text.setText("Probably best to avoid suspicious caves");
				break;
			case 7:
			case 8:
				text.setText("Best not to drink unknown bottles, who knows what's in there");
				break;
			case 10:
				text.setText("They're not happy with your rejection and attack you");
				backBtn.clearListeners();
				backBtn.setText("To battle");
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 11:
				if(storage.getEquippedItems().size() < 14) {
					text.setText("You gain an ability!");
					rewards.setText("+ " + option2.getText().toString());
					if(option2.getText().toString().equals("Hilt Bash"))
						storage.equippedItems(storage.itemHiltBash, "Add");
					else if(option2.getText().toString().equals("Ground Breaker"))
						storage.equippedItems(storage.itemGroundBreaker, "Add");
					else if(option2.getText().toString().equals("Bash"))
						storage.equippedItems(storage.itemBash, "Add");
				}
				else
					text.setText("Full inventory, cannot obtain an ability");
				break;
			case 31:
				text.setText("The suspicious person turned out to be a bandit. He proceeds"
						+ " to stab you and steal 5 gold from you before you run away");
				Player.loseHP(5);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				Player.loseRaidCoins(5);
				if(Player.getRaidCoins() < 0)
					Player.setRaidCoins(0);
				if(storage.getEquippedItems().size() > 0) {
					lostItem = storage.loseItem();
					rewards.setText("- 5 Health Points \n" + "- 5 Coins \n" + "- " + lostItem);
				}
				else	
					rewards.setText("- 5 Health Points \n" + "- 5 Coins");
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 32:
				text.setText("You quickly walk past the crevice, who knows what could be hiding there");
				break;
			case 33:
				option1.setVisible(true);
				option2.setVisible(true);
				option3.setVisible(true);
				text.setText("The suspicious looking figure turned out to be telling the "
						+ "truth and he offers you some items to buy");
				option1.setText("Health Potion (3 gold)");
				option1.clearListeners();
				option1.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			if(Player.getRaidCoins() >= 3) {
		    				Player.loseRaidCoins(3);
		    				storage.equippedItems(storage.healthPot, "Add");
		    				option1.setVisible(false);
		    				option2.setVisible(false);
		    				option3.setVisible(false);
		    			}
		    			else
		    				rewards.setText("Insufficient funds");
		    	    }});
				option2.setText("Bomb (4 gold)");
				option2.clearListeners();
				option2.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			if(Player.getRaidCoins() >= 4) {
		    				Player.loseRaidCoins(4);
		    				storage.equippedItems(storage.bomb, "Add");
		    				option1.setVisible(false);
		    				option2.setVisible(false);
		    				option3.setVisible(false);
		    			}
		    			else
		    				rewards.setText("Insufficient funds");
		    	    }});
				option3.setText("EXP Potion (15 gold)");
				option3.clearListeners();
				option3.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			if(Player.getRaidCoins() >= 15) {
		    				Player.loseRaidCoins(15);
		    				storage.equippedItems(storage.expBoost, "Add");
		    				option1.setVisible(false);
		    				option2.setVisible(false);
		    				option3.setVisible(false);
		    			}
		    			else
		    				rewards.setText("Insufficient funds");
		    	    }});				
				break;
			case 34:
				text.setText("You walk away from the hole in the wall, not willing to "
						+ "risk finding out what's hiding there");
				break;
			case 36:
				text.setText("You hastly walk in the other direction, not willing to "
						+ "risk finding out who or what made such a sound");
				break;
			case 37:
				text.setText("The loud shrieks send a shiver down your spine and you "
						+ "choose to run away, losing an item in the process.");
				if(storage.getEquippedItems().size() > 0) {
					lostItem = storage.loseItem();
					rewards.setText("- 1 " + lostItem);
				}
				else
					rewards.setText("Nothing to lose");
				break;
			case 38:
				text.setText("You throw rocks at the box, causing it to fall down.");				
				Player.gainRaidCoins(5);
				if(storage.getEquippedItems().size() < 14) {
					storage.equippedItems(storage.healthPot, "Add");
					rewards.setText("+ 5 Coins" + "\n+ 1 Health Potion");
				}
				else
					rewards.setText("+ 5 Coins");
				break;
			case 39:
				text.setText("You throw rocks at the box, causing it to fall on the "
						+ "ground. As you attempt to see what's inside, the box suddenly "
						+ "explodes, hurting you in the process");
				rewards.setText("- 5 Health Points");
				Player.loseHP(5);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;					
			case 40:
				text.setText("You throw rocks at the box, causing it to fall on the "
						+ "ground. As you attempt to see what's inside, the box suddenly "
						+ "explodes, hurting you in the process");
				rewards.setText("- 5 Health Points");
				Player.loseHP(5);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;	
			case 43:
				text.setText("Better to trust your gut and turn coat, than to get eaten by monsters");
				break;
			case 44:
			case 45:
				text.setText("You decide to go past the abandoned building, but an enemy creeps up behind you");
				backBtn.clearListeners();
				backBtn.setText("To battle");
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 46:
				text.setText("You go around and past the building, but trip on some debris");
				rewards.setText("+ 5 Coins" + "\n- 1 Health Point");
				Player.loseHP(1);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 47:
				text.setText("Apparently flicking their hat angered them, who would've thought");
				backBtn.clearListeners();
				backBtn.setText("To battle");
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 48:
				text.setText("Maybe you shouldn't be agitating random strangers. Just a passing thought");
				rewards.setText("Player poisoned");
				poison = true;
				break;
			case 49:
				text.setText("They run away in shame, as they were using the hat to hide their deformities");
				break;
			case 50:
				text.setText("Next time, don't fuck with stranger, you never know what they might do");
				backBtn.clearListeners();
				backBtn.setText("To battle");
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.elite = FightScene.boss = false;
		    			FightScene.normal = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 51:
				text.setText("They beat you up and to add insult to injury, ask you if you were even trying to fight them");
				rewards.setText("- 10 Health Points");
				Player.loseHP(10);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 52:
				text.setText("They find your answer compeling and decide to spar with you");
				rewards.setText("+ 1 Strength" + "\n+ 1 Damage Resist");
				Player.gainExtraAP(1);
				Player.gainExtraDP(1);
				break;
			case 53:
				text.setText("You attack the entity and as you do in fact lose the fight, at least you gained some experience");
				rewards.setText("+ 5 Experience Points" + "\n- 3 Health Points");
				Player.loseHP(3);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				Player.gainExp(5);
				Player.checkExp();
				break;
			case 54:
			case 55:
			case 56:
				text.setText("You're too lazy to actually work on yourself, you'd rather just depend on your gear");
				break;
			case 59:
				text.setText("You climb out of the hole with your bare hands and strength");
				rewards.setText("+ 2 Damage Resist" + "- 1 Health Points");
				Player.gainExtraDP(2);
				Player.loseHP(1);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 60:
				text.setText("You manage to climb out of the hole. It was quite strenuous on your body");
				if(Player.getExtraAP() > 0) {
					Player.setExtraAP(Player.getExtraAP() - 1);
					rewards.setText("- 1 Strength");
				}					
				break;
			case 61:
			case 62:
			case 63:
				text.setText("You don't feel like working out");
				break;
			case 64:
				text.setText("You decide to not train, which makes you feel bad about yourself. You feel weakened");
				rewards.setText("Player weakened");
				weaken = true;
				break;
			case 65:
				text.setText("All that running boosted your overall strength");
				rewards.setText("+ 1 Strength");
				Player.gainExtraAP(1);
				break;
			}
		}
		
		// Option 3
		if(btnSelected == 3) {
			switch(encNum) {
			case 11:
				if(storage.getEquippedItems().size() < 14) {
					text.setText("You gain an ability!");
					rewards.setText("+ " + option3.getText().toString());
					if(option3.getText().toString().equals("Mend"))
						storage.equippedItems(storage.itemMend, "Add");
					else if(option3.getText().toString().equals("Barrier"))
						storage.equippedItems(storage.itemBarrier, "Add");
					else if(option3.getText().toString().equals("Barbed Armor"))
						storage.equippedItems(storage.itemBarbedArmor, "Add");
				}
				else
					text.setText("Full inventory, cannot obtain an ability");
				break;
			case 31:
				text.setText("You kill the suspicious person, stealing their belongings");
				Player.gainRaidCoins(10);
				if(storage.getEquippedItems().size() < 14) {
					storage.equippedItems(storage.bomb, "Add");
					rewards.setText("+ 10 Coins \n" + "+ 1 Bomb");
				}					
				if(storage.getEquippedItems().size() < 14) {
					storage.equippedItems(storage.healthPot, "Add");
					rewards.setText("+ 10 Coins \n" + "+ 1 Bomb \n" + "+ 1 Health Potion");
				}	
				else
					rewards.setText("+ 10 Coins");
				break;
			case 33:
				text.setText("The suspicious looking person turned out to be a merchant, "
						+ "but you mercilessly killed them and their belongings "
						+ "disappear as their life slowly fades away");
				break;
			case 38:
				text.setText("You burn the tree, but as you're clumsy, you burn "
						+ "yourself as well.");
				rewards.setText("- 5 Health Points");
				Player.loseHP(5);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 39:
				text.setText("You set the tree on fire. You stand next to it, to "
						+ "warm yourself up, leading to regaining some of your lost health");
				Player.gainHP(5);
				if(Player.getHp() > Player.getMaxHP())
					Player.setHp(Player.getMaxHP());
				rewards.setText("+ 5 Health Points");
				break;
			case 40:
				text.setText("You burn the tree. It turns out the tree is actually "
						+ "a Mimic Tree. Good luck");
				backBtn.clearListeners();
				backBtn.setText("To battle!");
				backBtn.addListener(new ClickListener() {
		    		@Override
		    	    public void clicked(InputEvent event, float x, float y) {
		    			FightScene.boss = FightScene.normal = false;
		    			FightScene.elite = true;
		    			mimic = true;
		    			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
		    	    }});
				break;
			case 47:
				text.setText("Killing them made you feel like a villain and you start spiraling");
				rewards.setText("- 5 Health Points");
				Player.loseHP(5);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 48:
				text.setText("You don't know whether they were good or bad, but they did drop loot, so you're happy "
						+ "with your decision");
				if(storage.getEquippedItems().size() < 14) {
					storage.equippedItems(storage.healthPot, "Add");
					Player.gainRaidCoins(5);
					rewards.setText("+ 1 Health Potion" + "\n+ 5 Coins");
				}
				else {
					Player.gainRaidCoins(5);
					rewards.setText("+ 5 Coins");
				}
				break;
			case 49:
				text.setText("You feel bad for killing a potentially innocent stranger");
				break;
			case 50:
				text.setText("You kill the stranger, which causes you to learn the Stab ability");
				if(storage.getEquippedItems().size() < 14) {
					storage.equippedItems(storage.itemStab, "Add");
					rewards.setText("+ Stab");
				}
				else
					rewards.setText("Inventory full");
				break;
			case 51:
				text.setText("You politely decline their offer");
				break;
			case 52:
				text.setText("You tell them you don't need any training. They are insulted by this and attack you");
				rewards.setText("- 5 Health Points");
				Player.loseHP(5);
				if(Player.getHp() <= 0)
					Player.setHp(1);
				break;
			case 53:
				text.setText("You politely decline their offer");
				break;
			case 65:
				text.setText("You feel stronger and think to yourself, that sometimes it pays off to be a punching dummy");
				Player.gainExtraDP(2);
				rewards.setText("+ 2 Damage Resist");
				break;
			}
		}
		
		rewards.setPosition(centerX - text.getWidth() / 2, vp.getWorldHeight() / 4f); 
	}
	
	private void addBooster() {
		if(storage.getEquippedItems().size() < 14) {
			switch(rand.nextInt(3)) {
			case 0:
				rewards.setText("+ 1 Attack Boost");
				if(storage.getEquippedItems().size() < 14)
				storage.equippedItems(storage.apBoost, "Add");
				break;
			case 1:
				rewards.setText("+ 1 Defense Boost");
				storage.equippedItems(storage.dpBoost, "Add");
				break;
			case 2:
				rewards.setText("+ 1 Health Boost");
				storage.equippedItems(storage.hpBoost, "Add");
				break;
			}
		}
		else
			rewards.setText("No space in inventory");
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
		
		stage.act();
		stage.draw();	
		
		if(Gdx.input.isKeyPressed(Keys.F5)) {
			option1.remove();
			option2.remove();
			option3.remove();
			title.remove();
			text.remove();
			
			createComponents();
		}
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
