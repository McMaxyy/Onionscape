package player;

import java.util.Random;

import com.onionscape.game.GameScreen;

public class Player {
	private static int raidCoins = 50;
	private static int maxHP = 70;
	private static int hp;
	private static int strength = 3;
	private static int weaponDmg = 0;
	private static int dmgResist = 0;
	private static int skillDmgResist = 0;
	private static int skillWeaponDmg = 0;
	private static int skillMaxHP = 0;
	private static int skillStrength = 0;
	private static int coins = 200;
	private static int level = 1;
	private static int exp = 0;
	private static int levelCap = 20;
	private static int skillPoints = 30;	
	private static int twoHandStr = 0, oneHandStr = 0;
	public static int abID1, abID2, abID3, abID4;
	public static int weaponState = 0;	
	private static int extraAP = 0;
	private static int extraDP = 0;
	private static int extraHP = 0;
	private static int gearHP = 0;
	private static int gearAP = 0;
	private static int gearDP = 0;
	private static Random rand = new Random();
	
	public static void skillTreeGains(int x) {
		switch(x) {
		case 0:
			twoHandStr++;
			break;
		case 1:
			oneHandStr++;
			break;
		case 2:
			skillDmgResist++;
			break;
		case 3:
			skillWeaponDmg++;
			break;
		case 4:
			skillStrength++;
			break;
		case 5:
			skillMaxHP += 3;
			break;
		}
	}
	
	public static void gainExtraAP(int x) {
		extraAP += x;
	}
	
	public static void gainExtraDP(int x) {
		extraDP += x;
	}
	
	public static void gainExtraHP(int x) {
		extraHP += x;
	}
	
	public static void gainDR(int x) {
		dmgResist += x;
	}
	
	public static void loseDR(int x) {
		dmgResist -= x;
	}
	
	public static void gainHP(int x) {
		hp += x;
	}
	
	public static void loseHP(int x) {
		hp -= x;
	}
	
	public static void gainExp(int x) {
		exp += x;
	}
	
	public static void checkExp() {
		if(exp >= levelCap) {
			level++;			
			exp = 0;
			skillPoints++;
			if(level >= 1 && level <= 5)
				levelCap += 10;
			else if(level >= 6 && level <= 10)
				levelCap += 20;
			else if(level >= 11 && level <= 15)
				levelCap += 35;
			else if(level >= 16 && level <= 20)
				levelCap += 50;
			else
				levelCap += 80;
		}			
	}
	
	public static void gainMaxHP(int x) {
		maxHP += x;
	}
	
	public static void loseMaxHP(int x) {
		maxHP -= x;
	}
	
	public static void gainWeaponDmg(int x) {
		weaponDmg += x;
	}
	
	public static void loseWeaponDmg(int x) {
		weaponDmg -= x;
	}
	
	public static void gainBonusStr(int x) {
		strength += x;
	}
	
	public static void loseBonusStr(int x) {
		strength -= x;
	}
	
	public static void skillPointUse() {
		skillPoints--;
	}
	
	public static void gainCoins(int x) {
		coins += x;
	}
	
	public static void loseCoins(int x) {
		coins -= x;
	}
	
	public static void gainRaidCoins(int x) {
		raidCoins += x;
	}
	
	public static void loseRaidCoins(int x) {
		raidCoins -= x;
	}
	
	public static void newGame() {
		int id1, id2, id3, id4;
		hp = maxHP;
				
    	id1 = rand.nextInt(1,15);   
    	id2 = rand.nextInt(1,15);    
    	id3 = rand.nextInt(1,15);    
    	id4 = rand.nextInt(1,15);
    	
    	abID1 = id1;
    	while(id1 == id2)
    		id2 = rand.nextInt(1,15); 
    	abID2 = id2;
    	while(id3 == id1 || id3 == id2)
    		id3 = rand.nextInt(1,15); 
    	abID3 = id3;
    	while(id4 == id1 || id4 == id2 || id4 == id3)
    		id4 = rand.nextInt(1,15); 
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

	public static void setSkillPoints(int skillPoints) {
		Player.skillPoints = skillPoints;
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
	
	public static int getWeaponDmg() {
		return weaponDmg;
	}

	public static void setWeaponDmg(int weaponDmg) {
		Player.weaponDmg = weaponDmg;
	}
	public static int getLevelCap() {
		return levelCap;
	}
	
	public static int getRaidCoins() {
		return raidCoins;
	}

	public static void setRaidCoins(int raidCoins) {
		Player.raidCoins = raidCoins;
	}

	public static int getSkillDmgResist() {
		return skillDmgResist;
	}

	public static void setSkillDmgResist(int skillDmgResist) {
		Player.skillDmgResist = skillDmgResist;
	}

	public static int getSkillWeaponDmg() {
		return skillWeaponDmg;
	}

	public static void setSkillWeaponDmg(int skillWeaponDmg) {
		Player.skillWeaponDmg = skillWeaponDmg;
	}

	public static int getSkillMaxHP() {
		return skillMaxHP;
	}

	public static void setSkillMaxHP(int skillMaxHP) {
		Player.skillMaxHP = skillMaxHP;
	}

	public static int getExtraAP() {
		return extraAP;
	}

	public static void setExtraAP(int extraAP) {
		Player.extraAP = extraAP;
	}

	public static int getExtraDP() {
		return extraDP;
	}

	public static void setExtraDP(int extraDP) {
		Player.extraDP = extraDP;
	}

	public static int getExtraHP() {
		return extraHP;
	}

	public static void setExtraHP(int extraHP) {
		Player.extraHP = extraHP;
	}

	public static int getSkillStrength() {
		return skillStrength;
	}

	public static void setSkillStrength(int skillStrength) {
		Player.skillStrength = skillStrength;
	}

	public static int getGearHP() {
		return gearHP;
	}

	public static void setGearHP(int gearHP) {
		Player.gearHP = gearHP;
	}

	public static int getGearAP() {
		return gearAP;
	}

	public static void setGearAP(int gearAP) {
		Player.gearAP = gearAP;
	}

	public static int getGearDP() {
		return gearDP;
	}

	public static void setGearDP(int gearDP) {
		Player.gearDP = gearDP;
	}
}
