package storage;

import java.util.Objects;

public abstract class Weapons {
	private String weaponName, handed;
	private int weaponDmg, ID, bonusStat;
	
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
		setBonusStat(5);
		setID(1);
	}
}

class StrongIronGreatAxe extends Weapons{
	public StrongIronGreatAxe() {
		setWeaponName("Strong Iron Greataxe");
		setWeaponDmg(4);
		setHanded("TwoHanded");
		setBonusStat(2);
		setID(1);
	}
}

class DefensiveIronGreatAxe extends Weapons{
	public DefensiveIronGreatAxe() {
		setWeaponName("Defensive Iron Greataxe");
		setWeaponDmg(4);
		setHanded("TwoHanded");
		setBonusStat(2);
		setID(1);
	}
}

class HealthyIronAxe extends Weapons{
	public HealthyIronAxe() {
		setWeaponName("Healthy Iron Axe");
		setWeaponDmg(2);
		setHanded("OneHanded");
		setBonusStat(1);
		setID(2);
	}
}

class StrongIronAxe extends Weapons{
	public StrongIronAxe() {
		setWeaponName("Strong Iron Axe");
		setWeaponDmg(2);
		setHanded("OneHanded");
		setBonusStat(1);
		setID(2);
	}
}

class DefensiveIronAxe extends Weapons{
	public DefensiveIronAxe() {
		setWeaponName("Defensive Iron Axe");
		setWeaponDmg(2);
		setHanded("OneHanded");
		setBonusStat(1);
		setID(2);
	}
}

class WoodenShield extends Weapons{
	public WoodenShield() {
		setWeaponName("Wooden Shield");
		setWeaponDmg(1);
		setHanded("OffHand");
		setID(3);
	}
}

class HealthyIronShield extends Weapons{
	public HealthyIronShield() {
		setWeaponName("Healthy Iron Shield");
		setWeaponDmg(3);
		setHanded("OffHand");
		setBonusStat(1);
		setID(4);
	}
}

class StrongIronShield extends Weapons{
	public StrongIronShield() {
		setWeaponName("Strong Iron Shield");
		setWeaponDmg(3);
		setHanded("OffHand");
		setBonusStat(1);
		setID(4);
	}
}

class DefensiveIronShield extends Weapons{
	public DefensiveIronShield() {
		setWeaponName("Defensive Iron Shield");
		setWeaponDmg(3);
		setHanded("OffHand");
		setBonusStat(1);
		setID(4);
	}
}