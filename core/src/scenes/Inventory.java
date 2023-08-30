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
	private static String equippedHead = "", equippedChest = "", equippedLegs = "", 
			equippedMainHand = "", equippedOffHand = "";
	private static String equippedHand = "";
	
	// Get textures from storage
	Texture inventorySlotTexture = Storage.assetManager.get("InventorySlot.png", Texture.class);
	Texture ironGreataxeTexture = Storage.assetManager.get("weapons/IronGreataxe.png", Texture.class);
	Texture ironAxeTexture = Storage.assetManager.get("weapons/IronAxe.png", Texture.class);

	// Create images of textures
	Image inventorySlotImg = new Image(inventorySlotTexture);
	Image ironGreataxeImg = new Image(ironGreataxeTexture);
	Image ironAxeImg = new Image(ironAxeTexture);
	
	public Inventory(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = new Skin(Gdx.files.internal("buttons/uiskin.json"));
		storage.createFont();
		System.out.println(equippedMainHand);
		
		createComponents();	
		createInventoryGrid();
		createCharacterGrid();
		checkEquippedItems();
		
		equippedHead = equippedChest = equippedLegs =  equippedMainHand = equippedOffHand = "";
	}
	
	private void createInventoryGrid() {	
		java.util.List<Weapons> playerWeapons = storage.getPlayerWeapons(); 
	    inventoryTable.defaults().size(100, 100); 	    
	    
	    int weaponIndex = 0;
	    Texture slotTexture;
	    
	    for (int y = 0; y < 8; y++) {
	        for (int x = 0; x < 5; x++) {	        	
	        	boolean emptySlot = false;
	        	String itemName = "";
	            
	            // Check if there's a weapon to display in this slot.
	            if (weaponIndex < playerWeapons.size()) {
	                Weapons weapon = playerWeapons.get(weaponIndex);
	                slotTexture = getWeaponTexture(weapon.getWeaponName());
	                weaponIndex++;
	                itemName = weapon.getWeaponName();
	            } else {
	                slotTexture = inventorySlotTexture;
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
	    Texture slotTexture;
		
		for(int i = 0; i < 5; i++) {
	    	slotTexture = inventorySlotTexture;
	    	
	    	final Image characterSlotImage = new Image(slotTexture);
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
	
	// Used upon entering Inventory scene
	private void checkEquippedItems() {
		Table characterTable = (Table) stage.getRoot().findActor("characterTable");
		if (characterTable != null) {
		    for (int i = 0; i < 5; i++) {
		        Actor child = characterTable.getChildren().get(i);
		        if (child instanceof Image) {
		        	switch(i) {
			    	case 0:
			    	case 1:
			    	case 2:
			    		break;
			    	case 3:
			    		if(equippedMainHand != "")
			    			setWeapon(equippedMainHand);
			    		break;
			    	case 4:
			    		if(equippedOffHand != "")
			    			setWeapon(equippedOffHand);
			    		break;
			    	}
		        }
		    }
		}
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
			    	case 1:
			    	case 2:
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
		case "Head":
		case "Chest":
		case "Legs":
			break;
		case "MainHand":
			equippedMainHand = gearName;
			break;
		case "OffHand":
			equippedOffHand = gearName;
			break;
		}
	}
	
	private void setWeapon(String weapon) {
		Actor fifthSlotActor = characterTable.getChildren().get(4); // 5th slot would be indexed as 4	    
		switch(weapon) {
		case "Iron Greataxe":
			equippedHand = "TwoHand";
			addToCharacter(ironGreataxeImg, weapon, 4);			
			if (fifthSlotActor instanceof Image) {
		        Image fifthSlotImage = (Image) fifthSlotActor;
		        fifthSlotImage.setDrawable(new Image(inventorySlotTexture).getDrawable());
		        fifthSlotImage.setName("Empty");  // Assuming you want to reset the name as well
		    }
			break;
		case "Iron Axe":
			if(equippedHand == "TwoHand" || equippedHand == "") {
				addToCharacter(ironAxeImg, weapon, 4);
				equippedHand = "OneHand";
			}
			else {
				addToCharacter(ironAxeImg, weapon, 5);
			}			
			break;
		}		
	}	

	private void addToCharacter(Image weaponImage, String weaponName, int slot) {
	    int currentIndex = 0;

	    // Directly fetch the characterTable using its unique name
	    Table table = (Table) stage.getRoot().findActor("characterTable");
	    
	    if (table != null) {
	        for (Actor child : table.getChildren()) {
	            if (child instanceof Image) {
	                currentIndex++;
	                if (currentIndex == slot) {
	                    Image image = (Image) child;
	                    
	                    // Set the weapon to the desired slot
	                    image.setDrawable(weaponImage.getDrawable());
	                    image.setName(weaponName);
	                    return; // Stop once weapon is added
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
	    	
	    	setWeapon(itemName);
	    	
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

	private Texture getWeaponTexture(String weaponName) {
	    switch (weaponName) {
	        case "Iron Greataxe":
	            return ironGreataxeTexture;
	        case "Iron Axe":
	            return ironAxeTexture;
	        default:
	            return inventorySlotTexture;
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
