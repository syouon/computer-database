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
		if (page != null && !page.equals("") && page.matches("\\d*")) {
			this.page = Integer.parseInt(page);
		}
	}

	public int getRange() {
		return range;
	}

	public void setRange(String range) {
		if (range != null && !range.equals("") && range.matches("\\d*")) {
			this.range = Integer.parseInt(range);
		}
	}

	public boolean isDesc() {
		return desc;
	}

	public void setDesc(String descState) {
		if (descState != null && !descState.equals("")
				&& descState.equals("true")) {
			desc = true;
		} else {
			desc = false;
		}
	}

	public void reverseDesc() {
		this.desc = !this.desc;
	}
}
