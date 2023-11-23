package storage;

public abstract class Armor {
	private int defense, ID, value, bonusStat;
	private String armorName, armorSlot;
	
	public int getDefense() {
		return defense;
	}
	public void setDefense(int defense) {
		this.defense = defense;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getArmorName() {
		return armorName;
	}
	public void setArmorName(String armorName) {
		this.armorName = armorName;
	}
	public String getArmorSlot() {
		return armorSlot;
	}
	public void setArmorSlot(String armorSlot) {
		this.armorSlot = armorSlot;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int amount) {
		this.value = amount;
	}
	public int getBonusStat() {
		return bonusStat;
	}
	public void setBonusStat(int bonusStat) {
		this.bonusStat = bonusStat;
	}
}

class IronHelmet extends Armor{
	public IronHelmet() {
		setArmorName("Iron Helmet");
		setArmorSlot("Head");
		setDefense(4);
		setValue(4);
		setID(0);
	}
}

class HealthyIronHelmet extends Armor{
	public HealthyIronHelmet() {
		setArmorName("Healthy Iron Helmet");
		setArmorSlot("Head");
		setBonusStat(2);
		setDefense(4);
		setValue(5);
		setID(1);
	}
}

class StrongIronHelmet extends Armor{
	public StrongIronHelmet() {
		setArmorName("Strong Iron Helmet");
		setArmorSlot("Head");
		setBonusStat(1);
		setDefense(4);
		setValue(5);
		setID(1);
	}
}

class DefensiveIronHelmet extends Armor{
	public DefensiveIronHelmet() {
		setArmorName("Defensive Iron Helmet");
		setArmorSlot("Head");
		setBonusStat(1);
		setDefense(4);
		setValue(5);
		setID(1);
	}
}

class IronChest extends Armor{
	public IronChest() {
		setArmorName("Iron Chest");
		setArmorSlot("Chest");
		setDefense(6);
		setValue(8);
		setID(2);
	}
}

class HealthyIronChest extends Armor{
	public HealthyIronChest() {
		setArmorName("Healthy Iron Chest");
		setArmorSlot("Chest");
		setBonusStat(3);
		setDefense(6);
		setValue(8);
		setID(2);
	}
}

class StrongIronChest extends Armor{
	public StrongIronChest() {
		setArmorName("Strong Iron Chest");
		setArmorSlot("Chest");
		setBonusStat(1);
		setDefense(6);
		setValue(8);
		setID(2);
	}
}

class DefensiveIronChest extends Armor{
	public DefensiveIronChest() {
		setArmorName("Defensive Iron Chest");
		setArmorSlot("Chest");
		setBonusStat(1);
		setDefense(6);
		setValue(8);
		setID(2);
	}
}

class IronBoots extends Armor{
	public IronBoots() {
		setArmorName("Iron Boots");
		setArmorSlot("Feet");
		setDefense(4);
		setValue(5);
		setID(3);
	}
}

class HealthyIronBoots extends Armor{
	public HealthyIronBoots() {
		setArmorName("Healthy Iron Boots");
		setArmorSlot("Feet");
		setBonusStat(2);
		setDefense(4);
		setValue(5);
		setID(3);
	}
}

class StrongIronBoots extends Armor{
	public StrongIronBoots() {
		setArmorName("Strong Iron Boots");
		setArmorSlot("Feet");
		setBonusStat(1);
		setDefense(4);
		setValue(5);
		setID(3);
	}
}

class DefensiveIronBoots extends Armor{
	public DefensiveIronBoots() {
		setArmorName("Defensive Iron Boots");
		setArmorSlot("Feet");
		setBonusStat(1);
		setDefense(4);
		setValue(5);
		setID(3);
	}
}

class HealthyBronzeHelmet extends Armor{
	public HealthyBronzeHelmet() {
		setArmorName("Healthy Bronze Helmet");
		setArmorSlot("Head");
		setBonusStat(3);
		setDefense(5);
		setValue(10);
		setID(4);
	}
}

class StrongBronzeHelmet extends Armor{
	public StrongBronzeHelmet() {
		setArmorName("Strong Bronze Helmet");
		setArmorSlot("Head");
		setBonusStat(2);
		setDefense(5);
		setValue(10);
		setID(4);
	}
}

class DefensiveBronzeHelmet extends Armor{
	public DefensiveBronzeHelmet() {
		setArmorName("Defensive Bronze Helmet");
		setArmorSlot("Head");
		setBonusStat(2);
		setDefense(5);
		setValue(10);
		setID(4);
	}
}

class HealthyBronzeChest extends Armor{
	public HealthyBronzeChest() {
		setArmorName("Healthy Bronze Chest");
		setArmorSlot("Chest");
		setBonusStat(4);
		setDefense(7);
		setValue(15);
		setID(5);
	}
}

class StrongBronzeChest extends Armor{
	public StrongBronzeChest() {
		setArmorName("Strong Bronze Chest");
		setArmorSlot("Chest");
		setBonusStat(2);
		setDefense(7);
		setValue(15);
		setID(5);
	}
}

class DefensiveBronzeChest extends Armor{
	public DefensiveBronzeChest() {
		setArmorName("Defensive Bronze Chest");
		setArmorSlot("Chest");
		setBonusStat(2);
		setDefense(7);
		setValue(15);
		setID(5);
	}
}

class HealthyBronzeBoots extends Armor{
	public HealthyBronzeBoots() {
		setArmorName("Healthy Bronze Boots");
		setArmorSlot("Feet");
		setBonusStat(3);
		setDefense(5);
		setValue(10);
		setID(6);
	}
}

class StrongBronzeBoots extends Armor{
	public StrongBronzeBoots() {
		setArmorName("Strong Bronze Boots");
		setArmorSlot("Feet");
		setBonusStat(2);
		setDefense(5);
		setValue(10);
		setID(6);
	}
}

class DefensiveBronzeBoots extends Armor{
	public DefensiveBronzeBoots() {
		setArmorName("Defensive Bronze Boots");
		setArmorSlot("Feet");
		setBonusStat(2);
		setDefense(5);
		setValue(10);
		setID(6);
	}
}

class HealthySteelHelmet extends Armor{
	public HealthySteelHelmet() {
		setArmorName("Healthy Steel Helmet");
		setArmorSlot("Head");
		setBonusStat(5);
		setDefense(7);
		setValue(20);
		setID(7);
	}
}

class StrongSteelHelmet extends Armor{
	public StrongSteelHelmet() {
		setArmorName("Strong Steel Helmet");
		setArmorSlot("Head");
		setBonusStat(3);
		setDefense(7);
		setValue(20);
		setID(7);
	}
}

class DefensiveSteelHelmet extends Armor{
	public DefensiveSteelHelmet() {
		setArmorName("Defensive Steel Helmet");
		setArmorSlot("Head");
		setBonusStat(3);
		setDefense(7);
		setValue(20);
		setID(7);
	}
}

class HealthySteelChest extends Armor{
	public HealthySteelChest() {
		setArmorName("Healthy Steel Chest");
		setArmorSlot("Chest");
		setBonusStat(8);
		setDefense(10);
		setValue(30);
		setID(8);
	}
}

class StrongSteelChest extends Armor{
	public StrongSteelChest() {
		setArmorName("Strong Steel Chest");
		setArmorSlot("Chest");
		setBonusStat(3);
		setDefense(10);
		setValue(30);
		setID(8);
	}
}

class DefensiveSteelChest extends Armor{
	public DefensiveSteelChest() {
		setArmorName("Defensive Steel Chest");
		setArmorSlot("Chest");
		setBonusStat(3);
		setDefense(10);
		setValue(30);
		setID(8);
	}
}

class HealthySteelBoots extends Armor{
	public HealthySteelBoots() {
		setArmorName("Healthy Steel Boots");
		setArmorSlot("Feet");
		setBonusStat(5);
		setDefense(7);
		setValue(20);
		setID(9);
	}
}

class StrongSteelBoots extends Armor{
	public StrongSteelBoots() {
		setArmorName("Strong Steel Boots");
		setArmorSlot("Feet");
		setBonusStat(3);
		setDefense(7);
		setValue(20);
		setID(9);
	}
}

class DefensiveSteelBoots extends Armor{
	public DefensiveSteelBoots() {
		setArmorName("Defensive Steel Boots");
		setArmorSlot("Feet");
		setBonusStat(3);
		setDefense(7);
		setValue(20);
		setID(9);
	}
}