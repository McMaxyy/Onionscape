package storage;

public abstract class Items {
	private int value, ID;
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

class ItemRend extends Items{
	public ItemRend() {
		setItemName("Rend");
		setID(4);
	}
}

class ItemWhirlwind extends Items{
	public ItemWhirlwind() {
		setItemName("Whirlwind");
		setID(5);
	}
}

class ItemGroundBreaker extends Items{
	public ItemGroundBreaker() {
		setItemName("Ground Breaker");
		setID(6);
	}
}

class ItemBash extends Items{
	public ItemBash() {
		setItemName("Bash");
		setID(7);
	}
}

class ItemBarrier extends Items{
	public ItemBarrier() {
		setItemName("Barrier");
		setID(8);
	}
}

class ItemHarden extends Items{
	public ItemHarden() {
		setItemName("Harden");
		setID(9);
	}
}

class ItemMend extends Items{
	public ItemMend() {
		setItemName("Mend");
		setID(10);
	}
}

class ItemHiltBash extends Items{
	public ItemHiltBash() {
		setItemName("Hilt Bash");
		setID(11);
	}
}

class ItemBarbedArmor extends Items{
	public ItemBarbedArmor() {
		setItemName("Barbed Armor");
		setID(12);
	}
}

class ItemEnrage extends Items{
	public ItemEnrage() {
		setItemName("Enrage");
		setID(13);
	}
}

class ItemRiposte extends Items{
	public ItemRiposte() {
		setItemName("Riposte");
		setID(14);
	}
}

class ItemStab extends Items{
	public ItemStab() {
		setItemName("Stab");
		setID(15);
	}
}

class ItemDecapitate extends Items{
	public ItemDecapitate() {
		setItemName("Decapitate");
		setID(16);
	}
}