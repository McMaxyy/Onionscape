package storage;

public abstract class Enemy {
	private int attackPower, ID, maxHP, value, exp;
	private String enemyName, ability1, ability2, ability3;
	
	public int getAttackPower() {
		return attackPower;
	}
	public void setAttackPower(int attackPower) {
		this.attackPower = attackPower;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getEnemyName() {
		return enemyName;
	}
	public void setEnemyName(String enemyName) {
		this.enemyName = enemyName;
	}
	public String getAbility1() {
		return ability1;
	}
	public void setAbility1(String ability1) {
		this.ability1 = ability1;
	}
	public String getAbility2() {
		return ability2;
	}
	public void setAbility2(String ability2) {
		this.ability2 = ability2;
	}
	public String getAbility3() {
		return ability3;
	}
	public void setAbility3(String ability3) {
		this.ability3 = ability3;
	}
	public int getMaxHP() {
		return maxHP;
	}
	public void setMaxHP(int maxHP) {
		this.maxHP = maxHP;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
}

class Wolf extends Enemy{
	public Wolf() {
		setEnemyName("Wolf");
		setAttackPower(5);
		setMaxHP(30);
		setValue(5);
		setExp(5);
		setAbility1("Bleed");
		setAbility2("Enrage");
	}
}

class Spider extends Enemy{
	public Spider() {
		setEnemyName("Spider");
		setAttackPower(4);
		setMaxHP(35);
		setValue(7);
		setExp(6);
		setAbility1("Poison");
		setAbility2("Harden");
	}
}

class Bear extends Enemy{
	public Bear() {
		setEnemyName("Bear");
		setAttackPower(6);
		setMaxHP(40);
		setValue(10);
		setExp(8);
		setAbility1("Enrage");
		setAbility2("Harden");
	}
}

class Monkey extends Enemy{
	public Monkey() {
		setEnemyName("Monkey");
		setAttackPower(8);
		setMaxHP(30);
		setValue(10);
		setExp(7);
		setAbility1("Enrage");
		setAbility2("Stun");
	}
}

class Wasp extends Enemy{
	public Wasp() {
		setEnemyName("Wasp");
		setAttackPower(6);
		setMaxHP(35);
		setValue(10);
		setExp(8);
		setAbility1("Poison");
		setAbility2("Stun");
	}
}