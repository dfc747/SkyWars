package new_sd3;

public class CommandRemoveEnemy implements ICommand {

	EnemyShip enemy;

	public CommandRemoveEnemy(EnemyShip theEnemy) {
		this.enemy = theEnemy;
	}

	@Override
	public void execute() {
		enemy.setAlive(false);
		System.out.println("Removed enemy");
		GUI.getMasterShip().removeObserver(enemy);
	}

	@Override
	public void undo() {
		enemy.setAlive(true);
		System.out.println("Resurected enemy");
		GUI.getMasterShip().addObserver(enemy);
		GUI.getMasterShip().score--;
	}

	public String toString() {
		return "RemoveEnemy";
	}

	@Override
	public void redo() {
		enemy.setAlive(false);
		System.out.println("Re-killed enemy");
		GUI.getMasterShip().removeObserver(enemy);
		GUI.getMasterShip().score++;

	}

}
