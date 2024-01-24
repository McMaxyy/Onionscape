package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;
import com.onionscape.game.SaveData;
import com.onionscape.game.TextureManager;

import java.util.HashMap;
import java.util.Map;

import player.Player;
import storage.Armor;
import storage.Items;
import storage.Storage;
import storage.Weapons;

public class Inventory implements Screen {
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Game game;
	private GameScreen gameScreen;
	private Storage storage;
	private TextButton backBtn;
	private Label gearName;
	public static Table characterTable = new Table();
	Table inventoryTable = new Table();	
	Table itemTable = new Table();
	java.util.List<Weapons> inventoryWeapons;
	java.util.List<Armor> inventoryArmor;
	java.util.List<Items> inventoryItems;
	java.util.List<Weapons> equippedWeapons;
	java.util.List<Armor> equippedArmor;
	java.util.List<Items> equippedItems;
	private SpriteBatch onionBatch = new SpriteBatch();
	private SpriteBatch weaponBatch = new SpriteBatch();
	private SpriteBatch shieldBatch = new SpriteBatch();
	private SpriteBatch helmetBatch = new SpriteBatch();
	private SpriteBatch chestBatch = new SpriteBatch();
	private SpriteBatch bootsBatch = new SpriteBatch();
	private SpriteBatch mapBatch = new SpriteBatch();
	private SpriteBatch abilityBatch = new SpriteBatch();
	public static int shieldDP = 0, helmetDP = 0, chestDP = 0, bootsDP = 0, weaponAP = 0,
			bonusHP = 0, bonusAP = 0, bonusDP = 0;
	boolean twoHand = false, gearEq, showCard;
	public static boolean haveGear = true;
	
	// Map texture
	private Texture mapTexture  = Storage.assetManager.get("maps/InventoryScreen.png", Texture.class);
	
	// Get textures from storage
	
	
	public Inventory(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		storage.createFont();	
		characterTable.clear();
		Home.freshLoad = false;

		smoothFilter();				
		createComponents();	
		createInventoryGrid();
		createCharacterGrid();
		createItemGrid();
		removeBonusStats();
		if(gearEq)
			addBonusStats();
	}
	
	private void addBonusStats() {
		System.out.println("Adding stats");
		Player.setDmgResist(helmetDP + chestDP + bootsDP + shieldDP + bonusDP);
		Player.setWeaponDmg(weaponAP);	
		Player.gainBonusStr(bonusAP);
		Player.gainMaxHP(bonusHP);
	}

	private void smoothFilter(){
		mapTexture.setFilter(TextureFilter.MipMap,TextureFilter.Nearest);
		
	}
	
	private void removeBonusStats() {		
		Actor item = characterTable.getChildren().get(0);					
		String itemName = item.getName();
		if(!itemName.equals("Empty")) {
			setArmor(itemName, "Helmet");
			addArmorDefenses(itemName);
		}
		
		item = characterTable.getChildren().get(1);					
		itemName = item.getName();
		if(!itemName.equals("Empty")) {
			setArmor(itemName, "Chest");
			addArmorDefenses(itemName);
		}	
		
		item = characterTable.getChildren().get(2);					
		itemName = item.getName();
		if(!itemName.equals("Empty")) {
			setArmor(itemName, "Boots");
			addArmorDefenses(itemName);
		}
		
		item = characterTable.getChildren().get(3);					
		itemName = item.getName();
		if(!itemName.equals("Empty")) {
			if(itemName.endsWith("Axe"))
				setWeapon(itemName, "OneHanded");	
			else
				setWeapon(itemName, "TwoHanded");
			addWeaponDamage(itemName);
		}
						
		item = characterTable.getChildren().get(4);					
		itemName = item.getName();
		if(!itemName.equals("Empty")) {
			setWeapon(itemName, "Offhand");		
			addWeaponDamage(itemName);
		}
		if(SaveData.loaded) {
			bonusAP = bonusHP = bonusDP = 0;
			for(int ap : storage.getBonusAP())
				bonusAP += ap;
			for(int hp : storage.getBonusHP())
				bonusHP += hp;
			for(int dp : storage.getBonusDP())
				bonusDP += dp;
		}
		
		if(helmetDP != 0 || chestDP != 0 || bootsDP != 0 || shieldDP != 0 || weaponAP != 0)	
			if(!haveGear) {
				haveGear = true;
				gearEq = true;
			}
		Player.loseDR(helmetDP + chestDP + bootsDP + shieldDP + bonusDP);
		Player.loseMaxHP(bonusHP);
		Player.loseWeaponDmg(weaponAP);
		Player.loseBonusStr(bonusAP);
	}
	
