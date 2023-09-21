package storage;

import java.util.Objects;

public abstract class Weapons {
	private String weaponName, handed;
	private int weaponDmg, ID, amount, bonusStat;
	
	public String getWeaponName() {
		return weaponName;
	}
	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}
	public int getWeaponDmg() {
		return weaponDmg;
	}
	public void setWeaponDmg(int weaponDmg) {
		this.weaponDmg = weaponDmg;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getHanded() {
		return handed;
	}
	public void setHanded(String handed) {
		this.handed = handed;
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
	@Override
	public int hashCode() {
	    return Objects.hash(weaponName, bonusStat);
	}
}

class WoodenGreatAxe extends Weapons{
	public WoodenGreatAxe() {
		setWeaponName("Wooden Greataxe");
		setWeaponDmg(2);
		setHanded("TwoHanded");
		setID(0);
	}
}

class HealthyIronGreatAxe extends Weapons{
	public HealthyIronGreatAxe() {
		setWeaponName("Healthy Iron Greataxe");
		setWeaponDmg(4);
		setHanded("TwoHanded");
		setAmount(0);
		setBonusStat(5);
		setID(1);
	}
}

class StrongIronGreatAxe extends Weapons{
	public StrongIronGreatAxe() {
		setWeaponName("Strong Iron Greataxe");
		setWeaponDmg(4);
		setHanded("TwoHanded");
		setAmount(0);
		setBonusStat(2);
		setID(1);
	}
}

class DefensiveIronGreatAxe extends Weapons{
	public DefensiveIronGreatAxe() {
		setWeaponName("Defensive Iron Greataxe");
		setWeaponDmg(4);
		setHanded("TwoHanded");
		setAmount(0);
		setBonusStat(2);
		setID(1);
	}
}

class IronAxe extends Weapons{
	public IronAxe() {
		setWeaponName("Iron Axe");
		setWeaponDmg(2);
		setHanded("OneHanded");
		setAmount(0);
		setID(2);
	}
}

class WoodenShield extends Weapons{
	public WoodenShield() {
		setWeaponName("Wooden Shield");
		setWeaponDmg(2);
		setHanded("OffHand");
		setAmount(0);
		setID(3);
	}
}