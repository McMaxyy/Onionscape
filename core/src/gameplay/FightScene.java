package gameplay;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

public class FightScene {
    private Stage stage;
    private TextButton attackBtn, fleeBtn, ability1, ability2, ability3, ability4;
    private Label playerHP, enemyHP, combatLog;
    private int pHP = 20, eHP = 20, pDmg = 2, eDmg = 2;
    private boolean pDead, eDead, btnClicked;
    Skin skin;
    BitmapFont font;
    TextButton.TextButtonStyle buttonStyle;
	LabelStyle labelStyle;
    Viewport vp;
    String combatText;

    public FightScene(Viewport viewport) {
        stage = new Stage(viewport);
        vp = viewport;
        Gdx.input.setInputProcessor(stage);  // Set the stage to process inputs
        skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
        
        createFont();       
        createComponents();
        componentParameters();       
    }

    public void render() {
    	update();
        stage.act();
        stage.draw();
    }
    
    public void update() {
    	if(!pDead && !eDead && btnClicked) {
    		playerHP.setText("Player HP: " + pHP + "/" + 20);
        	enemyHP.setText("Enemy HP: " + eHP + "/" + 20); 
        	btnClicked = false;
    	}
    	  	
    	if(pDead || eDead) {
    		attackBtn.setTouchable(Touchable.disabled);
    		attackBtn.setText("");
    		fleeBtn.setTouchable(Touchable.disabled);
    		fleeBtn.setText("");
    		
    		newLine();
	        if(pDead)
	        	combatLog.setText(combatText + "\n Player died");
	        else
	        	combatLog.setText(combatText + "\n Enemy died");
	        
	        pDead = eDead = false; 		
    	} 		
    }
    
    private void createFont() {
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/RetroGaming.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        font = generator.generateFont(parameter);
        generator.dispose();
        
        // Create a new style for button
        buttonStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        buttonStyle.font = font;
        
        labelStyle = new Label.LabelStyle(skin.get(Label.LabelStyle.class));
        labelStyle.font = font;
    }
    
    private void createComponents() {
    	playerHP = new Label("Player HP: " + pHP + "/" + 20, labelStyle);
    	enemyHP = new Label("Enemy HP: " + eHP + "/" + 20, labelStyle);
    	
    	combatLog = new Label("", labelStyle);
    	
    	attackBtn = new TextButton("Attack", buttonStyle);
    	attackBtn.addListener(new ClickListener() {
    	    @Override
    	    public void clicked(InputEvent event, float x, float y) {
    	        eHP -= pDmg;      	        
    	        newLine();
    	        combatLog.setText(combatText + "\n Player attacked for " + pDmg + " damage");
    	        
    	        if(eHP <= 0)
    	        	eDead = true;    	        
    	        btnClicked = true;
    	    }});
        
    	fleeBtn = new TextButton("Flee", buttonStyle);
    	fleeBtn.addListener(new ClickListener() {
        	@Override
    	    public void clicked(InputEvent event, float x, float y) {
    	        pHP -= eDmg;    	        
    	        newLine();
    	        combatLog.setText(combatText + "\n Enemy attacked for " + eDmg + " damage");
    	        
    	        if(pHP <= 0)
    	        	pDead = true;
    	        btnClicked = true;
    	    }});
    }
    
    private void componentParameters() {
    	playerHP.setPosition(vp.getWorldWidth() / 1.7f, vp.getWorldHeight() / 2.5f);
    	
    	enemyHP.setPosition(vp.getWorldWidth() / 1.7f, vp.getWorldHeight() / 1.2f);
    	
    	combatLog.setColor(Color.BLACK);
    	Container<Label> container = new Container<Label>(combatLog);
    	container.setBackground(new TextureRegionDrawable(new TextureRegion(new Texture("white.png"))));
    	container.setBounds(vp.getWorldWidth() / 20f, vp.getWorldHeight() / 1.8f, 800, 400);
    	container.align(Align.topLeft);
    	combatLog.setWidth(container.getWidth());
    	
        attackBtn.setSize(200, 150); 
        attackBtn.setPosition(
        	    vp.getWorldWidth() / 10f - attackBtn.getWidth() / 2f, 
        	    vp.getWorldHeight() / 10f - attackBtn.getHeight() / 2f);
        
        fleeBtn.setSize(200, 80); 
        fleeBtn.setPosition(
        	    vp.getWorldWidth() / 1.7f,
        	    vp.getWorldHeight() / 3.3f);
        
        stage.addActor(attackBtn);
        stage.addActor(fleeBtn);
        stage.addActor(playerHP);
        stage.addActor(enemyHP);
        stage.addActor(container);
    }
    
    public void newLine() {
    	combatText = combatLog.getText().toString();
        int lines = combatText.split("\n").length;
        if(lines >= 12) {
            int index = combatText.indexOf("\n");
            combatText = combatText.substring(index+1);
        }
    }
    
    public void dispose() {
        stage.dispose();
        skin.dispose();
        font.dispose();
    }
}