package storage;

import java.util.Objects;

public abstract class Weapons {
	private String weaponName, handed;
	private int weaponDmg, ID, bonusStat, value;
	
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
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
}

class WoodenGreatAxe extends Weapons{
	public WoodenGreatAxe() {
		setWeaponName("Wooden Greataxe");
		setWeaponDmg(2);
		setHanded("TwoHanded");
		setValue(1);
		setID(0);
	}
}

class HealthyIronGreatAxe extends Weapons{
	public HealthyIronGreatAxe() {
		setWeaponName("Healthy Iron Greataxe");
		setWeaponDmg(4);
		setHanded("TwoHanded");
		setBonusStat(5);
		setValue(4);
		setID(1);
	}
}

class StrongIronGreatAxe extends Weapons{
	public StrongIronGreatAxe() {
		setWeaponName("Strong Iron Greataxe");
		setWeaponDmg(4);
		setHanded("TwoHanded");
		setBonusStat(2);
		setValue(4);
		setID(1);
	}
}

class DefensiveIronGreatAxe extends Weapons{
	public DefensiveIronGreatAxe() {
		setWeaponName("Defensive Iron Greataxe");
		setWeaponDmg(4);
		setHanded("TwoHanded");
		setBonusStat(2);
		setValue(4);
		setID(1);
	}
}

class HealthyIronAxe extends Weapons{
	public HealthyIronAxe() {
		setWeaponName("Healthy Iron Axe");
		setWeaponDmg(2);
		setHanded("OneHanded");
		setBonusStat(2);
		setValue(2);
		setID(2);
	}
}

class StrongIronAxe extends Weapons{
	public StrongIronAxe() {
		setWeaponName("Strong Iron Axe");
		setWeaponDmg(2);
		setHanded("OneHanded");
		setBonusStat(1);
		setValue(2);
		setID(2);
	}
}

class DefensiveIronAxe extends Weapons{
	public DefensiveIronAxe() {
		setWeaponName("Defensive Iron Axe");
		setWeaponDmg(2);
		setHanded("OneHanded");
		setBonusStat(1);
		setValue(2);
		setID(2);
	}
}

class WoodenShield extends Weapons{
	public WoodenShield() {
		setWeaponName("Wooden Shield");
		setWeaponDmg(1);
		setHanded("OffHand");
		setValue(1);
		setID(3);
	}
}

class HealthyIronShield extends Weapons{
	public HealthyIronShield() {
		setWeaponName("Healthy Iron Shield");
		setWeaponDmg(3);
		setHanded("OffHand");
		setBonusStat(3);
		setValue(2);
		setID(4);
	}
}

class StrongIronShield extends Weapons{
	public StrongIronShield() {
		setWeaponName("Strong Iron Shield");
		setWeaponDmg(3);
		setHanded("OffHand");
		setValue(2);
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
		setValue(2);
		setID(4);
	}
}

class WoodenAxe extends Weapons{
	public WoodenAxe() {
		setWeaponName("Wooden Axe");
		setWeaponDmg(1);
		setHanded("OneHanded");
		setValue(1);
		setID(5);
	}
}

class HealthyBronzeGreatAxe extends Weapons{
	public HealthyBronzeGreatAxe() {
		setWeaponName("Healthy Bronze Greataxe");
		setWeaponDmg(5);
		setHanded("TwoHanded");
		setBonusStat(7);
		setValue(6);
		setID(6);
	}
}

class StrongBronzeGreatAxe extends Weapons{
	public StrongBronzeGreatAxe() {
		setWeaponName("Strong Bronze Greataxe");
		setWeaponDmg(5);
		setHanded("TwoHanded");
		setBonusStat(3);
		setValue(6);
		setID(6);
	}
}

class DefensiveBronzeGreatAxe extends Weapons{
	public DefensiveBronzeGreatAxe() {
		setWeaponName("Defensive Bronze Greataxe");
		setWeaponDmg(5);
		setHanded("TwoHanded");
		setBonusStat(3);
		setValue(6);
		setID(6);
	}
}

