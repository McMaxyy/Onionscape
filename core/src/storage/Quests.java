package storage;

public abstract class Quests {
	private int reward, questID, obj1, obj2, obj3, rewardType, obj1Prog, obj2Prog, obj3Prog, active;
	private String questName, objective1, objective2, objective3;
	
	public int getReward() {
		return reward;
	}
	public void setReward(int reward) {
		this.reward = reward;
	}
	public int getQuestID() {
		return questID;
	}
	public void setQuestID(int questID) {
		this.questID = questID;
	}
	public String getQuestName() {
		return questName;
	}
	public void setQuestName(String questName) {
		this.questName = questName;
	}
	public String getObjective1() {
		return objective1;
	}
	public void setObjective1(String objective1) {
		this.objective1 = objective1;
	}
	public String getObjective2() {
		return objective2;
	}
	public void setObjective2(String objective2) {
		this.objective2 = objective2;
	}
	public String getObjective3() {
		return objective3;
	}
	public void setObjective3(String objective3) {
		this.objective3 = objective3;
	}
	public int getObj1() {
		return obj1;
	}
	public void setObj1(int obj1) {
		this.obj1 = obj1;
	}
	public int getObj2() {
		return obj2;
	}
	public void setObj2(int obj2) {
		this.obj2 = obj2;
	}
	public int getObj3() {
		return obj3;
	}
	public void setObj3(int obj3) {
		this.obj3 = obj3;
	}
	public int getRewardType() {
		return rewardType;
	}
	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
	}
	public int getActive() {
		return active;
	}
	public void setActive(int active) {
		this.active = active;
	}
	public int getObj1Prog() {
		return obj1Prog;
	}
	public void setObj1Prog(int obj1Prog) {
		this.obj1Prog = obj1Prog;
	}
	public int getObj2Prog() {
		return obj2Prog;
	}
	public void setObj2Prog(int obj2Prog) {
		this.obj2Prog = obj2Prog;
	}
	public int getObj3Prog() {
		return obj3Prog;
	}
	public void setObj3Prog(int obj3Prog) {
		this.obj3Prog = obj3Prog;
	}
}

class WolfQuest extends Quests{
	public WolfQuest() {
		setQuestName("Wolf Extermination");
		setQuestID(1);
		setObjective1("Kill 5 Wolves");
		setObj1(5);
		setObj1Prog(4);
		setRewardType(1);
		setReward(20);
		setActive(0);
	}
}

class SpiderQuest extends Quests{
	public SpiderQuest() {
		setQuestName("Spider Extermination");
		setQuestID(2);
		setObjective1("Kill 5 Spiders");
		setObj1(5);
		setObj1Prog(4);
		setRewardType(1);
		setReward(20);
		setActive(0);
	}
}

class SpiderBearQuest extends Quests{
	public SpiderBearQuest() {
		setQuestName("Mass Extermination");
		setQuestID(3);
		setObjective1("Kill 3 Spiders");
		setObjective2("Kill 3 Bears");
		setObj1(3);
		setObj2(3);
		setObj1Prog(0);
		setObj2Prog(0);
		setRewardType(2);
		setReward(20);
		setActive(0);
	}
}

class BearQuest extends Quests{
	public BearQuest() {
		setQuestName("Bear Extermination");
		setQuestID(4);
		setObjective1("Kill 5 Bears");
		setObj1(5);
		setObj1Prog(0);
		setRewardType(1);
		setReward(20);
		setActive(0);
	}
}

class WaspQuest extends Quests{
	public WaspQuest() {
		setQuestName("Wasp Extermination");
		setQuestID(5);
		setObjective1("Kill 5 Wasps");
		setObj1(5);
		setObj1Prog(0);
		setRewardType(2);
		setReward(20);
		setActive(0);
	}
}

class MonkeyQuest extends Quests{
	public MonkeyQuest() {
		setQuestName("Monkey Extermination");
		setQuestID(6);
		setObjective1("Kill 5 Monkeys");
		setObj1(5);
		setObj1Prog(0);
		setRewardType(2);
		setReward(20);
		setActive(0);
	}
}

class MonkeyWaspWolfQuest extends Quests{
	public MonkeyWaspWolfQuest() {
		setQuestName("Mass Extermination");
		setQuestID(7);
		setObjective1("Kill 2 Wolves");
		setObjective2("Kill 2 Monkeys");
		setObjective3("Kill 2 Wasps");
		setObj1(2);
		setObj2(2);
		setObj3(2);
		setObj1Prog(0);
		setObj2Prog(0);
		setRewardType(1);
		setReward(30);
		setActive(0);
	}
}

class WhirlwindQuest extends Quests{
	public WhirlwindQuest() {
		setQuestName("Skill Training");
		setQuestID(8);
		setObjective1("Hit enemies with Whirlwind 5 times");
		setObj1(5);
		setObj1Prog(0);
		setRewardType(3);
		setReward(1);
		setActive(0);
	}
}