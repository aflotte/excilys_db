package com.excilys.db.mapper;

public class Companies {
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder(200);
		// sB.append(super.toString());
		sB.append(" | name=").append(this.getName());
		return sB.toString();
		
	}
	
	
}
