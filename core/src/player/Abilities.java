package player;

public abstract class Abilities {
	private int attackPower, usesLeft, ID;
	private String abilityName;

	public int getAttackPower() {
		return attackPower;
	}

	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}

	public int getUsesLeft() {
		return usesLeft;
	}

	public void setUsesLeft(int usesLeft) {
		this.usesLeft = usesLeft;
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}

	public String getAbilityName() {
		return abilityName;
	}

	public void setAbilityName(String abilityName) {
		this.abilityName = abilityName;
	}
}

class Swing extends Abilities{
	public Swing() {
		setAbilityName("Swing");
		setAttackPower(6);
		setUsesLeft(10);
		setID(1);
	}
}

class Rend extends Abilities{
	public Rend() {
		setAbilityName("Rend");
		setAttackPower(4);
		setUsesLeft(6);
		setID(2);
	}
}

class Whirlwind extends Abilities{
	public Whirlwind() {
		setAbilityName("Whirlwind");
		setAttackPower(8);
		setUsesLeft(3);
		setID(3);
	}
}

class GroundBreaker extends Abilities{
	public GroundBreaker() {
		setAbilityName("Ground Breaker");
		setAttackPower(8);
		setUsesLeft(3);
		setID(4);
	}
}

class Bash extends Abilities{
	public Bash() {
		setAbilityName("Bash");
		setAttackPower(3);
		setUsesLeft(6);
		setID(5);
	}
}

class Barrier extends Abilities{
	public Barrier() {
		setAbilityName("Barrier");
		setAttackPower(0);
		setUsesLeft(4);
		setID(6);
	}
}

class Harden extends Abilities{
	public Harden() {
		setAbilityName("Harden");
		setAttackPower(0);
		setUsesLeft(5);
		setID(7);
	}
}

class Mend extends Abilities{
	public Mend() {
		setAbilityName("Mend");
		setAttackPower(5);
		setUsesLeft(4);
		setID(8);
	}
}

class HiltBash extends Abilities{
	public HiltBash() {
		setAbilityName("Hilt Bash");
		setAttackPower(0);
		setUsesLeft(4);
		setID(9);
	}
}

class BarbedArmor extends Abilities{
	public BarbedArmor() {
		setAbilityName("Barbed Armor");
		setAttackPower(3);
		setUsesLeft(3);
		setID(10);
	}
}

class Enrage extends Abilities{
	public Enrage() {
		setAbilityName("Enrage");
		setAttackPower(3);
		setUsesLeft(4);
		setID(11);
	}
}

class Riposte extends Abilities{
	public Riposte() {
		setAbilityName("Riposte");
		setAttackPower(0);
		setUsesLeft(5);
		setID(12);
	}
}

class Stab extends Abilities{
	public Stab() {
		setAbilityName("Stab");
		setAttackPower(5);
		setUsesLeft(7);
		setID(13);
	}
}

class Decapitate extends Abilities{
	public Decapitate() {
		setAbilityName("Decapitate");
		setAttackPower(15);
		setUsesLeft(4);
		setID(14);
	}
}