package player;

public abstract class Abilities {
	private int attackPower, usesLeft,  ID;
	private String abilityName, classID;

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

	public String getClassID() {
		return classID;
	}

	public void setClassID(String classID) {
		this.classID = classID;
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