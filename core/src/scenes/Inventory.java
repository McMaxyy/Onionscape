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
	java.util.List<Weapons> playerWeapons;
	private static String equippedHead = "", equippedChest = "", equippedLegs = "", 
			equippedMainHand = "", equippedOffHand = "";
	private static String equippedHand = "";
	
	// Get textures from storage
	Texture inventorySlotTexture = Storage.assetManager.get("InventorySlot.png", Texture.class);
	Texture ironGreataxeTexture = Storage.assetManager.get("weapons/IronGreataxe.png", Texture.class);
	Texture ironAxeTexture = Storage.assetManager.get("weapons/IronAxe.png", Texture.class);
	Texture ironHelmetTexture = Storage.assetManager.get("armor/IronHelmet.png", Texture.class);
	Texture ironChestTexture = Storage.assetManager.get("armor/IronChest.png", Texture.class);
	Texture ironBootsTexture = Storage.assetManager.get("armor/IronBoots.png", Texture.class);

	// Create images of textures
	Image inventorySlotImg = new Image(inventorySlotTexture);
	Image ironGreataxeImg = new Image(ironGreataxeTexture);
	Image ironAxeImg = new Image(ironAxeTexture);
	Image ironHelmetImg = new Image(ironHelmetTexture);
	Image ironChestImg = new Image(ironChestTexture);
	Image ironBootsImg = new Image(ironBootsTexture);
	
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
		checkEquippedItems();
		
		equippedHead = equippedChest = equippedLegs =  equippedMainHand = equippedOffHand = "";
	}
	
	private void createInventoryGrid() {    
	    playerWeapons = storage.getPlayerWeapons(); 
	    inventoryTable.defaults().size(100, 100); 
	    inventoryTable.setName("inventoryTable");
	    
	    int weaponIndex = 0;
	    
	    for (int y = 0; y < 8; y++) {
	        for (int x = 0; x < 5; x++) {                
	            boolean emptySlot = false;
	            String itemName = "";
	            Texture slotTexture = null;
	            
	            // Check if there's a weapon to display in this slot.
	            if (weaponIndex < playerWeapons.size()) {
	                Weapons weapon = playerWeapons.get(weaponIndex);
	                slotTexture = setSlotImage(weapon.getWeaponName(), "Weapon");
	                weaponIndex++;
	                itemName = weapon.getWeaponName();
	            } else {
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
	    characterTable.defaults().size(100, 100);
	    characterTable.setName("characterTable");
		
		for(int i = 0; i < 5; i++) {	    	
	    	final Image characterSlotImage = new Image(inventorySlotTexture);
	    	characterTable.add(characterSlotImage).pad(3);	
	    	characterSlotImage.setName("Empty");
	    	
	    	characterSlotImage.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                	handleCharacterSlotClick(characterSlotImage);
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
			default:
				return inventorySlotTexture;
			}
		}
		else
			return inventorySlotTexture;
	}
	
	// Used upon entering Inventory scene
	private void checkEquippedItems() {
		characterTable = (Table) stage.getRoot().findActor("characterTable");
		if (characterTable != null) {
		    for (int i = 0; i < 5; i++) {
		        Actor child = characterTable.getChildren().get(i);
		        if (child instanceof Image) {
		        	switch(i) {
			    	case 0:
			    		if(equippedHead != "")
			    			setArmor(equippedHead, checkGearSlot(equippedHead));
			    		break;
			    	case 1:
			    		if(equippedChest != "")
			    			setArmor(equippedChest, checkGearSlot(equippedChest));
			    		break;
			    	case 2:
			    		if(equippedLegs != "")
			    			setArmor(equippedLegs, checkGearSlot(equippedLegs));
			    		break;
			    	case 3:
			    		if(equippedMainHand != "")
			    			setWeapon(equippedMainHand, checkGearSlot(equippedMainHand));
			    		break;
			    	case 4:
			    		if(equippedOffHand != "")
			    			setWeapon(equippedOffHand, checkGearSlot(equippedOffHand));
			    		break;
			    	}
		        }
		    }
		}
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
		else
			return null;
	}

	// For leaving the Inventory scene
	private void checkEquippedGear() {
		Table characterTable = (Table) stage.getRoot().findActor("characterTable");
		if (characterTable != null) {
		    for (int i = 0; i < 5; i++) {
		        Actor child = characterTable.getChildren().get(i);
		        if (child instanceof Image) {
		        	Image slotImage = (Image) child;
		        	
		        	switch(i) {
			    	case 0:
			    		if(slotImage != inventorySlotImg)
			    			setGearVariables(slotImage.getName(), "Helmet");
			    		break;
			    	case 1:
			    		if(slotImage != inventorySlotImg)
			    			setGearVariables(slotImage.getName(), "Chest");
			    		break;
			    	case 2:
			    		if(slotImage != inventorySlotImg)
			    			setGearVariables(slotImage.getName(), "Boots");
			    		break;
			    	case 3:
			    		if(slotImage != inventorySlotImg)
			    			setGearVariables(slotImage.getName(), "MainHand");
			    		break;
			    	case 4:
			    		if(slotImage != inventorySlotImg)
			    			setGearVariables(slotImage.getName(), "OffHand");
			    		break;
			    	}
		        }
		    }
		}
	}
	
	private void setGearVariables(String gearName, String gearSlot) {
		switch(gearSlot) {
		case "Helmet":
			equippedHead = gearName;
			break;
		case "Chest":
			equippedChest = gearName;
			break;
		case "Boots":
			equippedLegs = gearName;
			break;
		case "MainHand":
			equippedMainHand = gearName;
			break;
		case "OffHand":
			equippedOffHand = gearName;
			break;
		}
	}
	
	private void changeEquippedSlot(int slot, String gearSlot) {
		Actor tableItem = characterTable.getChildren().get(slot);
		Image slotImg = (Image)tableItem;
		String gearPiece = tableItem.getName();
		
		if(gearSlot == "Helmet") {
			switch(gearPiece) {
			case "Iron Helmet":
				storage.addArmor(storage.ironHelmet);				
				break;
			}
		}
		else if(gearSlot == "Chest") {
			switch(gearPiece) {
			case "Iron Chest":
				storage.addArmor(storage.ironChest);
				break;
			}
		}
		else if(gearSlot == "Boots") {
			switch(gearPiece) {
			case "Iron Boots":
				storage.addArmor(storage.ironBoots);
				break;
			}
		}
		else if(gearSlot == "TwoHanded") {
			switch(gearPiece) {
			case "Iron Greataxe":
				storage.addWeapon(storage.ironGreatAxe);
				break;
			}
			if(slot == 4) {
				slotImg = new Image(inventorySlotTexture);
				slotImg.setName("Empty");
			}			
		}
		else if(gearSlot == "OneHanded") {
			switch(gearPiece) {
			case "Iron Axe":
				storage.addWeapon(storage.ironAxe);
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
			case "Iron Helmet":
				addToCharacter(new Image(ironHelmetTexture), armor, 1);
				storage.removeArmor(storage.ironHelmet);
				break;
			}
		}
		
		if(gearSlot == "Chest") {
			if(chest.getName() != "Empty")
		        changeEquippedSlot(1, "Chest");
			
			switch(armor) {
			case "Iron Chest":
				addToCharacter(new Image(ironChestTexture), armor, 2);
				break;
			}
		}
		
		if(gearSlot == "Boots") {
			if(boots.getName() != "Empty")
		        changeEquippedSlot(0, "Boots");
			
			switch(armor) {
			case "Iron Boots":
				addToCharacter(new Image(ironBootsTexture), armor, 3);
				break;
			}
		}
	}
	
	private void setWeapon(String weapon, String handed) {
		Actor mainHand = characterTable.getChildren().get(3);
		Actor offHand = characterTable.getChildren().get(4);

		if(handed == "TwoHanded") {				
			if (!mainHand.getName().equals("Empty")) {
		        changeEquippedSlot(3, "TwoHanded");
		    }
		    if (!offHand.getName().equals("Empty")) {
		        changeEquippedSlot(4, "TwoHanded");
		    }
			
			switch(weapon) {
			case "Iron Greataxe":
				equippedHand = "TwoHand";
				addToCharacter(new Image(ironGreataxeTexture), weapon, 4);							
				break;
			}
		}
		
		if(handed == "OneHanded") {
			switch(weapon) {
			case "Iron Axe":
				if(equippedHand == "TwoHand" || equippedHand == "") {
					changeEquippedSlot(3, handed);
					addToCharacter(new Image(ironAxeTexture), weapon, 4);
					equippedHand = "OneHand";
				}
				else {
					addToCharacter(ironAxeImg, weapon, 5);
				}			
				break;
			}
		}
	}	

	private void addToCharacter(Image itemImg, String itemName, int slot) {
	    int currentIndex = 0;

	    // Directly fetch the characterTable using its unique name
	    Table table = (Table) stage.getRoot().findActor("characterTable");
	    
	    if (table != null) {
	        for (Actor child : table.getChildren()) {
	            if (child instanceof Image) {
	                currentIndex++;
	                if (currentIndex == slot) {
	                    Image image = (Image) child;	                    
	                    image.setDrawable(itemImg.getDrawable());
	                    image.setName(itemName);
	                    return;
	                }
	            }
	        }
	    }
	}
	
	private void handleCharacterSlotClick(Image slot) {
		
	}

	private void handleSlotClick(Image slot, String itemName) {	    
	    if(slot.getName() == "Empty")
	    	System.out.println("Empty slot clicked!");
	    else {
	    	System.out.println("Clicked on " + itemName);
	    	addWeaponDamage(itemName);
	    	
	    	setWeapon(itemName, checkGearSlot(itemName));
	    	
	    	Weapons weaponToRemove = null;
	        for (Weapons weapon : storage.getPlayerWeapons()) {
	            if (weapon.getWeaponName().equals(itemName)) {
	                weaponToRemove = weapon;
	                break;
	            }
	        }
	        if (weaponToRemove != null) {
	        	storage.removeWeapon(weaponToRemove);
	        }
	        	        
	        inventoryTable.clear();
	        createInventoryGrid();
	    }	    	
	}
	
	private void addWeaponDamage(String weapon) {
		switch(weapon) {
		case "Iron Greataxe":
			Player.setWeaponDmg(4);
			Player.weaponState = 2;
			break;
		case "Iron Axe":
			Player.setWeaponDmg(2);
			Player.weaponState = 1;
			break;
		}
	}

	private void createComponents() {
		backBtn = new TextButton("Back", storage.buttonStyle);
		backBtn.setColor(Color.LIGHT_GRAY);
		backBtn.addListener(new ClickListener() {
    		@Override
    	    public void clicked(InputEvent event, float x, float y) {
    			checkEquippedGear();
    			equippedHand = "";
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
