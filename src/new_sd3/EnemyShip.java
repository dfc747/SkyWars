package new_sd3;

public class EnemyShip extends Ship implements IObserver {

	public EnemyShip(int xpos, int ypos) {
		setXpos(xpos);
		setYpos(ypos);
	}

	@Override
	public void update(Object o) {

		if (o instanceof MasterSpaceShip) {
			CommandManager.executeCommand(new CommandMove(this));
		}
	}

}
