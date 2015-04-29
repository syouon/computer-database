package servlet;

public class Page {

	private int page;
	private int range;
	private boolean desc;

	public Page() {
		page = 1;
		range = 10;
	}

	public int getPage() {
		return page;
	}

	public void setPage(String page) {
		
	}

	public int getRange() {
		return range;
	}

	public void setRange(String range) {
		
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(String descState) {
		
	}

	public void reverseDesc() {
		this.desc = !this.desc;
	}
}