class HealthySteelGreatAxe extends Weapons{
	public HealthySteelGreatAxe() {
		setWeaponName("Healthy Steel Greataxe");
		setWeaponDmg(7);
		setHanded("TwoHanded");
		setBonusStat(10);
		setValue(10);
		setID(7);
	}
}

class StrongSteelGreatAxe extends Weapons{
	public StrongSteelGreatAxe() {
		setWeaponName("Strong Steel Greataxe");
		setWeaponDmg(7);
		setHanded("TwoHanded");
		setBonusStat(4);
		setValue(10);
		setID(7);
	}
}

class DefensiveSteelGreatAxe extends Weapons{
	public DefensiveSteelGreatAxe() {
		setWeaponName("Defensive Steel Greataxe");
		setWeaponDmg(7);
		setHanded("TwoHanded");
		setBonusStat(4);
		setValue(10);
		setID(7);
	}
}

class HealthyBronzeAxe extends Weapons{
	public HealthyBronzeAxe() {
		setWeaponName("Healthy Bronze Axe");
		setWeaponDmg(3);
		setHanded("OneHanded");
		setBonusStat(3);
		setValue(3);
		setID(8);
	}
}

class StrongBronzeAxe extends Weapons{
	public StrongBronzeAxe() {
		setWeaponName("Strong Bronze Axe");
		setWeaponDmg(3);
		setHanded("OneHanded");
		setBonusStat(2);
		setValue(3);
		setID(8);
	}
}

class DefensiveBronzeAxe extends Weapons{
	public DefensiveBronzeAxe() {
		setWeaponName("Defensive Bronze Axe");
		setWeaponDmg(3);
		setHanded("OneHanded");
		setBonusStat(2);
		setValue(3);
		setID(8);
	}
}

class HealthySteelAxe extends Weapons{
	public HealthySteelAxe() {
		setWeaponName("Healthy Steel Axe");
		setWeaponDmg(4);
		setHanded("OneHanded");
		setBonusStat(5);
		setValue(5);
		setID(9);
	}
}

class StrongSteelAxe extends Weapons{
	public StrongSteelAxe() {
		setWeaponName("Strong Steel Axe");
		setWeaponDmg(4);
		setHanded("OneHanded");
		setBonusStat(3);
		setValue(5);
		setID(9);
	}
}

class DefensiveSteelAxe extends Weapons{
	public DefensiveSteelAxe() {
		setWeaponName("Defensive Steel Axe");
		setWeaponDmg(4);
		setHanded("OneHanded");
		setBonusStat(3);
		setValue(5);
		setID(9);
	}
}

class HealthyBronzeShield extends Weapons{
	public HealthyBronzeShield() {
		setWeaponName("Healthy Bronze Shield");
		setWeaponDmg(4);
		setHanded("OffHand");
		setBonusStat(4);
		setValue(3);
		setID(10);
	}
}

class StrongBronzeShield extends Weapons{
	public StrongBronzeShield() {
		setWeaponName("Strong Bronze Shield");
		setWeaponDmg(4);
		setHanded("OffHand");
		setBonusStat(1);
		setValue(3);
		setID(10);
	}
}

class DefensiveBronzeShield extends Weapons{
	public DefensiveBronzeShield() {
		setWeaponName("Defensive Bronze Shield");
		setWeaponDmg(4);
		setHanded("OffHand");
		setBonusStat(1);
		setValue(3);
		setID(10);
	}
}

class HealthySteelShield extends Weapons{
	public HealthySteelShield() {
		setWeaponName("Healthy Steel Shield");
		setWeaponDmg(5);
		setHanded("OffHand");
		setBonusStat(5);
		setValue(5);
		setID(11);
	}
}

class StrongSteelShield extends Weapons{
	public StrongSteelShield() {
		setWeaponName("Strong Steel Shield");
		setWeaponDmg(5);
		setHanded("OffHand");
		setBonusStat(2);
		setValue(5);
		setID(11);
	}
}

class DefensiveSteelShield extends Weapons{
	public DefensiveSteelShield() {
		setWeaponName("Defensive Steel Shield");
		setWeaponDmg(5);
		setHanded("OffHand");
		setBonusStat(2);
		setValue(5);
		setID(11);
	}
}