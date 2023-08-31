package player;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Storage {
	private static Storage instance = null;	
	public Skin skin;
	public TextButton.TextButtonStyle buttonStyle;
	public LabelStyle labelStyle;
	public BitmapFont font;
	private List<Weapons> playerWeapons = new ArrayList<>();
	private List<Armor> playerArmor = new ArrayList<>();
	public static AssetManager assetManager = new AssetManager();
	private static boolean newLoad = true;
	
	public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
	
	public Storage() {
		skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));	
		if(newLoad) {
			newLoad = false;
			loadAssets();
		}		
	}
	
	// Load assets
	public static void loadAssets() {
		assetManager.load("InventorySlot.png", Texture.class);
		assetManager.load("weapons/IronGreataxe.png", Texture.class);
		assetManager.load("weapons/IronAxe.png", Texture.class);
		assetManager.load("enemies/Wolfie.png", Texture.class);
		assetManager.load("enemies/Bear.png", Texture.class);
		assetManager.load("enemies/Spider.png", Texture.class);
		assetManager.load("enemies/Monkey.png", Texture.class);
		assetManager.load("player/Onion.png", Texture.class);
		assetManager.load("armor/IronHelmet.png", Texture.class);
		assetManager.load("armor/IronChest.png", Texture.class);
		assetManager.load("armor/IronBoots.png", Texture.class);
	}
	
	// Load abilities
	public Abilities swing = new Swing();
	public Abilities rend = new Rend();
	public Abilities whirlwind = new Whirlwind();
	public Abilities groundBreaker = new GroundBreaker();
	public Abilities bash = new Bash();
	public Abilities barrier = new Barrier();
	public Abilities harden = new Harden();
	
	// Load weapons
	public Weapons ironGreatAxe = new IronGreatAxe();
	public Weapons ironAxe = new IronAxe();
	
	// Load armor
	public Armor ironHelmet = new IronHelmet();
	public Armor ironChest = new IronChest();
	public Armor ironBoots = new IronBoots();
	
	public void addWeapon(Weapons weapon) {
		weapon.setAmount(weapon.getAmount() + 1);
		playerWeapons.add(weapon);
	}
	
	public List<Weapons> getPlayerWeapons() {
        return new ArrayList<>(playerWeapons);
    }
	
	public void removeWeapon(Weapons weapon) {
	    playerWeapons.remove(weapon);
	}
	
	public void addArmor(Armor armor) {
		armor.setAmount(armor.getAmount() + 1);
		playerArmor.add(armor);
	}
	
	public List<Armor> getPlayerArmor() {
        return new ArrayList<>(playerArmor);
    }
	
	public void removeArmor(Armor armor) {
		playerArmor.remove(armor);
	}
	
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
