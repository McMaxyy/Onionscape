package com.onionscape.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import scenes.*;

public class GameScreen implements Screen {
	private Game game;
	private FightScene fightScene;
	private Home home;
	private BerserkerSkillTree zerkerTree;
	private Inventory inventory;
	private LoadingScreen loadingScreen;
	public ForestMap forestMap;
	private RaidTextScenes textScene;
	private StartScreen startScreen;
	private Merchant merchant;
	private Settings settings;
	private SlotMinigame slot;
	private QuestLog quest;
	private static QuestLog questSave;
	private Viewport viewport;
	public static boolean newGame = true;
	
	private static final int MIN_WIDTH = 1280;
	private static final int MIN_HEIGHT = 720;
	private static final int MAX_WIDTH = 1920;
    private static final int MAX_HEIGHT = 1080;
    public static int SELECTED_WIDTH = MAX_WIDTH;
    public static int SELECTED_HEIGHT = MAX_HEIGHT;
    
    public static final int LOADING_SCREEN = 0;
    public static final int HOME = 1;
    public static final int ZERKER_TREE = 2;
    public static final int INVENTORY = 3;
    public static final int FIGHT_SCENE = 4;
    public static final int FOREST_MAP = 5;    
    public static final int TEXT_SCENE = 6;
    public static final int START_SCREEN = 7;
    public static final int MERCHANT = 8;
    public static final int SETTINGS = 9;
    public static final int SLOT_GAME = 10;
    public static final int FOREST_MAP_QUICK = 11;
    public static final int QUEST_LOG = 12;
    private int currentState;
    
    private boolean isTransitioning;
    private float transitionTime;
    private float elapsedTime;
    private ShapeRenderer shapeRenderer = new ShapeRenderer();

	public GameScreen(Game game) {		
		this.game = game;
		viewport = new FitViewport(SELECTED_WIDTH, SELECTED_HEIGHT);	
		Gdx.graphics.setUndecorated(true);
		Gdx.graphics.setWindowedMode(SELECTED_WIDTH, SELECTED_HEIGHT);
	    setCurrentState(LOADING_SCREEN);
	    
	    isTransitioning = false;
        transitionTime = 0.55f;
        elapsedTime = 0.0f;       
	}
	
	public void setCurrentState(int newState) {
	    currentState = newState;
	    switch (currentState) {
		    case LOADING_SCREEN:
	        	this.loadingScreen = new LoadingScreen(viewport, game, this);
	            Gdx.input.setInputProcessor(loadingScreen.stage);
	            break;
	        case FIGHT_SCENE:
	        	this.fightScene = new FightScene(viewport, game, this);
	            Gdx.input.setInputProcessor(fightScene.stage);
	            break;
	        case HOME:
	        	this.home = new Home(viewport, game, this);
	            Gdx.input.setInputProcessor(home.stage);
	            break;
	        case ZERKER_TREE:
	        	this.zerkerTree = new BerserkerSkillTree(viewport, game, this);
	            Gdx.input.setInputProcessor(zerkerTree.stage);
	            break;
	        case INVENTORY:
	        	this.inventory = new Inventory(viewport, game, this);
	            Gdx.input.setInputProcessor(inventory.stage);
	            break;
	        case FOREST_MAP:
	        	if(this.forestMap == null)
	        		this.forestMap = new ForestMap(viewport, game, this);
	            Gdx.input.setInputProcessor(forestMap.stage);
	            break;
	        case TEXT_SCENE:
	        	this.textScene = new RaidTextScenes(viewport, game, this);
	            Gdx.input.setInputProcessor(textScene.stage);
	            break;
	        case START_SCREEN:
	        	this.startScreen = new StartScreen(viewport, game, this);
	            Gdx.input.setInputProcessor(startScreen.stage);
	            break;
	        case MERCHANT:
	        	this.merchant = new Merchant(viewport, game, this);
	            Gdx.input.setInputProcessor(merchant.stage);
	            break;
	        case SETTINGS:
	            this.settings = new Settings(viewport, game, this);
	            Gdx.input.setInputProcessor(settings.stage);
	            break;
	        case SLOT_GAME:
	        	this.slot = new SlotMinigame(viewport, game, this);
	            Gdx.input.setInputProcessor(slot.stage);
	            break;
	        case FOREST_MAP_QUICK:
	        	this.forestMap = new ForestMap(viewport, game, this);
	            Gdx.input.setInputProcessor(forestMap.stage);
	            break;
	        case QUEST_LOG:
	        	if(this.quest == null && questSave != null)
	        		this.quest = questSave;
	        	else if(this.quest == null) {
	        		this.quest = new QuestLog(viewport, game, this);
	        		questSave = quest;
	        	}
	        	else if(StartScreen.isFreshLoad()) {
	        		this.quest = new QuestLog(viewport, game, this);
	        		questSave = quest;
	        	}
	        		
	            Gdx.input.setInputProcessor(quest.stage);
	            break;
	    }
	}
	
