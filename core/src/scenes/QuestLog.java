package scenes;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;

import player.Player;
import storage.Quests;
import storage.Storage;
	
public class QuestLog implements Screen {
		Skin skin;
		Viewport vp;
		public Stage stage;
		private Storage storage;
		private Game game;
		private GameScreen gameScreen; 
		private TextButton backBtn;
		private static TextButton q1Accept, q2Accept, q3Accept;
		private static Label q1Title, q2Title, q3Title, q1Text, q2Text, q3Text, timer;
		private static int[] questDone = {0, 0, 0};
		private static int[] newQuest = {0, 0, 0};
		private static int[] takenQuest = {0, 0, 0};
		private static int[] activeQuest = {0, 0, 0};
		private Random rand = new Random();
		public static boolean checkQuests = false, activeQuests = false;
	
	public QuestLog(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		storage.createFont();	
		checkQuests = false;
		
		if(StartScreen.isFreshLoad()) {
			resetQuests();
			StartScreen.setFreshLoad(false);
		}
		
		createComponents();
		setQuest();
	}	
	
	private void checkCompletedQuest() {
	    for (int i = 0; i < takenQuest.length; i++) {
	        int questIndex = i + 1;
	        Quests currentQuest = null;

	        switch (takenQuest[i]) {
	            case 1:
	                currentQuest = storage.wolfQuest;
	                break;
	            case 2:
	                currentQuest = storage.spiderQuest;
	                break;
	            case 3:
	                currentQuest = storage.spiderBearQuest;
	                break;
	            case 4:
	            	currentQuest = storage.bearQuest;
	            	break;
	            case 5:
	            	currentQuest = storage.waspQuest;
	            	break;
	            case 6:
	            	currentQuest = storage.monkeyQuest;
	            	break;
	            case 7:
	            	currentQuest = storage.monWasWolfQuest;
	            	break;
	            case 8:
	            	currentQuest = storage.whirlwindQuest;
	            	break;
	        }

	        if (currentQuest != null && 
	            currentQuest.getObj1Prog() >= currentQuest.getObj1() && 
	            (currentQuest.getObj2Prog() >= currentQuest.getObj2() || 
	            currentQuest.getObj2() == 0)) {
	            switch (questIndex) {
	                case 1:
	                    updateQuestButton(q1Accept, 0, currentQuest);
	                    break;
	                case 2:
	                    updateQuestButton(q2Accept, 1, currentQuest);
	                    break;
	                case 3:
	                    updateQuestButton(q3Accept, 2, currentQuest);
	                    break;
	            }
	        }
	    }
	}

