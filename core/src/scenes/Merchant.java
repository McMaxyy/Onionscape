package scenes;

import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.onionscape.game.GameScreen;

import player.Player;
import storage.Armor;
import storage.Items;
import storage.Storage;
import storage.Weapons;

public class Merchant implements Screen{
	Skin skin;
	Viewport vp;
	public Stage stage;
	private Game game;
	private GameScreen gameScreen;
	private Storage storage;
	java.util.List<Weapons> inventoryWeapons;
	java.util.List<Armor> inventoryArmor;
	java.util.List<Items> inventoryItems;
	java.util.List<Items> equippedItems;
	Table inventoryTable = new Table();	
	Table itemTable = new Table();
	Table prefixTable = new Table();
	Table gearTable = new Table();
	Table discountTable = new Table();
	Table standardTable = new Table();
	private Image[] discountBtns = new Image[3];
	private Image[] standardBtns = new Image[3];
	private TextButton[] prefixBtns = new TextButton[11];
	private Image[] gearBtns = new Image[18];
	private Label gearName, coins;
	private TextButton backBtn;
	public static boolean raid;
	private int itemValue;
	private String itemName;
	private Random rand = new Random();
	
	public Merchant(Viewport viewport, Game game, GameScreen gameScreen) {
		this.gameScreen = gameScreen;
		this.game = game;
		stage = new Stage(viewport);
		vp = viewport;
		Gdx.input.setInputProcessor(stage);
		storage = Storage.getInstance();
		skin = storage.skin;
		storage.createFont();
		
		createComponents();	
		if(!raid) {
			createInventoryGrid();
		}			
		else {
			createItemGrid();
		}			
	}
	
