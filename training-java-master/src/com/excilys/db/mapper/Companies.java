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
	
	@Override
	public boolean equals(Object obj) {
		if (super.equals(obj)) {
			return true;
		}
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		if (!this.getName().equals(((Companies) obj).getName())) {
			return false;
		}
		return true;
	}
}
