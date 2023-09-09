package storage;

public abstract class Armor {
	private int defense, ID, amount;
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
}

class IronHelmet extends Armor{
	public IronHelmet() {
		setArmorName("Iron Helmet");
		setArmorSlot("Head");
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

class IronBoots extends Armor{
	public IronBoots() {
		setArmorName("Iron Boots");
		setArmorSlot("Feet");
		setDefense(2);
		setID(3);
	}
}