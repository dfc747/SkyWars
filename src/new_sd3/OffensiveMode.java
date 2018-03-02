package new_sd3;

import java.util.ArrayList;
import java.util.List;

public class OffensiveMode implements IShipModeStrategy {

	@Override
	public void conflict(MasterSpaceShip ms, List<EnemyShip> enemies) {
		// A conflict happens when the master ship is on the same square with
		// one or more enemies
		// Get the number of enemies in the same square with MS
		List<EnemyShip> es = new ArrayList<>();
		int msx = ms.getXpos();
		int msy = ms.getYpos();

		for (EnemyShip e : enemies) {
			if (msx == e.getXpos() && msy == e.getYpos()) {
				es.add(e);
			}
		}

		int numOFEnemies = es.size();

		// In offensive mode if 3 or more enemies are on the same square player
		// dies and the game is over
		// If 1 or 2 enemies are in the same square with player, then player
		// kills the ships
		if (numOFEnemies >= 3) {
			ms.setAlive(false);
		} else if (numOFEnemies == 1 || numOFEnemies == 2) {
			// remove enemies and observers
			for (EnemyShip enmy : es) {
				CommandManager.getInstance().executeCommand(new CommandRemoveEnemy(enmy));
				ms.removeObserver(enmy); // unregister from observers

				// increment score
				GUI.getMasterShip().score++;

				// play the explosion sound
				SoundEffects.EXPLOSION.run();
				System.out.println("Offensive mode: killed a ship");
			}
		}

	}

}
