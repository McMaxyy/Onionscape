package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Storage {
	private static Storage instance = null;	
	Skin skin;
	public TextButton.TextButtonStyle buttonStyle;
	public LabelStyle labelStyle;
	public BitmapFont font;
	
	public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
	
	public Storage() {
		skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
	}
	
	// Load abilities
	public Abilities swing = new Swing();
	public Abilities rend = new Rend();
	public Abilities whirlwind = new Whirlwind();
	public Abilities groundBreaker = new GroundBreaker();
	public Abilities bash = new Bash();
	public Abilities barrier = new Barrier();
	public Abilities harden = new Harden();
	
	public void createFont() {
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
}
