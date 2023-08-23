package player;

import java.util.Random;

public class Player {
	private static int maxHP = 60;
	private static int hp;
	private static int strength = 3;
	private static int dmgResist = 0;
	private static int coins = 9999;
	private static int level = 1;
	private static int exp = 0;
	private static int levelCap = 20;
	private static int skillPoints = 1;
	private static int twoHandStr = 0, oneHandStr = 0;
	public static int abID1, abID2, abID3, abID4;
	private static Random rand = new Random();
	
	public static void loseHP(int x) {
		hp -= x;
	}
	
	public static void gainExp(int x) {
		exp += x;
	}
	
	public static void checkExp() {
		if(exp >= levelCap) {
			level++;
			levelCap += 10;
			exp = 0;
			skillPoints++;
		}			
	}
	
	public static void skillPointUse() {
		skillPoints--;
	}
	
	public static void newGame() {
		int id1, id2, id3, id4;
		hp = maxHP;
				
    	id1 = rand.nextInt(1,8);   
    	id2 = rand.nextInt(1,8);    
    	id3 = rand.nextInt(1,8);    
    	id4 = rand.nextInt(1,8);
    	
    	abID1 = id1;
    	while(id1 == id2)
    		id2 = rand.nextInt(1,8); 
    	abID2 = id2;
    	while(id3 == id1 || id3 == id2)
    		id3 = rand.nextInt(1,8); 
    	abID3 = id3;
    	while(id4 == id1 || id4 == id2 || id4 == id3)
    		id4 = rand.nextInt(1,8); 
    	abID4 = id4;
	}
	
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

	public static int getSkillPoints() {
		return skillPoints;
	}

	public static int getDmgResist() {
		return dmgResist;
	}

	public static void setDmgResist(int dmgResist) {
		Player.dmgResist = dmgResist;
	}

	public static int getTwoHandStr() {
		return twoHandStr;
	}

	public static void setTwoHandStr(int twoHandStr) {
		Player.twoHandStr = twoHandStr;
	}

	public static int getOneHandStr() {
		return oneHandStr;
	}

	public static void setOneHandStr(int oneHandStr) {
		Player.oneHandStr = oneHandStr;
	}	
}