	private void createInventoryGrid() {    
	    inventoryWeapons = storage.getPlayerWeapons();
	    inventoryArmor = storage.getPlayerArmor();
	    inventoryItems = storage.getPlayerItems();
	    inventoryTable.defaults().size(100, 100);
	    inventoryTable.setName("inventoryTable");
	    
	    int weaponIndex = 0;
	    int armorIndex = 0;
	    int itemIndex = 0;
	    
	    for (int y = 0; y < 8; y++) {
	        for (int x = 0; x < 5; x++) {                
	            boolean emptySlot = false;
	            String itemName = "";
	            Texture slotTexture = null;
	            int itemPower = 0;
	            int bonus1 = 0;
	            int gType = 0;
	        	
	            // Check if there's a weapon to display in this slot.
	            if (weaponIndex < inventoryWeapons.size()) {
	                Weapons weapon = inventoryWeapons.get(weaponIndex);
	                slotTexture = setSlotImage(weapon.getWeaponName(), "Weapon");
	                weaponIndex++;
	                itemName = weapon.getWeaponName();
	                itemPower = weapon.getWeaponDmg();
	                bonus1 = weapon.getBonusStat();
	                gType = 1;
	            } 
	            else if (armorIndex < inventoryArmor.size()) {
	                Armor armor = inventoryArmor.get(armorIndex);
	                slotTexture = setSlotImage(armor.getArmorName(), "Armor");
	                armorIndex++;
	                itemName = armor.getArmorName();
	                itemPower = armor.getDefense();
	                bonus1 = armor.getBonusStat();
	                gType = 2;
	            }
	            else if (itemIndex < inventoryItems.size()) {
	                Items item = inventoryItems.get(itemIndex);
	                slotTexture = setSlotImage(item.getItemName(), "Item");
	                itemIndex++;
	                itemName = item.getItemName();	
	            }
	            else {
	                slotTexture = setSlotImage("", "");
	                emptySlot = true;
	            }

	            final Image inventorySlotImage = new Image(slotTexture);
	            inventoryTable.add(inventorySlotImage).pad(3);
	            
	            if(emptySlot) {
	                emptySlot = false;
	                inventorySlotImage.setName("Empty");
	            }
	            else {
	                inventorySlotImage.setName(itemName);                    
	            }                    
	            
	            final String item = itemName;
            	final int itemP = itemPower;
            	final int bonus = bonus1;
            	String gearType = "";
            	
            	String prefix;
            	if(item.startsWith("Healthy"))
            		prefix = " Health";
            	else if(item.startsWith("Strong"))
            		prefix = " Attack Power";
            	else if(item.startsWith("Defensive"))
            		prefix = " Defense";
            	else
            		prefix = "";
            	
            	switch(gType) {
            	case 0:
            		gearType = "";
            		break;
            	case 1:
            		gearType = "Attack Power: ";
            		break;
            	case 2:
            		gearType = "Defense: ";
            		break;
            	}
            	
            	final String type = gearType;
            	final String bonusStat = prefix; 

	            inventorySlotImage.addListener(new ClickListener() {
	                @Override
	                public void clicked(InputEvent event, float x, float y) {
	                    handleInventoryClick(inventorySlotImage, item);
	                }
	            });
	            
	            inventorySlotImage.addListener(new InputListener() {
	                @Override
	                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                    if(itemP > 0 && bonus > 0)
	                    	gearName.setText(item + "\n\n\n+" + bonus + bonusStat + "\n\n\n" + type + itemP);
	                    else if(itemP > 0)
	                    	gearName.setText(item + "\n\n\n" + type + itemP);
	                    else
	                    	gearName.setText(item + "\n\n\n" + storage.itemDescription(item));
	                    gearName.setAlignment(Align.center);
	                	gearName.setVisible(true);	                  	                    
	                    gearName.setPosition(vp.getWorldWidth() / 2.2f, vp.getWorldHeight() / 2f);
	                    if(!item.equals(""))
	                    	showCard = true;
	                }

	                @Override
	                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    gearName.setVisible(false);
	                    showCard = false;
	                }
	            });
	        }
	        inventoryTable.row(); // To move to the next row after 5 items
	    } 
	    
	    inventoryTable.setPosition(vp.getWorldWidth() / 1.25f, vp.getWorldHeight() / 2f, Align.center);
	    stage.addActor(inventoryTable);
	}
	
	public void createCharacterGrid() {
		equippedWeapons = storage.getEquippedWeapons();
		equippedArmor = storage.getEquippedArmor();		
	    characterTable.defaults().size(100, 100);
	    characterTable.setName("characterTable");
	    
	    // Create a map of armor type to armor for quick lookup
	    Map<String, Armor> armorMap = new HashMap<>();
	    for (Armor armor : equippedArmor) {
	        if (armor.getArmorName().endsWith("Helmet")) {
	            armorMap.put("Helmet", armor);
	        } else if (armor.getArmorName().endsWith("Chest")) {
	            armorMap.put("Chest", armor);
	        } else if (armor.getArmorName().endsWith("Boots")) {
	            armorMap.put("Boots", armor);
	        }
	    }
	    
	    Map<String, Weapons> weaponMap = new HashMap<>();
	    for(Weapons weapon : equippedWeapons) {
	    	if(weapon.getWeaponName().endsWith("Greataxe"))
	    		weaponMap.put("Greataxe", weapon);
	    	else if(weapon.getWeaponName().endsWith("Axe"))
	    		weaponMap.put("Axe", weapon);
	    	else if(weapon.getWeaponName().endsWith("Shield"))
	    		weaponMap.put("Shield", weapon);
	    }
	    
		for(int i = 0; i < 5; i++) {
	        String itemName = "";
	        Texture slotTexture = setSlotImage("", "");
            boolean emptySlot = true;
            int itemPower = 0;
            int bonus1 = 0;
            int gType = 0;
	        
	        if (i <= 2) {  // Checking armor slots
	            String armorType = (i == 0) ? "Helmet" : (i == 1) ? "Chest" : "Boots";
	            if (armorMap.containsKey(armorType)) {
	                Armor armor = armorMap.get(armorType);
	                slotTexture = setSlotImage(armor.getArmorName(), "Armor");
	                emptySlot = false;
	                itemName = armor.getArmorName();
	                itemPower = armor.getDefense();
	                bonus1 = armor.getBonusStat();
	                gType = 2;
	            }
	        }
	        else if(i >= 3) {
	        	if(i == 3) {
	        		if (weaponMap.containsKey("Greataxe")) {
	                    Weapons weapon = weaponMap.get("Greataxe");
	                    slotTexture = setSlotImage(weapon.getWeaponName(), "Weapon");
	                    emptySlot = false;
	                    itemName = weapon.getWeaponName();
	                    itemPower = weapon.getWeaponDmg();
		                bonus1 = weapon.getBonusStat();
		                gType = 1;
	                }
	        		else if (weaponMap.containsKey("Axe")) {
	                    Weapons weapon = weaponMap.get("Axe");
	                    slotTexture = setSlotImage(weapon.getWeaponName(), "Weapon");
	                    emptySlot = false;
	                    itemName = weapon.getWeaponName();
	                    itemPower = weapon.getWeaponDmg();
		                bonus1 = weapon.getBonusStat();
		                gType = 1;
	                }	        		
	        	}
	        	else if(i == 4) {
	        		if (weaponMap.containsKey("Shield")) {
	                    Weapons weapon = weaponMap.get("Shield");
	                    slotTexture = setSlotImage(weapon.getWeaponName(), "Weapon");
	                    emptySlot = false;
	                    itemName = weapon.getWeaponName();
	                    itemPower = weapon.getWeaponDmg();
		                bonus1 = weapon.getBonusStat();
		                gType = 2;
	                }
	        	}
	        }	                  
            else {
                slotTexture = setSlotImage("", "");
                emptySlot = true;
            }	
			 
			final Image characterSlotImage = new Image(slotTexture);
            characterTable.add(characterSlotImage).pad(3);
            
            if(emptySlot) {
                emptySlot = false;
                characterSlotImage.setName("Empty");
            }
            else {
            	characterSlotImage.setName(itemName);                    
            }                    

            final String item = itemName;
        	final int itemP = itemPower;
        	final int bonus = bonus1;
        	String gearType = "";
        	
        	String prefix;
        	if(item.startsWith("Healthy"))
        		prefix = " Health";
        	else if(item.startsWith("Strong"))
        		prefix = " Attack Power";
        	else if(item.startsWith("Defensive"))
        		prefix = " Defense";
        	else
        		prefix = "";
        	
        	switch(gType) {
        	case 0:
        		gearType = "";
        		break;
        	case 1:
        		gearType = "Attack Power: ";
        		break;
        	case 2:
        		gearType = "Defense: ";
        		break;
        	}
        	
        	final String type = gearType;
        	final String bonusStat = prefix;
	    	
	    	characterSlotImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                	handleCharacterSlotClick(characterSlotImage, item);
                }
            });
	    	characterSlotImage.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    if(itemP > 0 && bonus > 0)
                    	gearName.setText(item + "\n\n+" + bonus + bonusStat + "\n\n" + type + itemP);
                    else if(itemP > 0)
                    	gearName.setText(item + "\n\n" + type + itemP);
                    else
                    	gearName.setText(item);
                    gearName.setAlignment(Align.center);
                	gearName.setVisible(true);	                  	                    
                    gearName.setPosition(vp.getWorldWidth() / 2.2f, vp.getWorldHeight() / 2f);
                    if(!item.equals(""))
                    	showCard = true;
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    gearName.setVisible(false);
                    showCard = false;
                }
            });
	    	
	    	characterTable.row();
        }
		characterTable.setPosition(vp.getWorldWidth() / 5f, vp.getWorldHeight() / 1.55f, Align.center);
	    stage.addActor(characterTable);
	}
	
	private void createItemGrid() {    
	    equippedItems = storage.getEquippedItems();
	    itemTable.defaults().size(100, 100); 
	    itemTable.setName("itemTable");

	    int itemIndex = 0;
	    
	    for (int y = 0; y < 2; y++) {
	        for (int x = 0; x < 7; x++) {                
	            boolean emptySlot = false;
	            String itemName = "";
	            Texture slotTexture = null;
	            
	            // Check if there's a weapon to display in this slot.
	            if (itemIndex < equippedItems.size()) {
	                Items item = equippedItems.get(itemIndex);
	                slotTexture = setSlotImage(item.getItemName(), "Item");
	                itemIndex++;
	                itemName = item.getItemName();
	            }
	            else {
	                slotTexture = setSlotImage("", "");
	                emptySlot = true;
	            }

	            final Image inventorySlotImage = new Image(slotTexture);
	            itemTable.add(inventorySlotImage).pad(3);
	            
	            if(emptySlot) {
	                emptySlot = false;
	                inventorySlotImage.setName("Empty");
	            }
	            else {
	                inventorySlotImage.setName(itemName);                    
	            }                    

	            final String item = itemName;

	            inventorySlotImage.addListener(new ClickListener() {
	                @Override
	                public void clicked(InputEvent event, float x, float y) {
	                    handleEquippedItemsClick(inventorySlotImage, item);
	                }					
	            });
	            inventorySlotImage.addListener(new InputListener() {
	                @Override
	                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                	gearName.setText(item + "\n\n\n" + storage.itemDescription(item));
	                    gearName.setAlignment(Align.center);
	                	gearName.setVisible(true);	                  	                    
	                    gearName.setPosition(vp.getWorldWidth() / 2.2f, vp.getWorldHeight() / 2f);
	                    if(!item.equals(""))
	                    	showCard = true;
	                }

	                @Override
	                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    gearName.setVisible(false);
	                    showCard = false;
	                }
	            });
	        }
	        itemTable.row();
	    } 
	    
	    itemTable.setPosition(vp.getWorldWidth() / 3.5f, vp.getWorldHeight() / 4.85f, Align.center);
	    stage.addActor(itemTable);
	}
	
	private void setArmorBonus(String bonus, int slot, Armor armor) {
		switch(bonus) {
		case "Healthy":
			storage.setBonusHP(slot, armor.getBonusStat());
			break;
		case "Strong":
			storage.setBonusAP(slot, armor.getBonusStat());
			break;
		case "Defensive":
			storage.setBonusDP(slot, armor.getBonusStat());
			break;
		}
	}
	
	private void setWeaponBonus(String bonus, int slot, Weapons weapon) {
		switch(bonus) {
		case "Healthy":
			storage.setBonusHP(slot, weapon.getBonusStat());
			break;
		case "Strong":
			storage.setBonusAP(slot, weapon.getBonusStat());
			break;
		case "Defensive":
			storage.setBonusDP(slot, weapon.getBonusStat());
			break;
		}
	}
	
	private Texture setSlotImage(String itemName, String gearType) {
		String gearSlot = "";
		String[] words = itemName.split(" ");
		if(!itemName.equals("") && !gearType.equals("Item"))
			gearSlot = words[words.length - 2] + " " + words[words.length - 1];
		
		if(gearType == "Weapon") {
			switch(gearSlot) {
			case "Wooden Greataxe":
				return TextureManager.woodenGreataxeTexture;
			case "Iron Greataxe":
				return TextureManager.ironGreataxeTexture;
			case "Iron Axe":
				return TextureManager.ironAxeTexture;
			case "Wooden Axe":
				return TextureManager.woodenAxeTexture;
			case "Wooden Shield":
				return TextureManager.woodenShieldTexture;
			case "Iron Shield":
				return TextureManager.ironShieldTexture;
			case "Bronze Greataxe":
				return TextureManager.bronzeGreataxeTexture;
			case "Bronze Axe":
				return TextureManager.bronzeAxeTexture;
			case "Bronze Shield":
				return TextureManager.bronzeShieldTexture;
			case "Steel Greataxe":
				return TextureManager.steelGreataxeTexture;
			case "Steel Axe":
				return TextureManager.steelAxeTexture;
			case "Steel Shield":
				return TextureManager.steelShieldTexture;
			case "":
				return TextureManager.inventorySlotTexture;
			default:
				return TextureManager.inventorySlotTexture;
			}
		}
		else if(gearType == "Armor") {
			switch(gearSlot) {
			case "Iron Helmet":
				return TextureManager.ironHelmetTexture;
			case "Iron Chest":
				return TextureManager.ironChestTexture;
			case "Iron Boots":
				return TextureManager.ironBootsTexture;
			case "Bronze Helmet":
				return TextureManager.bronzeHelmetTexture;
			case "Bronze Chest":
				return TextureManager.bronzeChestTexture;
			case "Bronze Boots":
				return TextureManager.bronzeBootsTexture;
			case "Steel Helmet":
				return TextureManager.steelHelmetTexture;
			case "Steel Chest":
				return TextureManager.steelChestTexture;
			case "Steel Boots":
				return TextureManager.steelBootsTexture;
			case "":
				return TextureManager.inventorySlotTexture;
			default:
				return TextureManager.inventorySlotTexture;
			}
		}
		else if(gearType == "Item") {
			switch(itemName) {
			case "Health Potion":
				return TextureManager.healthPotionTexture;
			case "Bomb":
				return TextureManager.bombTexture;
			case "Throwing Knife":
				return TextureManager.knifeTexture;
			case "Swing":
				return TextureManager.swingTexture;
			case "Rend":
				return TextureManager.rendTexture;
			case "Whirlwind":
				return TextureManager.whirlwindTexture;
			case "Ground Breaker":
				return TextureManager.groundBreakerTexture;
			case "Bash":
				return TextureManager.bashTexture;
			case "Barrier":
				return TextureManager.barrierTexture;
			case "Harden":
				return TextureManager.hardenTexture;
			case "Mend":
				return TextureManager.mendTexture;
			case "Hilt Bash":
				return TextureManager.hiltBashTexture;
			case "Barbed Armor":
				return TextureManager.barbedArmorTexture;
			case "Enrage":
				return TextureManager.enrageTexture;
			case "Riposte":
				return TextureManager.riposteTexture;
			case "Stab":
				return TextureManager.stabTexture;
			case "Decapitate":
				return TextureManager.decapitateTexture;
			case "Attack Boost":
				return TextureManager.apTexture;
			case "Defense Boost":
				return TextureManager.dpTexture;
			case "Health Boost":
				return TextureManager.hpTexture;
			case "Experience Boost":
				return TextureManager.expTexture;
			case "Ability Refill Potion":
				return TextureManager.abilityRefillTexture;
			default:
				return TextureManager.inventorySlotTexture;
			}
		}
		else
			return TextureManager.inventorySlotTexture;
	}	
	
	private String checkGearSlot(String gearSlot) {
		if(gearSlot.endsWith("Helmet"))
			return "Helmet";
		else if(gearSlot.endsWith("Chest"))
			return "Chest";
		else if(gearSlot.endsWith("Boots"))
			return "Boots";
		else if(gearSlot.endsWith("Greataxe"))
			return "TwoHanded";
		else if(gearSlot.endsWith("Axe"))
			return "OneHanded";
		else if(gearSlot.endsWith("Shield"))
			return "OffHand";
		else
			return null;
	}
	
	private void changeEquippedSlot(int slot, String gearSlot) {
		Actor tableItem = characterTable.getChildren().get(slot);
		String gearPiece = tableItem.getName();				
		
		if(gearSlot == "Helmet") {
			helmetDP = 0;
			switch(gearPiece) {
			case "Healthy Iron Helmet":
				storage.inventoryArmor(storage.healthyIronHelmet, "Add");
				storage.equippedArmor(storage.healthyIronHelmet, "Remove");	
				storage.setBonusHP(0, 0);
				break;
			case "Strong Iron Helmet":
				storage.inventoryArmor(storage.strongIronHelmet, "Add");
				storage.equippedArmor(storage.strongIronHelmet, "Remove");	
				storage.setBonusAP(0, 0);
				break;
			case "Defensive Iron Helmet":
				storage.inventoryArmor(storage.defensiveIronHelmet, "Add");
				storage.equippedArmor(storage.defensiveIronHelmet, "Remove");	
				storage.setBonusDP(0, 0);
				break;
			case "Healthy Bronze Helmet":
				storage.inventoryArmor(storage.healthyBronzeHelmet, "Add");
				storage.equippedArmor(storage.healthyBronzeHelmet, "Remove");	
				storage.setBonusHP(0, 0);
				break;
			case "Strong Bronze Helmet":
				storage.inventoryArmor(storage.strongBronzeHelmet, "Add");
				storage.equippedArmor(storage.strongBronzeHelmet, "Remove");	
				storage.setBonusAP(0, 0);
				break;
			case "Defensive Bronze Helmet":
				storage.inventoryArmor(storage.defensiveBronzeHelmet, "Add");
				storage.equippedArmor(storage.defensiveBronzeHelmet, "Remove");	
				storage.setBonusDP(0, 0);
				break;
			case "Healthy Steel Helmet":
				storage.inventoryArmor(storage.healthySteelHelmet, "Add");
				storage.equippedArmor(storage.healthySteelHelmet, "Remove");	
				storage.setBonusHP(0, 0);
				break;
			case "Strong Steel Helmet":
				storage.inventoryArmor(storage.strongSteelHelmet, "Add");
				storage.equippedArmor(storage.strongSteelHelmet, "Remove");	
				storage.setBonusAP(0, 0);
				break;
			case "Defensive Steel Helmet":
				storage.inventoryArmor(storage.defensiveSteelHelmet, "Add");
				storage.equippedArmor(storage.defensiveSteelHelmet, "Remove");	
				storage.setBonusDP(0, 0);
				break;
			}		
		}
		else if(gearSlot == "Chest") {
			chestDP = 0;
			switch(gearPiece) {
			case "Healthy Iron Chest":
				storage.inventoryArmor(storage.healthyIronChest, "Add");
				storage.equippedArmor(storage.healthyIronChest, "Remove");	
				storage.setBonusHP(1, 0);
				break;
			case "Strong Iron Chest":
				storage.inventoryArmor(storage.strongIronChest, "Add");
				storage.equippedArmor(storage.strongIronChest, "Remove");	
				storage.setBonusAP(1, 0);
				break;
			case "Defensive Iron Chest":
				storage.inventoryArmor(storage.defensiveIronChest, "Add");
				storage.equippedArmor(storage.defensiveIronChest, "Remove");	
				storage.setBonusDP(1, 0);
				break;
			case "Healthy Bronze Chest":
				storage.inventoryArmor(storage.healthyBronzeChest, "Add");
				storage.equippedArmor(storage.healthyBronzeChest, "Remove");	
				storage.setBonusHP(1, 0);
				break;
			case "Strong Bronze Chest":
				storage.inventoryArmor(storage.strongBronzeChest, "Add");
				storage.equippedArmor(storage.strongBronzeChest, "Remove");	
				storage.setBonusAP(1, 0);
				break;
			case "Defensive Bronze Chest":
				storage.inventoryArmor(storage.defensiveBronzeChest, "Add");
				storage.equippedArmor(storage.defensiveBronzeChest, "Remove");	
				storage.setBonusDP(1, 0);
				break;
			case "Healthy Steel Chest":
				storage.inventoryArmor(storage.healthySteelChest, "Add");
				storage.equippedArmor(storage.healthySteelChest, "Remove");	
				storage.setBonusHP(1, 0);
				break;
			case "Strong Steel Chest":
				storage.inventoryArmor(storage.strongSteelChest, "Add");
				storage.equippedArmor(storage.strongSteelChest, "Remove");	
				storage.setBonusAP(1, 0);
				break;
			case "Defensive Steel Chest":
				storage.inventoryArmor(storage.defensiveSteelChest, "Add");
				storage.equippedArmor(storage.defensiveSteelChest, "Remove");	
				storage.setBonusDP(1, 0);
				break;
			}
		}
		else if(gearSlot == "Boots") {
			bootsDP = 0;
			switch(gearPiece) {
			case "Healthy Iron Boots":
				storage.inventoryArmor(storage.healthyIronBoots, "Add");
				storage.equippedArmor(storage.healthyIronBoots, "Remove");	
				storage.setBonusHP(2, 0);
				break;
			case "Strong Iron Boots":
				storage.inventoryArmor(storage.strongIronBoots, "Add");
				storage.equippedArmor(storage.strongIronBoots, "Remove");	
				storage.setBonusAP(2, 0);
				break;
			case "Defensive Iron Boots":
				storage.inventoryArmor(storage.defensiveIronBoots, "Add");
				storage.equippedArmor(storage.defensiveIronBoots, "Remove");	
				storage.setBonusDP(2, 0);
				break;
			case "Healthy Bronze Boots":
				storage.inventoryArmor(storage.healthyBronzeBoots, "Add");
				storage.equippedArmor(storage.healthyBronzeBoots, "Remove");	
				storage.setBonusHP(2, 0);
				break;
			case "Strong Bronze Boots":
				storage.inventoryArmor(storage.strongBronzeBoots, "Add");
				storage.equippedArmor(storage.strongBronzeBoots, "Remove");	
				storage.setBonusAP(2, 0);
				break;
			case "Defensive Bronze Boots":
				storage.inventoryArmor(storage.defensiveBronzeBoots, "Add");
				storage.equippedArmor(storage.defensiveBronzeBoots, "Remove");	
				storage.setBonusDP(2, 0);
				break;
			case "Healthy Steel Boots":
				storage.inventoryArmor(storage.healthySteelBoots, "Add");
				storage.equippedArmor(storage.healthySteelBoots, "Remove");	
				storage.setBonusHP(2, 0);
				break;
			case "Strong Steel Boots":
				storage.inventoryArmor(storage.strongSteelBoots, "Add");
				storage.equippedArmor(storage.strongSteelBoots, "Remove");	
				storage.setBonusAP(2, 0);
				break;
			case "Defensive Steel Boots":
				storage.inventoryArmor(storage.defensiveSteelBoots, "Add");
				storage.equippedArmor(storage.defensiveSteelBoots, "Remove");	
				storage.setBonusDP(2, 0);
				break;
			}
		}
		else if(gearSlot == "Weapon") {
			weaponAP = 0;
			switch(gearPiece) {
			case "Wooden Greataxe":
				storage.inventoryWeapons(storage.woodenGA, "Add");
				storage.equippedWeapons(storage.woodenGA, "Remove");	
				break;
			case "Wooden Axe":
				storage.inventoryWeapons(storage.woodenAxe, "Add");
				storage.equippedWeapons(storage.woodenAxe, "Remove");	
				break;
			case "Healthy Iron Greataxe":
				storage.inventoryWeapons(storage.healthyIronGA, "Add");
				storage.equippedWeapons(storage.healthyIronGA, "Remove");	
				storage.setBonusHP(3, 0);
				break;
			case "Strong Iron Greataxe":
				storage.inventoryWeapons(storage.strongIronGA, "Add");
				storage.equippedWeapons(storage.strongIronGA, "Remove");
				storage.setBonusAP(3, 0);
				break;
			case "Defensive Iron Greataxe":
				storage.inventoryWeapons(storage.defensiveIronGA, "Add");
				storage.equippedWeapons(storage.defensiveIronGA, "Remove");
				storage.setBonusDP(3, 0);
				break;
			case "Healthy Iron Axe":
				storage.inventoryWeapons(storage.healthyIronAxe, "Add");
				storage.equippedWeapons(storage.healthyIronAxe, "Remove");
				break;
			case "Strong Iron Axe":
				storage.inventoryWeapons(storage.strongIronAxe, "Add");
				storage.equippedWeapons(storage.strongIronAxe, "Remove");
				break;
			case "Defensive Iron Axe":
				storage.inventoryWeapons(storage.defensiveIronAxe, "Add");
				storage.equippedWeapons(storage.defensiveIronAxe, "Remove");
				break;
			case "Healthy Bronze Greataxe":
				storage.inventoryWeapons(storage.healthyBronzeGA, "Add");
				storage.equippedWeapons(storage.healthyBronzeGA, "Remove");	
				storage.setBonusHP(3, 0);
				break;
			case "Strong Bronze Greataxe":
				storage.inventoryWeapons(storage.strongBronzeGA, "Add");
				storage.equippedWeapons(storage.strongBronzeGA, "Remove");
				storage.setBonusAP(3, 0);
				break;
			case "Defensive Bronze Greataxe":
				storage.inventoryWeapons(storage.defensiveBronzeGA, "Add");
				storage.equippedWeapons(storage.defensiveBronzeGA, "Remove");
				storage.setBonusDP(3, 0);
				break;
			case "Healthy Bronze Axe":
				storage.inventoryWeapons(storage.healthyBronzeAxe, "Add");
				storage.equippedWeapons(storage.healthyBronzeAxe, "Remove");
				break;
			case "Strong Bronze Axe":
				storage.inventoryWeapons(storage.strongBronzeAxe, "Add");
				storage.equippedWeapons(storage.strongBronzeAxe, "Remove");
				break;
			case "Defensive Bronze Axe":
				storage.inventoryWeapons(storage.defensiveBronzeAxe, "Add");
				storage.equippedWeapons(storage.defensiveBronzeAxe, "Remove");
				break;
			case "Healthy Steel Greataxe":
				storage.inventoryWeapons(storage.healthySteelGA, "Add");
				storage.equippedWeapons(storage.healthySteelGA, "Remove");	
				storage.setBonusHP(3, 0);
				break;
			case "Strong Steel Greataxe":
				storage.inventoryWeapons(storage.strongSteelGA, "Add");
				storage.equippedWeapons(storage.strongSteelGA, "Remove");
				storage.setBonusAP(3, 0);
				break;
			case "Defensive Steel Greataxe":
				storage.inventoryWeapons(storage.defensiveSteelGA, "Add");
				storage.equippedWeapons(storage.defensiveSteelGA, "Remove");
				storage.setBonusDP(3, 0);
				break;
			case "Healthy Steel Axe":
				storage.inventoryWeapons(storage.healthySteelAxe, "Add");
				storage.equippedWeapons(storage.healthySteelAxe, "Remove");
				break;
			case "Strong Steel Axe":
				storage.inventoryWeapons(storage.strongSteelAxe, "Add");
				storage.equippedWeapons(storage.strongSteelAxe, "Remove");
				break;
			case "Defensive Steel Axe":
				storage.inventoryWeapons(storage.defensiveSteelAxe, "Add");
				storage.equippedWeapons(storage.defensiveSteelAxe, "Remove");
				break;
			}		
			if(slot == 4) {
				shieldDP = 0;
				switch(gearPiece) {
				case "Wooden Shield":
					storage.inventoryWeapons(storage.woodenShield, "Add");
					storage.equippedWeapons(storage.woodenShield, "Remove");
					break;
				case "Healthy Iron Shield":
					storage.inventoryWeapons(storage.healthyIronShield, "Add");
					storage.equippedWeapons(storage.healthyIronShield, "Remove");
					storage.setBonusHP(4, 0);
					break;
				case "Strong Iron Shield":
					storage.inventoryWeapons(storage.strongIronShield, "Add");
					storage.equippedWeapons(storage.strongIronShield, "Remove");
					storage.setBonusAP(4, 0);
					break;
				case "Defensive Iron Shield":
					storage.inventoryWeapons(storage.defensiveIronShield, "Add");
					storage.equippedWeapons(storage.defensiveIronShield, "Remove");
					storage.setBonusDP(4, 0);
					break;
				case "Healthy Bronze Shield":
					storage.inventoryWeapons(storage.healthyBronzeShield, "Add");
					storage.equippedWeapons(storage.healthyBronzeShield, "Remove");
					storage.setBonusHP(4, 0);
					break;
				case "Strong Bronze Shield":
					storage.inventoryWeapons(storage.strongBronzeShield, "Add");
					storage.equippedWeapons(storage.strongBronzeShield, "Remove");
					storage.setBonusAP(4, 0);
					break;
				case "Defensive Bronze Shield":
					storage.inventoryWeapons(storage.defensiveBronzeShield, "Add");
					storage.equippedWeapons(storage.defensiveBronzeShield, "Remove");
					storage.setBonusDP(4, 0);
					break;
				case "Healthy Steel Shield":
					storage.inventoryWeapons(storage.healthySteelShield, "Add");
					storage.equippedWeapons(storage.healthySteelShield, "Remove");
					storage.setBonusHP(4, 0);
					break;
				case "Strong Steel Shield":
					storage.inventoryWeapons(storage.strongSteelShield, "Add");
					storage.equippedWeapons(storage.strongSteelShield, "Remove");
					storage.setBonusAP(4, 0);
					break;
				case "Defensive Steel Shield":
					storage.inventoryWeapons(storage.defensiveSteelShield, "Add");
					storage.equippedWeapons(storage.defensiveSteelShield, "Remove");
					storage.setBonusDP(4, 0);
					break;
				}			
			}	
		}
		else if(gearSlot == "OffHand") {
			shieldDP = 0;
			switch(gearPiece) {
			case "Wooden Shield":
				storage.inventoryWeapons(storage.woodenShield, "Add");
				storage.equippedWeapons(storage.woodenShield, "Remove");
				break;
			case "Healthy Iron Shield":
				storage.inventoryWeapons(storage.healthyIronShield, "Add");
				storage.equippedWeapons(storage.healthyIronShield, "Remove");
				storage.setBonusHP(4, 0);
				break;
			case "Strong Iron Shield":
				storage.inventoryWeapons(storage.strongIronShield, "Add");
				storage.equippedWeapons(storage.strongIronShield, "Remove");
				storage.setBonusAP(4, 0);
				break;
			case "Defensive Iron Shield":
				storage.inventoryWeapons(storage.defensiveIronShield, "Add");
				storage.equippedWeapons(storage.defensiveIronShield, "Remove");
				storage.setBonusDP(4, 0);
				break;
			case "Healthy Bronze Shield":
				storage.inventoryWeapons(storage.healthyBronzeShield, "Add");
				storage.equippedWeapons(storage.healthyBronzeShield, "Remove");
				storage.setBonusHP(4, 0);
				break;
			case "Strong Bronze Shield":
				storage.inventoryWeapons(storage.strongBronzeShield, "Add");
				storage.equippedWeapons(storage.strongBronzeShield, "Remove");
				storage.setBonusAP(4, 0);
				break;
			case "Defensive Bronze Shield":
				storage.inventoryWeapons(storage.defensiveBronzeShield, "Add");
				storage.equippedWeapons(storage.defensiveBronzeShield, "Remove");
				storage.setBonusDP(4, 0);
				break;
			case "Healthy Steel Shield":
				storage.inventoryWeapons(storage.healthySteelShield, "Add");
				storage.equippedWeapons(storage.healthySteelShield, "Remove");
				storage.setBonusHP(4, 0);
				break;
			case "Strong Steel Shield":
				storage.inventoryWeapons(storage.strongSteelShield, "Add");
				storage.equippedWeapons(storage.strongSteelShield, "Remove");
				storage.setBonusAP(4, 0);
				break;
			case "Defensive Steel Shield":
				storage.inventoryWeapons(storage.defensiveSteelShield, "Add");
				storage.equippedWeapons(storage.defensiveSteelShield, "Remove");
				storage.setBonusDP(4, 0);
				break;
			}
		}
	}

	private void setArmor(String armor, String gearSlot) {
		Actor helmet = characterTable.getChildren().get(0);
		Actor chest = characterTable.getChildren().get(1);
		Actor boots = characterTable.getChildren().get(2);
		
		if(gearSlot == "Helmet") {
			if(helmet.getName() != "Empty")
		        changeEquippedSlot(0, "Helmet");
			
			switch(armor) {
			case "Healthy Iron Helmet":
				storage.inventoryArmor(storage.healthyIronHelmet, "Remove");
				storage.equippedArmor(storage.healthyIronHelmet, "Add");	
				setArmorBonus("Healthy", 0, storage.healthyIronHelmet);
				break;
			case "Strong Iron Helmet":
				storage.inventoryArmor(storage.strongIronHelmet, "Remove");
				storage.equippedArmor(storage.strongIronHelmet, "Add");	
				setArmorBonus("Strong", 0, storage.strongIronHelmet);
				break;
			case "Defensive Iron Helmet":
				storage.inventoryArmor(storage.defensiveIronHelmet, "Remove");
				storage.equippedArmor(storage.defensiveIronHelmet, "Add");	
				setArmorBonus("Defensive", 0, storage.defensiveIronHelmet);
				break;
			case "Healthy Bronze Helmet":
				storage.inventoryArmor(storage.healthyBronzeHelmet, "Remove");
				storage.equippedArmor(storage.healthyBronzeHelmet, "Add");	
				setArmorBonus("Healthy", 0, storage.healthyBronzeHelmet);
				break;
			case "Strong Bronze Helmet":
				storage.inventoryArmor(storage.strongBronzeHelmet, "Remove");
				storage.equippedArmor(storage.strongBronzeHelmet, "Add");	
				setArmorBonus("Strong", 0, storage.strongBronzeHelmet);
				break;
			case "Defensive Bronze Helmet":
				storage.inventoryArmor(storage.defensiveBronzeHelmet, "Remove");
				storage.equippedArmor(storage.defensiveBronzeHelmet, "Add");	
				setArmorBonus("Defensive", 0, storage.defensiveBronzeHelmet);
				break;
			case "Healthy Steel Helmet":
				storage.inventoryArmor(storage.healthySteelHelmet, "Remove");
				storage.equippedArmor(storage.healthySteelHelmet, "Add");	
				setArmorBonus("Healthy", 0, storage.healthySteelHelmet);
				break;
			case "Strong Steel Helmet":
				storage.inventoryArmor(storage.strongSteelHelmet, "Remove");
				storage.equippedArmor(storage.strongSteelHelmet, "Add");	
				setArmorBonus("Strong", 0, storage.strongSteelHelmet);
				break;
			case "Defensive Steel Helmet":
				storage.inventoryArmor(storage.defensiveSteelHelmet, "Remove");
				storage.equippedArmor(storage.defensiveSteelHelmet, "Add");	
				setArmorBonus("Defensive", 0, storage.defensiveSteelHelmet);
				break;
			}
		}
		
		if(gearSlot == "Chest") {
			if(chest.getName() != "Empty")
		        changeEquippedSlot(1, "Chest");
			
			switch(armor) {
			case "Healthy Iron Chest":
				storage.inventoryArmor(storage.healthyIronChest, "Remove");
				storage.equippedArmor(storage.healthyIronChest, "Add");	
				setArmorBonus("Healthy", 1, storage.healthyIronChest);
				break;
			case "Strong Iron Chest":
				storage.inventoryArmor(storage.strongIronChest, "Remove");
				storage.equippedArmor(storage.strongIronChest, "Add");	
				setArmorBonus("Strong", 1, storage.strongIronChest);
				break;
			case "Defensive Iron Chest":
				storage.inventoryArmor(storage.defensiveIronChest, "Remove");
				storage.equippedArmor(storage.defensiveIronChest, "Add");	
				setArmorBonus("Defensive", 1, storage.defensiveIronChest);
				break;
			case "Healthy Bronze Chest":
				storage.inventoryArmor(storage.healthyBronzeChest, "Remove");
				storage.equippedArmor(storage.healthyBronzeChest, "Add");	
				setArmorBonus("Healthy", 1, storage.healthyBronzeChest);
				break;
			case "Strong Bronze Chest":
				storage.inventoryArmor(storage.strongBronzeChest, "Remove");
				storage.equippedArmor(storage.strongBronzeChest, "Add");	
				setArmorBonus("Strong", 1, storage.strongBronzeChest);
				break;
			case "Defensive Bronze Chest":
				storage.inventoryArmor(storage.defensiveBronzeChest, "Remove");
				storage.equippedArmor(storage.defensiveBronzeChest, "Add");	
				setArmorBonus("Defensive", 1, storage.defensiveBronzeChest);
				break;
			case "Healthy Steel Chest":
				storage.inventoryArmor(storage.healthySteelChest, "Remove");
				storage.equippedArmor(storage.healthySteelChest, "Add");	
				setArmorBonus("Healthy", 1, storage.healthySteelChest);
				break;
			case "Strong Steel Chest":
				storage.inventoryArmor(storage.strongSteelChest, "Remove");
				storage.equippedArmor(storage.strongSteelChest, "Add");	
				setArmorBonus("Strong", 1, storage.strongSteelChest);
				break;
			case "Defensive Steel Chest":
				storage.inventoryArmor(storage.defensiveSteelChest, "Remove");
				storage.equippedArmor(storage.defensiveSteelChest, "Add");	
				setArmorBonus("Defensive", 1, storage.defensiveSteelChest);
				break;
			}			
		}
		
		if(gearSlot == "Boots") {
			if(boots.getName() != "Empty")
		        changeEquippedSlot(2, "Boots");
			
			switch(armor) {
			case "Healthy Iron Boots":
				storage.inventoryArmor(storage.healthyIronBoots, "Remove");
				storage.equippedArmor(storage.healthyIronBoots, "Add");	
				setArmorBonus("Healthy", 2, storage.healthyIronBoots);
				break;
			case "Strong Iron Boots":
				storage.inventoryArmor(storage.strongIronBoots, "Remove");
				storage.equippedArmor(storage.strongIronBoots, "Add");	
				setArmorBonus("Strong", 2, storage.strongIronBoots);
				break;
			case "Defensive Iron Boots":
				storage.inventoryArmor(storage.defensiveIronBoots, "Remove");
				storage.equippedArmor(storage.defensiveIronBoots, "Add");	
				setArmorBonus("Defensive", 2, storage.defensiveIronBoots);
				break;
			case "Healthy Bronze Boots":
				storage.inventoryArmor(storage.healthyBronzeBoots, "Remove");
				storage.equippedArmor(storage.healthyBronzeBoots, "Add");	
				setArmorBonus("Healthy", 2, storage.healthyBronzeBoots);
				break;
			case "Strong Bronze Boots":
				storage.inventoryArmor(storage.strongBronzeBoots, "Remove");
				storage.equippedArmor(storage.strongBronzeBoots, "Add");	
				setArmorBonus("Strong", 2, storage.strongBronzeBoots);
				break;
			case "Defensive Bronze Boots":
				storage.inventoryArmor(storage.defensiveBronzeBoots, "Remove");
				storage.equippedArmor(storage.defensiveBronzeBoots, "Add");	
				setArmorBonus("Defensive", 2, storage.defensiveBronzeBoots);
				break;
			case "Healthy Steel Boots":
				storage.inventoryArmor(storage.healthySteelBoots, "Remove");
				storage.equippedArmor(storage.healthySteelBoots, "Add");	
				setArmorBonus("Healthy", 2, storage.healthySteelBoots);
				break;
			case "Strong Steel Boots":
				storage.inventoryArmor(storage.strongSteelBoots, "Remove");
				storage.equippedArmor(storage.strongSteelBoots, "Add");	
				setArmorBonus("Strong", 2, storage.strongSteelBoots);
				break;
			case "Defensive Steel Boots":
				storage.inventoryArmor(storage.defensiveSteelBoots, "Remove");
				storage.equippedArmor(storage.defensiveSteelBoots, "Add");	
				setArmorBonus("Defensive", 2, storage.defensiveSteelBoots);
				break;
			}
		}
	}
	
	private void setWeapon(String weapon, String handed) {
		Actor mainHand = characterTable.getChildren().get(3);
		Actor offHand = characterTable.getChildren().get(4);

		if(handed == "TwoHanded") {				
			if (mainHand.getName() != "Empty")
		        changeEquippedSlot(3, "Weapon");
		    if (offHand.getName() != "Empty")
		        changeEquippedSlot(4, "Weapon");
			
			switch(weapon) {
			case "Wooden Greataxe":
				storage.inventoryWeapons(storage.woodenGA, "Remove");
				storage.equippedWeapons(storage.woodenGA, "Add");				
				break;
			case "Healthy Iron Greataxe":
				storage.inventoryWeapons(storage.healthyIronGA, "Remove");
				storage.equippedWeapons(storage.healthyIronGA, "Add");				
				setWeaponBonus("Healthy", 3, storage.healthyIronGA);
				break;
			case "Strong Iron Greataxe":
				storage.inventoryWeapons(storage.strongIronGA, "Remove");
				storage.equippedWeapons(storage.strongIronGA, "Add");	
				setWeaponBonus("Strong", 3, storage.strongIronGA);
				break;
			case "Defensive Iron Greataxe":
				storage.inventoryWeapons(storage.defensiveIronGA, "Remove");
				storage.equippedWeapons(storage.defensiveIronGA, "Add");
				setWeaponBonus("Defensive", 3, storage.defensiveIronGA);
				break;
			case "Healthy Bronze Greataxe":
				storage.inventoryWeapons(storage.healthyBronzeGA, "Remove");
				storage.equippedWeapons(storage.healthyBronzeGA, "Add");				
				setWeaponBonus("Healthy", 3, storage.healthyBronzeGA);
				break;
			case "Strong Bronze Greataxe":
				storage.inventoryWeapons(storage.strongBronzeGA, "Remove");
				storage.equippedWeapons(storage.strongBronzeGA, "Add");	
				setWeaponBonus("Strong", 3, storage.strongBronzeGA);
				break;
			case "Defensive Bronze Greataxe":
				storage.inventoryWeapons(storage.defensiveBronzeGA, "Remove");
				storage.equippedWeapons(storage.defensiveBronzeGA, "Add");
				setWeaponBonus("Defensive", 3, storage.defensiveBronzeGA);
				break;
			case "Healthy Steel Greataxe":
				storage.inventoryWeapons(storage.healthySteelGA, "Remove");
				storage.equippedWeapons(storage.healthySteelGA, "Add");				
				setWeaponBonus("Healthy", 3, storage.healthySteelGA);
				break;
			case "Strong Steel Greataxe":
				storage.inventoryWeapons(storage.strongSteelGA, "Remove");
				storage.equippedWeapons(storage.strongSteelGA, "Add");	
				setWeaponBonus("Strong", 3, storage.strongSteelGA);
				break;
			case "Defensive Steel Greataxe":
				storage.inventoryWeapons(storage.defensiveSteelGA, "Remove");
				storage.equippedWeapons(storage.defensiveSteelGA, "Add");
				setWeaponBonus("Defensive", 3, storage.defensiveSteelGA);
				break;
			}			
		}		
		else if(handed == "OneHanded") {
			if(mainHand.getName() != "Empty")
				changeEquippedSlot(3, "Weapon");

			switch(weapon) {
			case "Wooden Axe":
				storage.inventoryWeapons(storage.woodenAxe, "Remove");
				storage.equippedWeapons(storage.woodenAxe, "Add");				
				break;
			case "Healthy Iron Axe":
				storage.inventoryWeapons(storage.healthyIronAxe, "Remove");
				storage.equippedWeapons(storage.healthyIronAxe, "Add");
				setWeaponBonus("Healthy", 3, storage.healthyIronAxe);
				break;
			case "Strong Iron Axe":
				storage.inventoryWeapons(storage.strongIronAxe, "Remove");
				storage.equippedWeapons(storage.strongIronAxe, "Add");
				setWeaponBonus("Strong", 3, storage.strongIronAxe);
				break;
			case "Defensive Iron Axe":
				storage.inventoryWeapons(storage.defensiveIronAxe, "Remove");
				storage.equippedWeapons(storage.defensiveIronAxe, "Add");
				setWeaponBonus("Defensive", 3, storage.defensiveIronAxe);
				break;
			case "Healthy Bronze Axe":
				storage.inventoryWeapons(storage.healthyBronzeAxe, "Remove");
				storage.equippedWeapons(storage.healthyBronzeAxe, "Add");
				setWeaponBonus("Healthy", 3, storage.healthyBronzeAxe);
				break;
			case "Strong Bronze Axe":
				storage.inventoryWeapons(storage.strongBronzeAxe, "Remove");
				storage.equippedWeapons(storage.strongBronzeAxe, "Add");
				setWeaponBonus("Strong", 3, storage.strongBronzeAxe);
				break;
			case "Defensive Bronze Axe":
				storage.inventoryWeapons(storage.defensiveBronzeAxe, "Remove");
				storage.equippedWeapons(storage.defensiveBronzeAxe, "Add");
				setWeaponBonus("Defensive", 3, storage.defensiveBronzeAxe);
				break;
			case "Healthy Steel Axe":
				storage.inventoryWeapons(storage.healthySteelAxe, "Remove");
				storage.equippedWeapons(storage.healthySteelAxe, "Add");
				setWeaponBonus("Healthy", 3, storage.healthySteelAxe);
				break;
			case "Strong Steel Axe":
				storage.inventoryWeapons(storage.strongSteelAxe, "Remove");
				storage.equippedWeapons(storage.strongSteelAxe, "Add");
				setWeaponBonus("Strong", 3, storage.strongSteelAxe);
				break;
			case "Defensive Steel Axe":
				storage.inventoryWeapons(storage.defensiveSteelAxe, "Remove");
				storage.equippedWeapons(storage.defensiveSteelAxe, "Add");
				setWeaponBonus("Defensive", 3, storage.defensiveSteelAxe);
				break;
			}
		}
		else if(handed == "OffHand") {
			if(mainHand.getName() != "Empty" && mainHand.getName().endsWith("Greataxe"))
				changeEquippedSlot(3, "Weapon");
			if (offHand.getName() != "Empty")
		        changeEquippedSlot(4, "Weapon");

			switch(weapon) {
			case "Wooden Shield":
				storage.inventoryWeapons(storage.woodenShield, "Remove");
				storage.equippedWeapons(storage.woodenShield, "Add");
				break;
			case "Healthy Iron Shield":
				storage.inventoryWeapons(storage.healthyIronShield, "Remove");
				storage.equippedWeapons(storage.healthyIronShield, "Add");
				setWeaponBonus("Healthy", 4, storage.healthyIronShield);
				break;
			case "Strong Iron Shield":
				storage.inventoryWeapons(storage.strongIronShield, "Remove");
				storage.equippedWeapons(storage.strongIronShield, "Add");
				setWeaponBonus("Strong", 4, storage.strongIronShield);
				break;
			case "Defensive Iron Shield":
				storage.inventoryWeapons(storage.defensiveIronShield, "Remove");
				storage.equippedWeapons(storage.defensiveIronShield, "Add");
				setWeaponBonus("Defensive", 4, storage.defensiveIronShield);
				break;
			case "Healthy Bronze Shield":
				storage.inventoryWeapons(storage.healthyBronzeShield, "Remove");
				storage.equippedWeapons(storage.healthyBronzeShield, "Add");
				setWeaponBonus("Healthy", 4, storage.healthyBronzeShield);
				break;
			case "Strong Bronze Shield":
				storage.inventoryWeapons(storage.strongBronzeShield, "Remove");
				storage.equippedWeapons(storage.strongBronzeShield, "Add");
				setWeaponBonus("Strong", 4, storage.strongBronzeShield);
				break;
			case "Defensive Bronze Shield":
				storage.inventoryWeapons(storage.defensiveBronzeShield, "Remove");
				storage.equippedWeapons(storage.defensiveBronzeShield, "Add");
				setWeaponBonus("Defensive", 4, storage.defensiveBronzeShield);
				break;
			case "Healthy Steel Shield":
				storage.inventoryWeapons(storage.healthySteelShield, "Remove");
				storage.equippedWeapons(storage.healthySteelShield, "Add");
				setWeaponBonus("Healthy", 4, storage.healthySteelShield);
				break;
			case "Strong Steel Shield":
				storage.inventoryWeapons(storage.strongSteelShield, "Remove");
				storage.equippedWeapons(storage.strongSteelShield, "Add");
				setWeaponBonus("Strong", 4, storage.strongSteelShield);
				break;
			case "Defensive Steel Shield":
				storage.inventoryWeapons(storage.defensiveSteelShield, "Remove");
				storage.equippedWeapons(storage.defensiveSteelShield, "Add");
				setWeaponBonus("Defensive", 4, storage.defensiveSteelShield);
				break;
			}
		}
	}	

	private void handleCharacterSlotClick(Image slot, String itemName) {
		if(slot.getName() == "Empty")
	    	System.out.println("Empty character slot clicked!");
		else if(inventoryWeapons.size() + inventoryArmor.size() + inventoryItems.size() < 40) {
			
			if(slot.getName().endsWith("Helmet")) {
				changeEquippedSlot(0, "Helmet");
				slot.setName("Empty");
				storage.setBonusAP(0, 0);
				storage.setBonusHP(0, 0);
				storage.setBonusDP(0, 0);
				helmetDP = 0;
			}
			else if(slot.getName().endsWith("Chest")) {
				changeEquippedSlot(1, "Chest");
				slot.setName("Empty");
				storage.setBonusAP(1, 0);
				storage.setBonusHP(1, 0);
				storage.setBonusDP(1, 0);
				chestDP = 0;
			}
			else if(slot.getName().endsWith("Boots")) {
				changeEquippedSlot(2, "Boots");
				slot.setName("Empty");
				storage.setBonusAP(2, 0);
				storage.setBonusHP(2, 0);
				storage.setBonusDP(2, 0);
				bootsDP = 0;
			}
			else if(slot.getName().endsWith("Greataxe")) {
				changeEquippedSlot(3, "Weapon");
				slot.setName("Empty");
				weaponAP = 0;
				storage.setBonusAP(3, 0);
				storage.setBonusHP(3, 0);
				storage.setBonusDP(3, 0);
				Player.weaponState = 0;
			}
			else if(slot.getName().endsWith("Axe")) {
				changeEquippedSlot(3, "Weapon");
				slot.setName("Empty");
				storage.setBonusAP(3, 0);
				storage.setBonusHP(3, 0);
				storage.setBonusDP(3, 0);
				weaponAP = 0;
				Player.weaponState = 0;
			}
			else if(slot.getName().endsWith("Shield")) {
				changeEquippedSlot(4, "OffHand");
				slot.setName("Empty");
				storage.setBonusAP(4, 0);
				storage.setBonusHP(4, 0);
				storage.setBonusDP(4, 0);
				shieldDP = 0;
			}
		}		
		
		inventoryTable.clear();
        createInventoryGrid();
        characterTable.clear();
        createCharacterGrid();       
	}

	private void handleInventoryClick(Image slot, String itemName) {	
		boolean itemMoved = false; 
		
	    if(itemName.endsWith("Helmet")) {
	    	setArmor(itemName, "Helmet");
	    	addArmorDefenses(itemName);
	    }
	    else if(itemName.endsWith("Chest")) {
	    	setArmor(itemName, "Chest");
	    	addArmorDefenses(itemName);
	    }
	    else if(itemName.endsWith("Boots")) {
	    	setArmor(itemName, "Boots");
	    	addArmorDefenses(itemName);
	    }
	    else if(itemName.endsWith("xe") || itemName.endsWith("Shield")){
	    	setWeapon(itemName, checkGearSlot(itemName));
	    	addWeaponDamage(itemName);	    		    	      
	    }
	    else if(equippedItems.size() < 14){
	    	itemMoved = true;
	    	switch(itemName) {
	    	case "Health Potion":
	    		storage.inventoryItems(storage.healthPot, "Remove");
	    		storage.equippedItems(storage.healthPot, "Add");
	    		break;
	    	case "Bomb":
	    		storage.inventoryItems(storage.bomb, "Remove");
	    		storage.equippedItems(storage.bomb, "Add");
	    		break;
	    	case "Throwing Knife":
	    		storage.inventoryItems(storage.throwingKnife, "Remove");
	    		storage.equippedItems(storage.throwingKnife, "Add");
	    		break;
	    	case "Attack Boost":
	    		storage.inventoryItems(storage.apBoost, "Remove");
	    		storage.equippedItems(storage.apBoost, "Add");
	    		break;
	    	case "Defense Boost":
	    		storage.inventoryItems(storage.dpBoost, "Remove");
	    		storage.equippedItems(storage.dpBoost, "Add");
	    		break;
	    	case "Health Boost":
	    		storage.inventoryItems(storage.hpBoost, "Remove");
	    		storage.equippedItems(storage.hpBoost, "Add");
	    		break;
	    	case "Experience Boost":
	    		storage.inventoryItems(storage.expBoost, "Remove");
	    		storage.equippedItems(storage.expBoost, "Add");
	    		break;
	    	case "Ability Refill Potion":
	    		storage.inventoryItems(storage.abilityRefill, "Remove");
	    		storage.equippedItems(storage.abilityRefill, "Add");
	    		break;
	    	case "Swing":
	    		storage.inventoryItems(storage.itemSwing, "Remove");
	    		storage.equippedItems(storage.itemSwing, "Add");
	    		break;
	    	case "Rend":
	    		storage.inventoryItems(storage.itemRend, "Remove");
	    		storage.equippedItems(storage.itemRend, "Add");
	    		break;
	    	case "Whirlwind":
	    		storage.inventoryItems(storage.itemWhirlwind, "Remove");
	    		storage.equippedItems(storage.itemWhirlwind, "Add");
	    		break;
	    	case "Ground Breaker":
	    		storage.inventoryItems(storage.itemGroundBreaker, "Remove");
	    		storage.equippedItems(storage.itemGroundBreaker, "Add");
	    		break;
	    	case "Bash":
	    		storage.inventoryItems(storage.itemBash, "Remove");
	    		storage.equippedItems(storage.itemBash, "Add");
	    		break;
	    	case "Barrier":
	    		storage.inventoryItems(storage.itemBarrier, "Remove");
	    		storage.equippedItems(storage.itemBarrier, "Add");
	    		break;
	    	case "Harden":
	    		storage.inventoryItems(storage.itemHarden, "Remove");
	    		storage.equippedItems(storage.itemHarden, "Add");
	    		break;
	    	case "Mend":
	    		storage.inventoryItems(storage.itemMend, "Remove");
	    		storage.equippedItems(storage.itemMend, "Add");
	    		break;
	    	case "Hilt Bash":
	    		storage.inventoryItems(storage.itemHiltBash, "Remove");
	    		storage.equippedItems(storage.itemHiltBash, "Add");
	    		break;
	    	case "Barbed Armor":
	    		storage.inventoryItems(storage.itemBarbedArmor, "Remove");
	    		storage.equippedItems(storage.itemBarbedArmor, "Add");
	    		break;
	    	case "Enrage":
	    		storage.inventoryItems(storage.itemEnrage, "Remove");
	    		storage.equippedItems(storage.itemEnrage, "Add");
	    		break;
	    	case "Riposte":
	    		storage.inventoryItems(storage.itemRiposte, "Remove");
	    		storage.equippedItems(storage.itemRiposte, "Add");
	    		break;
	    	case "Stab":
	    		storage.inventoryItems(storage.itemStab, "Remove");
	    		storage.equippedItems(storage.itemStab, "Add");
	    		break;
	    	case "Decapitate":
	    		storage.inventoryItems(storage.itemDecapitate, "Remove");
	    		storage.equippedItems(storage.itemDecapitate, "Add");
	    		break;
	    	}
	    }
	    
	    inventoryTable.clear();
        createInventoryGrid();
        characterTable.clear();
        createCharacterGrid();
        if(itemMoved) {
        	itemTable.clear();
            createItemGrid();
        }        
	}
	
	private void handleEquippedItemsClick(Image slot, String itemName) {
		boolean itemMoved = false;
		
		if(slot.getName() == "Empty")
	    	System.out.println("Empty item slot clicked!");
		else if(inventoryWeapons.size() + inventoryArmor.size() + inventoryItems.size() < 40){
			itemMoved = true;		
			switch(itemName) {
	    	case "Health Potion":
	    		storage.inventoryItems(storage.healthPot, "Add");
	    		storage.equippedItems(storage.healthPot, "Remove");
	    		break;
	    	case "Bomb":
	    		storage.inventoryItems(storage.bomb, "Add");
	    		storage.equippedItems(storage.bomb, "Remove");
	    		break;
	    	case "Throwing Knife":
	    		storage.inventoryItems(storage.throwingKnife, "Add");
	    		storage.equippedItems(storage.throwingKnife, "Remove");
	    		break;
	    	case "Attack Boost":
	    		storage.inventoryItems(storage.apBoost, "Add");
	    		storage.equippedItems(storage.apBoost, "Remove");
	    		break;
	    	case "Defense Boost":
	    		storage.inventoryItems(storage.dpBoost, "Add");
	    		storage.equippedItems(storage.dpBoost, "Remove");
	    		break;
	    	case "Health Boost":
	    		storage.inventoryItems(storage.hpBoost, "Add");
	    		storage.equippedItems(storage.hpBoost, "Remove");
	    		break;
	    	case "Experience Boost":
	    		storage.inventoryItems(storage.expBoost, "Add");
	    		storage.equippedItems(storage.expBoost, "Remove");
	    		break;
	    	case "Ability Refill Potion":
	    		storage.inventoryItems(storage.abilityRefill, "Add");
	    		storage.equippedItems(storage.abilityRefill, "Remove");
	    		break;
	    	case "Swing":
	    		storage.inventoryItems(storage.itemSwing, "Add");
	    		storage.equippedItems(storage.itemSwing, "Remove");
	    		break;
	    	case "Rend":
	    		storage.inventoryItems(storage.itemRend, "Add");
	    		storage.equippedItems(storage.itemRend, "Remove");
	    		break;
	    	case "Whirlwind":
	    		storage.inventoryItems(storage.itemWhirlwind, "Add");
	    		storage.equippedItems(storage.itemWhirlwind, "Remove");
	    		break;
	    	case "Ground Breaker":
	    		storage.inventoryItems(storage.itemGroundBreaker, "Add");
	    		storage.equippedItems(storage.itemGroundBreaker, "Remove");
	    		break;
	    	case "Bash":
	    		storage.inventoryItems(storage.itemBash, "Add");
	    		storage.equippedItems(storage.itemBash, "Remove");
	    		break;
	    	case "Barrier":
	    		storage.inventoryItems(storage.itemBarrier, "Add");
	    		storage.equippedItems(storage.itemBarrier, "Remove");
	    		break;
	    	case "Harden":
	    		storage.inventoryItems(storage.itemHarden, "Add");
	    		storage.equippedItems(storage.itemHarden, "Remove");
	    		break;
	    	case "Mend":
	    		storage.inventoryItems(storage.itemMend, "Add");
	    		storage.equippedItems(storage.itemMend, "Remove");
	    		break;
	    	case "Hilt Bash":
	    		storage.inventoryItems(storage.itemHiltBash, "Add");
	    		storage.equippedItems(storage.itemHiltBash, "Remove");
	    		break;
	    	case "Barbed Armor":
	    		storage.inventoryItems(storage.itemBarbedArmor, "Add");
	    		storage.equippedItems(storage.itemBarbedArmor, "Remove");
	    		break;
	    	case "Enrage":
	    		storage.inventoryItems(storage.itemEnrage, "Add");
	    		storage.equippedItems(storage.itemEnrage, "Remove");
	    		break;
	    	case "Riposte":
	    		storage.inventoryItems(storage.itemRiposte, "Add");
	    		storage.equippedItems(storage.itemRiposte, "Remove");
	    		break;
	    	case "Stab":
	    		storage.inventoryItems(storage.itemStab, "Add");
	    		storage.equippedItems(storage.itemStab, "Remove");
	    		break;
	    	case "Decapitate":
	    		storage.inventoryItems(storage.itemDecapitate, "Add");
	    		storage.equippedItems(storage.itemDecapitate, "Remove");
	    		break;
	    	}
		}
		
		if(itemMoved) {
			inventoryTable.clear();
	        createInventoryGrid();
	        itemTable.clear();
	        createItemGrid();
		}	
	}
	
	private void addArmorDefenses(String armor) {
		String gearSlot = "";
		String[] words = armor.split(" ");
		gearSlot = words[words.length - 2] + " " + words[words.length - 1];
		
		switch(gearSlot) {
		case "Iron Helmet":
			helmetDP = storage.healthyIronHelmet.getDefense();
			break;
		case "Iron Chest":
			chestDP = storage.healthyIronChest.getDefense();
			break;
		case "Iron Boots":
			bootsDP = storage.healthyIronBoots.getDefense();
			break;
		case "Bronze Helmet":
			helmetDP = storage.healthyBronzeHelmet.getDefense();
			break;
		case "Bronze Chest":
			chestDP = storage.healthyBronzeChest.getDefense();
			break;
		case "Bronze Boots":
			bootsDP = storage.healthyBronzeBoots.getDefense();
			break;
		case "Steel Helmet":
			helmetDP = storage.healthySteelHelmet.getDefense();
			break;
		case "Steel Chest":
			chestDP = storage.healthySteelChest.getDefense();
			break;
		case "Steel Boots":
			bootsDP = storage.healthySteelBoots.getDefense();
			break;
		}
	}
	
	private void addWeaponDamage(String weapon) {
		String gearSlot = "";
		String[] words = weapon.split(" ");
		gearSlot = words[words.length - 2] + " " + words[words.length - 1];
		
		switch(gearSlot) {
		case "Iron Greataxe":
			weaponAP = storage.healthyIronGA.getWeaponDmg();
			Player.weaponState = 2;
			break;
		case "Wooden Greataxe":
			weaponAP = storage.woodenGA.getWeaponDmg();
			Player.weaponState = 2;
			break;
		case "Iron Axe":
			weaponAP = storage.healthyIronAxe.getWeaponDmg();
			Player.weaponState = 1;
			break;
		case "Wooden Axe":
			weaponAP = storage.woodenAxe.getWeaponDmg();
			Player.weaponState = 1;
			break;
		case "Wooden Shield":
			shieldDP = storage.woodenShield.getWeaponDmg();
			break;
		case "Iron Shield":
			shieldDP = storage.healthyIronShield.getWeaponDmg();
			break;
		case "Bronze Greataxe":
			weaponAP = storage.healthyBronzeGA.getWeaponDmg();
			Player.weaponState = 2;
			break;
		case "Bronze Axe":
			weaponAP = storage.healthyBronzeAxe.getWeaponDmg();
			Player.weaponState = 1;
			break;
		case "Bronze Shield":
			shieldDP = storage.healthyBronzeShield.getWeaponDmg();
			break;
		case "Steel Greataxe":
			weaponAP = storage.healthySteelGA.getWeaponDmg();
			Player.weaponState = 2;
			break;
		case "Steel Axe":
			weaponAP = storage.healthySteelAxe.getWeaponDmg();
			Player.weaponState = 1;
			break;
		case "Steel Shield":
			shieldDP = storage.healthySteelShield.getWeaponDmg();
			break;
		}
	}

	private void createComponents() {
		backBtn = new TextButton("Back", storage.buttonStyle);
		backBtn.setColor(Color.LIGHT_GRAY);
		backBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {  
    			bonusAP = bonusHP = bonusDP = 0;
    			for(int ap : storage.getBonusAP())
    				bonusAP += ap;
    			for(int hp : storage.getBonusHP())
    				bonusHP += hp;
    			for(int dp : storage.getBonusDP())
    				bonusDP += dp;
    			
    			Player.setDmgResist(helmetDP + chestDP + bootsDP + shieldDP + bonusDP);
    			Player.gainMaxHP(bonusHP);
    			Player.setWeaponDmg(weaponAP);
    			Player.gainBonusStr(bonusAP);
    			gameScreen.switchToNewState(GameScreen.HOME);
    	    }});
		backBtn.setSize(150, 100);
		backBtn.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.25f);
		stage.addActor(backBtn);	
		
		gearName = new Label("", storage.labelStyle);
		gearName.setVisible(false);
		gearName.setSize(300, 450);
		gearName.setWrap(true);
		stage.addActor(gearName);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		mapBatch.setProjectionMatrix(vp.getCamera().combined);
		abilityBatch.setProjectionMatrix(vp.getCamera().combined);
		
		mapBatch.begin();
		mapBatch.draw(mapTexture, 0, 0, GameScreen.SELECTED_WIDTH, GameScreen.SELECTED_HEIGHT);
		mapBatch.end();
		
		Actor helmetTable = characterTable.getChildren().get(0);
		Actor chestTable = characterTable.getChildren().get(1);
		Actor bootsTable = characterTable.getChildren().get(2);
		Actor weaponTable = characterTable.getChildren().get(3);
		Actor shieldTable = characterTable.getChildren().get(4);
		
		String helmetPiece = helmetTable.getName();
		String chestPiece = chestTable.getName();
		String bootsPiece = bootsTable.getName();
		String weaponPiece = weaponTable.getName();
		String shieldPiece = shieldTable.getName();
		String helmet = null;
		String chest = null;
		String boots = null;
		String weapon = null;
		String shield = null;
		Texture helmetTexture = null;
		Texture chestTexture = null;
		Texture bootsTexture = null;
		Texture weaponTexture = null;
		Texture shieldTexture = null;
		Sprite weaponSprite, shieldSprite, helmetSprite, chestSprite, bootsSprite;		
		
		onionBatch.setProjectionMatrix(vp.getCamera().combined);
		weaponBatch.setProjectionMatrix(vp.getCamera().combined);
		shieldBatch.setProjectionMatrix(vp.getCamera().combined);
		helmetBatch.setProjectionMatrix(vp.getCamera().combined);
		chestBatch.setProjectionMatrix(vp.getCamera().combined);
		bootsBatch.setProjectionMatrix(vp.getCamera().combined);
		
		if(!weaponPiece.equals("Empty")) {
			String[] words = weaponPiece.split(" ");
			weapon = words[words.length - 2] + " " + words[words.length - 1];				
		}
		if(!shieldPiece.equals("Empty")) {
			String[] words = shieldPiece.split(" ");
			shield = words[words.length - 2] + " " + words[words.length - 1];				
		}
		if(!helmetPiece.equals("Empty")) {
			String[] words = helmetPiece.split(" ");
			helmet = words[words.length - 2] + " " + words[words.length - 1];				
		}
		if(!chestPiece.equals("Empty")) {
			String[] words = chestPiece.split(" ");
			chest = words[words.length - 2] + " " + words[words.length - 1];				
		}
		if(!bootsPiece.equals("Empty")) {
			String[] words = bootsPiece.split(" ");
			boots = words[words.length - 2] + " " + words[words.length - 1];				
		}
		
		onionBatch.begin();
		onionBatch.draw(TextureManager.onionTexture, vp.getWorldWidth() / 3.5f, vp.getWorldHeight() / 3f, 310, 500);
		onionBatch.end();
		
		if(!helmetPiece.equals("Empty")) {
			switch(helmet) {
			case "Iron Helmet":
				helmetTexture = TextureManager.eIronHelmetTexture;
				break;
			case "Bronze Helmet":
				helmetTexture = TextureManager.eBronzeHelmetTexture;
				break;
			case "Steel Helmet":
				helmetTexture = TextureManager.eSteelHelmetTexture;
				break;
			}
			
			helmetSprite = new Sprite(helmetTexture);
			helmetSprite.setPosition(vp.getWorldWidth() / 3.65f, vp.getWorldHeight() / 1.65f);				
			helmetSprite.setSize(320, 330);
			
			helmetBatch.begin();
			helmetSprite.draw(helmetBatch);		
			helmetBatch.end();
		}
		
		if(!chestPiece.equals("Empty")) {
			switch(chest) {
			case "Iron Chest":
				chestTexture = TextureManager.eIronChestTexture;
				break;
			case "Bronze Chest":
				chestTexture = TextureManager.eBronzeChestTexture;
				break;
			case "Steel Chest":
				chestTexture = TextureManager.eSteelChestTexture;
				break;
			}
			
			chestSprite = new Sprite(chestTexture);
			chestSprite.setPosition(vp.getWorldWidth() / 3.5f, vp.getWorldHeight() / 2.55f);				
			chestSprite.setSize(309, 140);
			
			chestBatch.begin();
			chestSprite.draw(chestBatch);		
			chestBatch.end();
		}
		
		if(!bootsPiece.equals("Empty")) {
			switch(boots) {
			case "Iron Boots":
				bootsTexture = TextureManager.eIronBootsTexture;
				break;
			case "Bronze Boots":
				bootsTexture = TextureManager.eBronzeBootsTexture;
				break;
			case "Steel Boots":
				bootsTexture = TextureManager.eSteelBootsTexture;
				break;
			}
			
			bootsSprite = new Sprite(bootsTexture);
			bootsSprite.setPosition(vp.getWorldWidth() / 2.93f, vp.getWorldHeight() / 3f);				
			bootsSprite.setSize(150, 60);
			
			bootsBatch.begin();
			bootsSprite.draw(bootsBatch);		
			bootsBatch.end();
		}
		
		if(!weaponPiece.equals("Empty")) {
			switch(weapon) {
			case "Iron Greataxe":
				weaponTexture = TextureManager.eIronGreataxeTexture;
				twoHand = true;
				break;
			case "Wooden Greataxe":
				weaponTexture = TextureManager.eWoodenGreataxeTexture;
				twoHand = true;
				break;
			case "Iron Axe":
				weaponTexture = TextureManager.eIronAxeTexture;
				twoHand = false;
				break;
			case "Wooden Axe":
				weaponTexture = TextureManager.eWoodenAxeTexture;
				twoHand = false;
				break;
			case "Bronze Greataxe":
				weaponTexture = TextureManager.eBronzeGreataxeTexture;
				twoHand = true;
				break;
			case "Bronze Axe":
				weaponTexture = TextureManager.eBronzeAxeTexture;
				twoHand = false;
				break;
			case "Steel Greataxe":
				weaponTexture = TextureManager.eSteelGreataxeTexture;
				twoHand = true;
				break;
			case "Steel Axe":
				weaponTexture = TextureManager.eSteelAxeTexture;
				twoHand = false;
				break;
			}
			
			weaponSprite = new Sprite(weaponTexture);
			weaponSprite.setOrigin(0, 0);
			if(twoHand) {
				weaponSprite.setPosition(vp.getWorldWidth() / 2.5f, vp.getWorldHeight() / 3f);				
				weaponSprite.setSize(350, 400);
			}			
			else {
				weaponSprite.setPosition(vp.getWorldWidth() / 2.26f, vp.getWorldHeight() / 2.5f);
				weaponSprite.setSize(220, 220);
			}
			
			weaponBatch.begin();
			if(twoHand)
				weaponSprite.setRotation(20f);
			else
				weaponSprite.setRotation(10f);
			weaponSprite.draw(weaponBatch);		
			weaponBatch.end();
		}
		
		if(!shieldPiece.equals("Empty")) {
			switch(shield) {
			case "Wooden Shield":
				shieldTexture = TextureManager.eWoodenShieldTexture;
				break;
			case "Iron Shield":
				shieldTexture = TextureManager.eIronShieldTexture;
				break;
			case "Bronze Shield":
				shieldTexture = TextureManager.eBronzeShieldTexture;
				break;
			case "Steel Shield":
				shieldTexture = TextureManager.eSteelShieldTexture;
				break;
			}
			
			shieldSprite = new Sprite(shieldTexture);
			shieldSprite.setPosition(vp.getWorldWidth() / 3.5f, vp.getWorldHeight() / 2.8f);				
			shieldSprite.setSize(200, 250);
			
			shieldBatch.begin();
			shieldSprite.draw(shieldBatch);		
			shieldBatch.end();
		}
		
		if(showCard) {
			abilityBatch.begin();
			abilityBatch.draw(TextureManager.gearCardTexture, gearName.getX() - 40f, gearName.getY(), 380, gearName.getHeight());
			abilityBatch.end();
		}
		
		stage.act();
		stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
		onionBatch.setProjectionMatrix(vp.getCamera().combined);
		weaponBatch.setProjectionMatrix(vp.getCamera().combined);
		shieldBatch.setProjectionMatrix(vp.getCamera().combined);
		helmetBatch.setProjectionMatrix(vp.getCamera().combined);
		chestBatch.setProjectionMatrix(vp.getCamera().combined);
		bootsBatch.setProjectionMatrix(vp.getCamera().combined);
		mapBatch.setProjectionMatrix(vp.getCamera().combined);	
		abilityBatch.setProjectionMatrix(vp.getCamera().combined);
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
		stage.dispose();
		skin.dispose();
		storage.font.dispose();	
		Storage.assetManager.dispose();	
	}

	public static int getShieldDP() {
		return shieldDP;
	}

	public static void setShieldDP(int shieldDP) {
		Inventory.shieldDP = shieldDP;
	}

	public static int getHelmetDP() {
		return helmetDP;
	}

	public static void setHelmetDP(int helmetDP) {
		Inventory.helmetDP = helmetDP;
	}

	public static int getChestDP() {
		return chestDP;
	}

	public static void setChestDP(int chestDP) {
		Inventory.chestDP = chestDP;
	}

	public static int getBootsDP() {
		return bootsDP;
	}

	public static void setBootsDP(int bootsDP) {
		Inventory.bootsDP = bootsDP;
	}
}