	private void loadPrefixGear(String prefix) {
	    String[] gearTypes = {"Helmet", "Chest", "Boots", "Axe", "Greataxe", "Shield"};
	    String[] t1Mats = {"Iron", "Bronze", "Steel"};
	    String[] t2Mats = {"Silver", "Gold", "Platinum"};
	    String[] t3Mats = {"Obsidian", "Molten", "Starmetal"};	    
	    String[] chosenPrefix;
	    
	    if(prefix.equals("Healthy") || prefix.equals("Strong") || prefix.equals("Defensive"))
	    	chosenPrefix = t1Mats;
	    else if(prefix.equals("Valkyrie") || prefix.equals("Ravager") || prefix.equals("Protector"))
	    	chosenPrefix = t2Mats;
	    else
	    	chosenPrefix = t3Mats;
	    
	    int index = 0;
	    
	    for (String material : chosenPrefix) {
	        for (String type : gearTypes) {
	            gearBtns[index] = new Image(getTextureByTypeAndMaterial(type, material));
	            gearBtns[index].setName(prefix + " " + material + " " + type);
	            final String item = gearBtns[index].getName().toString();	         
	            gearBtns[index].addListener(new ClickListener() {
	        		@Override
	        	    public void clicked(InputEvent event, float x, float y) {
	        			handleInventoryClick(item, 2);
	        	    }});
	            
	            final int num = index;
	            final int cost = itemValue;
	            gearBtns[index].addListener(new InputListener() {
	                @Override
	                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    	gearName.setText(gearBtns[num].getName() + "\nCost: " + cost);
	                    gearName.setAlignment(Align.center);
	                	gearName.setVisible(true);	                  	                    
	                    gearName.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 1.2f);            	
	                }

	                @Override
	                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    gearName.setVisible(false);
	                }
	            });
	            index++;
	        }
	    }
	    
	    gearTable.clearChildren();  // Clear the existing content
	    index = 0;
	    for (int x = 0; x < 6; x++) {
	        for (int y = 0; y < 3; y++) {
	            if (index < gearBtns.length) {
	                gearTable.add(gearBtns[index]).pad(0);
	                index++;
	            }
	        }
	        gearTable.row();
	    }
	}
	
	public Texture getTextureByTypeAndMaterial(String type, String material) {
	    switch (material) {
	        case "Iron":
	            switch (type) {
	            case "Helmet": 
	            	itemValue = storage.healthyIronHelmet.getValue();
	            	return Inventory.ironHelmetTexture;
	            case "Chest": 
	            	itemValue = storage.healthyIronChest.getValue();
	            	return Inventory.ironChestTexture;
	            case "Boots": 
	            	itemValue = storage.healthyIronBoots.getValue();
	            	return Inventory.ironBootsTexture;
	            case "Axe": 
	            	itemValue = storage.healthyIronAxe.getValue();
	            	return Inventory.ironAxeTexture;
	            case "Greataxe": 
	            	itemValue = storage.healthyIronGA.getValue();
	            	return Inventory.ironGreataxeTexture;
	            case "Shield": 
	            	itemValue = storage.healthyIronShield.getValue();
	            	return Inventory.ironShieldTexture;
	            default: 
	            	return Inventory.inventorySlotTexture;
	            }
	        case "Bronze":
	            switch (type) {
	            case "Helmet": 
	            	itemValue = storage.healthyBronzeHelmet.getValue();
	            	return Inventory.bronzeHelmetTexture;
	            case "Chest": 
	            	itemValue = storage.healthyBronzeChest.getValue();
	            	return Inventory.bronzeChestTexture;
	            case "Boots": 
	            	itemValue = storage.healthyBronzeBoots.getValue();
	            	return Inventory.bronzeBootsTexture;
	            case "Axe": 
	            	itemValue = storage.healthyBronzeAxe.getValue();
	            	return Inventory.bronzeAxeTexture;
	            case "Greataxe": 
	            	itemValue = storage.healthyBronzeGA.getValue();
	            	return Inventory.bronzeGreataxeTexture;
	            case "Shield": 
	            	itemValue = storage.healthyBronzeShield.getValue();
	            	return Inventory.bronzeShieldTexture;
		        default: 
		        	return Inventory.inventorySlotTexture;
	            }
	        case "Steel":
	            switch (type) {
	            case "Helmet": 
                	itemValue = storage.healthySteelHelmet.getValue();
                	return Inventory.steelHelmetTexture;
                case "Chest": 
                	itemValue = storage.healthySteelChest.getValue();
                	return Inventory.steelChestTexture;
                case "Boots": 
                	itemValue = storage.healthySteelBoots.getValue();
                	return Inventory.steelBootsTexture;
                case "Axe": 
                	itemValue = storage.healthySteelAxe.getValue();
                	return Inventory.steelAxeTexture;
                case "Greataxe": 
                	itemValue = storage.healthySteelGA.getValue();
                	return Inventory.steelGreataxeTexture;
                case "Shield": 
                	itemValue = storage.healthySteelShield.getValue();
                	return Inventory.steelShieldTexture;
                default: 
                	return Inventory.inventorySlotTexture;
	            }
	        default:
	        	return Inventory.inventorySlotTexture;
	    }
	}
	
	private void createItemGrid() {    
	    equippedItems = storage.getEquippedItems();
	    itemTable.defaults().size(100, 100); 
	    itemTable.setName("itemTable");

	    int itemIndex = 0;
	    
	    for (int y = 0; y < 7; y++) {
	        for (int x = 0; x < 2; x++) {                
	            boolean emptySlot = false;
	            String itemName = "";
	            Texture slotTexture = null;
	            int value = 0;
	            
	            // Check if there's a weapon to display in this slot.
	            if (itemIndex < equippedItems.size()) {
	                Items item = equippedItems.get(itemIndex);
	                slotTexture = setSlotImage(item.getItemName(), "Item");
	                itemIndex++;
	                itemName = item.getItemName();
	                value = item.getValue();
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
	            final int cost = value;

	            inventorySlotImage.addListener(new ClickListener() {
	                @Override
	                public void clicked(InputEvent event, float x, float y) {
	                	handleInventoryClick(item, 1);
	                }					
	            });
	            inventorySlotImage.addListener(new InputListener() {
	                @Override
	                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                    if(!item.equals("")) {
	                    	gearName.setText(item + "\nValue: " + cost / 2);
		                    if(cost / 2 == 0)
		                    	gearName.setText(item + "\nValue: " + cost);
		                    gearName.setAlignment(Align.center);
		                	gearName.setVisible(true);	                  	                    
		                    gearName.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 1.2f);
	                    }	                    	            
	                }

	                @Override
	                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    gearName.setVisible(false);
	                }
	            });
	        }
	        itemTable.row();
	    } 
	    
	    itemTable.setPosition(vp.getWorldWidth() / 1.25f, vp.getWorldHeight() / 2f, Align.center);
	    stage.addActor(itemTable);
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
	            int value = 0;
	        	
	            // Check if there's a weapon to display in this slot.
	            if (weaponIndex < inventoryWeapons.size()) {
	                Weapons weapon = inventoryWeapons.get(weaponIndex);
	                slotTexture = setSlotImage(weapon.getWeaponName(), "Weapon");
	                weaponIndex++;
	                itemName = weapon.getWeaponName();
	                value = weapon.getValue();
	            } 
	            else if (armorIndex < inventoryArmor.size()) {
	                Armor armor = inventoryArmor.get(armorIndex);
	                slotTexture = setSlotImage(armor.getArmorName(), "Armor");
	                armorIndex++;
	                itemName = armor.getArmorName();
	                value = armor.getValue();
	            }
	            else if (itemIndex < inventoryItems.size()) {
	                Items item = inventoryItems.get(itemIndex);
	                slotTexture = setSlotImage(item.getItemName(), "Item");
	                itemIndex++;
	                itemName = item.getItemName();	
	                value = item.getValue();
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
	            final int cost = value;

	            inventorySlotImage.addListener(new ClickListener() {
	                @Override
	                public void clicked(InputEvent event, float x, float y) {
	                    handleInventoryClick(item, 1);
	                }
	            });
	            
	            inventorySlotImage.addListener(new InputListener() {
	                @Override
	                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                	if(!item.equals("")) {
	                		gearName.setText(item + "\nValue: " + cost / 2);
		                    if(cost / 2 == 0)
		                    	gearName.setText(item + "\nValue: " + cost);
		                    gearName.setAlignment(Align.center);
		                	gearName.setVisible(true);	                  	                    
		                    gearName.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 1.2f);
	                	}
	                }

	                @Override
	                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    gearName.setVisible(false);
	                }
	            });
	        }
	        inventoryTable.row();
	    } 
	    
	    inventoryTable.setPosition(vp.getWorldWidth() / 1.25f, vp.getWorldHeight() / 2f, Align.center);
	    stage.addActor(inventoryTable);
	}
	
	private Texture setSlotImage(String itemName, String gearType) {
		String gearSlot = "";
		String[] words = itemName.split(" ");
		if(!itemName.equals("") && !gearType.equals("Item"))
			gearSlot = words[words.length - 2] + " " + words[words.length - 1];
		
		if(gearType == "Weapon") {
			switch(gearSlot) {
			case "Wooden Greataxe":
				return Inventory.woodenGreataxeTexture;
			case "Iron Greataxe":
				return Inventory.ironGreataxeTexture;
			case "Iron Axe":
				return Inventory.ironAxeTexture;
			case "Wooden Axe":
				return Inventory.woodenAxeTexture;
			case "Wooden Shield":
				return Inventory.woodenShieldTexture;
			case "Iron Shield":
				return Inventory.ironShieldTexture;
			case "Bronze Greataxe":
				return Inventory.bronzeGreataxeTexture;
			case "Bronze Axe":
				return Inventory.bronzeAxeTexture;
			case "Bronze Shield":
				return Inventory.bronzeShieldTexture;
			case "Steel Greataxe":
				return Inventory.steelGreataxeTexture;
			case "Steel Axe":
				return Inventory.steelAxeTexture;
			case "Steel Shield":
				return Inventory.steelShieldTexture;
			case "":
				return Inventory.inventorySlotTexture;
			default:
				return Inventory.inventorySlotTexture;
			}
		}
		else if(gearType == "Armor") {
			switch(gearSlot) {
			case "Iron Helmet":
				return Inventory.ironHelmetTexture;
			case "Iron Chest":
				return Inventory.ironChestTexture;
			case "Iron Boots":
				return Inventory.ironBootsTexture;
			case "Bronze Helmet":
				return Inventory.bronzeHelmetTexture;
			case "Bronze Chest":
				return Inventory.bronzeChestTexture;
			case "Bronze Boots":
				return Inventory.bronzeBootsTexture;
			case "Steel Helmet":
				return Inventory.steelHelmetTexture;
			case "Steel Chest":
				return Inventory.steelChestTexture;
			case "Steel Boots":
				return Inventory.steelBootsTexture;
			case "":
				return Inventory.inventorySlotTexture;
			default:
				return Inventory.inventorySlotTexture;
			}
		}
		else if(gearType == "Item") {
			switch(itemName) {
			case "Health Potion":
				return Inventory.healthPotionTexture;
			case "Bomb":
				return Inventory.bombTexture;
			case "Swing":
				return Inventory.swingTexture;
			case "Rend":
				return Inventory.rendTexture;
			case "Whirlwind":
				return Inventory.whirlwindTexture;
			case "Ground Breaker":
				return Inventory.groundBreakerTexture;
			case "Bash":
				return Inventory.bashTexture;
			case "Barrier":
				return Inventory.barrierTexture;
			case "Harden":
				return Inventory.hardenTexture;
			case "Mend":
				return Inventory.mendTexture;
			case "Hilt Bash":
				return Inventory.hiltBashTexture;
			case "Barbed Armor":
				return Inventory.barbedArmorTexture;
			case "Enrage":
				return Inventory.enrageTexture;
			case "Riposte":
				return Inventory.riposteTexture;
			case "Stab":
				return Inventory.stabTexture;
			case "Decapitate":
				return Inventory.decapitateTexture;
			case "Attack Boost":
				return Inventory.apTexture;
			case "Defense Boost":
				return Inventory.dpTexture;
			case "Health Boost":
				return Inventory.hpTexture;
			case "Experience Boost":
				return Inventory.expTexture;
			default:
				return Inventory.inventorySlotTexture;
			}
		}
		else
			return Inventory.inventorySlotTexture;
	}	
	
	private void createComponents() {
		backBtn = new TextButton("Back", storage.buttonStyle);
		backBtn.setColor(Color.LIGHT_GRAY);
		backBtn.addListener(new ClickListener() {
			@Override
		    public void clicked(InputEvent event, float x, float y) {  
				if(!raid)
					gameScreen.setCurrentState(GameScreen.HOME);
				else
					gameScreen.setCurrentState(GameScreen.FOREST_MAP);
					
		    }});
		backBtn.setSize(150, 100);
		backBtn.setPosition(vp.getWorldWidth() / 40f, vp.getWorldHeight() / 1.25f);
		stage.addActor(backBtn);	
		
		gearName = new Label("", storage.labelStyle);
		gearName.setVisible(false);
		stage.addActor(gearName);
		
		if(!raid)
			coins = new Label("Coins: " + Player.getCoins(), storage.labelStyle);
		else
			coins = new Label("Coins: " + Player.getRaidCoins(), storage.labelStyle);
		coins.setPosition(vp.getWorldWidth() / 7f, vp.getWorldHeight() / 1.25f);
		stage.addActor(coins);
		
		// Normal merchant
		if(!raid) {
			// Prefix table (non-raid)		
			prefixBtns[0] = new TextButton("Healthy", storage.buttonStyle);
			prefixBtns[1] = new TextButton("Strong", storage.buttonStyle);
			prefixBtns[2] = new TextButton("Defensive", storage.buttonStyle);
			prefixBtns[3] = new TextButton("Valkyrie", storage.buttonStyle);
			prefixBtns[4] = new TextButton("Ravager", storage.buttonStyle);
			prefixBtns[5] = new TextButton("Protector", storage.buttonStyle);
			prefixBtns[6] = new TextButton("Marauder", storage.buttonStyle);
			prefixBtns[7] = new TextButton("Knight", storage.buttonStyle);
			prefixBtns[8] = new TextButton("Berserker", storage.buttonStyle);
			prefixBtns[9] = new TextButton("Paladin", storage.buttonStyle);
			prefixBtns[10] = new TextButton("Barbarian", storage.buttonStyle);
			
			prefixTable.defaults().size(200, 60);
			
			for (int i = 0; i < prefixBtns.length; i++) {	
				final String prefix = prefixBtns[i].getText().toString();
				prefixBtns[i].addListener(new ClickListener() {
	                @Override
	                public void clicked(InputEvent event, float x, float y) {
	                    handlePrefixClick(prefix);
	                }				
	            });
			}
			
			for(TextButton button : prefixBtns) {
				prefixTable.add(button).pad(0);
				prefixTable.row();
			}
			
			prefixTable.setPosition(vp.getWorldWidth() / 10f, vp.getWorldHeight() / 2.3f, Align.center);
			stage.addActor(prefixTable);
			
			// Gear table (non-raid)		
			for (int i = 0; i < gearBtns.length; i++) {
				gearBtns[i] = new Image(Inventory.inventorySlotTexture);
			}
			gearTable.defaults().size(100, 100);
			
			int index = 0;
			for(int x = 0; x < 6; x++) {
				for(int y = 0; y < 3; y++) {
					if (index < gearBtns.length) {
			            gearTable.add(gearBtns[index]).pad(0);
			            index++;
			        }
				}
				gearTable.row();
			}
			
			gearTable.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 2.3f, Align.center);
			stage.addActor(gearTable);
		}
		
		// Raid merchant
		else {	
			// Discounted prices table
			for (int i = 0; i < discountBtns.length; i++) {
				if(i == 0)
					discountBtns[i] = new Image(chooseItem(rand.nextInt(1, 7)));					
				else 
					discountBtns[i] = new Image(chooseItem(rand.nextInt(7, 21)));
					
				discountBtns[i].setName(itemName);
				
				final int cost = itemValue;
				final String item = discountBtns[i].getName().toString();
				
				discountBtns[i].addListener(new InputListener() {
	                @Override
	                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                	gearName.setText(item + "\nCost: " + cost / 2);
	                    if(cost / 2 == 0)
	                    	gearName.setText(item + "\nCost: " + cost);
	                    gearName.setAlignment(Align.center);
	                	gearName.setVisible(true);	                  	                    
	                    gearName.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 1.2f);
	                }

	                @Override
	                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    gearName.setVisible(false);
	                }
	            });
			}
			discountTable.defaults().size(150, 150);
			
			int index = 0;
			for(int y = 0; y < 3; y++) {
				if (index < discountBtns.length) {
					discountTable.add(discountBtns[index]).pad(0);
		            index++;
		        }
				
				final String item = discountBtns[y].getName().toString();
				final int loc = y;
				
				discountBtns[y].addListener(new ClickListener() {
	                @Override
	                public void clicked(InputEvent event, float x, float y) {
	                    handleRaidPurchases(true, item, loc);
	                    discountBtns[loc] = new Image(Inventory.inventorySlotTexture);
	                }				
	            });
			}
			
			discountTable.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 1.9f, Align.center);
			stage.addActor(discountTable);
			
			// Normal prices table
			for (int i = 0; i < standardBtns.length; i++) {
				if(i == 0)
					standardBtns[i] = new Image(chooseItem(rand.nextInt(1, 7)));
				else
					standardBtns[i] = new Image(chooseItem(rand.nextInt(7, 21)));
				standardBtns[i].setName(itemName);
				
				final int cost = itemValue;
				final String item = standardBtns[i].getName().toString();
				
				standardBtns[i].addListener(new InputListener() {
	                @Override
	                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
	                	gearName.setText(item + "\nCost: " + cost);
	                    gearName.setAlignment(Align.center);
	                	gearName.setVisible(true);	                  	                    
	                    gearName.setPosition(vp.getWorldWidth() / 2f, vp.getWorldHeight() / 1.2f);
	                }

	                @Override
	                public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
	                    gearName.setVisible(false);
	                }
	            });
			}
			standardTable.defaults().size(150, 150);
			
			index = 0;
			for(int y = 0; y < 3; y++) {
				if (index < standardBtns.length) {
					standardTable.add(standardBtns[index]).pad(0);
		            index++;
		        }
				
				final String item = standardBtns[y].getName().toString();
				final int loc = y;
				
				standardBtns[y].addListener(new ClickListener() {
	                @Override
	                public void clicked(InputEvent event, float x, float y) {
	                    handleRaidPurchases(false, item, loc);
	                    standardBtns[loc] = new Image(Inventory.inventorySlotTexture);
	                }				
	            });
			}
			
			standardTable.setPosition(vp.getWorldWidth() / 3f, vp.getWorldHeight() / 4.4f, Align.center);
			stage.addActor(standardTable);
		}
	}
	
	private Texture chooseItem(int x) {
		switch(x) {
		case 1:
			itemName = storage.healthPot.getItemName().toString();
			itemValue = storage.healthPot.getValue();
			return Inventory.healthPotionTexture;
		case 2:
			itemName = storage.bomb.getItemName().toString();
			itemValue = storage.bomb.getValue();
			return Inventory.bombTexture;
		case 3:
			itemName = storage.apBoost.getItemName().toString();
			itemValue = storage.apBoost.getValue();
			return Inventory.apTexture;
		case 4:
			itemName = storage.dpBoost.getItemName().toString();
			itemValue = storage.dpBoost.getValue();
			return Inventory.dpTexture;
		case 5:
			itemName = storage.hpBoost.getItemName().toString();
			itemValue = storage.hpBoost.getValue();
			return Inventory.hpTexture;
		case 6:
			itemName = storage.expBoost.getItemName().toString();
			itemValue = storage.expBoost.getValue();
			return Inventory.expTexture;
		case 7:
			itemName = storage.itemSwing.getItemName().toString();
			itemValue = storage.itemSwing.getValue();
			return Inventory.swingTexture;
		case 8:
			itemName = storage.itemRend.getItemName().toString();
			itemValue = storage.itemRend.getValue();
			return Inventory.rendTexture;
		case 9:
			itemName = storage.itemWhirlwind.getItemName().toString();
			itemValue = storage.itemWhirlwind.getValue();
			return Inventory.whirlwindTexture;
		case 10:
			itemName = storage.itemGroundBreaker.getItemName().toString();
			itemValue = storage.itemGroundBreaker.getValue();
			return Inventory.groundBreakerTexture;
		case 11:
			itemName = storage.itemBash.getItemName().toString();
			itemValue = storage.itemBash.getValue();
			return Inventory.bashTexture;
		case 12:
			itemName = storage.itemBarrier.getItemName().toString();
			itemValue = storage.itemBarrier.getValue();
			return Inventory.barrierTexture;
		case 13:
			itemName = storage.itemHarden.getItemName().toString();
			itemValue = storage.itemHarden.getValue();
			return Inventory.hardenTexture;
		case 14:
			itemName = storage.itemMend.getItemName().toString();
			itemValue = storage.itemMend.getValue();
			return Inventory.mendTexture;
		case 15:
			itemName = storage.itemHiltBash.getItemName().toString();
			itemValue = storage.itemHiltBash.getValue();
			return Inventory.hiltBashTexture;
		case 16:
			itemName = storage.itemBarbedArmor.getItemName().toString();
			itemValue = storage.itemBarbedArmor.getValue();
			return Inventory.barbedArmorTexture;
		case 17:
			itemName = storage.itemRiposte.getItemName().toString();
			itemValue = storage.itemRiposte.getValue();
			return Inventory.riposteTexture;
		case 18:
			itemName = storage.itemStab.getItemName().toString();
			itemValue = storage.itemStab.getValue();
			return Inventory.stabTexture;
		case 19:
			itemName = storage.itemDecapitate.getItemName().toString();
			itemValue = storage.itemDecapitate.getValue();
			return Inventory.decapitateTexture;
		case 20:
			itemName = storage.itemEnrage.getItemName().toString();
			itemValue = storage.itemEnrage.getValue();
			return Inventory.enrageTexture;
		default:
			return Inventory.inventorySlotTexture;
		}		
	}
	
	private void handleRaidPurchases(boolean discount, String itemName, int loc) {
		HashMap<String, Items> itemMap = new HashMap<>();
		itemMap.put("Health Potion", storage.healthPot);
		itemMap.put("Bomb", storage.bomb);
		itemMap.put("Experience Boost", storage.expBoost);
		itemMap.put("Attack Boost", storage.apBoost);
		itemMap.put("Defense Boost", storage.dpBoost);
		itemMap.put("Health Boost", storage.hpBoost);
		itemMap.put("Swing", storage.itemSwing);
		itemMap.put("Rend", storage.itemRend);
		itemMap.put("Whirlwind", storage.itemWhirlwind);
		itemMap.put("Ground Breaker", storage.itemGroundBreaker);
		itemMap.put("Bash", storage.itemBash);
		itemMap.put("Barrier", storage.itemBarrier);
		itemMap.put("Harden", storage.itemHarden);
		itemMap.put("Mend", storage.itemMend);
		itemMap.put("Hilt Bash", storage.itemHiltBash);
		itemMap.put("Barbed Armor", storage.itemBarbedArmor);
		itemMap.put("Enrage", storage.itemEnrage);
		itemMap.put("Riposte", storage.itemRiposte);
		itemMap.put("Stab", storage.itemStab);
		itemMap.put("Decapitate", storage.itemDecapitate);
		
		if (itemMap.containsKey(itemName)){
			Items item = itemMap.get(itemName);
			if(Player.getRaidCoins() >= item.getValue() && storage.getEquippedItems().size() < 14 && !discount) {
				Player.loseRaidCoins(item.getValue());
				standardBtns[loc].setColor(Color.DARK_GRAY);
				standardBtns[loc].setTouchable(Touchable.disabled);					
				storage.equippedItems(item, "Add");				
				itemTable.clear();
				createItemGrid();
				coins.setText("Coins: " + Player.getRaidCoins());
			}
			else if(Player.getRaidCoins() >= item.getValue() / 2 && storage.getEquippedItems().size() < 14 && discount) {
				Player.loseRaidCoins(item.getValue() / 2);
				discountBtns[loc].setTouchable(Touchable.disabled);
				discountBtns[loc].setColor(Color.DARK_GRAY);
				storage.equippedItems(item, "Add");				
				itemTable.clear();
				createItemGrid();
				coins.setText("Coins: " + Player.getRaidCoins());
			}
		}		
	}
	
	private void handlePrefixClick(String prefix) {
		loadPrefixGear(prefix);
	}
	
	private void handleInventoryClick(String itemName, int tr) {
		int finalCost = 0;
		
		HashMap<String, Armor> armorMap = new HashMap<>();
		armorMap.put("Iron Helmet", storage.ironHelmet);
		armorMap.put("Healthy Iron Helmet", storage.healthyIronHelmet);
		armorMap.put("Strong Iron Helmet", storage.strongIronHelmet);
		armorMap.put("Defensive Iron Helmet", storage.defensiveIronHelmet);
		armorMap.put("Iron Chest", storage.ironChest);		
		armorMap.put("Healthy Iron Chest", storage.healthyIronChest);	
		armorMap.put("Strong Iron Chest", storage.strongIronChest);	
		armorMap.put("Defensive Iron Chest", storage.defensiveIronChest);	
		armorMap.put("Iron Boots", storage.ironBoots);		
		armorMap.put("Healthy Iron Boots", storage.healthyIronBoots);	
		armorMap.put("Strong Iron Boots", storage.strongIronBoots);	
		armorMap.put("Defensive Iron Boots", storage.defensiveIronBoots);
		armorMap.put("Healthy Bronze Helmet", storage.healthyBronzeHelmet);
		armorMap.put("Strong Bronze Helmet", storage.strongBronzeHelmet);
		armorMap.put("Defensive Bronze Helmet", storage.defensiveBronzeHelmet);	
		armorMap.put("Healthy Bronze Chest", storage.healthyBronzeChest);	
		armorMap.put("Strong Bronze Chest", storage.strongBronzeChest);	
		armorMap.put("Defensive Bronze Chest", storage.defensiveBronzeChest);		
		armorMap.put("Healthy Bronze Boots", storage.healthyBronzeBoots);	
		armorMap.put("Strong Bronze Boots", storage.strongBronzeBoots);	
		armorMap.put("Defensive Bronze Boots", storage.defensiveBronzeBoots);
		armorMap.put("Healthy Steel Helmet", storage.healthySteelHelmet);
		armorMap.put("Strong Steel Helmet", storage.strongSteelHelmet);
		armorMap.put("Defensive Steel Helmet", storage.defensiveSteelHelmet);	
		armorMap.put("Healthy Steel Chest", storage.healthySteelChest);	
		armorMap.put("Strong Steel Chest", storage.strongSteelChest);	
		armorMap.put("Defensive Steel Chest", storage.defensiveSteelChest);		
		armorMap.put("Healthy Steel Boots", storage.healthySteelBoots);	
		armorMap.put("Strong Steel Boots", storage.strongSteelBoots);	
		armorMap.put("Defensive Steel Boots", storage.defensiveSteelBoots);
		
		HashMap<String, Weapons> weaponMap = new HashMap<>();
		weaponMap.put("Wooden Axe", storage.woodenAxe);
		weaponMap.put("Wooden Greataxe", storage.woodenGA);
		weaponMap.put("Wooden Shield", storage.woodenShield);
		weaponMap.put("Healthy Iron Axe", storage.healthyIronAxe);
		weaponMap.put("Strong Iron Axe", storage.strongIronAxe);
		weaponMap.put("Defensive Iron Axe", storage.defensiveIronAxe);
		weaponMap.put("Healthy Iron Greataxe", storage.healthyIronGA);
		weaponMap.put("Strong Iron Greataxe", storage.strongIronGA);
		weaponMap.put("Defensive Iron Greataxe", storage.defensiveIronGA);
		weaponMap.put("Healthy Iron Shield", storage.healthyIronShield);
		weaponMap.put("Strong Iron Shield", storage.strongIronShield);
		weaponMap.put("Defensive Iron Shield", storage.defensiveIronShield);
		weaponMap.put("Healthy Bronze Axe", storage.healthyBronzeAxe);
		weaponMap.put("Strong Bronze Axe", storage.strongBronzeAxe);
		weaponMap.put("Defensive Bronze Axe", storage.defensiveBronzeAxe);
		weaponMap.put("Healthy Bronze Greataxe", storage.healthyBronzeGA);
		weaponMap.put("Strong Bronze Greataxe", storage.strongBronzeGA);
		weaponMap.put("Defensive Bronze Greataxe", storage.defensiveBronzeGA);
		weaponMap.put("Healthy Bronze Shield", storage.healthyBronzeShield);
		weaponMap.put("Strong Bronze Shield", storage.strongBronzeShield);
		weaponMap.put("Defensive Bronze Shield", storage.defensiveBronzeShield);
		weaponMap.put("Healthy Steel Axe", storage.healthySteelAxe);
		weaponMap.put("Strong Steel Axe", storage.strongSteelAxe);
		weaponMap.put("Defensive Steel Axe", storage.defensiveSteelAxe);
		weaponMap.put("Healthy Steel Greataxe", storage.healthySteelGA);
		weaponMap.put("Strong Steel Greataxe", storage.strongSteelGA);
		weaponMap.put("Defensive Steel Greataxe", storage.defensiveSteelGA);
		weaponMap.put("Healthy Steel Shield", storage.healthySteelShield);
		weaponMap.put("Strong Steel Shield", storage.strongSteelShield);
		weaponMap.put("Defensive Steel Shield", storage.defensiveSteelShield);
		
		HashMap<String, Items> itemMap = new HashMap<>();
		itemMap.put("Health Potion", storage.healthPot);
		itemMap.put("Bomb", storage.bomb);
		itemMap.put("Experience Boost", storage.expBoost);
		itemMap.put("Attack Boost", storage.apBoost);
		itemMap.put("Defense Boost", storage.dpBoost);
		itemMap.put("Health Boost", storage.hpBoost);
		itemMap.put("Swing", storage.itemSwing);
		itemMap.put("Rend", storage.itemRend);
		itemMap.put("Whirlwind", storage.itemWhirlwind);
		itemMap.put("Ground Breaker", storage.itemGroundBreaker);
		itemMap.put("Bash", storage.itemBash);
		itemMap.put("Barrier", storage.itemBarrier);
		itemMap.put("Harden", storage.itemHarden);
		itemMap.put("Mend", storage.itemMend);
		itemMap.put("Hilt Bash", storage.itemHiltBash);
		itemMap.put("Barbed Armor", storage.itemBarbedArmor);
		itemMap.put("Enrage", storage.itemEnrage);
		itemMap.put("Riposte", storage.itemRiposte);
		itemMap.put("Stab", storage.itemStab);
		itemMap.put("Decapitate", storage.itemDecapitate);
		
		if(tr == 1) {
			if (armorMap.containsKey(itemName)) {
			    Armor armor = armorMap.get(itemName);
			    finalCost = armor.getValue() / 2;
			    if (finalCost == 0)
			    	finalCost = 1;
			    Player.gainCoins(finalCost);
			    if (itemName.endsWith("Helmet") || itemName.endsWith("Chest") || itemName.endsWith("Boots"))
			        storage.inventoryArmor(armor, "Remove");		    
			}
			else if (weaponMap.containsKey(itemName)) {
				Weapons weapon = weaponMap.get(itemName);
				finalCost = weapon.getValue() / 2;
			    if (finalCost == 0)
			    	finalCost = 1;
			    Player.gainCoins(finalCost);
				if (itemName.endsWith("Axe") || itemName.endsWith("Greataxe") || itemName.endsWith("Shield"))
			        storage.inventoryWeapons(weapon, "Remove");
			}
			else if (itemMap.containsKey(itemName)){
				Items item = itemMap.get(itemName);
				finalCost = item.getValue() / 2;
			    if (finalCost == 0)
			    	finalCost = 1;
				if(!raid) {
					Player.gainCoins(finalCost);
					storage.inventoryItems(item, "Remove");				
				}				
				else {
					Player.gainRaidCoins(item.getValue() / 2);
					storage.equippedItems(item, "Remove");
				}			
			}
		}
		else {
			if (armorMap.containsKey(itemName)) {
			    Armor armor = armorMap.get(itemName);
			    if(Player.getCoins() >= armor.getValue()) {
			    	Player.loseCoins(armor.getValue());
				    if (itemName.endsWith("Helmet") || itemName.endsWith("Chest") || itemName.endsWith("Boots"))
				        storage.inventoryArmor(armor, "Add");
			    }		    		    
			}
			else if (weaponMap.containsKey(itemName)) {
				Weapons weapon = weaponMap.get(itemName);
				if(Player.getCoins() >= weapon.getValue()) {
					Player.loseCoins(weapon.getValue());
					if (itemName.endsWith("Axe") || itemName.endsWith("Greataxe") || itemName.endsWith("Shield"))
				        storage.inventoryWeapons(weapon, "Add");
				}				
			}
			else if (itemMap.containsKey(itemName)){
				Items item = itemMap.get(itemName);
				if(!raid) {
					if(Player.getCoins() >= item.getValue()) {
						Player.loseCoins(item.getValue());
						storage.inventoryItems(item, "Add");
					}
				}
				else {
					if(Player.getRaidCoins() >= item.getValue()) {
						Player.loseCoins(item.getValue());
						storage.equippedItems(item, "Add");
					}
				}			
			}
		}
		
		if(!raid) {
			coins.setText("Coins: " + Player.getCoins());
			inventoryTable.clear();
		    createInventoryGrid();
		}
		else {
			coins.setText("Coins: " + Player.getRaidCoins());
			itemTable.clear();
			createItemGrid();
		}
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
		
	}

}
