package com.onionscape.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import player.Player;
import scenes.BerserkerSkillTree;
import scenes.Inventory;
import storage.Armor;
import storage.Items;
import storage.Storage;
import storage.Weapons;

public class SaveData {	
	Json json = new Json();
	String jsonString;
	private Storage storage;
	
	public int maxHP;
	public int strength;
	public int weaponDmg;
	public int dmgResist;
	public int coins;
	public int level;
	public int exp;
	public int levelCap;
	public int skillPoints;
	public int twoHandStr, oneHandStr;
	public int weaponState;
	public int shieldDP, helmetDP, chestDP, bootsDP;
	public List<Weapons> playerWeapons = new ArrayList<>();
	public List<Armor> playerArmor = new ArrayList<>();
	public List<Items> playerItems = new ArrayList<>();
	public List<Weapons> equippedWeapons = new ArrayList<>();
	public List<Armor> equippedArmor = new ArrayList<>();
	public List<Items> equippedItems = new ArrayList<>();
	public int[] bonusHP = {0, 0, 0, 0, 0};
	public int[] bonusAP = {0, 0, 0, 0, 0};
	public int[] bonusDP = {0, 0, 0, 0, 0};
	public float musicVol;
	public float sfxVol;
	
	public int twoHMastery;
	public int oneHMastery;
	public int thickSkin;
	public int weaponMastery;
	public int blockAura;
	public int eleResist;
	public int rendMastery;
	public int lifeSteal;
	public int poisonRend;
	public int ironSkin;
	public int bulkUp;
	public int sharpenWeapons;
	public int luckyStrike;
	public int blockEfficiency;
	public int bludgeonEnemy;
	public int doubleSwing;
	public int thorns;
	public int healthy;
	public int skillPointsUsed;
	public int twoHandStr2;
	public int oneHandStr2;
	public int dmgResist2;
	public int weaponDmg2;
	public int strength2;
	public int maxHP2;
	
	public static boolean loaded = true;
	
	public SaveData() {
		storage = Storage.getInstance();
	}

	public void saveGame() {
		SaveData saveData = new SaveData();
		
		saveData.maxHP = Player.getMaxHP();
		saveData.strength = Player.getStrength();		
		saveData.weaponDmg = Player.getWeaponDmg();
		saveData.dmgResist = Player.getDmgResist();
		saveData.coins = Player.getCoins();
		saveData.level = Player.getLevel();
		saveData.exp = Player.getExp();
		saveData.levelCap = Player.getLevelCap();
		saveData.skillPoints = Player.getSkillPoints();
		saveData.twoHandStr = Player.getTwoHandStr();
		saveData.oneHandStr = Player.getOneHandStr();
		saveData.weaponState = Player.weaponState;
		saveData.shieldDP = Inventory.getShieldDP();
		saveData.helmetDP = Inventory.getHelmetDP();
		saveData.chestDP = Inventory.getChestDP();
		saveData.bootsDP = Inventory.getBootsDP();
		
		saveData.playerArmor = storage.getPlayerArmor();
		saveData.playerWeapons = storage.getPlayerWeapons();
		saveData.playerItems = storage.getPlayerItems();
		saveData.equippedArmor = storage.getEquippedArmor();
		saveData.equippedWeapons = storage.getEquippedWeapons();
		saveData.equippedItems = storage.getEquippedItems();
		saveData.bonusAP = storage.getBonusAP();
		saveData.bonusHP = storage.getBonusHP();
		saveData.bonusDP = storage.getBonusDP();
		
		saveData.twoHMastery = BerserkerSkillTree.twoHMastery;
		saveData.oneHMastery = BerserkerSkillTree.oneHMastery;
		saveData.thickSkin = BerserkerSkillTree.thickSkin;
		saveData.weaponMastery = BerserkerSkillTree.weaponMastery;
		saveData.blockAura = BerserkerSkillTree.blockAura;
		saveData.eleResist = BerserkerSkillTree.eleResist;
		saveData.rendMastery = BerserkerSkillTree.rendMastery;
		saveData.lifeSteal = BerserkerSkillTree.lifeSteal;
		saveData.poisonRend = BerserkerSkillTree.poisonRend;
		saveData.ironSkin = BerserkerSkillTree.ironSkin;
		saveData.bulkUp = BerserkerSkillTree.bulkUp;
		saveData.healthy = BerserkerSkillTree.healthy;
		saveData.sharpenWeapons = BerserkerSkillTree.sharpenWeapons;
		saveData.luckyStrike = BerserkerSkillTree.luckyStrike;
		saveData.blockEfficiency = BerserkerSkillTree.blockEfficiency;
		saveData.bludgeonEnemy = BerserkerSkillTree.bludgeonEnemy;
		saveData.doubleSwing = BerserkerSkillTree.doubleSwing;
		saveData.thorns = BerserkerSkillTree.thorns;
		saveData.skillPointsUsed = BerserkerSkillTree.skillPointsUsed;
		saveData.twoHandStr2 = BerserkerSkillTree.twoHandStr;
		saveData.oneHandStr2 = BerserkerSkillTree.oneHandStr;
		saveData.dmgResist2 = BerserkerSkillTree.dmgResist;
		saveData.weaponDmg2 = BerserkerSkillTree.weaponDmg;
		saveData.strength2 = BerserkerSkillTree.strength;
		saveData.maxHP2 = BerserkerSkillTree.maxHP;
		
		saveData.musicVol = MusicManager.musicVol;
		saveData.sfxVol = MusicManager.sfxVol;
		
		jsonString = json.toJson(saveData);		
		FileHandle file = Gdx.files.local("saveData.json");
		file.writeString(jsonString, false);
		System.out.println("Saved the game state");
	}
	
