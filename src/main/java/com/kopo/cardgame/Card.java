package com.kopo.cardgame;

public class Card {
	
	int idx;
	String name;
	int atk;
	int def;
	int atkRate;
	int defRate;
	
	Card(){
		
	}
	

	Card(int idx, String name , int atk, int def, int atkRate, int defRate){
		this.idx = idx;
		this.name = name;
		this.atk = atk;
		this.def = def;
		this.atkRate = atkRate;
		this.defRate = defRate;
		
	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAtk() {
		return atk;
	}

	public void setAtk(int atk) {
		this.atk = atk;
	}

	public int getDef() {
		return def;
	}

	public void setDef(int def) {
		this.def = def;
	}

	public int getAtkRate() {
		return atkRate;
	}

	public void setAtkRate(int atkRate) {
		this.atkRate = atkRate;
	}

	public int getDefRate() {
		return defRate;
	}

	public void setDefRate(int defRate) {
		this.defRate = defRate;
	}
	

}