	public void startTransition() {
        isTransitioning = true;
        elapsedTime = 0.0f;
    }
	
	public void switchToNewState(int scene) {
        startTransition();
        setCurrentState(scene);
    }

	@Override
	public void render(float delta) {
		shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
		
	    Gdx.gl.glClearColor(55 / 255f, 55 / 255f, 55 / 255f, 1);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	    
	    if (isTransitioning) {
            // Perform fade in/out logic during transition
            float alpha = Math.min(1.0f, elapsedTime / transitionTime);

            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

            // Draw a black rectangle with alpha to create fade effect
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, alpha);
            shapeRenderer.rect(0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
            shapeRenderer.end();

            Gdx.gl.glDisable(GL20.GL_BLEND);

            elapsedTime += delta;

            // If the transition is complete, switch to the new state
            if (alpha >= transitionTime)
                isTransitioning = false;
        } else {
            // Render the current scene
        	switch (currentState) {
		    case LOADING_SCREEN:
	            loadingScreen.render(delta);
	            break;
	        case FIGHT_SCENE:
	            fightScene.render(delta);
	            break;
	        case HOME:
	            home.render(delta);
	            break;
	        case ZERKER_TREE:
	        	zerkerTree.render(delta);
	            break;
	        case INVENTORY:
	        	inventory.render(delta);
	            break;
	        case FOREST_MAP:
	        	forestMap.render(delta);
	        	break;
	        case TEXT_SCENE:
	        	textScene.render(delta);
	        	break;
	        case START_SCREEN:
	        	startScreen.render(delta);
	        	break;
	        case MERCHANT:
	        	merchant.render(delta);
	        	break;
	        case SETTINGS:
	        	settings.render(delta);
	        	break;
	        case SLOT_GAME:
	        	slot.render(delta);
	        	break;
	        case FOREST_MAP_QUICK:
	        	forestMap.render(delta);
	        	break;
	        case QUEST_LOG:
	        	quest.render(delta);
	        	break;
        	}
        }    
	}

	@Override
	public void show() {
			
	}

	@Override
	public void resize(int width, int height) {
        int finalWidth = Math.min(MAX_WIDTH, Math.max(MIN_WIDTH, width));
        int finalHeight = Math.min(MAX_HEIGHT, Math.max(MIN_HEIGHT, height));

        // Update the viewport with the adjusted width and height
        viewport.update(finalWidth, finalHeight, true);
        viewport.apply();
        viewport.getCamera().update(); 
        
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);
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
		MusicManager.getInstance().stopCurrentTrack();
	}

	@Override
	public void dispose() {
		fightScene.dispose();
		home.dispose();
		inventory.dispose();
		zerkerTree.dispose();
		loadingScreen.dispose();
		textScene.dispose();
		merchant.dispose();
		settings.dispose();
		slot.dispose();
		quest.dispose();
		shapeRenderer.dispose();
		forestMap.dispose();
	}

	public static QuestLog getQuestSave() {
		return questSave;
	}

	public static void setQuestSave(QuestLog questSave) {
		GameScreen.questSave = questSave;
	}
}