	public void loadGame() {		
		SaveData loadedData = null;
		FileHandle file = Gdx.files.local("saveData.json");
		if (file.exists()) {
		    String readJson = file.readString();
		    loadedData = json.fromJson(SaveData.class, readJson);
		}
		
		if (loadedData != null) {
		    Player.setMaxHP(loadedData.maxHP);
		    Player.setStrength(loadedData.strength);
		    Player.setWeaponDmg(loadedData.weaponDmg);
		    Player.setDmgResist(loadedData.dmgResist);
		    Player.setCoins(loadedData.coins);
		    Player.setLevel(loadedData.level);
		    Player.setExp(loadedData.exp);
		    Player.setSkillPoints(loadedData.skillPoints);
		    Player.setTwoHandStr(loadedData.twoHandStr);
		    Player.setOneHandStr(loadedData.oneHandStr);
		    Player.weaponState = loadedData.weaponState;
		    Inventory.setShieldDP(loadedData.shieldDP);
		    Inventory.setHelmetDP(loadedData.helmetDP);
		    Inventory.setChestDP(loadedData.chestDP);
		    Inventory.setBootsDP(loadedData.bootsDP);
		    
		    loadArmor(loadedData.playerArmor, "Bag");
		    loadWeapons(loadedData.playerWeapons, "Bag");	
		    loadItems(loadedData.playerItems, "Bag");
		    loadArmor(loadedData.equippedArmor, "Character");
		    loadWeapons(loadedData.equippedWeapons, "Character");	
		    loadItems(loadedData.equippedItems, "Character");
		    loadBonusStats(loadedData.bonusAP, "AP");
		    loadBonusStats(loadedData.bonusHP, "HP");
		    loadBonusStats(loadedData.bonusDP, "DP");
		    
		    BerserkerSkillTree.twoHMastery = loadedData.twoHMastery;
		    BerserkerSkillTree.oneHMastery = loadedData.oneHMastery;
		    BerserkerSkillTree.thickSkin = loadedData.thickSkin;
		    BerserkerSkillTree.weaponMastery = loadedData.weaponMastery;
		    BerserkerSkillTree.blockAura = loadedData.blockAura;
		    BerserkerSkillTree.eleResist = loadedData.eleResist;
		    BerserkerSkillTree.rendMastery = loadedData.rendMastery;
		    BerserkerSkillTree.lifeSteal = loadedData.lifeSteal;
		    BerserkerSkillTree.poisonRend = loadedData.poisonRend;
		    BerserkerSkillTree.ironSkin = loadedData.ironSkin;
		    BerserkerSkillTree.bulkUp = loadedData.bulkUp;
		    BerserkerSkillTree.healthy = loadedData.healthy;
		    BerserkerSkillTree.sharpenWeapons = loadedData.sharpenWeapons;
		    BerserkerSkillTree.luckyStrike = loadedData.luckyStrike;
		    BerserkerSkillTree.blockEfficiency = loadedData.blockEfficiency;
		    BerserkerSkillTree.bludgeonEnemy = loadedData.bludgeonEnemy;
		    BerserkerSkillTree.doubleSwing = loadedData.doubleSwing;
		    BerserkerSkillTree.thorns = loadedData.thorns;
		    BerserkerSkillTree.skillPointsUsed = loadedData.skillPointsUsed;
		    BerserkerSkillTree.twoHandStr = loadedData.twoHandStr2;
		    BerserkerSkillTree.oneHandStr = loadedData.oneHandStr2;
		    BerserkerSkillTree.dmgResist = loadedData.dmgResist2;
		    BerserkerSkillTree.weaponDmg = loadedData.weaponDmg2;
		    BerserkerSkillTree.strength = loadedData.strength2;
		    BerserkerSkillTree.maxHP = loadedData.maxHP2;
		    
		    MusicManager.musicVol = loadedData.musicVol;
		    MusicManager.sfxVol = loadedData.sfxVol;
		    MusicManager.getInstance().changeVolume();
		    
		    System.out.println("Loaded the game state");
		}
	}
	
