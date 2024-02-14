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
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
		private TextButton backBtn, resetQuestBtn;
		private static TextButton q1Accept, q2Accept, q3Accept;
		private static Label q1Title, q2Title, q3Title, q1Text, q2Text, q3Text, timer;
		private static int[] questDone = {0, 0, 0};
		private static int[] newQuest = {0, 0, 0};
		private static int[] takenQuest = {0, 0, 0};
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
	
		createComponents();
		setQuest();
		checkAvailableQuests();
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
	    button.setDisabled(false);
	    button.clearListeners();
	    button.addListener(new ClickListener() {
	        @Override
	        public void clicked(InputEvent event, float x, float y) {
	            Player.gainCoins(currentQuest.getReward());
	            questDone[questIndex] = 1;
	            button.setVisible(false);
	        }
	    });
	}

	
	private void checkAvailableQuests() {
		 if(questDone[0] == 1) {
			 q1Title.setVisible(false);
			 q1Text.setVisible(false);
			 q1Accept.setVisible(false);
		 }
		 
		 if(questDone[1] == 1) {
			 q2Title.setVisible(false);
			 q2Text.setVisible(false);
			 q2Accept.setVisible(false);
		 }
		 
		 if(questDone[2] == 1) {
			 q3Title.setVisible(false);
			 q3Text.setVisible(false);
			 q3Accept.setVisible(false);
		 }
	}
	
	private void setActiveQuest(int x) {
		switch(x) {
		case 1:
			storage.wolfQuest.setActive(1);
			break;
		case 2:
			storage.spiderQuest.setActive(1);
			break;
		case 3:
			storage.spiderBearQuest.setActive(1);
			break;
		case 4:
			storage.bearQuest.setActive(1);
			break;
		}	
	}
	
	private void selectQuest(int x, int q) {
	    takenQuest[q - 1] = x;
	    String questTitle = "";
	    String questText = "";

	    switch (x) {
	        case 1:
	            questTitle = storage.wolfQuest.getQuestName();
	            questText = storage.wolfQuest.getObjective1() + "\n" + storage.wolfQuest.getObj1Prog() + "/" + storage.wolfQuest.getObj1()
	            + "\n\nReward: " + storage.wolfQuest.getReward() + " coins";
	            break;
	        case 2:
	            questTitle = storage.spiderQuest.getQuestName();
	            questText = storage.spiderQuest.getObjective1() + "\n" + storage.spiderQuest.getObj1Prog() + "/" + storage.spiderQuest.getObj1()
	            + "\n\nReward: " + storage.spiderQuest.getReward() + " coins";
	            break;
	        case 3:
	            questTitle = storage.spiderBearQuest.getQuestName();
	            questText = storage.spiderBearQuest.getObjective1() + "\n" + storage.spiderBearQuest.getObjective2() + "\n\n" +
	                    storage.spiderBearQuest.getObj1Prog() + "/" + storage.spiderBearQuest.getObj1() + "\n" +
	                    storage.spiderBearQuest.getObj2Prog() + "/" + storage.spiderBearQuest.getObj2()
	                    + "\n\nReward: " + storage.spiderBearQuest.getReward() + " coins";
	            break;
	        case 4:
	        	questTitle = storage.bearQuest.getQuestName();
	            questText = storage.bearQuest.getObjective1() + "\n" + storage.bearQuest.getObj1Prog() + "/" + storage.bearQuest.getObj1()
	            + "\n\nReward: " + storage.bearQuest.getReward() + " coins";
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
		int x = rand.nextInt(1, 5);
		boolean newQ = false;
		
		if(newQuest[0] == 0) {
			 newQuest[0] = 1;
			 selectQuest(x, 1);
		}
		 
		x = rand.nextInt(1, 5);
		while(x == takenQuest[0])
			x = rand.nextInt(1, 5);
		
		if(newQuest[1] == 0) {
			newQuest[1] = 1;
			selectQuest(x, 2);
		}
		
		x = rand.nextInt(1, 5);
		while(x == takenQuest[0] || x == takenQuest[1])
			x = rand.nextInt(1, 5);
		 
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
		backBtn.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.25f);
		stage.addActor(backBtn);	
		
//		resetQuestBtn = new TextButton("Reset", storage.buttonStyle);
//		resetQuestBtn.setColor(Color.LIGHT_GRAY);
//		resetQuestBtn.addListener(new ClickListener() {
//    		@Override
//    	    public void clicked(InputEvent event, float x, float y) {     			
//    			resetQuests();
//    			setQuest();
//    			checkAvailableQuests();
//    	    }});
//		resetQuestBtn.setSize(150, 100);
//		resetQuestBtn.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 1.25f);
//		stage.addActor(resetQuestBtn);
		
		timer = new Label("", storage.labelStyle);
		timer.setPosition(backBtn.getX() + backBtn.getWidth() * 2, backBtn.getY());
		stage.addActor(timer);
		
		q1Title = new Label("Some Extermination1", storage.labelStyle);
		q1Title.setPosition(vp.getWorldWidth() / 5f, vp.getWorldHeight() / 1.5f);
		q1Title.setSize(300, 100);
		q1Title.setWrap(true);
		stage.addActor(q1Title);
		
		q2Title = new Label("Some Extermination2", storage.labelStyle);
		q2Title.setPosition(q1Title.getX() + q1Title.getWidth() + 50, vp.getWorldHeight() / 1.5f);
		q2Title.setSize(300, 100);
		q2Title.setWrap(true);
		stage.addActor(q2Title);
		
		q3Title = new Label("Some Extermination3", storage.labelStyle);
		q3Title.setPosition(q2Title.getX() + q2Title.getWidth() + 50, vp.getWorldHeight() / 1.5f);
		q3Title.setSize(300, 100);
		q3Title.setWrap(true);
		stage.addActor(q3Title);
		
		q1Text = new Label("Kill 5 stuff", storage.labelStyle);
		q1Text.setPosition(q1Title.getX(), q1Title.getY() - q1Title.getHeight() * 2);
		q1Text.setSize(300, 200);
		stage.addActor(q1Text);
		
		q2Text = new Label("Kill 3 stuff", storage.labelStyle);
		q2Text.setPosition(q2Title.getX(), q2Title.getY() - q2Title.getHeight() * 2);
		q2Text.setSize(300, 200);
		stage.addActor(q2Text);
		
		q3Text = new Label("Kill 5 stuff \nKill 3 stuff", storage.labelStyle);
		q3Text.setPosition(q3Title.getX(), q3Title.getY() - q3Title.getHeight() * 2);
		q3Text.setSize(300, 200);
		stage.addActor(q3Text);
		
		q1Accept = new TextButton("Accept", storage.buttonStyle);
		q1Accept.setColor(Color.LIGHT_GRAY);
		q1Accept.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			setActiveQuest(takenQuest[0]);
    			q1Accept.setText("Complete");
    			q1Accept.setDisabled(true);
    			q1Accept.setTouchable(Touchable.disabled);
    	    }});
		q1Accept.setSize(150, 70);
		q1Accept.setPosition(q1Text.getX(), q1Text.getY() - q1Text.getHeight());
		stage.addActor(q1Accept);
		
		q2Accept = new TextButton("Accept", storage.buttonStyle);
		q2Accept.setColor(Color.LIGHT_GRAY);
		q2Accept.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			setActiveQuest(takenQuest[1]);
    			q2Accept.setText("Complete");
    			q2Accept.setDisabled(true);
    			q2Accept.setTouchable(Touchable.disabled);
    	    }});
		q2Accept.setSize(150, 70);
		q2Accept.setPosition(q2Text.getX(), q2Text.getY() - q2Text.getHeight());
		stage.addActor(q2Accept);
		
		q3Accept = new TextButton("Accept", storage.buttonStyle);
		q3Accept.setColor(Color.LIGHT_GRAY);
		q3Accept.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			setActiveQuest(takenQuest[2]);
    			q3Accept.setText("Complete");
    			q3Accept.setDisabled(true);
    			q3Accept.setTouchable(Touchable.disabled);
    	    }});
		q3Accept.setSize(150, 70);
		q3Accept.setPosition(q3Text.getX(), q3Text.getY() - q3Text.getHeight());
		stage.addActor(q3Accept);
	}
	
	private void resetQuests() {
		for (int i = 0; i < questDone.length; i++)
	        questDone[i] = 0;

	    for (int i = 0; i < newQuest.length; i++)
	        newQuest[i] = 0;

	    for (int i = 0; i < takenQuest.length; i++)
	        takenQuest[i] = 0;
	    
	    storage.wolfQuest.setActive(0);
	    storage.wolfQuest.setObj1Prog(0);
	    storage.spiderQuest.setActive(0);
	    storage.spiderQuest.setObj1Prog(0);
	    storage.spiderBearQuest.setActive(0);
	    storage.spiderBearQuest.setObj1Prog(0);
	    storage.spiderBearQuest.setObj2Prog(0);
	    storage.bearQuest.setActive(0);
	    storage.bearQuest.setObj1Prog(0);
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

	        createComponents();
	    }

	    if (checkQuests && activeQuests) {
	        checkCompletedQuest();
	        checkQuests = false;
	        selectQuest(takenQuest[0], 1);
			selectQuest(takenQuest[1], 2);
			selectQuest(takenQuest[2], 3);
	    }

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
	
}