package player;

public abstract class Items {
	private int amount, value, ID;
	private String itemName;
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
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
		setAmount(0);
		setValue(5);
		setItemName("Health Potion");
		setID(1);
	}
}

class Bomb extends Items{
	public Bomb() {
		setAmount(0);
		setValue(5);
		setItemName("Bomb");
		setID(2);
	}
}