	private void updateQuestButton(TextButton button, int questIndex, Quests currentQuest) {
	    button.setTouchable(Touchable.enabled);
	    button.setColor(Color.GREEN);
	    button.setDisabled(false);
	    button.clearListeners();
	    button.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	        	if(currentQuest.getRewardType() == 1)
	        		Player.gainCoins(currentQuest.getReward());
	        	else if(currentQuest.getRewardType() == 2){
	        		Player.gainExp(currentQuest.getReward());
	        		Player.checkExp();
	        	}
	        	else {
	        		storage.inventoryItems(storage.itemWhirlwind, "Add");
	        	}
	            questDone[questIndex] = 1;
	            button.setVisible(false);
	            setActiveQuest(takenQuest[questIndex], 0);
	        }
	    });
	}
	
	private void setActiveQuest(int x, int a) {		
		switch(x) {
		case 1:
			storage.wolfQuest.setActive(a);
			break;
		case 2:
			storage.spiderQuest.setActive(a);
			break;
		case 3:
			storage.spiderBearQuest.setActive(a);
			break;
		case 4:
			storage.bearQuest.setActive(a);
			break;
		case 5:
			storage.waspQuest.setActive(a);
			break;
		case 6:
			storage.monkeyQuest.setActive(a);
			break;
		case 7:
			storage.monWasWolfQuest.setActive(a);
			break;
		case 8:
			storage.whirlwindQuest.setActive(a);
			break;
		}	
	}
	
	private void selectQuest(int x, int q) {
	    takenQuest[q - 1] = x;
	    String questTitle = "";
	    String questText = "";
	    String questObj1, questObj2, questObj3;
	    String[] objective;

	    switch (x) {
	        case 1:
	        	objective = storage.wolfQuest.getObjective1().split(" ");
	        	questObj1 = objective[objective.length - 1];
	            questTitle = storage.wolfQuest.getQuestName();
	            questText = "\n" + storage.wolfQuest.getObjective1() + "\n\n\n" + storage.wolfQuest.getObj1Prog() + "/" + storage.wolfQuest.getObj1()
	            + " " + questObj1 + "\n\n\nReward: " + storage.wolfQuest.getReward() + " coins";
	            break;
	        case 2:
	        	objective = storage.spiderQuest.getObjective1().split(" ");
	        	questObj1 = objective[objective.length - 1];
	            questTitle = storage.spiderQuest.getQuestName();
	            questText = "\n" + storage.spiderQuest.getObjective1() + "\n\n\n" + storage.spiderQuest.getObj1Prog() + "/" + storage.spiderQuest.getObj1()
	            + " " + questObj1 + "\n\n\nReward: " + storage.spiderQuest.getReward() + " coins";
	            break;
	        case 3:
	        	objective = storage.spiderBearQuest.getObjective1().split(" ");
	        	questObj1 = objective[objective.length - 1];
	        	objective = storage.spiderBearQuest.getObjective2().split(" ");
	        	questObj2 = objective[objective.length - 1];
	            questTitle = storage.spiderBearQuest.getQuestName();
	            questText = "\n" + storage.spiderBearQuest.getObjective1() + "\n" + storage.spiderBearQuest.getObjective2() + "\n\n\n" +
	                    storage.spiderBearQuest.getObj1Prog() + "/" + storage.spiderBearQuest.getObj1() + " " + questObj1 + "\n" +
	                    storage.spiderBearQuest.getObj2Prog() + "/" + storage.spiderBearQuest.getObj2()
	                    + " " + questObj2 + "\n\n\nReward: " + storage.spiderBearQuest.getReward() + " EXP";
	            break;
	        case 4:
	        	objective = storage.bearQuest.getObjective1().split(" ");
	        	questObj1 = objective[objective.length - 1];
	        	questTitle = storage.bearQuest.getQuestName();
	            questText = "\n" + storage.bearQuest.getObjective1() + "\n\n\n" + storage.bearQuest.getObj1Prog() + "/" + storage.bearQuest.getObj1()
	            + " " + questObj1 + "\n\n\nReward: " + storage.bearQuest.getReward() + " coins";
	            break;
	        case 5:
	        	objective = storage.waspQuest.getObjective1().split(" ");
	        	questObj1 = objective[objective.length - 1];
	        	questTitle = storage.waspQuest.getQuestName();
	            questText = "\n" + storage.waspQuest.getObjective1() + "\n\n\n" + storage.waspQuest.getObj1Prog() + "/" + storage.waspQuest.getObj1()
	            + " " + questObj1 + "\n\n\nReward: " + storage.waspQuest.getReward() + " EXP";
	            break;
	        case 6:
	        	objective = storage.monkeyQuest.getObjective1().split(" ");
	        	questObj1 = objective[objective.length - 1];
	        	questTitle = storage.monkeyQuest.getQuestName();
	            questText = "\n" + storage.monkeyQuest.getObjective1() + "\n\n\n" + storage.monkeyQuest.getObj1Prog() + "/" + storage.monkeyQuest.getObj1()
	            + " " + questObj1 + "\n\n\nReward: " + storage.monkeyQuest.getReward() + " EXP";
	            break;
	        case 7:
	        	objective = storage.monWasWolfQuest.getObjective1().split(" ");
	        	questObj1 = objective[objective.length - 1];
	        	objective = storage.monWasWolfQuest.getObjective2().split(" ");
	        	questObj2 = objective[objective.length - 1];
	        	objective = storage.monWasWolfQuest.getObjective3().split(" ");
	        	questObj3 = objective[objective.length - 1];
	            questTitle = storage.monWasWolfQuest.getQuestName();
	            questText = "\n" + storage.monWasWolfQuest.getObjective1() + "\n" + storage.monWasWolfQuest.getObjective2() + "\n" + storage.monWasWolfQuest.getObjective3() +"\n\n\n" +
	                    storage.monWasWolfQuest.getObj1Prog() + "/" + storage.monWasWolfQuest.getObj1() + " " + questObj1 + "\n" +
	                    storage.monWasWolfQuest.getObj2Prog() + "/" + storage.monWasWolfQuest.getObj2() + " " + questObj2 + "\n" +
	                    storage.monWasWolfQuest.getObj3Prog() + "/" + storage.monWasWolfQuest.getObj3() + " " + questObj3 +
	                    "\n\n\nReward: " + storage.monWasWolfQuest.getReward() + " EXP";
	            break;
	        case 8:
	        	questTitle = storage.whirlwindQuest.getQuestName();
	            questText = "\n" + storage.whirlwindQuest.getObjective1() + "\n\n\n" + storage.whirlwindQuest.getObj1Prog() + "/" + storage.whirlwindQuest.getObj1()
	            + " Whirlwinds" + "\n\n\nReward: Whirlwind";
	            break;
	    }

	    switch (q) {
	        case 1:
	            q1Title.setText(questTitle);
	            q1Text.setText(questText);
	            break;
	        case 2:
	            q2Title.setText(questTitle);
	            q2Text.setText(questText);
	            break;
	        case 3:
	            q3Title.setText(questTitle);
	            q3Text.setText(questText);
	            break;
	    }
	}

	
	private void setQuest() {		
		int x = rand.nextInt(1, 9);
		boolean newQ = false;
		
		if(newQuest[0] == 0) {
			 newQuest[0] = 1;
			 selectQuest(x, 1);
		}
		 
		x = rand.nextInt(1, 9);
		while(x == takenQuest[0])
			x = rand.nextInt(1, 9);
		
		if(newQuest[1] == 0) {
			newQuest[1] = 1;
			selectQuest(x, 2);
		}
		
		x = rand.nextInt(1, 9);
		while(x == takenQuest[0] || x == takenQuest[1])
			x = rand.nextInt(1, 9);
		 
		if(newQuest[2] == 0) {
			newQuest[2] = 1;
			selectQuest(x, 3);
		}
				
		for (int i = 0; i < newQuest.length; i++)
	        if(newQuest[i] == 0)
	        	newQ = true;
		
		if(!newQ) {
			selectQuest(takenQuest[0], 1);
			selectQuest(takenQuest[1], 2);
			selectQuest(takenQuest[2], 3);
			
			if(activeQuest[0] == 1) {
				q1Accept.setText("Complete");
    			q1Accept.setDisabled(true);
    			q1Accept.setTouchable(Touchable.disabled);
			}
			if(activeQuest[1] == 1) {
				q2Accept.setText("Complete");
				q2Accept.setDisabled(true);
				q2Accept.setTouchable(Touchable.disabled);
			}
			if(activeQuest[2] == 1) {
				q3Accept.setText("Complete");
				q3Accept.setDisabled(true);
				q3Accept.setTouchable(Touchable.disabled);
			}
		}
	}
	
	private void createComponents() {
		backBtn = new TextButton("Back", storage.buttonStyle);
		backBtn.setColor(Color.LIGHT_GRAY);
		backBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {     			
    			gameScreen.switchToNewState(GameScreen.HOME);
    	    }});
		backBtn.setSize(150, 100);
		backBtn.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.15f);
		stage.addActor(backBtn);	
		
		timer = new Label("", storage.labelStyle);
		timer.setPosition(backBtn.getX() + backBtn.getWidth() * 2, backBtn.getY() + 50);
		stage.addActor(timer);
		
		q1Title = new Label("Some Extermination1", storage.labelStyleMed);
		q1Title.setPosition(vp.getWorldWidth() / 4f, vp.getWorldHeight() / 1.5f, Align.center);
		q1Title.setSize(300, 100);
		q1Title.setWrap(true);
		q1Title.setAlignment(Align.center);
		stage.addActor(q1Title);
		
		q2Title = new Label("Some Extermination2", storage.labelStyleMed);
		q2Title.setPosition(q1Title.getX() + q1Title.getWidth() + 400, vp.getWorldHeight() / 1.5f, Align.center);
		q2Title.setSize(300, 100);
		q2Title.setWrap(true);
		q2Title.setAlignment(Align.center);
		stage.addActor(q2Title);
		
		q3Title = new Label("Some Extermination3", storage.labelStyleMed);
		q3Title.setPosition(q2Title.getX() + q2Title.getWidth() + 400, vp.getWorldHeight() / 1.5f, Align.center);
		q3Title.setSize(300, 100);
		q3Title.setWrap(true);
		q3Title.setAlignment(Align.center);
		stage.addActor(q3Title);
		
		q1Text = new Label("Kill 5 stuff", storage.labelStyle);
		q1Text.setPosition(q1Title.getX(), q1Title.getY() - q1Title.getHeight() * 2.5f);
		q1Text.setSize(300, 200);
		q1Text.setWrap(true);
		stage.addActor(q1Text);
		
		q2Text = new Label("Kill 3 stuff", storage.labelStyle);
		q2Text.setPosition(q2Title.getX(), q2Title.getY() - q2Title.getHeight() * 2.5f);
		q2Text.setSize(300, 200);
		q2Text.setWrap(true);
		stage.addActor(q2Text);
		
		q3Text = new Label("Kill 5 stuff \nKill 3 stuff", storage.labelStyle);
		q3Text.setPosition(q3Title.getX(), q3Title.getY() - q3Title.getHeight() * 2.5f);
		q3Text.setSize(300, 200);
		q3Text.setWrap(true);
		stage.addActor(q3Text);
		
		q1Accept = new TextButton("Accept", storage.buttonStyle);
		q1Accept.setColor(Color.LIGHT_GRAY);
		q1Accept.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			setActiveQuest(takenQuest[0], 1);
    			q1Accept.setText("Complete");
    			q1Accept.setDisabled(true);
    			q1Accept.setTouchable(Touchable.disabled);
    			activeQuest[0] = 1;
    	    }});
		q1Accept.setSize(200, 70);
		q1Accept.setPosition(q1Text.getX() + q1Text.getWidth() / 6f, q1Text.getY() - q1Text.getHeight());
		stage.addActor(q1Accept);
		
		q2Accept = new TextButton("Accept", storage.buttonStyle);
		q2Accept.setColor(Color.LIGHT_GRAY);
		q2Accept.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			setActiveQuest(takenQuest[1], 1);
    			q2Accept.setText("Complete");
    			q2Accept.setDisabled(true);
    			q2Accept.setTouchable(Touchable.disabled);
    			activeQuest[1] = 1;
    	    }});
		q2Accept.setSize(200, 70);
		q2Accept.setPosition(q2Text.getX() + q2Text.getWidth() / 6f, q2Text.getY() - q2Text.getHeight());
		stage.addActor(q2Accept);
		
		q3Accept = new TextButton("Accept", storage.buttonStyle);
		q3Accept.setColor(Color.LIGHT_GRAY);
		q3Accept.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			setActiveQuest(takenQuest[2], 1);
    			q3Accept.setText("Complete");
    			q3Accept.setDisabled(true);
    			q3Accept.setTouchable(Touchable.disabled);
    			activeQuest[2] = 1;
    	    }});
		q3Accept.setSize(200, 70);
		q3Accept.setPosition(q3Text.getX() + q3Text.getWidth() / 6f, q3Text.getY() - q3Text.getHeight());
		stage.addActor(q3Accept);
	}
	
	private void resetQuests() {
		for (int i = 0; i < questDone.length; i++)
	        questDone[i] = 0;

	    for (int i = 0; i < newQuest.length; i++)
	        newQuest[i] = 0;

	    for (int i = 0; i < takenQuest.length; i++)
	        takenQuest[i] = 0;
	    
	    for (int i = 0; i < activeQuest.length; i++)
	    	activeQuest[i] = 0;
	    
	    for (int i = 0; i < storage.quests.length; i++) {
	    	storage.quests[i].setActive(0);
	    	storage.quests[i].setObj1Prog(0);
    		storage.quests[i].setObj2Prog(0);
    		storage.quests[i].setObj3Prog(0);
	    }
	}
	
	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void render(float delta) {
	    long currentTime = System.currentTimeMillis();

	    // Get the current time in the CET timezone
	    ZoneId zoneId = ZoneId.of("CET");
	    ZonedDateTime now = ZonedDateTime.ofInstant(Instant.ofEpochMilli(currentTime), zoneId);
	    ZonedDateTime resetTime = now.with(LocalTime.MIDNIGHT);

	    // If the current time is after the reset time, set reset time to next day
	    if (now.compareTo(resetTime) >= 0) {
	        resetTime = resetTime.plusDays(1);
	    }

	    // Calculate the time until the next reset
	    Duration durationUntilReset = Duration.between(now, resetTime);

	    // Convert the duration to hours, minutes, and seconds
	    long hours = durationUntilReset.toHours();
	    long minutes = durationUntilReset.minusHours(hours).toMinutes();
	    long seconds = durationUntilReset.minusHours(hours).minusMinutes(minutes).getSeconds();

	    String timeLeft = String.format("Daily quest reset in: %02d:%02d:%02d", hours, minutes, seconds);

	    if (timer != null)
	        timer.setText(timeLeft);

	    if (durationUntilReset.isZero() || durationUntilReset.isNegative()) {
	        resetQuests();
	        // Update reset time to next day
	        resetTime = resetTime.plusDays(1);
	    }

	    if (Gdx.input.isKeyPressed(Keys.F5)) {
	        for (Actor actor : stage.getActors()) {
	            actor.addAction(Actions.removeActor());
	        }
	        resetQuests();
	        createComponents();
	        setQuest();      
	    }

	    if (checkQuests && activeQuests) {
	        checkCompletedQuest();
	        checkQuests = false;
	        selectQuest(takenQuest[0], 1);
			selectQuest(takenQuest[1], 2);
			selectQuest(takenQuest[2], 3);
	    }

	    ShapeRenderer shapeRenderer = new ShapeRenderer();
	    shapeRenderer.setProjectionMatrix(vp.getCamera().combined);
	    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
	    shapeRenderer.setColor(Color.BLACK);
	    
	    int thickness = 3; // Set thickness value
	    for (int i = 0; i < thickness; i++) {
            shapeRenderer.rect(q1Title.getX() - 50 - i, q1Accept.getY() - 25 - i, q1Title.getWidth() + 100 + 2 * i, 600 + 2 * i);
            shapeRenderer.rect(q2Title.getX() - 50 - i, q2Accept.getY() - 25 - i, q2Title.getWidth() + 100 + 2 * i, 600 + 2 * i);
            shapeRenderer.rect(q3Title.getX() - 50 - i, q3Accept.getY() - 25 - i, q3Title.getWidth() + 100 + 2 * i, 600 + 2 * i);
	    }

	    shapeRenderer.end();
	    shapeRenderer.dispose();
	    
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
		// TODO Auto-generated method stub
		
	}

	public static int[] getQuestDone() {
		return questDone;
	}

	public static void setQuestDone(int[] questDone) {
		QuestLog.questDone = questDone;
	}

	public static int[] getNewQuest() {
		return newQuest;
	}

	public static void setNewQuest(int[] newQuest) {
		QuestLog.newQuest = newQuest;
	}

	public static int[] getTakenQuest() {
		return takenQuest;
	}

	public static void setTakenQuest(int[] takenQuest) {
		QuestLog.takenQuest = takenQuest;
	}

	public static int[] getActiveQuest() {
		return activeQuest;
	}

	public static void setActiveQuest(int[] activeQuest) {
		QuestLog.activeQuest = activeQuest;
	}
}