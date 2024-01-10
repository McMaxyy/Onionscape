package storage;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.graphics.g2d.NinePatch;

public class Storage {
	private static Storage instance = null;
	public Skin skin;
	public TextButton.TextButtonStyle buttonStyle, buttonStyleBig, homeBtnStyle;
	public LabelStyle labelStyle, labelStyleBig, labelStyleSmol;
	public BitmapFont font, fontBig, fontSmol, fontMedium;
	private List<Weapons> playerWeapons = new ArrayList<>();
	private List<Armor> playerArmor = new ArrayList<>();
	private List<Items> playerItems = new ArrayList<>();
	private List<Weapons> equippedWeapons = new ArrayList<>();
	private List<Armor> equippedArmor = new ArrayList<>();
	private List<Items> equippedItems = new ArrayList<>();
	private static int[] bonusAP = {0, 0, 0, 0, 0};
	private static int[] bonusHP = {0, 0, 0, 0, 0};
	private static int[] bonusDP = {0, 0, 0, 0, 0};
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
		TextureLoader.TextureParameter textureParameter = new TextureLoader.TextureParameter(); 
		textureParameter.genMipMaps = true; 
		
		// Inventory weapons
		assetManager.load("InventorySlot.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/IronGreataxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/IronAxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/WoodenAxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/WoodenShield.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/WoodenGreataxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/IronShield.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/BronzeShield.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/BronzeGreataxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/BronzeAxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/SteelShield.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/SteelGreataxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/inventory/SteelAxe.png", Texture.class, textureParameter);
		
		// Equipped weapons
		assetManager.load("weapons/equipped/IronGreataxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/WoodenGreataxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/IronAxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/WoodenAxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/WoodenShield.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/IronShield.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/BronzeShield.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/BronzeGreataxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/BronzeAxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/SteelShield.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/SteelGreataxe.png", Texture.class, textureParameter);
		assetManager.load("weapons/equipped/SteelAxe.png", Texture.class, textureParameter);
		
		// Enemy images
		assetManager.load("enemies/Wolfie.png", Texture.class, textureParameter);
		assetManager.load("enemies/Bear.png", Texture.class, textureParameter);
		assetManager.load("enemies/Spider.png", Texture.class, textureParameter);
		assetManager.load("enemies/Monkey.png", Texture.class, textureParameter);
		assetManager.load("enemies/Wasp.png", Texture.class, textureParameter);		
		assetManager.load("enemies/Boar.png", Texture.class, textureParameter);	
		assetManager.load("enemies/MimicTree.png", Texture.class, textureParameter);	
		assetManager.load("enemies/ForestGuardian.png", Texture.class, textureParameter);	
		assetManager.load("enemies/Vulture.png", Texture.class, textureParameter);	
		
		// Inventory armor
		assetManager.load("armor/inventory/IronHelmet.png", Texture.class, textureParameter);
		assetManager.load("armor/inventory/IronChest.png", Texture.class, textureParameter);
		assetManager.load("armor/inventory/IronBoots.png", Texture.class, textureParameter);
		assetManager.load("armor/inventory/SteelHelmet.png", Texture.class, textureParameter);
		assetManager.load("armor/inventory/SteelChest.png", Texture.class, textureParameter);
		assetManager.load("armor/inventory/SteelBoots.png", Texture.class, textureParameter);
		assetManager.load("armor/inventory/BronzeHelmet.png", Texture.class, textureParameter);
		assetManager.load("armor/inventory/BronzeChest.png", Texture.class, textureParameter);
		assetManager.load("armor/inventory/BronzeBoots.png", Texture.class, textureParameter);
		
		// Equipped armor
		assetManager.load("armor/equipped/IronHelmet.png", Texture.class, textureParameter);
		assetManager.load("armor/equipped/IronChest.png", Texture.class, textureParameter);
		assetManager.load("armor/equipped/IronBoots.png", Texture.class, textureParameter);
		assetManager.load("armor/equipped/SteelHelmet.png", Texture.class, textureParameter);
		assetManager.load("armor/equipped/SteelChest.png", Texture.class, textureParameter);
		assetManager.load("armor/equipped/SteelBoots.png", Texture.class, textureParameter);
		assetManager.load("armor/equipped/BronzeHelmet.png", Texture.class, textureParameter);
		assetManager.load("armor/equipped/BronzeChest.png", Texture.class, textureParameter);
		assetManager.load("armor/equipped/BronzeBoots.png", Texture.class, textureParameter);
		
		// Items
		assetManager.load("items/HealthPotion.png", Texture.class, textureParameter);
		assetManager.load("items/Bomb.png", Texture.class, textureParameter);
		assetManager.load("items/AttackBoost.png", Texture.class, textureParameter);
		assetManager.load("items/DefenseBoost.png", Texture.class, textureParameter);
		assetManager.load("items/HealthBoost.png", Texture.class, textureParameter);
		assetManager.load("items/ExperienceBoost.png", Texture.class, textureParameter);
		
