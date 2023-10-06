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
		
		jsonString = json.toJson(saveData);		
		FileHandle file = Gdx.files.local("saveData.json");
		file.writeString(jsonString, false);
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
	    	}
	    }
	}
}

