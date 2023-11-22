package scenes;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;

import player.Player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import storage.Storage;

public class ForestMap implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Storage storage;
	private Game game;
	private GameScreen gameScreen; 
	private SpriteBatch mapBatch = new SpriteBatch();
	private SpriteBatch onionBatch = new SpriteBatch();
	private Texture mapTexture, playerTexture;
	private static TextButton button1, button2, button3, button4, button5, button6, button7, button8,
	button9, button10, button11, button12, button13, button14;
	private static TextButton button15, button16, button17, button18, button19, button20, button21, button22, button23;
	private static TextButton button24, button25, button26, button27, button28, button29, button30, 
	button31, button32, button33, button34;
	private TextButton exit1, exit2, exit3;
	private static TextButton[] buttons = new TextButton[34];
	private static int location = 0, lastLocation = 0;
	private Random rand = new Random();
	private boolean moved = false;
	public static int encounter = 0, ex;
	
	public ForestMap(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		mapTexture = Storage.assetManager.get("maps/ForestMap.png", Texture.class);
		mapTexture.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
		playerTexture = Storage.assetManager.get("player/MapIcon.png", Texture.class);
		playerTexture.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
		Merchant.raid = true;
		FightScene.normal = FightScene.elite = FightScene.boss = false;
				
		if(GameScreen.newGame) {	
			createComponents();	
			location = 0;
			ex = rand.nextInt(3);
			
			if(ex == 0)
				exit1.setColor(Color.GREEN);
			else if(ex == 1) 
				exit2.setColor(Color.GREEN);
			else 
				exit3.setColor(Color.GREEN);			
		}
		
		disableButtons();
		enableButtons();
	}
	
	private String setEncounter(int loc, TextButton button) {
		int x = rand.nextInt(1, 11);
		
		if(!button.getText().toString().equals("")) {
			switch(loc) {
			case 1:
				return "F";
			case 2:
				if(x > 5)
					return "R";
				else
					return "F";
			case 3:
				if(x > 7)
					return "M";
				else if (x > 4)
					return "R";
				else
					return "F";
			case 4:
				if(x > 6)
					return "T";
				else if(x > 4)
					return "R";
				else if(x > 2)
					return "E";
				else
					return "F";
			case 5:
				return "F";
			case 6:
				if(x > 5)
					return "R";
				else
					return "F";
			case 7:
				if(x > 6)
					return "T";
				else if(x > 3)
					return "R";
				else
					return "F";
			case 8:
				if(x > 5)
					return "R";
				else
					return "F";
			case 9:
				return "";
			case 10:
				return "F";
			case 11:
				return "F";
			case 12:
				return "B";
			case 13:
				return "R";
			case 14:
				if(x > 5)
					return "R";
				else
					return "F";
			case 15:
				if(x > 4)
					return "T";
				else
					return "E";
			case 16:
				if(x > 5)
					return "R";
				else
					return "F";
			case 17:
				if(x > 5)
					return "R";
				else
					return "F";
			case 18:
				if(x > 5)
					return "B";
				else
					return "R";
			case 19:
				return "R";
			case 20:
				return "F";
			case 21:
				return "R";
			case 22:
				if(x > 4)
					return "M";
				else
					return "R";
			case 23:
				return "F";
			case 24:
				return "F";
			case 25:
				if(x > 4)
					return "T";
				else
					return "E";
			case 26:
				return "F";
			case 27:
				if(x > 4)
					return "M";
				else
					return "R";
			case 28:
				return "F";
			case 29:
				return "B";
			case 30:
				return "R";
			case 31:
				if(x > 8)
					return "E";
				else if (x > 4)
					return "R";
				else
					return "F";
			case 32:
				return "R";
			case 33:
				if(x > 6)
					return "E";
				else
					return "R";
			case 34:
				if(x > 5)
					return "R";
				else
					return "F";
			default:
				return null;
			}
		}
		else
			return null;		
	}
	
	private void createComponents() {
		button1 = new TextButton("1", storage.buttonStyle);
		button1.setColor(Color.LIGHT_GRAY);
		button1.setBounds(vp.getWorldWidth() / 1.865f, vp.getWorldHeight() / 4f, 70, 70);
		buttons[0] = button1;
		button1.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
				lastLocation = location;
				location = 1;				
				btnClick(button1.getText().toString());
    	    }});
		button1.setText(setEncounter(1, button1));
		
		button2 = new TextButton("2", storage.buttonStyle);
		button2.setColor(Color.LIGHT_GRAY);
		button2.setBounds(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[1] = button2;
		button2.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {   				
    			lastLocation = location;	
    			location = 2;
    			btnClick(button2.getText().toString());
    	    }});
		button2.setText(setEncounter(2, button2));
		
		button3 = new TextButton("3", storage.buttonStyle);
		button3.setColor(Color.LIGHT_GRAY);
		button3.setBounds(vp.getWorldWidth() / 5.2f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[2] = button3;
		button3.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 3;
				btnClick(button3.getText().toString());
    	    }});
		button3.setText(setEncounter(3, button3));
		
		button4 = new TextButton("4", storage.buttonStyle);
		button4.setColor(Color.LIGHT_GRAY);
		button4.setBounds(vp.getWorldWidth() / 100f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[3] = button4;
		button4.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 4;
				btnClick(button4.getText().toString());
    	    }});
		button4.setText(setEncounter(4, button4));
		
		button5 = new TextButton("5", storage.buttonStyle);
		button5.setColor(Color.LIGHT_GRAY);
		button5.setBounds(vp.getWorldWidth() / 6.78f, vp.getWorldHeight() / 4f, 70, 70);
		buttons[4] = button5;
		button5.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 5;
				btnClick(button5.getText().toString());
    	    }});
		button5.setText(setEncounter(5, button5));
		
		button6 = new TextButton("6", storage.buttonStyle);
		button6.setColor(Color.LIGHT_GRAY);
		button6.setBounds(vp.getWorldWidth() / 13f, vp.getWorldHeight() / 2.55f, 70, 70);
		buttons[5] = button6;
		button6.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 6;
				btnClick(button6.getText().toString());
    	    }});
		button6.setText(setEncounter(6, button6));
		
		button7 = new TextButton("7", storage.buttonStyle);
		button7.setColor(Color.LIGHT_GRAY);
		button7.setBounds(vp.getWorldWidth() / 100f, vp.getWorldHeight() / 2.55f, 70, 70);
		buttons[6] = button7;
		button7.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 7;
				btnClick(button7.getText().toString());
    	    }});
		button7.setText(setEncounter(7, button7));
		
		button8 = new TextButton("8", storage.buttonStyle);
		button8.setColor(Color.LIGHT_GRAY);
		button8.setBounds(vp.getWorldWidth() / 4.25f, vp.getWorldHeight() / 4f, 70, 70);
		buttons[7] = button8;
		button8.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 8;
				btnClick(button8.getText().toString());
    	    }});
		button8.setText(setEncounter(8, button8));
		
		button9 = new TextButton("9", storage.buttonStyle);
		button9.setColor(Color.LIGHT_GRAY);
		button9.setBounds(vp.getWorldWidth() / 1.63f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[8] = button9;
		button9.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 9;
				btnClick(button9.getText().toString());
    	    }});
		button9.setText(setEncounter(9, button9));
		
		button10 = new TextButton("10", storage.buttonStyle);
		button10.setColor(Color.LIGHT_GRAY);
		button10.setBounds(vp.getWorldWidth() / 1.455f, vp.getWorldHeight() / 6f, 70, 70);
		buttons[9] = button10;
		button10.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 10;
				btnClick(button10.getText().toString());
    	    }});
		button10.setText(setEncounter(10, button10));
		
		button11 = new TextButton("11", storage.buttonStyle);
		button11.setColor(Color.LIGHT_GRAY);
		button11.setBounds(vp.getWorldWidth() / 1.25f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[10] = button11;
		button11.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 11;
				btnClick(button11.getText().toString());
    	    }});
		button11.setText(setEncounter(11, button11));
		
		button12 = new TextButton("12", storage.buttonStyle);
		button12.setColor(Color.LIGHT_GRAY);
		button12.setBounds(vp.getWorldWidth() / 1.05f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[11] = button12;
		button12.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 12;
				btnClick(button12.getText().toString());
    	    }});
		button12.setText(setEncounter(12, button12));
		
		button13 = new TextButton("13", storage.buttonStyle);
		button13.setColor(Color.LIGHT_GRAY);
		button13.setBounds(vp.getWorldWidth() / 1.345f, vp.getWorldHeight() / 2.8f, 70, 70);
		buttons[12] = button13;
		button13.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 13;
				btnClick(button13.getText().toString());
    	    }});
		button13.setText(setEncounter(13, button13));
		
		button14 = new TextButton("14", storage.buttonStyle);
		button14.setColor(Color.LIGHT_GRAY);
		button14.setBounds(vp.getWorldWidth() / 1.075f, vp.getWorldHeight() / 2.8f, 70, 70);
		buttons[13] = button14;
		button14.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 14;
				btnClick(button14.getText().toString());
    	    }});
		button14.setText(setEncounter(14, button14));
		
		button15 = new TextButton("15", storage.buttonStyle);
		button15.setColor(Color.LIGHT_GRAY);
		button15.setBounds(vp.getWorldWidth() / 1.115f, vp.getWorldHeight() / 2.2f, 70, 70);
		buttons[14] = button15;
		button15.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 15;
				btnClick(button15.getText().toString());
    	    }});
		button15.setText(setEncounter(15, button15));
		
		button16 = new TextButton("16", storage.buttonStyle);
		button16.setColor(Color.LIGHT_GRAY);
		button16.setBounds(vp.getWorldWidth() / 1.25f, vp.getWorldHeight() / 2.2f, 70, 70);
		buttons[15] = button16;
		button16.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 16;
				btnClick(button16.getText().toString());
    	    }});
		button16.setText(setEncounter(16, button16));
		
		button17 = new TextButton("17", storage.buttonStyle);
		button17.setColor(Color.LIGHT_GRAY);
		button17.setBounds(vp.getWorldWidth() / 1.13f, vp.getWorldHeight() / 1.655f, 70, 70);
		buttons[16] = button17;
		button17.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 17;
				btnClick(button17.getText().toString());
    	    }});
		button17.setText(setEncounter(17, button17));
		
		button18 = new TextButton("18", storage.buttonStyle);
		button18.setColor(Color.LIGHT_GRAY);
		button18.setBounds(vp.getWorldWidth() / 1.55f, vp.getWorldHeight() / 2.03f, 70, 70);
		buttons[17] = button18;
		button18.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 18;
				btnClick(button18.getText().toString());
    	    }});
		button18.setText(setEncounter(18, button18));
		
		button19 = new TextButton("19", storage.buttonStyle);
		button19.setColor(Color.LIGHT_GRAY);
		button19.setBounds(vp.getWorldWidth() / 2.03f, vp.getWorldHeight() / 1.8f, 70, 70);
		buttons[18] = button19;
		button19.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 19;
				btnClick(button19.getText().toString());
    	    }});
		button19.setText(setEncounter(19, button19));
		
		button20 = new TextButton("20", storage.buttonStyle);
		button20.setColor(Color.LIGHT_GRAY);
		button20.setBounds(vp.getWorldWidth() / 2.19f, vp.getWorldHeight() / 2.2f, 70, 70);
		buttons[19] = button20;
		button20.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 20;
				btnClick(button20.getText().toString());
    	    }});
		button20.setText(setEncounter(20, button20));
		
		button21 = new TextButton("21", storage.buttonStyle);
		button21.setColor(Color.LIGHT_GRAY);
		button21.setBounds(vp.getWorldWidth() / 2.55f, vp.getWorldHeight() / 1.72f, 70, 70);
		buttons[20] = button21;
		button21.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 21;
				btnClick(button21.getText().toString());
    	    }});
		button21.setText(setEncounter(21, button21));
		
		button22 = new TextButton("22", storage.buttonStyle);
		button22.setColor(Color.LIGHT_GRAY);
		button22.setBounds(vp.getWorldWidth() / 3.5f, vp.getWorldHeight() / 1.72f, 70, 70);
		buttons[21] = button22;
		button22.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 22;
				btnClick(button22.getText().toString());
    	    }});
		button22.setText(setEncounter(22, button22));
		
		button23 = new TextButton("23", storage.buttonStyle);
		button23.setColor(Color.LIGHT_GRAY);
		button23.setBounds(vp.getWorldWidth() / 16.5f, vp.getWorldHeight() / 1.6f, 70, 70);
		buttons[22] = button23;
		button23.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 23;
				btnClick(button23.getText().toString());
    	    }});
		button23.setText(setEncounter(23, button23));
		
		button24 = new TextButton("24", storage.buttonStyle);
		button24.setColor(Color.LIGHT_GRAY);
		button24.setBounds(vp.getWorldWidth() / 2.75f, vp.getWorldHeight() / 1.37f, 70, 70);
		buttons[23] = button24;
		button24.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 24;
				btnClick(button24.getText().toString());
    	    }});
		button24.setText(setEncounter(24, button24));
		
		button25 = new TextButton("25", storage.buttonStyle);
		button25.setColor(Color.LIGHT_GRAY);
		button25.setBounds(vp.getWorldWidth() / 2.18f, vp.getWorldHeight() / 1.37f, 70, 70);
		buttons[24] = button25;
		button25.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 25;
				btnClick(button25.getText().toString());
    	    }});
		button25.setText(setEncounter(25, button25));
		
		button26 = new TextButton("26", storage.buttonStyle);
		button26.setColor(Color.LIGHT_GRAY);
		button26.setBounds(vp.getWorldWidth() / 1.7f, vp.getWorldHeight() / 1.37f, 70, 70);
		buttons[25] = button26;
		button26.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 26;
				btnClick(button26.getText().toString());
    	    }});
		button26.setText(setEncounter(26, button26));
		
		button27 = new TextButton("27", storage.buttonStyle);
		button27.setColor(Color.LIGHT_GRAY);
		button27.setBounds(vp.getWorldWidth() / 1.43f, vp.getWorldHeight() / 1.37f, 70, 70);
		buttons[26] = button27;
		button27.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 27;
				btnClick(button27.getText().toString());
    	    }});
		button27.setText(setEncounter(27, button27));
		
		button28 = new TextButton("28", storage.buttonStyle);
		button28.setColor(Color.LIGHT_GRAY);
		button28.setBounds(vp.getWorldWidth() / 1.28f, vp.getWorldHeight() / 1.107f, 70, 70);
		buttons[27] = button28;
		button28.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 28;
				btnClick(button28.getText().toString());
    	    }});
		button28.setText(setEncounter(28, button28));
		
		button29 = new TextButton("29", storage.buttonStyle);
		button29.setColor(Color.LIGHT_GRAY);
		button29.setBounds(vp.getWorldWidth() / 1.05f, vp.getWorldHeight() / 1.107f, 70, 70);
		buttons[28] = button29;
		button29.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 29;
				btnClick(button29.getText().toString());
    	    }});
		button29.setText(setEncounter(29, button29));
		
		button30 = new TextButton("30", storage.buttonStyle);
		button30.setColor(Color.LIGHT_GRAY);
		button30.setBounds(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 1.107f, 70, 70);
		buttons[29] = button30;
		button30.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 30;
				btnClick(button30.getText().toString());
    	    }});
		button30.setText(setEncounter(30, button30));
		
		button31 = new TextButton("31", storage.buttonStyle);
		button31.setColor(Color.LIGHT_GRAY);
		button31.setBounds(vp.getWorldWidth() / 1.865f, vp.getWorldHeight() / 1.107f, 70, 70);
		buttons[30] = button31;
		button31.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 31;
				btnClick(button31.getText().toString());
    	    }});
		button31.setText(setEncounter(31, button31));
		
		button32 = new TextButton("32", storage.buttonStyle);
		button32.setColor(Color.LIGHT_GRAY);
		button32.setBounds(vp.getWorldWidth() / 2.28f, vp.getWorldHeight() / 1.107f, 70, 70);
		buttons[31] = button32;
		button32.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 32;
				btnClick(button32.getText().toString());
    	    }});
		button32.setText(setEncounter(32, button32));
		
		button33 = new TextButton("33", storage.buttonStyle);
		button33.setColor(Color.LIGHT_GRAY);
		button33.setBounds(vp.getWorldWidth() / 6.9f, vp.getWorldHeight() / 1.26f, 70, 70);
		buttons[32] = button33;
		button33.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 33;
				btnClick(button33.getText().toString());
    	    }});
		button33.setText(setEncounter(33, button33));
		
		button34 = new TextButton("34", storage.buttonStyle);
		button34.setColor(Color.LIGHT_GRAY);
		button34.setBounds(vp.getWorldWidth() / 8f, vp.getWorldHeight() / 1.075f, 70, 70);
		buttons[33] = button34;
		button34.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 34;
				btnClick(button34.getText().toString());
    	    }});
		button34.setText(setEncounter(34, button34));
		
		exit1 = new TextButton("", storage.buttonStyle);
		exit1.setColor(Color.RED);
		exit1.setTouchable(Touchable.disabled);
		exit1.setBounds(vp.getWorldWidth() / 1.865f, vp.getWorldHeight() / 1.03f, 70, 30);
		exit1.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(exit1.getColor().equals(Color.GREEN)) {
    				Player.gainCoins(Player.getRaidCoins());
    				Player.setRaidCoins(0);
    				gameScreen.setCurrentState(GameScreen.HOME);
    			}  				
    	    }});
		
		exit2 = new TextButton("", storage.buttonStyle);
		exit2.setColor(Color.RED);
		exit2.setTouchable(Touchable.disabled);
		exit2.setBounds(vp.getWorldWidth() / 500f, vp.getWorldHeight() / 1.075f, 50, 70);
		exit2.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(exit2.getColor().equals(Color.GREEN)) {
    				Player.gainCoins(Player.getRaidCoins());
    				Player.setRaidCoins(0);
    				gameScreen.setCurrentState(GameScreen.HOME);
    			}  
    	    }});
		
		exit3 = new TextButton("", storage.buttonStyle);
		exit3.setColor(Color.RED);
		exit3.setTouchable(Touchable.disabled);
		exit3.setBounds(vp.getWorldWidth() / 1.029f, vp.getWorldHeight() / 1.655f, 50, 70);
		exit3.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(exit3.getColor().equals(Color.GREEN)) {
    				Player.gainCoins(Player.getRaidCoins());
    				Player.setRaidCoins(0);
    				gameScreen.setCurrentState(GameScreen.HOME);
    			}  
    	    }});
		
		stage.addActor(button1);
		stage.addActor(button2);
		stage.addActor(button3);
		stage.addActor(button4);
		stage.addActor(button5);
		stage.addActor(button6);
		stage.addActor(button7);
		stage.addActor(button8);
		stage.addActor(button9);
		stage.addActor(button10);
		stage.addActor(button11);
		stage.addActor(button12);
		stage.addActor(button13);
		stage.addActor(button14);
		stage.addActor(button15);
		stage.addActor(button16);
		stage.addActor(button17);
		stage.addActor(button18);
		stage.addActor(button19);
		stage.addActor(button20);
		stage.addActor(button21);
		stage.addActor(button22);
		stage.addActor(button23);
		stage.addActor(button24);
		stage.addActor(button25);
		stage.addActor(button26);
		stage.addActor(button27);
		stage.addActor(button28);
		stage.addActor(button29);
		stage.addActor(button30);
		stage.addActor(button31);
		stage.addActor(button32);
		stage.addActor(button33);
		stage.addActor(button34);
		stage.addActor(exit1);
		stage.addActor(exit2);
		stage.addActor(exit3);
	}
	
	private void btnClick(String action) {
		disableButtons();
		moved = true;
		enableButtons();	
		
		switch(action) {
		case "F":
			FightScene.elite = FightScene.boss = false;
			FightScene.normal = true;
			gameScreen.setCurrentState(GameScreen.FIGHT_SCENE);			
			break;
		case "R":
			encounter = 1;
			gameScreen.setCurrentState(GameScreen.TEXT_SCENE);
			break;
		case "B":
			FightScene.normal = FightScene.elite = false;
			FightScene.boss = true;
			gameScreen.setCurrentState(GameScreen.FIGHT_SCENE);
			break;
		case "T":
			encounter = 2;
			gameScreen.setCurrentState(GameScreen.TEXT_SCENE);
			break;
		case "M":
			gameScreen.setCurrentState(GameScreen.MERCHANT);
			break;
		case "E":
			FightScene.normal = FightScene.boss = false;
			FightScene.elite = true;
			gameScreen.setCurrentState(GameScreen.FIGHT_SCENE);			
			break;
		}
	}
	
	private void disableButtons() {
		for(Integer i = 0; i < buttons.length; i++) {
			buttons[i].setColor(Color.GRAY);
			buttons[i].setTouchable(Touchable.disabled);
//			i += 1;
//			buttons[i - 1].setText(i.toString());
//			i -= 1;
		}
	}
	
	private void enableButtons() {		
		switch(location) {
		case 0:
			button1.setTouchable(Touchable.enabled);
			button2.setTouchable(Touchable.enabled);
			button9.setTouchable(Touchable.enabled);
			button1.setColor(Color.LIGHT_GRAY);
			button2.setColor(Color.LIGHT_GRAY);
			button9.setColor(Color.LIGHT_GRAY);
			break;
		case 1:
			button9.setTouchable(Touchable.enabled);
			button20.setTouchable(Touchable.enabled);
			button2.setTouchable(Touchable.enabled);
			button9.setColor(Color.LIGHT_GRAY);
			button20.setColor(Color.LIGHT_GRAY);
			button2.setColor(Color.LIGHT_GRAY);
			button1.setText("P");
			break;
		case 2:
			button1.setTouchable(Touchable.enabled);
			button8.setTouchable(Touchable.enabled);
			button3.setTouchable(Touchable.enabled);
			button1.setColor(Color.LIGHT_GRAY);
			button8.setColor(Color.LIGHT_GRAY);
			button3.setColor(Color.LIGHT_GRAY);
			button2.setText("P");
			break;
		case 3:
			button4.setTouchable(Touchable.enabled);
			button5.setTouchable(Touchable.enabled);
			button8.setTouchable(Touchable.enabled);
			button2.setTouchable(Touchable.enabled);
			button4.setColor(Color.LIGHT_GRAY);
			button5.setColor(Color.LIGHT_GRAY);
			button8.setColor(Color.LIGHT_GRAY);
			button2.setColor(Color.LIGHT_GRAY);
			button3.setText("P");
			break;
		case 4:
			button3.setTouchable(Touchable.enabled);
			button5.setTouchable(Touchable.enabled);
			button3.setColor(Color.LIGHT_GRAY);
			button5.setColor(Color.LIGHT_GRAY);
			button4.setText("P");
			break;
		case 5:
			button3.setTouchable(Touchable.enabled);
			button6.setTouchable(Touchable.enabled);
			button3.setColor(Color.LIGHT_GRAY);
			button6.setColor(Color.LIGHT_GRAY);
			button5.setText("P");
			break;
		case 6:
			button5.setTouchable(Touchable.enabled);
			button7.setTouchable(Touchable.enabled);
			button5.setColor(Color.LIGHT_GRAY);
			button7.setColor(Color.LIGHT_GRAY);
			button6.setText("P");
			break;
		case 7:
			button6.setTouchable(Touchable.enabled);
			button6.setColor(Color.LIGHT_GRAY);
			button7.setText("P");
			break;
		case 8:
			button3.setTouchable(Touchable.enabled);
			button21.setTouchable(Touchable.enabled);
			button20.setTouchable(Touchable.enabled);
			button2.setTouchable(Touchable.enabled);
			button3.setColor(Color.LIGHT_GRAY);
			button21.setColor(Color.LIGHT_GRAY);
			button20.setColor(Color.LIGHT_GRAY);
			button2.setColor(Color.LIGHT_GRAY);
			button8.setText("P");
			break;
		case 9:
			button1.setTouchable(Touchable.enabled);
			button10.setTouchable(Touchable.enabled);
			button11.setTouchable(Touchable.enabled);
			button1.setColor(Color.LIGHT_GRAY);
			button10.setColor(Color.LIGHT_GRAY);
			button11.setColor(Color.LIGHT_GRAY);
			button9.setText("P");
			break;
		case 10:
			button9.setTouchable(Touchable.enabled);
			button13.setTouchable(Touchable.enabled);
			button14.setTouchable(Touchable.enabled);
			button11.setTouchable(Touchable.enabled);
			button11.setColor(Color.LIGHT_GRAY);
			button9.setColor(Color.LIGHT_GRAY);
			button13.setColor(Color.LIGHT_GRAY);
			button14.setColor(Color.LIGHT_GRAY);
			button10.setText("P");
			break;
		case 11:
			button12.setTouchable(Touchable.enabled);
			button9.setTouchable(Touchable.enabled);
			button10.setTouchable(Touchable.enabled);
			button12.setColor(Color.LIGHT_GRAY);
			button9.setColor(Color.LIGHT_GRAY);
			button10.setColor(Color.LIGHT_GRAY);
			button11.setText("P");
			break;
		case 12:
			button11.setTouchable(Touchable.enabled);
			button11.setColor(Color.LIGHT_GRAY);
			button12.setText("P");
			break;
		case 13:
			button16.setTouchable(Touchable.enabled);
			button18.setTouchable(Touchable.enabled);
			button10.setTouchable(Touchable.enabled);
			button14.setTouchable(Touchable.enabled);
			button14.setColor(Color.LIGHT_GRAY);
			button10.setColor(Color.LIGHT_GRAY);
			button16.setColor(Color.LIGHT_GRAY);
			button18.setColor(Color.LIGHT_GRAY);
			button13.setText("P");
			break;
		case 14:
			button15.setTouchable(Touchable.enabled);
			button13.setTouchable(Touchable.enabled);
			button15.setColor(Color.LIGHT_GRAY);
			button13.setColor(Color.LIGHT_GRAY);
			button14.setText("P");
			break;
		case 15:
			button14.setTouchable(Touchable.enabled);
			button14.setColor(Color.LIGHT_GRAY);
			button15.setText("P");
			break;
		case 16:
			button13.setTouchable(Touchable.enabled);
			button17.setTouchable(Touchable.enabled);
			button18.setTouchable(Touchable.enabled);
			button13.setColor(Color.LIGHT_GRAY);
			button17.setColor(Color.LIGHT_GRAY);
			button18.setColor(Color.LIGHT_GRAY);
			button16.setText("P");
			break;
		case 17:
			button16.setTouchable(Touchable.enabled);
			button17.setText("P");
			if(exit3.getColor().equals(Color.GREEN))
				exit3.setTouchable(Touchable.enabled);
			button16.setColor(Color.LIGHT_GRAY);
			break;
		case 18:
			button16.setTouchable(Touchable.enabled);
			button19.setTouchable(Touchable.enabled);
			button20.setTouchable(Touchable.enabled);
			button16.setColor(Color.LIGHT_GRAY);
			button19.setColor(Color.LIGHT_GRAY);
			button20.setColor(Color.LIGHT_GRAY);
			button18.setText("P");
			break;
		case 19:
			button25.setTouchable(Touchable.enabled);
			button26.setTouchable(Touchable.enabled);
			button20.setTouchable(Touchable.enabled);
			button18.setTouchable(Touchable.enabled);
			button25.setColor(Color.LIGHT_GRAY);
			button26.setColor(Color.LIGHT_GRAY);
			button20.setColor(Color.LIGHT_GRAY);
			button18.setColor(Color.LIGHT_GRAY);
			button19.setText("P");
			break;
		case 20:
			button19.setTouchable(Touchable.enabled);
			button18.setTouchable(Touchable.enabled);
			button8.setTouchable(Touchable.enabled);
			button21.setTouchable(Touchable.enabled);
			button19.setColor(Color.LIGHT_GRAY);
			button18.setColor(Color.LIGHT_GRAY);
			button8.setColor(Color.LIGHT_GRAY);
			button21.setColor(Color.LIGHT_GRAY);
			button20.setText("P");
			break;
		case 21:
			button20.setTouchable(Touchable.enabled);
			button24.setTouchable(Touchable.enabled);
			button22.setTouchable(Touchable.enabled);
			button20.setColor(Color.LIGHT_GRAY);
			button24.setColor(Color.LIGHT_GRAY);
			button22.setColor(Color.LIGHT_GRAY);
			button21.setText("P");
			break;
		case 22:
			button21.setTouchable(Touchable.enabled);
			button24.setTouchable(Touchable.enabled);
			button23.setTouchable(Touchable.enabled);
			button21.setColor(Color.LIGHT_GRAY);
			button24.setColor(Color.LIGHT_GRAY);
			button23.setColor(Color.LIGHT_GRAY);
			button22.setText("P");
			break;
		case 23:
			button22.setTouchable(Touchable.enabled);
			button33.setTouchable(Touchable.enabled);
			button22.setColor(Color.LIGHT_GRAY);
			button33.setColor(Color.LIGHT_GRAY);
			button23.setText("P");
			break;
		case 24:
			button22.setTouchable(Touchable.enabled);
			button32.setTouchable(Touchable.enabled);
			button22.setColor(Color.LIGHT_GRAY);
			button32.setColor(Color.LIGHT_GRAY);
			button24.setText("P");
			break;
		case 25:
			button19.setTouchable(Touchable.enabled);
			button26.setTouchable(Touchable.enabled);
			button19.setColor(Color.LIGHT_GRAY);
			button26.setColor(Color.LIGHT_GRAY);
			button25.setText("P");
			break;
		case 26:
			button19.setTouchable(Touchable.enabled);
			button25.setTouchable(Touchable.enabled);
			button30.setTouchable(Touchable.enabled);
			button27.setTouchable(Touchable.enabled);
			button19.setColor(Color.LIGHT_GRAY);
			button25.setColor(Color.LIGHT_GRAY);
			button30.setColor(Color.LIGHT_GRAY);
			button27.setColor(Color.LIGHT_GRAY);
			button26.setText("P");
			break;
		case 27:
			button28.setTouchable(Touchable.enabled);
			button26.setTouchable(Touchable.enabled);
			button30.setTouchable(Touchable.enabled);
			button28.setColor(Color.LIGHT_GRAY);
			button26.setColor(Color.LIGHT_GRAY);
			button30.setColor(Color.LIGHT_GRAY);
			button27.setText("P");
			break;
		case 28:
			button27.setTouchable(Touchable.enabled);
			button29.setTouchable(Touchable.enabled);
			button27.setColor(Color.LIGHT_GRAY);
			button29.setColor(Color.LIGHT_GRAY);
			button28.setText("P");
			break;
		case 29:
			button28.setTouchable(Touchable.enabled);
			button28.setColor(Color.LIGHT_GRAY);
			button29.setText("P");
			break;
		case 30:
			button26.setTouchable(Touchable.enabled);
			button27.setTouchable(Touchable.enabled);
			button31.setTouchable(Touchable.enabled);
			button26.setColor(Color.LIGHT_GRAY);
			button27.setColor(Color.LIGHT_GRAY);
			button31.setColor(Color.LIGHT_GRAY);
			button30.setText("P");
			break;
		case 31:
			button30.setTouchable(Touchable.enabled);
			button32.setTouchable(Touchable.enabled);
			button31.setText("P");
			if(exit1.getColor().equals(Color.GREEN))
				exit1.setTouchable(Touchable.enabled);
			button30.setColor(Color.LIGHT_GRAY);
			button32.setColor(Color.LIGHT_GRAY);
			break;
		case 32:
			button31.setTouchable(Touchable.enabled);
			button24.setTouchable(Touchable.enabled);
			button31.setColor(Color.LIGHT_GRAY);
			button24.setColor(Color.LIGHT_GRAY);
			button32.setText("P");
			break;
		case 33:
			button23.setTouchable(Touchable.enabled);
			button34.setTouchable(Touchable.enabled);
			button23.setColor(Color.LIGHT_GRAY);
			button34.setColor(Color.LIGHT_GRAY);
			button33.setText("P");
			break;
		case 34:
			button33.setTouchable(Touchable.enabled);
			button34.setText("P");
			if(exit2.getColor().equals(Color.GREEN))
				exit2.setTouchable(Touchable.enabled);
			button33.setColor(Color.LIGHT_GRAY);
			break;
		}
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		mapBatch.setProjectionMatrix(vp.getCamera().combined);
		onionBatch.setProjectionMatrix(vp.getCamera().combined);
		
		mapBatch.begin();
		mapBatch.draw(mapTexture, 0, 0, GameScreen.MAX_WIDTH, GameScreen.MAX_HEIGHT);
		mapBatch.end();
		
		if(location == 0) {
			onionBatch.begin();
			onionBatch.draw(playerTexture, vp.getWorldWidth() / 2.1f, vp.getWorldHeight() / 13f, 60, 60);
			onionBatch.end();
		}				
		
		if(Gdx.input.isKeyPressed(Keys.F5)) {
			for(int i = 0; i < buttons.length; i++) {
				if(buttons[i] != null)
					buttons[i].remove();
			}
			exit1.remove();
			exit2.remove();
			exit3.remove();
			
			createComponents();
		}
		
		if(moved) {			
			switch(lastLocation) {
			case 1:
				button1.setText("");
				break;
			case 2:
				button2.setText("");
				break;
			case 3:
				button3.setText("");
				break;
			case 4:
				button4.setText("");
				break;
			case 5:
				button5.setText("");
				break;
			case 6:
				button6.setText("");
				break;
			case 7:
				button7.setText("");
				break;
			case 8:
				button8.setText("");
				break;
			case 9:
				button9.setText("");
				break;
			case 10:
				button10.setText("");
				break;
			case 11:
				button11.setText("");
				break;
			case 12:
				button12.setText("");
				break;
			case 13:
				button13.setText("");
				break;
			case 14:
				button14.setText("");
				break;
			case 15:
				button15.setText("");
				break;
			case 16:
				button16.setText("");
				break;
			case 17:
				button17.setText("");
				break;
			case 18:
				button18.setText("");
				break;
			case 19:
				button19.setText("");
				break;
			case 20:
				button20.setText("");
				break;
			case 21:
				button21.setText("");
				break;
			case 22:
				button22.setText("");
				break;
			case 23:
				button23.setText("");
				break;
			case 24:
				button24.setText("");
				break;
			case 25:
				button25.setText("");
				break;
			case 26:
				button26.setText("");
				break;
			case 27:
				button27.setText("");
				break;
			case 28:
				button28.setText("");
				break;
			case 29:
				button29.setText("");
				break;
			case 30:
				button30.setText("");
				break;
			case 31:
				button31.setText("");
				break;
			case 32:
				button32.setText("");
				break;
			case 33:
				button33.setText("");
				break;
			case 34:
				button34.setText("");
				break;
			}
			moved = false;
		}
		
		stage.act();
		stage.draw();
	}

	@Override
	public void resize(int width, int height) {
		mapBatch.setProjectionMatrix(vp.getCamera().combined);	
		onionBatch.setProjectionMatrix(vp.getCamera().combined);
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
}
