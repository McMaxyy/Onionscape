package com.onionscape.game;

import java.util.ArrayList;
import java.util.List;

import player.Armor;
import player.Weapons;

public class SaveData {	
	public int maxHP;
	public int strength;
	public List<Weapons> playerWeapons = new ArrayList<>();
	public List<Armor> playerArmor = new ArrayList<>();
}
