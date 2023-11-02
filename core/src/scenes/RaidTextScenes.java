package scenes;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
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
		GameScreen.newGame = false;
		
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
		text.setPosition(centerX - text.getWidth() / 2, vp.getWorldHeight() / 2f);
		
		if(ForestMap.encounter == 2) {
			encNum = rand.nextInt(1, 3);
			option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
			option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
		}			
		else
			encNum = rand.nextInt(11, 13);
		
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
		case 11:
			text.setText("You stumble upon a suspicious looking fella and he tells you he's a "
					+ "travelling merchant, what will you do?");
			option1.setText("Run away");
			option2.setText("Ask him about his wares");
			option3.setText("Kill him (stranger danger)");
			break;
		case 12:
			option1.setPosition(vp.getWorldWidth() / 4.5f, vp.getWorldHeight() / 10f);
			option2.setPosition(vp.getWorldWidth() / 1.9f, vp.getWorldHeight() / 10f);
			text.setText("You notice that there's a light shining from a crevice, will you "
					+ "attempt to investigate what it is?");
			option1.setText("Yes");
			option2.setText("No");
			option3.setVisible(false);
			break;
		}
	}
	
	private void createComponents() {
		title = new Label("", storage.labelStyleBig);
	    title.setSize(vp.getWorldWidth(), 100); // Set the desired width
	    title.setAlignment(Align.center);
	    title.setPosition(0, vp.getWorldHeight() / 1.3f); // Set Y position at the top
	    stage.addActor(title);
	    
	    text = new Label("", storage.labelStyle);
	    text.setSize(800, 400); // Set the desired width and height
	    text.setWrap(true); // Enable word wrapping
	    text.setAlignment(Align.center); // Center horizontally and align text to the top
	    text.setPosition(centerX - text.getWidth() / 2, vp.getWorldHeight() / 1.3f); // Center on screen horizontally
	    stage.addActor(text);
	    
	    rewards = new Label("", storage.labelStyle);
	    rewards.setSize(400, 200);
	    rewards.setWrap(true);
	    rewards.setPosition(centerX - text.getWidth() / 2, vp.getWorldHeight() / 5f); 
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
    			gameScreen.setCurrentState(GameScreen.FOREST_MAP);
    	    }});
		backBtn.setSize(250, 150);
		backBtn.setPosition(vp.getWorldWidth() / 2f - backBtn.getWidth() / 2, vp.getWorldHeight() / 10f);
		backBtn.setVisible(false);
		stage.addActor(backBtn);	

		option3.setVisible(false);
	}

	private void optionActions() {
		String lostItem;
		text.setPosition(centerX - text.getWidth() / 2, vp.getWorldHeight() / 2f);
		
		backBtn.setVisible(true);
		option1.setVisible(false);
		option2.setVisible(false);
		option3.setVisible(false);
		
		// Option 1
		if(btnSelected == 1) {
			switch(encNum) {
			case 1:
				text.setText("You found 5 gold and a Health potion in the chest!");
				Player.gainCoins(5);
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
				break;
			case 11:
				text.setText("The suspicious person turned out to be a bandit, good thing you're "
						+ "quick on your feet, so you manage to get away");
				break;
			case 12:
				text.setText("You move to the crevice to investigate it and find a pile of gold "
						+ "hidden there, looks like your detective instinct paid off!");
				Player.gainCoins(10);
				rewards.setText("+ 10 Coins");
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
			case 11:
				text.setText("The suspicious person turned out to be a bandit. He proceeds"
						+ " to stab you and steal 5 gold from you before you run away");
				Player.loseHP(5);
				Player.loseCoins(5);
				if(storage.getEquippedItems().size() > 0) {
					lostItem = storage.loseItem();
					rewards.setText("- 5 Health Points \n" + "- 5 Coins \n" + "- " + lostItem);
				}
				else	
					rewards.setText("- 5 Health Points \n" + "- 5 Coins");
				break;
			case 12:
				text.setText("You quickly walk past the crevice, who knows what could be hiding there");
				break;
			}
		}
		
		// Option 3
		if(btnSelected == 3) {
			switch(encNum) {
			case 11:
				text.setText("You kill the suspicious person, stealing their belongings");
				Player.gainCoins(10);
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
			}
		}
		
		rewards.setPosition(centerX - text.getWidth() / 2, vp.getWorldHeight() / 3f); 
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
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
