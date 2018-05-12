package com.fun;

public enum Side {

	DARK("X"), LIGHT("O");

	private String id;

	Side(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