	private void loadBonusStats(int[] bonus, String stat) {
		switch(stat) {
		case "AP":
			for(int i = 0; i < 5; i++)
				storage.setBonusAP(i, bonus[i]);
			break;
		case "HP":
			for(int i = 0; i < 5; i++)
				storage.setBonusHP(i, bonus[i]);	
			break;
		case "DP":
			for(int i = 0; i < 5; i++)
				storage.setBonusDP(i, bonus[i]);
			break;
		}
	}
	
	private void loadArmor(List<Armor> playerArmor, String inventory) {
		if(inventory.equals("Bag"))
			storage.inventoryArmor(null, "Clear");
		else
			storage.equippedArmor(null, "Clear");
		
		for(Armor armor : playerArmor) {
	    	switch(armor.getArmorName()) {
	    	case "Healthy Iron Helmet":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.healthyIronHelmet, "Add");	
	    		else
	    			storage.equippedArmor(storage.healthyIronHelmet, "Add");
    			break;
	    	case "Strong Iron Helmet":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.strongIronHelmet, "Add");	
	    		else
	    			storage.equippedArmor(storage.strongIronHelmet, "Add");
    			break;
	    	case "Defensive Iron Helmet":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.defensiveIronHelmet, "Add");	
	    		else
	    			storage.equippedArmor(storage.defensiveIronHelmet, "Add");
    			break;
	    	case "Healthy Iron Chest":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.healthyIronChest, "Add");	
	    		else
	    			storage.equippedArmor(storage.healthyIronChest, "Add");
    			break;
	    	case "Strong Iron Chest":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.strongIronChest, "Add");	
	    		else
	    			storage.equippedArmor(storage.strongIronChest, "Add");
    			break;
	    	case "Defensive Iron Chest":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.defensiveIronChest, "Add");	
	    		else
	    			storage.equippedArmor(storage.defensiveIronChest, "Add");
    			break;
	    	case "Healthy Iron Boots":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.healthyIronBoots, "Add");	
	    		else
	    			storage.equippedArmor(storage.healthyIronBoots, "Add");
    			break;
	    	case "Strong Iron Boots":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.strongIronBoots, "Add");	
	    		else
	    			storage.equippedArmor(storage.strongIronBoots, "Add");
    			break;
	    	case "Defensive Iron Boots":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.defensiveIronBoots, "Add");	
	    		else
	    			storage.equippedArmor(storage.defensiveIronBoots, "Add");
    			break;
	    	case "Healthy Bronze Helmet":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.healthyBronzeHelmet, "Add");	
	    		else
	    			storage.equippedArmor(storage.healthyBronzeHelmet, "Add");
    			break;
	    	case "Strong Bronze Helmet":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.strongBronzeHelmet, "Add");	
	    		else
	    			storage.equippedArmor(storage.strongBronzeHelmet, "Add");
    			break;
	    	case "Defensive Bronze Helmet":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.defensiveBronzeHelmet, "Add");	
	    		else
	    			storage.equippedArmor(storage.defensiveBronzeHelmet, "Add");
    			break;
	    	case "Healthy Bronze Chest":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.healthyBronzeChest, "Add");	
	    		else
	    			storage.equippedArmor(storage.healthyBronzeChest, "Add");
    			break;
	    	case "Strong Bronze Chest":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.strongBronzeChest, "Add");	
	    		else
	    			storage.equippedArmor(storage.strongBronzeChest, "Add");
    			break;
	    	case "Defensive Bronze Chest":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.defensiveBronzeChest, "Add");	
	    		else
	    			storage.equippedArmor(storage.defensiveBronzeChest, "Add");
    			break;
	    	case "Healthy Bronze Boots":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.healthyBronzeBoots, "Add");	
	    		else
	    			storage.equippedArmor(storage.healthyBronzeBoots, "Add");
    			break;
	    	case "Strong Bronze Boots":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.strongBronzeBoots, "Add");	
	    		else
	    			storage.equippedArmor(storage.strongBronzeBoots, "Add");
    			break;
	    	case "Defensive Bronze Boots":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.defensiveBronzeBoots, "Add");	
	    		else
	    			storage.equippedArmor(storage.defensiveBronzeBoots, "Add");
    			break;
	    	case "Healthy Steel Helmet":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.healthySteelHelmet, "Add");	
	    		else
	    			storage.equippedArmor(storage.healthySteelHelmet, "Add");
    			break;
	    	case "Strong Steel Helmet":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.strongSteelHelmet, "Add");	
	    		else
	    			storage.equippedArmor(storage.strongSteelHelmet, "Add");
    			break;
	    	case "Defensive Steel Helmet":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.defensiveSteelHelmet, "Add");	
	    		else
	    			storage.equippedArmor(storage.defensiveSteelHelmet, "Add");
    			break;
	    	case "Healthy Steel Chest":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.healthySteelChest, "Add");	
	    		else
	    			storage.equippedArmor(storage.healthySteelChest, "Add");
    			break;
	    	case "Strong Steel Chest":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.strongSteelChest, "Add");	
	    		else
	    			storage.equippedArmor(storage.strongSteelChest, "Add");
    			break;
	    	case "Defensive Steel Chest":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.defensiveSteelChest, "Add");	
	    		else
	    			storage.equippedArmor(storage.defensiveSteelChest, "Add");
    			break;
	    	case "Healthy Steel Boots":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.healthySteelBoots, "Add");	
	    		else
	    			storage.equippedArmor(storage.healthySteelBoots, "Add");
    			break;
	    	case "Strong Steel Boots":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.strongSteelBoots, "Add");	
	    		else
	    			storage.equippedArmor(storage.strongSteelBoots, "Add");
    			break;
	    	case "Defensive Steel Boots":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryArmor(storage.defensiveSteelBoots, "Add");	
	    		else
	    			storage.equippedArmor(storage.defensiveSteelBoots, "Add");
    			break;
	    	}
	    }
	}
	
	private void loadWeapons(List<Weapons> playerWeapons, String inventory) {
		if(inventory.equals("Bag"))
			storage.inventoryWeapons(null, "Clear");
		else
			storage.equippedWeapons(null, "Clear");
		
		for(Weapons weapon : playerWeapons) {
	    	switch(weapon.getWeaponName()) {
	    	case "Wooden Greataxe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.woodenGA, "Add");	    
	    		else
	    			storage.equippedWeapons(storage.woodenGA, "Add");	
    			break;
	    	case "Healthy Iron Greataxe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.healthyIronGA, "Add");	    
	    		else
	    			storage.equippedWeapons(storage.healthyIronGA, "Add");	
    			break;
	    	case "Strong Iron Greataxe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.strongIronGA, "Add");	    
	    		else
	    			storage.equippedWeapons(storage.strongIronGA, "Add");	
    			break;
	    	case "Defensive Iron Greataxe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.defensiveIronGA, "Add");	    
	    		else
	    			storage.equippedWeapons(storage.defensiveIronGA, "Add");	
    			break;
	    	case "Healthy Iron Axe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.healthyIronAxe, "Add");
	    		else
	    			storage.equippedWeapons(storage.healthyIronAxe, "Add");
	    		break;
	    	case "Strong Iron Axe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.strongIronAxe, "Add");
	    		else
	    			storage.equippedWeapons(storage.strongIronAxe, "Add");
	    		break;
	    	case "Defensive Iron Axe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.defensiveIronAxe, "Add");
	    		else
	    			storage.equippedWeapons(storage.defensiveIronAxe, "Add");
	    		break;
	    	case "Wooden Axe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.woodenAxe, "Add");
	    		else
	    			storage.equippedWeapons(storage.woodenAxe, "Add");
	    		break;
	    	case "Wooden Shield":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.woodenShield, "Add");	
	    		else
	    			storage.equippedWeapons(storage.woodenShield, "Add");
	    		break;
	    	case "Healthy Iron Shield":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.healthyIronShield, "Add");
	    		else
	    			storage.equippedWeapons(storage.healthyIronShield, "Add");
	    		break;
	    	case "Strong Iron Shield":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.strongIronShield, "Add");
	    		else
	    			storage.equippedWeapons(storage.strongIronShield, "Add");
	    		break;
	    	case "Defensive Iron Shield":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.defensiveIronShield, "Add");
	    		else
	    			storage.equippedWeapons(storage.defensiveIronShield, "Add");
	    		break;
	    	case "Healthy Bronze Greataxe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.healthyBronzeGA, "Add");	    
	    		else
	    			storage.equippedWeapons(storage.healthyBronzeGA, "Add");	
    			break;
	    	case "Strong Bronze Greataxe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.strongBronzeGA, "Add");	    
	    		else
	    			storage.equippedWeapons(storage.strongBronzeGA, "Add");	
    			break;
	    	case "Defensive Bronze Greataxe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.defensiveBronzeGA, "Add");	    
	    		else
	    			storage.equippedWeapons(storage.defensiveBronzeGA, "Add");	
    			break;
	    	case "Healthy Bronze Axe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.healthyBronzeAxe, "Add");
	    		else
	    			storage.equippedWeapons(storage.healthyBronzeAxe, "Add");
	    		break;
	    	case "Strong Bronze Axe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.strongBronzeAxe, "Add");
	    		else
	    			storage.equippedWeapons(storage.strongBronzeAxe, "Add");
	    		break;
	    	case "Defensive Bronze Axe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.defensiveBronzeAxe, "Add");
	    		else
	    			storage.equippedWeapons(storage.defensiveBronzeAxe, "Add");
	    		break;	    
	    	case "Healthy Bronze Shield":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.healthyBronzeShield, "Add");
	    		else
	    			storage.equippedWeapons(storage.healthyBronzeShield, "Add");
	    		break;
	    	case "Strong Bronze Shield":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.strongBronzeShield, "Add");
	    		else
	    			storage.equippedWeapons(storage.strongBronzeShield, "Add");
	    		break;
	    	case "Defensive Bronze Shield":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.defensiveBronzeShield, "Add");
	    		else
	    			storage.equippedWeapons(storage.defensiveBronzeShield, "Add");
	    		break;
	    	case "Healthy Steel Greataxe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.healthySteelGA, "Add");	    
	    		else
	    			storage.equippedWeapons(storage.healthySteelGA, "Add");	
    			break;
	    	case "Strong Steel Greataxe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.strongSteelGA, "Add");	    
	    		else
	    			storage.equippedWeapons(storage.strongSteelGA, "Add");	
    			break;
	    	case "Defensive Steel Greataxe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.defensiveSteelGA, "Add");	    
	    		else
	    			storage.equippedWeapons(storage.defensiveSteelGA, "Add");	
    			break;
	    	case "Healthy Steel Axe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.healthySteelAxe, "Add");
	    		else
	    			storage.equippedWeapons(storage.healthySteelAxe, "Add");
	    		break;
	    	case "Strong Steel Axe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.strongSteelAxe, "Add");
	    		else
	    			storage.equippedWeapons(storage.strongSteelAxe, "Add");
	    		break;
	    	case "Defensive Steel Axe":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.defensiveSteelAxe, "Add");
	    		else
	    			storage.equippedWeapons(storage.defensiveSteelAxe, "Add");
	    		break;	    
	    	case "Healthy Steel Shield":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.healthySteelShield, "Add");
	    		else
	    			storage.equippedWeapons(storage.healthySteelShield, "Add");
	    		break;
	    	case "Strong Steel Shield":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.strongSteelShield, "Add");
	    		else
	    			storage.equippedWeapons(storage.strongSteelShield, "Add");
	    		break;
	    	case "Defensive Steel Shield":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryWeapons(storage.defensiveSteelShield, "Add");
	    		else
	    			storage.equippedWeapons(storage.defensiveSteelShield, "Add");
	    		break;
	    	}
	    }
	}
	
	private void loadItems(List<Items> playerItems, String inventory) {
		if(inventory.equals("Bag"))
			storage.inventoryItems(null, "Clear");
		else
			storage.equippedItems(null, "Clear");
		
		for(Items item : playerItems) {
	    	switch(item.getItemName()) {
	    	case "Health Potion":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.healthPot, "Add");	 
	    		else
	    			storage.equippedItems(storage.healthPot, "Add");
    			break;
	    	case "Bomb":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.bomb, "Add");
	    		else
	    			storage.equippedItems(storage.bomb, "Add");
	    		break;
	    	case "Attack Boost":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.apBoost, "Add");
	    		else
	    			storage.equippedItems(storage.apBoost, "Add");
	    		break;
	    	case "Defense Boost":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.dpBoost, "Add");
	    		else
	    			storage.equippedItems(storage.dpBoost, "Add");
	    		break;
	    	case "Health Boost":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.hpBoost, "Add");
	    		else
	    			storage.equippedItems(storage.hpBoost, "Add");
	    		break;
	    	case "Experience Boost":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.expBoost, "Add");
	    		else
	    			storage.equippedItems(storage.expBoost, "Add");
	    		break;
	    	case "Swing":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemSwing, "Add");
	    		else
	    			storage.equippedItems(storage.itemSwing, "Add");
	    		break;
	    	case "Rend":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemRend, "Add");
	    		else
	    			storage.equippedItems(storage.itemRend, "Add");
	    		break;
	    	case "Whirlwind":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemWhirlwind, "Add");
	    		else
	    			storage.equippedItems(storage.itemWhirlwind, "Add");
	    		break;
	    	case "Ground Breaker":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemGroundBreaker, "Add");
	    		else
	    			storage.equippedItems(storage.itemGroundBreaker, "Add");
	    		break;
	    	case "Bash":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemBash, "Add");
	    		else
	    			storage.equippedItems(storage.itemBash, "Add");
	    		break;
	    	case "Barrier":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemBarrier, "Add");
	    		else
	    			storage.equippedItems(storage.itemBarrier, "Add");
	    		break;
	    	case "Harden":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemHarden, "Add");
	    		else
	    			storage.equippedItems(storage.itemHarden, "Add");
	    		break;
	    	case "Mend":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemMend, "Add");
	    		else
	    			storage.equippedItems(storage.itemMend, "Add");
	    		break;
	    	case "Hilt Bash":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemHiltBash, "Add");
	    		else
	    			storage.equippedItems(storage.itemHiltBash, "Add");
	    		break;
	    	case "Barbed Armor":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemBarbedArmor, "Add");
	    		else
	    			storage.equippedItems(storage.itemBarbedArmor, "Add");
	    		break;
	    	case "Enrage":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemEnrage, "Add");
	    		else
	    			storage.equippedItems(storage.itemEnrage, "Add");
	    		break;
	    	case "Riposte":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemRiposte, "Add");
	    		else
	    			storage.equippedItems(storage.itemRiposte, "Add");
	    		break;
	    	case "Stab":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemStab, "Add");
	    		else
	    			storage.equippedItems(storage.itemStab, "Add");
	    		break;
	    	case "Decapitate":
	    		if(inventory.equals("Bag"))
	    			storage.inventoryItems(storage.itemDecapitate, "Add");
	    		else
	    			storage.equippedItems(storage.itemDecapitate, "Add");
	    		break;
	    	}
	    }
	}
}