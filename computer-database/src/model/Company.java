package model;

public class Company {

	private long id;
	private String name;

	public Company(String name) {
		this.name = name;
	}

	public Company(long id, String name) {
		this.name = name;
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String toString() {
		return "n°" + id + ") " + name;
	}
}
