package new_sd3;

public class CommandMove implements ICommand {
	private int previousX;
	private int previousY;
	private int redoX;
	private int redoY;
	private Ship ship;

	public CommandMove(Ship theShip) {
		this.ship = theShip;

	}

	// Executes the Move command
	@Override
	public void execute() {
		this.previousX = ship.getXpos();
		this.previousY = ship.getYpos();
		ship.Move();

	}

	// Undo the move command
	@Override
	public void undo() {
		this.redoX = ship.getXpos();
		this.redoY = ship.getYpos();
		ship.setXpos(previousX);
		ship.setYpos(previousY);
	}

	
	@Override
	public void redo() {
		ship.setXpos(redoX);
		ship.setYpos(redoY);
		//System.out.println("Redo2 x=" + redoX + " y=" + redoY);

	}
	
	public String toString() {
		return "Move";
	}


}
