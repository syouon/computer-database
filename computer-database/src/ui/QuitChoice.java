package ui;

/**
 * The Class QuitChoice.
 */
public class QuitChoice extends Choice {

	/**
	 * Instantiates a new quit choice.
	 */
	public QuitChoice() {
		super(null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ui.Choice#execute()
	 */
	@Override
	public void execute() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "quit";
	}
}