		// Maps
		assetManager.load("maps/InventoryScreen.png", Texture.class, textureParameter);
		assetManager.load("maps/StartScreen.png", Texture.class, textureParameter);
		assetManager.load("maps/ForestMap.png", Texture.class, textureParameter);
		assetManager.load("maps/ForestFight.png", Texture.class, textureParameter);
		assetManager.load("maps/HomeScreen.png", Texture.class, textureParameter);
		assetManager.load("maps/MerchantScreen.png", Texture.class, textureParameter);

		// Buffs & Debuffs
		assetManager.load("buffs/Barrier.png", Texture.class, textureParameter);
		assetManager.load("buffs/Enrage.png", Texture.class, textureParameter);
		assetManager.load("buffs/Bleed.png", Texture.class, textureParameter);
		assetManager.load("buffs/Poison.png", Texture.class, textureParameter);
		assetManager.load("buffs/Stun.png", Texture.class, textureParameter);
		assetManager.load("buffs/Harden.png", Texture.class, textureParameter);
		assetManager.load("buffs/Weaken.png", Texture.class, textureParameter);
		assetManager.load("buffs/Thorns.png", Texture.class, textureParameter);
		
		// Slot minigame
		assetManager.load("slots/10.png", Texture.class, textureParameter);
		assetManager.load("slots/A.png", Texture.class, textureParameter);
		assetManager.load("slots/Book.png", Texture.class, textureParameter);
		assetManager.load("slots/Gun.png", Texture.class, textureParameter);
		assetManager.load("slots/J.png", Texture.class, textureParameter);
		assetManager.load("slots/K.png", Texture.class, textureParameter);
		assetManager.load("slots/Q.png", Texture.class, textureParameter);
		assetManager.load("slots/Skull.png", Texture.class, textureParameter);
		assetManager.load("slots/Scatter.png", Texture.class, textureParameter);
		
