package player;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class Storage {
	private static Storage instance = null;	
	public Skin skin;
	public TextButton.TextButtonStyle buttonStyle;
	public LabelStyle labelStyle;
	public BitmapFont font;
	public List<Weapons> playerWeapons = new ArrayList<>();
	public List<Armor> playerArmor = new ArrayList<>();
	public List<Items> playerItems = new ArrayList<>();
	public List<Weapons> equippedWeapons = new ArrayList<>();
	public List<Armor> equippedArmor = new ArrayList<>();
	public List<Items> equippedItems = new ArrayList<>();
	public static AssetManager assetManager = new AssetManager();
	private static boolean newLoad = true;
	
	public static Storage getInstance() {
        if (instance == null) {
            instance = new Storage();
        }
        return instance;
    }
	
	public Storage() {
		skin = new Skin(Gdx.files.internal("buttons/newskin/newskin.json"));	
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
		assetManager.load("weapons/WoodenShield.png", Texture.class);
		assetManager.load("enemies/Wolfie.png", Texture.class);
		assetManager.load("enemies/Bear.png", Texture.class);
		assetManager.load("enemies/Spider.png", Texture.class);
		assetManager.load("enemies/Monkey.png", Texture.class);
		assetManager.load("player/Onion.png", Texture.class);
		assetManager.load("armor/IronHelmet.png", Texture.class);
		assetManager.load("armor/IronChest.png", Texture.class);
		assetManager.load("armor/IronBoots.png", Texture.class);
		assetManager.load("items/HealthPotion.png", Texture.class);
		assetManager.load("items/Bomb.png", Texture.class);
	}
	
	// Load items
	public Items healthPot = new HealthPot();
	public Items bomb = new Bomb();
	
	// Load abilities
	public Abilities swing = new Swing();
	public Abilities rend = new Rend();
	public Abilities whirlwind = new Whirlwind();
	public Abilities groundBreaker = new GroundBreaker();
	public Abilities bash = new Bash();
	public Abilities barrier = new Barrier();
	public Abilities harden = new Harden();
	public Abilities mend = new Mend();
	public Abilities hiltBash = new HiltBash();
	public Abilities barbedArmor = new BarbedArmor();
	public Abilities enrage = new Enrage();
	public Abilities riposte = new Riposte();
	public Abilities stab = new Stab();
	public Abilities decapitate = new Decapitate();
	
	// Load weapons
	public Weapons ironGreataxe = new IronGreatAxe();
	public Weapons ironAxe = new IronAxe();
	public Weapons woodenShield = new WoodenShield();
	
	// Load armor
	public Armor ironHelmet = new IronHelmet();
	public Armor ironChest = new IronChest();
	public Armor ironBoots = new IronBoots();
	
	// Inventory arrays
	public void inventoryWeapons(Weapons weapon, String action) {
		if(action.equals("Add")) {
			weapon.setAmount(weapon.getAmount() + 1);
			playerWeapons.add(weapon);			
		}
		else if(action.equals("Remove")) {
			weapon.setAmount(weapon.getAmount() - 1);
			playerWeapons.remove(weapon);
		}	
		else if(action.equals("Clear")) {
			playerWeapons.clear();
		}
	}
	
	public List<Weapons> getPlayerWeapons() {
        return new ArrayList<>(playerWeapons);
    }
	
	public void inventoryArmor(Armor armor, String action) {
		if(action.equals("Add")) {
			armor.setAmount(armor.getAmount() + 1);
			playerArmor.add(armor);
		}
		else if(action.equals("Remove")) {
			armor.setAmount(armor.getAmount() - 1);
			playerArmor.remove(armor);
		}
		else if(action.equals("Clear")) {
			playerArmor.clear();
		}
	}
	
	public List<Armor> getPlayerArmor() {
        return new ArrayList<>(playerArmor);
    }
	
	public void inventoryItems(Items item, String action) {
		if(action.equals("Add")) {
			item.setAmount(item.getAmount() + 1);
			playerItems.add(item);
		}
		else if(action.equals("Remove")) {
			item.setAmount(item.getAmount() - 1);
			playerItems.remove(item);
		}	
		else if(action.equals("Clear")) {
			playerItems.clear();
		}
	}
	
	public List<Items> getPlayerItems() {
        return new ArrayList<>(playerItems);
    }
	
	// Equiped inventory arrays
	public List<Weapons> getEquippedWeapons(){
		return new ArrayList<>(equippedWeapons);
	}
	
	public void equippedWeapons(Weapons weapon, String action) {
		if(action.equals("Add"))
			equippedWeapons.add(weapon);
		else if(action.equals("Remove"))
			equippedWeapons.remove(weapon);
		else if(action.equals("Clear"))
			equippedWeapons.clear();
	}
	
	public List<Armor> getEquippedArmor(){
		return new ArrayList<>(equippedArmor);
	}
	
	public void equippedArmor(Armor armor, String action) {
		if(action.equals("Add"))
			equippedArmor.add(armor);
		else if(action.equals("Remove"))
			equippedArmor.remove(armor);
		else if(action.equals("Clear"))
			equippedArmor.clear();
	}
	
	public void equippedItems(Items item, String action) {
		if(action.equals("Add"))
			equippedItems.add(item);
		else if(action.equals("Remove"))
			equippedItems.remove(item);
		else if(action.equals("Clear"))
			equippedItems.clear();
	}
	
	public List<Items> getEquippedItems() {
        return new ArrayList<>(equippedItems);
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
