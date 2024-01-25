package scenes;

import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
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
	private static ImageButton button1, button2, button3, button4, button5, button6, button7, button8,
	button9, button10, button11, button12, button13, button14;
	private static ImageButton button15, button16, button17, button18, button19, button20, button21, button22, button23;
	private static ImageButton button24, button25, button26, button27, button28, button29, button30, 
	button31, button32, button33, button34;
	private TextButton exit1, exit2, exit3;
	private ImageButton settingsBtn, homeBtn;
	private static ImageButton[] buttons = new ImageButton[34];
	private static int location = 0, lastLocation = 0;
	private Random rand = new Random();
	private boolean moved = false;
	public static int encounter = 0, ex;
	public static boolean newRaid = true;
	
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
				
		if(newRaid) {	
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
	
	private void setBtnStyle(String enc, ImageButton button) {
		switch(enc) {
		case "F":
			button.setStyle(skin.get("fightIcon", ImageButton.ImageButtonStyle.class));
			break;
		case "R":
			button.setStyle(skin.get("randIcon", ImageButton.ImageButtonStyle.class));
			break;
		case "M":
			button.setStyle(skin.get("merchIcon", ImageButton.ImageButtonStyle.class));
			break;
		case "B":
			button.setStyle(skin.get("bossIcon", ImageButton.ImageButtonStyle.class));
			break;
		case "E":
			button.setStyle(skin.get("eliteIcon", ImageButton.ImageButtonStyle.class));
			break;
		case "T":
			button.setStyle(skin.get("treasureIcon", ImageButton.ImageButtonStyle.class));
			break;
		case "P":
			button.setStyle(skin.get("playerIcon", ImageButton.ImageButtonStyle.class));
			break;
		case "D":
			button.setStyle(skin.get("default", ImageButton.ImageButtonStyle.class));
			break;
		}
	}
	
	private String setEncounter(int loc, ImageButton button) {
		int x = rand.nextInt(1, 11);
		
		if(button.getName() == null) {
			switch(loc) {
			case 1:
				setBtnStyle("F", button);
				return "F";
			case 2:
				if(x > 5) {
					setBtnStyle("R", button);
					return "R";	
				}					
				else {
					setBtnStyle("F", button);
					return "F";	
				}					
			case 3:
				if(x > 7){
					setBtnStyle("M", button);
					return "M";	
				}
				else if (x > 4){
					setBtnStyle("R", button);
					return "R";	
				}		
				else {
					setBtnStyle("F", button);
					return "F";	
				}
			case 4:
				if(x > 6){
					setBtnStyle("T", button);
					return "T";	
				}
				else if(x > 4){
					setBtnStyle("R", button);
					return "R";	
				}
				else if(x > 2){
					setBtnStyle("E", button);
					return "E";	
				}
				else {
					setBtnStyle("F", button);
					return "F";	
				}
			case 5:
				setBtnStyle("F", button);
				return "F";	
			case 6:
				if(x > 5){
					setBtnStyle("R", button);
					return "R";	
				}
				else {
					setBtnStyle("F", button);
					return "F";	
				}
			case 7:
				if(x > 6){
					setBtnStyle("T", button);
					return "T";	
				}
				else if(x > 3){
					setBtnStyle("R", button);
					return "R";	
				}
				else {
					setBtnStyle("F", button);
					return "F";	
				}
			case 8:
				if(x > 5){
					setBtnStyle("R", button);
					return "R";	
				}
				else {
					setBtnStyle("F", button);
					return "F";	
				}
			case 9:
				button = new ImageButton(skin, "default");
				return "";
			case 10:
				setBtnStyle("F", button);
				return "F";	
			case 11:
				setBtnStyle("F", button);
				return "F";	
			case 12:
				setBtnStyle("B", button);
				return "B";	
			case 13:
				setBtnStyle("R", button);
				return "R";	
			case 14:
				if(x > 5){
					setBtnStyle("R", button);
					return "R";	
				}
				else {
					setBtnStyle("F", button);
					return "F";	
				}
			case 15:
				if(x > 4){
					setBtnStyle("T", button);
					return "T";	
				}
				else{
					setBtnStyle("E", button);
					return "E";	
				}
			case 16:
				if(x > 5){
					setBtnStyle("R", button);
					return "R";	
				}
				else {
					setBtnStyle("F", button);
					return "F";	
				}
			case 17:
				if(x > 5){
					setBtnStyle("R", button);
					return "R";	
				}
				else {
					setBtnStyle("F", button);
					return "F";	
				}
			case 18:
				if(x > 5) {
					setBtnStyle("B", button);
					return "B";	
				}
				else{
					setBtnStyle("R", button);
					return "R";	
				}
			case 19:
				setBtnStyle("R", button);
				return "R";	
			case 20:
				setBtnStyle("F", button);
				return "F";	
			case 21:
				setBtnStyle("R", button);
				return "R";	
			case 22:
				if(x > 4){
					setBtnStyle("M", button);
					return "M";	
				}
				else{
					setBtnStyle("R", button);
					return "R";	
				}
			case 23:
				setBtnStyle("F", button);
				return "F";	
			case 24:
				setBtnStyle("F", button);
				return "F";	
			case 25:
				if(x > 4){
					setBtnStyle("T", button);
					return "T";	
				}
				else{
					setBtnStyle("E", button);
					return "E";	
				}
			case 26:
				setBtnStyle("F", button);
				return "F";	
			case 27:
				if(x > 4){
					setBtnStyle("M", button);
					return "M";	
				}
				else{
					setBtnStyle("R", button);
					return "R";	
				}
			case 28:
				setBtnStyle("F", button);
				return "F";	
			case 29:
				setBtnStyle("B", button);
				return "B";	
			case 30:
				setBtnStyle("R", button);
				return "R";	
			case 31:
				if(x > 8) {
					setBtnStyle("E", button);
					return "E";	
				}
				else if (x > 4){
					setBtnStyle("R", button);
					return "R";	
				}
				else {
					setBtnStyle("F", button);
					return "F";	
				}
			case 32:
				setBtnStyle("R", button);
				return "R";	
			case 33:
				if(x > 6){
					setBtnStyle("E", button);
					return "E";	
				}
				else{
					setBtnStyle("R", button);
					return "R";	
				}
			case 34:
				if(x > 5){
					setBtnStyle("R", button);
					return "R";	
				}
				else {
					setBtnStyle("F", button);
					return "F";	
				}
			default:
				setBtnStyle("D", button);
				return "";
			}
		}
		else {
			setBtnStyle("D", button);
			return "";
		}
	}
	
	private void createComponents() {
		button1 = new ImageButton(skin, "default");		
		button1.setBounds(vp.getWorldWidth() / 1.865f, vp.getWorldHeight() / 4f, 70, 70);
		buttons[0] = button1;
		button1.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
				lastLocation = location;
				location = 1;				
				btnClick(button1.getName().toString());
				setBtnStyle("P", button1);
    	    }});
		button1.setName(setEncounter(1, button1));
		
		button2 = new ImageButton(skin, "default");
		button2.setBounds(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[1] = button2;
		button2.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {   				
    			lastLocation = location;	
    			location = 2;
    			btnClick(button2.getName().toString());
    			setBtnStyle("P", button2);
    	    }});	
		button2.setName(setEncounter(2, button2));
		
		button3 = new ImageButton(skin, "default");
		button3.setBounds(vp.getWorldWidth() / 5.2f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[2] = button3;
		button3.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 3;
				btnClick(button3.getName().toString());
				setBtnStyle("P", button3);
    	    }});
		button3.setName(setEncounter(3, button3));
		
		button4 = new ImageButton(skin, "default");
		button4.setBounds(vp.getWorldWidth() / 100f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[3] = button4;
		button4.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 4;
				btnClick(button4.getName().toString());
				setBtnStyle("P", button4);
    	    }});
		button4.setName(setEncounter(4, button4));
		
		button5 = new ImageButton(skin, "default");
		button5.setBounds(vp.getWorldWidth() / 6.78f, vp.getWorldHeight() / 4f, 70, 70);
		buttons[4] = button5;
		button5.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 5;
				btnClick(button5.getName().toString());
				setBtnStyle("P", button5);
    	    }});
		button5.setName(setEncounter(5, button5));
		
		button6 = new ImageButton(skin, "default");
		button6.setBounds(vp.getWorldWidth() / 13f, vp.getWorldHeight() / 2.55f, 70, 70);
		buttons[5] = button6;
		button6.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 6;
				btnClick(button6.getName().toString());
				setBtnStyle("P", button6);
    	    }});
		button6.setName(setEncounter(6, button6));
		
		button7 = new ImageButton(skin, "default");
		button7.setBounds(vp.getWorldWidth() / 100f, vp.getWorldHeight() / 2.55f, 70, 70);
		buttons[6] = button7;
		button7.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 7;
				btnClick(button7.getName().toString());
				setBtnStyle("P", button7);
    	    }});
		button7.setName(setEncounter(7, button7));
		
		button8 = new ImageButton(skin, "default");
		button8.setBounds(vp.getWorldWidth() / 4.25f, vp.getWorldHeight() / 4f, 70, 70);
		buttons[7] = button8;
		button8.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 8;
				btnClick(button8.getName().toString());
				setBtnStyle("P", button8);
    	    }});
		button8.setName(setEncounter(8, button8));
		
		button9 = new ImageButton(skin, "default");
		button9.setBounds(vp.getWorldWidth() / 1.63f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[8] = button9;
		button9.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 9;
				btnClick(button9.getName().toString());
				setBtnStyle("P", button9);
    	    }});
		button9.setName(setEncounter(9, button9));
		
		button10 = new ImageButton(skin, "default");
		button10.setBounds(vp.getWorldWidth() / 1.455f, vp.getWorldHeight() / 6f, 70, 70);
		buttons[9] = button10;
		button10.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 10;
				btnClick(button10.getName().toString());
				setBtnStyle("P", button10);
    	    }});
		button10.setName(setEncounter(10, button10));
		
		button11 = new ImageButton(skin, "default");
		button11.setBounds(vp.getWorldWidth() / 1.25f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[10] = button11;
		button11.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 11;
				btnClick(button11.getName().toString());
				setBtnStyle("P", button11);
    	    }});
		button11.setName(setEncounter(11, button11));
		
		button12 = new ImageButton(skin, "default");
		button12.setBounds(vp.getWorldWidth() / 1.05f, vp.getWorldHeight() / 13.4f, 70, 70);
		buttons[11] = button12;
		button12.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 12;
				btnClick(button12.getName().toString());
				setBtnStyle("P", button12);
    	    }});
		button12.setName(setEncounter(12, button12));
		
		button13 = new ImageButton(skin, "default");
		button13.setBounds(vp.getWorldWidth() / 1.345f, vp.getWorldHeight() / 2.8f, 70, 70);
		buttons[12] = button13;
		button13.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 13;
				btnClick(button13.getName().toString());
				setBtnStyle("P", button13);
    	    }});
		button13.setName(setEncounter(13, button13));
		
		button14 = new ImageButton(skin, "default");
		button14.setBounds(vp.getWorldWidth() / 1.075f, vp.getWorldHeight() / 2.8f, 70, 70);
		buttons[13] = button14;
		button14.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 14;
				btnClick(button14.getName().toString());
				setBtnStyle("P", button14);
    	    }});
		button14.setName(setEncounter(14, button14));
		
		button15 = new ImageButton(skin, "default");
		button15.setBounds(vp.getWorldWidth() / 1.115f, vp.getWorldHeight() / 2.2f, 70, 70);
		buttons[14] = button15;
		button15.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 15;
				btnClick(button15.getName().toString());
				setBtnStyle("P", button15);
    	    }});
		button15.setName(setEncounter(15, button15));
		
		button16 = new ImageButton(skin, "default");
		button16.setBounds(vp.getWorldWidth() / 1.25f, vp.getWorldHeight() / 2.2f, 70, 70);
		buttons[15] = button16;
		button16.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 16;
				btnClick(button16.getName().toString());
				setBtnStyle("P", button16);
    	    }});
		button16.setName(setEncounter(16, button16));
		
		button17 = new ImageButton(skin, "default");
		button17.setBounds(vp.getWorldWidth() / 1.13f, vp.getWorldHeight() / 1.655f, 70, 70);
		buttons[16] = button17;
		button17.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 17;
				btnClick(button17.getName().toString());
				setBtnStyle("P", button17);
    	    }});
		button17.setName(setEncounter(17, button17));
		
		button18 = new ImageButton(skin, "default");
		button18.setBounds(vp.getWorldWidth() / 1.55f, vp.getWorldHeight() / 2.03f, 70, 70);
		buttons[17] = button18;
		button18.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 18;
				btnClick(button18.getName().toString());
				setBtnStyle("P", button18);
    	    }});
		button18.setName(setEncounter(18, button18));
		
		button19 = new ImageButton(skin, "default");
		button19.setBounds(vp.getWorldWidth() / 2.03f, vp.getWorldHeight() / 1.8f, 70, 70);
		buttons[18] = button19;
		button19.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 19;
				btnClick(button19.getName().toString());
				setBtnStyle("P", button19);
    	    }});
		button19.setName(setEncounter(19, button19));
		
		button20 = new ImageButton(skin, "default");
		button20.setBounds(vp.getWorldWidth() / 2.19f, vp.getWorldHeight() / 2.2f, 70, 70);
		buttons[19] = button20;
		button20.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 20;
				btnClick(button20.getName().toString());
				setBtnStyle("P", button20);
    	    }});
		button20.setName(setEncounter(20, button20));
		
		button21 = new ImageButton(skin, "default");
		button21.setBounds(vp.getWorldWidth() / 2.55f, vp.getWorldHeight() / 1.72f, 70, 70);
		buttons[20] = button21;
		button21.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 21;
				btnClick(button21.getName().toString());
				setBtnStyle("P", button21);
    	    }});
		button21.setName(setEncounter(21, button21));
		
		button22 = new ImageButton(skin, "default");
		button22.setBounds(vp.getWorldWidth() / 3.5f, vp.getWorldHeight() / 1.72f, 70, 70);
		buttons[21] = button22;
		button22.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 22;
				btnClick(button22.getName().toString());
				setBtnStyle("P", button22);
    	    }});
		button22.setName(setEncounter(22, button22));
		
		button23 = new ImageButton(skin, "default");
		button23.setBounds(vp.getWorldWidth() / 16.5f, vp.getWorldHeight() / 1.6f, 70, 70);
		buttons[22] = button23;
		button23.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 23;
				btnClick(button23.getName().toString());
				setBtnStyle("P", button23);
    	    }});
		button23.setName(setEncounter(23, button23));
		
		button24 = new ImageButton(skin, "default");
		button24.setBounds(vp.getWorldWidth() / 2.75f, vp.getWorldHeight() / 1.37f, 70, 70);
		buttons[23] = button24;
		button24.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 24;
				btnClick(button24.getName().toString());
				setBtnStyle("P", button24);
    	    }});
		button24.setName(setEncounter(24, button24));
		
		button25 = new ImageButton(skin, "default");
		button25.setBounds(vp.getWorldWidth() / 2.18f, vp.getWorldHeight() / 1.37f, 70, 70);
		buttons[24] = button25;
		button25.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 25;
				btnClick(button25.getName().toString());
				setBtnStyle("P", button25);
    	    }});
		button25.setName(setEncounter(25, button25));
		
		button26 = new ImageButton(skin, "default");
		button26.setBounds(vp.getWorldWidth() / 1.7f, vp.getWorldHeight() / 1.37f, 70, 70);
		buttons[25] = button26;
		button26.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 26;
				btnClick(button26.getName().toString());
				setBtnStyle("P", button26);
    	    }});
		button26.setName(setEncounter(26, button26));
		
		button27 = new ImageButton(skin, "default");
		button27.setBounds(vp.getWorldWidth() / 1.43f, vp.getWorldHeight() / 1.37f, 70, 70);
		buttons[26] = button27;
		button27.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 27;
				btnClick(button27.getName().toString());
				setBtnStyle("P", button27);
    	    }});
		button27.setName(setEncounter(27, button27));
		
		button28 = new ImageButton(skin, "default");
		button28.setBounds(vp.getWorldWidth() / 1.28f, vp.getWorldHeight() / 1.107f, 70, 70);
		buttons[27] = button28;
		button28.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 28;
				btnClick(button28.getName().toString());
				setBtnStyle("P", button28);
    	    }});
		button28.setName(setEncounter(28, button28));
		
		button29 = new ImageButton(skin, "default");
		button29.setBounds(vp.getWorldWidth() / 1.05f, vp.getWorldHeight() /  1.655f, 70, 70);
		buttons[28] = button29;
		button29.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 29;
				btnClick(button29.getName().toString());
				setBtnStyle("P", button29);
    	    }});
		button29.setName(setEncounter(29, button29));
		
		button30 = new ImageButton(skin, "default");
		button30.setBounds(vp.getWorldWidth() / 1.6f, vp.getWorldHeight() / 1.107f, 70, 70);
		buttons[29] = button30;
		button30.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 30;
				btnClick(button30.getName().toString());
				setBtnStyle("P", button30);
    	    }});
		button30.setName(setEncounter(30, button30));
		
		button31 = new ImageButton(skin, "default");
		button31.setBounds(vp.getWorldWidth() / 1.865f, vp.getWorldHeight() / 1.107f, 70, 70);
		buttons[30] = button31;
		button31.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 31;
				btnClick(button31.getName().toString());
				setBtnStyle("P", button31);
    	    }});
		button31.setName(setEncounter(31, button31));
		
		button32 = new ImageButton(skin, "default");
		button32.setBounds(vp.getWorldWidth() / 2.28f, vp.getWorldHeight() / 1.107f, 70, 70);
		buttons[31] = button32;
		button32.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 32;
				btnClick(button32.getName().toString());
				setBtnStyle("P", button32);
    	    }});
		button32.setName(setEncounter(32, button32));
		
		button33 = new ImageButton(skin, "default");
		button33.setBounds(vp.getWorldWidth() / 6.9f, vp.getWorldHeight() / 1.26f, 70, 70);
		buttons[32] = button33;
		button33.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 33;
				btnClick(button33.getName().toString());
				setBtnStyle("P", button33);
    	    }});
		button33.setName(setEncounter(33, button33));
		
		button34 = new ImageButton(skin, "default");
		button34.setBounds(vp.getWorldWidth() / 8f, vp.getWorldHeight() / 1.075f, 70, 70);
		buttons[33] = button34;
		button34.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			lastLocation = location;
				location = 34;
				btnClick(button34.getName().toString());
				setBtnStyle("P", button34);
    	    }});
		button34.setName(setEncounter(34, button34));
		
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
    				gameScreen.switchToNewState(GameScreen.HOME);
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
    				gameScreen.switchToNewState(GameScreen.HOME);
    			}  
    	    }});
		
		exit3 = new TextButton("", storage.buttonStyle);
		exit3.setColor(Color.RED);
		exit3.setTouchable(Touchable.disabled);
		exit3.setBounds(vp.getWorldWidth() / 1.02f, vp.getWorldHeight() / 1.107f, 50, 70);
		exit3.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			if(exit3.getColor().equals(Color.GREEN)) {
    				Player.gainCoins(Player.getRaidCoins());
    				Player.setRaidCoins(0);
    				gameScreen.switchToNewState(GameScreen.HOME);
    			}  
    	    }});
		
		settingsBtn = new ImageButton(skin, "default");	
		settingsBtn.setStyle(skin.get("settingsIcon", ImageButton.ImageButtonStyle.class));
		settingsBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			gameScreen.switchToNewState(GameScreen.SETTINGS);
    	    }});
		settingsBtn.setSize(60, 50);
		settingsBtn.setPosition(vp.getWorldWidth() / 1.05f, vp.getWorldHeight() / 80f);
		stage.addActor(settingsBtn);
		
		homeBtn = new ImageButton(skin, "default");	
		homeBtn.setStyle(skin.get("homeIcon", ImageButton.ImageButtonStyle.class));
		homeBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			Player.gainCoins(Player.getRaidCoins());
				Player.setRaidCoins(0);
				Player.setStrength(3);
    			Player.setMaxHP(70);
    			Player.setDmgResist(0);
    			Player.setWeaponDmg(0);
    			if(Home.story) {
    				storage.equippedArmor(null, "Clear");
    				storage.equippedWeapons(null, "Clear");
    				storage.equippedItems(null, "Clear");
    				Player.setGearAP(0);
    				Player.setGearDP(0);
    				Player.setGearHP(0);
    				storage.resetBonuses();
    			}				
    			gameScreen.switchToNewState(GameScreen.HOME);
    	    }});
		homeBtn.setSize(60, 50);
		homeBtn.setPosition(vp.getWorldWidth() / 1.2f, vp.getWorldHeight() / 80f);
		stage.addActor(homeBtn);
		
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
		if(newRaid)
			newRaid = false;
		disableButtons();
		moved = true;
		enableButtons();
		int x = rand.nextInt(13);
		
		switch(action) {
		case "F":
			FightScene.elite = FightScene.boss = false;
			FightScene.normal = true;
			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);			
			break;
		case "R":
			if(x <= 6) {
				encounter = 1;
				gameScreen.switchToNewState(GameScreen.TEXT_SCENE);
			}
			else if(x == 7 || x == 8){
				FightScene.elite = FightScene.boss = false;
				FightScene.normal = true;
				gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
			}
			else if(x == 9 || x == 10){
				encounter = 2;
				gameScreen.switchToNewState(GameScreen.TEXT_SCENE);
			}
			else
				gameScreen.switchToNewState(GameScreen.MERCHANT);
			break;
		case "B":
			FightScene.normal = FightScene.elite = false;
			FightScene.boss = true;
			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);
			break;
		case "T":
			encounter = 2;
			gameScreen.switchToNewState(GameScreen.TEXT_SCENE);
			break;
		case "M":
			gameScreen.switchToNewState(GameScreen.MERCHANT);
			break;
		case "E":
			FightScene.normal = FightScene.boss = false;
			FightScene.elite = true;
			gameScreen.switchToNewState(GameScreen.FIGHT_SCENE);			
			break;
		}
	}
	
	private void disableButtons() {
		for(Integer i = 0; i < buttons.length; i++) {
			buttons[i].setTouchable(Touchable.disabled);
			buttons[i].setDisabled(true);
		}
	}
	
	private void enableButtons() {		
		switch(location) {
		case 0:
			button1.setTouchable(Touchable.enabled);
			button2.setTouchable(Touchable.enabled);
			button9.setTouchable(Touchable.enabled);
			button1.setDisabled(false);
			button2.setDisabled(false);
			button9.setDisabled(false);
			break;
		case 1:
			button9.setTouchable(Touchable.enabled);
			button20.setTouchable(Touchable.enabled);
			button2.setTouchable(Touchable.enabled);
			button9.setDisabled(false);
			button20.setDisabled(false);
			button2.setDisabled(false);
			button1.setName("P");
			break;
		case 2:
			button1.setTouchable(Touchable.enabled);
			button8.setTouchable(Touchable.enabled);
			button3.setTouchable(Touchable.enabled);
			button9.setTouchable(Touchable.enabled);
			button9.setDisabled(false);
			button1.setDisabled(false);
			button8.setDisabled(false);
			button3.setDisabled(false);
			button2.setName("P");
			break;
		case 3:
			button4.setTouchable(Touchable.enabled);
			button5.setTouchable(Touchable.enabled);
			button8.setTouchable(Touchable.enabled);
			button2.setTouchable(Touchable.enabled);
			button4.setDisabled(false);
			button5.setDisabled(false);
			button8.setDisabled(false);
			button2.setDisabled(false);
			button3.setName("P");
			break;
		case 4:
			button3.setTouchable(Touchable.enabled);
			button5.setTouchable(Touchable.enabled);
			button3.setDisabled(false);
			button5.setDisabled(false);
			button4.setName("P");
			break;
		case 5:
			button3.setTouchable(Touchable.enabled);
			button6.setTouchable(Touchable.enabled);
			button3.setDisabled(false);
			button6.setDisabled(false);
			button5.setName("P");
			break;
		case 6:
			button5.setTouchable(Touchable.enabled);
			button7.setTouchable(Touchable.enabled);
			button5.setDisabled(false);
			button7.setDisabled(false);
			button6.setName("P");
			break;
		case 7:
			button6.setTouchable(Touchable.enabled);
			button6.setDisabled(false);
			button7.setName("P");
			break;
		case 8:
			button3.setTouchable(Touchable.enabled);
			button21.setTouchable(Touchable.enabled);
			button20.setTouchable(Touchable.enabled);
			button2.setTouchable(Touchable.enabled);
			button3.setDisabled(false);
			button21.setDisabled(false);
			button20.setDisabled(false);
			button2.setDisabled(false);
			button8.setName("P");
			break;
		case 9:
			button1.setTouchable(Touchable.enabled);
			button10.setTouchable(Touchable.enabled);
			button11.setTouchable(Touchable.enabled);
			button2.setTouchable(Touchable.enabled);
			button1.setDisabled(false);
			button2.setDisabled(false);
			button10.setDisabled(false);
			button11.setDisabled(false);
			button9.setName("P");
			break;
		case 10:
			button9.setTouchable(Touchable.enabled);
			button13.setTouchable(Touchable.enabled);
			button14.setTouchable(Touchable.enabled);
			button11.setTouchable(Touchable.enabled);
			button11.setDisabled(false);
			button9.setDisabled(false);
			button13.setDisabled(false);
			button14.setDisabled(false);
			button10.setName("P");
			break;
		case 11:
			button12.setTouchable(Touchable.enabled);
			button9.setTouchable(Touchable.enabled);
			button10.setTouchable(Touchable.enabled);
			button12.setDisabled(false);
			button9.setDisabled(false);
			button10.setDisabled(false);
			button11.setName("P");
			break;
		case 12:
			button11.setTouchable(Touchable.enabled);
			button11.setDisabled(false);
			button12.setName("P");
			break;
		case 13:
			button16.setTouchable(Touchable.enabled);
			button18.setTouchable(Touchable.enabled);
			button10.setTouchable(Touchable.enabled);
			button14.setTouchable(Touchable.enabled);
			button14.setDisabled(false);
			button10.setDisabled(false);
			button16.setDisabled(false);
			button18.setDisabled(false);
			button13.setName("P");
			break;
		case 14:
			button15.setTouchable(Touchable.enabled);
			button13.setTouchable(Touchable.enabled);
			button15.setDisabled(false);
			button13.setDisabled(false);
			button14.setName("P");
			break;
		case 15:
			button14.setTouchable(Touchable.enabled);
			button14.setDisabled(false);
			button15.setName("P");
			break;
		case 16:
			button13.setTouchable(Touchable.enabled);
			button17.setTouchable(Touchable.enabled);
			button18.setTouchable(Touchable.enabled);
			button13.setDisabled(false);
			button17.setDisabled(false);
			button18.setDisabled(false);
			button16.setName("P");
			break;
		case 17:
			button16.setTouchable(Touchable.enabled);
			button17.setName("P");
			button29.setTouchable(Touchable.enabled);
			button29.setDisabled(false);
			button16.setDisabled(false);
			break;
		case 18:
			button16.setTouchable(Touchable.enabled);
			button19.setTouchable(Touchable.enabled);
			button20.setTouchable(Touchable.enabled);
			button16.setDisabled(false);
			button19.setDisabled(false);
			button20.setDisabled(false);
			button18.setName("P");
			break;
		case 19:
			button25.setTouchable(Touchable.enabled);
			button26.setTouchable(Touchable.enabled);
			button20.setTouchable(Touchable.enabled);
			button18.setTouchable(Touchable.enabled);
			button25.setDisabled(false);
			button26.setDisabled(false);
			button20.setDisabled(false);
			button18.setDisabled(false);
			button19.setName("P");
			break;
		case 20:
			button19.setTouchable(Touchable.enabled);
			button18.setTouchable(Touchable.enabled);
			button8.setTouchable(Touchable.enabled);
			button21.setTouchable(Touchable.enabled);
			button1.setTouchable(Touchable.enabled);
			button1.setDisabled(false);
			button19.setDisabled(false);
			button18.setDisabled(false);
			button8.setDisabled(false);
			button21.setDisabled(false);
			button20.setName("P");
			break;
		case 21:
			button20.setTouchable(Touchable.enabled);
			button24.setTouchable(Touchable.enabled);
			button22.setTouchable(Touchable.enabled);
			button20.setDisabled(false);
			button24.setDisabled(false);
			button22.setDisabled(false);
			button21.setName("P");
			break;
		case 22:
			button21.setTouchable(Touchable.enabled);
			button24.setTouchable(Touchable.enabled);
			button23.setTouchable(Touchable.enabled);
			button21.setDisabled(false);
			button24.setDisabled(false);
			button23.setDisabled(false);
			button22.setName("P");
			break;
		case 23:
			button22.setTouchable(Touchable.enabled);
			button33.setTouchable(Touchable.enabled);
			button22.setDisabled(false);
			button33.setDisabled(false);
			button23.setName("P");
			break;
		case 24:
			button22.setTouchable(Touchable.enabled);
			button32.setTouchable(Touchable.enabled);
			button22.setDisabled(false);
			button32.setDisabled(false);
			button24.setName("P");
			break;
		case 25:
			button19.setTouchable(Touchable.enabled);
			button26.setTouchable(Touchable.enabled);
			button19.setDisabled(false);
			button26.setDisabled(false);
			button25.setName("P");
			break;
		case 26:
			button19.setTouchable(Touchable.enabled);
			button25.setTouchable(Touchable.enabled);
			button30.setTouchable(Touchable.enabled);
			button27.setTouchable(Touchable.enabled);
			button19.setDisabled(false);
			button25.setDisabled(false);
			button30.setDisabled(false);
			button27.setDisabled(false);
			button26.setName("P");
			break;
		case 27:
			button28.setTouchable(Touchable.enabled);
			button26.setTouchable(Touchable.enabled);
			button30.setTouchable(Touchable.enabled);
			button28.setDisabled(false);
			button26.setDisabled(false);
			button30.setDisabled(false);
			button27.setName("P");
			break;
		case 28:
			button27.setTouchable(Touchable.enabled);
			button27.setDisabled(false);
			if(exit3.getColor().equals(Color.GREEN))
				exit3.setTouchable(Touchable.enabled);
			button28.setName("P");
			break;
		case 29:
			button17.setTouchable(Touchable.enabled);
			button17.setDisabled(false);
			button29.setName("P");
			break;
		case 30:
			button26.setTouchable(Touchable.enabled);
			button27.setTouchable(Touchable.enabled);
			button31.setTouchable(Touchable.enabled);
			button26.setDisabled(false);
			button27.setDisabled(false);
			button31.setDisabled(false);
			button30.setName("P");
			break;
		case 31:
			button30.setTouchable(Touchable.enabled);
			button32.setTouchable(Touchable.enabled);
			button31.setName("P");
			if(exit1.getColor().equals(Color.GREEN))
				exit1.setTouchable(Touchable.enabled);
			button30.setDisabled(false);
			button32.setDisabled(false);
			break;
		case 32:
			button31.setTouchable(Touchable.enabled);
			button24.setTouchable(Touchable.enabled);
			button31.setDisabled(false);
			button24.setDisabled(false);
			button32.setName("P");
			break;
		case 33:
			button23.setTouchable(Touchable.enabled);
			button34.setTouchable(Touchable.enabled);
			button23.setDisabled(false);
			button34.setDisabled(false);
			button33.setName("P");
			break;
		case 34:
			button33.setTouchable(Touchable.enabled);
			button34.setName("P");
			if(exit2.getColor().equals(Color.GREEN))
				exit2.setTouchable(Touchable.enabled);
			button33.setDisabled(false);
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
		mapBatch.draw(mapTexture, 0, 0, GameScreen.SELECTED_WIDTH, GameScreen.SELECTED_HEIGHT);
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
				setBtnStyle("D", button1);
				break;
			case 2:
				setBtnStyle("D", button2);
				break;
			case 3:
				setBtnStyle("D", button3);
				break;
			case 4:
				setBtnStyle("D", button4);
				break;
			case 5:
				setBtnStyle("D", button5);
				break;
			case 6:
				setBtnStyle("D", button6);
				break;
			case 7:
				setBtnStyle("D", button7);
				break;
			case 8:
				setBtnStyle("D", button8);
				break;
			case 9:
				setBtnStyle("D", button9);
				break;
			case 10:
				setBtnStyle("D", button10);
				break;
			case 11:
				setBtnStyle("D", button11);
				break;
			case 12:
				setBtnStyle("D", button12);
				break;
			case 13:
				setBtnStyle("D", button13);
				break;
			case 14:
				setBtnStyle("D", button14);
				break;
			case 15:
				setBtnStyle("D", button15);
				break;
			case 16:
				setBtnStyle("D", button16);
				break;
			case 17:
				setBtnStyle("D", button17);
				break;
			case 18:
				setBtnStyle("D", button18);
				break;
			case 19:
				setBtnStyle("D", button19);
				break;
			case 20:
				setBtnStyle("D", button20);
				break;
			case 21:
				setBtnStyle("D", button21);
				break;
			case 22:
				setBtnStyle("D", button22);
				break;
			case 23:
				setBtnStyle("D", button23);
				break;
			case 24:
				setBtnStyle("D", button24);
				break;
			case 25:
				setBtnStyle("D", button25);
				break;
			case 26:
				setBtnStyle("D", button26);
				break;
			case 27:
				setBtnStyle("D", button27);
				break;
			case 28:
				setBtnStyle("D", button28);
				break;
			case 29:
				setBtnStyle("D", button29);
				break;
			case 30:
				setBtnStyle("D", button30);
				break;
			case 31:
				setBtnStyle("D", button31);
				break;
			case 32:
				setBtnStyle("D", button32);
				break;
			case 33:
				setBtnStyle("D", button33);
				break;
			case 34:
				setBtnStyle("D", button34);
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
