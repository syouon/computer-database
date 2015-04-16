package model;

/**
 * The Class Company.
 */
public class Company {

	/** The id. */
	private long id;

	/** The name. */
	private String name;

	/**
	 * Instantiates a new company.
	 *
	 * @param name
	 *            the name
	 */
	public Company(String name) {
		this.name = name;
	}

	/**
	 * Instantiates a new company.
	 *
	 * @param id
	 *            the id
	 * @param name
	 *            the name
	 */
	public Company(long id, String name) {
		this.name = name;
		this.id = id;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "n°" + id + ") " + name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Company other = (Company) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
