package scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;
import java.util.HashMap;
import java.util.Map;

import player.Armor;
import player.Player;
import player.Storage;
import player.Weapons;

public class Inventory implements Screen {
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Game game;
	private GameScreen gameScreen;
	private Storage storage;
	private TextButton backBtn;
	Table characterTable = new Table();
	Table inventoryTable = new Table();	
	java.util.List<Weapons> inventoryWeapons;
	java.util.List<Armor> inventoryArmor;
	java.util.List<Weapons> equippedWeapons;
	java.util.List<Armor> equippedArmor;
	private static int shieldDP = 0, helmetDP = 0, chestDP = 0, bootsDP = 0;
	
	// Get textures from storage
	Texture inventorySlotTexture = Storage.assetManager.get("InventorySlot.png", Texture.class);
	Texture ironGreataxeTexture = Storage.assetManager.get("weapons/IronGreataxe.png", Texture.class);
	Texture ironAxeTexture = Storage.assetManager.get("weapons/IronAxe.png", Texture.class);
	Texture ironHelmetTexture = Storage.assetManager.get("armor/IronHelmet.png", Texture.class);
	Texture ironChestTexture = Storage.assetManager.get("armor/IronChest.png", Texture.class);
	Texture ironBootsTexture = Storage.assetManager.get("armor/IronBoots.png", Texture.class);
	Texture woodenShieldTexture = Storage.assetManager.get("weapons/WoodenShield.png", Texture.class);
	
	Image inventorySlotImg = new Image(inventorySlotTexture);
	
	public Inventory(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
		storage.createFont();
		
		createComponents();	
		createInventoryGrid();
		createCharacterGrid();
	}
	
