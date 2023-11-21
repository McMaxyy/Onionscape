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
		setValue(4);
		setItemName("Health Potion");
		setID(1);
	}
}

class Bomb extends Items{
	public Bomb() {
		setValue(3);
		setItemName("Bomb");
		setID(2);
	}
}

class ItemSwing extends Items{
	public ItemSwing() {
		setItemName("Swing");
		setValue(5);
		setID(3);
	}
}

class ItemRend extends Items{
	public ItemRend() {
		setItemName("Rend");
		setValue(5);
		setID(4);
	}
}

class ItemWhirlwind extends Items{
	public ItemWhirlwind() {
		setItemName("Whirlwind");
		setValue(5);
		setID(5);
	}
}

class ItemGroundBreaker extends Items{
	public ItemGroundBreaker() {
		setItemName("Ground Breaker");
		setValue(5);
		setID(6);
	}
}

class ItemBash extends Items{
	public ItemBash() {
		setItemName("Bash");
		setValue(5);
		setID(7);
	}
}

class ItemBarrier extends Items{
	public ItemBarrier() {
		setItemName("Barrier");
		setValue(5);
		setID(8);
	}
}

class ItemHarden extends Items{
	public ItemHarden() {
		setItemName("Harden");
		setValue(5);
		setID(9);
	}
}

class ItemMend extends Items{
	public ItemMend() {
		setItemName("Mend");
		setValue(5);
		setID(10);
	}
}

class ItemHiltBash extends Items{
	public ItemHiltBash() {
		setItemName("Hilt Bash");
		setValue(5);
		setID(11);
	}
}

class ItemBarbedArmor extends Items{
	public ItemBarbedArmor() {
		setItemName("Barbed Armor");
		setValue(5);
		setID(12);
	}
}

class ItemEnrage extends Items{
	public ItemEnrage() {
		setItemName("Enrage");
		setValue(5);
		setID(13);
	}
}

class ItemRiposte extends Items{
	public ItemRiposte() {
		setItemName("Riposte");
		setValue(5);
		setID(14);
	}
}

class ItemStab extends Items{
	public ItemStab() {
		setItemName("Stab");
		setValue(5);
		setID(15);
	}
}

class ItemDecapitate extends Items{
	public ItemDecapitate() {
		setItemName("Decapitate");
		setValue(5);
		setID(16);
	}
}

class ExpBoost extends Items{
	public ExpBoost() {
		setValue(20);
		setItemName("Experience Boost");
		setID(17);
	}
}

class APBoost extends Items{
	public APBoost() {
		setValue(10);
		setItemName("Attack Boost");
		setID(18);
	}
}

class DPBoost extends Items{
	public DPBoost() {
		setValue(10);
		setItemName("Defense Boost");
		setID(19);
	}
}

class HPBoost extends Items{
	public HPBoost() {
		setValue(10);
		setItemName("Health Boost");
		setID(20);
	}
}