package com.kopo.cardgame;

public class Card {
	
	int idx;
	String name;
	int atk;
	int def;
	int atk_rate;
	int def_rate;
	String tribe;
	
	Card(){
		
	}

	Card(int idx){
		this.idx = idx;

	}

	Card(String name , int atk, int def, int atk_rate, int def_rate, String tribe){
		this.name = name;
		this.atk = atk;
		this.def = def;
		this.atk_rate = atk_rate;
		this.def_rate = def_rate;
		this.tribe = tribe;
	}
	

	Card(int idx, String name , int atk, int def, int atk_rate, int def_rate, String tribe){
		this.idx = idx;
		this.name = name;
		this.atk = atk;
		this.def = def;
		this.atk_rate = atk_rate;
		this.def_rate = def_rate;
		this.tribe = tribe;
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



	public String getTribe() {
		return tribe;
	}


	public void setTribe(String tribe) {
		this.tribe = tribe;
	}


	public int getAtk_rate() {
		return atk_rate;
	}


	public void setAtk_rate(int atk_rate) {
		this.atk_rate = atk_rate;
	}


	public int getDef_rate() {
		return def_rate;
	}


	public void setDef_rate(int def_rate) {
		this.def_rate = def_rate;
	}
	

}
