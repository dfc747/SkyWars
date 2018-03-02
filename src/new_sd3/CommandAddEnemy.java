package new_sd3;

public class CommandAddEnemy implements ICommand {
	private EnemyShip enemy;

	public CommandAddEnemy(EnemyShip theEnemy) {
		this.enemy = theEnemy;
	}

	@Override
	public void execute() {
		// play the spawn sound
		SoundEffects.SPAWN.run();
		// MasterShip will execute the command
	}

	@Override
	public void undo() {
		enemy.setAlive(false);
		System.out.println("Removed enemy");
		GUI.getMasterShip().removeObserver(enemy); // unregister observer
	}

	public String toString() {
		return "AddEnemy";
	}

	@Override
	public void redo() {
		enemy.setAlive(true);
		GUI.getMasterShip().addObserver(enemy);

	}
}