		// Misc
		assetManager.load("abilities/SwingIcon.png", Texture.class, textureParameter);
		assetManager.load("player/Onion.png", Texture.class, textureParameter);
		assetManager.load("player/MapIcon.png", Texture.class, textureParameter);	
		assetManager.load("items/CardLarge.png", Texture.class, textureParameter);	
		assetManager.load("items/CardSmall.png", Texture.class, textureParameter);	
	}
	
	// Load items
	public Items healthPot = new HealthPot();
	public Items bomb = new Bomb();
	public Items throwingKnife = new ThrowingKnife();
	public Items expBoost = new ExpBoost();
	public Items apBoost = new APBoost();
	public Items dpBoost = new DPBoost();
	public Items hpBoost = new HPBoost();
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
	public Weapons healthyBronzeShield = new HealthyBronzeShield();
	public Weapons strongBronzeShield = new StrongBronzeShield();
	public Weapons defensiveBronzeShield = new DefensiveBronzeShield();
	public Weapons healthySteelShield = new HealthySteelShield();
	public Weapons strongSteelShield = new StrongSteelShield();
	public Weapons defensiveSteelShield = new DefensiveSteelShield();
	public Weapons healthyBronzeGA = new HealthyBronzeGreatAxe();
	public Weapons strongBronzeGA = new StrongBronzeGreatAxe();
	public Weapons defensiveBronzeGA = new DefensiveBronzeGreatAxe();
	public Weapons healthyBronzeAxe = new HealthyBronzeAxe();
	public Weapons strongBronzeAxe = new StrongBronzeAxe();
	public Weapons defensiveBronzeAxe = new DefensiveBronzeAxe();
	public Weapons healthySteelGA = new HealthySteelGreatAxe();
	public Weapons strongSteelGA = new StrongSteelGreatAxe();
	public Weapons defensiveSteelGA = new DefensiveSteelGreatAxe();
	public Weapons healthySteelAxe = new HealthySteelAxe();
	public Weapons strongSteelAxe = new StrongSteelAxe();
	public Weapons defensiveSteelAxe = new DefensiveSteelAxe();
	
	// Load armor
	public Armor ironHelmet = new IronHelmet();
	public Armor ironChest = new IronChest();
	public Armor ironBoots = new IronBoots();
	public Armor healthyIronHelmet = new HealthyIronHelmet();
	public Armor strongIronHelmet = new StrongIronHelmet();
	public Armor defensiveIronHelmet = new DefensiveIronHelmet();
	public Armor healthyIronChest = new HealthyIronChest();
	public Armor strongIronChest = new StrongIronChest();
	public Armor defensiveIronChest = new DefensiveIronChest();
	public Armor healthyIronBoots = new HealthyIronBoots();
	public Armor strongIronBoots = new StrongIronBoots();
	public Armor defensiveIronBoots = new DefensiveIronBoots();
	public Armor healthyBronzeHelmet = new HealthyBronzeHelmet();
	public Armor strongBronzeHelmet = new StrongBronzeHelmet();
	public Armor defensiveBronzeHelmet = new DefensiveBronzeHelmet();
	public Armor healthyBronzeChest = new HealthyBronzeChest();
	public Armor strongBronzeChest = new StrongBronzeChest();
	public Armor defensiveBronzeChest = new DefensiveBronzeChest();
	public Armor healthyBronzeBoots = new HealthyBronzeBoots();
	public Armor strongBronzeBoots = new StrongBronzeBoots();
	public Armor defensiveBronzeBoots = new DefensiveBronzeBoots();
	public Armor healthySteelHelmet = new HealthySteelHelmet();
	public Armor strongSteelHelmet = new StrongSteelHelmet();
	public Armor defensiveSteelHelmet = new DefensiveSteelHelmet();
	public Armor healthySteelChest = new HealthySteelChest();
	public Armor strongSteelChest = new StrongSteelChest();
	public Armor defensiveSteelChest = new DefensiveSteelChest();
	public Armor healthySteelBoots = new HealthySteelBoots();
	public Armor strongSteelBoots = new StrongSteelBoots();
	public Armor defensiveSteelBoots = new DefensiveSteelBoots();
	
	// Load enemies
	public Enemy wolf = new Wolf();
	public Enemy spider = new Spider();
	public Enemy bear = new Bear();
	public Enemy monkey = new Monkey();
	public Enemy wasp = new Wasp();
	public Enemy forestGuardian = new ForestGuardian();
	public Enemy mimicTree = new MimicTree();
	public Enemy boar = new BoarBoss();
	public Enemy vulture = new Vulture();
	
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
	
	public String loseItem() {
		String item = equippedItems.get(0).getItemName();
		equippedItems.remove(0);
		return item;
	}
	
	public List<Items> getEquippedItems() {
        return new ArrayList<>(equippedItems);
    }
	
	public void createFont() {
    	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Cascadia.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 25;
        parameter.minFilter = Texture.TextureFilter.Linear;
        parameter.magFilter = Texture.TextureFilter.Linear;
        font = generator.generateFont(parameter);
        parameter.size = 50;
        fontBig = generator.generateFont(parameter);
        parameter.size = 18;
        fontSmol = generator.generateFont(parameter);
        parameter.size = 40;
        fontMedium = generator.generateFont(parameter);
        
        Texture borderTextureUp = new Texture(Gdx.files.internal("buttons/newskin/newskin_data/textbutton.9.png"));
        Texture borderTextureDown = new Texture(Gdx.files.internal("buttons/newskin/newskin_data/textbutton-down.9.png"));
        Texture homeBorderTextureUp = new Texture(Gdx.files.internal("buttons/newskin/newskin_data/homeButton.9.png"));
        Texture homeBorderTextureDown = new Texture(Gdx.files.internal("buttons/newskin/newskin_data/homeButton-down.9.png"));
        NinePatch borderPatchUp = new NinePatch(borderTextureUp, 1, 1, 1, 1);
        NinePatch borderPatchDown = new NinePatch(borderTextureDown, 1, 1, 1, 1);
        NinePatch homeBorderPatchUp = new NinePatch(homeBorderTextureUp, 1, 1, 1, 1);
        NinePatch homeBorderPatchDown = new NinePatch(homeBorderTextureDown, 1, 1, 1, 1);
        
        // Create a new style for button
        buttonStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        buttonStyle.up = new NinePatchDrawable(borderPatchUp);
        buttonStyle.down = new NinePatchDrawable(borderPatchDown);
        buttonStyle.font = font;
        
        buttonStyleBig = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        buttonStyleBig.up = new NinePatchDrawable(borderPatchUp);
        buttonStyleBig.down = new NinePatchDrawable(borderPatchDown);
        buttonStyleBig.font = fontBig; 
                
        homeBtnStyle = new TextButton.TextButtonStyle(skin.get(TextButton.TextButtonStyle.class));
        homeBtnStyle.up = new NinePatchDrawable(homeBorderPatchUp);
        homeBtnStyle.down = new NinePatchDrawable(homeBorderPatchDown);
        homeBtnStyle.font = font;

        labelStyle = new Label.LabelStyle(skin.get(Label.LabelStyle.class));
        labelStyle.font = font;  
        
        labelStyleBig = new Label.LabelStyle(skin.get(Label.LabelStyle.class));
        labelStyleBig.font = fontBig; 
        
        labelStyleSmol = new Label.LabelStyle(skin.get(Label.LabelStyle.class));
        labelStyleSmol.font = fontSmol; 
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
