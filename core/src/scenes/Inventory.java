package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
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
	Table characterTable = new Table();
	Table inventoryTable = new Table();	
	Table itemTable = new Table();
	java.util.List<Weapons> inventoryWeapons;
	java.util.List<Armor> inventoryArmor;
	java.util.List<Items> inventoryItems;
	java.util.List<Weapons> equippedWeapons;
	java.util.List<Armor> equippedArmor;
	java.util.List<Items> equippedItems;
	private static int shieldDP = 0, helmetDP = 0, chestDP = 0, bootsDP = 0, weaponAP = 0,
			bonusHP = 0, bonusAP = 0, bonusDP = 0;
	
	// Get textures from storage
	public static Texture inventorySlotTexture = Storage.assetManager.get("InventorySlot.png", Texture.class);
	public static Texture ironGreataxeTexture = Storage.assetManager.get("weapons/IronGreataxe.png", Texture.class);
	public static Texture ironAxeTexture = Storage.assetManager.get("weapons/IronAxe.png", Texture.class);
	public static Texture ironHelmetTexture = Storage.assetManager.get("armor/IronHelmet.png", Texture.class);
	public static Texture ironChestTexture = Storage.assetManager.get("armor/IronChest.png", Texture.class);
	public static Texture ironBootsTexture = Storage.assetManager.get("armor/IronBoots.png", Texture.class);
	public static Texture woodenShieldTexture = Storage.assetManager.get("weapons/WoodenShield.png", Texture.class);
	public static Texture healthPotionTexture = Storage.assetManager.get("items/HealthPotion.png", Texture.class);
	public static Texture bombTexture = Storage.assetManager.get("items/Bomb.png", Texture.class);
	public static Texture swingTexture = Storage.assetManager.get("abilities/Swing.png", Texture.class);

	Image inventorySlotImg = new Image(inventorySlotTexture);
	
	public Inventory(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		storage.createFont();	
		
		removeBonusStats();
		createComponents();	
		createInventoryGrid();
		createCharacterGrid();
		createItemGrid();
	}
	
	private void removeBonusStats() {
		if(SaveData.loaded) {
			bonusAP = bonusHP = bonusDP = 0;
			for(int ap : storage.getBonusAP())
				bonusAP += ap;
			for(int hp : storage.getBonusHP())
				bonusHP += hp;
			for(int dp : storage.getBonusDP())
				bonusDP += dp;
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
	            
	            // Check if there's a weapon to display in this slot.
	            if (weaponIndex < inventoryWeapons.size()) {
	                Weapons weapon = inventoryWeapons.get(weaponIndex);
	                slotTexture = setSlotImage(weapon.getWeaponName(), "Weapon");
	                weaponIndex++;
	                itemName = weapon.getWeaponName();
	            } 
	            else if (armorIndex < inventoryArmor.size()) {
	                Armor armor = inventoryArmor.get(armorIndex);
	                slotTexture = setSlotImage(armor.getArmorName(), "Armor");
	                armorIndex++;
	                itemName = armor.getArmorName();
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

	            inventorySlotImage.addListener(new ClickListener() {
	                @Override
	                public void clicked(InputEvent event, float x, float y) {
	                    handleInventoryClick(inventorySlotImage, item);
	                }
	            });
	            
	            inventorySlotImage.addListener(new InputListener() {
	                @Override
	                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                    gearName.setText(item);
	                    gearName.setAlignment(Align.center);
	                	gearName.setVisible(true);	                  	                    
	                    gearName.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 2f);
	                }

	                @Override
	                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    gearName.setVisible(false);
	                }
	            });
	        }
	        inventoryTable.row(); // To move to the next row after 5 items
	    } 
	    
	    inventoryTable.setPosition(vp.getWorldWidth() / 1.25f, vp.getWorldHeight() / 2f, Align.center);
	    stage.addActor(inventoryTable);
	}
	
	private void createCharacterGrid() {
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
	        
	        if (i <= 2) {  // Checking armor slots
	            String armorType = (i == 0) ? "Helmet" : (i == 1) ? "Chest" : "Boots";
	            if (armorMap.containsKey(armorType)) {
	                Armor armor = armorMap.get(armorType);
	                slotTexture = setSlotImage(armor.getArmorName(), "Armor");
	                emptySlot = false;
	                itemName = armor.getArmorName();
	            }
	        }
	        else if(i >= 3) {
	        	if(i == 3) {
	        		if (weaponMap.containsKey("Greataxe")) {
	                    Weapons weapon = weaponMap.get("Greataxe");
	                    slotTexture = setSlotImage(weapon.getWeaponName(), "Weapon");
	                    emptySlot = false;
	                    itemName = weapon.getWeaponName();
	                }
	        		else if (weaponMap.containsKey("Axe")) {
	                    Weapons weapon = weaponMap.get("Axe");
	                    slotTexture = setSlotImage(weapon.getWeaponName(), "Weapon");
	                    emptySlot = false;
	                    itemName = weapon.getWeaponName();
	                }
	        	}
	        	else if(i == 4) {
	        		if (weaponMap.containsKey("Shield")) {
	                    Weapons weapon = weaponMap.get("Shield");
	                    slotTexture = setSlotImage(weapon.getWeaponName(), "Weapon");
	                    emptySlot = false;
	                    itemName = weapon.getWeaponName();
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
	    	
	    	characterSlotImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                	handleCharacterSlotClick(characterSlotImage, item);
                }
            });
	    	characterSlotImage.addListener(new InputListener() {
                @Override
                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    gearName.setText(item);
                    gearName.setAlignment(Align.center);
                	gearName.setVisible(true);	                  	                    
                    gearName.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 2f);
                }

                @Override
                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                    gearName.setVisible(false);
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
	                    gearName.setText(item);
	                    gearName.setAlignment(Align.center);
	                	gearName.setVisible(true);	                  	                    
	                    gearName.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 2f);
	                }

	                @Override
	                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    gearName.setVisible(false);
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
			case "Iron Greataxe":
				return ironGreataxeTexture;
			case "Iron Axe":
				return ironAxeTexture;
			case "Wooden Shield":
				return woodenShieldTexture;
			case "":
				return inventorySlotTexture;
			default:
				return inventorySlotTexture;
			}
		}
		else if(gearType == "Armor") {
			switch(gearSlot) {
			case "Iron Helmet":
				return ironHelmetTexture;
			case "Iron Chest":
				return ironChestTexture;
			case "Iron Boots":
				return ironBootsTexture;
			case "":
				return inventorySlotTexture;
			default:
				return inventorySlotTexture;
			}
		}
		else if(gearType == "Item") {
			switch(itemName) {
			case "Health Potion":
				return healthPotionTexture;
			case "Bomb":
				return bombTexture;
			case "Swing":
				return swingTexture;
			default:
				return inventorySlotTexture;
			}
		}
		else
			return inventorySlotTexture;
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
			}
		}
		else if(gearSlot == "Weapon") {
			weaponAP = 0;
			switch(gearPiece) {
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
			case "Iron Axe":
				storage.inventoryWeapons(storage.ironAxe, "Add");
				storage.equippedWeapons(storage.ironAxe, "Remove");
				break;
			}		
			if(slot == 4) {
				storage.inventoryWeapons(storage.woodenShield, "Add");
				storage.equippedWeapons(storage.woodenShield, "Remove");			
			}	
		}
		else if(gearSlot == "OffHand") {
			switch(gearPiece) {
			case "Wooden Shield":
				storage.inventoryWeapons(storage.woodenShield, "Add");
				storage.equippedWeapons(storage.woodenShield, "Remove");
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
			}
		}		
		else if(handed == "OneHanded") {
			if(mainHand.getName() != "Empty")
				changeEquippedSlot(3, "Weapon");

			switch(weapon) {
			case "Iron Axe":
				storage.inventoryWeapons(storage.ironAxe, "Remove");
				storage.equippedWeapons(storage.ironAxe, "Add");
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
		
	    if(slot.getName().equals("Empty"))
	    	System.out.println("Empty slot clicked!");
	    else if(itemName.endsWith("Helmet")) {
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
	    	case "Swing":
	    		storage.inventoryItems(storage.itemSwing, "Remove");
	    		storage.equippedItems(storage.itemSwing, "Add");
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
	    	case "Swing":
	    		storage.inventoryItems(storage.itemSwing, "Add");
	    		storage.equippedItems(storage.itemSwing, "Remove");
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
		case "Iron Axe":
			weaponAP = storage.ironAxe.getWeaponDmg();
			Player.weaponState = 1;
			break;
		case "Wooden Shield":
			shieldDP = storage.woodenShield.getWeaponDmg();
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
    			
    			Player.gainDR(helmetDP + chestDP + bootsDP + shieldDP + bonusDP);
    			Player.gainMaxHP(bonusHP);
    			Player.gainWeaponDmg(weaponAP);
    			Player.gainBonusStr(bonusAP);
    			gameScreen.setCurrentState(GameScreen.HOME);
    	    }});
		backBtn.setSize(150, 100);
		backBtn.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.25f);
		stage.addActor(backBtn);	
		
		gearName = new Label("", storage.labelStyle);
		gearName.setVisible(false);
		stage.addActor(gearName);
	}

	@Override
	public void show() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(float delta) {
		stage.act();
		stage.draw();		
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
