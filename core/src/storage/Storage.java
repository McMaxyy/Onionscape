package storage;

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
	private List<Items> playerItems = new ArrayList<>();
	private List<Weapons> equippedWeapons = new ArrayList<>();
	private List<Armor> equippedArmor = new ArrayList<>();
	private List<Items> equippedItems = new ArrayList<>();
	private int[] bonusAP = {0, 0, 0, 0, 0};
	private int[] bonusHP = {0, 0, 0, 0, 0};
	private int[] bonusDP = {0, 0, 0, 0, 0};
	public static AssetManager assetManager = new AssetManager();
	private static boolean newLoad = true;	
	
	public static synchronized Storage getInstance() {
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
		// Inventory weapons
		assetManager.load("InventorySlot.png", Texture.class);
		assetManager.load("weapons/inventory/IronGreataxe.png", Texture.class);
		assetManager.load("weapons/inventory/IronAxe.png", Texture.class);
		assetManager.load("weapons/inventory/WoodenAxe.png", Texture.class);
		assetManager.load("weapons/inventory/WoodenShield.png", Texture.class);
		assetManager.load("weapons/inventory/WoodenGreataxe.png", Texture.class);
		assetManager.load("weapons/inventory/IronShield.png", Texture.class);
		
		// Equipped weapons
		assetManager.load("weapons/equipped/IronGreataxe.png", Texture.class);
		assetManager.load("weapons/equipped/WoodenGreataxe.png", Texture.class);
		assetManager.load("weapons/equipped/IronAxe.png", Texture.class);
		assetManager.load("weapons/equipped/WoodenAxe.png", Texture.class);
		assetManager.load("weapons/equipped/WoodenShield.png", Texture.class);
		assetManager.load("weapons/equipped/IronShield.png", Texture.class);
		
		// Enemy images
		assetManager.load("enemies/Wolfie.png", Texture.class);
		assetManager.load("enemies/Bear.png", Texture.class);
		assetManager.load("enemies/Spider.png", Texture.class);
		assetManager.load("enemies/Monkey.png", Texture.class);
		assetManager.load("enemies/Wasp.png", Texture.class);
		assetManager.load("player/Onion.png", Texture.class);
		
		// Inventory armor
		assetManager.load("armor/inventory/IronHelmet.png", Texture.class);
		assetManager.load("armor/inventory/IronChest.png", Texture.class);
		assetManager.load("armor/inventory/IronBoots.png", Texture.class);
		assetManager.load("armor/inventory/SteelHelmet.png", Texture.class);
		assetManager.load("armor/inventory/SteelChest.png", Texture.class);
		assetManager.load("armor/inventory/SteelBoots.png", Texture.class);
		assetManager.load("armor/inventory/BronzeHelmet.png", Texture.class);
		assetManager.load("armor/inventory/BronzeChest.png", Texture.class);
		assetManager.load("armor/inventory/BronzeBoots.png", Texture.class);
		
		// Equipped armor
		assetManager.load("armor/equipped/IronHelmet.png", Texture.class);
		assetManager.load("armor/equipped/IronChest.png", Texture.class);
		assetManager.load("armor/equipped/IronBoots.png", Texture.class);
		assetManager.load("armor/equipped/SteelHelmet.png", Texture.class);
		assetManager.load("armor/equipped/SteelChest.png", Texture.class);
		assetManager.load("armor/equipped/SteelBoots.png", Texture.class);
		assetManager.load("armor/equipped/BronzeHelmet.png", Texture.class);
		assetManager.load("armor/equipped/BronzeChest.png", Texture.class);
		assetManager.load("armor/equipped/BronzeBoots.png", Texture.class);
		
		// Items
		assetManager.load("items/HealthPotion.png", Texture.class);
		assetManager.load("items/Bomb.png", Texture.class);
		
		// Misc
		assetManager.load("abilities/SwingIcon.png", Texture.class);
		assetManager.load("BattleOver.png", Texture.class);
		assetManager.load("maps/ForestMap.png", Texture.class);
	}
	
	// Load items
	public Items healthPot = new HealthPot();
	public Items bomb = new Bomb();
	public Items itemSwing = new ItemSwing();
	public Items itemRend = new ItemRend();
	public Items itemWhirlwind = new ItemWhirlwind();
	public Items itemGroundBreaker = new ItemGroundBreaker();
	public Items itemBash = new ItemBash();
	public Items itemBarrier = new ItemBarrier();
	public Items itemHarden = new ItemHarden();
	public Items itemMend = new ItemMend();
	public Items itemHiltBash = new ItemHiltBash();
	public Items itemBarbedArmor = new ItemBarbedArmor();
	public Items itemEnrage = new ItemEnrage();
	public Items itemRiposte = new ItemRiposte();
	public Items itemStab = new ItemStab();
	public Items itemDecapitate = new ItemDecapitate();
	
	// Load abilities
	public Abilities emptyAbility = new EmptyAbility();
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
	public Weapons healthyIronGA = new HealthyIronGreatAxe();
	public Weapons strongIronGA = new StrongIronGreatAxe();
	public Weapons defensiveIronGA = new DefensiveIronGreatAxe();
	public Weapons woodenGA = new WoodenGreatAxe();
	public Weapons woodenAxe = new WoodenAxe();
	public Weapons healthyIronAxe = new HealthyIronAxe();
	public Weapons strongIronAxe = new StrongIronAxe();
	public Weapons defensiveIronAxe = new DefensiveIronAxe();
	public Weapons woodenShield = new WoodenShield();
	public Weapons healthyIronShield = new HealthyIronShield();
	public Weapons strongIronShield = new StrongIronShield();
	public Weapons defensiveIronShield = new DefensiveIronShield();
	
	// Load armor
	public Armor healthyIronHelmet = new HealthyIronHelmet();
	public Armor strongIronHelmet = new StrongIronHelmet();
	public Armor defensiveIronHelmet = new DefensiveIronHelmet();
	public Armor healthyIronChest = new HealthyIronChest();
	public Armor strongIronChest = new StrongIronChest();
	public Armor defensiveIronChest = new DefensiveIronChest();
	public Armor healthyIronBoots = new HealthyIronBoots();
	public Armor strongIronBoots = new StrongIronBoots();
	public Armor defensiveIronBoots = new DefensiveIronBoots();
	
	// Load enemies
	public Enemy wolf = new Wolf();
	public Enemy spider = new Spider();
	public Enemy bear = new Bear();
	public Enemy monkey = new Monkey();
	public Enemy wasp = new Wasp();
	
	public void swapAbilities(Abilities ability) {
		emptyAbility = ability;
	}
	
	// Inventory arrays
	public void inventoryWeapons(Weapons weapon, String action) {
		if(action.equals("Add")) {
			if(playerWeapons.size() + playerArmor.size() + playerItems.size() < 40)
				playerWeapons.add(weapon);						
		}
		else if(action.equals("Remove")) 
			playerWeapons.remove(weapon);	
		else if(action.equals("Clear")) {
			playerWeapons.clear();
		}
	}
	
	public List<Weapons> getPlayerWeapons() {
        return new ArrayList<>(playerWeapons);
    }
	
	public void inventoryArmor(Armor armor, String action) {
		if(action.equals("Add")) {
			if(playerWeapons.size() + playerArmor.size() + playerItems.size() < 40)
				playerArmor.add(armor);
		}
		else if(action.equals("Remove"))
			playerArmor.remove(armor);
		else if(action.equals("Clear"))
			playerArmor.clear();
	}
	
	public List<Armor> getPlayerArmor() {
        return new ArrayList<>(playerArmor);
    }
	
	public void inventoryItems(Items item, String action) {
		if(action.equals("Add")) {
			if(playerWeapons.size() + playerArmor.size() + playerItems.size() < 40)
				playerItems.add(item);
		}
		else if(action.equals("Remove"))
			playerItems.remove(item);
		else if(action.equals("Clear"))
			playerItems.clear();
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
		if(action.equals("Add")) {
			if(equippedItems.size() < 14)
				equippedItems.add(item);
		}		
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

	public int[] getBonusHP() {
		return bonusHP;
	}

	public void setBonusHP(int index, int x) {
		this.bonusHP[index] = x;
	}

	public int[] getBonusAP() {
		return bonusAP;
	}

	public void setBonusAP(int index, int x) {
		this.bonusAP[index] = x;
	}
	
	public int[] getBonusDP() {
		return bonusDP;
	}

	public void setBonusDP(int index, int x) {
		this.bonusDP[index] = x;
	}
}
