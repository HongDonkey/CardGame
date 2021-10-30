package com.kopo.cardgame;

public class Member {

	int idx;
	String id;
	String name;
	String password;
	String created;
	String updated;

	Member() {

	}

	Member(String id) {
		this.id = id;
	}

	Member(int idx, String id, String password, String name, String created, String updated) {

		this.idx = idx;
		this.id = id;
		this.password = password;
		this.name = name;
		this.created = created;
		this.updated = updated;
	}

	Member(int idx, String id, String name, String created, String updated) {
		this.idx = idx;
		this.id = id;
		this.name = name;
		this.created = created;
		this.updated = updated;
	}

	Member(String id, String password, String name) {
		this.id = id;
		this.password = password;
		this.name = name;
	}

	Member(String id, String password) {
		this.id = id;
		this.password = password;
	}

	Member(int idx, String id, String name) {
		this.idx = idx;
		this.id = id;
		this.name = name;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getUpdated() {
		return updated;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "Member [created=" + created + ", id=" + id + ", idx=" + idx + ", name=" + name + ", password="
				+ password + ", updated=" + updated + "]";
	}

}
