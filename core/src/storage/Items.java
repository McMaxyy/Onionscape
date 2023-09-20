package storage;

public abstract class Items {
	private int amount, value, ID;
	private String itemName;
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
}

class HealthPot extends Items{
	public HealthPot() {
		setValue(5);
		setItemName("Health Potion");
		setID(1);
	}
}

class Bomb extends Items{
	public Bomb() {
		setValue(5);
		setItemName("Bomb");
		setID(2);
	}
}

class ItemSwing extends Items{
	public ItemSwing() {
		setItemName("Swing");
		setID(3);
	}
}