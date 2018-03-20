package com.excilys.db.model;

public class Companies {
	static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Companies.class);
	private Integer id;
	private String name;

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Companies() {
		super();
		name="";
		id=null;
	}


	public Companies(Integer id){
		this.id = id;
		name = "";
	}

	@Override
	public String toString() {
		StringBuilder sB = new StringBuilder(200);
		sB.append(" | id=").append(this.getId());
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
		if (!(this.getName().equals(((Companies) obj).getName()))) {
			return false;
		}
		if (this.getId()==null) {
			if (((Companies) obj).getId()!=null){
				return false;
			}
		}else {
			if((!this.getId().equals(((Companies) obj).getId()))) {
				return false;
			}
		}
		return true;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
