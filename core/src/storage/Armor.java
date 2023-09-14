package storage;

public abstract class Armor {
	private int defense, ID, amount, bonusStat;
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
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getBonusStat() {
		return bonusStat;
	}
	public void setBonusStat(int bonusStat) {
		this.bonusStat = bonusStat;
	}
}

class HealthyIronHelmet extends Armor{
	public HealthyIronHelmet() {
		setArmorName("Healthy Iron Helmet");
		setArmorSlot("Head");
		setBonusStat(2);
		setDefense(2);
		setID(1);
	}
}

class StrongIronHelmet extends Armor{
	public StrongIronHelmet() {
		setArmorName("Strong Iron Helmet");
		setArmorSlot("Head");
		setBonusStat(1);
		setDefense(2);
		setID(1);
	}
}

class DefensiveIronHelmet extends Armor{
	public DefensiveIronHelmet() {
		setArmorName("Defensive Iron Helmet");
		setArmorSlot("Head");
		setBonusStat(1);
		setDefense(2);
		setID(1);
	}
}

class IronChest extends Armor{
	public IronChest() {
		setArmorName("Iron Chest");
		setArmorSlot("Chest");
		setDefense(3);
		setID(2);
	}
}

class HealthyIronChest extends Armor{
	public HealthyIronChest() {
		setArmorName("Healthy Iron Chest");
		setArmorSlot("Chest");
		setBonusStat(3);
		setDefense(3);
		setID(2);
	}
}

class StrongIronChest extends Armor{
	public StrongIronChest() {
		setArmorName("Strong Iron Chest");
		setArmorSlot("Chest");
		setBonusStat(1);
		setDefense(3);
		setID(2);
	}
}

class DefensiveIronChest extends Armor{
	public DefensiveIronChest() {
		setArmorName("Defensive Iron Chest");
		setArmorSlot("Chest");
		setBonusStat(1);
		setDefense(3);
		setID(2);
	}
}

class IronBoots extends Armor{
	public IronBoots() {
		setArmorName("Iron Boots");
		setArmorSlot("Feet");
		setDefense(2);
		setID(3);
	}
}

class HealthyIronBoots extends Armor{
	public HealthyIronBoots() {
		setArmorName("Healthy Iron Boots");
		setArmorSlot("Feet");
		setBonusStat(2);
		setDefense(2);
		setID(3);
	}
}

class StrongIronBoots extends Armor{
	public StrongIronBoots() {
		setArmorName("Strong Iron Boots");
		setArmorSlot("Feet");
		setBonusStat(1);
		setDefense(2);
		setID(3);
	}
}

class DefensiveIronBoots extends Armor{
	public DefensiveIronBoots() {
		setArmorName("Defensive Iron Boots");
		setArmorSlot("Feet");
		setBonusStat(1);
		setDefense(2);
		setID(3);
	}
}