	private void createInventoryGrid() {    
	    inventoryWeapons = storage.getPlayerWeapons();
	    inventoryArmor = storage.getPlayerArmor();
	    inventoryTable.defaults().size(100, 100); 
	    inventoryTable.setName("inventoryTable");
	    
	    int weaponIndex = 0;
	    int armorIndex = 0;
	    
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
	                    handleSlotClick(inventorySlotImage, item);
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
	    	characterTable.row();
        }
		characterTable.setPosition(vp.getWorldWidth() / 5f, vp.getWorldHeight() / 2f, Align.center);
	    stage.addActor(characterTable);
	}
	
	private Texture setSlotImage(String itemName, String gearType) {
		if(gearType == "Weapon") {
			switch(itemName) {
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
			switch(itemName) {
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
			switch(gearPiece) {
			case "Iron Helmet":
				storage.inventoryArmor(storage.ironHelmet, "Add");
				storage.characterArmor(storage.ironHelmet, "Remove");
				break;
			}
		}
		else if(gearSlot == "Chest") {
			switch(gearPiece) {
			case "Iron Chest":
				storage.inventoryArmor(storage.ironChest, "Add");
				storage.characterArmor(storage.ironChest, "Remove");
				break;
			}
		}
		else if(gearSlot == "Boots") {
			switch(gearPiece) {
			case "Iron Boots":
				storage.inventoryArmor(storage.ironBoots, "Add");
				storage.characterArmor(storage.ironBoots, "Remove");
				break;
			}
		}
		else if(gearSlot == "TwoHanded") {
			switch(gearPiece) {
			case "Iron Greataxe":
				storage.inventoryWeapons(storage.ironGreataxe, "Add");
				storage.characterWeapons(storage.ironGreataxe, "Remove");
				break;
			case "Iron Axe":
				storage.inventoryWeapons(storage.ironAxe, "Add");
				storage.characterWeapons(storage.ironAxe, "Remove");
				break;
			}		
			if(slot == 4) {
				System.out.println("Removed offhand");
				storage.inventoryWeapons(storage.woodenShield, "Add");
				storage.characterWeapons(storage.woodenShield, "Remove");
				
			}			
		}
		else if(gearSlot == "OneHanded") {
			switch(gearPiece) {
			case "Iron Axe":
				storage.inventoryWeapons(storage.ironAxe, "Add");
				storage.characterWeapons(storage.ironAxe, "Remove");
				break;
			case "Iron Greataxe":
				storage.inventoryWeapons(storage.ironGreataxe, "Add");
				storage.characterWeapons(storage.ironGreataxe, "Remove");
				break;
			}
		}
		else if(gearSlot == "OffHand") {
			switch(gearPiece) {
			case "Wooden Shield":
				storage.inventoryWeapons(storage.woodenShield, "Add");
				storage.characterWeapons(storage.woodenShield, "Remove");
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
			if(helmetDP != 0)
				Player.loseDR(helmetDP);
			
			switch(armor) {
			case "Iron Helmet":
				storage.inventoryArmor(storage.ironHelmet, "Remove");
				storage.characterArmor(storage.ironHelmet, "Add");
				helmetDP = storage.ironHelmet.getDefense();
				break;
			}
			Player.gainDR(helmetDP);
		}
		
		if(gearSlot == "Chest") {
			if(chest.getName() != "Empty")
		        changeEquippedSlot(1, "Chest");
			if(chestDP != 0)
				Player.loseDR(chestDP);
			
			switch(armor) {
			case "Iron Chest":
				storage.inventoryArmor(storage.ironChest, "Remove");
				storage.characterArmor(storage.ironChest, "Add");
				chestDP = storage.ironChest.getDefense();				
				break;
			}			
			Player.gainDR(chestDP);
		}
		
		if(gearSlot == "Boots") {
			if(boots.getName() != "Empty")
		        changeEquippedSlot(2, "Boots");
			if(bootsDP != 0)
				Player.loseDR(bootsDP);
			
			switch(armor) {
			case "Iron Boots":
				storage.inventoryArmor(storage.ironBoots, "Remove");
				storage.characterArmor(storage.ironBoots, "Add");
				bootsDP = storage.ironBoots.getDefense();				
				break;
			}
			Player.gainDR(bootsDP);
		}
	}
	
	private void setWeapon(String weapon, String handed) {
		Actor mainHand = characterTable.getChildren().get(3);
		Actor offHand = characterTable.getChildren().get(4);

		if(handed == "TwoHanded") {				
			if (mainHand.getName() != "Empty") {
		        changeEquippedSlot(3, "TwoHanded");
		    }
		    if (offHand.getName() != "Empty") {
		        changeEquippedSlot(4, "TwoHanded");
		    }
			
			switch(weapon) {
			case "Iron Greataxe":
				storage.inventoryWeapons(storage.ironGreataxe, "Remove");
				storage.characterWeapons(storage.ironGreataxe, "Add");
				break;
			}
		}		
		else if(handed == "OneHanded") {
			if(mainHand.getName() != "Empty")
				changeEquippedSlot(3, "OneHanded");

			switch(weapon) {
			case "Iron Axe":
				storage.inventoryWeapons(storage.ironAxe, "Remove");
				storage.characterWeapons(storage.ironAxe, "Add");
				break;
			}
		}
		else if(handed == "OffHand") {
			if(mainHand.getName() != "Empty" && mainHand.getName().endsWith("Greataxe"))
				changeEquippedSlot(3, "OneHanded");
			if (offHand.getName() != "Empty")
		        changeEquippedSlot(4, "TwoHanded");

			switch(weapon) {
			case "Wooden Shield":
				storage.inventoryWeapons(storage.woodenShield, "Remove");
				storage.characterWeapons(storage.woodenShield, "Add");
				break;
			}
		}
	}	

	private void handleCharacterSlotClick(Image slot, String itemName) {
		if(slot.getName() == "Empty")
	    	System.out.println("Empty character slot clicked!");
		else if(slot.getName().endsWith("Helmet")) {
			changeEquippedSlot(0, "Helmet");
			slot.setName("Empty");
			Player.loseDR(helmetDP);
			helmetDP = 0;
		}
		else if(slot.getName().endsWith("Chest")) {
			changeEquippedSlot(1, "Chest");
			slot.setName("Empty");
			Player.loseDR(chestDP);
			chestDP = 0;
		}
		else if(slot.getName().endsWith("Boots")) {
			changeEquippedSlot(2, "Boots");
			slot.setName("Empty");
			Player.loseDR(bootsDP);
			bootsDP = 0;
		}
		else if(slot.getName().endsWith("Greataxe")) {
			changeEquippedSlot(3, "TwoHanded");
			slot.setName("Empty");
			Player.setWeaponDmg(0);
			Player.weaponState = 0;
		}
		else if(slot.getName().endsWith("Axe")) {
			changeEquippedSlot(3, "OneHanded");
			slot.setName("Empty");
			Player.setWeaponDmg(0);
			Player.weaponState = 0;
		}
		else if(slot.getName().endsWith("Shield")) {
			changeEquippedSlot(4, "OffHand");
			slot.setName("Empty");
			Player.loseDR(shieldDP);
			shieldDP = 0;
		}
		
		inventoryTable.clear();
        createInventoryGrid();
        characterTable.clear();
        createCharacterGrid();
	}

	private void handleSlotClick(Image slot, String itemName) {	
		 
	    if(slot.getName() == "Empty")
	    	System.out.println("Empty slot clicked!");
	    else if(itemName.endsWith("Helmet"))
	    	setArmor(itemName, "Helmet");
	    else if(itemName.endsWith("Chest"))
	    	setArmor(itemName, "Chest");
	    else if(itemName.endsWith("Boots"))
	    	setArmor(itemName, "Boots");
	    else {
	    	addWeaponDamage(itemName);	    	
	    	setWeapon(itemName, checkGearSlot(itemName));      
	    }
	    
	    inventoryTable.clear();
        createInventoryGrid();
        characterTable.clear();
        createCharacterGrid();
	}
	
	private void addWeaponDamage(String weapon) {
		switch(weapon) {
		case "Iron Greataxe":
			Player.setWeaponDmg(storage.ironGreataxe.getWeaponDmg());
			Player.weaponState = 2;
			break;
		case "Iron Axe":
			Player.setWeaponDmg(storage.ironAxe.getWeaponDmg());
			Player.weaponState = 1;
			break;
		case "Wooden Shield":
			if(shieldDP != 0)
				Player.loseDR(shieldDP);
			shieldDP = storage.woodenShield.getWeaponDmg();
			Player.gainDR(shieldDP);
			break;
		}
	}

	private void createComponents() {
		backBtn = new TextButton("Back", storage.buttonStyle);
		backBtn.setColor(Color.LIGHT_GRAY);
		backBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {   			
    			gameScreen.setCurrentState(GameScreen.HOME);
    	    }});
		backBtn.setSize(150, 100);
		backBtn.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.25f);
		stage.addActor(backBtn);	
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
}
