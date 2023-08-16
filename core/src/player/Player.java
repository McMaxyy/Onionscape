package player;

public class Player {
	private static int maxHP = 60;
	private static int hp;
	private static int strength = 3;
	private static int coins = 9999;
	private static int level = 0;
	private static int exp = 0;
	public static int abID1, abID2, abID3, abID4;
	
	public static int getMaxHP() {
		return maxHP;
	}
	public static void setMaxHP(int maxHP) {
		Player.maxHP = maxHP;
	}
	public static int getHp() {
		return hp;
	}
	public static void setHp(int hp) {
		Player.hp = hp;
	}
	public static int getStrength() {
		return strength;
	}
	public static void setStrength(int strength) {
		Player.strength = strength;
	}
	public static int getCoins() {
		return coins;
	}
	public static void setCoins(int coins) {
		Player.coins = coins;
	}
	public static int getLevel() {
		return level;
	}
	public static void setLevel(int level) {
		Player.level = level;
	}
	public static int getExp() {
		return exp;
	}
	public static void setExp(int exp) {
		Player.exp = exp;
	}
	public static int getAbID1() {
		return abID1;
	}
	public static void setAbID1(int abID1) {
		Player.abID1 = abID1;
	}
	public static int getAbID2() {
		return abID2;
	}
	public static void setAbID2(int abID2) {
		Player.abID2 = abID2;
	}
	public static int getAbID3() {
		return abID3;
	}
	public static void setAbID3(int abID3) {
		Player.abID3 = abID3;
	}
	public static int getAbID4() {
		return abID4;
	}
	public static void setAbID4(int abID4) {
		Player.abID4 = abID4;
	}
	
}
